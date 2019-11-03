package PLT;

/*
 * This file is an interface that tells our Screen
 * class what methods it needs to implement.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
interface ScreenSpec {

  /**
   * Gets resolution.
   *
   * @return the resolution
   */
  String getResolution();

  /**
   * Gets refresh rate.
   *
   * @return the refresh rate
   */
  int getRefreshRate();

  /**
   * Gets response time.
   *
   * @return the response time
   */
  int getResponseTime();
}
