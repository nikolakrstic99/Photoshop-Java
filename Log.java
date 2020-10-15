package photoshop;

public class Log extends Operation {

	public Log() {
	}

	public String getXML() {
		return "<Op>Log</Op>\n<Const>" + 0 + "</Const>\n";
	}

	public String getName() {
		return "Log";
	}
}
