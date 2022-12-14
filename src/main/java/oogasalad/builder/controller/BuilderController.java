package oogasalad.builder.controller;

import oogasalad.builder.BuilderMain;
import oogasalad.builder.model.BuilderModel;
import oogasalad.builder.model.GameConfiguration;
import oogasalad.builder.model.element.ElementRecord;
import oogasalad.builder.model.element.FileMapper;
import oogasalad.builder.model.exception.ElementNotFoundException;
import oogasalad.builder.model.exception.InvalidTypeException;
import oogasalad.builder.model.exception.MissingRequiredPropertyException;
import oogasalad.builder.model.exception.NullBoardException;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.view.BuilderView;
import oogasalad.builder.view.callback.ClearCellBackgroundCallback;
import oogasalad.builder.view.callback.ClearCellCallback;
import oogasalad.builder.view.callback.ColorCellBackgroundCallback;
import oogasalad.builder.view.callback.FindCellBackgroundCallback;
import oogasalad.builder.view.callback.FindPieceAtCallback;
import oogasalad.builder.view.callback.GetElementNamesCallback;
import oogasalad.builder.view.callback.GetElementPropertiesCallback;
import oogasalad.builder.view.callback.GetElementPropertyByKeyCallback;
import oogasalad.builder.view.callback.GetHeightCallback;
import oogasalad.builder.view.callback.GetPropertiesCallback;
import oogasalad.builder.view.callback.GetWidthCallback;
import oogasalad.builder.view.callback.LoadCallback;
import oogasalad.builder.view.callback.MakeBoardCallback;
import oogasalad.builder.view.callback.PlacePieceCallback;
import oogasalad.builder.view.callback.SaveCallback;
import oogasalad.builder.view.callback.UpdateGameElementCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Controller for the Builder. Interfaces between the Builder View and Builder Model.
 *
 * @author Shaan Gondalia
 */
public class BuilderController {

    private static final String JSON_FILENAME = "/config.json";
    private final BuilderModel gameConfig;
    private final BuilderView builderView;
    private final Logger LOG;

    /**
     * Creates a BuilderController Object that interfaces between the view and model.
     *
     */
    public BuilderController(BuilderView view) {
        gameConfig = new GameConfiguration();
        builderView = view;
        LOG = LogManager.getLogger(BuilderController.class);

        registerHandlers();
    }

    // Registers all callback handling methods so that the view can communicate with the controller
    private void registerHandlers() {
        builderView.registerCallbackHandler(GetPropertiesCallback.class, this::getRequiredProperties);
        builderView.registerCallbackHandler(GetElementPropertiesCallback.class, this::getElementProperties);
        builderView.registerCallbackHandler(GetElementNamesCallback.class, this::getElementNames);
        builderView.registerCallbackHandler(SaveCallback.class, this::save);
        builderView.registerCallbackHandler(UpdateGameElementCallback.class, this::update);
        builderView.registerCallbackHandler(ClearCellCallback.class, this::clearCell);
        builderView.registerCallbackHandler(PlacePieceCallback.class, this::placePiece);
        builderView.registerCallbackHandler(GetElementPropertyByKeyCallback.class, this::getElementPropertyByKey);
        builderView.registerCallbackHandler(MakeBoardCallback.class, this::makeBoard);
        builderView.registerCallbackHandler(LoadCallback.class, this::load);
        builderView.registerCallbackHandler(ClearCellBackgroundCallback.class, this::clearCellBackground);
        builderView.registerCallbackHandler(ColorCellBackgroundCallback.class, this::colorCellBackground);
        builderView.registerCallbackHandler(GetWidthCallback.class, this::getWidth);
        builderView.registerCallbackHandler(GetHeightCallback.class, this::getHeight);
        builderView.registerCallbackHandler(FindPieceAtCallback.class, this::findPieceAt);
        builderView.registerCallbackHandler(FindCellBackgroundCallback.class, this::findCellBackground);
    }

    /**
     * Creates a new board with the given dimensions.
     *
     * @param callback callback containing the width and height of the board (in cells)
     */
    public Void makeBoard(MakeBoardCallback callback) {
        gameConfig.makeBoard(callback.width(), callback.height());
        return null;
    }

    /**
     * Attempts to place a piece at the given coordinates
     *
     * @param callback callback object representing what piece and where to place it
     * @throws NullBoardException    if the board has not been initialized
     */
    Void placePiece(PlacePieceCallback callback)
        throws NullBoardException, ElementNotFoundException {
        gameConfig.placeBoardPiece(callback.x(), callback.y(), callback.piece());
        return null;
    }

    /**
     * Finds the name of the piece at the given coordinates
     *
     * @param x the x location to query
     * @param y the y location to query
     * @return the name of the piece
     * @throws NullBoardException if the board has not been initialized
     */
    String findPieceAt(FindPieceAtCallback callback) throws NullBoardException, ElementNotFoundException {
        return gameConfig.findBoardPieceAt(callback.x(), callback.y());
    }

    /**
     * Clears the cell on the board at the given coordinates
     *
     * @param callback callback object containing the x and y location to clear
     */
    Void clearCell(ClearCellCallback callback) throws NullBoardException {
        gameConfig.clearBoardCell(callback.x(), callback.y());
        return null;
    }

