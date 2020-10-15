package photoshop;

public abstract class Operation {
	
	public String getName() {
		return "x";
	}

	public String getXML() {
		return "x";
	}

	public int getSize() {
		return 1;
	}

	public void addOperation(Operation operation) {}
}
