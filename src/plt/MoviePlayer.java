package plt;

/*
 * This file is the blueprint to create Movie Player Products.
 * Movie Player objects inherit from the abstract class Product.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
public class MoviePlayer extends Product implements MultimediaControl {

  private final Screen screen;
  private final MonitorType monitorType;

  /**
   * Instantiates a new Movie player.
   *
   * @param name the name
   * @param manufacturer the manufacturer
   * @param type the type
   * @param screen the screen
   * @param monitorType the monitor type
   */
  /* Same thing for this itemtype in this constructor*/
  MoviePlayer(
      String name, String manufacturer, ItemType type, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, type);
    setType(ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  public void play() {
    System.out.println("Playing movie");
  }

  public void stop() {
    System.out.println("Stopping movie");
  }

  public void next() {
    System.out.println("Next movie");
  }

  public void previous() {
    System.out.println("Previous movie");
  }

  public String toString() {
    return super.toString() + screen.toString() + "\nMonitor Type: " + monitorType.toString();
  }
}
