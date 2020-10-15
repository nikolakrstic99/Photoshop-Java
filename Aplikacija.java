package photoshop;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Map;

public class Aplikacija extends Frame {

	private Scena scena;
	private boolean postojiSlika = false;
	private MenuItem dodajSliku = new MenuItem("Dodaj sliku"), ispisiSliku = new MenuItem("Ispisi sliku"),
			sacuvajProjekat = new MenuItem("Sacuvaj projekat"), ucitajProjekat = new MenuItem("Ucitaj projekat");

	private MenuItem dodajSloj = new MenuItem("Dodaj sloj"), obrisiSloj = new MenuItem("Obrisi sloj"),
			aktivnostSloja = new MenuItem("Aktivnost sloja"),
			transparentnostSloja = new MenuItem("Transparentnost sloja");

	private MenuItem dodajSelekciju = new MenuItem("Dodaj selekciju"),
			obrisiSelekciju = new MenuItem("Obrisi selekciju"), prikaziSelekcije = new MenuItem("Prikazi selekciju"),
			aktivnostSelekcije = new MenuItem("Aktivnost Selekcije"), obojiSelekciju = new MenuItem("Oboji selekciju");

	private MenuItem dodajOperaciju = new MenuItem("Dodaj Operaciju"),
			izvrsiOperaciju = new MenuItem("Izvrsi operaciju"), obrisiOperaciju = new MenuItem("Obrisi operaciju"),
			sacuvajKompozitnu = new MenuItem("Sacuvaj kompozitnu");

	private Frame frame;
	private Operation compositeOp = null;

	
	
