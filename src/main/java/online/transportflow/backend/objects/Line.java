package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Line extends BaseObject {
    @Expose
    private String name;
    @Expose
    private String fahrtNr;

    @Expose
    private Mode mode;
    @Expose(deserialize = false)
    private Product product;
    @Expose
    private Route[] routes;
    @Expose
    private Operator operator;

    public void setProduct(Product product) {
        this.product = product;
    }
}
