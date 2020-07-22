package online.transportflow.backend.providers.HafasUtils;

import com.google.gson.*;
import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Product;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HafasLineDeserializer implements JsonDeserializer<Line> {
    List<Product> providerProducts = null;

    public HafasLineDeserializer(List<Product> providerProducts) {
        this.providerProducts = providerProducts;
    }

    @Override
    public Line deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Line line = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(jsonElement.getAsJsonObject(), Line.class);

        try {
            AtomicReference<Product> p = new AtomicReference<>(null);
            if (jsonElement.getAsJsonObject().get("product") != null) {
                String jsonProduct = jsonElement.getAsJsonObject().get("product").getAsString();
                providerProducts.forEach(product -> {
                    if (product.name.equals(jsonProduct))
                        p.set(product);
                });
            }

            line.setProduct(p.get());
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            System.out.println("Product cannot be serialized ..");
        }

        return line;
    }
}
