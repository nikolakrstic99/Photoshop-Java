package photoshop;

public class Sub extends Operation {
	private int n;

	public Sub(int n) {
		this.n = n;
	}

	public String getXML() {
		return "<Op>Sub</Op>\n<Const>" + n + "</Const>\n";
	}

	public String getName() {
		return "Sub";
	}
}
