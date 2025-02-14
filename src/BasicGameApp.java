
//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class BasicGameApp implements Runnable {

	//Variable Definition Section
	//Declare the variables used in the program
	//You can set their initial values too

	//Sets the width and height of the window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
	public JPanel panel;

	public BufferStrategy bufferStrategy;
	public Image birdpic;
	public Image bird2pic;
	public Image BackgroundPic;
	public Image brid3pic;
	//Declare the objects used in the program
	//These are things that are made up of more than one variable type
	private Bird bird;
	private Bird bird2;
	private Bird bird3;

	// Main method definition
	// This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
	}



	public BasicGameApp() {

		setUpGraphics();

		//variable and objects
		//create (construct) the objects needed for the game and load up

		birdpic = Toolkit.getDefaultToolkit().getImage("Bird1.jpeg"); //load the picture
		bird2pic= Toolkit.getDefaultToolkit().getImage("Hawk1.jpg");
		brid3pic = Toolkit.getDefaultToolkit().getImage("SmallBird.jpg");
		BackgroundPic= Toolkit.getDefaultToolkit().getImage("Sky.jpeg");
		bird = new Bird(300, 20);
		bird2 = new Bird(100, 20);
		bird3 = new Bird(50,600);
	}// BasicGameApp()


//*******************************************************************************

	public void run() {


		while (true) {

			moveThings();  //moves all the birds
			render();  // renders the graphics for birds and sky
			pause(20); // stop for 10 ms
		}
	}
	public void moveThings() {
		//calls the move methods+collisions and small bird methods
		collisions();
		smallBird();
		bird.move();
		bird.bounce();
		bird2.move();
		bird2.wrap();
        bird3.move();
       bird3.smallbirdMoves();
	   bird3.wrap();
	}
	//fight count is outside of if statement so it doesnt reset allows to keep constant count of number of total fights
private int Fightcount;


	// sees if the rectangles around the birds touch, if they do then it adds to the fight count and displays it, sends the birds in opposite directions, changes the speed and size of the birds
	public void collisions(){
		if(bird.rec.intersects(bird2.rec)&& bird.iscrash== false && bird2.isAlive && bird.isAlive){
		Fightcount++;
			System.out.println("Bird fight number " + Fightcount);
			bird.dx = -bird.dx;
			bird.dy = -bird.dy;
			bird2.dx = -bird2.dx;
			bird2.dy = -bird2.dy;
			bird.dx = 2+bird.dx;
			bird.dy = 2+bird.dy;
			bird2.height = bird2.height+2;
			bird2.width = bird2.width+2;
			bird.iscrash = true;
			bird2.isAlive = true;
			bird3.isAlive = true;
		}
		// stops birds from stuttering why they hit
		if(!bird.rec.intersects(bird2.rec)){
			bird.iscrash=false;
		}


	}
	// two counters for when each big bird hits the little one. brown bird is Sbird2 and blue bird is SBird1
	public int SBird1;
	public int SBird2;
//makes it so that if the Brown bird hits the little bird it sends the brown bird away, resets the small bird to a random ypos, and adds to the counter for that bird + displays the count
	public void smallBird(){
		if (bird3.rec.intersects(bird2.rec)&& bird.iscrash== false && bird2.isAlive && bird.isAlive){
			SBird2++;
			System.out.println("Small birds eaten by brown bird " + SBird2);

			bird2.dx = -bird2.dx;
			bird2.dy = -bird2.dy;
			bird2.iscrash = true;
			bird3.dx=-bird3.dx;
			bird3.ypos = (int) (10 + (Math.random() * (650 - 10)));
			wincon();
		}
		//makes it so that if the Blue bird hits the little bird it sends the Blue bird away, resets the small bird to a random ypos, and adds to the counter for that bird + displays the count

		if (bird3.rec.intersects(bird.rec)&& bird.iscrash== false && bird2.isAlive && bird.isAlive){
SBird1++;
			System.out.println("Small birds eaten by blue bird " + SBird1);
			bird.dx = -bird.dx;
			bird.dy = -bird.dy;
			bird.iscrash = true;
			bird3.dx=-bird3.dx;
			bird3.ypos = (int) (10 + (Math.random() * (650 - 10)));
			wincon();
}



	}
	// a check to see if one of the big birds has hit the small one 6 or more times, if it has then it souts that that bird won and stops the small
	public void wincon(){
		if (SBird1>=6){
			System.out.println("Blue bird wins!!!!");
			bird3.isAlive=false;

	}
		if (SBird2>=6){
			System.out.println("Brown bird wins!!!!");
			bird3.isAlive=false;
		}


	}

	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	//Graphics setup
	private void setUpGraphics() {
		frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

		panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
		panel.setLayout(null);   //set up the screen layout

		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);
		panel.add(canvas);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		// sets up the graphics
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		System.out.println("DONE graphic setup");

	}


	//renders  the birds
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(BackgroundPic,0,0,WIDTH,HEIGHT,  null);

		//draw the Birds if they are alive
		g.drawImage(birdpic, bird.xpos, bird.ypos, bird.width, bird.height, null);


		if(bird2.isAlive == true) {
			g.drawImage(bird2pic, bird2.xpos, bird2.ypos, bird2.width, bird2.height, null);
		}


		if (bird3.isAlive == true) {
			g.drawImage(brid3pic, bird3.xpos, bird3.ypos, bird2.width, bird3.height, null );
		}
		g.dispose();
		bufferStrategy.show();

	}


}
