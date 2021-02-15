package zur.fyayc.util;

/**
 * Converter or adapter of one object type to another object type.
 *
 * @param <I> input type
 * @param <O> output type
 */
public abstract class Adapter<I, O> {

    abstract public O convert(I input);

}
