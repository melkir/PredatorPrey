package agents;

public class Sheep extends AbstractAgent {

    public Sheep() {
        // TODO
    }

    public Sheep(double energy) {
        this.setEnergy(energy);
        this.setDirection(Math.random() * 360);
    }


    @Override
    public void step() {
        move();
    }

    @Override
    public AgentType getType() {
        return AgentType.Sheep;
    }

}
