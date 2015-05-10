package phoebe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A játéktér megjelenítéséért felelős osztály. Megjeleníti a pályát a map állapotától függően.
 *
 */
public class Paladrawin extends JPanel {
	private Map map = null;
	private JFrame frame;
	private Player player0;
	private Player player1;
	
	/**
	 * Az osztály konstruktora.
	 * @param map A térkép, amelyet meg kell jeleníteni.
	 * @param player0 Az első játékos.
	 * @param player1 A második játékos.
	 */
	public Paladrawin(Map map, Player player0, Player player1) {
		super();
		frame = new JFrame("Phoebe");
		frame.setSize(400, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);
		this.map = map;
		this.player0 = player0;
		this.player1 = player1;
	}

	public Paladrawin() {
		super();
	}
	
	/**
	 * Az újrarajzolásésrt felelős metódus. Akkor hívjuk meg, ha a térkép állapota változott.
	 */
	public void reDraw() {
		this.repaint();
	}
	
	/**
	 * A térkép kirajzolásáért felelős metódus.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = 20;
		if (map != null) {			
			for (int i = 0; i < map.getSize()[0]; i++) {
				for (int j = 0; j < map.getSize()[1]; j++) {

					Cell c = map.getCell(i, j);
					String type = c.printCell();

					if (type.equals("0")) { // Érvénytelen cella
						g.setColor(Color.black);
					} else if (type.equals("1")) { // Érvényes cella
						g.setColor(Color.white);
					} else if (type.equals("2")) { // Olajfoltos cella
						g.setColor(Color.yellow);
					} else if (type.equals("3")) { // Ragacsfoltos cella
						g.setColor(Color.green);
					} else if (type.equals("4")) { // Első játékos cellája
						g.setColor(Color.red);
					} else if (type.equals("5")) { // Második játékos cellája
						g.setColor(Color.blue);
					} else if (type.equals("6")) { // Cellák, amelyeken kisrobotok tartózkodnak
						g.setColor(Color.pink);
					}
					g.fillRect(j + j * size, i + i * size, size, size);
				}
			}
			// A játékosok adatainak kiírása
			g.setColor(Color.RED);
			g.drawString(player0.toString(), 20, 20 + map.getSize()[1] * (size+8));
			g.setColor(Color.BLUE);
			g.drawString(player1.toString(), 20, 40 + map.getSize()[1] * (size+8));
		}
	}
	
	/**
	 * Játék végén a dialógusablakot feldobó metódus.
	 * @param output Dialógusablakban kiírandó szöveg.
	 * @return 0, ha nem léptünk ki.
	 */
	public int showDialog(String output) {

		Object [] stringArray = {"Restart", "Quit"};
		
		Icon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(new File("balage.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int result = JOptionPane.showOptionDialog(this, output, "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, stringArray, stringArray[0]);
		
		switch (result) {
		case JOptionPane.YES_OPTION: return JOptionPane.YES_OPTION;
		case JOptionPane.NO_OPTION: System.exit(0);
		default: 
		}
		
		return 0;
	}
	
	/**
	 * Beállítja melyik map tartalmát kell kirajzolnia.
	 * @param map A térkép, amelynek tartalmát szeretnénk kirajzolni.
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * KeyListenerek hozzáadásáért felelős metódus.
	 */
	@Override
	public synchronized void addKeyListener(KeyListener keyListener) {
		super.addKeyListener(keyListener);
		frame.addKeyListener(keyListener);
	}
}
