package photoshop;

public class Mul extends Operation {
	private int n;

	public Mul(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>Mul</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "Mul";
	}
}
