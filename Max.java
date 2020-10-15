package photoshop;

public class Max extends Operation {
	private int n;

	public Max(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>Max</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "Max";
	}
}
