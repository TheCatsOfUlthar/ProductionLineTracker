package plt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This file allows me to create employee objects from the Employee blueprint.
 *
 * This class is used when creating user accounts and logging into the program.
 * It automatically generates a username and email for each object based on the
 * users first and last name.
 *
 * @author Robert L. Kissinger
 * @version 1.3 Tuesday, November 26th, 2019
 */
class Employee {

  private final String name;
  private String username;
  private final String password;
  private String email;

  Employee(String name, String password) {
    this.name = name;
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
    }
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  private boolean checkName(String name) {
    String pattern = "[A-Za-z]\\w+\\s[A-Za-z]\\w+";
    return name.matches(pattern);
  }

  private void setUsername(String name) {
    // rkissinger
    char c = name.toLowerCase().charAt(0);
    String[] stringArray = name.toLowerCase().split(" ");
    this.username = c + stringArray[1];
  }

  private void setEmail(String name) {
    // robert.kissinger@oracleacademy.Test
    String[] stringArray = name.toLowerCase().split(" ");
    this.email = stringArray[0] + "." + stringArray[1] + "@oracleacademy.Test";
  }

  private boolean isValidPassword(String password) {
    // a A !
    Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z@$!%*?&]");
    Matcher m = p.matcher(password);

    return m.find();
  }

  @Override
  public String toString() {
    return "Employee Details"
        + "\nName : "
        + name
        + "\nUsername : "
        + username
        + "\nEmail : "
        + email
        + "\nInitial Password : "
        + password;
  }

  String getName() {
    return this.name;
  }

  String getUsername() {
    return this.username;
  }

  String getPassword() {
    return this.password;
  }

  String getEmail() {
    return this.email;
  }

  public static void main(String[] args) {
    Employee employee = new Employee("Robert Kissinger", "Rlk2391094155&");
    System.out.println(employee.toString());
  }
}
