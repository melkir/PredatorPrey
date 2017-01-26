package agents;

public class Grass extends AbstractAgent {

    private int countdown; // countdown timer for grass to re-grow
    private boolean alive; // boolean for grass alive / dead

    public Grass(int x, int y) {
        // TODO
    }

    @Override
    public void step() {
        // TODO
    }

    @Override
    public AgentType getType() {
        return AgentType.Grass;
    }

    /**
     * Called when a sheep eat this grass
     */
    public void consume() {
        alive = false;
        updateValueLayer();
    }

    private void updateValueLayer() {
        // TODO
    }

    /**
     * @return if the grass is alive
     */
    public boolean isAlive() {
        return alive;
    }

}
