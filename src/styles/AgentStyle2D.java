package styles;

import java.awt.Color;

import agents.Sheep;
import agents.Wolf;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;

public class AgentStyle2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(Object o) {
		if (o instanceof Wolf)
			return Color.DARK_GRAY;
		else if (o instanceof Sheep)
			return Color.WHITE;
		return null;
	}

	@Override
	public float getScale(Object o) {
		if (o instanceof Wolf)
			return 2f;
		else if (o instanceof Sheep)
			return 1f;
		return 1f;
	}
}