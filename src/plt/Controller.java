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

  // These are my lists that the database populates upon program initialization.
  private final List<Product> productList = new ArrayList<>();
  private final ObservableList<Product> productListData =
      FXCollections.observableArrayList(productList);

  private final List<ProductionRecord> productionRecordList = new ArrayList<>();
  private final ObservableList<ProductionRecord> productionRecordListData =
      FXCollections.observableArrayList(productionRecordList);

  // This one is not connected to the database. It exists so that an employee
  //  account can be created upon program start. It is temporary and gets erased
  //   each time the program ends.
  private final List<Employee> employeeList = new ArrayList<>();
  private final ObservableList<Employee> employeeListData =
      FXCollections.observableArrayList(employeeList);

  // Declarations for all of the containers and controls utilized in scene builder.
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

  /**
   * This code runs each time the program is started and mainly functions to fill each container in
   * scene-builder with the database info.
   */
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

    // This call fills the product list.
    addProductsToDataList();

    tableView.setItems(productListData);
    produceListView.setItems(productListData);

    addProductionRecordToDataList();
    for (ProductionRecord pr : productionRecordListData) {
      productionLogTextArea.appendText(pr + "\n");
    }
  }

  /**
   * I think this method is beautiful. It uses a ternary operator return
   * statement to recursively reverse the characters in a string.
   *
   * @param id is the String you want reversed.
   */
  private static String reverseString(String id) {
    return id.isEmpty() ? id : reverseString(id.substring(1)) + id.charAt(0);
  }

  /**
   * This method can be called when inserting info to the database to prevent writing extra code.
   *
   * @param myString is the SQL String query.
   */
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

  /**
   * This method is executed when the login label is pressed
   * from the beginning screen upon program start. It essentially
   * sets the visibility of certain scene-builder objects to true
   * or false.
   */
  public void accessLoginPressed() {
    name.setVisible(false);
    password.setVisible(false);
    createAccount.setVisible(false);
    userLogin.setVisible(true);
    password.setVisible(true);
    login.setVisible(true);
    accountCreated.setVisible(false);
  }

  /**
   * This method is executed when the create account label is pressed
   * from the beginning screen upon program start. It utilizes the same
   * visibility concept as stated above.
   */
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

  /**
   * This method fist gets the user input of name and password from the text
   * and password containers and checks to see if the input is null. If not true,
   * then an employee account is created and saved to the employee list. Finally,
   * it iterates through the employee list, finds the employee with the specific name
   * and gets its generated username and displays it so the user can then login with
   * it.
   */
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

  /**
   * This method checks to see if entered info is null. If not true,
   * the employee list is searched for the entered username and password.
   * If a match is found, te user is logged into the system and set as
   * active.
   */
  public void loginPressed() {
    String personsUsername = userLogin.getText();
    String personsPassword = password.getText();
    if (!personsUsername.isEmpty() && !personsPassword.isEmpty()) {
      for (Employee employee : employeeListData) {
        if (employee.getUsername().equals(personsUsername)
            && employee.getPassword().equals(personsPassword)) {
          // Setting the user active by calling this method.
          setActiveUser(employee);
        }
      }
    } else {
      accountCreated.setText("You left a field blank.");
      accountCreated.setVisible(true);
    }
  }

  /**
   * Once the user has been found, the Employee object is passed into
   * this method so that the employees info can be displayed on the homescreen.
   * The employee can now access the other tabs for production.
   *
   * @param employee is an Employee object that contains the active users
   *                 information.
   */
  private void setActiveUser(Employee employee) {
    identifier1.setText(employee.getName());
    identifier2.setText(employee.getUsername());
    identifier3.setText(employee.getEmail());
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

  /**
   * When the logout button is pressed, this method is called and
   * returns the user to the login screen and sets the production tabs
   * to disabled.
   */
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

  /**
   * This method was supposed to check the employee list for duplicates
   * but I couldn't get it working. Check out that lambda expression though ;-)
   *
   * @param list the employee list that contains employees.
   * @param name the name to match to duplicates within list.
   */
    private boolean containsName(final List<Employee> list, final String name) {
      return list.stream().anyMatch(o -> o.getName().equals(name));
    }

  /**
   * This method gets the input from the name, manufacturer and type fields,
   * saves them to a SQL String query, checks the fields to make sure they're
   * not null, then sends myString into the call to insertToDB and creates a
   * product object in a switch statement that has cases based on the saved
   * ItemType enumeration from the input. This object will be saved into the
   * product list upon program start.
   */
  @FXML
  public void addProductButton() {

    String name = this.productName.getText();
    String manufacturer = this.productManufacturer.getText();
    String type = this.productTypeBox.getValue();

    String myString =
        "INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER) VALUES "
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

  /**
   * This method retrieves all the information from the product database table
   * and saves into a ResultSet. The ResultSet is then iterated through and retrieves
   * all of the product objects saved by the user. It then populates the product list
   * with all of these objects upon program start.
   */
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

  /**
   * This method takes a selected product and number and creates a new
   * production record with them along with the time the production record was created.
   * It then saves the production record to the database.
   */
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
    insertToDB(myString);
    productionLogTextArea.clear();
    productionRecordListData.clear();
    addProductionRecordToDataList();
    for (ProductionRecord prr : productionRecordListData) {
      productionLogTextArea.appendText(prr + "\n");
    }
  }

  /**
   * This method retrieves a ResultSet from the production list database table,
   * iterates through the ResultSet and saves each of the production records
   * into a production record list which is then used to populate the production log
   * upon program start.
   */
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
   * I used this main method to generate the config.properties file and
   * save my database information to it. It also calls the reverseString() method
   * to partially encrypt the file.
   *
   * @param args default parameter.
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
