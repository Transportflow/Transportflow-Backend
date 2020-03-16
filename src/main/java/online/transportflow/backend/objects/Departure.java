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
    public String arrivalTimeRelative;
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
