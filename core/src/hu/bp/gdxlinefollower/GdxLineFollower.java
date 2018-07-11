package hu.bp.gdxlinefollower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import hu.bp.ai.agents.TemporalDifferenceAgent;
import hu.bp.ai.interfaces.Agent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.linefollowerrobot.Car;
import hu.bp.rl.LineFollowerAgent;
import hu.bp.rl.World;

import java.util.Random;

public class GdxLineFollower extends ApplicationAdapter {
	/**
	 * The size of our world
	 */
	public static final int WORLD_WIDTH = 1000;
	public static final int WORLD_HEIGHT = 1000;

	/**
	 * Our users will see a square from our world which size
	 * is VIEWPORT_SIZE in the units of our World
	 *
	 * The exacts ize of our viewport will be slightly modified, so
	 * only the viewport width will be exactly this size, the height will
	 * be adjusted to keep the aspect ratio
	 */
	public static final int VIEWPORT_SIZE = 100;

	/**
	 * The size of the car in world units. If you want to see
	 * the whole car do not set viewport size smaller than the size of
	 * the car
	 */
	public static final int CAR_HEIGHT = 50;
	public static final int CAR_WIDTH = 100;

	private Random rnd = new Random();

	private Routes routes;
	private GdxCar gdxCar;

	private long counter = 0;
	private CarDriveState driveState;

	private Environment world;
	private TemporalDifferenceAgent agent;

	@Override
	public void create () {
		routes = new Routes(WORLD_WIDTH, WORLD_HEIGHT);
		Car car = new Car(40, 11.66666667);
		gdxCar = new GdxCar(car, WORLD_WIDTH, WORLD_HEIGHT,  WORLD_WIDTH, WORLD_HEIGHT);
		routes.createRandomRoutes(10, 90, 50);

		world = new World(gdxCar, routes);
		agent = new LineFollowerAgent(world);

		agent.controlQLearning();
	}

	public void randomDriving() {
		counter--;

		if (counter < 0) {
			counter = rnd.nextInt(100);

			driveState = CarDriveState.getRandomState();
		}

		CarState carState = gdxCar.goCar(driveState);

		gdxCar.setState(carState);
	}

	private void qLearning() {
		if (!agent.oneStep()) {
			agent.controlQLearning();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		routes.drawRoutes(Color.BLUE);
		qLearning();
		//randomDriving();
		gdxCar.drawCar();
	}
}
