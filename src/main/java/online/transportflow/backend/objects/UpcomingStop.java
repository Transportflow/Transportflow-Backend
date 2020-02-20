package online.transportflow.backend.objects;

import java.util.Date;
import java.util.List;

public class UpcomingStop extends Stop {
    private Date when;

    public UpcomingStop(String stopId, String stopName, String stopCity, List<String> stopIcons, long latitude, long longitude, Date when) {
        super(stopId, stopName, stopCity, stopIcons, latitude, longitude);
        this.when = when;
    }

    public Date getWhen() {
        return when;
    }
}
