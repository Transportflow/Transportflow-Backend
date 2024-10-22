package online.transportflow.backend.providers.HafasUtils;

import com.google.gson.*;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Stopover;
import online.transportflow.backend.utils.TimeUtils;

import java.lang.reflect.Type;
import java.util.List;

public class HafasStopoverDeserializer implements JsonDeserializer<Stopover> {
    List<Product> providerProducts = null;
    long relative = 0;

    public HafasStopoverDeserializer(List<Product> providerProducts, long relative) {
        this.providerProducts = providerProducts;
        this.relative = relative;
    }

    @Override
    public Stopover deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Stopover stopover = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Stop.class, new HafasStopDeserializer(providerProducts))
                .registerTypeAdapter(Line.class, new HafasLineDeserializer(providerProducts))
                .create().fromJson(jsonElement.getAsJsonObject(), Stopover.class);

        stopover.polish();

        stopover.delay = stopover.delay/60;
        if (stopover.when != null) {
            stopover.relativeWhen = TimeUtils.getRelativeTime(stopover.when, relative);
            stopover.clockWhen = TimeUtils.getClockTime(stopover.when);
        }

        return stopover;
    }
}
