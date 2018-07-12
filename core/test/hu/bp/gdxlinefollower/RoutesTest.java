package hu.bp.gdxlinefollower;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class RoutesTest {

	@Test
	void getIterator() {
		Routes routes = new Routes();
		Iterator<Rectangle> iterator = routes.getIterator();
		assertNotNull(iterator);
	}

	@Test
	void isOnRoute() {
		Routes routes = new Routes();
		Rectangle rect1 = new Rectangle(1, 1, 10, 20);
		routes.addRectangle(rect1);

		assertTrue(routes.isOnRoute(new Vector3(2, 2, 0)));
		assertTrue(routes.isOnRoute(new Vector3(1, 1, 0)));
		assertTrue(routes.isOnRoute(new Vector3(8, 1, 0)));

		assertFalse(routes.isOnRoute(new Vector3(0, 0, 0)));
		assertFalse(routes.isOnRoute(new Vector3(1, 22, 0)));
	}

	@Test
	void createRandomRoutes() {
		Routes routes = new Routes();
		routes.createRandomRoutes(1, 1, 10);
		assertEquals(10, routes.stream().filter(rect -> rect != null).count());
	}

	@Test
	void addRectangle() {
		Routes routes = new Routes();
		Rectangle rect1 = new Rectangle(1, 2, 10, 20);
		routes.addRectangle(rect1);
		Iterator<Rectangle> iterator = routes.getIterator();
		assertTrue(iterator.hasNext());
		Rectangle rect2 = iterator.next();
		assertFalse(iterator.hasNext());
		assertEquals(rect1, rect2);
	}

	void createConcentricRoutes() {
		fail("Not tested");
	}
}