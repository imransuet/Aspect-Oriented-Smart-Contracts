package hometransfer;

import hometransfer.contract.AccountContract;
import hometransfer.contract.HomeContract;
import hometransfer.contract.PersonContract;
import hometransfer.decorators.account.LoggingAccount;
import hometransfer.decorators.home.LoggingHome;
import hometransfer.decorators.person.LoggingPerson;
import hometransfer.interfaces.AccountInterface;
import hometransfer.interfaces.HomeInterface;
import hometransfer.interfaces.PersonInterface;
import org.hyperledger.fabric.contract.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecoratorManager {
    HomeContract homeContract =new HomeContract();
    PersonContract personContract  =new PersonContract();
    AccountContract accountContract= new AccountContract();
    private static DecoratorManager instance = null;
    private final LayerObject loggingLayer;

    private List<LayerObject> layers;
    HomeInterface decoratedHomeContract = homeContract;
    PersonInterface decoratedPersonContract = personContract ;
    AccountInterface decoratedAccountContract =accountContract;


    public DecoratorManager() {
        this.layers = new ArrayList<>();
        loggingLayer = new LayerObject();
        loggingLayer.setHomeDecorator(new LoggingHome());
        loggingLayer.setAccountDecorator(new LoggingAccount());
        loggingLayer.setPersonDecorator(new LoggingPerson());
        layers.add(loggingLayer);
    }
    public static synchronized DecoratorManager getInstance() {
        if (instance == null) {
            instance = new DecoratorManager();
        }
        return instance;
    }
    public void configureLayer(Context ctx) {
        Map<String, byte[]> transientMap = ctx.getStub().getTransient();

        if (transientMap.containsKey("decorate") && new String(transientMap.get("decorate")).equals("true")) {

            // Check for logging flag and set activation accordingly
            if (transientMap.containsKey("logging")) {
                loggingLayer.setActivation(Boolean.parseBoolean(new String(transientMap.get("logging"))));
            }
            decorateContracts();

        }
    }
    public void decorateContracts() {
       decoratedHomeContract=homeContract ;
       decoratedPersonContract=personContract ;
      decoratedAccountContract=accountContract;
        for (LayerObject layer : layers) {
            if (layer.getActivation()) {
                decoratedHomeContract = layer.getHomeDecorator().decorate(decoratedHomeContract);
               decoratedPersonContract = layer.getPersonDecorator().decorate(decoratedPersonContract);
               decoratedAccountContract = layer.getAccountDecorator().decorate(decoratedAccountContract);

            }
        }
    }


    public HomeInterface getHomeContract(final Context ctx)
    {configureLayer(ctx);
        return decoratedHomeContract;
    }
    public PersonInterface getPersonContract(final Context ctx)
    {
        configureLayer(ctx);
        return decoratedPersonContract;
    }
    public AccountInterface getAccountContract(final Context ctx)
    {
        configureLayer(ctx);
        return decoratedAccountContract;
    }

}