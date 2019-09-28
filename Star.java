//RACHEL LUKER - APRIL 2018 - ASTEROIDS GAME 
//Class the creates star objects for the asteroids game screen.

import java.awt.Color;
import java.awt.Graphics;

public class Star extends Circle {

	public Star(Point center, int radius) {
		super(center, radius);
	}

	@Override
	public void paint(Graphics brush, Color color) {
		//draws the stars (int x, int y, int width, int height)
		brush.setColor(color);
		//fills to a solid color
		brush.fillOval((int)this.center.x, (int)this.center.y, 2 * this.radius, 2 * this.radius); 
	}

	@Override
	public void move() {
		//stars don't move
	}

}
