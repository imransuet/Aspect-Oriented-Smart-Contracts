package hometransfer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataType()
public final class Account {
    @Property()
    private final String accountId;
    @Property()
    private final String personId;  // Changed from Person to String and renamed from person to personId
    @Property()
    private final double balance;

    public Account(@JsonProperty("accountId") final String accountId, @JsonProperty("personId") final String personId, @JsonProperty("balance") final double balance) {
        this.accountId = accountId;
        this.personId = personId;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPersonId() {   // Method renamed from getPerson() to getPersonId()
        return personId;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Account other = (Account) obj;

        return Objects.deepEquals(new Object[] { getAccountId(), getPersonId(), getBalance() },   // Method renamed from getPerson() to getPersonId()
                new Object[] { other.getAccountId(), other.getPersonId(), other.getBalance() });   // Method renamed from getPerson() to getPersonId()
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getPersonId(), getBalance());   // Method renamed from getPerson() to getPersonId()
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [accountId=" + accountId + ", personId=" + personId + ", balance=" + balance + "]";  // Changed person.toString() to personId
    }
}
