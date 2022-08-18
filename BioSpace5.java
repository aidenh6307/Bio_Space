// Aiden Huang
// 4-11-22
// BioSpace5.java
/* The player can go around a 2D ship doing biology questions which 
 * will be documented as tasks henceforth. Once a task is completed, a 
 * taskbar will increase, and once said taskbar is full, the player wins. 
 * If a task was incorrectly completed, the user will be able to redo the 
 * questions as many times as they would like. At the end of the game, 
 * there will be a screen where they can see each question and why the 
 * answer is correct. Scattered throughout the 2D ship, henceforth documented 
 * as the map, there will be 9 images of a cartoon character, henceforth 
 * documented as crewmates. Every 30 seconds, one of the crewmates will be 
 * rotated 90 degrees and they will be “killed”. If the user navigates their 
 * way to the dead crewmate, they can press the body and the user has one opportunity
 *  to answer a challenge correctly. If they answer the question correctly, 
 * their point total increases by one. The user can set the point threshold needed to win
 *  at whatever number they choose. The score is calculated by finding the number of
 *  seconds taken to win and multiplying by 5.
*/
import java.awt.Insets;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.FileReader;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

/// TODO: Add ToolTextTips

public class BioSpace5 implements WindowListener
{	
	private PrintWriter writer; // PrintWriter that writes to the file
	private String fileName; // name of the file to write to
	
	public BioSpace5()
	{
		writer = null;
		fileName = new String("");
	}
	
	public static void main(String [] args)
	{
		BioSpace5 bs = new BioSpace5();
		bs.run();
	}
	
	public void run()
	{
		JFrame frame = new JFrame("BioSpace5");
		frame.addWindowListener(this);
		frame.setSize( 1366, 735);				
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); 
		frame.setLocation(0,0);
		frame.setResizable(true);
		BioSpace5Holder bs2h = new BioSpace5Holder(); 		
		frame.getContentPane().add( bs2h );		
		frame.setVisible(true);		
	}
	
	public void windowClosed(WindowEvent evt){}
	
	public void windowActivated (WindowEvent evt) {}    
	public void windowDeactivated (WindowEvent evt) {}
	public void windowIconified (WindowEvent evt) {}   
	public void windowDeiconified (WindowEvent evt) {}   
	public void windowOpened (WindowEvent evt) {} 
	
	// This method calls the methods to return the values to default and changes 
	// the file names.
	public void windowClosing (WindowEvent evt)
	{
		fileName = new String("maps.txt");
		findFile();
		writeFile();
		fileName = new String("settings.txt");
		findFile();
		writeFile();
		fileName = new String("account.txt");
		findFile();
		writeFile();
	} 
 
	// This method tries to create the PrintWriter
	public void findFile()
	{
		File file = new File(fileName);
		try
		{
			writer = new PrintWriter(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.\n\n", fileName);
			System.exit(1);
		}
	}
	
	// This method returns the settings to their default ones when the game is closed
	public void writeFile()
	{
		if(fileName.equals("maps.txt"))
		{
			writer.println("skeld.jpg");
		}
		
		else if(fileName.equals("settings.txt"))
		{
			writer.println("15 5 1");
		}
		
		else if(fileName.equals("account.txt"))
		{
			writer.println("Guest player");
		}
		writer.close();
	}
}

// this panel holds the main cards/panel 
class BioSpace5Holder extends JPanel
{
	private Font font; //Font so that every method can use it
	private JMenuItem [] menuItems, menuItems2, menuItems3, menuItems4
		, menuItems6, menuItems7, menuItems8; //JMenuItem arrays for each game panel to use to navigate
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	/*
	 * Constructor that creates the JMenus and also creates the cardlayout that stores
	 * the game panels.
	 */
	public BioSpace5Holder()
	{
		CardLayout cards = new CardLayout();
		setLayout(cards);
		font = new Font("Serif" , Font.PLAIN, 50);
		
		yellow = new Color(255, 221, 0);
		blue = new Color(0, 87, 183);
		
		menuItems = new JMenuItem[7];
		menuItems2 = new JMenuItem[7];
		menuItems3 = new JMenuItem[7];
		menuItems4 = new JMenuItem[7];
		menuItems6 = new JMenuItem[7];
		menuItems7 = new JMenuItem[7];
		menuItems8 = new JMenuItem[7];
		
		JMenuBar bar = makeOptionMenuBar();
		JMenuBar bar2 = makeOptionMenuBar2();
		JMenuBar bar3 = makeOptionMenuBar3();
		JMenuBar bar4 = makeOptionMenuBar4();
		JMenuBar bar6 = makeOptionMenuBar6();
		JMenuBar bar7 = makeOptionMenuBar7();
		JMenuBar bar8 = makeOptionMenuBar8();
		
		Information info = new Information();
		
		ScorePanel scp = new ScorePanel(this, cards, bar3, menuItems3, info, blue, yellow);
		StartPanel sp = new StartPanel(this, cards, blue, yellow);
		DirectPanel dp = new DirectPanel(this, cards, bar2, menuItems2, scp, blue, yellow);
		OptionPanel op = new OptionPanel(this, cards, bar, menuItems, scp, blue, yellow);
		LoginPanel lp = new LoginPanel(this, cards, bar4, menuItems4, scp, blue, yellow, info);
		SetPanel setp = new SetPanel(this, cards, info, bar6, menuItems6, scp, blue, yellow);
		CreatePanel cp = new CreatePanel(this, cards, bar7, menuItems7, scp, blue, yellow, info);
		GamePanel gp = new GamePanel(this, cards, info, bar8, menuItems8, scp, blue, yellow);
		
		add(sp, "Start");
		add(op, "Option");
		add(dp, "Direct");
		add(scp, "Scoreboard");
		add(lp, "Login");
		add(cp, "Create");
		add(gp, "Game");
		add(setp, "Setting");
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		options.setFont(font);
		
		menuItems[0] = new JMenuItem("Loading Screen:");
		menuItems[1] = new JMenuItem("Scoreboard:");
		menuItems[2] = new JMenuItem("Start Game:");
		menuItems[3] = new JMenuItem("Directions:");
		menuItems[4] = new JMenuItem("Login:");
		menuItems[5] = new JMenuItem(" -Create:");
		menuItems[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar2()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems2[0] = new JMenuItem("Loading Screen:");
		menuItems2[1] = new JMenuItem("Scoreboard:");
		menuItems2[2] = new JMenuItem("Start Game:");
		menuItems2[3] = new JMenuItem("Directions:");
		menuItems2[4] = new JMenuItem("Login:");
		menuItems2[5] = new JMenuItem(" -Create:");
		menuItems2[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems2[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar3()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems3[0] = new JMenuItem("Loading Screen:");
		menuItems3[1] = new JMenuItem("Scoreboard:");
		menuItems3[2] = new JMenuItem("Start Game:");
		menuItems3[3] = new JMenuItem("Directions:");
		menuItems3[4] = new JMenuItem("Login:");
		menuItems3[5] = new JMenuItem(" -Create:");
		menuItems3[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems3[i]);
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar4()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems4[0] = new JMenuItem("Loading Screen:");
		menuItems4[1] = new JMenuItem("Scoreboard:");
		menuItems4[2] = new JMenuItem("Start Game:");
		menuItems4[3] = new JMenuItem("Directions:");
		menuItems4[4] = new JMenuItem("Login:");
		menuItems4[5] = new JMenuItem(" -Create:");
		menuItems4[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems4[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar6()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
			
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems6[0] = new JMenuItem("Loading Screen:");
		menuItems6[1] = new JMenuItem("Scoreboard:");
		menuItems6[2] = new JMenuItem("Start Game:");
		menuItems6[3] = new JMenuItem("Directions:");
		menuItems6[4] = new JMenuItem("Login:");
		menuItems6[5] = new JMenuItem(" -Create:");
		menuItems6[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems6[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar7()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems7[0] = new JMenuItem("Loading Screen:");
		menuItems7[1] = new JMenuItem("Scoreboard:");
		menuItems7[2] = new JMenuItem("Start Game:");
		menuItems7[3] = new JMenuItem("Directions:");
		menuItems7[4] = new JMenuItem("Login:");
		menuItems7[5] = new JMenuItem(" -Create:");
		menuItems7[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems7[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
	
	// This method creates the JMenuBar and creates the menuItems and adds it to the JMenuBar
	public JMenuBar makeOptionMenuBar8()
 	{
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Bio-Space");
		
		bar.setBackground(yellow);
		options.setForeground(blue);
		
		options.setFont(font);
		
		menuItems8[0] = new JMenuItem("Loading Screen:");
		menuItems8[1] = new JMenuItem("Scoreboard:");
		menuItems8[2] = new JMenuItem("Start Game:");
		menuItems8[3] = new JMenuItem("Directions:");
		menuItems8[4] = new JMenuItem("Login:");
		menuItems8[5] = new JMenuItem(" -Create:");
		menuItems8[6] = new JMenuItem("Quit:");
		
		for(int i = 0; i < 7; i++)
		{
			options.add( menuItems8[i] );
		}
			
		bar.add( options);
	
		return bar;
	}
}

class StartPanel extends JPanel
{
	private BioSpace5Holder panelCards; // needed for CardLayout to function
	private String imageFileName;
	private CardLayout cards; // needed for CardLayout to function
	private Font font; //font for the components to use
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	private Image introScreen;
	/*
	 *This constructor creates the StartPanel which has a JLabel and a JButton
	 * which takes you to the OptionPanel.
	 */
	public StartPanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		setBackground(blue);
		panelCards = panelCardsIn;
		cards = cardsIn;
		setLayout(null);
		font = new Font("Serif", Font.PLAIN, 50);
		imageFileName = new String("introScreen.jpg");
		
		JButton start = new JButton("SHALL WE PLAY A GAME?");
		JButton exit  = new JButton("Exit");

		start.setBounds(283,350,800,200);
		exit.setBounds(283, 575, 800, 100);
		start.setHorizontalAlignment(JLabel.CENTER);
		
		start.setToolTipText("Click me to start the game");
		start.setBackground(yellow);
		start.setForeground(blue);
		start.setOpaque(true);
		start.setBorderPainted(false);
		
		exit.setHorizontalAlignment(JLabel.CENTER);
		
		exit.setToolTipText("Click me to exit the game");
		exit.setBackground(yellow);
		exit.setForeground(blue);
		exit.setOpaque(true);
		exit.setBorderPainted(false);
		
		ButtonHandler bh = new ButtonHandler();
		start.addActionListener(bh);
		exit.addActionListener(bh);
		
		createImage();
		repaint();
		start.setFont(font);
		exit.setFont(font);
		add(start);
		add(exit);
	}
	
	public void createImage()
	{
		File imageFile = new File(imageFileName);
		try
		{
			introScreen = ImageIO.read(imageFile);
			introScreen = introScreen.getScaledInstance(1366,735, java.awt.Image.SCALE_SMOOTH);
		}
			
		catch(IOException e)
		{
			System.err.println("\n\n" + imageFileName + " can't be found.\n\n");
			e.printStackTrace();
		}
	}
	
	//Handler class that makes the start button actually go to the OptionPanel
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("SHALL WE PLAY A GAME?"))
			{
				cards.show(panelCards, "Option");
			}
			
			if(command.equals("Exit"))
			{
				System.exit(1);
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(introScreen, 0, 0, this);
	}
}

class OptionPanel extends JPanel
{
	private BioSpace5Holder panelCards; // needed for CardLayout to function
	private CardLayout cards; //needed for CardLayout to function
	
	private Font font; //font for each component to use
	
	private JButton scores, start, direct; // JButtons that are used to navigate
	private JMenuBar bar; //JMenuBar for the navigation menu
	private JMenuItem [] menuItems;  //JMenuItems for the navigation menu
	
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use
	private ScorePanel scp; // Instance of ScorePanel to update the scoreboard
	/*
	 *This constructor creates the OptionPanel using a BorderLayout.
	 * It has 6 JButtons to go to other panels and also one JMenuBar for navigation
	 * betweeen the panels
	 */
	public OptionPanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, JMenuBar barIn, JMenuItem[] menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		scp = scpIn;
		setBackground(Color.GREEN);
		panelCards = panelCardsIn;
		cards = cardsIn;
		bar = barIn;
		menuItems = menuItemsIn;
		
		font = new Font("Serif", Font.PLAIN, 50);
		
		setLayout(new BorderLayout(5,5));
			
		OptionMenuHandler omh = new OptionMenuHandler();		
		
		for(int i = 0; i < menuItems.length; i++)
		{
			menuItems[i].addActionListener(omh);
		}
		
		makeButtons();
		
		MenuPanel mp = new MenuPanel();
		AccPanel ap = new AccPanel();
		
		add(mp, BorderLayout.NORTH);
		add(direct,BorderLayout.WEST);
		add(start,BorderLayout.CENTER);
		add(scores,BorderLayout.EAST);
		add(ap, BorderLayout.SOUTH);
 	}	
 	
 	// This class create the navigation menu with a JLabel to tell you what page you are on
 	class MenuPanel extends JPanel
 	{
		public MenuPanel()
		{
			setLayout(new BorderLayout());
			setBackground(blue);
			
			JLabel title = new JLabel("    Options:");
			title.setFont(font);
			title.setForeground(yellow);
			
			add(bar, BorderLayout.WEST);
			add(title, BorderLayout.CENTER);
		}
	}
	
	// This method creates the navigation buttons 
 	public void makeButtons()
 	{	
		scores = new JButton("Scoreboard");
		start = new JButton("Start New Game");
		direct = new JButton("How to Play");
		
		scores.setForeground(yellow);
		scores.setOpaque(true);
		scores.setBorderPainted(false);
		scores.setBackground(blue);
		scores.setFont(font);
		scores.setToolTipText("Click me to go to the scoreboard");
		
		start.setForeground(yellow);	
		start.setOpaque(true);
		start.setBorderPainted(false);
		start.setBackground(blue);
		start.setFont(font);
		start.setToolTipText("Click me to go to the settings");
		
		direct.setForeground(yellow);	
		direct.setOpaque(true);
		direct.setBorderPainted(false);
		direct.setBackground(blue);
		direct.setFont(font);
		direct.setToolTipText("Click me to go to the directions");
		
		ActionEventHandler aeh = new ActionEventHandler();
		
		direct.addActionListener(aeh);
		start.addActionListener(aeh);
		scores.addActionListener(aeh);		
			
	}
	
 	//Used for formatting the account panels and create the account related buttons
 	class AccPanel extends JPanel
 	{
		private JButton login, create;
		private Font font;
		
		public AccPanel()
		{
			GridLayout layout = new GridLayout(1,2);
			layout.setHgap(10);
			setLayout(layout);
			setBackground(Color.GREEN);
			
			font = new Font("Serif", Font.PLAIN, 50);
			
			login = new JButton("Login:");
			login.setFont(font);
			login.setForeground(yellow);
			login.setBorderPainted(false);
			login.setOpaque(true);
			login.setBackground(blue);
			login.setToolTipText("Click me to go to the login page");
			
			create = new JButton("Create:");
			create.setFont(font);
			create.setForeground(yellow);
			create.setBorderPainted(false);
			create.setOpaque(true); //setOpaque is from StackOverflow
			create.setBackground(blue);
			create.setToolTipText("Click me to go to the create page");
			
			login.setVerticalAlignment(JButton.CENTER);
			create.setVerticalAlignment(JButton.CENTER);
			
			AccEventHandler acch = new AccEventHandler();
			login.addActionListener(acch);
			create.addActionListener(acch);
			
			add(login);
			add(create);
		}
	}
	
	// This Handler class makes the JMenuBar actually navigate between panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	// Handler class that allows the panel JButtons to navigate between panels
	class ActionEventHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("How to Play"))
			{
				cards.show(panelCards,"Direct");
			}
			
			else if(command.equals("Start New Game"))
			{
				cards.show(panelCards,"Setting");
			}
			
			else if(command.equals("Scoreboard"))
			{
				cards.show(panelCards,"Scoreboard");
			}
		}
	}	

	// Handler class that allows the account JButtons to navigate between panels
	class AccEventHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals("Create:"))
			{
				cards.show(panelCards,"Create");
			}
		}
	}	
}

class DirectPanel extends JPanel
{
	private BioSpace5Holder panelCards; // needed for CardLayout
	private CardLayout cards; // needed for CardLayout
	
