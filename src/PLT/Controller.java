package PLT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
public class Controller {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL
  /** The Tabpane. */
  public TabPane tabpane;

  private final List<Product> list = new ArrayList<>();
  private final ObservableList<Product> data = FXCollections.observableArrayList(list);

  // fx:id's for my Controls under the Product Line tab.
  @FXML private TextField productName;
  @FXML private TextField productManufacturer;
  @FXML private ChoiceBox<String> productTypeBox;
  @FXML private TextArea productionLogTextArea;
  @FXML private ListView<Product> produceListView;
  @FXML private TableView<Product> tableView;
  @FXML private TableColumn<?, ?> column1 = new TableColumn<>("Name");
  @FXML private TableColumn<?, ?> column2 = new TableColumn<>("Manufacturer");
  @FXML private TableColumn<?, ?> column3 = new TableColumn<>("Type");
  @FXML private ComboBox<Integer> chooseQuantityBox;

  /** Add product button. */
  // Needs to store product to database
  @FXML
  public void addProductButton() {

    String name = this.productName.getText();
    String manufacturer = this.productManufacturer.getText();
    String type = this.productTypeBox.getValue();
    data.add(new Widget(name, manufacturer, ItemType.AUDIO));

    String myString =
        "INSERT INTO Product(NAME, TYPE, MANUFACTURER) VALUES  "
            + "('"
            + name
            + "', '"
            + type
            + "', '"
            + manufacturer
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

  /** This method handles events for the Record Production Button. */
  @FXML
  public void recordProductionButton() {
    produceListView.getSelectionModel().getSelectedItem();
    chooseQuantityBox.getSelectionModel().getSelectedItem();

    ProductionRecord pr =
        new ProductionRecord(
            produceListView.getSelectionModel().getSelectedItem(),
            chooseQuantityBox.getSelectionModel().getSelectedIndex());

    productionLogTextArea.appendText(pr.toString() + "\n");
  }

  /** Choice Box and Combo Box Controls. */
  @FXML
  private void initialize() {
    // Choice box control.
    // The .setValue method determines which choice box option is initially displayed.

    for (ItemType items : ItemType.values()) {
      productTypeBox
          .getItems()
          .addAll(items.getType()); // Here I am adding items to the choice box.
      // Combo box control.
    }

    productTypeBox.setValue("Audio");
    chooseQuantityBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    chooseQuantityBox.getSelectionModel().selectFirst();
    chooseQuantityBox.setEditable(
        true); // Allows the user to edit the field and enter their own number.

    column1.setCellValueFactory(new PropertyValueFactory<>("name"));
    column2.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    column3.setCellValueFactory(new PropertyValueFactory<>("type"));
    tableView.setItems(data);
    produceListView.setItems(data);
  }
}
