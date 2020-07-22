package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Line extends BaseObject {
    @Expose
    public String name;
    @Expose
    public String fahrtNr;

    @Expose
    public Mode mode;
    @Expose(deserialize = false)
    public Product product;
    @Expose
    public Route[] routes;
    @Expose
    public Operator operator;

    public void setProduct(Product product) {
        this.product = product;
    }
}
