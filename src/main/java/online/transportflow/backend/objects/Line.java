package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Nullable;

public class Line {
    @Expose(serialize = true)
    public String id;
    @Expose(serialize = true)
    public String fahrtNr;
    @Expose(serialize = true)
    public String name;
    @Expose(serialize = true)
    public String mode;
    @Expose(serialize = true)
    public Product product;
    @Expose(serialize = true)
    public String symbol;
    @Expose(serialize = true)
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
