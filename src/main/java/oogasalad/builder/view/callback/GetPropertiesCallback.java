package oogasalad.builder.view.callback;

import oogasalad.builder.model.property.Property;

import java.util.Collection;

// Commit: 79905bc6
// This is a good example of a callback, which you can see being using in GameElementTab
// It's basically just telling you "a handler should take a string argument and return a collection of properties"
// What's nice is that a class like this is all that is needed to define a callback
/**
 * A callback for getting the default properties for a given element type
 */
public record GetPropertiesCallback(String type) implements Callback<Collection<Property>> {

}
