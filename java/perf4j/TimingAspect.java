import AspectUtils.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;

@Aspect
public class TimingAspect {

	@Pointcut("execution(@Timed * *(..))")
	@SuppressWarnings("unused")
	private void tracedMethod() {
	}

	@Around("tracedMethod()")
	public Object trace(ProceedingJoinPoint pjp) throws Throwable {
		long x = System.currentTimeMillis();
		try {
			return pjp.proceed();
		} finally {
			long y = System.currentTimeMillis();
			Logger log = getLogger(pjp);
			long time = (y - x);
			log.info("Execution took " + time + " ms: " + buildSignature(pjp));
		}
	}

}