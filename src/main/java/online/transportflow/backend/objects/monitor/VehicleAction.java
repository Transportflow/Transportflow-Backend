package online.transportflow.backend.objects.monitor;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.location.Stop;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * This is either a departure or arrival
 */
public class VehicleAction {
    @Expose
    private String tripId;
    @Expose
    private int trip;
    @Expose
    private String direction;

    @Expose
    private Line line;
    @Expose
    private Stop stop;

    @Expose
    private boolean cancelled;

    @Expose
    @Nullable
    private Date when;
    @Expose
    @Nullable
    private Date plannedWhen;
    @Expose
    @Nullable
    private Date scheduledWhen;
    @Expose
    private int delay;

    @Expose
    @Nullable
    private String platform;
    @Expose
    @Nullable
    private String plannedPlatform;
    @Expose
    @Nullable
    private String prognosedPlatform;

    public void polish() {
        // try to always present when
        if (when == null && plannedWhen != null)
            when = plannedWhen;
        else if (when == null && scheduledWhen != null)
            when = scheduledWhen;

        // try to always present platform
        if (platform == null && prognosedPlatform != null)
            platform = prognosedPlatform;
        else if (platform == null && plannedPlatform != null)
            platform = plannedPlatform;
    }
}