	private Font font; // font for components to use
	private JCheckBox confirm; // button to show the user that they understand the directions
	
	private BufferedReader reader; // BufferedReader that reads the instructions file
	private String infoFileName; // name of the instructions file
	
	private JMenuBar bar;  //JMenuBar for the navigation menu
	private JMenuItem [] menuItems; //JMenuItems for the navigation menu
	
	private JLabel directions; // JLabel that tells you what page you're one
	private JLabel empty, empty2; // JLabels used for formatting
	private JTextArea infoArea; // JTextArea that actually has the instructions
	private JScrollPane scroller; //JScrollPane that is used for scrolling through the TextArea
	
	private JLabel [] images; // Images used for instructing the user on what each thing is
	private String [] pictNames; // Names of the images
	private JTextArea [] pictLabels; // JTextAreas to explain what each thing does
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	private ScorePanel scp; // Instance of ScorePanel to update the scoreboard
	/*
	 *This constructor uses a flow layout in order to show a navigation JMenuBar
	 * , Instruction JTextArea with a JScrollPane and a JCheckBox to prove that the
	 * user understands the instructions.
	 */
	public DirectPanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, JMenuBar barIn, JMenuItem[] menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		panelCards = panelCardsIn;
		cards = cardsIn;
		bar = barIn;
		menuItems = menuItemsIn;
		scp = scpIn;
		
		images = new JLabel[3];
		pictLabels = new JTextArea[4];
		reader = null;
		infoFileName = new String("instructions.txt");
		pictNames = new String[]{"yellow.png", "deadyellow.png", "userGhost.png"};
		
		font = new Font("Serif", Font.PLAIN, 30);
		
		setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2,10,10));
		centerPanel.setBackground(blue);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(blue);
		
		OptionMenuHandler omh = new OptionMenuHandler();		
		
		for(int i = 0; i < menuItems.length; i++)
		{
			menuItems[i].addActionListener(omh);
		}
		
		makeDirections();
		
		menuPanel.add(bar,BorderLayout.WEST);
		menuPanel.add(directions, BorderLayout.CENTER);
		
		findInfoFile();
		infoArea = getInfoData();
		findInfoFile();
		getInfoData();
		
		printDirections();
		
		makeCheckBox();
		
		add(menuPanel, BorderLayout.NORTH);	
			
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(blue);
		leftPanel.setLayout(new BorderLayout(25,25));
		leftPanel.add(scroller, BorderLayout.CENTER);
		leftPanel.add(confirm, BorderLayout.SOUTH);
		
		JPanel rightPanel = new JPanel();
		createImages();
		rightPanel.setBackground(blue);
		rightPanel.setLayout(new GridLayout(4,2,10,10));
		
		createTextAreas();
		for(int i = 0; i < pictNames.length; i++)
		{
			rightPanel.add(images[i]);
			rightPanel.add(pictLabels[i]);
		}
		
		JButton menu = new JButton("Bio-Space");
		menu.setFont(font);
		menu.setBackground(blue);
		menu.setOpaque(true);
		menu.setBorderPainted(false);
		menu.setForeground(blue);
		menu.setBackground(yellow);
		menu.setHorizontalAlignment(JButton.CENTER);
		rightPanel.add(menu);
		rightPanel.add(pictLabels[3]);
		centerPanel.add(leftPanel);
		centerPanel.add(rightPanel);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	// This method creates the navigation JLabel and formatting JLabels
	public void makeDirections()
	{
		directions = new JLabel("  Directions");
		directions.setForeground(yellow);
		directions.setFont(font);
	}
	
	// This method creates the TextArea that has the instructions and adds the scrollpane to it
	public void printDirections()
	{
		infoArea.setMargin(new Insets(25,25,25,25));
		infoArea.setFont(font);
		infoArea.setLineWrap(true);  // goes to the next line when printing the text.
		infoArea.setWrapStyleWord(true); // prevents a word from being split 
		infoArea.setEditable(false);
		infoArea.setBackground(blue);
		infoArea.setForeground(yellow);
		
		scroller = new JScrollPane(infoArea);	// type inside the area and see the effect
		scroller.setVisible(true);
	}
	
	// This method makes the JCheckBox that the user presses to move to the option panel
	public void makeCheckBox()
	{
		confirm = new JCheckBox("I understand");
		confirm.setBackground(yellow);
		confirm.setOpaque(true);
		confirm.setBorderPainted(false);
		confirm.setForeground(blue);
		ButtonHandler bh = new ButtonHandler();
		confirm.addActionListener(bh);
		confirm.setFont(font);
		
		
	}
	
	// This method creates the images
	public void createImages()
	{
		Image [] pictures = new Image[4];
		for(int i = 0; i < pictNames.length; i++)
		{
			File pictFile = new File(pictNames[i]);
			
			try
			{
				pictures[i] = ImageIO.read(pictFile);
				pictures[i] = pictures[i].getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
				images[i] = new JLabel(new ImageIcon(pictures[i]));
			}
			
			catch(IOException e)
			{
				System.err.println("\n\n" + pictNames[i] + " can't be found.\n\n");
				e.printStackTrace();
			}
		}
	}
	
	// This method creates the labels for the images and components on the right side of the screen
	public void createTextAreas()
	{
		pictLabels[0] = new JTextArea("Click on this on the Game panel to answer a question");
		pictLabels[1] = new JTextArea("This is a dead crewmate which has no more function");
		pictLabels[2] = new JTextArea("Use WASD or the arrow keys to move this to a crewmate to answer a question");
		pictLabels[3] = new JTextArea("Press when in the top left corner to navigate " + 
			"through the game.");
		font = new Font("Serif", Font.PLAIN,30); 
		for(int i = 0; i < pictLabels.length; i++)
		{
			pictLabels[i].setFont(font);
			pictLabels[i].setLineWrap(true);
			pictLabels[i].setWrapStyleWord(true);
			pictLabels[i].setEditable(false);
			pictLabels[i].setBackground(blue);
			pictLabels[i].setForeground(yellow);
		}
	}
	// Tries to find the instructions file
	public void findInfoFile()
	{
		File infoFile = new File(infoFileName);
		try
		{
			reader = new BufferedReader(new FileReader(infoFile));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.\n\n", infoFileName);
			System.exit(1);
		}
	}
	
	// This method finds the instructions and sets infoArea equal to it
	public JTextArea getInfoData()
	{
		infoArea = new JTextArea("");
		infoArea.setEditable(false);
		try
		{
			infoArea.read(reader, null);
		}
		catch(IOException e)
		{
			System.err.println("Error: Unable to write to infoArea:");
			System.exit(2);
		}
		return infoArea;
	}
	
	//Handler class that allows the JCheckBox to go to the OptionPanel
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			confirm.setSelected(false);
			cards.show(panelCards,"Option");	
		}
	}
	
	// This class allows the navigation JMenuBar to actually navigate between panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
}

class ScorePanel extends JPanel
{
	private BioSpace5Holder panelCards; // needed for CardLayut
	private CardLayout cards; // needed for CardLayout
	
	private Information info; // Instance of Information for its methods
	private Font font; // font for components to use
	
	private JTextArea userArea; // JTextArea that has the users 
	private JTextArea scoreArea; // JTextArea that has the scores
	
