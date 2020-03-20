package online.transportflow.backend.objects;

import com.google.gson.annotations.Expose;

public class Product {
    @Expose(serialize = true)
    public String code;
    @Expose(serialize = true)
    public String name;
    @Expose(serialize = true)
    public String iconUrl;

    public Product(String code, String name, String iconUrl) {
        this.code = code;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
