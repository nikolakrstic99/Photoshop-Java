package photoshop;

import java.awt.Label;

public class Ucitaj extends Thread {
	private Scena scena;
	private String string;
	public Ucitaj(Scena scena, String string) {
		this.scena=scena;
		this.string=string;
	}
	public void run() {
		scena.readXML(string);
	}
}
