package tests;

import agents.AgentType;
import agents.Grass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrassTest {

    @Test
    void getType() {
        assertEquals(AgentType.Grass, new Grass(0, 0).getType());
    }

    @Test
    void consume() {
        Grass grass = new Grass(0, 0);
        if (grass.isAlive()) grass.consume();
        assertEquals(grass.isAlive(), false);
    }

    @Test
    void step() {
        // TODO
    }

}