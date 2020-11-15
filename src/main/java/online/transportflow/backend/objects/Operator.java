package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Operator extends BaseObject {
    @Expose
    private String name;

    public Operator(String name) {
        this.name = name;
    }
}
