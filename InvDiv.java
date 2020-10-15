package photoshop;

public class InvDiv extends Operation {
	private int n;

	public InvDiv(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>InvDiv</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "InvDiv";
	}
}
