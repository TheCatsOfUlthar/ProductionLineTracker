package PLT;

import java.sql.Timestamp;
import java.util.Date;

/*
 * This file allows us to record the production of each product we create.
 * The fields specify our individual products and we can use this class to
 * show how many of each item we made.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */
class ProductionRecord {

  private int productionNumber;
  private String productID;
  private String serialNumber;
  private final Date dateProduced;

  /** Instantiates a new Production record. */
  ProductionRecord(int id) {
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
  ProductionRecord(int productionNumber, String productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Instantiates a new Production record.
   *
   * @param product the product
   * @param itemCount the item count
   */
  ProductionRecord(Product product, int itemCount) {
    dateProduced = new Date();
  }

  @Override
  public String toString() {
    return "Prod. Num:\t"
        + productionNumber
        + "\tProduct ID:\t"
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
  String getProductID() {
    return productID;
  }

  /**
   * Sets product id.
   *
   * @param product the product id
   */
  String setProductID(Product product) {
    this.productID = product.getName();
    return productID;
  }

  /**
   * Gets serial number.
   *
   * @return the serial number
   */
  String getSerialNumber() {
    return serialNumber;
  }

  String setSerialNumber(Product product, int itemCount) {
    this.serialNumber =
        product.getManufacturer().substring(0, 3)
            + product.getType().getCode()
            + String.format("%05d", itemCount);
    return serialNumber;
  }

  private Date getDateProduced() {
    return new Date();
  }
}
