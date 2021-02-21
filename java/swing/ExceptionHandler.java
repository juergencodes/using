import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;

/**
 * Central exception handler. It handles two kinds of exceptions:<br>
 * 1. Method <code>handle(Throwable e)</code> is called when an exception in
 * the swing event thread happens.<br>
 * 2. Method <code>uncaughtException</code> (Interface
 * <code>UncaughtExceptionHandler</code>) is called whenever an exceptions is
 * not caught. <p/> The planned behavior is the following: If an exception
 * should be handled directly in the specific code it must be caught there.
 * Otherwise an exception that occurs in the swing event thread can be ignored
 * (i.e. not been caught) and will then be caught in this <code>handle</code>
 * method. All other exceptions will be caught in <code>uncaughtException</code>.
 * Thus a call of this method indicates that the exception handling is
 * incorrect.
 *
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

  /**
   * Enum that contains types for errors that can be displayed.
   */
  public enum Error {

    /** Error in server */
    UNEXPECTED_SERVER_ERROR("Server error", "Unexpected server error occurred."),

    /** Error in connection to server */
    CONNECTION_ERROR("Connection error.", "Error during connection to server occurred."),

    /** Unexpected error, i.e. programming error */
    UNEXPECTED_ERROR("Error", "Unexpected error occurred.");

    /** Title */
    private String title;

    /** Message */
    private String message;

    /**
     * Private constructor
     *
     * @param title
     *            the title
     * @param message
     *            the message
     */
    private Error(String title, String message) {
      this.title = title;
      this.message = message;
    }
  }

  /** Logger */
  private static Logger log = Logger.getLogger(ExceptionHandler.class);

  /**
   * This method will be called whenever an uncaught exceptions occurs. Thus a
   * call of this methode indicates that the exception handling is incorrect.
   * Registering this handler will be done
   * <code>Thread.setDefaultUncaughtExceptionHandler</code>.
   *
   * @param t
   *            Thread
   * @param e
   *            Throwable
   */
  @Override
  public void uncaughtException(Thread t, Throwable e) {
    if (e instanceof SOAPFaultException) {
      handleError(Error.UNEXPECTED_SERVER_ERROR, t, e, true, true);
    } else if (e instanceof WebServiceException) {
      handleError(Error.CONNECTION_ERROR, t, e, true, true);
    } else {
      handleError(Error.UNEXPECTED_ERROR, t, e, true, true);
    }

  }

  /**
   * This method will be called by the swing event thread when an error
   * occurs.
   *
   * @param e
   *            Cause
   */
  public void handle(Throwable e) {
    uncaughtException(null, e);
  }

  /**
   * Display an error message of the given type and the given
   * <code>cause</code>
   *
   * @param error
   *            Error type
   * @param cause
   *            Cause
   */
  public static void showError(Error error, Throwable cause) {
    handleError(error, null, cause, false, true);
  }

  /**
   * Convenience method to show an exception in the gui.
   *
   * @param cause
   *            the thrown error
   * @param parent
   *            the parent component, if parent is of type
   *            <code>JDesktopPane</code>, the exception is shown in an
   *            internal dialog. If the parent is of another type or
   *            <code>null</code> a top level container will be shown. This
   *            container will not have the M look and feel.
   */
  public static void showError(Throwable cause, JComponent parent) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  /**
   * Private generic method to log and display all possible errors
   *
   * @param error
   *            Type of error
   * @param thread
   *            Tread that caused the exception (may be null)
   * @param cause
   *            Throwable that caused the error (may be null)
   * @param uncaught
   *            True if exception was uncaught
   * @param showAsError
   *            True if error, false if warning
   */
  private static void handleError(Error error, Thread thread, Throwable cause, boolean uncaught,
      final boolean showAsError) {
    // Log exception
    if (cause != null) {
      // Build message for logfile
      StringBuilder logMessage = new StringBuilder();
      if (uncaught) {
        logMessage.append("Uncaught ");
      } else {
        logMessage.append("Caught ");
      }
      logMessage.append("Exception occured: ");
      if (thread != null) {
        logMessage.append("in Thread '" + thread.getName() + "' ");
      }

      // Write log
      log.error(logMessage.toString(), cause);
    }

    // Build message box text
    final String messageTitle = error.title;
    final String messageText = error.message;
    final StringBuilder detailsText = new StringBuilder();
    if (cause != null) {
      StringWriter stackTraceWriter = new StringWriter();
      cause.printStackTrace(new PrintWriter(stackTraceWriter));
      detailsText.append(stackTraceWriter.toString());
    }

    // show dialog
    if (SwingUtilities.isEventDispatchThread()) {
      ExceptionDialog.showExceptionDialog(messageTitle, messageText, detailsText
          .toString(), showAsError);
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          ExceptionDialog.showExceptionDialog(messageTitle, messageText,
              detailsText.toString(), showAsError);
        }
      });
    }

  }

}