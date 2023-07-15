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
    private final String homeOwnerId;
    @Property()
    private final String homeName;
    @Property()
    private final String homeAddress;
    @Property()
    private final String area;
    @Property()
    private final String propertyType;
    @Property()
    private final double homeValue;
    @Property()
    private final int buildYear;

    public Home(@JsonProperty("homeId") final String homeId, @JsonProperty("homeOwnerId") final String homeOwnerId,
                @JsonProperty("homeName") final String homeName, @JsonProperty("homeAddress") final String homeAddress,
                @JsonProperty("area") final String area, @JsonProperty("propertyType") final String propertyType,
                @JsonProperty("homeValue") final double homeValue, @JsonProperty("buildYear") final int buildYear) {
        this.homeId = homeId;
        this.homeOwnerId = homeOwnerId;
        this.homeName = homeName;
        this.homeAddress = homeAddress;
        this.area = area;
        this.propertyType = propertyType;
        this.homeValue = homeValue;
        this.buildYear = buildYear;
    }

    public String getHomeId() {
        return homeId;
    }

    public String getHomeOwnerId() {
        return homeOwnerId;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getArea() {
        return area;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public double getHomeValue() {
        return homeValue;
    }

    public int getBuildYear() {
        return buildYear;
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

        return Objects.deepEquals(new Object[] {getHomeId(), getHomeOwnerId(), getHomeName(), getHomeAddress(),
                        getArea(), getPropertyType(), getHomeValue(), getBuildYear()},
                new Object[] {other.getHomeId(), other.getHomeOwnerId(), other.getHomeName(),
                        other.getHomeAddress(), other.getArea(), other.getPropertyType(),
                        other.getHomeValue(), other.getBuildYear()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHomeId(), getHomeOwnerId(), getHomeName(), getHomeAddress(),
                getArea(), getPropertyType(), getHomeValue(), getBuildYear());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [homeId=" + homeId
                + ", homeOwnerId=" + homeOwnerId + ", homeName=" + homeName + ", homeAddress=" + homeAddress
                + ", area=" + area + ", propertyType=" + propertyType + ", homeValue=" + homeValue
                + ", buildYear=" + buildYear + "]";
    }
}
