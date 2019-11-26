package plt;

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
  @FXML private Label productLineBlankLabel;

  @FXML private Label identifier1;
  @FXML private Label identifier2;
  @FXML private Label identifier3;
  @FXML private Button identifier4;

  @FXML private Label welcomeLabel;
  @FXML private Label welcomeLabel2;
  @FXML private Label welcomeLabel3;

  @FXML private Tab tab1;
  @FXML private Tab tab2;
  @FXML private Tab tab3;

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

  // I think this method is beautiful.
  private static String reverseString(String id) {
    return id.isEmpty() ? id : reverseString(id.substring(1)) + id.charAt(0);
  }

  private void insertToDB(String myString) {
    try {
      Properties properties = new Properties();
      properties.load(
          new FileInputStream(
              "/Users/robertkissinger/OneDrive - Florida Gulf Coast "
                  + "University/Projects/ProductionLineTracker/res/config.properties"));

      String user = properties.getProperty("db.username");
      String pass = properties.getProperty("db.password");
      String url = properties.getProperty("db.url");

      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(url, user, reverseString(pass)); // Database Url
      PreparedStatement stmt = conn.prepareStatement(myString);
      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /** Access login pressed. */
  public void accessLoginPressed() {
    name.setVisible(false);
    password.setVisible(false);
    createAccount.setVisible(false);
    userLogin.setVisible(true);
    password.setVisible(true);
    login.setVisible(true);
    accountCreated.setVisible(false);
  }

  /** Access create account pressed. */
  public void accessCreateAccountPressed() {
    userLogin.setVisible(false);
    password.setVisible(false);
    login.setVisible(false);
    name.setVisible(true);
    password.setVisible(true);
    createAccount.setVisible(true);
    accountCreated.setText(
        "Please enter your full name and a\npassword "
            + "with at least one capital\nletter, lowercase letter and special\ncharacter.");
    accountCreated.setVisible(true);
  }

  /** Create account pressed. */
  public void createAccountPressed() {
    String personsName = name.getText();
    String personsPassword = password.getText();

    if (!personsName.isEmpty() && !personsPassword.isEmpty()) {
      // if (!containsName(employeeList, personsName)) {
      employeeListData.add(new Employee(personsName, personsPassword));
      for (Employee employee : employeeListData) {
        if (employee.getName().equals(personsName)
            && employee.getPassword().equals(personsPassword)) {
          accountCreated.setText(
              "Account successfully created.\nYour username is: " + employee.getUsername());
        }
      }
      accountCreated.setVisible(true);
      //      } else {
      //        accountCreated.setText("Sorry, that name is already in use.");
      //        accountCreated.setVisible(true);
      //      }
    } else {
      accountCreated.setText("Sorry, you left a field blank.");
      accountCreated.setVisible(true);
    }
  }

  /** Login pressed. */
  public void loginPressed() {
    String personsUsername = userLogin.getText();
    String personsPassword = password.getText();
    if (!personsUsername.isEmpty() && !personsPassword.isEmpty()) {
      for (Employee employee : employeeListData) {
        if (employee.getUsername().equals(personsUsername)
            && employee.getPassword().equals(personsPassword)) {
          setActiveUser(employee);
        }
      }
    } else {
      accountCreated.setText("You left a field blank.");
      accountCreated.setVisible(true);
    }
  }

  private void setActiveUser(Employee employee) {
    identifier1.setText("Name:\t" + employee.getName());
    identifier2.setText("Username:\t" + employee.getUsername());
    identifier3.setText("Email:\t" + employee.getEmail());
    identifier1.setVisible(true);
    identifier2.setVisible(true);
    identifier3.setVisible(true);
    identifier4.setVisible(true);
    welcomeLabel.setVisible(false);
    welcomeLabel2.setVisible(false);
    welcomeLabel3.setVisible(false);
    name.setVisible(false);
    password.setVisible(false);
    createAccount.setVisible(false);
    login.setVisible(false);
    accountCreated.setVisible(false);
    userLogin.setVisible(false);
    tab1.setDisable(false);
    tab2.setDisable(false);
    tab3.setDisable(false);
  }

  /** Logout user. */
  public void logoutUser() {
    identifier1.setVisible(false);
    identifier2.setVisible(false);
    identifier3.setVisible(false);
    identifier4.setVisible(false);
    welcomeLabel.setVisible(true);
    welcomeLabel2.setVisible(true);
    welcomeLabel3.setVisible(true);
    name.clear();
    userLogin.clear();
    password.clear();
    tab1.setDisable(true);
    tab2.setDisable(true);
    tab3.setDisable(true);
  }

  // --Commented out by Inspection START (11/25/19, 8:23 PM):
  //  private boolean containsName(final List<Employee> list, final String name) {
  //    return list.stream().anyMatch(o -> o.getName().equals(name));
  //  }
  // --Commented out by Inspection STOP (11/25/19, 8:23 PM)

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
    if (!name.isEmpty() && !manufacturer.isEmpty() && !type.isEmpty()) {
      productLineBlankLabel.setVisible(false);
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
    } else {
      productLineBlankLabel.setVisible(true);
    }
  }

  private void addProductsToDataList() {
    try {
      Properties properties = new Properties();
      properties.load(
          new FileInputStream(
              "/Users/robertkissinger/OneDrive - Florida Gulf Coast "
                  + "University/Projects/ProductionLineTracker/res/config.properties"));
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

  private void addProductionRecordToDataList() {
    try {
      InputStream input =
          new FileInputStream(
              "/Users/robertkissinger/OneDrive - Florida Gulf Coast "
                  + "University/Projects/ProductionLineTracker/res/config.properties");
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
        int productionNum = rs.getInt("PRODUCTION_NUM");
        String productId = rs.getString("PRODUCT_ID");
        String serialNum = rs.getString("SERIAL_NUM");
        Timestamp ts = rs.getTimestamp("DATE_PRODUCED");

        productionRecordListData.add(new ProductionRecord(productionNum, productId, serialNum, ts));
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    try (OutputStream outputStream =
        new FileOutputStream(
            "/Users/robertkissinger/OneDrive - Florida Gulf Coast "
                + "University/Projects/ProductionLineTracker/res/config.properties")) {
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
