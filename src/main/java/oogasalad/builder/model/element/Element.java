package oogasalad.builder.model.element;


import oogasalad.builder.model.JSONSerializable;

/**
 * API for a generic game element.
 *
 * @author Shaan Gondalia
 */
public interface Element extends JSONSerializable {

  /**
   * Returns whether the parameter matches the name of the game element
   *
   * @param desiredName the name to match to
   * @return whether the parameter matches the name of the game element
   */
  boolean checkName(String desiredName);

  /**
   * Returns an immutable record containing the GameElement's data
   *
   * @return an immutable record containing the GameElement's data
   */
  ElementRecord toRecord();

  /**
   * Converts an Object into a String representing the object's JSON Format
   *
   * @return a String representation of the objects JSON Format
   */
  String toJSON();

}