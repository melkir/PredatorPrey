import agents.AbstractAgent;
import agents.Grass;
import agents.Sheep;
import agents.Wolf;
import config.Constants;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.valueLayer.GridValueLayer;

public class PAContextCreator implements ContextBuilder<AbstractAgent> {

    @Override
    public Context build(Context<AbstractAgent> context) {
        int xdim = 50, ydim = 50; // dimension of the physical space

        GridFactoryFinder.createGridFactory(null).createGrid(
                Constants.PROJECTION_GRID, context,
                new GridBuilderParameters<>(new repast.simphony.space.grid.WrapAroundBorders(),
                new RandomGridAdder<>(), true, xdim, ydim));

        ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).createContinuousSpace(
                Constants.PROJECTION_SPACE, context, new RandomCartesianAdder<>(),
                new repast.simphony.space.continuous.WrapAroundBorders(), xdim, ydim, 1);

        GridValueLayer valueLayer = new GridValueLayer(Constants.VALUE_LAYER_NAME, true,
                new repast.simphony.space.grid.WrapAroundBorders(), xdim, ydim);

        // retrieve the number of wolves and sheep from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        int numWolves = (Integer) params.getValue(Constants.PARAM_NG_WOLVES_INIT);
        int numSheep = (Integer) params.getValue(Constants.PARAM_NB_SHEEP_INIT);

        // add wolves, sheep and grass agents to the context
        for (int i = 0; i < numWolves; i++) context.add(new Wolf());
        for (int i = 0; i < numSheep; i++) context.add(new Sheep());
        for (int i = 0; i < xdim; i++) for (int j = 0; j < ydim; j++) new Grass(context, i, j);

        context.addValueLayer(valueLayer);

        return context;
    }

}
