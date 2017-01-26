package tests;

import agents.AgentType;
import agents.Sheep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SheepTest {

    @Test
    void getType() {
        assertEquals(AgentType.Sheep, new Sheep().getType());
    }

    @Test
    void move() {
        // TODO
        Sheep sheep = new Sheep(2);
        sheep.move();
        assertEquals(1, sheep.getEnergy());
    }

    @Test
    void die() {
    }

    @Test
    void step() {
        // TODO
    }

}