package server;

public enum ContentType {
    TEXT_PLAIN("text/plain; charset=utf-8"),
    TEXT_HTML("text/html; charset=utf-8"),
    TEXT_CSS("text/css"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private final String desc;

    ContentType(String desc) {
        this.desc =  desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
