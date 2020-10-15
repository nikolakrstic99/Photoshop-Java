package photoshop;

public class Pixel {
	private int red, green, blue, alpha;

	public Pixel(int r, int g, int b, int a) {
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}

	public Pixel(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public Pixel() {
		this(255, 255, 255, 255);
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = (int)alpha;
	}
}
