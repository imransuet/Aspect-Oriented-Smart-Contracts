package hometransfer;

import hometransfer.interfaces.AccountInterface;
import hometransfer.interfaces.HomeInterface;
import hometransfer.interfaces.PersonInterface;

class LayerObject {

    private boolean activation;
    private HomeInterface homeDecorator;
    private PersonInterface personDecorator;
    private AccountInterface accountDecorator;

    public LayerObject() {
        this.activation = true; // Default activation status is true
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public boolean getActivation() {
        return this.activation;
    }

    public void setHomeDecorator(HomeInterface homeDecorator) {
        this.homeDecorator = homeDecorator;
    }

    public HomeInterface getHomeDecorator() {
        return this.homeDecorator;
    }

    public void setPersonDecorator(PersonInterface personDecorator) {
        this.personDecorator = personDecorator;
    }

    public PersonInterface getPersonDecorator() {
        return this.personDecorator;
    }

    public void setAccountDecorator(AccountInterface accountDecorator) {
        this.accountDecorator = accountDecorator;
    }

    public AccountInterface getAccountDecorator() {
        return this.accountDecorator;
    }
}
