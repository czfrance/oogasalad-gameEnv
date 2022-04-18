package oogasalad.builder.view.property;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.model.property.StringListProperty;
import oogasalad.builder.view.ViewResourcesSingleton;
import oogasalad.builder.view.callback.CallbackDispatcher;
import oogasalad.builder.view.callback.GetElementNamesCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameElementListSelector implements PropertySelector {
  private static final String IMAGE_PROPERTY_NAME = "image";

  private final CallbackDispatcher callbackDispatcher;
  private final Property property;
  private final String type;

  private final BorderPane pane;
  private final ComboBox<String> addElement;
  private final ListView<String> elementsList;


  protected GameElementListSelector(Property property, String type, CallbackDispatcher dispatcher) {
    this.property = property;
    this.type = type;
    this.callbackDispatcher = dispatcher;
    pane = new BorderPane();
    addElement = new ComboBox<>();
    elementsList = new ListView<>();
    setup();
  }

  private void setup() {
    elementsList.setEditable(true);
    String startElements = property.valueAsString();
    if(startElements.length() > 2) {
      elementsList.getItems().setAll(Arrays.stream(startElements.substring(1, startElements.length() - 1).split(",")).map(s -> s.substring(1, s.length() - 1)).toList());
    }
    elementsList.setCellFactory(view -> new ListCell<>() {
      @Override
      protected void updateItem(String elementName, boolean b) {
        super.updateItem(elementName, b);
        setText(null);
        if(elementName == null) {
          setGraphic(null);
          return;
        }
        BorderPane pane = new BorderPane();
        pane.setCenter(new Label(elementName));

        Button deleteButton = new Button("X");
        deleteButton.setOnAction(e -> removeElement(this.getIndex()));
        pane.setRight(deleteButton);

        setGraphic(pane);
      }
    });

    addElement.setPromptText(ViewResourcesSingleton.getInstance().getString("game-element-list-add-" + type));
    addElement.getItems().setAll(callbackDispatcher.call(new GetElementNamesCallback(type)).orElse(List.of()));
    addElement.setOnAction(e -> addGameElement(addElement.getSelectionModel().getSelectedItem()));

    pane.setTop(addElement);
    pane.setCenter(elementsList);
  }

  private void removeElement(int index) {
    elementsList.getItems().remove(index);
  }

  private void addGameElement(String element) {
    if(element != null) {
      elementsList.getItems().add(element);
      Platform.runLater(() -> {
        addElement.getSelectionModel().clearSelection();
        addElement.setValue(null);
      });
    }
  }

  @Override
  public Node display() {
    return pane;
  }

  @Override
  public StringListProperty getProperty() {
    String[] nameParts = property.name().split("-");
    return new StringListProperty(nameParts[nameParts.length - 1], new ArrayList<>(elementsList.getItems()), property.form());
  }

  @Override
  public void addListener(ChangeListener updateFields) {

  }
}