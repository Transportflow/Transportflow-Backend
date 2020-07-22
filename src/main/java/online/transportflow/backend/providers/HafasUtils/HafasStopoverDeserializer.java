package online.transportflow.backend.providers.HafasUtils;

import com.google.gson.*;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Stopover;

import java.lang.reflect.Type;
import java.util.List;

public class HafasStopoverDeserializer implements JsonDeserializer<Stopover> {
    List<Product> providerProducts = null;

    public HafasStopoverDeserializer(List<Product> providerProducts) {
        this.providerProducts = providerProducts;
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
            stopover.relativeWhen = (stopover.when.getTime() - System.currentTimeMillis()) / 60000+ "'";
            if (Integer.parseInt(stopover.relativeWhen.replaceAll("'", "")) > 59)
                stopover.relativeWhen = (stopover.when.getTime() - System.currentTimeMillis()) / 60000 / 60 + "h";
            var hours = String.valueOf(stopover.when.getHours());
            var minutes = String.valueOf(stopover.when.getMinutes());
            stopover.clockWhen = ("0" + hours).substring(hours.length()-1) + ":" + ("0" + minutes).substring(minutes.length()-1);
        }

        return stopover;
    }
}
