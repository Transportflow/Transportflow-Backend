package online.transportflow.backend.objects;

import org.jetbrains.annotations.Nullable;

public class Line {
    public String id;
    public String fahrtNr;
    public String name;
    public String mode;
    public Product product;
    public String symbol;
    public int nr;

    public Line(String id, String fahrtNr, String name, String mode, Product product, @Nullable String symbol, int nr) {
        this.id = id;
        this.fahrtNr = fahrtNr;
        this.name = name;
        this.mode = mode;
        this.product = product;
        this.symbol = symbol;
        this.nr = nr;
    }

    public String getId() {
        return id;
    }

    public String getFahrtNr() {
        return fahrtNr;
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }

    public Product getProduct() {
        return product;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNr() {
        return nr;
    }
}
