/*
 * This file allows us to record the production of each product we create.
 * The fields specify our individual products and we can use this class to
 * show how many of each item we made.
 *
 * @author Robert L. Kissinger
 * @version 1.2 Saturday, November 2nd, 2019
 */

package sample;

import java.util.Date;

class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private final Date dateProduced;

  ProductionRecord() {
    this.productionNumber = 0;
    this.serialNumber = "0";
    dateProduced = new Date();
  }

  ProductionRecord(int productionNumber, int productID, String serialNumber) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(getDateProduced().getTime());
  }

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

  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

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
