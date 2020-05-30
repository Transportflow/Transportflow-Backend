package online.transportflow.backend.objects.location;

import com.google.gson.annotations.Expose;

/**
 * A location object is used by other items to indicate their locations.
 */
public class Location {
    @Expose
    private final String type = "location";
    @Expose
    private String name;
    @Expose
    private String address;

    @Expose
    private double longitude;
    @Expose
    private double latitude;
    @Expose
    private double altitude;
}
