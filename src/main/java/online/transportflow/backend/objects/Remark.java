package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Remark {
    @Expose(serialize = true)
    public RemarkType type;
    @Expose(serialize = true)
    public String code;
    @Expose(serialize = true)
    public String summary = "";
    @Expose(serialize = true)
    public String text;

    public Remark(RemarkType type, String code, String summary, String text) {
        this.type = type;
        this.code = code;
        this.summary = summary;
        this.text = text;
    }
}
