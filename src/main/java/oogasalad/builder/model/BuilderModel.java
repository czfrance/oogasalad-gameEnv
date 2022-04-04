package oogasalad.builder.model;

import java.util.*;
import oogasalad.builder.controller.Property;
import oogasalad.builder.model.element.ElementRecord;
import oogasalad.builder.model.exception.ElementNotFoundException;
import oogasalad.builder.model.exception.NullBoardException;
import oogasalad.builder.model.exception.OccupiedCellException;

/**
 * Describes the external API of the Builder Model.
 *
 * @author Shaan Gondalia
 */
public interface BuilderModel extends JSONSerializable<BuilderModel> {

    /**
     * Creates a new board with the given dimensions.
     *
     * @param width the width of the board (in cells)
     * @param height the height of the board (in cells)
     */
    void makeBoard(int width, int height);

    /**
     * Returns an Element Record that contains information of the element with the given type/name
     *
     * @param type the type of the element to find
     * @param name the name of the element to find
     * @return an element record containing information about the game element
     */
    ElementRecord findElementInfo(String type, String name) throws ElementNotFoundException;

    /**
     * Adds a Game Element to the game, updating an existing element if possible
     *
     * @param type the type of the game element
     * @param name the name of the game element
     * @param properties the properties of the game element
     */
    void addGameElement(String type, String name, Collection<Property> properties);

    /**
     * Attempts to place a piece at the given coordinates
     *
     * @param x the x location to place
     * @param y the y location to place
     * @param name the name of the piece to place
     * @throws OccupiedCellException if the cell at x, y is already occupied by a piece
     * @throws NullBoardException if the board has not been initialized
     */
    void placeBoardPiece(int x, int y, String name)
        throws OccupiedCellException, NullBoardException;

    /**
     * Finds the name of the piece at the given coordinates
     *
     * @param x the x location to query
     * @param y the y location to query
     * @return the name of the piece
     * @throws NullBoardException if the board has not been initialized
     */
    String findBoardPieceAt(int x, int y) throws NullBoardException;

    /**
     * Clears the cell on the board at the given coordinates
     *
     * @param x the x location to clear
     * @param y the y location to clear
     */
    void clearBoardCell(int x, int y) throws NullBoardException;

    /**
     * Converts a Builder Model into a String representing the model's JSON Format
     * @return a String representation of the model's JSON Format
     */
    String toJSON() throws NullBoardException, ElementNotFoundException;

    /**
     * Converts a JSON String into a Builder Model
     *
     * @param json the JSON string
     * @return a model made from the JSON string
     */
    BuilderModel fromJSON(String json);

}