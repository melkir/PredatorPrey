package styles;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

public class GrassStyle2D implements ValueLayerStyleOGL {

	protected ValueLayer layer;
	private Color tan = new Color(141,110,99);
	private Color grass = new Color(100,221,23);

	public void init(ValueLayer layer) {
		this.layer = layer;
	}

	public float getCellSize() {
		return 15.0f;
	}

	/**
	 * Return the color based on the value at given coordinates.
	 */
	public Color getColor(double... coordinates) {
		double v = layer.get(coordinates);

		if (v == 1) {
			return grass;
		} else {
			return tan;
		}
	}
}