package photoshop;

public class Div extends Operation {
	private int n;

	public Div(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>Div</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "Div";
	}
}
