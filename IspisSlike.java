package photoshop;

import java.io.IOException;

public class IspisSlike extends Thread {
	private Scena scena;
	private String str;
	
	public IspisSlike(Scena scena,String str) {
		this.scena=scena;
		this.str=str;
	}
	
	public void run() {
		String c = "C:\\Users\\Nikola\\Desktop\\POOP\\",
				java = "C:\\Users\\Nikola\\Desktop\\photoshop\\Photoshop\\";

		scena.saveXML(java + "projekat.xml");

		String file = c + "Debug\\Project1.exe " + java + "projekat.xml " + java + str;
		// 1. exe fajl c++ programa
		// 2. projekat
		// 3. destinacija slike

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(file);
			process.waitFor();
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
