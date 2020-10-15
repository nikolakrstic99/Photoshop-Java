package photoshop;

public class Oboji extends Thread {
	private Scena scena;
	private int red,green,blue,alpha;
	private String string;
	public Oboji(Scena scena, String selectedItem, int parseInt, int parseInt2, int parseInt3, int parseInt4) {
		this.scena=scena;
		red=parseInt;
		green=parseInt2;
		blue=parseInt3;
		alpha=parseInt4;
		string=selectedItem;
	}
	
	public void run() {
		scena.oboji(string, red, green, blue, alpha);
	}

}
