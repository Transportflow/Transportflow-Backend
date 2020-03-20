package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Monitor {
    @Expose(serialize = true)
    public Location location;
    @Expose(serialize = true)
    public List<Departure> departures;
}
