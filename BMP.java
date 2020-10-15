package photoshop;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class BMP extends Formater {

	public BMP(String str) {
		read(str);
	}

	@Override
	public void read(String str) {
		File f = new File(str);
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(str));
			//BufferedImage image = ImageIO.read(f);
			width = image.getWidth();
			height = image.getHeight();
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int rgb = image.getRGB(j, i);
					int red = (int) ((rgb & 0xff0000) >> 16);
					int green = (int) ((rgb & 0xff00) >> 8);
					int blue = (int) (rgb & 0xff);
					int alpha = (int) (256 + ((rgb & 0xff000000) >> 24));
					pixels.add(new Pixel(red, green, blue, alpha));
				}
			}
		} catch (IOException e) {
			//
		}

	}

	@Override
	public ArrayList<Pixel> getPixels() {
		return pixels;
	}

	@Override
	public void write(String str) {
		//OutputStream file=new FileOutputStream(str);
		//ImageIO.write(bImage, "bmp", file);
	}

	@Override
	public void setVector(ArrayList<Pixel> a) {
		pixels=a;
		
	}

}