	private JMenuBar bar; //JMenuBar for the navigation menu
	private JMenuItem [] menuItems; //JMenuItems for the navigation menu
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use
	/*This constructor uses a BorderLayout to show the high scores and the users that acheived them
	 * and also has a navigation JMenuBar
	 */
	public ScorePanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, JMenuBar barIn, JMenuItem [] menuItemsIn, Information infoIn
		, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		panelCards = panelCardsIn;
		cards = cardsIn;
		font = new Font("Serif", Font.PLAIN, 40);
		bar = barIn;
		menuItems = menuItemsIn;
		
		info = infoIn;
		setBackground(blue);
		
		setLayout(new BorderLayout());
		
		scoreArea = info.getScoreData();
		userArea = info.getUserData();
		
		MenuPanel mp = new MenuPanel();
		
		add(mp,BorderLayout.NORTH);
		repaint();
	}
	
	// This class creates the navigation menu and label to tell you what page you are on
	class MenuPanel extends JPanel
	{
		public MenuPanel()
		{
			setLayout(new BorderLayout());
			setBackground(blue);
			setForeground(yellow);
			
			OptionMenuHandler omh = new OptionMenuHandler();		
			
			for(int i = 0; i < menuItems.length; i++)
			{
				menuItems[i].addActionListener(omh);
			}
			
			JLabel scoreLabel = new JLabel("    Scoreboard");
			scoreLabel.setFont(font);
			scoreLabel.setBackground(blue);
			scoreLabel.setForeground(yellow);
			
			add(bar, BorderLayout.WEST);
			add(scoreLabel, BorderLayout.CENTER);
		}
	}
	
	//This class adds the top scorers and their respective scores in a gridlayout
	class BoardPanel extends JPanel
	{
		public BoardPanel()
		{
			setLayout(new GridLayout(1,2));
			
			userArea.setFont(font);
			userArea.setBackground(blue);
			userArea.setForeground(yellow);
			
			scoreArea.setFont(font);
			scoreArea.setBackground(blue);
			scoreArea.setForeground(yellow);
		
			add(userArea);
			add(scoreArea);
		}
	}
	// This Handler class makes the navigation menu actually navigate between panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals(" -Reset:"))
			{
				cards.show(panelCards,"Reset");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	// paintComponent that updates the textAreas and also adds the BoardPanel to the score panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		scoreArea = info.getScoreData();
		userArea = info.getUserData();
		revalidate();
		BoardPanel bp = new BoardPanel();
		add(bp, BorderLayout.CENTER);
	}
}

class LoginPanel extends JPanel
{
	private BioSpace5Holder panelCards; // needed for CardLayout
	private CardLayout cards; // needed for CardLayout
	
	private Font font; // font for components to use
	
	private JTextField userField; // JTextField to enter the username
	private JPasswordField passField; // JPasswordField to enter the passowrd
	
	private String accFileName, acFileName; //name of the file with account information
	
	private Scanner reader; // Reads the file with account information
	private PrintWriter writer; //Writes to the file with the account
	
	private JMenuBar bar;  //JMenuBar for the navigation menu
	private JMenuItem [] menuItems; //JMenuItems for the navigation menu
	
	private JTextArea logArea, userArea, passArea; //JLabels to tell the user what to do
	private JButton create, login; //JButtons used for logging in or creating a new account
	
	private ScorePanel scp; // used to update the scorepanel with the most recent scores
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	
	private Information info; // Instance of Information to get it's methods
	/*
	 *This constructor uses a gridLayout to store the navigation menu, textAreas to tell the user what to do,
	 * textFields and passwordFields to get the account information
	 * 2 JButtons to move to the other account panels
	 */
	public LoginPanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, JMenuBar barIn, JMenuItem [] menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn, Information infoIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		setLayout(new GridLayout(4,2,3,3));
		setBackground(Color.GREEN);
		
		panelCards = panelCardsIn;
		cards = cardsIn;
		
		scp = scpIn;
		bar = barIn;
		menuItems = menuItemsIn;
		
		info = infoIn;
		acFileName = new String("account.txt");
		accFileName = new String("accounts.txt");
		font = new Font("Serif", Font.PLAIN, 40);
		
		OptionMenuHandler omh = new OptionMenuHandler();		
		
		for(int i = 0; i < menuItems.length; i++)
		{
			menuItems[i].addActionListener(omh);
		}
		
		createLogin();
		createButtons();

		MenuPanel mp = new MenuPanel();
		
		add(mp);
		add(logArea);
		
		add(userArea);
		add(passArea);
		
		add(userField);
		add(passField);
		
		add(create);
		add(login);
	}
	
	//This class creates the menu panel with FlowLayout and the navigation menu
	class MenuPanel extends JPanel
	{
		public MenuPanel()
		{
			setBackground(blue);
			setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			JLabel tricks = new JLabel("  Joshua - 1:48:33");
			tricks.setForeground(yellow);
			tricks.setFont(font);
			
			add(bar);
			add(tricks);
		}
	}
	
	// This method creates the labels that tell the user what to do and also creates the TextAreas for logging in
	public void createLogin()
	{	
		logArea = new JTextArea("Login:");
		logArea.setForeground(yellow);
		logArea.setBackground(blue);
		logArea.setEditable(false);
		logArea.setFont(font);
		logArea.setMargin(new Insets(5,5,5,5));
		
		userArea = new JTextArea("Enter Username Below:");
		userArea.setForeground(yellow);
		userArea.setBackground(blue);
		userArea.setEditable(false);
		userArea.setFont(font);
		userArea.setMargin(new Insets(5,5,5,5));
		
		passArea = new JTextArea("Enter Password Below:");
		passArea.setForeground(yellow);
		passArea.setBackground(blue);
		passArea.setEditable(false);
		passArea.setFont(font);
		passArea.setMargin(new Insets(5,5,5,5));
		
		userField = new JTextField("Type: Delete before typing");
		userField.setFont(font);
		userField.setBackground(blue);
		userField.setForeground(yellow);
		
		TextFieldHandler tfh = new TextFieldHandler();
		userField.addActionListener(tfh);
		
		passField = new JPasswordField("Joshua");
		passField.setFont(font);
		passField.setForeground(yellow);
		passField.setBackground(blue);
		passField.addActionListener(tfh);
	}
	
	// This method creates the button that logs in and also the button that moves to the create panel
	public void createButtons()
	{
		ButtonHandler bh = new ButtonHandler();
		
		create = new JButton("Create");
		create.setOpaque(true);
		create.setBorderPainted(false);
		create.setForeground(yellow);
		create.setBackground(blue);
		create.setFont(font);
		create.addActionListener(bh);
		
		login = new JButton("Login");
		login.setOpaque(true);
		login.setBorderPainted(false);
		login.setBackground(blue);
		login.setForeground(yellow);
		login.setFont(font);
		login.addActionListener(bh);
	}
	// makes the navigation menu actually navigate the panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			userField.setText("Type: Delete before typing");
			passField.setText("Joshua");
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals(" -Reset:"))
			{
				cards.show(panelCards,"Reset");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	// Handler class that makes sure the account information is valid
	class TextFieldHandler implements ActionListener
	{
		// actionPerformed that checks the username and password and if it is valid, adds it to the account file
		public void actionPerformed(ActionEvent evt)
		{
			boolean check = false;
			
			String username = userField.getText();
			String password = passField.getText();
			String info = new String(username + " " + password);
			
			openAccFile();
			
			check = checkInfo(username, password);
			
			if(check == false)
			{
				userField.setText("Incorrect Info, please try again");

			}
			
			else
			{
				userField.setText("Logged In Successfully");
				findAcFile();
				writeAcFile(info);
			}
		}
	}
	
	//Allows the JButtons to actually navigate the panels
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Create"))
			{
				userField.setText("Type: Delete before typing");
				passField.setText("Joshua");
				cards.show(panelCards, "Create");
			}
			
			else if(command.equals("Login"))
			{
				boolean check = false;
				
				String username = userField.getText();
				String password = passField.getText();
				String info = new String(username + " " + password);
				
				openAccFile();
				check = checkInfo(username, password);
				
				if(check == false)
				{
					userField.setText("Incorrect Info, please try again");
				}
				else
				{
					userField.setText("Logged In Successfully");
					findAcFile();
					writeAcFile(info);
				}
			}
		}
	}
	
	// Tries to create a PrintWriter for the account file
	public void findAcFile()
	{
		writer = info.writeFile(acFileName, false);
	}
			
	// Writes the current user to the account file
	private void writeAcFile(String infoIn)
	{
		String account = new String(infoIn);
		writer.println(account);
		writer.close();
	}
		
	// Tries to open the file with the account information
	public void openAccFile()
	{
		reader = info.findFile(accFileName);
	}
	// Checks if the user input is valid account information
	public boolean checkInfo(String username, String password)
	{
		boolean check = false;
		while(reader.hasNextLine())
		{
			String line = reader.nextLine();
			String text = username + " " + password;
				
			if(line.equals(text))
			{
				return true;
			}
			else
			{
				check = false;
			}
		}
		return check;
	}
}

class CreatePanel extends JPanel
{
	//PasswordField https://stackoverflow.com/questions/31762417/java-uses-or-overrides-a-deprecated-api-error
	private BioSpace5Holder panelCards; // used for CardLayout
	private CardLayout cards; // used for CardLayout
	
	private Font font; // font for the components to use
	
	private String accFileName; // name of the file with the account information
	private Scanner reader; // Scanner that actually reads the account file
	private PrintWriter pWriter; // Writes to the account file to add new accounts
	
	private JTextField userField; // JTextField to enter the username
	private JPasswordField passField; // JPasswordField to enter the password
	private String account; // new Account information that the user entered
	private JMenuBar bar;  //JMenuBar for the navigation menu
	private JMenuItem[] menuItems;  //JMenuItems for the navigation menu
	private JLabel tricks; // JLabels that tell the user what to do
	private JTextArea crArea; // JTextArea for the label to tell the user what page they're on
	private JTextArea userArea, passArea; // TextAreas that allow the user to create a new account
	private JButton login, create; //Button for creating an account and Button for moving to the login page
	private JScrollPane scroller; // JScrollPane for password text to be 
	
