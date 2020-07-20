package online.transportflow.backend.providers.HafasUtils;

import online.transportflow.backend.providers.BvgProvider;

public class BvgStopDeserializer extends HafasStopDeserializer {
    public BvgStopDeserializer() {
        super.providerProducts = BvgProvider.getProviderProducts();
    }
}
