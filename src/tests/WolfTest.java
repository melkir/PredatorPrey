import agents.AgentType;
import agents.Wolf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WolfTest {

    @Test
    void getType() {
        assertEquals(AgentType.Wolf, new Wolf().getType());
    }

    @Test
    void move() {
        Wolf wolf = new Wolf(2);
        wolf.move();
        assertEquals(1, wolf.getEnergy());
    }

    @Test
    void step() {
        // TODO
    }

}