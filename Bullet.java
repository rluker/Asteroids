import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Bullet extends Circle{

	public static final int RADIUS = 3;

	private double rotation;


	public Bullet(Point center, double rotation) {
		super(center, RADIUS);
		this.rotation = rotation;
	}

	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, 2 * RADIUS, 2 * RADIUS); 

	}

	@Override
	public void move() {
		center.x += 6 * Math.cos(Math.toRadians(rotation));
		center.y += 6 * Math.sin(Math.toRadians(rotation));

		//Point[] points = Ship.center;

	}
	
	public Point getCenter() {
		return center;
	}
	
	public boolean outOfBounds() {
		
		boolean outOfBounds = false;
		
		if(center.x >= Asteroids.SCREEN_WIDTH)
			outOfBounds = true;
		else if (center.x <= 0) 
			outOfBounds = true;
		
		if(center.y >= Asteroids.SCREEN_HEIGHT)
			outOfBounds = true;
		else if (center.y <= 0) 
			outOfBounds = true;
		
		return outOfBounds;
	}


}
