package photoshop;

import java.util.*;
import java.util.stream.*;

public abstract class Formater {
	protected int width,height,depth;
	protected ArrayList<Pixel> pixels=new ArrayList<Pixel>();
	public int getWidth() {return width;};
	public int getHeight() {return height;};
	public int getDepth() {return depth;};
	public abstract ArrayList<Pixel> getPixels();
	public abstract void read(String str);
	public abstract void write(String str);
	public abstract void setVector(ArrayList<Pixel> a);
}
