package PLT;

/*
 * This file is an abstract class that holds the base information for how each
 * kind of product sub-classed from it is characterized. We cannot instantiate
 * directly from this class.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
public abstract class Product implements Item {

  /** The constant id. */
  public int id;

  /** The constant type. */
  private ItemType type;

  /** The constant manufacturer. */
  private String manufacturer;

  /** The constant name. */
  private String name;

  /**
   * Instantiates a new Product.
   *
   * @param name the name
   * @param manufacturer the manufacturer
   * @param type the type
   */
  Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public int getId() {
    return 0;
  }

  public void setName(String setName) {
    name = setName;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String setManufacturer) {
    manufacturer = setManufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setType(ItemType setType) {
    type = setType;
  }

  public ItemType getType() {
    return type;
  }

  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + getType();
  }
}
