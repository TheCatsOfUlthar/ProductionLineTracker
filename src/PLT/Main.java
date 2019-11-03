/*
 * This file allows me to override my main method and instead load the PLTGUI.fxml
 * in order to use scene builder and javaFX.
 *
 * @author Robert L. Kissinger
 * @version 1.0 Saturday, September 21st, 2019
 */

package PLT;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** The type Main. */
public class Main extends Application {

  /**
   * This method overrides main and loads my PLTGUI.fxml file.
   *
   * @param primaryStage Created by the platform and passed to the start method.
   * @throws Exception Handed to Java runtime to prevent exceptional events.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("PLTGUI.fxml"));
    primaryStage.setTitle("Production Line Tracker");
    // primaryStage.setScene(new Scene(root, 700, 400));
    Scene scene = new Scene(root, 600, 400);
    scene.getStylesheets().add(getClass().getResource("ProjectGUI.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
