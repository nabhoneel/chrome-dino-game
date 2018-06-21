package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
	
	public BufferedImage getResourceImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
}
