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

    /**
     * Builds and returns a context. Building a context consists of filling it with
     * agents, adding projects and so forth. When this is called for the master context
     * the system will pass in a created context based on information given in the
     * model.score file. When called for subcontexts, each subcontext that was added
     * when the master context was built will be passed in.
     * @param context
     * @return the build context
     */
    @Override
    public Context build(Context<AbstractAgent> context) {
        // dimension of the physical space
        int xdim = 50, ydim = 50;

        // Create a new 2D grid to model the discrete patches of grass. The inputs to the
        // GridFactory include the grid name, the context in which to place the grid,
        // and the grid parameters.  Grid parameters include the border specification,
        // random adder for populating the grid with agents, boolean for multiple occupancy,
        // and the dimensions of the grid.
        
        GridFactoryFinder.createGridFactory(null).createGrid(
            Constants.PROJECTION_GRID, context,
            new GridBuilderParameters<AbstractAgent>(new repast.simphony.space.grid.WrapAroundBorders(),
                new RandomGridAdder<AbstractAgent>(), true, xdim, ydim)
        );

        // Create a new 2D continuous space to model the physical space on which the sheep
        // and wolves will move. The inputs to the Space Factory include the space name,
        // the context in which to place the space, border specification,
        // random adder for populating the grid with agents,
        // and the dimensions of the grid.
        ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).createContinuousSpace(
            Constants.PROJECTION_SPACE, context, new RandomCartesianAdder<>(),
            new repast.simphony.space.continuous.WrapAroundBorders(), xdim, ydim, 1
        );

        // Create a new 2D value layer to store the state of the grass grid. This is
        // only used for visualization since it's faster to draw the value layer
        // in 2D displays compared with rendering each grass patch as an agent.
        GridValueLayer valueLayer = new GridValueLayer(Constants.VALUE_LAYER_NAME, true,
            new repast.simphony.space.grid.WrapAroundBorders(), xdim, ydim
        );
        context.addValueLayer(valueLayer);

        // retrieve the number of wolves and sheep from the parameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        int numWolves = (Integer) params.getValue(Constants.PARAM_WOLF_INIT_POP);
        int numSheep = (Integer) params.getValue(Constants.PARAM_SHEEP_INIT_POP);

        // add wolves, sheep and grass agents to the context
        for (int i = 0; i < numWolves; i++) context.add(new Wolf());
        for (int i = 0; i < numSheep; i++) context.add(new Sheep());
        for (int i = 0; i < xdim; i++) for (int j = 0; j < ydim; j++) new Grass(context, i, j);

        context.addValueLayer(valueLayer);

        return context;
    }

}
