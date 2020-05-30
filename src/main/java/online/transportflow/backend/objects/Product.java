package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Product {
    @Expose
    private String name;
    @Expose
    private String title;
    @Expose
    private String img;

    public Product(String name, String title, String img) {
        this.name = name;
        this.title = title;
        this.img = img;
    }
}
