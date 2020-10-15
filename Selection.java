package photoshop;

import java.util.*;

public class Selection {
	private String name;
	private boolean active;
	private Set<Rectangle> rectangles=new HashSet<Rectangle>();
	
	public Selection(String name, boolean active,Set<Rectangle> rectangles) {
		super();
		this.name = name;
		this.active = active;
		this.rectangles = rectangles;
	}

	public String getName() {
		return name;
	}

	public void setActive(int active) {
		this.active = active==1?true:false;
	}

	public Set<Rectangle> getRectangles() {
		return rectangles;
	}
	
	public int getActive() {
		return active?1:0;
	}
	
}
