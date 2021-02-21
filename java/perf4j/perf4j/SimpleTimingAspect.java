import java.util.List;

import org.apache.log4j.FileAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.perf4j.LoggingStopWatch;
import org.perf4j.aop.AbstractTimingAspect;
import org.perf4j.aop.Profiled;
import org.perf4j.log4j.AsyncCoalescingStatisticsAppender;
import org.perf4j.log4j.GraphingStatisticsAppender;

@Aspect
public class SimpleTimingAspect extends AbstractTimingAspect {

	private AsyncCoalescingStatisticsAppender appender;

	public SimpleTimingAspect() {
		appender = new AsyncCoalescingStatisticsAppender();
		appender.activateOptions();
		appender.setTimeSlice(10000);

		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile("performance.log");
		fileAppender.activateOptions();
		appender.addAppender(fileAppender);
	}

	public void setGraphingAppenders(List<GraphingStatisticsAppender> graphingAppenders) {
		if (graphingAppenders != null) {
			for (GraphingStatisticsAppender graphingAppender : graphingAppenders) {
				graphingAppender.activateOptions();
				appender.addAppender(graphingAppender);
			}
		}
	}

	@Pointcut("execution(* de.myproject.persistence.*..*.*(..))")
	private void daoOperation() {
	}

	@Around("daoOperation()")
	public Object traceDaoOperation(ProceedingJoinPoint pjp) throws Throwable {
		Profiled profiled = new ProfiledClass();
		return doPerfLogging(pjp, profiled);
	}

	// @Around("execution(* de.myproject.actions.*..*.*(..))")
	public Object traceAction(ProceedingJoinPoint pjp) throws Throwable {
		Profiled profiled = new ProfiledClass();
		return doPerfLogging(pjp, profiled);
	}

	@Override
	protected LoggingStopWatch newStopWatch(String loggerName, String levelName) {
		return new SimpleStopWatch(appender);
	}

}