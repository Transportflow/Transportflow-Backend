package online.transportflow.backend.objects.monitor;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Monitor {
    @Expose
    public List<Stopover> stopovers;

    public Monitor(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }
}
