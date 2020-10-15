package photoshop;

public class Sacuvaj extends Thread{
	private Scena scena;
	private String string;
	public Sacuvaj(Scena scena, String string) {
		this.scena=scena;
		this.string=string;
	}
	public void run() {
		scena.saveXML(string);
	}

}
