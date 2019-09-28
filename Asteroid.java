//Rachel Luker - April 2018 - Homework #5 - Milestone #1
//move() and paint() methods
//the asteroids now enter the screen from the opposite side when
//they move off of the screen

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

public class Asteroid extends Polygon {

	//CONSTRUCTOR
	//shape: an array of points, corresponding to vertices
	//position: offset between origin and center of Asteroid object
	//rotation: in degrees from the shape array
	public Asteroid(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
	}

	//Paints the asteroid object on the screen using drawPolygon()
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

		//brush.fillPolygon(xx, yy, shape.length); //fills to a solid color
	}

	//moves the asteroid to the right, down and rotates clockwise
	@Override
	public void move() {
		//position.x++;
		//position.y++;
		//rotation--;

		position.x += 2 * Math.cos(Math.toRadians(rotation));
		position.y += 2 * Math.sin(Math.toRadians(rotation));

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

}
