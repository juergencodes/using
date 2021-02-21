package x;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4J <code>Appender</code> implementation that forwards logging to Jdk-Logging.
 */
public class JdkLogAppender extends AppenderSkeleton {

  private static final Map LEVEL_MAP = new HashMap();

  static {
    LEVEL_MAP.put(org.apache.log4j.Level.OFF, Level.OFF);
    LEVEL_MAP.put(org.apache.log4j.Level.FATAL, Level.SEVERE);
    LEVEL_MAP.put(org.apache.log4j.Level.ERROR, Level.SEVERE);
    LEVEL_MAP.put(org.apache.log4j.Level.WARN, Level.WARNING);
    LEVEL_MAP.put(org.apache.log4j.Level.INFO, Level.INFO);
    LEVEL_MAP.put(org.apache.log4j.Level.DEBUG, Level.FINE);
    LEVEL_MAP.put(org.apache.log4j.Level.TRACE, Level.FINER);
    LEVEL_MAP.put(org.apache.log4j.Level.ALL, Level.ALL);
  }

  private final Logger logger = Logger.getLogger("");

  protected void append(LoggingEvent event) {
    // Extract and map information
    final Level level = (Level) LEVEL_MAP.get(event.getLevel());
    final String loggerName = event.getLoggerName();
    final String message = (event.getMessage() != null ? event.getMessage().toString() : null);
    final Throwable exception = (event.getThrowableInformation() != null ? event
        .getThrowableInformation().getThrowable() : null);

    // Create log record
    LogRecord record = new LogRecord(level, message);
    record.setLoggerName(loggerName);
    record.setMillis(event.timeStamp);
    record.setThrown(exception);
    logger.log(record);
  }

  public void close() {
    // nothing to do
  }

  public boolean requiresLayout() {
    return false;
  }
}