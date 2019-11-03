/*
 * This file is a blueprint for the Screen that the MoviePlayer needs to implement.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */

package sample;

public class Screen implements ScreenSpec {

  private final String resolution;
  private final int refreshrate;
  private final int responsetime;

  Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshrate;
  }

  @Override
  public int getResponseTime() {
    return responsetime;
  }

  public String toString() {
    return "\nScreen:\nResolution: "
        + resolution
        + "\nRefresh rate: "
        + refreshrate
        + "\nResponse time: "
        + responsetime;
  }
}
