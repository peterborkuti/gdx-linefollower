package hu.bp.gdxlinefollower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import hu.bp.linefollowerrobot.Car;
import hu.bp.linefollowerrobot.CarStateChange;

public class GdxCar {
	private Car car;
	private int carWidth;
	private int carHeight;
	private ShapeRenderer renderer;
	private float x = 0, y = 0, angle = 0;

	public GdxCar(Car car, int carWidth, int carHeight) {
		this.car = car;
		this.carWidth = carWidth;
		this.carHeight = carHeight;
		renderer = new ShapeRenderer();
	}

	public void drawCar(Camera camera, ShapeRenderer renderer, CarStateChange carStateChange) {
		renderer.translate(-x, -y, 0);
		renderer.rotate(0, 0, 1F, -angle);
		renderer.translate((float)carStateChange.x, (float)carStateChange.y, 0F);
		renderer.rotate(0, 0, 1F, (float)carStateChange.angle);
		renderer.rotate(0, 0, 1F, angle);
		renderer.translate(x, y, 0);
		Vector3 newPos = renderer.getTransformMatrix().getTranslation(new Vector3(0, 0, 0));
		x = newPos.x;
		y = newPos.y;

		angle += carStateChange.angle;

		renderer.setProjectionMatrix(camera.combined);
		drawCar(renderer, carWidth, carHeight, (int)car.wheelDiameter);
		Gdx.app.log("GdxCar", x + "," + y + "," + angle);
	}

	private void drawCar(ShapeRenderer renderer, int width, int height, int wheelDia) {

		int cx = - width / 2;
		int cy = 0;
		int wheelThickness = (int)(wheelDia / 2F);

		//Vector3 screenCenterInRealWorld = carCam.unproject(new Vector3);

		//front

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);

		renderer.rect(- wheelDia / 4, cy, wheelDia / 2, height / 2);
		renderer.end();

		//track
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.RED);

		renderer.rect(cx, cy - height / 8F, width, height / 4F);
		renderer.end();


		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		//left wheel
		renderer.rect(cx, cy - wheelDia / 2, wheelThickness, wheelDia);

		//right wheel
		renderer.rect(cx + width - wheelThickness, cy - wheelDia / 2, wheelThickness, wheelDia);

		renderer.end();
	}
}
