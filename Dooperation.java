package photoshop;

import java.io.IOException;

public class Dooperation extends Thread {
	private Scena scena;
	private String str;
	public Dooperation(Scena scena,String string) {
		this.scena=scena;
		str=string;
	}
	
	public void run() {
		String c = "C:\\Users\\Nikola\\Desktop\\FAKS\\2.godina\\4SEMESTAR\\POOP\\POOPC++\\projekatc++\\POOP\\",
				java = "C:\\Users\\Nikola\\Desktop\\FAKS\\2.godina\\4SEMESTAR\\POOP\\POOPJAVA\\photoshop\\Photoshop\\";

		scena.saveXML(java + "projekat.xml");
		scena.saveComposite(scena.getImage().getOperations().get(str),
				java + "composite.fun");

		String file = c + "Debug\\Project1.exe " + java + "projekat.xml " + java + "composite.fun "
				+ java;
		// 1. exe fajl c++ programa
		// 2. projekat
		// 3. kompozitna

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(file);
			process.waitFor();
			scena.readXML("projekat2.xml");
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
