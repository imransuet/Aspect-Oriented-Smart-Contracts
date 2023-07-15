package hometransfer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataType()
public final class Home {
    @Property()
    private final String homeId;
    @Property()
    private final String homeOwner;
    @Property()
    private final String homeName;
    @Property()
    private final String homeAddress;
    @Property()
    private final String area;  // Added new field

    public Home(@JsonProperty("homeId") final String homeId,
                @JsonProperty("homeOwner") final String homeOwner,
                @JsonProperty("homeName") final String homeName,
                @JsonProperty("address") final String homeAddress,
                @JsonProperty("area") final String area) {  // Added new parameter
        this.homeId = homeId;
        this.homeOwner = homeOwner;
        this.homeName = homeName;
        this.homeAddress = homeAddress;
        this.area = area;  // Assigning the new parameter
    }

    public String getHomeId() {
        return homeId;
    }

    public String getHomeOwner() {
        return homeOwner;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getArea() {  // Getter for the new field
        return area;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Home other = (Home) obj;

        return Objects.deepEquals(new String[] {getHomeId(), getHomeOwner(), getHomeName(), getHomeAddress(), getArea()},
                new String[] {other.getHomeId(), other.getHomeOwner(), other.getHomeName(), other.getHomeAddress(), other.getArea()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHomeId(), getHomeOwner(), getHomeName(), getHomeAddress(), getArea());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) +
                " [homeId=" + homeId + ", homeOwner=" + homeOwner +
                ", homeName=" + homeName + ", address=" + homeAddress +
                ", area=" + area + "]";  // Added new field to string
    }

}
