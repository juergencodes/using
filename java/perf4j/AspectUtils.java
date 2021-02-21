import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AspectUtils {
	
	public static Logger getLogger(JoinPoint joinPoint) {
		Object target = joinPoint.getTarget();
		return LoggerFactory.getLogger(target.getClass());
	}
	
	public static String buildSignature(JoinPoint joinPoint) {
		StringBuilder builder = new StringBuilder();
		builder.append(joinPoint.getTarget().getClass().getSimpleName());
		builder.append(".");
		builder.append(joinPoint.getSignature().getName());
		builder.append("(");
		String delimiter = "";
		for (Object arg : joinPoint.getArgs()) {
			builder.append(delimiter);
			builder.append(arg);
			delimiter = ", ";
		}
		builder.append(")");
		return builder.toString();
	}

}