package photoshop;

public class InvSub extends Operation {
	private int n;

	public InvSub(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>InvSub</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "InvSub";
	}
}
