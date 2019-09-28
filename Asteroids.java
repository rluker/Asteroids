//Rachel Luker - APRIL 2018 - Homework #5 - Milestone #1
//Creates random asteroids and a ship that re-enters the screen after leaving.
//The ship turns green when it makes contact with an asteroid.

/*
CLASS: AsteroidsGame
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.ArrayList;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	private Ship ship;

	static int counter = 0;
	private static final int COLLISION_PERIOD = 35;

	//lives of the ship
	private int shipLives = 5;
	
	//keeps track of the first collision with an asteroid
	private boolean loseLife = false;

	//tracking asteroid collisions
	private boolean collide = false;
	private int collisions = COLLISION_PERIOD;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();

	private Star[] randomStars = new Star[100];

	private ArrayList<Bullet> bullets;

	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(10,50,30);

		//create random stars
		randomStars = createStars(150, 3);

		//Asteroid ship
		ship = createShip();

		//register the ship as a key listener
		this.addKeyListener(ship);
	}

	// private helper method to create the Ship
	private Ship createShip() {
		// Look of ship
		Point[] shipShape = {
				new Point(0, 0),
				new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
				new Point(0, Ship.SHIP_HEIGHT),
				new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
		};
		// Set ship at the middle of the screen
		Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
		int startingRotation = 0; // Start facing to the right
		return new Ship(shipShape, startingPosition, startingRotation);
	}

	// Create a certain number of stars with a given max radius
	public Star[] createStars(int numberOfStars, int maxRadius) {
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; ++i) {
			Point center = new Point
					(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);

			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1) {
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}
		return stars;
	}

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	public void paint(Graphics brush) {
		collide = false;

		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter: " + counter,10,10);

		// display the random stars
		for (Star star : randomStars) {
			star.paint(brush,Color.white);
		}

		//create stars and make the stars twinkle
		if (counter%5 == 0) {
			randomStars = createStars(200, 3);
			for (Star star : randomStars) {
				star.paint(brush,Color.white);
			}
		}

		// display random asteroids and check for collisions 
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			if (asteroid.collision(ship)) {
				collide = true;
				if (loseLife == false) {
					if (shipLives > 0) {
						loseLife = true;
						shipLives--;
					}
				}
			}
		}

		// shoot the bullets
		bullets = ship.getBullets();
		ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();
		java.util.List<Asteroid> removeAsteroids = new ArrayList<Asteroid>();

		for (Bullet bullet : bullets) {
			bullet.paint(brush, Color.magenta);
			bullet.move();

			//adds any bullets that go off-screen to a temporary list to be removed
			if (bullet.outOfBounds()) {
				removeBullets.add(bullet);
			}

			//add any bullets that collide with an asteroid to a list to be removed
			for (Asteroid asteroid : randomAsteroids) {
				if (asteroid.contains(bullet.getCenter())) {
					removeAsteroids.add(asteroid);
					removeBullets.add(bullet);
				}
			} 
		}

		for(Asteroid asteroid : removeAsteroids) {
			randomAsteroids.remove(asteroid);
		}

		//when i remove the bullets, they are removed right away??
		for(Bullet bullet : removeBullets) {
			bullets.remove(bullet);
		}

		//if the ship has a collision with an asteroid
		if (collide == true) {
			collisions--;
			ship.paint(brush, Color.green);
			ship.move();

			if (collisions <= 0) {
				ship.paint(brush, Color.magenta);
				collide = false;
				collisions = COLLISION_PERIOD;
			}

		} else {
			//paint and move the ship
			ship.paint(brush, Color.magenta);
			ship.move();
			if(loseLife == true) {
				loseLife = false;
			}
		}

		//if the game is over, prints out 'You won!' or 'You lost!'
		if (randomAsteroids.size() <= 0 && shipLives > 0) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("You won!", 388, 288);
		} else if (randomAsteroids.size() > 0 && shipLives <= 0) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.red);
			brush.drawString("You lost!", 388, 288);
		}

		//prints out the total lives left during the game
		brush.setColor(Color.white);
		brush.drawString("Asteroids: " + randomAsteroids.size(), 10, 22);
		brush.drawString("Lives: " + shipLives, 10, 34);
	}

	//main method plays the game
	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}