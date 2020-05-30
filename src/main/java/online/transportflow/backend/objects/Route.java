package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.location.Stop;

public class Route extends BaseObject {
    @Expose
    private Line line;
    @Expose
    private String mode;
    @Expose
    private Stop[] stops;
}
