package photoshop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class PAM extends Formater {

	private int maxval;
	private String type;

	public PAM(String str) {
		read(str);
	}

	public PAM(int w, int h) {
		width = w;
		height = h;
		depth = 4;
		maxval = 255;
		type = "RGB_ALPHA";
	}

	@Override
	public void read(String str) {
		try {
			InputStream file = new FileInputStream(str);
			readHeader(file);
			readBody(file);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readHeader(InputStream file) {
		StringBuilder string;
		int byteRead;
		String[] niz = { "", "", "", "", "", "", "" };
		try {
			for (int i = 0; i < 7; i++) {
				string = new StringBuilder();
				while ((byteRead = file.read()) != 10) {
					string.append((char) byteRead);
				}
				niz[i] = string.toString();
			}
			Pattern pwidth = Pattern.compile("^WIDTH (.*)$");
			Matcher mwidth = pwidth.matcher(niz[1]);
			if (mwidth.matches())
				this.width = Integer.parseInt(mwidth.group(1));

			Pattern pheight = Pattern.compile("^HEIGHT (.*)$");
			Matcher mheight = pheight.matcher(niz[2]);
			if (mheight.matches())
				this.height = Integer.parseInt(mheight.group(1));

			Pattern pdepth = Pattern.compile("^DEPTH (.*)$");
			Matcher mdepth = pdepth.matcher(niz[3]);
			if (mdepth.matches())
				this.depth = Integer.parseInt(mdepth.group(1));

			Pattern pmaxval = Pattern.compile("^MAXVAL (.*)$");
			Matcher mmaxval = pmaxval.matcher(niz[4]);
			if (mmaxval.matches())
				this.maxval = Integer.parseInt(mmaxval.group(1));

			Pattern ptype = Pattern.compile("^TUPLTYPE (.*)$");
			Matcher mtype = ptype.matcher(niz[5]);
			if (mtype.matches())
				this.type = mtype.group(1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readBody(InputStream file) {
		int byteRead, r = 0, g = 0, b = 0, a = 0, count = 0;
		try {
			if (depth == 3)
				while ((byteRead = file.read()) != -1) {
					if (count % 3 == 0)
						r = byteRead;
					else if (count % 3 == 1)
						g = byteRead;
					else
						b = byteRead;
					count++;
					if (count % 3 == 0)
						pixels.add(new Pixel(r, g, b, 255));

				}
			else if (depth == 4) {
				while ((byteRead = file.read()) != -1) {
					if (count % 4 == 0)
						r = byteRead;
					else if (count % 4 == 1)
						g = byteRead;
					else if (count % 4 == 2)
						b = byteRead;
					else a=byteRead;
					count++;
					if (count % 4 == 0)
						pixels.add(new Pixel(r, g, b, a));

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("kraj");
	}

	@Override
	public ArrayList<Pixel> getPixels() {
		return pixels;
	}

	@Override
	public void write(String str) {
		try {
			OutputStream file = new FileOutputStream(str);
			String string = "P7\nWIDTH " + width + "\nHEIGHT " + height + "\nDEPTH " + depth + "\nMAXVAL " + maxval
					+ "\nTUPLTYPE " + type + "\nENDHDR\n";

			file.write(string.getBytes(StandardCharsets.UTF_8));

			for (int i = 0; i < pixels.size(); i++) {
				Pixel p = pixels.get(i);
				file.write(p.getRed());
				file.write(p.getGreen());
				file.write(p.getBlue());
				file.write(p.getAlpha());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setVector(ArrayList<Pixel> a) {
		pixels = a;

	}

}
