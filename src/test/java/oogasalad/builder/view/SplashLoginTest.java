package oogasalad.builder.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
<<<<<<< HEAD
import oogasalad.builder.view.ViewResourcesSingleton;
//import oogasalad.builder.view.tab.SplashLogin;
=======
>>>>>>> master
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

<<<<<<< HEAD
//import static oogasalad.builder.view.BuilderView.makeButton;
//import static oogasalad.builder.view.BuilderView.displayWelcome;
=======
>>>>>>> master
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class SplashLoginTest {
    static final String DEFAULT_RESOURCE_PACKAGE = "/view/";
    static final String SPLASH_PACKAGE = "SplashLogin.css";
    static String TAB_LANGUAGE = "English";
    static String TAB_PROPERTIES = "tabResources";
    static final String TAB_FORMAT = "tabFormat.css";
    String[] languageChoice = {"English", "Spanish", "Italian", "PigLatin"};

    Label myWelcome;
    ResourceBundle splashResources;
    ResourceBundle tabProperties;
    ChoiceBox<String> languageBox;
    VBox leftPanel;
    VBox rightPanel;
    VBox rightPanelElements;
    BorderPane buttonHolder;
    Button proceed;
    Stage stage;
    Scene myLoginScene;


<<<<<<< HEAD
//    @BeforeEach
//    public void start (Stage stage) {
//        SplashLogin testSplashLogin = new SplashLogin();
//    }
=======
    @BeforeEach
    public void start (Stage stage) {
        SplashLogin testSplashLogin = new SplashLogin(e -> {});
    }
>>>>>>> master

    @Test
    void testCellClicked() {
        assertDoesNotThrow(() -> {
            System.out.printf("success");
        });
    }

}