	private ScorePanel scp; //used to update the scoreboard with the most recent scores
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	private Information info; //Information class to access its methods
	/*
	 *This constructor uses a gridLayout to store the navigation menu, textAreas to tell the user what to do,
	 * textFields and passwordFields to get the new account information
	 * 2 JButtons to move to the other account panels
	 */
	public CreatePanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, JMenuBar barIn, JMenuItem []  menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn, Information infoIn)
	{
		yellow = yellowIn;
		blue = blueIn;

		setLayout(new GridLayout(4,2,3,3));
		setBackground(Color.GREEN);
		panelCards = panelCardsIn;
		cards = cardsIn;
		bar = barIn;
		menuItems = menuItemsIn;
		scp = scpIn;
		info = infoIn;
		reader = null;
		pWriter = null;
	
		accFileName = new String("accounts.txt");
		
		font = new Font("Serif", Font.PLAIN, 40);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		menuPanel.setBackground(blue);
		
		OptionMenuHandler omh = new OptionMenuHandler();		
		
		for(int i  = 0; i < menuItems.length; i++)
		{
			menuItems[i].addActionListener(omh);
		}
		
		createLabels();
		createButtons();
		
		menuPanel.add(bar);
		menuPanel.add(tricks);
		
		
		add(menuPanel);
		add(crArea);
		add(userArea);
		add(scroller);
		add(userField);
		add(passField);
		add(login);
		add(create);
	}
	
	// This method creates the labels that tell the user what to do
	public void createLabels()
	{
		tricks = new JLabel("  Joshua-1:48:33");
		tricks.setForeground(yellow);
		tricks.setFont(font);
		
		crArea =  new JTextArea("Create:");
		crArea.setMargin(new Insets(5,5,5,5));
		crArea.setForeground(yellow);
		crArea.setBackground(blue);
		crArea.setEditable(false);
		crArea.setFont(font);
		
		userArea =  new JTextArea("Enter New Username Below: Username cannot be shorter than"
			+ " 3 characters or include spaces or colons.");
		userArea.setLineWrap(true);
		userArea.setWrapStyleWord(true);
		userArea.setBackground(blue);
		userArea.setForeground(yellow);
		userArea.setFont(font);
		userArea.setEditable(false);
		userArea.setMargin(new Insets(5,5,5,5));
		
		passArea =  new JTextArea("Enter New Password Below: Password cannot be shorter than"
			+ " 3 characters or include spaces. \nPlaceholder password is Joshua");
		passArea.setLineWrap(true);
		passArea.setWrapStyleWord(true);
		passArea.setBackground(blue);
		passArea.setForeground(yellow);
		passArea.setFont(font);
		passArea.setEditable(false);
		scroller = new JScrollPane(passArea);	// type inside the area and see the effect
		scroller.setVisible(true);
		passArea.setMargin(new Insets(5,5,5,5));

		userField = new JTextField("Type: Delete before typing");
		userField.setFont(font);
		userField.setForeground(yellow);
		userField.setBackground(blue);
		TextFieldHandler tfh = new TextFieldHandler();
		userField.addActionListener(tfh);
		
		passField = new JPasswordField("Joshua");
		passField.setFont(font);
		passField.setForeground(yellow);
		passField.setBackground(blue);
		passField.addActionListener(tfh);
	}
	
	// This method creates the button for moving to the login panel and also the 
	// button for creating the account.
	public void createButtons()
	{
		create = new JButton("Create");
		create.setOpaque(true);
		create.setBorderPainted(false);
		create.setForeground(yellow);
		create.setBackground(blue);
		create.setFont(font);
		ButtonHandler bh = new ButtonHandler();
		create.addActionListener(bh);
		
		login = new JButton("Login");
		login.setOpaque(true);
		login.setBorderPainted(false);
		login.setForeground(yellow);
		login.setBackground(blue);
		login.setFont(font);
		login.addActionListener(bh);
	}
	
	// allows the navigation menu to actually navigate between panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			userField.setText("Type: Delete before typing");
			passField.setText("Joshua");
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals(" -Reset:"))
			{
				cards.show(panelCards,"Reset");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	// Handler class that handle the new account information
	class TextFieldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String line = new String("");
			boolean check = false;
			String username = userField.getText();
			String password = passField.getText();
			account = new String(username + " " +password);
			openAccFile();
			
			while(reader.hasNextLine())
			{
				line = reader.nextLine();
				line = line.substring(0, line.indexOf(" "));
				if(line.equals(username))
				{
					userField.setText("Error: Duplicate Username");
					check = true;
				}
			}
			if(username.indexOf(" ") != -1 || password.indexOf(" ") != -1)
			{
				userField.setText("Error: Cannot have spaces in username or password");
				check = true;
			}
			else if(username.indexOf(":") != -1)
			{
				userField.setText("Error: Cannot have colons in username");
				check = true;
			}
			
			else if(username.length() <= 2)
			{
				userField.setText("Error: Username is too short");
				check = true;
			}
			else if(username.indexOf("Ender") != -1 && !(username.equals("Ender")))
			{
				userField.setText("Error: Ender the Xenocide");
				check = true;
			}
			if(check == false)
			{
				check = true;
				userField.setText("Created Account Successfully");
				writeAccFile();
			}
		}
	}
	
	//allows the 2 JButtons to navigate the different panels
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Login"))
			{
				userField.setText("Type: Delete before typing");
				passField.setText("Joshua");
				cards.show(panelCards, "Login");
			}
			else if(command.equals("Create"))
			{
				String line = new String("");
				boolean check = false;
				String username = userField.getText();
				String password = passField.getText();
				account = new String(username + " " +password);
				openAccFile();
				while(reader.hasNextLine())
				{
					line = reader.nextLine();
					line = line.substring(0, line.indexOf(" "));
					if(line.equals(username))
					{
						userField.setText("Error: Duplicate Username");
						check = true;
					}
				}
				if(username.indexOf(" ") != -1 || password.indexOf(" ") != -1)
				{
					userField.setText("Error: Cannot have spaces in username or password");
					check = true;
				}
				else if(username.indexOf(":") != -1)
				{
					userField.setText("Error: Cannot have colons in username");
					check = true;
				}
				
				else if(username.length() <= 2)
				{
					userField.setText("Error: Username is too short");
					check = true;
				}
				if(check == false)
				{
					check = true;
					userField.setText("Created Account Successfully");
					writeAccFile();
				}
			}
		}
	}
	
	// Tries to open the account file
	public void openAccFile()
	{
		reader = info.findFile(accFileName);
		pWriter = info.writeFile(accFileName, true);
	}
		
	// Writes to the account file with the account
	public void writeAccFile()
	{
		pWriter.println(account);
		pWriter.close();
	}
}

class SetPanel extends JPanel
{
	private BioSpace5Holder panelCards; // used for CardLayout
	private CardLayout cards; // used for CardLayouts
	
	private Font font; // font that components will use
	
	private JSlider numCrew, numPoints, numKills; // JSLiders that adjust the settings
	private int crew, points, kills; // the settting values: 
	private JMenuBar bar;  //JMenuBar for the navigation menu
	private JMenuItem [] menuItems; //JMenuItems for the navigation menu

	private JRadioButton skeld, mirah,polus,airship; // RadioButtons to allow to user to choose their map
	private ButtonGroup bg; //ButtonGroup for the different maps
	private JButton start; // JButton to start the game
	
	private String setting, map; //String that contains the current settings and map
	private String mapFileName, setFileName; // File names for the settings and the map

	private Scanner reader; //Scanner
	private PrintWriter writer; // PrintWriter
	private Information info; //Instance of Information to access its methods
	private ScorePanel scp; //used to update the score board with the most recent scores
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use //Used to get the right hues of blue and yellow I want to use
	/*
	 * This constructor uses a BorderLayout to create the settings panel
	 * which consists of 3 sliders that adjust the settings along with a navigation menu
	 */
	public SetPanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, Information infoIn, JMenuBar barIn, JMenuItem[] menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;
		panelCards = panelCardsIn;
		cards = cardsIn;
		bar = barIn;
		info = infoIn;
		menuItems = menuItemsIn;
		font = new Font("Serif", Font.PLAIN, 40);
		crew = 15;
		points = 5;
		kills = 2;
		
		scp = scpIn;
		
		mapFileName = new String("maps.txt");
		setFileName = new String("settings.txt");
		
		setting = new String("skeld.jpg");
		
		setBackground(blue);
		setLayout(new BorderLayout());
		
		MenuPanel mp = new MenuPanel();
		
		SettingPanel sp = new SettingPanel();
		
		createStart();
		
		add(mp, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(start, BorderLayout.SOUTH);
	}
	
	// This method creates the navigation JMenu and the navigation label
  	class MenuPanel extends JPanel
  	{
		public MenuPanel()
		{
			setLayout(new BorderLayout());
			setBackground(blue);
			
			OptionMenuHandler omh = new OptionMenuHandler();		
			
			for(int i  = 0; i < menuItems.length; i++)
			{
				menuItems[i].addActionListener(omh);
			}
			
			JLabel setLabel = new JLabel("    Game Settings");
			setLabel.setFont(font);
			setLabel.setForeground(yellow);
			
			add(bar, BorderLayout.WEST);
			add(setLabel, BorderLayout.CENTER);
		}
	}
	
	// This method that uses a gridlayout to add the JSliders and the JLabels to show
	// what each slider does
	class SettingPanel extends JPanel
	{
		public SettingPanel()
		{
			setLayout(new GridLayout(4,2));
			setBackground(blue);
			
			JLabel mapLabel = new JLabel("Map");
			mapLabel.setFont(font);
			mapLabel.setForeground(yellow);
			
			JLabel scoreLabel = new JLabel("Score");
			scoreLabel.setFont(font);
			scoreLabel.setForeground(yellow);
			
			JLabel crewLabel = new JLabel("Number of Crewmates");
			crewLabel.setForeground(yellow);
			crewLabel.setFont(font);
		
			JLabel pointLabel = new JLabel("Point Threshold for Win");
			pointLabel.setForeground(yellow);
			pointLabel.setFont(font);
			
			JLabel killLabel = new JLabel("Number of Kills per 15 Seconds");
			killLabel.setForeground(yellow);
			killLabel.setFont(font);
			
			font = new Font("Serif", Font.PLAIN, 20);
			
			numCrew = new JSlider(4,15, 15);
			numCrew.setMajorTickSpacing(1);	// create tick marks on slider every unit
			numCrew.setPaintTicks(true);
			numCrew.setLabelTable( numCrew.createStandardLabels(1) ); // create labels on tick marks
			numCrew.setPaintLabels(true);
			numCrew.setOrientation(JSlider.HORIZONTAL);
			SliderListener sl = new SliderListener();
			numCrew.addChangeListener(sl);
			numCrew.setForeground(yellow);
			numCrew.setBackground(blue);
			numCrew.setFont(font);
			
			numPoints = new JSlider(2, 10, 5);
			numPoints.setMajorTickSpacing(1);	// create tick marks on slider every unit
			numPoints.setPaintTicks(true);
			numPoints.setLabelTable( numPoints.createStandardLabels(1) ); // create labels on tick marks
			numPoints.setPaintLabels(true);
			numPoints.setOrientation(JSlider.HORIZONTAL);
			numPoints.addChangeListener(sl);
			numPoints.setForeground(yellow);
			numPoints.setBackground(blue);
			
			numPoints.setFont(font);
			
			numKills = new JSlider(1, 3, 1);
			numKills.setMajorTickSpacing(1);	// create tick marks on slider every unit
			numKills.setPaintTicks(true);
			numKills.setLabelTable( numKills.createStandardLabels(1) ); // create labels on tick marks
			numKills.setPaintLabels(true);
			numKills.setOrientation(JSlider.HORIZONTAL);
			numKills.addChangeListener(sl);
			numKills.setForeground(yellow);
			numKills.setFont(font);
			numKills.setForeground(yellow);
			numKills.setBackground(blue);
			
			font = new Font("Serif", Font.PLAIN, 40);
			
			RbPanel rbp = new RbPanel();
			
			add(mapLabel);
			add(rbp);
			add(crewLabel);
			add(numCrew);
			add(pointLabel);
			add(numPoints);
			add(killLabel);
			add(numKills);
		}
	}
	
	// This method creates the button that begins the game
	public void createStart()
	{
		start = new JButton("Start New Game");
		start.setOpaque(true);
		start.setBorderPainted(false);
		start.setFont(font);
		start.setForeground(blue);
		start.setBackground(yellow);
		StartListener sListener = new StartListener();
		start.addActionListener(sListener);
	}
	// This method creates the radiobuttons for the different maps
	class RbPanel extends JPanel
	{
		public RbPanel()
		{
			setBackground(blue);
			setLayout(new GridLayout(4,1));
			
			map = new String("Skeld");
			
			bg = new ButtonGroup();
				
			RButtonHandler rbh = new RButtonHandler();
			
			skeld = new JRadioButton("Skeld");	// construct button  
			
			skeld.addActionListener(rbh); 	// add listener to JRadioButton
			skeld.setSelected(true);		// add b2utton to panel	
			skeld.addActionListener(rbh); 		// add listener to button
			skeld.setOpaque(true);
			skeld.setBorderPainted(false);
			skeld.setForeground(yellow);
			skeld.setBackground(blue);
			skeld.setFont(font);
				
			mirah = new JRadioButton( "Mirah" );	// construct button  
			mirah.addActionListener(rbh); 		// add listener to button
			mirah.setOpaque(true);
			mirah.setBorderPainted(false);
			mirah.setForeground(yellow);
			mirah.setBackground(blue);
			mirah.setFont(font);
				
			polus = new JRadioButton( "Polus" );	// construct button 
			polus.addActionListener(rbh); 	// add listener to button
			polus.setOpaque(true);
			polus.setBorderPainted(false);
			polus.setForeground(yellow);
			polus.setBackground(blue);
			polus.setFont(font);
				
			airship = new JRadioButton( "Airship" );	// construct button  
			airship.addActionListener(rbh); 	// add listener to button
			airship.setOpaque(true);
			airship.setBorderPainted(false);
			airship.setForeground(yellow);
			airship.setBackground(blue);
			airship.setFont(font);
			
			bg.add( skeld );
			bg.add( mirah );		// add b2utton to panel	
			bg.add( polus );
			bg.add( airship );		// add button to panel	
			
			add( skeld );	
			add( mirah );
			add( polus );
			add( airship );
		}
	}
	
