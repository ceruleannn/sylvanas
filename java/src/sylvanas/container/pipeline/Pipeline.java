package sylvanas.container.pipeline;

/**
 * @Description:
 */
public interface Pipeline {

    void addValue(Valve valve);

    Valve getFirstValue();

    void removeValue(Valve valve);

    Valve[] getValues();
}
