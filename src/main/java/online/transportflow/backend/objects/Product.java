package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Product {
    @Expose
    public String name;
    @Expose
    public String title;
    @Expose
    public String img;

    public Product(String name, String title, String img) {
        this.name = name;
        this.title = title;
        this.img = img;
    }
}
