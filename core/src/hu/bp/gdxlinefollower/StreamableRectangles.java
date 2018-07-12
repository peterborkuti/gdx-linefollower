package hu.bp.gdxlinefollower;

import com.badlogic.gdx.math.Rectangle;

import java.util.stream.Stream;

public interface StreamableRectangles {
	Stream<Rectangle> stream();
}
