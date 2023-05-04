package hometransfer;

import org.hyperledger.fabric.contract.Context;

import java.util.ArrayList;
import java.util.List;

public class DecoratorManager<T> {
    private T target;
    private List<GenericDecorator<T>> decorators;

    public DecoratorManager(T target) {
        this.target = target;
        this.decorators = new ArrayList<>();
    }

    public void addDecorator(GenericDecorator<T> decorator) {
        decorators.add(decorator);
    }

    public T composeChaincodeBasedOnMetadata(Context ctx) {
        T decoratedTarget = target;

        for (GenericDecorator<T> decorator : decorators) {
            if (decorator.shouldApplyForTransaction(ctx)) {
                decoratedTarget = decorator.decorate(decoratedTarget);
            }
        }
        return decoratedTarget;
    }
}
