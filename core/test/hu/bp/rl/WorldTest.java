package hu.bp.rl;

import hu.bp.gdxlinefollower.GdxCar;
import hu.bp.linefollowerrobot.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

	@Test
	void getOnRoute() {
		Car car = new Car(10, 1);
		GdxCar gdxCar = new GdxCar(car, 1000, 1000, 1000, 1000);

	}

	@Test
	void getReward() {
	}
}