package photoshop;

public class Min extends Operation {
	private int i;

	public Min(int i) {
		this.i = i;
	}

	public String getXML() {
		return "<Op>Min</Op>\n<Const>" + 0 + "</Const>\n";
	}

	public String getName() {
		return "Min";
	}
}