    /**
     * Sets the background color of the cell at the given coordinates
     *
     * @param callback callback object containing the x and y location and hexadecimal color
     */
    Void colorCellBackground(ColorCellBackgroundCallback callback) {
        gameConfig.colorCellBackground(callback.x(), callback.y(), callback.color());
        return null;
    }

    /**
     * Clears the background color of the cell at the given coordinates
     *
     * @param callback callback object containing the x and y location to clear
     */
    Void clearCellBackground(ClearCellBackgroundCallback callback) {
        gameConfig.clearCellBackground(callback.x(), callback.y());
        return null;
    }

    /**
     * Finds the background color of a piece at the given coordinates
     *
     * @param callback a callback object containing the x and y location to query
     * @return the hexadecimal value of the background color of the cell at the given coordinates
     */
    String findCellBackground(FindCellBackgroundCallback callback) throws NullBoardException, ElementNotFoundException {
        return gameConfig.findCellBackground(callback.x(), callback.y());
    }

    /**
     * Gets the properties of an element specified by its type and name
     *
     * @param callback a callback object containing the type and name of the element to get properties for
     * @return the properties of an element
     */
    Collection<Property> getElementProperties(GetElementPropertiesCallback callback)
        throws ElementNotFoundException {
        ElementRecord elementRecord = gameConfig.findElementInfo(callback.type(), callback.name());
        return elementRecord.properties();
    }

    /**
     * Gets the value of one property of an element specified by its type, name, and property key
     *
     * @param callback callback object containing the type, name, and property key
     * @return the value of the property
     */
    String getElementPropertyByKey(GetElementPropertyByKeyCallback callback)
        throws ElementNotFoundException {
        ElementRecord elementRecord = gameConfig.findElementInfo(callback.type(), callback.name());
        for (Property prop : elementRecord.properties()) {
            if (prop.name().equals(callback.key())) {
                return prop.valueAsString();
            }
        }
        throw new ElementNotFoundException();
    }

    /**
     * Returns the width of the board
     *
     * @return the width of the board
     */
    Integer getWidth(GetWidthCallback callback) {
        return gameConfig.getWidth();
    }

    /**
     * Returns the height of the board
     *
     * @return the height of the board
     */
    Integer getHeight(GetHeightCallback callback){
        return gameConfig.getHeight();
    }

    /**
     * Provides a list of element names that are of the given type
     *
     * @param callback callback object containing the type of the elements to name
     * @return a collection of names that are of a certain type (e.g. piece)
     */
    public Collection<String> getElementNames(GetElementNamesCallback callback) throws ElementNotFoundException {
        return gameConfig.getElementNames(callback.type());
    }

    /**
     * Updates the game configuration with an element record, either adding or modifying a game
     * element based on the parameters
     *
     *
     * @param callback a callback object containing the type, name, and properties of the element to update
     */
    Void update(UpdateGameElementCallback callback)
        throws InvalidTypeException, MissingRequiredPropertyException {
        LOG.info("Updating {} with name {}", callback.type(), callback.name());
        gameConfig.addGameElement(callback.type(), callback.name(), callback.properties());
        return null;
    }

    /**
     * Returns the required properties of a game element
     *
     * @param callback a callback object cont
     * @return the required properties of a game element
     */
    Collection<Property> getRequiredProperties(GetPropertiesCallback callback) throws InvalidTypeException {
        return gameConfig.getRequiredProperties(callback.type());
    }

    /**
     * Saves the existing Game Configuration to a directory, storing the JSON configuration as
     * well as the resources used to create the game (images, etc.)
     *
     * @param callback callback object containing the directory that the game configuration will be located in.
     */
    Void save(SaveCallback callback) throws NullBoardException {
        LOG.info("Attempting to save configuration to folder {}", callback.file().getAbsolutePath());
        File configFile = new File(callback.file().toString() + JSON_FILENAME);
        try {
            FileWriter writer = new FileWriter(configFile);
            writer.write(gameConfig.toJSON());
            writer.close();
            gameConfig.copyFiles(callback.file());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Successfully saved {}", gameConfig.getElementNames(GameConfiguration.METADATA).stream().findFirst().orElse("Untitled"));
        return null;
    }

    /**
     * Loads a Game Configuration from a JSON File
     *
     * @param callback a LoadCallback that contains a directory to load a game from
     */
    Void load(LoadCallback callback) {
        LOG.info("Attempting to load configuration from folder {}", callback.directory().getAbsolutePath());
        File configFile = new File(callback.directory().toString() + JSON_FILENAME);
        try {
            InputStream is = new DataInputStream(new FileInputStream(configFile));
            JSONTokener tokener = new JSONTokener(is);
            JSONObject object = new JSONObject(tokener);
            gameConfig.fromJSON(object.toString(), callback.directory().toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Successfully loaded {}", gameConfig.getElementNames(GameConfiguration.METADATA).stream().findFirst().orElse("Untitled"));
        return null;
    }

    public void showError(Throwable t) {
        builderView.showError(t);
    }

}