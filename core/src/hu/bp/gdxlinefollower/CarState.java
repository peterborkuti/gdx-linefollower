package hu.bp.gdxlinefollower;

import com.badlogic.gdx.math.MathUtils;

public class CarState {
	public final float x;
	public final float y;
	public final float directionInDegrees;
	public final float directionInRadiaans;

	public CarState(float x, float y, float directionInRadiaans) {
		this.x = x;
		this.y = y;
		this.directionInRadiaans = directionInRadiaans;
		this.directionInDegrees = MathUtils.radiansToDegrees * directionInRadiaans;
	}
}
