package photoshop;

import java.util.*;

public class Layer {
	private int id, width, height, transparency;
	private int originalWidth, originalHeight;
	private static int idNext = 0;
	private boolean active;
	private String string;
	public ArrayList<Pixel> pixels = new ArrayList<Pixel>();

	public Layer(int width, int height, int transparency, boolean active) {
		super();

		this.id = idNext++;
		this.originalWidth = this.width = width;
		this.originalHeight = this.height = height;
		this.transparency = transparency;
		this.active = active;
	}

	public Layer(String str, boolean active, int transparency) {
		this.string = str;
		this.transparency = transparency;
		this.active = active;
		this.id = idNext++;
		Formater f;
		if (str.substring(str.length() - 3).equals("pam")) {
			f = new PAM(str);
			this.originalWidth=width = f.getWidth();
			this.originalHeight = height = f.getHeight();
			pixels = f.getPixels();
			return;
		} else if (str.substring(str.length() - 3).equals("bmp")) {
			f = new BMP(str);
			this.originalWidth = width = f.getWidth();
			this.originalHeight = height = f.getHeight();
			pixels = f.getPixels();
			return;
		}
		// greska
	}

	public Layer(boolean active, int transparency) {
		this.active = active;
		this.transparency = transparency;
		this.height = this.width = 0;
		pixels = null;
		id = idNext++;
	}

	public Layer() {
		this(true, 100);
	}

	public void extend(int ImageWidth, int ImageHeight, int layerWidth, int layerHeight) {
		Pixel[] tmp = new Pixel[ImageWidth * ImageHeight];

		for (int i = 0; i < layerHeight; i++) {
			for (int j = 0; j < layerWidth; j++) {
				tmp[j + i * ImageWidth] = pixels.get(j + i * layerWidth);

			}
			for (int j = layerWidth; j < ImageWidth; j++)
				tmp[j + i * ImageWidth] = new Pixel(255,255,255, 0);
		}
		for (int i = layerHeight; i < ImageHeight; i++)
			for (int j = 0; j < ImageWidth; j++)
				tmp[j + i * ImageWidth] = new Pixel(255,255,255, 0);

		pixels = null;
		pixels = new ArrayList<Pixel>(Arrays.asList(tmp));
		width = ImageWidth;
		height = ImageHeight;
	}

	public ArrayList<Pixel> getPixels() {
		return pixels;
	}

	public void setPixels(ArrayList<Pixel> p) {
		pixels = p;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public int getOriginalWidth() {
		return originalWidth;
	}

	public int getOriginalHeight() {
		return originalHeight;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTransparency() {
		return transparency;
	}

	public void setTransparency(int transparency) {
		this.transparency = transparency;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getActive() {
		return active?1:0;
	}

	public String getString() {
		return string;
	}

	public void changeState() {
		active = !active;
	}

	public void changeTransparency(int t) {
		this.transparency = t;
	}
}
