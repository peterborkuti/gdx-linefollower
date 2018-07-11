package hu.bp.rl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.rl.Step;
import hu.bp.gdxlinefollower.CarDriveState;
import hu.bp.gdxlinefollower.CarState;
import hu.bp.gdxlinefollower.GdxCar;
import hu.bp.gdxlinefollower.Routes;

public class World implements Environment {
	private GdxCar gdxCar;
	private Routes routes;
	private long counter = 0;

	public World(GdxCar gdxCar, Routes routes) {
		this.gdxCar = gdxCar;
		this.routes = routes;
	}

	private int getOnRoute() {
		Vector3[] sensors = gdxCar.getSensorsWordCoordinates();
		int onRoute = 0;
		int exponent = 1;

		for (int i = 0; i < sensors.length; i++) {
			onRoute += routes.isOnRoute(sensors[i]) ? exponent : 0;

			exponent *= 2;
		}

		return onRoute;
	}

	@Override
	public int reset() {
		Gdx.app.log("World", "RESET");
		gdxCar.setState(new CarState(0, 0, 0));

		return getOnRoute();
	}

	private int getReward(int onRoute, int action) {
		int reward = onRoute == 2 ? CarDriveState.getState(action).actionValue * 10 : -100;

		//if (onRoute == 2) {
			//Gdx.app.log("World", "" + counter++ + ". action:" + CarDriveState.getState(action).name() + ", onRoad:" + onRoute + " -> reward:" + reward);
		//}

		return reward;
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
		return 8;
	}
}
