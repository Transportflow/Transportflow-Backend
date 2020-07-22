package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Nullable;

public class Remark {
    @Expose
    public String type;
    @Expose
    public String text;

    @Expose
    @Nullable
    public String summary;
    @Expose
    public int priority;
}