	Aplikacija() {
		super("Photoshop");
		this.setBounds(0, 0, 1900, 1350);
		scena = new Scena(this);
		add(scena);
		dodajMeni();

		setResizable(true);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				dispose();
			}
		});
	}

	private void dodajMeni() {
		MenuBar traka = new MenuBar();
		setMenuBar(traka);
		Menu meni1 = new Menu("File"), meni2 = new Menu("Slojevi"), meni3 = new Menu("Selekcije"),
				meni4 = new Menu("Operacije");

		traka.add(meni1);
		traka.add(meni2);
		traka.add(meni3);
		traka.add(meni4);
		meni1.add(dodajSliku);
		meni1.add(ispisiSliku);
		meni1.add(sacuvajProjekat);
		meni1.add(ucitajProjekat);
		meni1.add(sacuvajKompozitnu);

		meni2.add(dodajSloj);
		meni2.add(obrisiSloj);
		meni2.add(aktivnostSloja);
		meni2.add(transparentnostSloja);

		meni3.add(dodajSelekciju);
		meni3.add(obrisiSelekciju);
		meni3.add(prikaziSelekcije);
		meni3.add(aktivnostSelekcije);
		meni3.add(obojiSelekciju);

		meni4.add(dodajOperaciju);
		meni4.add(obrisiOperaciju);
		meni4.add(izvrsiOperaciju);

		class Osluskivac extends WindowAdapter {

			@Override
			public void windowClosing(WindowEvent dog) {
				frame.dispose();
			}
		}

		dodajSliku.addActionListener(new ActionListener() {

			TextField t = new TextField("Ime fajla", 20), tSirina = new TextField("Sirina", 10),
					tVisina = new TextField("Visina", 10), transparentnost = new TextField("Trans.[0-100]", 10);

			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Dodaj Sliku");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() == null) {
					Panel left = new Panel(new GridLayout(3, 1, 5, 5)), right = new Panel(new GridLayout(4, 1, 5, 5));
					// Checkbox cActive = new Checkbox("Aktina", true);
					Button dugme = new Button("Dodaj");

					t.setEnabled(true);
					tSirina.setEnabled(false);
					tVisina.setEnabled(false);
					// cActive.setEnabled(true);
					right.add(t);
					right.add(tSirina);
					right.add(tVisina);
					// right.add(cActive);
					right.add(transparentnost);
					CheckboxGroup c = new CheckboxGroup();
					Checkbox c1 = new Checkbox("Ime", c, true), c2 = new Checkbox("Sirina, duzina", c, false);

					left.add(c1);
					left.add(c2);
					left.add(dugme);

					c1.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							t.setEnabled(true);
							tSirina.setEnabled(false);
							tVisina.setEnabled(false);
							// cActive.setEnabled(true);
							transparentnost.setEnabled(true);
						}
					});
					c2.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							t.setEnabled(false);
							tSirina.setEnabled(true);
							tVisina.setEnabled(true);
							// cActive.setEnabled(false);
							transparentnost.setEnabled(false);
						}
					});

					dugme.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							if (c1.getState())
								scena.createImage(t.getText(), true, Integer.parseInt(transparentnost.getText()));
							else
								scena.createImageWH(Integer.parseInt(tVisina.getText()),
										Integer.parseInt(tVisina.getText()));
						}
					});

					frame.add(left, BorderLayout.WEST);
					frame.add(right, BorderLayout.CENTER);
				} else {
					Label l = new Label("Slika je vec ucitana");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		ispisiSliku.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Ispisi Sliku");
				frame.setBounds(300, 300, 500, 300);
				TextField t = new TextField("Ime fajla");
				Panel p = new Panel();

				Button dugme = new Button("Ispis");
				t.setEnabled(true);
				dugme.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String str=t.getText();
						if (str.substring(str.length() - 3).equals("bmp") || str.substring(str.length() - 3).equals("pam")) {
							IspisSlike i=new IspisSlike(scena,str);
							i.start();
						}else {
							frame.dispose();
						}
					}

				});
				p.add(t);
				p.add(dugme);
				frame.add(p);
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		sacuvajProjekat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Sacuvaj projekat");
				frame.setBounds(300, 300, 500, 300);

				TextField layer = new TextField("Ime fajla(.xml)", 20);
				Panel p = new Panel(new GridLayout(4, 1, 5, 5));
				Button dugme = new Button("Sacuvaj");
				p.add(layer);
				p.add(dugme);

				dugme.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!layer.getText().substring(layer.getText().length() - 3).equals("xml"))
							frame.dispose();

						Sacuvaj u = new Sacuvaj(scena, layer.getText());
						u.start();
					}
				});
				frame.add(p);
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		ucitajProjekat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Ucitaj projekat");
				frame.setBounds(300, 300, 500, 300);

				TextField layer = new TextField("Ime fajla(.xml)", 20);
				Panel p = new Panel(new GridLayout(4, 1, 5, 5));
				Button dugme = new Button("Ucitaj");
				p.add(layer);
				p.add(dugme);

				dugme.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (!layer.getText().substring(layer.getText().length() - 3).equals("xml"))
							frame.dispose();

						Ucitaj u = new Ucitaj(scena, layer.getText());
						u.start();

						System.out.print("fds");
					}
				});
				frame.add(p);
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		dodajSloj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Dodaj Sloj");
				frame.setBounds(300, 300, 500, 300);
				Checkbox cActive = new Checkbox("Aktina", true);
				TextField layer = new TextField("Ime fajla", 20),
						transparency = new TextField("Prozirnost 0-100[100-neprozirno]", 20);
				Panel p = new Panel(new GridLayout(4, 1, 5, 5));
				Button dugme = new Button("Dodaj");
				p.add(layer);
				p.add(cActive);
				p.add(transparency);
				p.add(dugme);

				dugme.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						scena.addLayer(layer.getText(), cActive.getState(), Integer.parseInt(transparency.getText()));
					}
				});
				frame.add(p);
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		obrisiSloj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Obrisi Sloj");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					if (scena.getImage().getLayers().size() > 0) {
						Panel p = new Panel();
						List lista = new List(5, false);
						Button dugme = new Button("Obrisi");
						for (int i = 0; i < scena.getImage().getLayers().size(); i++) {
							lista.add(scena.getImage().getLayers().get(i).getString());
						}
						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.obrisi(lista.getSelectedIndex());
							}
						});
						p.add(lista);
						p.add(dugme);
						frame.add(p);
					} else {
						Label l = new Label("Nijedan sloj nije ucitan!");
						frame.add(l);
					}
				} else {
					Label l = new Label("Slika nije ucitana!");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		aktivnostSloja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Aktivnost Sloja");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					if (scena.getImage().getLayers().size() > 0) {
						Panel p = new Panel(new GridLayout(3, 1, 5, 5));
						Label l = new Label("odaberi one kojima zelis da promenis stanje");
						List lista = new List(5, false);
						Button dugme = new Button("Promeni aktivnost");
						for (int i = 0; i < scena.getImage().getLayers().size(); i++) {
							lista.add(scena.getImage().getLayers().get(i).getString() + " "
									+ scena.getImage().getLayers().get(i).getActive());
						}
						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.changeActive(lista.getSelectedIndex());
							}
						});
						p.add(l);
						p.add(lista);
						p.add(dugme);
						frame.add(p);
					} else {
						Label l = new Label("Nijedan sloj nije ucitan!");
						frame.add(l);
					}
				} else {
					Label l = new Label("Slika nije ucitana!");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		transparentnostSloja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Promeni transparentost sloja");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					if (scena.getImage().getLayers().size() > 0) {
						TextField t = new TextField("unesi novu transp.", 20);
						Panel p = new Panel();
						List lista = new List(5, false);
						Button dugme = new Button("Promeni transparentnost");
						for (int i = 0; i < scena.getImage().getLayers().size(); i++) {
							lista.add(scena.getImage().getLayers().get(i).getString() + " "
									+ scena.getImage().getLayers().get(i).getTransparency());
						}
						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.changeTransparency(lista.getSelectedIndex(), Integer.parseInt(t.getText()));
								frame.dispose();
							}
						});
						p.add(t);
						p.add(lista);
						p.add(dugme);
						frame.add(p);
					} else {
						Label l = new Label("Nijedan sloj nije ucitan!");
						frame.add(l);
					}
				} else {
					Label l = new Label("Slika nije ucitana!");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		dodajSelekciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Dodaj Operaciju");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					Panel p = new Panel(new GridLayout(3, 1, 5, 5));
					Panel down = new Panel(new GridLayout(1, 2, 5, 5));
					Button kreni = new Button("Kreni"), kraj = new Button("Kraj");
					TextField tIme = new TextField("Ime selekcije");
					down.add(kreni);
					down.add(kraj);
					kreni.setEnabled(true);
					kraj.setEnabled(false);
					Checkbox cActive = new Checkbox("Aktina", true);

					kreni.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							scena.newNiz();
							scena.selectionGet(true);
							kreni.setEnabled(false);
							kraj.setEnabled(true);
						}
					});

					kraj.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							scena.getImage()
									.addSelection(new Selection(tIme.getText(), cActive.getState(), scena.getNiz()));
							scena.clearNiz();
							scena.selectionGet(false);
							kreni.setEnabled(true);
							kraj.setEnabled(false);
							frame.dispose();
						}
					});
					p.add(tIme);
					p.add(cActive);
					p.add(down);
					frame.add(p);
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);

			}
		});

		obrisiSelekciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Obrisi Selekciju");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					Label naslov = new Label("Ime selekcije koju brisete"); /// izlistaj sve selekcije
					Panel p = new Panel();
					if (scena.getImage().getSelections().size() > 0) {
						List lista = new List(5, false);

						Map<String, Selection> map = scena.getImage().getSelections();
						for (Map.Entry<String, Selection> pom : map.entrySet()) {
							lista.add(pom.getKey());
						}

						Button dugme = new Button("Obrisi");
						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.deleteSelection(lista.getSelectedItem());
							}

						});
						p.add(naslov, BorderLayout.NORTH);
						p.add(lista);
						p.add(dugme);
						frame.add(p);
					} else {
						Label naslov2 = new Label("Nijedan selekcija nije ucitana");
						frame.add(naslov2, BorderLayout.CENTER);
					}
				} else {
					Label naslov2 = new Label("Slika nije ucitana");
					frame.add(naslov2, BorderLayout.CENTER);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		prikaziSelekcije.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Prikazi selekcije");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					if (scena.getImage().getSelections().size() > 0) {
						Label t = new Label("Ime selekcije");
						Panel p = new Panel();
						List lista = new List(5, false);

						Map<String, Selection> map = scena.getImage().getSelections();
						for (Map.Entry<String, Selection> pom : map.entrySet()) {
							lista.add(pom.getKey());
						}
						Button dugme = new Button("Prikazi");
						t.setEnabled(true);
						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.showSelection(lista.getSelectedItem());
							}

						});
						p.add(t);
						p.add(lista);
						p.add(dugme);
						frame.add(p);
					} else {
						Label t = new Label("Nijedan selekcija nije ucitana");
						frame.add(t);
					}
				} else {
					Label t = new Label("Slika nije ucitana!");
					frame.add(t);
				}

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						scena.noShowSelection();
						frame.dispose();
					}
				});
				frame.setVisible(true);
			}
		});

		aktivnostSelekcije.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Aktivnost Selekcija");
				frame.setBounds(300, 300, 500, 300);

				if (scena.getImage() != null) {
					if (scena.getImage().getSelections().size() > 0) {
						Panel p = new Panel(new GridLayout(4, 1, 5, 5));
						Map<String, Selection> map = scena.getImage().getSelections();
						Button dugme = new Button("Promeni");
						Label l1 = new Label("Trenutne selekcije i njihove aktivnosti");
						List lista1 = new List(5, false);
						for (Map.Entry<String, Selection> pom : map.entrySet()) {
							lista1.add(pom.getKey() + " " + pom.getValue().getActive());
						}
						Label l2 = new Label("Odaberi selekcije kojima zelite da promenite aktivnost");
						List lista2 = new List(5, true);

						for (Map.Entry<String, Selection> pom : map.entrySet()) {
							lista2.add(pom.getKey());
						}

						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								scena.selectionActive(lista2.getSelectedItems());
							}

						});

						p.add(l1);
						p.add(lista1);
						p.add(l2);
						p.add(lista2);
						p.add(dugme);
						frame.add(p);

					} else {
						Label l = new Label("Nijedna selekcija nije ucitana");
						frame.add(l);
					}
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		obojiSelekciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Oboji Selekcija");
				frame.setBounds(300, 300, 500, 300);

				if (scena.getImage() != null) {
					if (scena.getImage().getSelections().size() > 0) {
						Panel p = new Panel(new GridLayout(7, 1, 5, 5));
						Map<String, Selection> map = scena.getImage().getSelections();
						Button dugme = new Button("Oboji");

						Label l = new Label("Odaberi selekciju koju zelite da obojite");
						List lista = new List(5, true);

						for (Map.Entry<String, Selection> pom : map.entrySet()) {
							lista.add(pom.getKey());
						}
						TextField red = new TextField("Red");
						TextField green = new TextField("Green");
						TextField blue = new TextField("Blue");
						TextField alpha = new TextField("Alpha");

						dugme.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								Oboji u = new Oboji(scena, lista.getSelectedItem(), Integer.parseInt(red.getText()),
										Integer.parseInt(green.getText()), Integer.parseInt(blue.getText()),
										Integer.parseInt(alpha.getText()));
								u.start();

								System.out.print("fds");
							}

						});

						p.add(l);
						p.add(lista);
						p.add(red);
						p.add(green);
						p.add(blue);
						p.add(alpha);
						p.add(dugme);
						frame.add(p);

					} else {
						Label l = new Label("Nijedna selekcija nije ucitana");
						frame.add(l);
					}
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		dodajOperaciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Dodaj Operaciju");
				frame.setBounds(300, 300, 600, 900);
				if (scena.getImage() != null) {

					CheckboxGroup c = new CheckboxGroup();
					Checkbox cBasic = new Checkbox("Baic", c, true), cComposite = new Checkbox("Composite", c, false);
					Button leftDugme = new Button("Dodaj"), rightDodaj = new Button("Kreiraj sa gore datim imenom"),
							rightKraj = new Button("Kraj");
					Panel left = new Panel(new GridLayout(5, 1, 5, 5)), right = new Panel(new GridLayout(6, 1, 5, 5));
					List basic = new List(7, false), composite = new List(7, false);
					TextField tLeft = new TextField("Opciono dodati broj za operaciju"),
							tRight = new TextField("Opciono dodati broj za operaciju"),
							tRightName = new TextField("Ime kompozitne");

					basic.setEnabled(true);
					tLeft.setEnabled(true);
					leftDugme.setEnabled(true);

					composite.setEnabled(false);
					tRight.setEnabled(false);
					rightDodaj.setEnabled(false);
					rightKraj.setEnabled(false);

					basic.add("Abs");
					basic.add("Add");
					basic.add("Div");
					basic.add("InvDiv");
					basic.add("InvSub");
					basic.add("Log");
					basic.add("Max");
					basic.add("Min");
					basic.add("Mul");
					basic.add("Power");
					basic.add("Sub");

					composite.add("Abs");
					composite.add("Add");
					composite.add("Div");
					composite.add("InvDiv");
					composite.add("InvSub");
					composite.add("Log");
					composite.add("Max");
					composite.add("Min");
					composite.add("Mul");
					composite.add("Power");
					composite.add("Sub");
					Map<String, Operation> map = scena.getImage().getOperations();
					for (Map.Entry<String, Operation> pom : map.entrySet()) {
						composite.add(pom.getKey());
					}

					cBasic.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							basic.setEnabled(true);
							tLeft.setEnabled(true);
							leftDugme.setEnabled(true);

							composite.setEnabled(false);
							tRight.setEnabled(false);
							rightDodaj.setEnabled(false);
							rightKraj.setEnabled(false);
						}
					});

					cComposite.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							basic.setEnabled(false);
							tLeft.setEnabled(false);
							leftDugme.setEnabled(false);

							composite.setEnabled(true);
							tRight.setEnabled(true);
							rightDodaj.setEnabled(true);
							rightKraj.setEnabled(true);
						}
					});

					leftDugme.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							scena.addOperation(basic.getSelectedItem(), tLeft.getText());
						}

					});

					rightDodaj.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (rightDodaj.getLabel() == "Kreiraj sa gore datim imenom") {
								rightDodaj.setLabel("Dodaj Operaciju");
								compositeOp = new CompositeOperation(tRightName.getText());
							} else {
								Operation o = scena.getImage().getOperations().get(composite.getSelectedItem());
								if (o == null) {
									o = scena.returnOperation(composite.getSelectedItem(), tRight.getText());
								}
								compositeOp.addOperation(o);
							}
						}

					});

					rightKraj.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							scena.addCompositeOperation(compositeOp);
							compositeOp = null;
							frame.dispose();
						}

					});

					left.add(cBasic);
					left.add(basic);
					left.add(tLeft);
					left.add(leftDugme);

					right.add(cComposite);
					right.add(tRightName);
					right.add(composite);
					right.add(tRight);
					right.add(rightDodaj);
					right.add(rightKraj);
					frame.add(left, BorderLayout.WEST);
					frame.add(right, BorderLayout.EAST);
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}
				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		obrisiOperaciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Obrisi Operaciju");
				frame.setBounds(300, 300, 500, 300);
				if (scena.getImage() != null) {
					Label l = new Label("Obrisi operaciju koju brisete");
					Panel p = new Panel();
					List lista = new List(5, false);
					Button dugme = new Button("Obrisi");
					Map<String, Operation> map = scena.getImage().getOperations();
					for (Map.Entry<String, Operation> pom : map.entrySet()) {
						lista.add(pom.getKey());
					}

					dugme.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							scena.deleteOperation(lista.getSelectedItem());
						}

					});

					p.add(l, BorderLayout.NORTH);
					p.add(lista);
					p.add(dugme);
					frame.add(p);

				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}

				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		izvrsiOperaciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Izvrsi Operaciju");
				frame.setBounds(300, 300, 500, 300);

				if (scena.getImage() != null) {
					Button dugme = new Button("Izvrisi");
					List lista = new List(5, false);
					Map<String, Operation> map = scena.getImage().getOperations();
					for (Map.Entry<String, Operation> pom : map.entrySet()) {
						lista.add(pom.getKey());
					}

					dugme.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Dooperation d = new Dooperation(scena, lista.getSelectedItem());
							
							d.start();
							/*
							String c = "C:\\Users\\Nikola\\Desktop\\2.godina\\4SEMESTAR\\POOP\\POOPC++\\projekatc++\\POOP\\",
									java = "C:\\Users\\Nikola\\Desktop\\photoshop\\Photoshop\\";

							scena.saveXML(java + "projekat.xml");
							scena.saveComposite(scena.getImage().getOperations().get(lista.getSelectedItem()),
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
							*/						
						}
					});
					Panel p = new Panel(new GridLayout(2, 1, 5, 5));
					p.add(lista);
					p.add(dugme);
					frame.add(p);
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}

				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

		sacuvajKompozitnu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("Sacuvaj kompozitnu");
				frame.setBounds(300, 300, 500, 300);

				if (scena.getImage() != null) {
					Button dugme = new Button("Sacuvaj");
					List lista = new List(5, false);
					TextField t = new TextField("Unesite ime fajla(.fun)");
					Map<String, Operation> map = scena.getImage().getOperations();
					for (Map.Entry<String, Operation> pom : map.entrySet()) {
						lista.add(pom.getKey());
					}

					dugme.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if ((scena.saveComposite(scena.getImage().getOperations().get(lista.getSelectedItem()),
									t.getText())) == 0) {
								Label l = new Label("Los ulazni fajl");
								frame.add(l);
								frame.dispose();
							}
						}

					});
					Panel p = new Panel(new GridLayout(2, 1, 5, 5));
					p.add(t);
					p.add(lista);
					p.add(dugme);
					frame.add(p);
				} else {
					Label l = new Label("Slika nije ucitana");
					frame.add(l);
				}

				WindowListener osl = new Osluskivac();
				frame.addWindowListener(osl);
				frame.setVisible(true);
			}
		});

	}

}
