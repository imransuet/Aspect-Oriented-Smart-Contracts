package hometransfer;

import hometransfer.interfaces.AccountInterface;
import hometransfer.interfaces.HomeInterface;
import hometransfer.interfaces.PersonInterface;

public class ContractRegistry {
    private static ContractRegistry instance;
    private HomeInterface homeContractDecorated;
    private PersonInterface personContractDecorated;

    private AccountInterface accountContractDecorated;

    private ContractRegistry() {
        // Private constructor prevents instantiation from other classes
    }

    public static synchronized ContractRegistry getInstance() {
        if (instance == null) {
            instance = new ContractRegistry();
        }
        return instance;
    }

    public void setHomeContractDecorated(HomeInterface homeContractDecorated) {
        this.homeContractDecorated = homeContractDecorated;
    }

    public HomeInterface getHomeContractDecorated() {
        return this.homeContractDecorated;
    }

    public void setPersonContractDecorated(PersonInterface personContractDecorated) {
        this.personContractDecorated = personContractDecorated;
    }

    public PersonInterface getPersonContract() {
        return this.personContractDecorated;
    }

    public void setAccountContractDecorated(AccountInterface accountContractDecorated) {
        this.accountContractDecorated = accountContractDecorated;
    }

    public AccountInterface getAccountContractDecorated() {
        return this.accountContractDecorated;
    }
}

