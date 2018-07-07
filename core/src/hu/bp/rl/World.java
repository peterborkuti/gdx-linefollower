package hu.bp.rl;

import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.rl.Step;
import hu.bp.gdxlinefollower.CarDriveState;
import hu.bp.gdxlinefollower.CarState;
import hu.bp.gdxlinefollower.GdxCar;
import hu.bp.gdxlinefollower.Routes;

public class World implements Environment {
	private GdxCar gdxCar;
	private Routes routes;

	public World(GdxCar gdxCar, Routes routes) {
		this.gdxCar = gdxCar;
		this.routes = routes;
	}

	private int getOnRoute() {
		boolean onRoute = routes.isOnRoute(gdxCar.getState());

		return onRoute ? 1 : 0;
	}

	@Override
	public int reset() {
		gdxCar.setState(new CarState(0, 0, 0));

		return getOnRoute();
	}

	private int getReward(int onRoute, int action) {
		if (onRoute == 0) {
			return -1;
		}

		if (CarDriveState.FORWARD.equals(CarDriveState.getState(action))) {
			return 10;
		}

		return 1;
	}

	@Override
	public Step step(int action) {
		CarState carState = gdxCar.goCar(CarDriveState.getState(action));
		gdxCar.setState(carState);

		int observation = getOnRoute();

		return new Step(observation, getReward(observation, action), false);
	}

	@Override
	public int getNumberOfStates() {
		return 2;
	}
}
