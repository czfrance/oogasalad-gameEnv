package oogasalad.engine.view.game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a single cell on the board. Can contain a piece, valid move marker, and highlights.
 *
 * @author Jake Heller, Cynthia France, Shaan Gondalia
 */
public class Cell {

  public static int BUFFER = 2;
  public static String VALID_MARKER_PATH = BoardView.IMAGES_FOLDER + "valid_marker.png";
  public static double OPACITY = 0.6;
  private static final Logger LOG = LogManager.getLogger(Cell.class);
  private final Shape myShape;
  private final StackPane myRoot;
  private ImageView myPiece;
  private ImageView myValidMarker;
  private Rectangle highlight;

  private final double myWidth;
  private final double myHeight;
  private final double myX;
  private final double myY;

  /**
   *
   * @param x x index in board
   * @param y y index in board
   * @param width cell width
   * @param height cell height
   */
  public Cell(int x, int y, double width, double height, Optional<String> hexColor) {
    myWidth = width;
    myHeight = height;
    myX = x;
    myY = y;
    myRoot = new StackPane();
    myRoot.getStyleClass().add("cell");
    myShape = new Rectangle(width, height);
    setColor(hexColor);

    myRoot.getChildren().add(myShape);
  }


  public Node getMyRoot() {
    return myRoot;
  }

  /**
   * Adds an image of a piece with the given image path to the cell
   *
   * @param imagePath the path to the image that will be displayed in the cell
   */
  public void addPiece(String imagePath) {
    removePiece();
    myPiece = createPiece(imagePath, myWidth-BUFFER, myHeight-BUFFER);
    myRoot.getChildren().add(myPiece);
  }

  /**
   * Removes a piece from the cell if there is one present
   */
  public void removePiece() {
    if (myPiece != null) {
      myRoot.getChildren().remove(myPiece);
    }
    myPiece = null;
  }

  /**
   * Adds a valid marker to the cell
   */
  public void addValidMarker(){
    myValidMarker = createValidMarker(VALID_MARKER_PATH, (myWidth-BUFFER)/3, (myHeight-BUFFER)/3);
    myRoot.getChildren().add(myValidMarker);
  }

  /**
   * Removes valid marker from cell if there is one present
   */
  public void removeValidMarker(){
    if(myValidMarker!= null) {
      myRoot.getChildren().remove(myValidMarker);
    }
    myValidMarker = null;

  }

  /**
   * Adds a highlight of to the cell whenever it is selected
   */
  public void addSelectedHighlight() {
    highlight = new Rectangle(myWidth-BUFFER, myHeight-BUFFER);
    highlight.setId("cell-highlight");

    myRoot.getChildren().add(highlight);
    System.out.println("selected");
  }

  /**
   * Removes the highlighting from the cell
   */
  public void removeHighlight() {
    myRoot.getChildren().remove(highlight);
    highlight = null;
  }

  /**
   * Returns whether the cell contains a piece
   *
   * @return whether the cell contains a piece
   */
  public boolean containsPiece() {
    return myRoot.getChildren().contains(myPiece);
  }

  /**
   * Creates a Piece View object given a path, width and height
   *
   * @param imagePath - String of path to image being created
   * @param width - Double of desired width of image
   * @param height - Double of desired height of image
   * @return - ImageView created from image path
   */
  private ImageView createPiece(String imagePath, double width, double height) {
    LOG.debug("Piece image path: " + imagePath);
    File f = new File(imagePath);
    try {
      ImageView myImageView = new ImageView(new Image(f.toURI().toURL().toExternalForm()));
      myImageView.setId("piece");
      myImageView.setFitWidth(width);
      myImageView.setFitHeight(height);
      return myImageView;
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Creates a valid marker object given a path, width and height
   *
   * @param imagePath - String of path to image being created
   * @param width - Double of desired width of image
   * @param height - Double of desired height of image
   * @return - ImageView created from image path
   */
  private ImageView createValidMarker(String imagePath, double width, double height) {
    LOG.debug("Marker image path: " + imagePath);
    ImageView myImageView = new ImageView(new Image(imagePath));
    myImageView.setId("valid-marker");
    myImageView.setFitWidth(width);
    myImageView.setFitHeight(height);
    return myImageView;
  }

  // Sets the color of the cell background
  private void setColor(Optional<String> hexColor) {
    if (hexColor.isPresent()) {
      myShape.setFill(Color.web(hexColor.get()));
    }
    else {
      setDefaultColor();
    }
  }

  // Sets the default color of the cell background
  private void setDefaultColor() {
    if ((myX+myY)%2 == 0) {
      myShape.setId("cell-type-A");
    }
    else {
      myShape.setId("cell-type-B");
    }
  }
}
