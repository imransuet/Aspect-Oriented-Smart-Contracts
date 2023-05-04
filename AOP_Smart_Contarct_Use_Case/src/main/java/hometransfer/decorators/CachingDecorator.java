package hometransfer.decorators;


import hometransfer.GenericDecorator;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CachingDecorator<T> extends GenericDecorator<T> {
    private final Map<String, Object> cache;
    private Object cachedResult;
    private boolean skipInvocation;

    public CachingDecorator(T target) {
        super(target);
        this.cache = new HashMap<>();
    }
    public CachingDecorator() {
        this.cache = new HashMap<>();
    }

    @Override
    public void beforeInvocation(Method method, Object[] args) {
        String cacheKey = generateCacheKey(method, args);
        System.out.println("Inside of beforeInvocation method");
        if (cache.containsKey(cacheKey)) {
            skipInvocation = true;
            cachedResult = cache.get(cacheKey);
        } else {
            skipInvocation = false;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeInvocation(method, args);
        if (skipInvocation) {
            System.out.println("providing result from cache");
            return cachedResult;
        }
        Object result = super.invoke(proxy, method, args);
        afterInvocation(method, args, result);
        return result;
    }

    @Override
    public void afterInvocation(Method method, Object[] args, Object result) {
        if (!skipInvocation) {
            String cacheKey = generateCacheKey(method, args);
            cache.put(cacheKey, result);
        }
    }

    @Override
    public boolean shouldApplyForTransaction(Context ctx) {
        System.out.println("i entered shouldApplyForTransaction of caching decorator ");
        ChaincodeStub stub= ctx.getStub();
        byte[] loggingBytes = stub.getTransient().get("caching");
        if (loggingBytes != null) {
            String loggingValue = new String(loggingBytes, StandardCharsets.UTF_8);
            return loggingValue.equalsIgnoreCase("true");
        }
        System.out.println("i left shouldApplyForTransaction with false from caching decorator");
        return false;
    }

    private String generateCacheKey(Method method, Object[] args) {
        return method.getName() + Arrays.deepHashCode(args);
    }
}
