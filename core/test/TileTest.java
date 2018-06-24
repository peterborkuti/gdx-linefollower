import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TileTest {

	@Test
	public void testHandler() {
		// Create a new texture to create a tileset from
		Texture texture = new Texture(512, 512, Pixmap.Format.RGB888);

		// Assert the tileset is not null
		assertNotNull(texture);
	}
}