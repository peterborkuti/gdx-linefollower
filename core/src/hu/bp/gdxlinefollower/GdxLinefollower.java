package hu.bp.gdxlinefollower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hu.bp.linefollowerrobot.Car;
import hu.bp.linefollowerrobot.CarStateChange;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GdxLinefollower extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;

	private Car car;
	private int x, y, halfTrackWidth, wheelRadius;
	private double angle;
	private Timer timer;
	private Random rnd = new Random();

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(600, 400);
		setCar(new Car(30, 5));

	}

	public void setCar(Car car) {
		this.car = car;
		halfTrackWidth = (int)car.trackWidth;
		wheelRadius = (int)(car.wheelDiameter / 2.0);

		x = 0;
		y = 0;
		angle = 0;
	}

	public void drawCar(CarStateChange carStateChange) {
		x += carStateChange.x;
		y += carStateChange.y;
		angle += carStateChange.angle;
	}

	@Override
	public void render () {
		CarStateChange carStateChange = moveCar();
		x += carStateChange.x;
		y += carStateChange.y;
		angle += carStateChange.angle;

		Gdx.gl.glClearColor(0, 1, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);

		drawCar();
	}

	private CarStateChange moveCar() {
		return car.move(Math.abs(rnd.nextInt(10)), Math.abs(rnd.nextInt(10)), 0.1);
	}

	private void drawCar() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.BLUE);

		shapeRenderer.line(x - halfTrackWidth, y, x + halfTrackWidth, y);
		shapeRenderer.line(x - halfTrackWidth, y - wheelRadius, x - halfTrackWidth, y + wheelRadius);
		shapeRenderer.line(x + halfTrackWidth, y - wheelRadius, x + halfTrackWidth, y + wheelRadius);

		shapeRenderer.setColor(Color.RED);

		shapeRenderer.line(x, y, x, y + 4 * wheelRadius);
		shapeRenderer.end();
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
