package hu.bp.gdxlinefollower;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Routes implements IterableRectangles, StreamableRectangles {
	private final int WORLD_WIDTH;
	private final int WORLD_HEIGHT;
	private final List<Rectangle> rectangles;

	public Routes() {
		this(1000, 1000);
	}

	public Routes(int WORLD_WIDTH, int WORLD_HEIGHT) {
		this.WORLD_WIDTH = WORLD_WIDTH;
		this.WORLD_HEIGHT = WORLD_HEIGHT;
		rectangles = new ArrayList<>();
	}

	@Override
	public Iterator<Rectangle> getIterator() {
		return rectangles.iterator();
	}

	public boolean isOnRoute(Vector3 point) {
		boolean onRoute = rectangles.stream().anyMatch(rect -> rect.contains(point.x, point.y));

		return onRoute;
	}

	public void createRandomRoutes(int maxWidth, int minLength, int numberOfRoutes) {
		rectangles.addAll(
				IntStream.range(0, numberOfRoutes).parallel().boxed().
						map(i -> getRandomRoute(maxWidth, minLength)).
						collect(Collectors.toList()));
	}

	public void addRectangle(Rectangle rectangle) {
		rectangles.add(rectangle);
	}

	private Rectangle getRandomRoute(int maxWidth, int minLength) {
		Random rnd = new Random();

		int x = rnd.nextInt(WORLD_WIDTH) - WORLD_WIDTH / 2;
		int y = rnd.nextInt(WORLD_HEIGHT) - WORLD_HEIGHT / 2;

		int width, height;

		if (rnd.nextBoolean()) {
			width = 10 + rnd.nextInt(maxWidth);
			height = rnd.nextInt() + minLength;
		}
		else {
			height = 10 + rnd.nextInt(maxWidth);
			width = rnd.nextInt() + minLength;
		}

		return new Rectangle(x, y, width, height);
	}

	public void createConcentricRoutes(int width, int space, int routeWidth) {
		int x = - width / 2 - routeWidth;
		int y = - space - routeWidth;
		int squareHeight = 2 * space + 2 * routeWidth;
		int squareWidth = width + 2 * routeWidth;

		int delta = space + routeWidth;

		for (int i = 0; x > -WORLD_WIDTH / 2 && y > -WORLD_HEIGHT / 2; i++) {
			createRectangleRoute(x, y, squareWidth, squareHeight, routeWidth);

			x -= delta;
			y -= delta;
			squareHeight += 2 * delta;
			squareWidth += 2 * delta;
		}
	}

	private void createRectangleRoute(int x, int y, int width, int height, int routeWidth) {
		rectangles.add(new Rectangle(x, y, routeWidth, height)); //left
		rectangles.add(new Rectangle(x, y + height - routeWidth, width, routeWidth)); //top
		rectangles.add(new Rectangle(x, y, width, routeWidth)); //bottom
		rectangles.add(new Rectangle(x + width, y, routeWidth, height)); //right
	}

	@Override
	public Stream<Rectangle> stream() {
		return rectangles.stream();
	}
}
