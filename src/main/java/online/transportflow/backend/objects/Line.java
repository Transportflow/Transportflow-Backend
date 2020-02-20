package online.transportflow.backend.objects;

public class Line {
    private String id;
    private String fahrtNr;
    private String name;
    private String mode;
    private String product;
    private String symbol;
    private int nr;

    public Line(String id, String fahrtNr, String name, String mode, String product, String symbol, int nr) {
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

    public String getProduct() {
        return product;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNr() {
        return nr;
    }
}
