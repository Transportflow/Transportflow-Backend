package online.transportflow.backend.objects;

import java.util.Date;
import java.util.List;

public class UpcomingStop extends Location {
    private Date when;

    public UpcomingStop(String stopId, String stopName, String stopCity, List<String> stopIcons, Coordinates location, Date when) {
        super(LocationType.STOP, stopId, stopName, stopCity, stopIcons, location);
        this.when = when;
    }

    public Date getWhen() {
        return when;
    }
}
