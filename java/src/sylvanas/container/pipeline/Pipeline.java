package sylvanas.container.pipeline;

/**
 * @Description:
 */
public interface Pipeline {

    public void addValue(Valve valve);

    public Valve getFirstValue();

    public void removeValue(Valve valve);

    public Valve[] getValues();
}
