package hu.bp.gdxlinefollower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import hu.bp.ai.util.MLUtil;
import hu.bp.linefollowerrobot.Car;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GdxCar {
	public final int SENSOR_NUM = 3;
	private Car car;
	private ShapeRenderer renderer;
	private ShapeRenderer invRenderer;
	private Camera camera;
	private float worldWidth, worldHeight;
	private CarState carState;
	private Vector3[] sensorPositionsInWorldCoordinates = new Vector3[SENSOR_NUM];

	public GdxCar(Car car, int viewPortWidth, int viewPortHeight, int worldWidth, int worldHeight) {
		this.car = car;
		this.camera = new OrthographicCamera(viewPortWidth, viewPortHeight);
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		renderer = new ShapeRenderer();
		carState = new CarState(0, 0, 0);
		Arrays.setAll(sensorPositionsInWorldCoordinates, i -> Vector3.Zero);
	}

	public Vector3[] getSensorsWordCoordinates() {
		return sensorPositionsInWorldCoordinates;
	}

	public CarState goCar(CarDriveState state) {
		return driveCar(state.leftLinearVelocity, state.rightLinearVelocity, state.time);
	}

	public CarState goStraight(float velocity, float timeInMinutes) {
		float s = velocity * timeInMinutes;
		float dx = s * (float)Math.cos(carState.directionInRadians);
		float dy = s * (float)Math.sin(carState.directionInRadians);

		return new CarState(carState.x + dx, carState.y + dy, carState.directionInRadians);
	}

	/**
	 * return with the new car state if the car moves from the current position and direction
	 * with the given motor RPM for the given time
	 *
	 * for more info, see the car'f forward kinematics in the docs
	 *
	 * @param leftWheelLinearVelocity
	 * @param rightWheelLinearVelocity
	 * @param timeInMinutes
	 *
	 * @return CarState the new state
	 */

	public CarState driveCar(float leftWheelLinearVelocity, float rightWheelLinearVelocity, float timeInMinutes) {
		if (leftWheelLinearVelocity == rightWheelLinearVelocity) {
			return goStraight(leftWheelLinearVelocity, timeInMinutes);
		}

		float vr = rightWheelLinearVelocity, vl = leftWheelLinearVelocity, l = (float)car.trackWidth;

		float R = l / 2F * (vl + vr) / (vr - vl);
		float omega = (vr - vl) / l;

		float ICCx = carState.x - R * (float) Math.sin(carState.directionInRadians);
		float ICCy = carState.y + R * (float) Math.cos(carState.directionInRadians);

		float omegat = omega * timeInMinutes;
		double cos = Math.cos(omegat);
		double sin = Math.sin(omegat);

		SimpleMatrix A = new SimpleMatrix(2, 2);
		A.set(0, 0, cos);
		A.set(0, 1, -sin);
		A.set(1, 0, sin);
		A.set(1, 1, cos);

		SimpleMatrix B = new SimpleMatrix(2, 1);
		B.set(0, 0, carState.x - ICCx);
		B.set(1, 0, carState.y - ICCy);

		SimpleMatrix C = new SimpleMatrix(2, 1);
		C.set(0, 0, ICCx);
		C.set(1, 0, ICCy);

		SimpleMatrix out = A.mult(B).plus(C);

		return new CarState((float) out.get(0, 0), (float) out.get(1, 0), carState.directionInRadians + omega);
	}

	/**
	 * Draws car in the world based on the given absolute car state
	 * @param carState
	 */
	public void drawCar(CarState carState) {
		drawCar(carState.x, carState.y, carState.directionInRadians);
	}

	public void setState(CarState carState) {
		this.carState = carState;
	}

	public CarState getState() {
		return carState;
	}

	public void drawCar() {
		drawCar(carState);
	}

	/**
	 * draws car in the world
	 *
	 * @param carCenterInWorldCoordX
	 * @param carCenterInWorldCoordY
	 * @param absoluteCarDirectionInRadians
	 */
	public void drawCar(float carCenterInWorldCoordX, float carCenterInWorldCoordY, float absoluteCarDirectionInRadians) {
		float degrees = MathUtils.radiansToDegrees * absoluteCarDirectionInRadians;

		renderer.identity();

		renderer.translate(carCenterInWorldCoordX, carCenterInWorldCoordY, 0);

		renderer.rotate(0, 0, 1F, degrees - 90);

		renderer.setProjectionMatrix(camera.combined);

		_drawCar();
	}

	private Vector3 toWorldCoordinate(float x, float y) {
		Vector3 coordInRenderer = new Vector3(x, y, 0);
		Vector3 coordInWorld = coordInRenderer.prj(renderer.getTransformMatrix());

		Gdx.app.log("GdxCar", "(" + x + "," + y +")->" + coordInWorld);
		return coordInWorld;
	}

	private void _drawCar() {
		float wheelDia = (float) car.wheelDiameter;
		float width = (float) car.trackWidth;

		int height = (int) car.trackWidth;
		int cx = -(int) (car.trackWidth / 2D);
		float cy = 0;
		int wheelThickness = (int) (car.wheelDiameter / 2F);

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);

		renderer.rect((float) (-car.wheelDiameter / 4F), cy, wheelDia / 2F, height / 2F);
		renderer.end();

		//track
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.RED);

		renderer.rect(cx, cy - height / 8F, width, height / 4F);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		//left wheel
		renderer.rect(cx, cy - wheelDia / 2F, wheelThickness, wheelDia);

		//right wheel
		renderer.rect(cx + width - wheelThickness, cy - wheelDia / 2, wheelThickness, wheelDia);

		renderer.end();

		sensorPositionsInWorldCoordinates[0] = toWorldCoordinate(cx, cy);
		sensorPositionsInWorldCoordinates[1] = toWorldCoordinate(0, 0);
		sensorPositionsInWorldCoordinates[2] = toWorldCoordinate(cx + width, cy);
	}
}
