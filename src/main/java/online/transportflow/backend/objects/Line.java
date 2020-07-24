package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Line extends BaseObject {
    @Expose
    public String name;
    @Expose
    public String fahrtNr;

    @Expose
    public String mode;
    @Expose(deserialize = false)
    public Product product;
    @Expose
    public Route[] routes;
    @Expose
    public Operator operator;

    public Line(String name, String fahrtNr, String mode, Product product, Route[] routes, Operator operator) {
        this.name = name;
        this.fahrtNr = fahrtNr;
        this.mode = mode;
        this.product = product;
        this.routes = routes;
        this.operator = operator;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
