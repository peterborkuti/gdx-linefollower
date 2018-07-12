package hu.bp.gdxlinefollower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GdxRoutes {
	private final StreamableRectangles routes;
	private final int VIEWPORT_WIDTH;
	private final int VIEWPORT_HEIGHT;
	private final ShapeRenderer renderer;

	public GdxRoutes(StreamableRectangles routes, int VIEWPORT_WIDTH, int VIEWPORT_HEIGHT) {
		this.routes = routes;
		this.VIEWPORT_WIDTH = VIEWPORT_WIDTH;
		this.VIEWPORT_HEIGHT = VIEWPORT_HEIGHT;

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT).combined);
	}

	public void drawRoutes(Color color) {
		renderer.setColor(color);
		renderer.begin(ShapeRenderer.ShapeType.Filled);

		routes.stream().forEach(rect -> renderer.rect(rect.x, rect.y, rect.width, rect. height));

		renderer.end();
	}

}
