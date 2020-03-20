package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class Departure {
    @Expose(serialize = true)
    public String tripId;
    public Location stop;
    @Expose(serialize = true)
    public Date when;
    @Expose(serialize = true)
    public String direction;
    @Expose(serialize = true)
    public Line line;
    @Expose(serialize = true)
    public List<Remark> remarks;
    @Expose(serialize = true)
    public int delay;
    @Expose(serialize = true)
    public String platform;
    @Expose(serialize = true)
    public Boolean cancelled;
    @Expose(serialize = true)
    public String arrivalTimeRelative;
    @Expose(serialize = true)
    public String arrivalTime;

    public Departure(String tripId, Location stop, Date when, String direction, Line line, List<Remark> remarks, int delay, String platform, Boolean cancelled) {
        this.tripId = tripId;
        this.stop = stop;
        this.when = when;
        this.direction = direction;
        this.line = line;
        this.remarks = remarks;
        this.delay = delay;
        this.platform = platform;
        this.cancelled = cancelled;

        Date currentDate = new Date();
        long arrivalTimeRelativeRaw = (when.getTime() - currentDate.getTime()) / 60000;
        if (arrivalTimeRelativeRaw < 60)
            arrivalTimeRelative = arrivalTimeRelativeRaw + "'";
        else
            arrivalTimeRelative = arrivalTimeRelativeRaw / 60 + "h";
        arrivalTime = when.getHours() + ":" + when.getMinutes();
    }
}
