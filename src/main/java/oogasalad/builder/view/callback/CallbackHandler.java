package oogasalad.builder.view.callback;

// Commit: 945dc869 (callbacks were introduced as a way to refactor the rest of the view)
// This code is simplistic
// It provides a way to handle callbacks that allows for any return type
// Through the use of generics, both the return type and arguments can be known
/**
 * A class that accepts callbacks and handles them
 * @param <R> the value the handler should return
 * @param <C> the type of the callback this handler accepts
 */
public interface CallbackHandler<R, C extends Callback<R>> {

    /**
     * Handles a callback
     * @param callback the callback to handle
     * @return the result of handling this callback
     */
    R handle(C callback);

}
