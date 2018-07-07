package hu.bp.gdxlinefollower;

import java.util.Random;

public enum CarDriveState {
	FORWARD (1, 1),
	LEFT (-1, 1),
	RIGHT (1, -1),
	BACKWARD (-1, -1),
	STOP (0, 0);

	public final float leftLinearVelocity;
	public final float rightLinearVelocity;
	public final float time;

	private CarDriveState(float leftLinearVelocity, float rightLinearVelocity) {
		this.leftLinearVelocity = leftLinearVelocity;
		this.rightLinearVelocity = rightLinearVelocity;
		this.time = 1;
	}

	@Override
	public String toString() {
		return "CarDriveState{" +
				"leftLinearVelocity=" + leftLinearVelocity +
				", rightLinearVelocity=" + rightLinearVelocity +
				", time=" + time +
				'}';
	}

	public static CarDriveState getRandomState() {
		int len = CarDriveState.values().length;

		int ord = new Random().nextInt(len);

		CarDriveState state = CarDriveState.values()[ord];

		return state;
	}

	public static CarDriveState getState(int index) {
		int len = CarDriveState.values().length;

		if (index < 0 || index >= len) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + len +". Actual value:" + index);
		}

		return CarDriveState.values()[index];
	}
}
