import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


/**
 * Class for showing an ExceptionDialog
 */
class ExceptionDialog extends JDialog {

  /** field serialVersionUID */
  private static final long serialVersionUID = 5310177379868118286L;

  /** internationalized text on details button */
  public String detailsButtonText;

  /** internationalized text on close button */
  public String closeButtonText;

  /** The text to be displayed */
  private String text;

  /** The exception's details */
  private String details;

  /** Flag for error message */
  private boolean showAsError;

  /** @return showAsError */
  public boolean isShowAsError() {
    return showAsError;
  }

  /** @return text */
  public String getText() {
    return text;
  }

  /** @return details */
  public String getDetails() {
    return details;
  }

  /** Text label */
  JLabel textlabel = new JLabel();

  /**
   *
   * Create a new instance
   *
   * @param title
   *            The Title of the dialog
   * @param text
   *            The main error description
   * @param details
   *            The details of the error (e.g. stack trace)
   * @param showAsError
   *            determines if the shown dialog is an error dialog or an info
   *            dialog
   */
  private ExceptionDialog(String title, String text, String details,
      boolean showAsError) {
    super((JDialog) null, title, true);
    this.text = text;
    this.details = details;
    this.showAsError = showAsError;
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    closeButtonText = "close";
    detailsButtonText = "details";

    showDialog();
  }

  /** show main Exception dialog */
  private void showDialog() {

    Object[] options = { closeButtonText, detailsButtonText };

    int type = isShowAsError() ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;

    final JOptionPane optionPane = new JOptionPane(text, type, JOptionPane.YES_NO_OPTION, null,
        options);
    setContentPane(optionPane);

    optionPane.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible() && (e.getSource() == optionPane)
            && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
          String value = (String) optionPane.getValue();
          optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
          if (value.equals(detailsButtonText)) {
            showDetails(getTitle());
          } else if (value.equals(closeButtonText)) {
            dispose();
          }
        }
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);

  }

  /**
   * open details dialog
   *
   * @param title
   *            Title of the calling error dialog
   */
  private void showDetails(String title) {
    final JDialog detailsDialog = new JDialog();
    detailsDialog.setModal(true);
    detailsDialog.setTitle(detailsButtonText + " - " + title);

    detailsDialog.pack();
    detailsDialog.setSize(new Dimension(600, 500));

    JTextArea textArea = new JTextArea();
    textArea.setText(details);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.getVerticalScrollBar().setBorder(new EmptyBorder(0, 8, 0, 6));
    scrollPane.getHorizontalScrollBar().setBorder(new EmptyBorder(8, -7, 6, -7));
    detailsDialog.add(scrollPane);
    textArea.setEditable(false);

    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    JPanel buttonPane = new JPanel();
    final JButton closeButton = new JButton(closeButtonText);
    buttonPane.add(closeButton, BorderLayout.CENTER);

    detailsDialog.add(buttonPane, BorderLayout.SOUTH);

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
          detailsDialog.dispose();
        }
      }
    });

    detailsDialog.setLocationRelativeTo(null);
    detailsDialog.setVisible(true);

  }

  /**
   * shows an exception dialog
   *
   * @param title
   *            The dialog's title
   * @param message
   *            The error message
   * @param details
   *            The details of the error (e.g. the stack trace)
   * @param showAsError
   *            determines if the shown dialog is an error dialog or an info
   *            dialog
   */
  public static void showExceptionDialog(String title, String message,
      String details, boolean showAsError) {
    new ExceptionDialog(title, message, details, showAsError);
  }

}