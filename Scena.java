package photoshop;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

import javax.imageio.ImageIO;

public class Scena extends Canvas {
	private Aplikacija aplikacija;
	private BufferedImage startImage;
	private Image image = null;
	private boolean selectionGet = false;
	private int x, y, x2, y2;
	private Set<Rectangle> niz = new HashSet<Rectangle>();

	Scena(Aplikacija a) {
		this.aplikacija = a;

		try {
			startImage = ImageIO.read(getClass().getResource("start.png"));
		} catch (IOException e) {
			// nema tog fajla
		}

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (selectionGet) {
					x = e.getX();
					y = e.getY();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (selectionGet) {
					x2 = e.getX();
					y2 = e.getY();
					niz.add(new Rectangle(y, x, x2 - x, y2 - y));
					repaint();
				}
			}
		});
	}

	public void createImage(String str, boolean active, int t) {
		image = new Image(str, active, t);
		repaint();
	}

	public void createImageWH(int width, int height) {
		image = new Image(width, height);
		repaint();
	}

	public void addLayer(String str, boolean active, int transparency) {
		image.addLayer(str, active, transparency);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		float[] dash1 = { 2f, 0f, 2f };
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image == null ? startImage : image.getImage(), null, 0, 0);
		g2.setColor(Color.black);
		if (niz != null) {
			for (Rectangle r : niz) {

				BasicStroke bs1 = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 8.0f, dash1, 1f);
				g2.setStroke(bs1);
				g2.drawLine(r.getColumn(), r.getRow(), r.getColumn() + r.getWidth(), r.getRow());
				g2.drawLine(r.getColumn(), r.getRow(), r.getColumn(), r.getRow() + r.getHeight());
				g2.drawLine(r.getColumn(), r.getRow() + r.getHeight(), r.getColumn() + r.getWidth(),
						r.getRow() + r.getHeight());
				g2.drawLine(r.getColumn() + r.getWidth(), r.getRow(), r.getColumn() + r.getWidth(),
						r.getRow() + r.getHeight());
			}
		}
	}

	public Image getImage() {
		return image;
	}

	public void obrisi(int i) {
		image.remove(i);
		repaint();
	}

	public void changeActive(int index) {
		image.changeState(index);
		repaint();
	}

	public void changeTransparency(int index, int t) {
		image.changeTransparency(index, t);
		repaint();
	}

	public void colorSelection(int i) {

	}

	public void showSelection(String str) {
		niz = image.getSelections().get(str).getRectangles();
		repaint();
	}

	public void noShowSelection() {
		niz = null;
		repaint();
	}

	public void deleteSelection(String str) {
		image.removeSelection(str);
	}

	public void selectionActive(String[] str) {
		image.selectionActive(str);
	}

	public void selectionGet(boolean t) {
		selectionGet = t;
	}

	public Set<Rectangle> getNiz() {
		return niz;
	}

	public void newNiz() {
		niz = new HashSet<Rectangle>();
	}

	public void clearNiz() {
		niz = null;
		repaint();
	}

	public void addOperation(String str, String i) {
		image.addOperation(str, i);
	}

	public void addCompositeOperation(Operation compositeOp) {
		image.addCompositeOperation(compositeOp);
	}

	public int saveXML(String str) {
		return image.saveXML(str);
	}

	public int readXML(String str) {
		image = new Image();
		int i = image.readXML(str);
		repaint();
		return i;
	}

	public Operation returnOperation(String Item, String text) {
		return image.returnOperation(Item, text);

	}

	public int saveComposite(Operation operation, String str) {
		return image.saveComposite(operation, str);
	}

	public int write(String str) {
		if (str.substring(str.length() - 3).equals("bmp") || str.substring(str.length() - 3).equals("pam")) {
			String c = "C:\\Users\\Nikola\\Desktop\\2.godina\\4SEMESTAR\\POOP\\POOPC++\\projekatc++\\POOP\\",
					java = "C:\\Users\\Nikola\\Desktop\\photoshop\\Photoshop\\";

			saveXML(java + "projekat.xml");

			String file = c + "Debug\\Project1.exe " + java + "projekat.xml " + java + str;
			// 1. exe fajl c++ programa
			// 2. projekat
			// 3. destinacija slike

			Runtime runtime = Runtime.getRuntime();
			try {
				Process process = runtime.exec(file);
				process.waitFor();
				return 1;
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return 0;
	}

	public void oboji(String selectedItem, int red, int green, int blue, int alpha) {
		image.oboji(selectedItem, red, green, blue, alpha);
		repaint();
	}

	public void deleteOperation(String selectedItem) {
		image.removeOperation(selectedItem);
	}
}
