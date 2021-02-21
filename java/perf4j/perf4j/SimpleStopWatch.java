import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.perf4j.LoggingStopWatch;
import org.perf4j.log4j.AsyncCoalescingStatisticsAppender;

class SimpleStopWatch extends LoggingStopWatch {

	/** serialVersionUID */
	private static final long serialVersionUID = 6112571713572375374L;

	private AsyncCoalescingStatisticsAppender appender;

	public SimpleStopWatch(AsyncCoalescingStatisticsAppender appender) {
		this.appender = appender;
	}

	/** {@inheritDoc} */
	@Override
	protected void log(String stopWatchAsString, Throwable exception) {
		LoggingEvent event = new LoggingEvent(null, Logger.getLogger("x"), null, stopWatchAsString,
				exception);
		appender.doAppend(event);
	}

}