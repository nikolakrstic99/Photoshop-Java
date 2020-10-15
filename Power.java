package photoshop;

public class Power extends Operation {
	private int n;

	public Power(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>Power</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "Power";
	}
}
