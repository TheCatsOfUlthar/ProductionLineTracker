/*
 *
 *
 *
 */

package sample;

/** The type Product. */
public abstract class Product implements Item {

  /** The constant id. */
  public int id;

  /** The constant type. */
  private String type;

  /** The constant manufacturer. */
  private String manufacturer;

  /** The constant name. */
  private String name;

  public Product(String name, String manufacturer) {
    this.name = name;
    this.manufacturer = manufacturer;
  }

  public int getId() {
    return 0;
  }

  public void setName(String setName_p) {
    name = setName_p;
  }

  public String getName() {
    return name;
  }

  void setType(String setType_p) {
    type = setType_p;
  }

  String getType() {
    return type;
  }

  public void setManufacturer(String setManufacturer_p) {
    manufacturer = setManufacturer_p;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String toString() {
    return "\nName: " + name + "\nManufacturer: " + manufacturer + "\nType: " + ItemType.AUDIO;
  }
}