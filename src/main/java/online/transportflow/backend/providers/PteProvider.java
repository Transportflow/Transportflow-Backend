package online.transportflow.backend.providers;

import online.transportflow.backend.objects.Line;
import online.transportflow.backend.objects.Operator;
import online.transportflow.backend.objects.Product;
import online.transportflow.backend.objects.location.Location;
import online.transportflow.backend.objects.location.Stop;
import online.transportflow.backend.objects.monitor.Stopover;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PteProvider extends GeneralProvider {
    public PteProvider(String regionName, String region, String provider, String language, String image, String textColor, List<Product> products) {
        super("", regionName, region, provider, language, image, textColor, products);
    }

    public static Stop PteStopLocationToTransportflow(de.schildbach.pte.dto.Location l) {
        return new Stop(l.id, l.name + ", " + l.place, PteLocationToTransportflow(l), l.products != null ? PteProductsToTransportflow(l.products) : new ArrayList<>(), null, null, 0);
    }

    public static List<Stopover> PteDeparturesToTransportflow(List<de.schildbach.pte.dto.Departure> departures, de.schildbach.pte.dto.Location location) {
        List<Stopover> stopovers = new ArrayList<>();
        departures.forEach(departure -> {
            int delay = 0;
            try {
                delay = Math.round(departure.predictedTime.getTime() - departure.plannedTime.getTime() * 60000);
            } catch (NullPointerException ignore) {}


            Stopover s = new Stopover(departure.line.id,
                    departure.destination != null ? departure.destination.name + ", " + departure.destination.place : "--",
                    new Line(departure.line.name,
                            departure.line.id,
                            departure.line.product != null ? String.valueOf(departure.line.product.code) : "",
                            departure.line.product != null ? PteProductToTransportflow(departure.line.product) : null,
                            null, new Operator(departure.line.network)),
                    PteStopLocationToTransportflow(location),
                    false,
                    departure.predictedTime,
                    departure.plannedTime,
                    departure.predictedTime,
                    delay,
                    departure.position != null ? departure.position.name : "--",
                    null,
                    null,
                    null
                    );
            s.polish();

            stopovers.add(s);
        });
        return stopovers;
    }

    public static List<Product> PteProductsToTransportflow(Set<de.schildbach.pte.dto.Product> p) {
        List<Product> products = new ArrayList<>();
        p.forEach(prdct -> {
            products.add(PteProductToTransportflow(prdct));
        });
        return products;
    }

    public static Product PteProductToTransportflow(de.schildbach.pte.dto.Product p) {
        Product product;
        switch (p) {
            case BUS:
                product = new Product("bus", "Bus", DefaultLogos.getBus());
                break;
            case TRAM:
                product = new Product("tram", "Tram", DefaultLogos.getTram());
                break;
            case FERRY:
                product = new Product("ferry", "Fähre", DefaultLogos.getFerry());
                break;
            case SUBWAY:
                product = new Product("subway", "U-Bahn", DefaultLogos.getSubway());
                break;
            case CABLECAR:
                product = new Product("cablecar", "Seilbahn", "https://www.dvb.de/assets/img/trans-icon/transport-lift.svg");
                break;
            case ON_DEMAND:
                product = new Product("taxi", "Taxi", DefaultLogos.getTaxi());
                break;
            case REGIONAL_TRAIN:
                product = new Product("regional", "Regionalzug", DefaultLogos.getRegional());
                break;
            case SUBURBAN_TRAIN:
                product = new Product("suburban", "Suburban", DefaultLogos.getSuburban());
                break;
            case HIGH_SPEED_TRAIN:
                product = new Product("highspeed", "Schnellzug", DefaultLogos.getTrain());
                break;
            default:
                product = new Product(String.valueOf(p.code), p.name(), "");
                break;
        }

        return product;
    }

    public static Location PteLocationToTransportflow(de.schildbach.pte.dto.Location l) {
        double lon = 0;
        double lat = 0;
        try {
            lon = l.getLonAsDouble();
            lat = l.getLatAsDouble();
        } catch (NullPointerException ignore) {
        }
        return new Location(l.name, l.place, lon, lat, 0);
    }
}
