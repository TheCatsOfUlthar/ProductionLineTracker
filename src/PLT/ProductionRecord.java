/*
 * This file allows us to record the production of each product we create.
 * The fields specify our individual products and we can use this class to
 * show how many of each item we made.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */

package PLT;

import java.util.Date;

/** The type Production record. */
class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private final Date dateProduced;

  /** Instantiates a new Production record. */
  ProductionRecord() {
    this.productionNumber = 0;
    this.serialNumber = "0";
    dateProduced = new Date();
  }

  /**
   * Instantiates a new Production record.
   *
   * @param productionNumber the production number
   * @param productID the product id
   * @param serialNumber the serial number
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(getDateProduced().getTime());
  }

  /**
   * Instantiates a new Production record.
   *
   * @param product the product
   * @param itemCount the item count
   */
  ProductionRecord(Product product, int itemCount) {
    setSerialNumber(
        product.getManufacturer().substring(0, 3)
            + product.getType().getCode()
            + String.format("%05d", itemCount));
    dateProduced = new Date();
  }

  @Override
  public String toString() {
    return "Prod. Num: "
        + productionNumber
        + " Product ID: "
        + productID
        + " Serial Num: "
        + serialNumber
        + " Date: "
        + dateProduced;
  }

  /**
   * Gets production number.
   *
   * @return the production number
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Sets production number.
   *
   * @param productionNumber the production number
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Gets product id.
   *
   * @return the product id
   */
  public int getProductID() {
    return productID;
  }

  /**
   * Sets product id.
   *
   * @param productID the product id
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Gets serial number.
   *
   * @return the serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  private void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  private Date getDateProduced() {
    return new Date(dateProduced.getTime());
  }
}
