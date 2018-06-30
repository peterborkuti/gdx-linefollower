package hu.bp.gdxlinefollower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import hu.bp.linefollowerrobot.Car;
import hu.bp.linefollowerrobot.CarStateChange;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GdxLinefollower extends ApplicationAdapter {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;

	private ShapeRenderer renderer;
	private Camera cam;
	private Car car;
	private float x, y, halfTrackWidth, wheelRadius;
	private double angle;
	private Random rnd = new Random();

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
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

	private void drawConcentricRoutes(ShapeRenderer renderer, Camera camera, int width, int space, int routeWidth) {
		Gdx.gl.glClearColor(0, 1, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setProjectionMatrix(cam.combined);

		int x = - width / 2 - routeWidth;
		int y = - space - routeWidth;
		int squareHeight = 2 * space + 2 * routeWidth;
		int squareWidth = width + 2 * routeWidth;

		int delta = space + routeWidth;

		renderer.begin(ShapeRenderer.ShapeType.Filled);

		for (int i = 0; x > - WIDTH / 2 && y > - HEIGHT / 2; i++) {
			drawRoute(renderer, x, y, squareWidth, squareHeight, routeWidth);

			x -= delta;
			y -= delta;
			squareHeight += 2 * delta;
			squareWidth += 2 * delta;
		}

		renderer.end();
	}

	private void drawRoute(ShapeRenderer renderer, int x, int y, int width, int height, int routeWidth) {
		renderer.setColor(Color.BLUE);

		renderer.rect(x, y, routeWidth, height); //left
		renderer.rect(x, y + height - routeWidth, width, routeWidth); //top
		renderer.rect(x, y, width, routeWidth); //bottom
		renderer.rect(x + width, y, routeWidth, height); //right
	}


	@Override
	public void render () {
		CarStateChange carStateChange = moveCar();
		x += (float)carStateChange.x;
		y += (float)carStateChange.y;
		angle += carStateChange.angle;
		Gdx.app.log("render",x + "," + y + "," + angle);

		drawConcentricRoutes(renderer, cam, 190, 90, 50);

		drawCar();
	}

	private CarStateChange moveCar() {
		int move = rnd.nextInt(9) - 4;
		return car.move(move, move, 0.1);
	}

	private void drawCar() {
		//shapeRenderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		renderer.setColor(Color.BLUE);

		renderer.line(x - halfTrackWidth, y, x + halfTrackWidth, y);
		renderer.line(x - halfTrackWidth, y - wheelRadius, x - halfTrackWidth, y + wheelRadius);
		renderer.line(x + halfTrackWidth, y - wheelRadius, x + halfTrackWidth, y + wheelRadius);

		renderer.setColor(Color.RED);

		renderer.line(x, y, x, y + 4 * wheelRadius);
		renderer.end();
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}
}
