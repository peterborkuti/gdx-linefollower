package hu.bp.gdxlinefollower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Routes {
	private int WORLD_WIDTH;
	private int WORLD_HEIGHT;

	public Routes(int WORLD_WIDTH, int WORLD_HEIGHT) {
		this.WORLD_WIDTH = WORLD_WIDTH;
		this.WORLD_HEIGHT = WORLD_HEIGHT;
	}

	public void drawConcentricRoutes(ShapeRenderer renderer, Color color, int width, int space, int routeWidth) {
		int x = - width / 2 - routeWidth;
		int y = - space - routeWidth;
		int squareHeight = 2 * space + 2 * routeWidth;
		int squareWidth = width + 2 * routeWidth;

		int delta = space + routeWidth;

		renderer.begin(ShapeRenderer.ShapeType.Filled);

		for (int i = 0; x > -WORLD_WIDTH / 2 && y > -WORLD_HEIGHT / 2; i++) {
			drawRoute(renderer, color, x, y, squareWidth, squareHeight, routeWidth);

			x -= delta;
			y -= delta;
			squareHeight += 2 * delta;
			squareWidth += 2 * delta;
		}

		renderer.end();
	}

	private void drawRoute(ShapeRenderer renderer, Color color, int x, int y, int width, int height, int routeWidth) {
		renderer.setColor(color);

		renderer.rect(x, y, routeWidth, height); //left
		renderer.rect(x, y + height - routeWidth, width, routeWidth); //top
		renderer.rect(x, y, width, routeWidth); //bottom
		renderer.rect(x + width, y, routeWidth, height); //right
	}
}