	// Handler Class that allows the navigation menu to actually navigate between panels
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals(" -Reset:"))
			{
				cards.show(panelCards,"Reset");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	//Handler class for the sliders in order to get the setting values
	class SliderListener implements ChangeListener 
	{
		public void stateChanged (ChangeEvent evt) 
		{
			crew = numCrew.getValue();	// get the value of the slider
			points = numPoints.getValue();
			kills = numKills.getValue();
			String settings = new String(crew + " " + points + " " + kills);
			findSetFile();
			writeSetFile(settings);
		}
		
		// This method creates a scanner for the file with the settings
		public void findSetFile()
		{
			reader = info.findFile(setFileName);
		}
		
		// This method writes to the file with the settings
		public void writeSetFile(String settingsIn)
		{
			String settings = settingsIn;
			writer = info.writeFile(setFileName, false);
			writer.println(settings);
			writer.close();
		}
	}
	
	// Handler class to start the game
	class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Start New Game"))
			{ 
				cards.show(panelCards, "Game");
			}
		}
	}
	
	// Handler class that finds the map that is currently being selected
	class RButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			map = evt.getActionCommand();
			
			findMapFile();
			writeMapFile();
			if(map.equals("Skeld"))
			{
				setting = new String("skeld.jpg");
			}
			
			else if(map.equals("Mirah"))
			{
				setting = new String("mirah.jpg");
			}
			
			else if(map.equals("Polus"))
			{
				setting = new String("polus.jpg");
			}
			
			else if(map.equals("Airship"))
			{
				setting = new String("airship.jpg");
			}
			findMapFile();
			writeMapFile();
		}
		
		// This method creates the Scanner for the map file
		public void findMapFile()
		{
			reader = info.findFile(mapFileName);
		}
		
		// This method writes to the map file with the current map
		public void writeMapFile()
		{
			writer = info.writeFile(mapFileName, false);
			writer.println(setting);
			writer.close();
		}
	}
}

class GamePanel extends JPanel implements KeyListener, MouseListener
{
	private BioSpace5Holder panelCards; // needed for cardlayout
	private CardLayout cards; // needed for cardlayout
	private JMenuBar bar;  //JMenuBar for the navigation menu
	private JMenuItem [] menuItems; //JMenuItems for the navigation menu
	
	private Font font; // font for the components to use
	
	private String username, password; //Strings that have the current settings
	
	private Timer timer; //Timer for the score
	private JTextArea scoreArea; //TextArea that updates with the score
	
	private Image mapImage; // The image that actually has the map
	
	private Scanner input, input2; // Scanner used to read the files
	
	// Strings that have the file names
	private String mapsFileName, mapFileName, setFileName, accFileName, acFileName;
	
	//The total elapsed time of the game and also check variable for the score increment and the kill times
	private int time, scoreCheck, killCheck; 
	private int numCrew, numPoints, numKills; // used for the current settings
	private int [] xPos; //int array that has where all the crewmates go
	private int [] yPos; //int array that has where all the crewmates go
	
	private JFrame questFrame; // JFrame that has the questions
	private int points; // int that has the point total
	private String [] crewNames; // String array with all the alive crewmates 
	private String [] deadNames; // String array with all the dead crewmates 
	private Image [] crewImages; // Image array with all of the alive crewmates
	private Image [] deadImages; // Image array with all of the dead crewmates
	
	private ScorePanel scp; // used to update the scoreboard with the most recent scores
		
	private boolean checkNew; // Checks to see if the program should go to the settings panel
	private GamePanel gpp2; // instance of the class to call repaint
	private JFrame doneFrame; // frame for the frame that shows up when you lose the game
	private int [] nums; // The question numbers
	private boolean startTimer; // This boolean to check wheter or not the timer needs to be turned on
	
	private Image userImage; // The image that the user uses for navigating the map
	private String userImageName; // The name of the image that the user uses for navigating the map
	
	private int userX, userY; // X and Y coordinates of the avatar the user controls
	private Information info; //Instance of Information to get its methods
	private int timeCheck; // Checks to see if a kill should occur
	private boolean mapCheck; // Checks to see if the map should be updated
	private boolean questCheck; // Checks to see if a new question frame should be made
	private boolean checkCrew; // Checks if the user avatar is on a crewmate
	private int time2; // Time for the timer for the correctFrame and incorrectFrame
	private Timer timer2; // Timer for the closing of correctFrame and incorrectFrame
	private Color yellow, blue; //Used to get the right hues of blue and yellow I want to use
	
	private int number; // crewmat enumber that the user clicked on
	/*
	 * This constructor creates the game panel that has a BorderLayout 
	 * and has the navigation menu and navigation label. The panel also 
	 * shows the score and it contains the game interface with all of the images
	 */
	public GamePanel(BioSpace5Holder panelCardsIn, CardLayout cardsIn, Information infoIn, JMenuBar barIn, JMenuItem [] menuItemsIn, ScorePanel scpIn
		, Color blueIn, Color yellowIn)
	{
		yellow = yellowIn;
		blue = blueIn;

		panelCards = panelCardsIn;
		cards = cardsIn;
		gpp2 = this;
		info = infoIn;
		bar = barIn;
		scp = scpIn;
		menuItems = menuItemsIn;
		input = null;
		setLayout(new BorderLayout(0,500));
		killCheck = 0;
		timeCheck = 0;
		startTimer = true;
		questCheck = false;
		
		mapsFileName= new String("maps.txt");
		mapFileName = new String("skeld.jpg");
		setFileName = new String("settings.txt");
		accFileName = new String("account.txt");
		userImageName = new String("userGhost.png");
		acFileName = new String("account.txt");
		font = new Font("Serif" , Font.PLAIN, 50);
		
		checkCrew = false;
		
		findRandomNumbers();
		
		userX = 683;
		userY = 500;
		
		makeUserImage();
		xPos = new int [] {180,650,730,1250,530,545,300,300,400,875,1040,870,970,1040,465};
		yPos = new int [] {360,210,655,360,520,345,230,550,375,640,560,450,333,210,225};
		
		crewNames = new String[]{"red.png", "blue.png", "green.png", "pink.png", "orange.png" 
			, "yellow.png", "tan.png", "white.png", "purp.png", "brown.png"
			, "cyan.png", "lime.png", "maroon.png", "rose.png", "banana.png"};
		
		deadNames = new String[]{"deadred.png", "deadblue.png", "deadgreen.png"
			, "deadpink.png", "deadorange.png" , "deadyellow.png", "deadtan.png"
			, "deadwhite.png", "deadpurp.png", "deadbrown.png", "deadcyan.png"
			, "deadlime.png", "deadmaroon.png", "deadrose.png", "deadbanana.png"};
		
		crewImages = new Image [15];
		deadImages = new Image [15];
			
		MenuPanel mp = new MenuPanel();
		
		timer = makeTimer();
		addMouseListener(this);
		addKeyListener(this);
		add(mp, BorderLayout.NORTH);
	}
	
	public void findRandomNumbers()
	{
		nums = new int [20];
		boolean checkAll = true;
		mapCheck = true;
		while(checkAll == true)
		{
			getRandomNumbers();
			boolean checkSome = true;
			for(int i = 0; i < 15; i++)
			{
				for(int j = 0; j < 15; j++)
				{
					if((nums[i] == nums[j]) && i != j)
					{
						 checkSome = false;
					}
				}
			}
			if(checkSome == true)
			{
				checkAll = false;
			}
			else
			{
				checkAll = true;
			}
		}

		updateNumArray();
	}
	// This method creates 10 random numbers
	public void getRandomNumbers()
	{
		nums = new int [30];
		for(int i = 0; i < nums.length; i++)
		{
			nums[i] = (int)(Math.random()*15)+1;
		}
	}
	
	// This method shuffles the array with question numbers randomly
	public void updateNumArray()
	{
		int [] nums2 = new int [15];
		for(int i = 0; i < nums2.length; i++)
		{
			nums2[i] = nums[i] + 15;
		}

		int [] nums3 = new int [30];
		for(int i = 0; i < nums3.length; i++)
		{
			if(i < 15)
			{
				nums3[i] = nums[i];
			}
			else
			{
				nums3[i] = nums2[i-15];
			}
		}
		for (int i=0; i < nums3.length; i++) {
		    int rpos = (int)(Math.random() * nums3.length);
		    int temp = nums3[i];
		    nums3[i] = nums3[rpos];
		    nums3[rpos] = temp;
		}

		for(int i = 0; i < nums3.length; i++)
		{
			nums[i] = nums3[i];
		}
	}
	
