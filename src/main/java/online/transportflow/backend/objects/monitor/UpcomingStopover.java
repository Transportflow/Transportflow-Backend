package online.transportflow.backend.objects.monitor;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.utils.TimeUtils;

import java.util.Date;
import java.util.List;

public class UpcomingStopover {
    @Expose
    public Stop stop;


    @Expose
    public Date departure;
    @Expose
    public Date plannedDeparture;
    @Expose(deserialize = false)
    public String relativeDeparture;
    @Expose(deserialize = false)
    public String clockDeparture;
    @Expose
    public double departureDelay;
    @Expose
    public String departurePlatform;
    @Expose
    public String plannedDeparturePlatform;


    @Expose
    public Date arrival;
    @Expose
    public Date plannedArrival;
    @Expose(deserialize = false)
    public String relativeArrival;
    @Expose(deserialize = false)
    public String clockArrival;
    @Expose
    public double arrivalDelay;
    @Expose
    public String arrivalPlatform;
    @Expose
    public String plannedArrivalPlatform;


    public UpcomingStopover() {

    }

    public UpcomingStopover(Stop stop, Date departure, Date plannedDeparture, double departureDelay, String departurePlatform, String plannedDeparturePlatform, Date arrival, Date plannedArrival, double arrivalDelay, String arrivalPlatform, String plannedArrivalPlatform) {
        this.stop = stop;
        this.departure = departure;
        this.plannedDeparture = plannedDeparture;
        this.relativeDeparture = relativeDeparture;
        this.clockDeparture = clockDeparture;
        this.departureDelay = departureDelay;
        this.departurePlatform = departurePlatform;
        this.plannedDeparturePlatform = plannedDeparturePlatform;
        this.arrival = arrival;
        this.plannedArrival = plannedArrival;
        this.relativeArrival = relativeArrival;
        this.clockArrival = clockArrival;
        this.arrivalDelay = arrivalDelay;
        this.arrivalPlatform = arrivalPlatform;
        this.plannedArrivalPlatform = plannedArrivalPlatform;
    }

    public void generateRelatives(Date to) {
        if (departure != null) {
            relativeDeparture = TimeUtils.getRelativeTime(departure, to.getTime());
            clockDeparture = TimeUtils.getClockTime(departure);
        }

        arrivalDelay = arrivalDelay/60;
        if (arrival != null) {
            relativeArrival = TimeUtils.getRelativeTime(arrival, to.getTime());
            clockArrival = TimeUtils.getClockTime(arrival);
        }
    }

    public void polish() {
        // try to always present departure when
        if (departure == null) {
            departure = plannedDeparture;
        } else {
            plannedDeparture = departure;
        }

        // try to always present departure platform
        if (departurePlatform == null) {
            departurePlatform = plannedDeparturePlatform;
        } else {
            plannedDeparturePlatform = departurePlatform;
        }

        // try to always present arrival when
        if (arrival == null) {
            arrival = plannedArrival;
        } else {
            plannedArrival = arrival;
        }

        // try to always present arrival platform
        if (arrivalPlatform == null) {
            arrivalPlatform = plannedArrivalPlatform;
        } else {
            plannedArrivalPlatform = arrivalPlatform;
        }
    }
}
