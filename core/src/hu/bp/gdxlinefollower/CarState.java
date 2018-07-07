package hu.bp.gdxlinefollower;

import com.badlogic.gdx.math.MathUtils;

public class CarState {
	public final float x;
	public final float y;
	public final float directionInDegrees;
	public final float directionInRadians;

	public CarState(float x, float y, float directionInRadians) {
		this.x = x;
		this.y = y;
		this.directionInRadians = directionInRadians;
		this.directionInDegrees = MathUtils.radiansToDegrees * directionInRadians;
	}
}
