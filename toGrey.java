package photoshop;

public class toGrey extends Operation {

	public toGrey() {
	}

	public String getXML() {
		return "<Op>toGrey</Op>\n<Const>" + 0 + "</Const>\n";
	}

	public String getName() {
		return "toGrey";
	}
}
