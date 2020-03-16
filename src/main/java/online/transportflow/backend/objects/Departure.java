package online.transportflow.backend.objects;

import java.util.Date;
import java.util.List;

public class Departure {
    public String tripId;
    public Location stop;
    public Date when;
    public String direction;
    public Line line;
    public List<Remark> remarks;
    public int delay;
    public String platform;
    public Boolean cancelled;

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
    }
}
