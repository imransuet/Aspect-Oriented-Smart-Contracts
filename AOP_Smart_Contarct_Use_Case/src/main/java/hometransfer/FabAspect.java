package hometransfer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class FabAspect {


    Logger log = LoggerFactory.getLogger(FabAspect.class);
    @Pointcut("execution(* hometransfer.HomeTransfer.*(..))")
    public void myPointcut() {
    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {

        //ProceedingJoinPoint is an extension of the JoinPoint which provides reflective access to the state available at a given join point, like method parameters, return value, or thrown exception. It also provides all static information about the method itself.
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      // mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        String methodName = pjp.getSignature().getName(); //this will give us the method name
        String className = pjp.getTarget().getClass().toString(); // this will give us the class name
        Object[] array = pjp.getArgs(); //this will capture the incoming argument to the method


        log.info("Entering the " + className);
        log.info("Entering the method "  + methodName + "()");
        // log.info("Entering the method "  + methodName + "()" + " with arguments : " +mapper.writeValueAsString(array));
        Object object = pjp.proceed(); // after calling this proceed method, we can capture the response
        log.info(" Leaving the method : " + methodName + "()");
        log.info(" Leaving the method : " + methodName + "()" + " with following Response : " + mapper.writeValueAsString(object));
        return object;
    }
}
