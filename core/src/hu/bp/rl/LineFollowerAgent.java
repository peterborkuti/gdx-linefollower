package hu.bp.rl;

import hu.bp.ai.agents.TemporalDifferenceAgent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.util.MLUtil;
import hu.bp.gdxlinefollower.CarDriveState;

import java.util.stream.IntStream;

public class LineFollowerAgent extends TemporalDifferenceAgent {
	public LineFollowerAgent(Environment world, double stepSize, double epsilon) {
		super(world, stepSize, epsilon);
	}

	@Override
	public int getNumberOfActions() {
		return CarDriveState.values().length;
	}

	@Override
	public int getAction(int state) {
		return 0;
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
