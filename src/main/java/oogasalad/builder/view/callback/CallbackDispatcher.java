package oogasalad.builder.view.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Commit: 945dc869 (callbacks were introduced as a way to refactor the rest of the view)
// This code is the meat of the callback system
// A class that wants to handle a callback only has to register it using the class of the callback to handle and the handler itself
// The handler is ensured to be the right type thanks to generics
// When calling a callback, it's as simple as passing in the argument to the handler
// The code is able to simply find the right handler for that argument and call it
// Additionally, it has the proper return value, so there's no need to cast anything
// This code makes it fast and easy to define callbacks, which allow for a type-safe way of defining event handlers with arbitrary return types
/**
 * This class allows for registering and calling callbacks
 */
public class CallbackDispatcher {
    private Map<Class<? extends Callback<?>>, CallbackHandler<?,?>> handlers = new HashMap<>();

    /**
     * Registers a handler for a specific callback
     * @param callback The class of the callback that will be handled
     * @param handler the handler to handle that callback
     * @param <R> the type the handler should return
     * @param <C> the type of the callback
     */
    public <R, C extends Callback<R>> void registerCallbackHandler(Class<C> callback, CallbackHandler<R, C> handler) {
        handlers.put(callback, handler);
    }

    /**
     * Calls the handler with the given callback as an argument
     * @param callback the callback to pass to a handler for it
     * @param <R> the type the handler should return
     * @param <C> the type of the callback
     * @return an optional which contains the result of calling the handler or
     * an empty optional if no handler is defined or the handler returns null
     */
    public <R, C extends Callback<R>> Optional<R> call(C callback) {
        Class<?> clazz = callback.getClass();
        if(handlers.containsKey(clazz)&& handlers.get(clazz) != null) {
            return Optional.ofNullable(((CallbackHandler<R, C>)handlers.get(clazz)).handle(callback));
        }
        return Optional.empty();
    }

}
