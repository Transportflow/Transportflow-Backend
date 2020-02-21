package online.transportflow.backend.objects;

import java.util.Date;
import java.util.List;

public class UpcomingStop extends Location {
    private Date when;

    public UpcomingStop(String stopId, String stopName, String stopCity, List<Product> products, Coordinates location, Date when) {
        super(LocationType.STOP, stopId, stopName, stopCity, products, location, null);
        this.when = when;
    }

    public Date getWhen() {
        return when;
    }
}
