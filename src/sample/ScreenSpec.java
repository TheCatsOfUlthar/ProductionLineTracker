/*
 * This file is an interface that tells our Screen
 * class what methods it needs to implement.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */

package sample;

interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
