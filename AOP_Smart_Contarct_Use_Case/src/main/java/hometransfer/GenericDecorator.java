package hometransfer;

import org.hyperledger.fabric.contract.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class GenericDecorator<T> implements InvocationHandler {
    private T target;

    public GenericDecorator(T target) {
        this.target = target;
    }

    public GenericDecorator() {
    }

    public T decorate(T target) {
        this.target = target;
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (shouldApplyForTransaction((Context) args[0])) {
            beforeInvocation(method, args);
        }
        Object result = method.invoke(target, args);
        if (shouldApplyForTransaction((Context) args[0])) {
            afterInvocation(method, args, result);
        }
        return result;
    }

    public abstract void beforeInvocation(Method method, Object[] args);

    public abstract void afterInvocation(Method method, Object[] args, Object result);

    public abstract boolean shouldApplyForTransaction(Context ctx);
}
