package online.transportflow.backend.providers.HafasUtils;

import com.google.gson.*;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Stop;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HafasStopDeserializer implements JsonDeserializer<Stop> {
    List<Product> providerProducts = null;

    public HafasStopDeserializer(List<Product> providerProducts) {
        this.providerProducts = providerProducts;
    }

    @Override
    public Stop deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Stop stop = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(jsonElement.getAsJsonObject(), Stop.class);

        try {
            List<Product> products = new ArrayList<>();
            if (jsonElement.getAsJsonObject().get("products") != null) {
                JsonObject jsonProducts = jsonElement.getAsJsonObject().get("products").getAsJsonObject();
                providerProducts.forEach(product -> {
                    if (jsonProducts.get(product.name).getAsBoolean()) {
                        products.add(product);
                    }
                });
            }

            stop.setProducts(products);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            System.out.println("Product cannot be serialized ..");
        }

        return stop;
    }
}
