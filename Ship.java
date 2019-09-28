//Rachel Luker - March 2018 - Homework #5 - Milestone #1
//move() and paint() methods for a ship object
//the ship enters the screen from the opposite side when
//it move off of the screen

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements java.awt.event.KeyListener {

	public static final int SHIP_WIDTH = 40;
	public static final int SHIP_HEIGHT = 25;

	private boolean right, left, forward, spaceBar = false;
	private boolean addBullet = false;
	private ArrayList<Bullet> bullets;

	static int counter = 0;

	public Ship(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		bullets = new ArrayList();
	}

	@Override
	public void paint(Graphics brush, Color color) {
		//gets the points of the Point[]
		Point [] shape = getPoints();
		int [] xx = new int[shape.length];
		int [] yy = new int[shape.length];
		double x,y;

		//separates the points into an x-array and y-array
		for (int i = 0; i < shape.length; i++) {
			Point point = shape[i];
			x = point.x;
			y = point.y;
			xx[i] = (int)x;
			yy[i] = (int)y;
		}

		//draws the polygon (int[x-points], int[y-points], # of points)
		brush.setColor(color);
		brush.drawPolygon(xx, yy, shape.length);
		brush.fillPolygon(xx, yy, shape.length); //fills to a solid color
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (forward == true) {
			//move forward
			position.x += 5 * Math.cos(Math.toRadians(rotation));
			position.y += 5 * Math.sin(Math.toRadians(rotation));
		}
		if (left == true) {
			//rotate left a few degrees
			rotate(-5);
		}
		if (right == true) {
			//rotate right a few degrees
			rotate(5);
		}
		if (spaceBar == true) {
			if (counter == 1) {
				if (addBullet == true) {
					Point[] points = getPoints();
					Point center = points[3]; 
					Bullet bullet = new Bullet(center, this.rotation);
					bullets.add(bullet);
					addBullet = false;
				}
			}
		}

		if (position.x > Asteroids.SCREEN_WIDTH) {
			position.x = 0;
		} else if (position.x <= 0) {
			position.x = Asteroids.SCREEN_WIDTH;
		}

		if (position.y >= Asteroids.SCREEN_HEIGHT) {
			position.y = 0;
		} else if (position.y <= 0) {
			position.y = Asteroids.SCREEN_HEIGHT;
		}

	}

	//returns the array list of bullet objects
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Stub method, not required for the game.
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			// up arrow key
			forward = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		} 
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		} 
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spaceBar = true;
			counter++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			// up arrow key
			forward = false;
		} 
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spaceBar = false;
			addBullet = true;
			counter = 0;
		}
	}

}