	// This method creates the user avatar that the user uses to navigate
	public void makeUserImage()
	{
		requestFocusInWindow();
		userImage = null;
			
		File imageFile = new File(userImageName);
		try
		{
			userImage = ImageIO.read(imageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" +userImageName + " can't be found.\n\n");
			e.printStackTrace();
		}
	}
	// Used to find the file with the current map
	public void findMapFile()
	{
		input = info.findFile(mapsFileName);
		mapFileName = new String(input.next());
	}
	
	// Used to create all of the images	
	public void createImage()
	{
		mapImage = null;
			
		File imageFile = new File(mapFileName);
		try
		{
			mapImage = ImageIO.read(imageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" +mapFileName + " can't be found.\n\n");
			e.printStackTrace();
		}
		for(int i = 0; i < numCrew; i++)
		{
			File crewFile = new File(crewNames[i]);
			try
			{
				crewImages[i] = ImageIO.read(crewFile);
			}
			catch(IOException e)
			{
				System.err.println("\n\n" + crewNames[i] + " can't be found.\n\n");
				e.printStackTrace();
			}
			
			File deadFile = new File(deadNames[i]);
			try
			{
				deadImages[i] = ImageIO.read(deadFile);
			}
			catch(IOException e)
			{
				System.err.println("\n\n" + deadNames[i] + " can't be found.\n\n");
				e.printStackTrace();
			}
		}
		repaint();
	}
	
	// Creates the navigation menu and also has the score text area
	class MenuPanel extends JPanel
	{
		public MenuPanel()
		{
			setLayout(new BorderLayout());
			setBackground(blue);
			
			OptionMenuHandler omh = new OptionMenuHandler();		
			
			for(int i  = 0; i < 7; i++)
			{
				menuItems[i].addActionListener(omh);
			}
			
			JLabel setLabel = new JLabel("    Game");
			setLabel.setFont(font);
			setLabel.setForeground(yellow);
			
			scoreArea = new JTextArea("Score: 00000");
			scoreArea.setFont(font);
			scoreArea.setForeground(yellow);
			scoreArea.setBackground(blue);
			scoreArea.setEditable(false);
			
			add(bar, BorderLayout.WEST);
			add(setLabel, BorderLayout.CENTER);
			add(scoreArea, BorderLayout.EAST);
		}
	}
	
	// Makes the navigation menu actually switch pages and also changes the variables to the default
	class OptionMenuHandler implements ActionListener
 	{
		public void actionPerformed(ActionEvent evt)
		{
			timer.stop();
			if(timer2 != null)
			{
				timer2.stop();
			}
			time2 = 0;
			time = 0;
			points = 0;
			killCheck = 0;
			scoreCheck = 0;
			timeCheck = 0;
			userX = 683;
			userY = 500;
			startTimer = true;
			mapCheck = true;
			questCheck = false;
			if(questFrame != null)
			{
				questFrame.dispose();
			}
			crewNames = new String[]{"red.png", "blue.png", "green.png"
				, "pink.png", "orange.png" 
				, "yellow.png", "tan.png", "white.png", "purp.png", "brown.png"
				, "cyan.png", "lime.png", "maroon.png", "rose.png", "banana.png"};
		
			deadNames = new String[]{"deadred.png", "deadblue.png", "deadgreen.png"
				, "deadpink.png", "deadorange.png" , "deadyellow.png", "deadtan.png"
				, "deadwhite.png", "deadpurp.png", "deadbrown.png", "deadcyan.png"
				, "deadlime.png", "deadmaroon.png", "deadrose.png", "deadbanana.png"};
			createImage();
			
			String command = evt.getActionCommand();
			
			if(command.equals("Loading Screen:"))
			{
				cards.show(panelCards,"Option");
			}
			else if(command.equals("Scoreboard:"))
			{
				scp.repaint();
				cards.show(panelCards,"Scoreboard");
			}
			else if(command.equals("Start Game:"))
			{
				cards.show(panelCards,"Setting");
			}
			else if(command.equals("Directions:"))
			{
				cards.show(panelCards,"Direct");
			}
			else if(command.equals("Login:"))
			{
				cards.show(panelCards,"Login");
			}
			else if(command.equals(" -Create:"))
			{
				cards.show(panelCards,"Create");
			}
			else if(command.equals(" -Reset:"))
			{
				cards.show(panelCards,"Reset");
			}
			else if(command.equals("Quit:"))
			{
				System.exit(1);
			}
		}
	}
	
	// This method creates the timer and returns it
	public Timer makeTimer()
	{
		TimerListener tl = new TimerListener();
		timer = new Timer(1000,tl);
		return timer;
	}
	/*
	 * This class updates the time and the check variable and uses if-else statements
	 * to increment the score.
	 */
	class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if(startTimer == true)
			{
				time++;
				scoreCheck++;
				timeCheck++;
				if(timeCheck == 15)
				{
					for(int i = 0; i < crewNames.length; i++)
					{
						xPos[i] += 40;
					}
					repaint();
				}
				if(timeCheck == 30)
				{
					for(int i = 0; i < crewNames.length; i++)
					{
						yPos[i] += 40;
					}
					repaint();
				}
				if(timeCheck == 45)
				{
					for(int i = 0; i < crewNames.length; i++)
					{
						xPos[i] -= 40;
					}
					repaint();
				}
				if(timeCheck == 60)
				{
					for(int i = 0; i < crewNames.length; i++)
					{
						yPos[i] -= 40;
					}
					timeCheck = 0;
					repaint();
				}
				String timeString = new String(time*5 + "");
				int timeLength = timeString.length();
				int length = 5 - timeLength;
				String zeros = new String("");
				
				
				for(int i = 0; i < length; i++)
				{
					zeros += "0";
				}
				
				timeString = zeros + timeString;
				
				String username = new String("");
				
				username = findAccFile();
				int tempCrew = numCrew - killCheck-points;
				if(tempCrew <= 0)
				{
					tempCrew = 0;
				}
				scoreArea.setText(username + ": " + timeString 
					+ "    Crew left: " + (tempCrew));
				if(scoreCheck == 15)
				{			
					if(killCheck >= numCrew - (numKills + points))
					{
						startTimer = false;
						doneFrame = new JFrame("Done");
						doneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
						doneFrame.setSize(1000, 500);
						doneFrame.setLocation(100, 100);
						doneFrame.setVisible(true);
						JPanel donePanel = new JPanel();
						donePanel.setBackground(blue);
						donePanel.setLayout(new GridLayout(3,1,0,5));
	
						font = new Font("Serif", Font.PLAIN, 50);
						
						JLabel loss = new JLabel("Sorry, you have lost the game");
						loss.setFont(font);
						loss.setForeground(yellow);
						loss.setHorizontalAlignment(JLabel.CENTER);
						donePanel.add(loss);
					
						JButton startGame = new JButton("Start New Game");
						startGame.setFont(font);
						startGame.setBorderPainted(false);
						startGame.setOpaque(true);
						StartHandler sh = new StartHandler();
						startGame.addActionListener(sh);
						startGame.setFont(font);
						startGame.setBackground(yellow);
						startGame.setForeground(blue);
						donePanel.add(startGame);
						
						JButton exitGame = new JButton("Exit Game");
						exitGame.setFont(font);
						exitGame.setBorderPainted(false);
						exitGame.setOpaque(true);
						exitGame.addActionListener(sh);
						exitGame.setFont(font);
						exitGame.setBackground(yellow);
						exitGame.setForeground(blue);
						donePanel.add(exitGame);
						
						doneFrame.add(donePanel);
					}
					killCheck += numKills;

					for(int i = 0; i < killCheck; i++)
					{
						if(crewNames[i].indexOf("empty") == -1)
						{
							crewImages[i] = deadImages[i];
							crewNames[i] = deadNames[i];
						}
						else
						{
							killCheck++;
						}
					}
					scoreArea.setText(username + ": " + timeString 
						+ "    Crew left: " + (numCrew - killCheck - points));
					scoreCheck = 0;
					repaint();
				}
			}
		}
		
		/* This Handler class resets the game panel when the user clicks the 
		 * Start New Game button
		 */
		class StartHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent evt)
			{
				String command = new String(evt.getActionCommand());
				
				if(command.equals("Start New Game"))
				{
					timer.stop();
					time2 = 0;
					if(timer2 != null)
					{
						timer2.stop();
					}	
					time = 0;
					points = 0;
					killCheck = 0;
					scoreCheck = 0;
					timeCheck = 0;
					userX = 683;
					userY = 500;
					startTimer = true;
					mapCheck = true;
					questCheck = false;
					if(questFrame != null)
					{
						questFrame.dispose();
					}
					crewNames = new String[]{"red.png", "blue.png", "green.png"
						, "pink.png", "orange.png" , "yellow.png"
						, "tan.png", "white.png", "purp.png", "brown.png"
						, "cyan.png", "lime.png", "maroon.png", "rose.png"
						, "banana.png"};
				
					deadNames = new String[]{"deadred.png", "deadblue.png"
						, "deadgreen.png"
						, "deadpink.png", "deadorange.png" 
						, "deadyellow.png", "deadtan.png"
						, "deadwhite.png", "deadpurp.png"
						, "deadbrown.png", "deadcyan.png"
						, "deadlime.png", "deadmaroon.png"
						, "deadrose.png", "deadbanana.png"};
					createImage();
					doneFrame.dispose();
					gpp2.repaint();
					findRandomNumbers();
					cards.show(panelCards, "Setting");
				}
				
