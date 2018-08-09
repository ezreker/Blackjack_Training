package gameboard;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import blackjack.card;
import neuralnet.network;

public class board extends JFrame{
	private JPanel rootPane;
	private JPanel dealerInfoPanel;
	private JPanel dealerCardsPanel;
	private JPanel playerCardsPanel;
	private JPanel playerInfoPanel;
	private JLabel dealerTitle;
	private JLabel playerTitle;
	private JLabel playerInfo;
	private JButton playAgain;
	private BufferedImage cardSheet;
	private ImageIcon[][] sprites;
	private int cashvalue;
	private boolean newGame;
	
	public board() throws IOException{
		super("Blackjack Game");
		setBounds(100,100,550,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		java.awt.Container con = this.getContentPane();
		
		rootPane = new JPanel();
		dealerInfoPanel = new JPanel();
		dealerCardsPanel = new JPanel();
		playerCardsPanel = new JPanel();
		playerInfoPanel = new JPanel();
		
		rootPane.setBackground(new Color(07,63,24));
		rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));
		con.add(rootPane);

		dealerInfoPanel.setOpaque(false);
		dealerCardsPanel.setOpaque(false);
		playerCardsPanel.setOpaque(false);
		playerInfoPanel.setOpaque(false);
		rootPane.add(dealerInfoPanel);
		dealerInfoPanel.setLayout(new BorderLayout());
		rootPane.add(dealerCardsPanel);
		dealerCardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dealerCardsPanel.setPreferredSize(new Dimension(100,550));
		rootPane.add(playerCardsPanel);
		playerCardsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		playerCardsPanel.setPreferredSize(new Dimension(100,550));
		rootPane.add(playerInfoPanel);
		playerInfoPanel.setLayout(new BorderLayout());
		
		dealerTitle =  new JLabel("<html><font color='white'>Dealer</font></html>");
		dealerTitle.setBorder(new EmptyBorder(10,10,10,10));
		dealerInfoPanel.add(dealerTitle, BorderLayout.PAGE_END);
		
		playerTitle = new JLabel("<html><font color='white'>Player</font></html>");
		playerTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		playerTitle.setBorder(new EmptyBorder(10,10,10,10));
		playerInfoPanel.add(playerTitle, BorderLayout.NORTH);
		
		cashvalue = 100;
		playerInfo = new JLabel("<htmL><font color='white'>Starting cash: $100<br/>Current cash: $" + cashvalue + "</font></html");
		playerInfo.setBorder(new EmptyBorder(10,10,10,10));
		playerInfoPanel.add(playerInfo, BorderLayout.CENTER);
		
		playAgain = new JButton("Play Again");
		playAgain.setOpaque(false);
		playAgain.setEnabled(false);
		playerInfoPanel.add(playAgain, BorderLayout.SOUTH);
		
		cardSheet = ImageIO.read(new File("resources/cards.png"));
		sprites = new ImageIcon[4][13];
		for(int i=0;i<4;i++) {
			for(int j=0;j<13;j++) {
				sprites[i][j] = new ImageIcon(cardSheet.getSubimage(73*j+1, 98*i+1, 72, 96));
			}
		}
		
		setVisible(true);
	}
	
	public void addPlayerCard(card c) throws InterruptedException {
		JLabel newCard =
				new JLabel(sprites[c.getSuitVal()][c.getFaceVal()-1]);
		
		playerCardsPanel.add(newCard);
		revalidate();
		repaint();
		TimeUnit.MILLISECONDS.sleep(500);
	}
	
	public void addDealerCard(card c) throws InterruptedException {
		JLabel newCard =
				new JLabel(sprites[c.getSuitVal()][c.getFaceVal()-1]);
		
		dealerCardsPanel.add(newCard);
		revalidate();
		repaint();
		TimeUnit.MILLISECONDS.sleep(500);
	}
	
	public void shuffleDeck() throws InterruptedException {
		dealerCardsPanel.removeAll();
		playerCardsPanel.removeAll();
		revalidate();
		repaint();
		TimeUnit.MILLISECONDS.sleep(500);
	}
	
	public void changeCash(int amount) {
		cashvalue += amount;
		playerInfo.setText("<htmL><font color='white'>Starting cash: $100<br/>Current cash: $" + cashvalue + "</font></html");
	}
	
	public void checkNewGame() throws InterruptedException {
		newGame = false;
		playAgain.setEnabled(true);
		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame = true;
			}
		});
		while(!newGame) {
			TimeUnit.MILLISECONDS.sleep(200);
		}
		return;
	}
	
	
}
