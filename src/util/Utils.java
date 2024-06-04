package util;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private Utils() {
    }

    public static Map<String, String> parseUrlEncoded(String raw, String delimiter) {
        String[] pairs = raw.split(delimiter);
        Stream<Map.Entry<String, String>> stream = Arrays.stream(pairs)
                .map(e -> decode(e))
                .filter(e -> e.isPresent())
                .map(e -> e.get());

        return stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Optional<Map.Entry<String,String>> decode(String kv){
        if (!kv.contains("=")){
            return Optional.empty();
        }

        String[] pair = kv.split("=");
        if (pair.length != 2){
            return Optional.empty();

        }

        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pair[0], utf8).strip();
        String value = URLDecoder.decode(pair[1],utf8).strip();
        return Optional.of(Map.entry(key,value));
    }
}