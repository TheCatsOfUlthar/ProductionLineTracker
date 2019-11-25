package PLT;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
  private static final String PROPERTY_FOLDER =
      "/Users/robertkissinger/OneDrive - Florida Gulf Coast University/Projects/ProductionLineTracker/res/config.properties";

  /** The Tabpane. */
  public TabPane tabpane;

  private final List<Product> productList = new ArrayList<>();
  private final ObservableList<Product> productListData =
      FXCollections.observableArrayList(productList);

  private final List<ProductionRecord> productionRecordList = new ArrayList<>();
  private final ObservableList<ProductionRecord> productionRecordListData =
      FXCollections.observableArrayList(productionRecordList);

  private final List<Employee> employeeList = new ArrayList<>();
  private final ObservableList<Employee> employeeListData =
      FXCollections.observableArrayList(employeeList);
  public Button logout;

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
  @FXML private TextField name;
  @FXML private PasswordField password;
  @FXML private Button login;
  @FXML private Button createAccount;
  @FXML private Label accountCreated;
  @FXML private TextField userLogin;

  public void accessLoginPressed() {
    name.setVisible(false);
    password.setVisible(false);
    createAccount.setVisible(false);
    userLogin.setVisible(true);
    password.setVisible(true);
    login.setVisible(true);
  }

  public void accessCreateAccountPressed() {
    userLogin.setVisible(false);
    password.setVisible(false);
    login.setVisible(false);
    name.setVisible(true);
    password.setVisible(true);
    createAccount.setVisible(true);
  }

  public void createAccountPressed() {
    String name = this.name.getText();
    String password = this.password.getText();
    if (!name.isEmpty() && !password.isEmpty()) {
      employeeListData.add(new Employee(name, password));
      accountCreated.setText("Account successfully created.");
      accountCreated.setVisible(true);
    } else {
      accountCreated.setText("Sorry, you left a field blank.");
      accountCreated.setVisible(true);

    }
  }

  public void loginPressed() {
    String name = this.name.getText();
    String password = this.password.getText();
    if (!containsName(employeeList, name)) {

    } else {
      accountCreated.setText("Sorry, that name is already in use.");
      accountCreated.setVisible(true);
    }
  }

  private boolean containsName(final List<Employee> list, final String name) {
    return list.stream().anyMatch(o -> o.getName().equals(name));
  }

  public void logoutPressed() {}

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
    chooseQuantityBox.setEditable(
        true); // Allows the user to edit the field and enter their own number.
    chooseQuantityBox.getSelectionModel().selectFirst();
    column1.setCellValueFactory(new PropertyValueFactory<>("name"));
    column2.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    column3.setCellValueFactory(new PropertyValueFactory<>("type"));

    addProductsToDataList();

    tableView.setItems(productListData);
    produceListView.setItems(productListData);

    addProductionRecordToDataList();
    for (ProductionRecord pr : productionRecordListData) {
      productionLogTextArea.appendText(pr + "\n");
    }
  }

  /** Add product button. */
  // Needs to store product to database
  @FXML
  public void addProductButton() {

    String name = this.productName.getText();
    String manufacturer = this.productManufacturer.getText();
    String type = this.productTypeBox.getValue();

    String myString =
        "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES  "
            + "('"
            + name
            + "', '"
            + type
            + "', '"
            + manufacturer
            + "')";
    // Here I am initializing my DataBase.
    insertToDB(myString);
    switch (type) {
      case ("Audio"):
        productListData.add(new Widget(name, manufacturer, ItemType.AUDIO));
        break;
      case ("Visual"):
        productListData.add(new Widget(name, manufacturer, ItemType.VISUAL));
        break;
      case ("Audio Mobile"):
        productListData.add(new Widget(name, manufacturer, ItemType.AUDIOMOBILE));
        break;
      case ("Visual Mobile"):
        productListData.add(new Widget(name, manufacturer, ItemType.VISUALMOBILE));
        break;
    }
  }

  private void insertToDB(String myString) {
    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream(PROPERTY_FOLDER));
      String user = properties.getProperty("db.username");
      String pass = properties.getProperty("db.password");
      String url = properties.getProperty("db.url");

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(url, user, reverseString(pass)); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(myString);
      stmt.execute();
      conn.close();
      stmt.close();

    } catch (ClassNotFoundException | SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /** This method handles events for the Record Production Button. */
  @FXML
  public void recordProductionButton() {

    Product pr = produceListView.getSelectionModel().getSelectedItem();
    int quantity = chooseQuantityBox.getSelectionModel().getSelectedIndex() + 1;

    ProductionRecord productionRecord = new ProductionRecord(pr, quantity);
    productionRecordListData.add(productionRecord);

    Timestamp ts = new Timestamp(System.currentTimeMillis());

    String myString =
        "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES"
            + "('"
            + productionRecord.setProductID(pr)
            + "', '"
            + productionRecord.setSerialNumber(pr, quantity)
            + "', '"
            + ts
            + "')";
    // Here I am initializing my DataBase.
    insertToDB(myString);
    productionLogTextArea.clear();
    productionRecordListData.clear();
    addProductionRecordToDataList();
    for (ProductionRecord prr : productionRecordListData) {
      productionLogTextArea.appendText(prr + "\n");
    }
  }

  private void addProductsToDataList() {
    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream(PROPERTY_FOLDER));
      String user = properties.getProperty("db.username");
      String pass = properties.getProperty("db.password");
      String url = properties.getProperty("db.url");

      String myString = "SELECT * FROM PRODUCT";
      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(url, user, reverseString(pass));

      PreparedStatement stmt = conn.prepareStatement(myString);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        String name = rs.getString("NAME");
        int type = rs.getInt("TYPE");
        String manufacturer = rs.getString("MANUFACTURER");

        switch (type) {
          case (0):
            productListData.add(new Widget(name, manufacturer, ItemType.AUDIO));
            break;
          case (1):
            productListData.add(new Widget(name, manufacturer, ItemType.VISUAL));
            break;
          case (2):
            productListData.add(new Widget(name, manufacturer, ItemType.AUDIOMOBILE));
            break;
          case (3):
            productListData.add(new Widget(name, manufacturer, ItemType.VISUALMOBILE));
            break;
        }
      }
      conn.close();
      stmt.close();

    } catch (ClassNotFoundException | SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  private void addProductionRecordToDataList() {
    try {
      InputStream input = new FileInputStream(PROPERTY_FOLDER);
      Properties properties = new Properties();
      properties.load(input);
      String user = properties.getProperty("db.username");
      String pass = properties.getProperty("db.password");
      String url = properties.getProperty("db.url");

      String myString = "SELECT * FROM PRODUCTIONRECORD";
      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(url, user, reverseString(pass));
      PreparedStatement stmt = conn.prepareStatement(myString);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int production_Num = rs.getInt("PRODUCTION_NUM");
        String product_id = rs.getString("PRODUCT_ID");
        String serial_Num = rs.getString("SERIAL_NUM");
        Timestamp ts = rs.getTimestamp("DATE_PRODUCED");

        productionRecordListData.add(
            new ProductionRecord(production_Num, product_id, serial_Num, ts));
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static String reverseString(String id) {
    return id.isEmpty() ? id : reverseString(id.substring(1)) + id.charAt(0);
  }

  public static void main(String[] args) {

    try (OutputStream outputStream = new FileOutputStream(PROPERTY_FOLDER)) {

      Properties properties = new Properties();
      properties.setProperty("db.url", "jdbc:h2:./res/H2");
      properties.setProperty("db.username", "rkissinger");
      properties.setProperty("db.password", reverseString("password"));
      properties.store(outputStream, null);
      System.out.println(properties);

    } catch (IOException io) {
      io.printStackTrace();
    }
  }
}
