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
    @Expose(serialize = false)
    public Stop stop;

    @Expose
    public boolean cancelled = false;

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
    public String relativeWhen;
    @Expose(deserialize = false)
    public String clockWhen;

    @Expose
    public List<Remark> remarks;

    public Stopover(String tripId, String direction, Line line, Stop stop, boolean cancelled, @Nullable Date when, @Nullable Date plannedWhen, @Nullable Date scheduledWhen, int delay, @Nullable String platform, @Nullable String plannedPlatform, @Nullable String prognosedPlatform, String relativeWhen, String clockWhen, List<Remark> remarks) {
        this.tripId = tripId;
        this.direction = direction;
        this.line = line;
        this.stop = stop;
        this.cancelled = cancelled;
        this.when = when;
        this.plannedWhen = plannedWhen;
        this.scheduledWhen = scheduledWhen;
        this.delay = delay;
        this.platform = platform;
        this.plannedPlatform = plannedPlatform;
        this.prognosedPlatform = prognosedPlatform;
        this.relativeWhen = relativeWhen;
        this.clockWhen = clockWhen;
        this.remarks = remarks;
    }

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
