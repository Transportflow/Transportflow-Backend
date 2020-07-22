package online.transportflow.backend.objects.monitor;

import com.google.gson.annotations.Expose;
import online.transportflow.backend.objects.location.Stop;

import java.util.List;

public class Monitor {
    @Expose
    public Stop stop;
    @Expose
    public List<Stopover> stopovers;

    public Monitor(Stop stop, List<Stopover> stopovers) {
        this.stop = stop;
        this.stopovers = stopovers;
    }
}
