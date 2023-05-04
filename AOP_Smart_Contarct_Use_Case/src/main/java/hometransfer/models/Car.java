package hometransfer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataType()
public final class Car {
    @Property()
    private String id;
    @Property()
    private String brand;
    @Property()
    private String model;
    @Property()
    private String color;
    @Property()
    private String ownerName;

    @Property()
    private String price;
    @Property()
    private String license;

    public Car(@JsonProperty("id") final String id, @JsonProperty("brand") final String brand, @JsonProperty("model") final String model, @JsonProperty("color") final String color, @JsonProperty("ownerName") final String ownerName, @JsonProperty("price") final String  license, @JsonProperty("license") final String  price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.ownerName = ownerName;
        this.price=price;
        this.license=license;

    }


    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id
                + ", brand=" + brand + ", model=" + model + ", color=" + color + ", ownerName=" + ownerName
                + ", license=" + license + ", price=" + price + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(color, car.color) && Objects.equals(ownerName, car.ownerName) && Objects.equals(price, car.price) && Objects.equals(license, car.license);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, color, ownerName, price, license);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
