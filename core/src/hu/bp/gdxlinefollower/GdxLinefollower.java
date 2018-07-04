package hu.bp.gdxlinefollower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import hu.bp.linefollowerrobot.Car;
import hu.bp.linefollowerrobot.CarStateChange;

import java.util.Random;

public class GdxLinefollower extends ApplicationAdapter {
	/**
	 * The size of our world
	 */
	public static final int WORLD_WIDTH = 10000;
	public static final int WORLD_HEIGHT = 10000;

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

	private ShapeRenderer renderer;
	private Camera camera;

	private Car car;
	private float carWorldX = 0, carWorldY = 0, carWorldAngle = 0;
	private Random rnd = new Random();

	private Routes routes;
	private GdxCar gdxCar;

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		routes = new Routes(WORLD_WIDTH, WORLD_HEIGHT);
		car = new Car(CAR_WIDTH, CAR_HEIGHT / 2);
		gdxCar = new GdxCar(car, CAR_WIDTH, CAR_HEIGHT);
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		renderer.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render () {
		moveCar();

		Gdx.app.log("render",carWorldX + "," + carWorldY + "," + carWorldAngle);
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		routes.drawConcentricRoutes(renderer, Color.BLUE, 190, 90, 50);

		gdxCar.drawCar(camera, carWorldX, carWorldY, carWorldAngle);

		//Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, 1, 1);
		//Gdx.app.log("pixel:", "" + pixmap.getPixel(0, 0));
	}

	private void moveCar() {
		//int move = rnd.nextInt(9) - 4;
		CarStateChange carStateChange = car.move(10, 30, 0.001);
		carWorldX += carStateChange.x;
		carWorldY += carStateChange.y;
		carWorldAngle += MathUtils.radDeg * carStateChange.angle;
		carWorldAngle %= 360;
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}
}
