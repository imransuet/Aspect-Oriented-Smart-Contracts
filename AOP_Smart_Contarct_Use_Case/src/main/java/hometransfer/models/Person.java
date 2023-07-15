package hometransfer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataType()
public final class Person {
    @Property()
    private final String personId;  // Changed to String
    @Property()
    private final String name;
    @Property()
    private final String personAddress;

    public Person(@JsonProperty("personId") final String personId, @JsonProperty("name") final String name,
                  @JsonProperty("personAddress") final String personAddress) {
        this.personId = personId;
        this.name = name;
        this.personAddress = personAddress;
    }

    public String getPersonId() {   // Changed to String
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    // Previous code...

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Person other = (Person) obj;

        return Objects.deepEquals(new Object[] {getPersonId(), getName(), getPersonAddress()},
                new Object[] {other.getPersonId(), other.getName(), other.getPersonAddress()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getName(), getPersonAddress());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [personId=" + personId + ", name=" + name
                + ", personAddress=" + personAddress + "]";
    }

}
