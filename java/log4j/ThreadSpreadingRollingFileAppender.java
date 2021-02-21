import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Custom {@link Appender} that logs to files: One main file (for all events) and one file for every
 * thread.
 */
public class ThreadSpreadingRollingFileAppender extends AppenderSkeleton {

  /** Prefix for file naming */
  private String file;

  /** maxFileSize to be passed to file appenders */
  private String maxFileSize;

  /** maxBackupIndex to be passed to file appenders */
  private int maxBackupIndex = 0;

  /** threadThreshold Customizable level for main appenders */
  protected Priority mainThreshold;

  /** threadThreshold Customizable level for thread appenders */
  protected Priority threadThreshold;

  /** appender Main appender */
  private Appender appender;

  /** threadAppender Thread appender */
  private ThreadLocal<Appender> threadAppender = new ThreadLocal<Appender>();

  /** allThreadAppenders Required to close appenders for all threads */
  private List<Appender> allThreadAppenders = new LinkedList<Appender>();

  /** @param file */
  public void setFile(String file) {
    this.file = file;
  }

  /** @param maxFileSize */
  public void setMaxFileSize(String maxFileSize) {
    this.maxFileSize = maxFileSize;
  }

  /** @param maxBackupIndex */
  public void setMaxBackupIndex(int maxBackupIndex) {
    this.maxBackupIndex = maxBackupIndex;
  }

  /**
   * Custom level for main logging.
   *
   * @param mainThreshold
   */
  public void setMainThreshold(Priority mainThreshold) {
    this.mainThreshold = mainThreshold;
  }

  /**
   * Custom level for thread based logging.
   *
   * @param threadThreshold
   */
  public void setThreadThreshold(Priority threadThreshold) {
    this.threadThreshold = threadThreshold;
  }

  /**
   * Create a new appender.
   *
   * @param fileNameSuffix Suffix for file name
   * @param errorOnly Set level to ERROR only or to configured level
   * @return {@link Appender}
   */
  private Appender createAppender(String fileNameSuffix, Priority threshold) {
    RollingFileAppender fileAppender = new RollingFileAppender();
    String fileName;
    if (fileNameSuffix == null || fileNameSuffix.equals("")) {
      fileName = file + ".log";
    } else {
      fileName = file + "-"
          + fileNameSuffix.replaceAll(" ", "_").replaceAll("\\p{Punct}", "_") + ".log";
    }
    fileAppender.setFile(fileName);
    fileAppender.setThreshold(threshold);
    fileAppender.setLayout(layout);
    if (maxFileSize != null) {
      fileAppender.setMaxFileSize(maxFileSize);
    }
    if (maxBackupIndex > 0) {
      fileAppender.setMaxBackupIndex(maxBackupIndex);
    }
    fileAppender.activateOptions();
    return fileAppender;
  }

  @Override
  protected void append(LoggingEvent event) {
    // Delegate to main appender
    appender.doAppend(event);

    // Delegate to thread appender
    if (threadAppender.get() == null) {
      String threadName = Thread.currentThread().getName();
      Appender currentThreadAppender = createAppender(threadName, threadThreshold);
      threadAppender.set(currentThreadAppender);
      allThreadAppenders.add(currentThreadAppender);
    }
    threadAppender.get().doAppend(event);
  }

  public void close() {
    // Close main appender
    appender.close();

    // Close thread appender
    for (Appender appender : allThreadAppenders) {
      appender.close();
    }
  }

  public boolean requiresLayout() {
    return true;
  }

  @Override
  public void activateOptions() {
    super.activateOptions();
    if (file == null) {
      throw new IllegalStateException("File must be set before using "
          + getClass().getSimpleName() + ".");
    }
    appender = createAppender(null, mainThreshold);
  }

}