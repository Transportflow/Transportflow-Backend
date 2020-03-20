package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Coordinates {
    @Expose(serialize = true)
    public double latitude;
    @Expose(serialize = true)
    public double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
