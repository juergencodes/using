import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GlassPane extends JPanel {

  public GlassPane() {
    setVisible(false);
    super.setOpaque(false);
    enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        return false;
      }
    });
  }

  protected void processMouseEvent(MouseEvent e) {
    if (e.getID() == e.MOUSE_CLICKED) {
      Toolkit.getDefaultToolkit().beep();
      e.consume();
    }
  }

  protected void processKeyEvent(KeyEvent e) {
    Toolkit.getDefaultToolkit().beep();
    e.consume();
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible) {
      requestFocusInWindow();
    }
  }

  public void paint(Graphics g) {
    g.setColor(new Color(0x10, 0x10, 0x10, 0x88));
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}