				if(command.equals("Exit Game"))
				{
					System.exit(1);
				}
			}
		}
		// This method finds the username and returns it
		public String findAccFile()
		{
			input = info.findFile(accFileName);
			String username = new String("");
			username = input.next();
			return username;
		}
	}
	
	// This method finds the location of a mouse clicked and shows the question attached
	// to that area.
	public void mouseClicked(MouseEvent evt)
	{
		requestFocusInWindow();
		int x = evt.getX();
		int y = evt.getY();
		int number = 0;
		boolean check = false;
		for(int i = 0; i < numCrew; i++)
		{
			if(x<=xPos[i]+50 && x >= xPos[i] && y<=yPos[i]+50 && y >= yPos[i])
			{
				number = i;
				check = true;
			}
		}
		
		if(!(crewNames[number].equals("empty.png")) && crewNames[number].indexOf("dead") == -1 
			&& check == true && questCheck == false)
		{
			questCheck = true;
			questFrame = new JFrame("Questions");
			makeQuestFrame(number+1);
		}
	}
	
	// This method creates the JFrame that contains the question
	public JFrame makeQuestFrame(int numIn)
	{
		int num = numIn;
		questFrame = new JFrame("Questions");
		questFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		questFrame.setSize(1000, 500);
		questFrame.setLocation(100, 100);
		questFrame.setVisible(true);
		QuestPanel qp = new QuestPanel(num, this);
		
		questFrame.add(qp);
		return questFrame;
	}
	/* 
	 * This class creates the question panel and also finds the answers
	 */
	class QuestPanel extends JPanel implements WindowListener
	{
		private Scanner input; // Scanner
		private String questFileName, userFileName, scoreFileName; 
		// The file names to read and write to
		
		private int num; // the crewmate number
		private JTextArea question; // JTextArea that shows the question 
		private JRadioButton aButton, bButton, cButton, dButton; 
		// JRadioButtons that show the different answer choices
		private GamePanel gpp; // Instance of GamePanel to use its methods
		private PrintWriter writer; // PrintWriter
		private String buttonSelected; // The button that is currently selected
		
		public QuestPanel(int numIn, GamePanel gpIn)
		{
			gpp = gpIn;
			num = numIn;
			writer = null;
			input = null;
			setBackground(blue);
			setLayout(new BorderLayout(5,5));

			JPanel topPanel = new JPanel();
			topPanel.setBackground(Color.BLUE);
			topPanel.setLayout(new GridLayout(2,1));
	
			aButton = new JRadioButton("a.");
			buttonSelected = "e.";
			
			bButton = new JRadioButton("b.");
			cButton = new JRadioButton("c.");
			dButton = new JRadioButton("d.");
			questFileName = new String("questions.txt");
			userFileName = new String("users.txt");
			scoreFileName = new String("scores.txt");
			time2 = 0;
			
			font = new Font("Serif", Font.PLAIN, 25);
			question = new JTextArea("");
			question.setMargin(new Insets(5,5,5,5));
			question.setFont(font);
			question.setBackground(blue);
			question.setForeground(yellow);
			question.setLineWrap(true);  // goes to the next line when printing the text.
			question.setWrapStyleWord(true); // prevents a word from being split
			question.setEditable(false); 
			findQuestFile();
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(blue);
			buttonPanel.setLayout(new GridLayout(2,2,5,5));
			buttonPanel.add(aButton);
			buttonPanel.add(bButton);
			buttonPanel.add(cButton);
			buttonPanel.add(dButton);
			
			topPanel.add(question);
			topPanel.add(buttonPanel);
			ButtonHandler bh = new ButtonHandler();
			JButton submit = new JButton("Submit:");
			submit.addActionListener(bh);
			submit.setFont(font);
			submit.setForeground(blue);
			submit.setBorderPainted(false);
			submit.setOpaque(true);
			submit.setBackground(yellow);
			questFrame.addWindowListener(this);
			add(topPanel, BorderLayout.CENTER);
			add(submit, BorderLayout.SOUTH);
		}
		
		// This method finds the question file and calls a method that creates the answer buttons
		public void findQuestFile()
		{
			input = info.findFile(questFileName);
			createButtons();
		}
		
		// This method creates the answer buttons from the question file
		public void createButtons()
		{
			RadioButtonHandler rbh = new RadioButtonHandler();
			ButtonGroup bg = new ButtonGroup();
			
			boolean check = false;
			boolean startAns = false;
			
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				String numString = nums[num] + "";
				
				if(Integer.parseInt(numString) < 10)
				{
					numString = "0" + numString;
				}
				
				String numString2 = (nums[num]+1) + "";
				
				if(Integer.parseInt(numString2) < 10)
				{
					numString2 = "0" + numString2;
				}
				
				if(line.indexOf(numString) != -1 && line.indexOf("a.") == -1)
				{
					check = true;
				}
	
				if(line.indexOf("a.") != -1 && check == true)
				{
					check = false;
					startAns = true;
					aButton = new JRadioButton(line);
					aButton.setFont(font);
					aButton.setBackground(yellow);
					aButton.setForeground(blue);
					aButton.setBorderPainted(false);
					aButton.addActionListener(rbh);
					aButton.setOpaque(true);
					bg.add(aButton);
				}
				
				else if(line.indexOf("b.") != -1 && startAns == true)
				{
					bButton = new JRadioButton(line);
					bButton.setFont(font);
					bButton.setBackground(yellow);
					bButton.setForeground(blue);
					bButton.setBorderPainted(false);
					bButton.setOpaque(true);
					bButton.addActionListener(rbh);
					bg.add(bButton);
				}
				
				else if(line.indexOf("c.") != -1 && startAns == true)
				{
					cButton = new JRadioButton(line);
					cButton.setFont(font);
					cButton.setBackground(yellow);
					cButton.setForeground(blue);
					cButton.setBorderPainted(false);
					cButton.setOpaque(true);
					cButton.addActionListener(rbh);
					bg.add(cButton);
				}
				
				else if(line.indexOf("d.") != -1 && startAns == true)
				{
					dButton = new JRadioButton(line);
					dButton.setFont(font);
					dButton.setBackground(yellow);
					dButton.setForeground(blue);
					dButton.setBorderPainted(false);
					dButton.setOpaque(true);
					dButton.addActionListener(rbh);
					bg.add(dButton);
					startAns = false;
				}
				if(check == true)
				{
					if(question.getText().equals(""))
					{
						line = line.substring(line.indexOf((numString + ". ")) + 4);
						line = line.trim();
						question.setText(question.getText() + line);
					}
					else
						question.setText(question.getText() + "\n" + line);
				}
			}
		}
		
		// This Handler class updates buttonSelected with the selected button
		class RadioButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent evt)
			{
				buttonSelected = evt.getActionCommand();
			}
		}
		// This Handler class determines if the button that the user 
		// clicked was correct and prints the judgement
		class ButtonHandler extends JPanel implements ActionListener
		{
			private String ansFileName;
			private String userAns;
			private String scoreString;
			private String [] usernames, scores;
			private PrintWriter writer;
			private int highest, score;
			private JFrame correctFrame, incorrectFrame;
			private JLabel incorrectLabel , correctLabel;
			
			public void actionPerformed(ActionEvent evt)
			{
				ansFileName = new String("answers.txt");
				highest = 0;
				usernames = new String [11];
				scores = new String [11];
				writer = null;
				userAns = buttonSelected.charAt(0) + "";
				findAnsFile();
			}
			
			// This method finds the file with the answers
			public void findAnsFile()
			{
				input = info.findFile(ansFileName);
				checkCorrect();
			}
			
			/*
			 * This method checks if the user button is correct by scanning the file with
			 * the answers until the correct question number is found and comparing
			 * the two answers. If the answer is found, the point total is increased.
			 */
			public void checkCorrect()
			{
				String correct = new String("");
				
				while(input.hasNextLine())
				{
					String line = new String("");
					line = input.nextLine();
					
					String numString = new String(nums[num] + "");
					if(nums[num] < 10)
					{
						numString = "0" + numString;
					}
					if(line.indexOf(numString) != -1)
					{
						line = line.substring(line.indexOf(".")+1);
						line = line.trim();
						correct = line;
					}
				}
				
				if(userAns.equalsIgnoreCase(correct))
				{
					time2 = 0;
					questCheck = false;
					Image emptyImage = null;
					File emptyFile = new File("empty.png");
					try
					{
						emptyImage = ImageIO.read(emptyFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n" + "empty.png" + " can't be found.\n\n");
						e.printStackTrace();
					}
					crewNames[num-1] = "empty.png";
					deadImages[num-1] = emptyImage;
					crewImages[num-1] = emptyImage;
					gpp.repaint();
					questFrame.dispose();
					if(correctFrame != null)
					{
						correctFrame.dispose();
					}
					correctFrame = new JFrame("correct");
					correctFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
					correctFrame.setSize(1000, 500);
					correctFrame.setLocation(100, 100);
					correctFrame.setVisible(true);
									
					JPanel labelPanel = new JPanel();	
					labelPanel.setLayout(new GridLayout(1,1));
					labelPanel.setBackground(blue);
					font = new Font("Serif" ,Font.PLAIN, 200);
					
					correctLabel = new JLabel("Correct");
					correctLabel.setFont(font);
					correctLabel.setForeground(yellow);
					correctLabel.setHorizontalAlignment(JLabel.CENTER);
					labelPanel.add(correctLabel);
				
					points++;
					correctFrame.add(labelPanel);
					time2 = 0;
					Timer2Listener tl2 = new Timer2Listener();
					timer2 = new Timer(1000,tl2);
					timer2.start();
					if(points == numPoints)
					{
						time2 = 0;
						timer.stop();
						String text = scoreArea.getText();
						text = text.substring(text.indexOf(":") + 1, text.indexOf("Crew")-2);
						text = text.trim();
						score  = Integer.parseInt(text);
						font = new Font("Serif" ,Font.PLAIN, 40);
						correctLabel.setText("<html>You win with a score of " + score
						 + " and a point total of " + points);
						correctLabel.setFont(font);
						calcSpot();
					}
				}
				else
				{
					time2 = 0;
					Timer2Listener tl2 = new Timer2Listener();
					timer2 = new Timer(1000, tl2);
					timer2.start();
					incorrectFrame = new JFrame("incorrect");
					incorrectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
					incorrectFrame.setSize(1000, 500);
					incorrectFrame.setLocation(100, 100);
					incorrectFrame.setVisible(true);
									
					JPanel labelPanel = new JPanel();	
					labelPanel.setLayout(new GridLayout(1,1));
					labelPanel.setBackground(blue);
					
					incorrectLabel = new JLabel("Incorrect");
					font = new Font("Serif" ,Font.PLAIN, 200);
					
					incorrectLabel.setFont(font);
					incorrectLabel.setForeground(yellow);
					incorrectLabel.setHorizontalAlignment(JLabel.CENTER);
					labelPanel.add(incorrectLabel);
					incorrectFrame.add(labelPanel);
				}
			}
			
			// This is the TimerListener class and it updates the correctFrame or incorrectFrame
			// with the number of seconds left before it automatically closes
			class Timer2Listener implements ActionListener
			{
				public void actionPerformed(ActionEvent evt)
				{
					time2++;
					if(points == numPoints && time2 <= 3 && correctFrame != null)
					{
						font = new Font("Serif" , Font.PLAIN, 60);
						correctLabel.setFont(font);
						correctLabel.setText("<html>You win with a score of " + score
						 + " <BR>and a point total of " + points + "<br>Wait " 
							+ (3 - (time2)) + " second(s)");
					}
					if(time2 < 3 && incorrectLabel != null)
					{
						font = new Font("Serif" , Font.PLAIN, 60);
						incorrectLabel.setFont(font);
						incorrectLabel.setText("<HTML>Incorrect<br>Wait " + (3 - time2) + " second(s)");
					}
					if(time2 < 3 && correctLabel != null && points < numPoints)
					{
						font = new Font("Serif" , Font.PLAIN, 60);
						correctLabel.setFont(font);
						correctLabel.setText("<HTML>Correct<br>Wait " + (3 - time2) + " second(s)");
					}
					if(time2 == 3 && correctLabel != null && points == numPoints)
					{
						correctLabel = null;
						correctFrame.dispose();
						timer.stop();
						timer2.stop();
						time2 = 0;
						time = 0;
						points = 0;
						killCheck = 0;
						scoreCheck = 0;
						timeCheck = 0;
						startTimer = true; 
						userX = 683;
						userY = 500;

						crewNames = new String[]{"red.png", "blue.png"
							, "green.png", "pink.png", "orange.png" ,
							 "yellow.png", "tan.png", "white.png"
							 , "purp.png", "brown.png", "cyan.png"
							 , "lime.png", "maroon.png", "rose.png"
							 , "banana.png"};
					
						deadNames = new String[]{"deadred.png"
							, "deadblue.png", "deadgreen.png"
							, "deadpink.png", "deadorange.png" 
							, "deadyellow.png", "deadtan.png"
							, "deadwhite.png", "deadpurp.png"
							, "deadbrown.png", "deadcyan.png"
							, "deadlime.png", "deadmaroon.png"
							, "deadrose.png", "deadbanana.png"};		
						checkNew = false;
						questCheck = false;
						gpp2.repaint();
						gpp2.createImage();
						questFrame.dispose();
						findRandomNumbers();
						cards.show(panelCards, "Setting");
					}
					if(time2 == 3 && incorrectLabel != null)
					{
						time2 = 0;
						timer2.stop();
						incorrectFrame.dispose();
					}
					if(time2 == 3 && correctFrame != null)
					{
						time2 = 0;
						timer2.stop();
						correctFrame.dispose();
					}
				}
			}
			// This method finds calculates the spot that you acheive with your given score
			public void calcSpot()
			{
				scoreString = new String("");
				scoreString = scoreArea.getText();
				scoreString = scoreString.substring(scoreString.indexOf(":") + 1,
					scoreString.indexOf("Crew")-2);
				scoreString = scoreString.trim();
				
				checkUsers();

				if(username.equals("Rachel") && password.equals("Huang"))
				{
					scoreString = "00000";
				}
				else if(username.equals("Stephanie") && password.equals("Zhang"))
				{
					scoreString = "00001";
				}	
				else if(username.equals("Dorothy") && password.equals("Zheng"))
				{
					scoreString = "00002";
				}	
				else if(username.equals("Qile") && password.equals("Bao"))
				{
					scoreString = "00003";
				}
				else if(username.equals("John") && password.equals("Conlin"))
				{
					scoreString = "00004";
				}	
				else if(username.equals("Aiden") && password.equals("Huang"))
				{
					scoreString = "99998";
				}	
				else if((username.equals("Ender") || username.equals("Andrew")) 
					&& password.equals("Wiggin"))
				{
					scoreString = "33333";
				}	
				
				else if(username.equals("Krishay") && password.equals("Bhople"))
				{
					scoreString = "00006";
				}	
				
				else if(username.equals("Indu") && password.equals("Devakonda"))
				{
					scoreString = "17319";
				}	
				
				else if(username.equals("Ganesh") && password.equals("Batchu"))
				{
					scoreString = "00008";
				}	
				if(scoreString.substring(3).equals("75"))
				{
					scoreString = "00007";
				}
				findScoreFile(); 
				findUserFile();
				writeScoreFile();
				writeUserFile();
				scp.repaint();
			}
			
			// This method finds the file with the scores
			public void findScoreFile()
			{
				input = info.findFile(scoreFileName);
				convertScores();
			}
			
			/* 
			 * This method determines the spot that the user acheived with their score
			 * by using a for-loop to determine which scores are above it.
			 */
			public void convertScores()
			{
				int count = 0;
				while(input.hasNextLine())
				{
					String line = input.nextLine();
					scores[count] = line;
					count++;
				}
				
				highest = 10;
				for(int i = 0; i < 10; i++)
				{
					if(Integer.parseInt(scores[i]) > Integer.parseInt(scoreString))
					{
						highest --;
					}
				}
				String [] newScores = new String[11];
				
				for(int i = 0; i < 10; i++)
				{
					newScores[i] = scores[i];
				}
				scores[highest] = scoreString;
				
				for(int i = highest; i < 10; i++)
				{
					scores[i+1] = newScores[i];
				}
			}
			
			// This method finds the file with the users
			public void findUserFile()
			{
				input = info.findFile(userFileName);
				
				findUserScore();			
			}
			
			public void checkUsers()
			{
				input2 = info.findFile(acFileName);
				username = input2.next();
				password = input2.next();
			}
			
			// This method changes the array with the users and updates 
			// it to the one with the current user
			public void findUserScore()
			{
				int count = 0;
				input = info.findFile(userFileName);
				while(input.hasNextLine())
				{
					String line = input.nextLine();
					usernames[count] = line;
					count++;
				}
				
				String [] newUsers = new String[11];
				
				for(int i = 0; i < 10; i++)
				{
					newUsers[i] = usernames[i];
					newUsers[i] = newUsers[i].substring(newUsers[i].indexOf(".")+1);
					newUsers[i] = newUsers[i].trim();
				}
				usernames[highest] = username;
				
				for(int i = highest; i < 10; i++)
				{
					usernames[i+1] = newUsers[i];
				}
				for(int i = 0; i < 10; i++)
				{
					if(usernames[i].indexOf(".") != -1)
					{
						usernames[i] = usernames[i].substring((usernames[i].indexOf(".")+1));
						usernames[i] = usernames[i].trim();
					}
				}
			}
			
			// This method writes to the score file with the score array
			public void writeScoreFile()
			{
				writer = info.writeFile(scoreFileName, false);
				for(int i = 0; i < 10; i++)
				{
					writer.println(scores[i]);
				}
				
				writer.close();
			}
			
			// This method writes to the user file with the users array
			public void writeUserFile()
			{
				writer = info.writeFile(userFileName, false);
				
				for(int i = 0; i < 10; i++)
				{
					if(i != 9)
					{
						writer.println("0" + (i+1) + ". " + usernames[i]);
					}
					else
					{
						writer.println((i+1) + ". " + usernames[i]);
					}
				}
				writer.close();
			}
		}
		public void windowClosed(WindowEvent evt){}
		public void windowActivated (WindowEvent evt) {}    
		public void windowDeactivated (WindowEvent evt) {}
		public void windowIconified (WindowEvent evt) {}   
		public void windowDeiconified (WindowEvent evt) {}   
		public void windowOpened (WindowEvent evt) {} 
		
		// This method allows the program to create another question frame when the question frame is closed
		public void windowClosing (WindowEvent evt)
		{
			questCheck = false;
		} 
	}
	
	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseReleased(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
	
	/* This method moves the user avatar based on the user's key presses and 
	 * creates the question frame if there is a crewmate in that area
	 */
	public void keyTyped(KeyEvent evt)
	{
		char key = evt.getKeyChar();
		
		if(key == 'w' && userY >= 60)
		{
			userY -= 10;
		}
		else if(key == 'a' && userX >= 10)
		{
			userX -= 10;
		}
		else if(key == 's' && userY <= 650)
		{
			userY += 10;
		}
		else if(key == 'd' && userX <= 1313)
		{
			userX += 10;
		}
		
		else if(key == ' ')
		{
			number = 0;
			boolean check = false;
			
			for(int i = 0; i < numCrew; i++)
			{
				if(userX<=xPos[i]+50 && userX >= xPos[i] 
					&& userY<=yPos[i]+50 && userY >= yPos[i])
				{
					number = i;
					check = true;
				}
			}
			
			if(!(crewNames[number].equals("empty.png")) 
				&& crewNames[number].indexOf("dead") == -1 && check == true 
				&& questCheck == false)
			{
				questFrame = new JFrame("Questions");
				questCheck = true;
				makeQuestFrame(number+1);
			}
			repaint();
		}
		
		number = 0;
		boolean check = false;
			
		for(int i = 0; i < numCrew; i++)
		{
			if(userX<=xPos[i]+50 && userX >= xPos[i] && userY<=yPos[i]+50 && userY >= yPos[i])
			{
				number = i;
				check = true;
			}
		}
			
		if(!(crewNames[number].equals("empty.png")) && crewNames[number].indexOf("dead") == -1 && check == true)
		{
			checkCrew = true;
		}
		repaint();
	}
	public void keyReleased(KeyEvent evt){}
	
	/*This method moves the user avatar based on the user's key presses and 
	 * creates the question frame if there is a crewmate in that area
	 */
	public void keyPressed(KeyEvent evt)
	{
		int code = evt.getKeyCode();
		
		if(code == 38 && userY >= 60)
		{
			userY -= 10;
		}
		else if(code == 37 && userX >= 10)
		{
			userX -= 10;
		}
		else if(code == 40 && userY <= 650)
		{
			userY += 10;
		}
		else if(code == 39 && userX <= 1313)
		{
			userX += 10;
		}
		else if(code == 32)
		{
			number = 0;
			boolean check = false;
			
			for(int i = 0; i < numCrew; i++)
			{
				if(userX<=xPos[i]+50 && userX >= xPos[i] && userY<=yPos[i]+50 && userY >= yPos[i])
				{
					number = i;
					check = true;
				}
			}
			
			if(!(crewNames[number].equals("empty.png")) && crewNames[number].indexOf("dead") == -1 && check == true && questCheck == false)
			{
				questFrame = new JFrame("Questions");
				questCheck = true;
				makeQuestFrame(number+1);
			}
			repaint();
		}
		number = 0;
		boolean check = false;
			
		for(int i = 0; i < numCrew; i++)
		{
			if(userX<=xPos[i]+50 && userX >= xPos[i] && userY<=yPos[i]+50 && userY >= yPos[i])
			{
				number = i;
				check = true;
			}
		}
			
		if(!(crewNames[number].equals("empty.png")) && crewNames[number].indexOf("dead") == -1 && check == true)
		{
			checkCrew = true;
		}
		repaint();
	}
	
	// This method finds the file with the settings
	public void findSetFile()
  	{
		File setFile = new File(setFileName);
		try
		{
			input = new Scanner(setFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.\n\n", setFileName);
			System.exit(1);
		}
		numCrew = Integer.parseInt(input.next());
		numPoints = Integer.parseInt(input.next());
		numKills = Integer.parseInt(input.next());
	}
	
	/* This paintComponent starts the timer and also creates the images on the game panel
	 * */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(checkNew == true)
		{
			timer.stop();
			time = 0;
			points = 0;
			killCheck = 0;
			scoreCheck = 0;
			timeCheck = 0;
			startTimer = true; 
			userX = 683;
			userY = 500;
			crewNames = new String[]{"red.png", "blue.png", "green.png", "pink.png", "orange.png" 
			, "yellow.png", "tan.png", "white.png", "purp.png", "brown.png"
			, "cyan.png", "lime.png", "maroon.png", "rose.png", "banana.png"};
		
			deadNames = new String[]{"deadred.png", "deadblue.png", "deadgreen.png"
				, "deadpink.png", "deadorange.png" , "deadyellow.png", "deadtan.png"
				, "deadwhite.png", "deadpurp.png", "deadbrown.png", "deadcyan.png"
				, "deadlime.png", "deadmaroon.png", "deadrose.png", "deadbanana.png"};
			createImage();
			doneFrame.dispose();
			if(questFrame != null)
			{
				questFrame.dispose();
			}
			
			questCheck = false;
			findRandomNumbers();
			cards.show(panelCards, "Setting");
			checkNew = false;
		}
		timer.start();
		
		findSetFile();
		findMapFile();
		if(mapCheck == true)
		{
			createImage();
		}
		
		g.drawImage(mapImage, 0,50,1366,660,this);
		if(mapFileName.equals("skeld.jpg"))
		{
			xPos = new int [] {180, 650, 730, 1250, 530, 545, 300, 300
				, 400, 875, 1040, 870, 970, 1040, 465};
			yPos = new int [] {360, 210, 600, 300, 465, 290, 230, 490
				, 375, 580, 500, 420, 320, 210, 225};
		}
			
		else if(mapFileName.equals("mirah.jpg"))
		{
			xPos = new int [] {80, 270, 460, 650, 245, 545, 680, 860
				, 930, 1010, 1200, 1060, 765, 590, 855};
			yPos = new int [] {510, 530, 375, 400, 610, 620, 590, 170
				, 340, 255, 570, 620, 570, 515, 265};
		}
		else if(mapFileName.equals("polus.jpg"))
		{
			xPos = new int [] {170, 590, 785, 1090, 855, 1250, 1245
				, 565, 445, 270, 70, 640, 765, 950, 540};
			yPos = new int [] {215, 160, 195, 279, 285, 400, 305, 525
				, 350, 400, 515, 385, 625, 470, 245};
		}
		else if(mapFileName.equals("airship.jpg"))
		{
			xPos = new int [] {70, 325, 225, 390, 645, 1000, 1250, 930
				, 718, 530, 250, 770, 280, 970, 850};
			yPos = new int [] {355, 515, 570, 550, 545, 500, 405, 225
				, 100, 240, 375, 250, 240, 340, 490};
		}
		for(int i = 0; i < numCrew; i++)
		{
			g.drawImage(crewImages[i], xPos[i],yPos[i],50,50,this);
		}
		
		g.drawImage(userImage, userX, userY, 50, 50, this);
		mapCheck = false;
		
		if(checkCrew == true)
		{
			g.setColor(Color.GREEN);
			g.drawRect(xPos[number], yPos[number] , 50, 50);
			checkCrew = false;
		}
		else
		{
			repaint();
		}
		requestFocusInWindow();
	}
}

