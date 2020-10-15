package photoshop;

public class Abs extends Operation {
	public Abs() {}

	public String getName() {
		return "Abs";
	}
	
	public String getXML() {
		return "<Op>Abs</Op>\n<Const>0</Const>\n";
	}
}
