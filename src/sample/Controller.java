/*
 * This file allows me to create action events from my Scene Builder controls.
 *
 * Controller.java assigns fx:id's to my Scene Builder controls and allows me to
 * create action events for the same controls through individual methods. It also
 * allows me to structure the controls, such as adding strings to choice boxes and
 * integers to combo boxes. Additionally, this file can insert user entered data from
 * text fields and choice boxes into a database.
 *
 * @author Robert L. Kissinger
 * @version 1.0 Saturday, September 21st, 2019
 */

package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class controls the action events of my Scene Builder Controls and contains my database
 * initialization.
 *
 * @author Robert L. Kissinger
 */
public class Controller {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  // fx:id's for my Controls under the Product Line tab.
  @FXML private TextField productName;
  @FXML private TextField productManufacturer;
  @FXML private ChoiceBox<String> productTypeBox = new ChoiceBox<>();

  // fx:id for my Control under the Produce tab.
  @FXML private ComboBox<Integer> chooseQuantityBox = new ComboBox<>();

  /**
   * When the Add Product Button is pressed, this method gets the information supplied in the text
   * fields and combo box and stores it into three variables. The method then checks to make sure
   * the variables aren't empty and then inserts the data into the database.
   */
  @FXML
  public void addProductButton() {

    /*
    This is how I receive the information that the user enters into both text fields and
    the choice box under Product Line.
     */
    String productName = this.productName.getText();
    String productManufacturer = this.productManufacturer.getText();
    String productType = this.productTypeBox.getValue();

    // Here I am making sure that the user information entered wasn't empty.
    if (productName.isEmpty() || productManufacturer.isEmpty()) {
      System.out.println("You entered nothing. Try again.");
      // If the input was correct, the data is concatenated into a SQL String Command.
    } else {
      String myString =
          "INSERT INTO Product(NAME, TYPE, MANUFACTURER) VALUES  "
              + "('"
              + productName
              + "', '"
              + productType
              + "', '"
              + productManufacturer
              + "')";
      // Here I am initializing my DataBase.
      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url
        // This prepared statement executes my SQL String Command.
        PreparedStatement stmt = conn.prepareStatement(myString);
        stmt.execute();
        conn.close();
        stmt.close();

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /** This method handles events for the Record Production Button. */
  @FXML
  public void recordProductionButton() {
    System.out.println("Record Production Button Pressed...");
  }

  /** Choice Box and Combo Box Controls. */
  @FXML
  private void initialize() {
    // Choice box control.
    productTypeBox.setValue(
        "Audio"); // The .setValue method determines which choice box option is initially displayed.
    productTypeBox.getItems().addAll("Audio", "Video"); // Here I am adding items to the choice box.
    // Combo box control.
    chooseQuantityBox
        .getItems()
        .addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // The numbers in my combo box.
    chooseQuantityBox.getSelectionModel().selectFirst();
    chooseQuantityBox.setEditable(
        true); // Allows the user to edit the field and enter their own number.
  }
}
