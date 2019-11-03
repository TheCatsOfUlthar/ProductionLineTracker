/*
 * This file is an interface which tells the classes that implement it which
 * methods those classes must implement.
 *
 * @author Robert L. Kissinger
 * @version 1.1 Saturday, September 28th, 2019
 */

package sample;

/** The interface Item. */
interface Item {

  /**
   * Gets id.
   *
   * @return the id
   */
  int getId();

  /**
   * Sets name.
   *
   * @param setName the set name p
   */
  void setName(String setName);

  /**
   * Gets name.
   *
   * @return the name
   */
  String getName();

  /**
   * Sets manufacturer.
   *
   * @param setManufacturer the set manufacturer p
   */
  void setManufacturer(String setManufacturer);

  /**
   * Gets manufacturer.
   *
   * @return the manufacturer
   */
  String getManufacturer();

  /**
   * Sets type.
   *
   * @param setType the set type
   */
  void setType(ItemType setType);

  /**
   * Gets type.
   *
   * @return the type
   */
  ItemType getType();
}
