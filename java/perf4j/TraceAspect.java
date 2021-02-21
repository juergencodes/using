import static AspectUtils.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;

@Aspect
public class TraceAspect {

	@Pointcut("execution(@Traced * *(..))")
	@SuppressWarnings("unused")
	private void tracedMethod() {
	}

	@Before("tracedMethod()")
	public void traceBegin(JoinPoint joinPoint) {
		Logger logger = getLogger(joinPoint);
		logger.warn("-> entering method " + buildSignature(joinPoint));
	}

	@AfterReturning(value = "tracedMethod()", returning = "result")
	public void traceEnd(JoinPoint joinPoint, Object result) {
		Logger logger = getLogger(joinPoint);
		logger.warn("-> leaving method " + buildSignature(joinPoint) + " with result '" + result
				+ "'");
	}

	@AfterThrowing(value = "tracedMethod()", throwing = "cause")
	public void traceException(JoinPoint joinPoint, Throwable cause) {
		Logger logger = getLogger(joinPoint);
		logger.warn("-> leaving method " + buildSignature(joinPoint) + " with exception '"
				+ cause.getClass().getSimpleName() + "'");

	}

}