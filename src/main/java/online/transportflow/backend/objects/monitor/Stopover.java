package online.transportflow.backend.objects.monitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Remark;
import online.transportflow.backend.objects.location.Stop;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

/**
 * This is either a departure or arrival
 */
public class Stopover {
    @Expose
    public String tripId;
    @Expose
    public String direction;

    @Expose
    public Line line;
    @Expose
    public Stop stop;

    @Expose
    public boolean cancelled;

    @Expose
    @Nullable
    public Date when;
    @Expose
    @Nullable
    public Date plannedWhen;
    @Expose
    @Nullable
    public Date scheduledWhen;
    @Expose
    public int delay;

    @Expose
    @Nullable
    public String platform;
    @Expose
    @Nullable
    public String plannedPlatform;
    @Expose
    @Nullable
    public String prognosedPlatform;
    @Expose(deserialize = false)
    public long relativeWhen;
    @Expose(deserialize = false)
    public String clockWhen;

    @Expose
    public List<Remark> remarks;

    public void polish() {
        // try to always present when
        if (when == null && plannedWhen != null)
            when = plannedWhen;
        else if (when == null && scheduledWhen != null) {
            when = scheduledWhen;
            plannedWhen = scheduledWhen;
        }

        if (plannedWhen == null)
            plannedWhen = when;

        // try to always present platform
        if (platform == null && prognosedPlatform != null)
            platform = prognosedPlatform;
        else if (platform == null && plannedPlatform != null)
            platform = plannedPlatform;
    }
}
