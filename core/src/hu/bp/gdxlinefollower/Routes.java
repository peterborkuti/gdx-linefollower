package hu.bp.gdxlinefollower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Routes {
	private final int WORLD_WIDTH;
	private final int WORLD_HEIGHT;
	private final List<Rectangle> rectangles;
	private final ShapeRenderer renderer;

	public Routes(int WORLD_WIDTH, int WORLD_HEIGHT) {
		this.WORLD_WIDTH = WORLD_WIDTH;
		this.WORLD_HEIGHT = WORLD_HEIGHT;
		rectangles = new ArrayList<>();
		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT).combined);
	}

	public boolean isOnRoute(Vector3 point) {
		return rectangles.stream().anyMatch(i -> i.contains(point.x, point.y));
	}

	public void createConcentricRoutes(int width, int space, int routeWidth) {
		int x = - width / 2 - routeWidth;
		int y = - space - routeWidth;
		int squareHeight = 2 * space + 2 * routeWidth;
		int squareWidth = width + 2 * routeWidth;

		int delta = space + routeWidth;

		for (int i = 0; x > -WORLD_WIDTH / 2 && y > -WORLD_HEIGHT / 2; i++) {
			createRoute(x, y, squareWidth, squareHeight, routeWidth);

			x -= delta;
			y -= delta;
			squareHeight += 2 * delta;
			squareWidth += 2 * delta;
		}
	}

	public void drawRoutes(Color color) {
		renderer.setColor(color);
		renderer.begin(ShapeRenderer.ShapeType.Filled);

		rectangles.forEach(rect -> renderer.rect(rect.x, rect.y, rect.width, rect. height));

		renderer.end();
	}

	private void createRoute(int x, int y, int width, int height, int routeWidth) {
		rectangles.add(new Rectangle(x, y, routeWidth, height)); //left
		rectangles.add(new Rectangle(x, y + height - routeWidth, width, routeWidth)); //top
		rectangles.add(new Rectangle(x, y, width, routeWidth)); //bottom
		rectangles.add(new Rectangle(x + width, y, routeWidth, height)); //right
	}
}
