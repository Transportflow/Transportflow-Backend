package online.transportflow.backend.objects;

import com.sun.istack.internal.Nullable;

import java.util.List;

public class Location {
    private LocationType type;
    private String id;
    private String name;
    private String city;
    private List<String> stopIcons;

    private Coordinates coordinates;

    public Location(LocationType type, @Nullable String id, String name, @Nullable String city, List<String> stopIcons, Coordinates coordinates) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.city = city;
        this.stopIcons = stopIcons;
        this.coordinates = coordinates;
    }

    public void setStopIcons(List<String> stopIcons) {
        this.stopIcons = stopIcons;
    }

    public void addStopIcon(String icon) {
        this.stopIcons.add(icon);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<String> getStopIcons() {
        return stopIcons;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocationType getType() {
        return type;
    }
}
