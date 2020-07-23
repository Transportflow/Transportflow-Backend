package online.transportflow.backend.providers.HafasUtils;

import com.google.gson.*;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.UpcomingStopover;

import java.lang.reflect.Type;
import java.util.List;

public class HafasUpcomingStopDeserializer implements JsonDeserializer<UpcomingStopover> {
    List<Product> providerProducts = null;

    public HafasUpcomingStopDeserializer(List<Product> providerProducts) {
        this.providerProducts = providerProducts;
    }

    @Override
    public UpcomingStopover deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        UpcomingStopover upcomingStopover = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Stop.class, new HafasStopDeserializer(providerProducts))
                .registerTypeAdapter(Line.class, new HafasLineDeserializer(providerProducts))
                .create().fromJson(jsonElement.getAsJsonObject(), UpcomingStopover.class);

        upcomingStopover.polish();

        upcomingStopover.departureDelay = upcomingStopover.departureDelay/60;

        return upcomingStopover;
    }
}