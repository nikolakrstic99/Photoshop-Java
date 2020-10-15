package photoshop;

import java.util.*;

public class CompositeOperation extends Operation {

	private LinkedList<Operation> operations = new LinkedList<Operation>();
	private String name;

	public CompositeOperation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public LinkedList<Operation> getOperations() {
		return operations;
	}

	public void clear() {
		operations = null;
	}

	public int getSize() {
		return operations.size();
	}

	public void addOperation(Operation o) {
		operations.add(o);
	}

	public String getXML() {
		StringBuilder s = new StringBuilder();
		s.append("<OperationName>" + name + "</OperationName>\n");
		s.append("<OperationSize>" + getSize() + "</OperationSize>\n");

		for (int i = 0; i < operations.size(); i++) {
			if (operations.get(i) instanceof CompositeOperation) {
				CompositeOperation o2 = (CompositeOperation) operations.get(i);
				s.append("<OperationName>" + o2.getName() + "</OperationName>\n");
				s.append("<OperationSize>" + o2.getSize() + "</OperationSize>\n");
				for (int j = 0; j < operations.get(i).getSize(); j++) {
					s.append(o2.getOperations().get(j).getXML());
				}
			} else {
				s.append(operations.get(i).getXML());
			}
		}

		return s.toString();
	}
}
