package photoshop;

public class Add extends Operation {
	private int n;

	public Add(int n) {
		this.n = n;
	}

	public String getName() {
		return "Add";
	}

	public String getXML() {
		return "<Op>Add</Op>\n<Const>" + n + "</Const>\n";
	}
}
