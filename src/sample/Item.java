/*
 * This file is an interface which tells the classes that implement it which methods those classes must implement.
 *
 * @author Robert L. Kissinger
 * @version 1.1 Saturday, September 28th, 2019
 */

package sample;

/** The interface Item. */
public interface Item {

  /**
   * Gets id.
   *
   * @return the id
   */
  int getId();

  /**
   * Sets name.
   *
   * @param setName_p the set name p
   */
  void setName(String setName_p);

  /**
   * Gets name.
   *
   * @return the name
   */
  String getName();

  /**
   * Sets manufacturer.
   *
   * @param setManufacturer_p the set manufacturer p
   */
  void setManufacturer(String setManufacturer_p);

  /**
   * Gets manufacturer.
   *
   * @return the manufacturer
   */
  String getManufacturer();
}
