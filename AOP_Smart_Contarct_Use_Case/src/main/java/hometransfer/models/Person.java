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
    private final String personId;
    @Property()
    private final String name;
    @Property()
    private final String personAddress;
    @Property()
    private final String dateOfBirth;
    @Property()
    private final String emailAddress;
    @Property()
    private final String phoneNumber;
    @Property()
    private final String nationality;

    public Person(@JsonProperty("personId") final String personId,
                  @JsonProperty("name") final String name,
                  @JsonProperty("personAddress") final String personAddress,
                  @JsonProperty("dateOfBirth") final String dateOfBirth,
                  @JsonProperty("emailAddress") final String emailAddress,
                  @JsonProperty("phoneNumber") final String phoneNumber,
                  @JsonProperty("nationality") final String nationality) {
        this.personId = personId;
        this.name = name;
        this.personAddress = personAddress;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.nationality = nationality;
    }

    public String getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Person other = (Person) obj;

        return Objects.deepEquals(new Object[] {getPersonId(), getName(), getPersonAddress(),
                        getDateOfBirth(), getEmailAddress(),
                        getPhoneNumber(), getNationality()},
                new Object[] {other.getPersonId(), other.getName(), other.getPersonAddress(),
                        other.getDateOfBirth(), other.getEmailAddress(),
                        other.getPhoneNumber(), other.getNationality()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getName(), getPersonAddress(), getDateOfBirth(),
                getEmailAddress(), getPhoneNumber(), getNationality());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) +
                " [personId=" + personId + ", name=" + name + ", personAddress=" + personAddress +
                ", dateOfBirth=" + dateOfBirth + ", emailAddress=" + emailAddress +
                ", phoneNumber=" + phoneNumber + ", nationality=" + nationality + "]";
    }

}
