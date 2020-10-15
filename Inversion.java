package photoshop;

public class Inversion extends Operation {
	public Inversion() {}
	public String getXML() {
		return "<Op>Inversion</Op>\n<Const>" + 0 + "</Const>\n";
	}
	
	public String getName() {
		return "Inversion";
	}
}
