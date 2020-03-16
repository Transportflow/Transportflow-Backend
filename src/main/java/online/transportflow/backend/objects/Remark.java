package online.transportflow.backend.objects;

public class Remark {
    public RemarkType type;
    public String code;
    public String summary = "";
    public String text;

    public Remark(RemarkType type, String code, String summary, String text) {
        this.type = type;
        this.code = code;
        this.summary = summary;
        this.text = text;
    }
}