class Information
{
	private String userFileName, scoreFileName; // file names with the 
		// scores and users
	private Scanner input; // Scanner
	private PrintWriter pWriter; // PrintWriter
	
	public Information()
	{
		userFileName = new String("users.txt");
		scoreFileName = new String("scores.txt");
		input = null;
		pWriter = null;
	}
	
	// Method that allows seperate classes to find the most recent version of the users
	public JTextArea getUserData()
	{
		JTextArea userArea = new JTextArea("");
		File userFile = new File(userFileName);
		try
		{
			input = new Scanner(userFile);
		}
		catch(IOException e)
		{
			System.err.println("Error: Unable to write to userArea:");
			System.exit(2);
		}
		while(input.hasNextLine())
		{
			userArea.setText(userArea.getText() + input.nextLine() + "\n");
		}	
		userArea.setMargin(new Insets(0,150,100,0));
		userArea.setText("\nTop Scorers:\n" + userArea.getText());
		userArea.setEditable(false);	
		return userArea;
	}
	
	// Method that allows seperate classes to find the most recent version of the users
	public JTextArea getScoreData()
	{
		JTextArea scoreArea = new JTextArea("");
		File scoreFile = new File(scoreFileName);
		try
		{
			input = new Scanner(scoreFile);
		}
		catch(IOException e)
		{
			System.err.println("Error: Unable to write to userArea:");
			System.exit(2);
		}
		while(input.hasNextLine())
		{
			scoreArea.setText(scoreArea.getText() + input.nextLine() + "\n");
		}	
		scoreArea.setMargin(new Insets(0,150,100,0));
		scoreArea.setText("\nScores:\n" + scoreArea.getText());
		scoreArea.setEditable(false);
		return scoreArea;
	}
	
	// This method returns a scanner which is created using the file name as a parameter
	public Scanner findFile(String inFileName)
	{
		File inFile = new File(inFileName);
		try
		{
			input = new Scanner(inFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.\n\n", inFileName);
			System.exit(1);
		}
		
		return input;
	}
	
	// This method returns a PrintWriter based off the file name that 
	// is sent into the method and aa boolean to check if 
	// the PrintWriter should append or overwrite
	public PrintWriter writeFile(String inFileName, boolean isFileWriter)
	{
		File inFile = new File(inFileName);
		try
		{
			pWriter = new PrintWriter(new FileWriter(inFile,isFileWriter));
		}
		catch(IOException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.\n\n", inFileName);
			System.exit(3);
		}
		return pWriter;
	}
}
