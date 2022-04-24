package oogasalad.builder.view.tab;


import static oogasalad.builder.view.tab.boardTab.BoardTab.BOARD_TYPE;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oogasalad.builder.view.BuilderView;
import oogasalad.builder.view.callback.CallbackDispatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.view.ViewResourcesSingleton;
import oogasalad.builder.view.callback.CallbackDispatcher;
import oogasalad.builder.view.callback.GetPropertiesCallback;
import java.util.ResourceBundle;


public class FormatTab extends AbstractTab{
    public static final String DEFAULT_RESOURCE_PACKAGE = "/view/";
    public static String FORMAT = "format";
    public static String HELP_RESOURCE_PATH = "/view/";
    public static String ELEMENTS_PACKAGE = "FormatList";
    public static int PRESENTATION = 0;
    public static int FANCY = 0;
    public static int NORMAL = 0;
    private StackPane leftDisplay;
    private VBox rightHelpBox;
    private static final ResourceBundle elementNames = ResourceBundle.getBundle(HELP_RESOURCE_PATH + ELEMENTS_PACKAGE);

    public FormatTab(CallbackDispatcher dispatcher) { super(FORMAT, dispatcher);}

    @Override
    protected Node setupRightSide() {
        rightHelpBox = new VBox();

        rightHelpBox.getChildren().add(makeButton("tabFormat-format", e -> displayBackground("tabFormat.css")));
        rightHelpBox.getChildren().add(makeButton("sunset-format", e -> displayBackground("nightTabFormat.css")));
        rightHelpBox.getChildren().add(makeButton("DukevUNC-format", e -> displayBackground("dukeTabFormat.css")));
        rightHelpBox.getChildren().add(makeButton("normalmode-format", e -> normalMode()));
        rightHelpBox.getChildren().add(makeButton("presentationmode-format", e -> presentationMode()));
        rightHelpBox.getChildren().add(makeButton("fancymode-format", e -> fancyMode()));
        rightHelpBox.getStyleClass().add("rightPane");
        return rightHelpBox;
        //return null;
    }

    @Override
    protected Node setupLeftSide() {
        leftDisplay = new StackPane();
//        //leftDisplay.getStyleClass().add("helpBox");
        return leftDisplay;
        //return null;
    }

    private void displayBackground(String newStyle){
        Stage newStage = new Stage();
        BuilderView newBuild = new BuilderView(newStage);
        newBuild.changeFormat(newStyle);
    }

    public void presentationMode(){
        if(PRESENTATION == 0) { PRESENTATION = 1;}

        else{ PRESENTATION = 0;}
        displayBackground("tabFormat.css");
    }

    public void fancyMode(){
        if(FANCY == 0) { FANCY = 1;}

        else{ FANCY = 0;}
        displayBackground("tabFormat.css");
    }

    public void normalMode(){
        if(NORMAL == 0) { NORMAL = 1;}

        else{ NORMAL = 0;}
        displayBackground("tabFormat.css");
    }

    @Override
    public void loadElements() {

    }
}
