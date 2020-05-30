package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Line extends BaseObject {
    @Expose
    private String name;

    @Expose
    private Mode mode;
    @Expose
    private Product product;
    @Expose
    private Route[] routes;
    @Expose
    private Operator operator;
}
