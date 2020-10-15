package photoshop;

import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Image {
	private int width, height;
	private Layer one;
	private LinkedList<Layer> layers = new LinkedList<Layer>();
	private BufferedImage bufImage;
	private Map<String, Selection> selections = new HashMap<String, Selection>();
	private Map<String, Operation> operations = new HashMap<String, Operation>();

	public Image(String str, boolean active, int transparentnost) {
		predifined();
		try {
			bufImage = ImageIO.read(getClass().getResource(str));
		} catch (IOException e) {

		}
		Layer tmp = new Layer(str, active, transparentnost);
		layers.push(tmp);
		Layer l = new Layer(str, active, transparentnost);

		updateOne(l, false);
		height = one.getHeight();
		width = one.getWidth();
	}

	private void predifined() {
		Operation inv, blackWhite, grey, medijana;
		inv = new Inversion();
		operations.put("Inversion", inv);
		blackWhite = new toBlackAndWhite();
		operations.put("toBlackAndWhite", blackWhite);
		grey = new toGrey();
		operations.put("toGrey", grey);
		medijana = new Medijana();
		operations.put("Medijana", medijana);
	}

	public Image(int w, int h) {
		predifined();
		width = w;
		height = h;
		try {
			bufImage = ImageIO.read(getClass().getResource("startNoLayer.png"));
		} catch (IOException e) {

		}
	}

	public Image() {
	}

	public int write(String str) {
		if (str.substring(str.length() - 3).equals("bmp")) {
			/*
			BufferedImage newBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for (int i = 0; i < bufImage.getHeight(); i++)
				for (int j = 0; j < bufImage.getWidth(); j++)
					newBufImage.setRGB(j, i, bufImage.getRGB(j, i));
			
			try {
				ImageIO.write(bufImage, "bmp", new File("C:\\Users\\Nikola\\Desktop\\SLIKA.bmp"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
			
			return 1;
		}
		if (str.substring(str.length() - 3).equals("pam")) {

			Formater f = new PAM(width, height);
			f.setVector(one.getPixels());
			f.write(str);
			return 1;
		}

		return 0;
	}

	public void updateOne(Layer l, boolean all) {
		double a = 0, r = 0, g = 0, b = 0, n = 0, a0, a1;

		ArrayList<Pixel> lPixels = new ArrayList<Pixel>();

		lPixels = l.getPixels();
		n = (double) l.getTransparency() / 100;

		if (all) {
			for (int i = 0; i < lPixels.size(); i++) {
				lPixels.get(i).setAlpha((double) 255 * n);
				// if (i % l.getWidth() >= l.getOriginalWidth() || i / l.getWidth() >=
				// l.getOriginalHeight())
				// lPixels.get(i).setAlpha(0);
			}
		} else {
			for (int i = 0; i < lPixels.size(); i++)
				lPixels.get(i).setAlpha((double) lPixels.get(i).getAlpha() * n);
		}
		if (one == null) {
			width = l.getWidth();
			height = l.getHeight();
			one = new Layer(width, height, l.getTransparency(), true);
			one.setPixels(lPixels);
			one.setHeight(l.getHeight());
			one.setWidth(l.getWidth());
			updateBuffImage();
			return;
		}

		ArrayList<Pixel> n0 = new ArrayList<Pixel>();
		ArrayList<Pixel> tmpPixels = new ArrayList<Pixel>();
		n0 = one.getPixels();

		for (int k = 0; k < width * height; k++) {
			a0 = (double) n0.get(k).getAlpha() / 255;
			a1 = (double) lPixels.get(k).getAlpha() / 255;
			a = a0 + (1 - a0) * a1;
			r = (double) n0.get(k).getRed() * (a0 / a) + ((double) lPixels.get(k).getRed() * (1 - a0) * a1 / a);
			g = (double) n0.get(k).getGreen() * (a0 / a) + ((double) lPixels.get(k).getGreen() * (1 - a0) * a1 / a);
			b = (double) n0.get(k).getBlue() * (a0 / a) + ((double) lPixels.get(k).getBlue() * (1 - a0) * a1 / a);
			tmpPixels.add(new Pixel((int) r, (int) g, (int) b, (int) (a * 255)));
		}

		one.setPixels(tmpPixels);
		updateBuffImage();
	}

	private void updateBuffImage() {
		bufImage = null;
		bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Pixel p = one.getPixels().get(i * width + j);
				int rgb = (p.getAlpha() << 24) | (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
				bufImage.setRGB(j, i, rgb);

			}
		}
	}

	public void addLayer(String str, boolean active, int transparency) {
		Layer l = new Layer(str, active, transparency);

		if (l.getHeight() == height && l.getWidth() == width) {// jednaki

		} else if ((l.getHeight() <= height && l.getWidth() <= width)) {// dodaje se koji je manji
			l.extend(width, height, l.getWidth(), l.getHeight());
		} else if ((l.getHeight() >= height && l.getWidth() >= width)) {// dodaje se koji je veci
			height = l.getHeight() > height ? l.getHeight() : height;
			width = l.getWidth() > width ? l.getWidth() : width;

			layers = extendAll();
			one = null;
			for (int i = 0; i < layers.size(); i++) {
				if (layers.get(i).getActive()==1)
					updateOne(layers.get(i), false);
			}
		} else { // dodaje se naizmenican
			height = l.getHeight() > height ? l.getHeight() : height;
			width = l.getWidth() > width ? l.getWidth() : width;
			layers = extendAll();
			l.extend(width, height, l.getWidth(), l.getHeight());
			one = null;
			for (int i = 0; i < layers.size(); i++) {
				if (layers.get(i).getActive()==1)
					updateOne(layers.get(i), false);
			}
		}

		if (l.getActive()==1)
			updateOne(l, false);
		layers.add(l);

	}

	public void addLayer(String str) {
		addLayer(str, true, 100);
	}

	public LinkedList<Layer> extendAll() {
		LinkedList<Layer> tmp = new LinkedList<Layer>();
		for (int i = 0; i < layers.size(); i++) {
			Layer l = layers.get(i);
			l.extend(width, height, l.getWidth(), l.getHeight());
			tmp.add(l);
		}
		return tmp;
	}

	public void addSelection(Selection s) {
		selections.put(s.getName(), s);
	}

	public BufferedImage getImage() {
		return bufImage;
	}

	public LinkedList<Layer> getLayers() {
		return layers;
	}

	public Map<String, Selection> getSelections() {
		return selections;
	}

	public Map<String, Operation> getOperations() {
		return operations;
	}

	public void remove(int i) {
		layers.remove(i);
		update();
	}

	public void removeSelection(String str) {
		selections.remove(str);
	}
	

	public void removeOperation(String str) {
		operations.remove(str);
	}

	public void selectionActive(String[] str) {
		for (int i = 0; i < str.length; i++) {
			selections.get(str[i]).setActive(1 - selections.get(str[i]).getActive());
		}
	}

	public void changeState(int i) {
		layers.get(i).changeState();
		update();
	}

	public void changeTransparency(int i, int t) {
		layers.get(i).changeTransparency(t);
		update();
	}

	private void update() {
		one = null;
		for (int j = 0; j < layers.size(); j++) {
			if (layers.get(j).getActive()==1)
				updateOne(layers.get(j), true);
		}
	}

	public void addOperation(String str, String i) {
		operations.put(str, findOp(str, i));
	}

	public Operation returnOperation(String str, String i) {
		return findOp(str, i);
	}

	public Operation findOp(String str, String j) {
		int i = 0;
		if (str != "Abs" && str != "Log")
			i = Integer.parseInt(j);
		switch (str) {
		case "Abs":
			return new Abs();
		case "Add":
			return new Add(i);
		case "Div":
			return new Div(i);
		case "InvDiv":
			return new InvDiv(i);
		case "InvSub":
			return new InvSub(i);
		case "Log":
			return new Log();
		case "Max":
			return new Max(i);
		case "Min":
			return new Min(i);
		case "Mul":
			return new Mul(i);
		case "Power":
			return new Power(i);
		case "Sub":
			return new Sub(i);
		case "toGrey":
			return new toGrey();
		case "toBlackAndWhite":
			return new toBlackAndWhite();
		case "Medijana":
			return new Medijana();
		case "Inversion":
			return new Inversion();
		}
		return null;
	}

	public void addCompositeOperation(Operation compositeOp) {
		operations.put(compositeOp.getName(), compositeOp);
	}

	public int saveXML(String str) {
		if (!str.substring(str.length() - 3).equals("xml")) {
			return 0;
		}
		int numOfLayer=1;
		String s = "layer.txt";
		try {
			FileWriter f = new FileWriter(str);
			f.write("< ? xml version = 1.0 ? >\n");
			f.write("<Image>\n");
			f.write("<Width>" + width + "</Width>\n");
			f.write("<Height>" + height + "</Height>\n");

			f.write("<One>\n");
			f.write("<oneWidth>" + one.getWidth() + "</oneWidth>\n");
			f.write("<oneHeight>" + one.getHeight() + "</oneHeight>\n");
			f.write("<oneTransparency>" + one.getTransparency() + "</oneTransparency>\n");
			f.write("<oneActive>" + one.getActive() + "</oneActive>\n");
			f.write("<oneId>" + one.getId() + "</oneId>\n");
			f.write("</One>\n");

			f.write("<LayersSize>" + one.getPixels().size() + "</LayersSize>\n\n");

			s = String.valueOf(numOfLayer++) + s;
			FileWriter filePixels = new FileWriter(s);
			int size = one.getPixels().size();
			Pixel p;
			for (int i = 0; i < size; i++) {
				p = one.getPixels().get(i);
				filePixels.write(p.getRed() + ",");
				filePixels.write(p.getGreen() + ",");
				filePixels.write(p.getBlue() + ",");
				filePixels.write(p.getAlpha() + ",");
			}
			filePixels.close();

			f.write("<Layers>\n");
			f.write("<LayersSize>" + layers.size() + "</LayersSize>\n");

			Layer l;
			for (int i = 0; i < layers.size(); i++) {
				l = layers.get(i);
				f.write("<LayerId>" + l.getId() + "</LayerId>\n");
				f.write("<LayerTransparency>" + l.getTransparency() + "</LayerTransparency>\n");
				f.write("<LayerActive>" + l.getActive() + "</LayerActive>\n");

				s = "layer.txt";
				s = String.valueOf(numOfLayer++) + s;
				filePixels = new FileWriter(s);
				for (int j = 0; j < size; j++) {
					p = l.getPixels().get(j);
					filePixels.write(p.getRed() + ",");
					filePixels.write(p.getGreen() + ",");
					filePixels.write(p.getBlue() + ",");
					filePixels.write(p.getAlpha() + ",");
				}
				filePixels.close();
			}

			f.write("</Layers>\n\n");

			f.write("<Selection>\n");
			f.write("<SelectionsSize>" + selections.size() + "</SelectionsSize>\n");
			for (Map.Entry<String, Selection> entry : selections.entrySet()) {
				f.write("<SelectionName>" + entry.getKey() + "</SelectionName>\n");
				f.write("<SelectionActive>" + entry.getValue().getActive() + "</SelectionActive>\n");
				f.write("<RectanglesSize>" + entry.getValue().getRectangles().size() + "</RectanglesSize>\n");
				Set<Rectangle> tmp = entry.getValue().getRectangles();
				f.write("<Rectangles>\n");
				for (Rectangle r : tmp) {
					f.write("<Width>" + r.getWidth() + "</Width>\n");
					f.write("<Height>" + r.getHeight() + "</Height>\n");
					f.write("<Row>" + r.getRow() + "</Row>\n");
					f.write("<Column>" + r.getColumn() + "</Column>\n");
				}
				f.write("</Rectangles>\n");
			}
			f.write("</Selection>\n\n");

			f.write("<CompositeOperations>\n");
			f.write("<NumberOfComposite>" + operations.size() + "</NumberOfComposite>\n\n");

			for (Map.Entry<String, Operation> entry : operations.entrySet()) {
				f.write(entry.getValue().getXML() + "\n");
			}

			f.write("<CompositeOperations>\n");
			f.write("</Image>");

			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public int readXML(String str) {
		if (!str.substring(str.length() - 3).equals("xml")) {
			return 0;
		}
		
		int numOfLayer=1;
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		LinkedList<Layer> tmpLayers = new LinkedList<Layer>();
		Set<Rectangle> tmpRectangles;
		File file = new File(str);
		Scanner s;
		String line = "", text = "";
		try {
			s = new Scanner(file);
			s.nextLine();
			Pattern p1 = Pattern.compile(
					"^<Image><Width>(.*)</Width><Height>(.*)</Height><One><oneWidth>(.*)</oneWidth><oneHeight>(.*)</oneHeight><oneTransparency>(.*)</oneTransparency><oneActive>(.*)</oneActive><oneId>(.*)</oneId></One>$");
			for (int i = 0; i < 10; i++) {
				line = s.nextLine();
				text = text + line; // zakljucno sa </One>
			}
			Matcher m1 = p1.matcher(text);
			if (m1.matches()) {
				width = Integer.parseInt(m1.group(1));
				height = Integer.parseInt(m1.group(2));
				one = new Layer();
				one.setWidth(Integer.parseInt(m1.group(3)));
				one.setHeight(Integer.parseInt(m1.group(4)));
				one.setTransparency(Integer.parseInt(m1.group(5)));
				one.setActive(Boolean.valueOf(m1.group(6)));
				one.setId(Integer.parseInt(m1.group(7)));
			}
			text = "";

			line = s.nextLine(); // LayersSize broj pixela
			Pattern p2 = Pattern.compile("^<LayersSize>(.*)</LayersSize><LayersSize>(.*)</LayersSize>$");
			// line=s.nextLine();
			text = line;
			line = s.nextLine(); // \n
			line = s.nextLine(); // layers
			line = s.nextLine(); // layersSize
			text = text + line;
			Matcher m2 = p2.matcher(text);
			int bound = 0, bound2 = 0, r = 0, g = 0, b = 0, a = 0, w = 0, h = 0, col = 0;
			if (m2.matches()) {
				bound = Integer.parseInt(m2.group(1));
				bound2 = Integer.parseInt(m2.group(2));
			}
			String pix = "layer.txt";
			pix = numOfLayer + pix;
			numOfLayer++;
			File filePixels = new File(pix);
			Scanner scannerPixels;
			text = "";
			scannerPixels = new Scanner(filePixels);

			while (scannerPixels.hasNextLine()) {
				line = scannerPixels.nextLine();
				text = text + line;
			}
			String[] array = text.split(",");

			for (int i = 0; i < array.length;) {
				r = Integer.parseInt(array[i++]);
				g = Integer.parseInt(array[i++]);
				b = Integer.parseInt(array[i++]);
				a = Integer.parseInt(array[i++]);
				pixels.add(new Pixel(r, g, b, a));
			}
			one.setPixels(pixels);
			array = null;
			// pixels.clear();

			for (int i = 0; i < bound2; i++) {
				line = text = "";
				for (int j = 0; j < 3; j++) {
					line = s.nextLine();
					text = text + line;
				}
				Layer tmpLayer = new Layer();
				Pattern p3 = Pattern.compile(
						"^<LayerId>(.*)</LayerId><LayerTransparency>(.*)</LayerTransparency><LayerActive>(.*)</LayerActive>$");
				Matcher m3 = p3.matcher(text);
				if (m3.matches()) {
					tmpLayer.setId(Integer.parseInt(m3.group(1)));
					tmpLayer.setTransparency(Integer.parseInt(m3.group(2)));
					tmpLayer.setActive(Boolean.parseBoolean(m3.group(3)));
				}
				tmpLayer.setHeight(one.getHeight());
				tmpLayer.setWidth(one.getWidth());

				pix = "layer.txt";
				pix = numOfLayer + pix;
				numOfLayer++;
				filePixels = new File(pix);
				text = "";
				scannerPixels = new Scanner(filePixels);

				while (scannerPixels.hasNextLine()) {
					line = scannerPixels.nextLine();
					text = text + line;
				}
				array = text.split(",");
				pixels = new ArrayList<Pixel>();
				for (int k = 0; k < array.length;) {
					r = Integer.parseInt(array[k++]);
					g = Integer.parseInt(array[k++]);
					b = Integer.parseInt(array[k++]);
					a = Integer.parseInt(array[k++]);
					pixels.add(new Pixel(r, g, b, a));
				}
				tmpLayer.setPixels(pixels);
				tmpLayers.add(tmpLayer);
				array = null;
				// pixels.clear();
			}
			layers = tmpLayers;
			s.nextLine();
			s.nextLine();
			s.nextLine();
			line = s.nextLine();
			Pattern p4 = Pattern.compile("^<SelectionsSize>(.*)</SelectionsSize>$");
			Matcher m4 = p4.matcher(line);
			if (m4.matches()) {
				bound = Integer.parseInt(m4.group(1));
			}
			String name = null;
			Boolean active = false;
			for (int i = 0; i < bound; i++) {
				tmpRectangles = new HashSet<Rectangle>();
				text = "";
				line = s.nextLine();
				text = text + line;
				line = s.nextLine();
				text = text + line;
				line = s.nextLine();
				text = text + line;

				Pattern p5 = Pattern.compile(
						"<SelectionName>(.*)</SelectionName><SelectionActive>(.*)</SelectionActive><RectanglesSize>(.*)</RectanglesSize>$");
				Matcher m5 = p5.matcher(text);
				if (m5.matches()) {
					name = m5.group(1);
					active = Boolean.parseBoolean(m5.group(2));
					bound2 = Integer.parseInt(m5.group(3));
				}

				s.nextLine();
				for (int j = 0; j < bound2; j++) {
					text = "";
					for (int k = 0; k < 4; k++) {
						line = s.nextLine();
						text = text + line;
					}
					Pattern p6 = Pattern
							.compile("^<Width>(.*)</Width><Height>(.*)</Height><Row>(.*)</Row><Column>(.*)</Column>$");
					Matcher m6 = p6.matcher(text);
					if (m6.matches()) {
						w = Integer.parseInt(m6.group(1));
						h = Integer.parseInt(m6.group(2));
						r = Integer.parseInt(m6.group(3));
						col = Integer.parseInt(m6.group(4));
					}
					tmpRectangles.add(new Rectangle(r, col, w, h));
				}
				line = s.nextLine();
				Selection tmpSelection = new Selection(name, active, tmpRectangles);

				selections.put(name, tmpSelection);
				tmpSelection = null;
				tmpRectangles = null;
			}

			line = s.nextLine(); // </Selection>
			line = s.nextLine(); // przaan red
			line = s.nextLine(); // <CompositeOperations>
			line = s.nextLine(); // <NumberOfComposite>

			Pattern p7 = Pattern.compile("<NumberOfComposite>(.*)</NumberOfComposite>$");
			Matcher m7 = p7.matcher(line);
			if (m7.matches())
				bound = Integer.parseInt(m7.group(1));
			Operation o;
			for (int i = 0; i < bound; i++) {
				s.nextLine();// prazan red
				text = "";
				line = s.nextLine();
				text = text + line;
				line = s.nextLine();
				text = text + line;
				Pattern basic = Pattern.compile("^<Op>(.*)</Op><Const>(.*)</Const>$");
				Pattern composite = Pattern
						.compile("^<OperationName>(.*)</OperationName><OperationSize>(.*)</OperationSize>$");
				Matcher mBasic = basic.matcher(text), mComposite = composite.matcher(text);
				if (mBasic.matches()) {
					line = mBasic.group(1);
					o = findOp(line, mBasic.group(2));
					operations.put(o.getName(), o);
					o = null;
				} else if (mComposite.matches()) {
					line = mComposite.group(1);
					r = Integer.parseInt(mComposite.group(2));
					o = (Operation) readComposite(file, s, line, r);
					operations.put(o.getName(), o);
				}

			}
			updateBuffImage();
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}

	private Operation readComposite(File file, Scanner s, String name, int tmp) {
		Operation c = new CompositeOperation(name), o;
		String text, line;
		int r;

		for (int i = 0; i < tmp; i++) {
			text = "";
			line = s.nextLine();
			text = text + line;
			line = s.nextLine();
			text = text + line;
			Pattern basic = Pattern.compile("^<Op>(.*)</Op><Const>(.*)</Const>$");
			Pattern composite = Pattern
					.compile("^<OperationName>(.*)</OperationName><OperationSize>(.*)</OperationSize>$");
			Matcher mBasic = basic.matcher(text), mComposite = composite.matcher(text);
			if (mBasic.matches()) {
				line = mBasic.group(1);
				o = findOp(line, mBasic.group(2));
				c.addOperation(o);
				o = null;
			} else if (mComposite.matches()) {
				line = mComposite.group(1);
				r = Integer.parseInt(mComposite.group(2));
				o = (Operation) readComposite(file, s, line, r);
				c.addOperation(o);
			}
		}
		return c;
	}

	public int saveComposite(Operation operation, String str) {
		if (!str.substring(str.length() - 3).equals("fun")) {
			return 0;
		}

		FileWriter file;
		try {
			file = new FileWriter(str);
			file.write("<CompositeOperaion>\n");
			file.write(operation.getXML());
			file.write("\n</CompositeOperaion>\n");
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}

	public void oboji(String selectedItem, int red, int green, int blue, int alpha) {
		Set<Rectangle> rectangles = new HashSet<Rectangle>();
		Layer layer;
		for (Map.Entry<String, Selection> entry : selections.entrySet()) {
			if (entry.getKey().equals(selectedItem)) {
				rectangles = entry.getValue().getRectangles();

				for (Rectangle r : rectangles) {
					for (int l = 0; l < layers.size(); l++) {
						layer = layers.get(l);
						for (int i = r.getRow(); i < (r.getRow() + r.getHeight()); i++)
							for (int j = r.getColumn(); j < (r.getColumn() + r.getWidth()); j++) {
								layer.pixels.remove(i * width + j);
								layer.pixels.add(i * width + j, new Pixel(red, green, blue, alpha));
							}

					}
				}
				update();
			}
		}

	}

}
