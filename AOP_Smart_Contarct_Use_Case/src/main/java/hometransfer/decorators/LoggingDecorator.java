package hometransfer.decorators;

import hometransfer.GenericDecorator;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingDecorator<T> extends GenericDecorator<T> {
    private static final Logger LOGGER = Logger.getLogger(LoggingDecorator.class.getName());

    public LoggingDecorator(T target) {
        super(target);
    }
    public LoggingDecorator() {

    }
/*
    public T decorate(T target) {
        return (T) new LoggingDecorator(target);
    }
    public LoggingDecorator() {
    }
*/
    @Override
    public void beforeInvocation(Method method, Object[] args) {
        LOGGER.log(Level.INFO, "Entering method: {0}", method.getName());
    }

    @Override
    public void afterInvocation(Method method, Object[] args, Object result) {
        LOGGER.log(Level.INFO, "Exiting method: {0}", method.getName());
    }

    @Override
    public boolean shouldApplyForTransaction(Context ctx) {

        System.out.println("i entered shouldApplyForTransaction of Logging ");
        ChaincodeStub stub= ctx.getStub();
        byte[] loggingBytes = stub.getTransient().get("logging");
        if (loggingBytes != null) {
            String loggingValue = new String(loggingBytes, StandardCharsets.UTF_8);
            System.out.println("i left shouldApplyForTransaction with true ");
            return loggingValue.equalsIgnoreCase("true");
        }
        System.out.println("i left shouldApplyForTransaction with false of Logging ");
        return false;
    }
}
