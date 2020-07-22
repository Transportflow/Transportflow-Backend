package online.transportflow.backend.providers;

import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.providers.regions.DvbProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DvbProviderTest {
    @Test
    public void locationSearchTest() {
        DvbProvider p = new DvbProvider(DvbProvider.getProviderProducts());
        List<Stop> s = p.searchLocation("Steglichstraße", 10, true, false, false);
        for (Stop i :
                s) {
            System.out.println(i.getName());
        }
    }
}
