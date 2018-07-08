package hu.bp.rl;

import hu.bp.ai.agents.TemporalDifferenceAgent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.util.MLUtil;
import hu.bp.gdxlinefollower.CarDriveState;

import java.util.stream.IntStream;

public class LineFollowerAgent extends TemporalDifferenceAgent {
	public LineFollowerAgent(Environment world) {
		super(world, 0.1,0.1,1, 1000, 0.5, 0.5);
	}

	@Override
	public int getNumberOfActions() {
		return CarDriveState.values().length;
	}

	@Override
	public void reset() {
		world.reset();
	}

	@Override
	public Integer[] getGreedyPolicy() {
		return
				IntStream.range(0, world.getNumberOfStates()).map(
						state-> MLUtil.argMax(policy[state])
				).boxed().toArray(Integer[]::new);
	}
}
