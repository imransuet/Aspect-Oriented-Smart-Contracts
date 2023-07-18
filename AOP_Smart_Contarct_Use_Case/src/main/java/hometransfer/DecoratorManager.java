package hometransfer;

import hometransfer.contract.AccountContract;
import hometransfer.contract.HomeContract;
import hometransfer.contract.PersonContract;
import hometransfer.decorators.account.CachingAccount;
import hometransfer.decorators.account.CouponAccount;
import hometransfer.decorators.account.LoggingAccount;
import hometransfer.decorators.account.PreConditionAccount;
import hometransfer.decorators.home.CachingHome;
import hometransfer.decorators.home.LoggingHome;
import hometransfer.decorators.home.PreConditionHome;
import hometransfer.decorators.person.CachingPerson;
import hometransfer.decorators.person.LoggingPerson;
import hometransfer.decorators.person.PreConditionPerson;
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
    private final LayerObject cachingLayer;
    private final LayerObject preConditionLayer;
    private final LayerObject couponLayer;

    private List<LayerObject> layers;
    HomeInterface decoratedHomeContract = homeContract;
    PersonInterface decoratedPersonContract = personContract ;
    AccountInterface decoratedAccountContract =accountContract;


    public DecoratorManager() {
        layers = new ArrayList<>();
        loggingLayer = new LayerObject();
        loggingLayer.setHomeDecorator(new LoggingHome());
        loggingLayer.setAccountDecorator(new LoggingAccount());
        loggingLayer.setPersonDecorator(new LoggingPerson());
        layers.add(loggingLayer);
        cachingLayer = new LayerObject();
        cachingLayer.setAccountDecorator(new CachingAccount());
        cachingLayer.setHomeDecorator(new CachingHome());
        cachingLayer.setPersonDecorator(new CachingPerson());
        layers.add(cachingLayer);
        preConditionLayer = new LayerObject();
        preConditionLayer.setAccountDecorator(new PreConditionAccount());
        preConditionLayer.setHomeDecorator(new PreConditionHome());
        preConditionLayer.setPersonDecorator(new PreConditionPerson());
        layers.add(preConditionLayer);
        couponLayer =new LayerObject();
        couponLayer.setAccountDecorator(new CouponAccount());
        layers.add(couponLayer);
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
                System.out.println("Layer Object Logging has been set as "+new String(transientMap.get("logging")));
            }
            if (transientMap.containsKey("caching")) {
                cachingLayer.setActivation(Boolean.parseBoolean(new String(transientMap.get("caching"))));
                System.out.println("Layer Object Caching has been set as "+new String(transientMap.get("caching")));
            }
             if (transientMap.containsKey("preCondition")) {
                 System.out.println("Layer Object preCondition has been set as "+new String(transientMap.get("preCondition")));
                preConditionLayer.setActivation(Boolean.parseBoolean(new String(transientMap.get("preCondition"))));

            }
             if (transientMap.containsKey("coupon")) {
                 System.out.println("Layer Object Coupon has been set as "+new String(transientMap.get("coupon")));
                couponLayer.setActivation(Boolean.parseBoolean(new String(transientMap.get("coupon"))));

            }
            decorateContracts();

        }
    }
    public void decorateContracts() {
        decoratedHomeContract = homeContract;
        decoratedPersonContract = personContract;
        decoratedAccountContract = accountContract;

        for (LayerObject layer : layers) {
            if (layer.getActivation()) {
                if(layer.getHomeDecorator() != null)
                    decoratedHomeContract = layer.getHomeDecorator().decorate(decoratedHomeContract);

                if(layer.getPersonDecorator() != null)
                    decoratedPersonContract = layer.getPersonDecorator().decorate(decoratedPersonContract);

                if(layer.getAccountDecorator() != null)
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