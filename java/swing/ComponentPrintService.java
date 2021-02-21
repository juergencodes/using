public class ComponentPrintService implements Printable {

  private Component component = null;
  private static double DEFAULTSCALE = 0.734;
  private double scale;
  private BufferedImage[] pages;
  private int currentPage;
  private String datum;

  private ComponentPrintService(Component component) {
    this.component = component;
  }

  public void print(boolean shrink) {
    // Read date
    datum = (new java.util.Date()).toLocaleString();

    // Create new printer job
    PrinterJob printerjob = PrinterJob.getPrinterJob();

    // Create PageFormat
    PageFormat pageFormat = printerjob.defaultPage();
    pageFormat.setOrientation(PageFormat.LANDSCAPE);

    // Show Dialog for adjusting pageFormat (?)
//		pageFormat = printerjob.pageDialog(pageFormat);

    // Let component paint content into object
    BufferedImage image = new BufferedImage(component.getWidth(),
        component.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    component.paint(image.getGraphics());

    // Determine sizes
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    int pageWidth = (int) pageFormat.getImageableWidth();
    int pageHeight = (int) pageFormat.getImageableHeight() - 8;

    // Determine scale factor
    if(shrink) {
      scale = 1 / Math.max((double) imageWidth / pageWidth, (double) imageHeight / pageHeight);
    }
    else {
      scale = DEFAULTSCALE;
    }

    // Split image into several pages
    int imagePartWidth = (int) (pageWidth / scale);
    int imagePartHeight = (int) (pageHeight / scale);

    int numberPagesX = (int) Math.ceil((double) imageWidth / imagePartWidth);
    int numberPagesY = (int) Math.ceil((double) imageHeight / imagePartHeight);
    int numberPages = numberPagesX * numberPagesY;

    pages = new BufferedImage[numberPages];

    int k=0;
    for(int i=0; i<numberPagesX; i++) {
      for(int j=0; j<numberPagesY; j++) {
        int width = Math.min(imagePartWidth, imageWidth - i * imagePartWidth);
        int height = Math.min(imagePartHeight, imageHeight - j * imagePartHeight);
        pages[k++] = image.getSubimage(i * imagePartWidth, j * imagePartHeight, width, height);
      }
    }

    // Show printer setting dialog
    if(printerjob.printDialog()) {
      try {
        // Set object to print
        printerjob.setPrintable(this, pageFormat);

        // Print page-wise
        for(int i=0; i<pages.length; i++) {
          currentPage = i;
          printerjob.print();
        }
      } catch (PrinterException e) {
        e.printStackTrace();
      }
    }
  }

  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    Graphics2D g = (Graphics2D) graphics;

    if(pageIndex > 0) {
      return Printable.NO_SUCH_PAGE;
    }
    else {
      // Translate graphic into printable area
      g.translate((int) pageFormat.getImageableX() + 1, (int) pageFormat.getImageableY() + 1);
      // Scale graphic
      g.scale(scale, scale);
      // Print current page
      g.drawImage(pages[currentPage], 0, 0, null);

      // Append print information
      String user = "Benutzer: ";
      try {
        user += SecurityTicket.getTicket().getUser().getLogin();
      } catch (ValueObjectException e) {}
      int graphicWidth = (int) g.getClipBounds().getWidth();
      int graphicHeight = (int) g.getClipBounds().getHeight();
      g.drawString(user, 0, graphicHeight - 5);
      g.drawString(datum, graphicWidth - 140, graphicHeight - 5);

      // Dispose graphic
      g.dispose();
    }
    return Printable.PAGE_EXISTS;
  }

  public static void printComponent(Component component) {
    (new ComponentPrintService(component)).print(true);
  }

  public static void printComponent(Component component, boolean shrink) {
    (new ComponentPrintService(component)).print(shrink);
  }

}