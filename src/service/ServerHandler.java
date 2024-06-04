package service;

import com.sun.net.httpserver.HttpExchange;
import entity.Patient;
import entity.ScheduleEntry;
import entity.ScheduleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import server.BasicServer;
import server.ContentType;
import server.ResponseCodes;
import util.Utils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ServerHandler extends BasicServer {
    private static final Random random = new Random();

    private final static Configuration freemarker = initFreeMarker();
    private static ScheduleModel scheduleModel;


    public ServerHandler(String host, int port) throws IOException {
        super(host, port);
        scheduleModel = generateScheduleModel();
        registerGet("/", this::mainHandler);
        registerGet("/schedule", this::scheduleHandler);
        registerGet("/schedule/currentday", this::thisDayHandler);
        registerPost("/delete", this::deleteHandler);
        registerPost("/register", this::registerPost);
        registerGet("/register", this::registerHandler);
    }

    private void mainHandler(HttpExchange exchange){
        String templateFile = "index.ftlh";
        renderTemplate(exchange, templateFile, null);
    }

    private void registerHandler(HttpExchange exchange) {
        String templateFile = "register.ftlh";
        renderTemplate(exchange, templateFile, null);
    }

    private void deleteHandler(HttpExchange exchange) {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            br.close();
            Map<String, String> formData = Utils.parseUrlEncoded(requestBody.toString(), "&");
            String name = formData.get("name");
            LocalDate dob = LocalDate.parse(formData.get("dob"));
            LocalDate selectedDate = LocalDate.parse(formData.get("selectedDate"));

            scheduleModel.removePatient(name, dob, selectedDate);

            redirect303(exchange, "/schedule/thisday?date=" + selectedDate);
        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }



    private void registerPost(HttpExchange exchange) {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            br.close();
            Map<String, String> formData = Utils.parseUrlEncoded(requestBody.toString(), "&");
            String name = formData.get("name");
            LocalDate dob = LocalDate.parse(formData.get("dob"));
            String type = formData.get("type");
            String symptoms = formData.get("symptoms");
            LocalTime time = LocalTime.parse(formData.get("time"));
            LocalDate selectedDate = LocalDate.parse(formData.get("selectedDate"));
            if (selectedDate.isBefore(LocalDate.now())) {
                redirect303(exchange, "/");
                return;
            }

            Patient patient = new Patient(name, dob, type, symptoms, time);
            scheduleModel.addPatient(patient, selectedDate);
            redirect303(exchange, "/schedule");
        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }


    private Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                map.put(key, value);
            }
        }
        return map;
    }

    private void thisDayHandler(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        if (queryParams.length == 2 && queryParams[0].equals("date")) {
            try {
                LocalDate selectedDate = LocalDate.parse(queryParams[1]);

                List<Patient> patients = scheduleModel.getPatientsForDate(selectedDate);

                patients.sort(Comparator.comparing(Patient::getTime));

                Map<String, Object> model = new HashMap<>();
                model.put("selectedDate", selectedDate);
                model.put("patients", patients);

                String templateFile = "currentday.ftlh";
                renderTemplate(exchange, templateFile, model);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        } else {
            redirect303(exchange, "/schedule");
        }
    }


    private void scheduleHandler(HttpExchange exchange) {
        String templateFile = "schedule.ftlh";
        Map<String, Object> model = new HashMap<>();
        model.put("currentDate", LocalDate.now());
        model.put("scheduleEntries", scheduleModel.getScheduleEntries());
        renderTemplate(exchange, templateFile, model);
    }

    private ScheduleModel generateScheduleModel() {
        List<ScheduleEntry> entries = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int dayOfMonth = 1; dayOfMonth <= currentDate.lengthOfMonth(); dayOfMonth++) {
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth);
            List<Patient> patients = generateRandomPatients();
            ScheduleEntry entry = new ScheduleEntry();
            entry.setDate(date);
            entry.setTime(LocalTime.of(random.nextInt(8)+7, 0));
            entry.setPatients(patients);
            entries.add(entry);
        }
        return new ScheduleModel(entries);
    }

    private List<Patient> generateRandomPatients() {
        List<Patient> patients = new ArrayList<>();
        int numPatients = new Random().nextInt(5)+1;
        for (int i = 0; i < numPatients; i++) {
            String name = Generator.makeName();
            LocalDate dob = generateRandomDateOfBirth();
            String type = generateRandomPatientType();
            String symptoms =  Generator.makeDescription();
            LocalTime time = LocalTime.of(random.nextInt(8)+7, 0);

            Patient patient = new Patient(name, dob, type, symptoms, time );
            patients.add(patient);
        }

        return patients;
    }
    private static LocalDate generateRandomDateOfBirth() {
        int year = random.nextInt(100) + 1900;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        return LocalDate.of(year, month, day);
    }

    private static String generateRandomPatientType() {
        return random.nextBoolean() ? "Первичный" : "Вторичный";
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("templates"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();

                var data = stream.toByteArray();

                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
