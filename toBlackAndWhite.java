package photoshop;

public class toBlackAndWhite extends Operation {

	public toBlackAndWhite() {
	}

	public String getXML() {
		return "<Op>toBlackAndWhite</Op>\n<Const>" + 0 + "</Const>\n";
	}

	public String getName() {
		return "toBlackAndWhite";
	}
}
