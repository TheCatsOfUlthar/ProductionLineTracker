/*
 * This file allows me to create constants for the type of items I
 * will be storing into the database and product
 * collections. Additionally, I can use these constant to populate
 * my choice-box in Controller.java.
 *
 * @author Robert L. Kissinger
 * @version 1.1 Saturday, September 28th, 2019
 */

package sample;

/** The enum Item type. These constants have two variables, type and code. */
public enum
    ItemType { // By creating an enumeration class, I can access constants from anywhere in my
  // program.
  /** Audio item type. */
  AUDIO("Audio", "AU"), // CONSTANT 1
  /** Visual item type. */
  VISUAL("Visual", "VI"), // CONSTANT 2
  /** The Audiomobile. */
  AUDIOMOBILE("Audio Mobile", "AM"), // CONSTANT 3
  /** The Visualmobile. */
  VISUALMOBILE("Visual Mobile", "VM"); // CONSTANT 4

  private final String type;
  private final String code;

  /** This is my constructor for each enumeration constant within enum ItemType. */
  ItemType(String typeDescription, String codeDescription) {
    type = typeDescription;
    code = codeDescription;
  }

  /**
   * Get type string.
   *
   * @return the string
   */
  public String getType() {
    return type;
  }

  /**
   * Get code string.
   *
   * @return the string
   */
  public String getCode() {
    return code;
  }
}
