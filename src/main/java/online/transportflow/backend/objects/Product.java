package online.transportflow.backend.objects;

public class Product {
    public String code;
    public String name;
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
