package phoebe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Paladrawin extends JPanel {
	private Map map = null;
	private JFrame frame;
	private Player player0;
	private Player player1;
	
	public Paladrawin(Map map, Player player0, Player player1) {
		super();
		frame = new JFrame("Phoebe");
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		this.map = map;
		this.player0 = player0;
		this.player1 = player1;
	}

	public Paladrawin() {
		super();
	}
	
	public void reDraw() {
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = 20;
		if (map != null) {			
			for (int i = 0; i < map.getSize()[0]; i++) {
				for (int j = 0; j < map.getSize()[1]; j++) {

					Cell c = map.getCell(i, j);
					String type = c.printCell();

					if (type.equals("0")) {
						g.setColor(Color.black);
					} else if (type.equals("1")) {
						g.setColor(Color.white);
					} else if (type.equals("2")) {
						g.setColor(Color.yellow);
					} else if (type.equals("3")) {
						g.setColor(Color.green);
					} else if (type.equals("4")) {
						g.setColor(Color.red);
					} else if (type.equals("5")) {
						g.setColor(Color.blue);
					} else if (type.equals("6")) {
						g.setColor(Color.pink);
					}
					g.fillRect(j + j * size, i + i * size, size, size);
				}
			}
			g.drawString(player0.toString(), 20 * size + 20, 20 + 20 * size);
			g.drawString(player1.toString(), 20 * size + 20, 40 + 20 * size);
		}
		
		/*if (players.size() > 0) {
		g.setColor(Color.red);
		g.drawString("Player 0: ",400,  350);
		g.drawString("----------------",400,  360);
		g.drawString("Speed: " + players.get(0).getSpeed(), 400,  375);
		g.drawString("CanChangeDirection: " + players.get(0).isCanChangeDirection(),400,  400);
		g.drawString("Distance: " + players.get(0).getDistance(), 400,  425);
		g.setColor(Color.blue);
		g.drawString("Player 1: ", 400,  450);
		g.drawString("----------------",400,  460);
		g.drawString("Speed: " + players.get(1).getSpeed(), 400,  475);
		g.drawString("CanChangeDirection: " + players.get(1).isCanChangeDirection(), 400,  500);
		g.drawString("Distance: " + players.get(1).getDistance(), 400,  525);
		}*/
	}
	
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
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
		frame.addKeyListener(l);
	}
}
