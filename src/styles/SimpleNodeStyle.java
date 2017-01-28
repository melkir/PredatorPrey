package styles;

import java.awt.Color;
import java.awt.Font;

import javax.media.j3d.Shape3D;

import agents.AbstractAgent;
import agents.Sheep;
import agents.Wolf;
import repast.simphony.visualization.visualization3D.AppearanceFactory;
import repast.simphony.visualization.visualization3D.ShapeFactory;
import repast.simphony.visualization.visualization3D.style.Style3D;
import repast.simphony.visualization.visualization3D.style.TaggedAppearance;
import repast.simphony.visualization.visualization3D.style.TaggedBranchGroup;

public class SimpleNodeStyle implements Style3D<AbstractAgent> {

	public TaggedBranchGroup getBranchGroup(AbstractAgent agent, TaggedBranchGroup taggedGroup) {
		if (taggedGroup == null || taggedGroup.getTag() == null) {
			taggedGroup = new TaggedBranchGroup("DEFAULT");
			Shape3D sphere = ShapeFactory.createSphere(.03f, "DEFAULT");
			sphere.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
			taggedGroup.getBranchGroup().addChild(sphere);

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
		if (taggedAppearance == null)
			taggedAppearance = new TaggedAppearance();

		if (agent instanceof Wolf)
			AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.darkGray);
		if (agent instanceof Sheep)
			AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.white);

		return taggedAppearance;
	}

	public float[] getScale(AbstractAgent o) {
		return null;
	}
}
