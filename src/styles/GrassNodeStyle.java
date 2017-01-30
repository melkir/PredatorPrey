package styles;

import java.awt.Color;
import java.awt.Font;

import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import agents.AbstractAgent;
import agents.Grass;
import repast.simphony.visualization.visualization3D.AppearanceFactory;
import repast.simphony.visualization.visualization3D.ShapeFactory;
import repast.simphony.visualization.visualization3D.style.Style3D;
import repast.simphony.visualization.visualization3D.style.TaggedAppearance;
import repast.simphony.visualization.visualization3D.style.TaggedBranchGroup;

public class GrassNodeStyle implements Style3D<AbstractAgent> {

	private Color tan = new Color(121,85,72);
	private Color grass = new Color(100,221,23);

	public TaggedBranchGroup getBranchGroup(AbstractAgent agent, TaggedBranchGroup taggedGroup) {

		if (taggedGroup == null || taggedGroup.getTag() == null) {
			taggedGroup = new TaggedBranchGroup("DEFAULT");
			Shape3D cube = ShapeFactory.createCube(.03f, "DEFAULT");

			Transform3D trans = new Transform3D();
			trans.set(new Vector3f(0, 0, -.05f));
			trans.setScale(new Vector3d(1, 1, 0.5));
			TransformGroup grp = new TransformGroup(trans);

			grp.addChild(cube);
			taggedGroup.getBranchGroup().addChild(grp);

			return taggedGroup;
		}
		return null;
	}

	public float[] getRotation(AbstractAgent o) {
		return null;
	}

	public String getLabel(AbstractAgent o, String currentLabel) {
		return null;
	}

	public Color getLabelColor(AbstractAgent t, Color currentColor) {
		return Color.YELLOW;
	}

	public Font getLabelFont(AbstractAgent t, Font currentFont) {
		return null;
	}

	public LabelPosition getLabelPosition(AbstractAgent o, LabelPosition curentPosition) {
		return LabelPosition.NORTH;
	}

	public float getLabelOffset(AbstractAgent t) {
		return .035f;
	}

	public TaggedAppearance getAppearance(AbstractAgent agent, TaggedAppearance taggedAppearance, Object shapeID) {
		if (taggedAppearance == null) {
			taggedAppearance = new TaggedAppearance();
		}

		Grass grass = (Grass) agent;
		if (grass.isAlive())
			AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), this.grass);
		else
			AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), this.tan);

		return taggedAppearance;

	}

	public float[] getScale(AbstractAgent o) {
		return null;
	}
}
