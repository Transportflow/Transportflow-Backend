package online.transportflow.backend.objects;

import java.util.List;

public class Stop {
    private String stopId;
    private String stopName;
    private String stopCity;
    private List<String> stopIcons;

    private long latitude;
    private long longitude;

    public Stop(String stopId, String stopName, String stopCity, List<String> stopIcons, long latitude, long longitude) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopCity = stopCity;
        this.stopIcons = stopIcons;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setStopIcons(List<String> stopIcons) {
        this.stopIcons = stopIcons;
    }

    public void addStopIcon(String icon) {
        this.stopIcons.add(icon);
    }

    public String getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public String getStopCity() {
        return stopCity;
    }

    public List<String> getStopIcons() {
        return stopIcons;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
