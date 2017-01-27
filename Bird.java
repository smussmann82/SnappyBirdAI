import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Graphics;

class Bird extends Sprite{
	static Image image1 = null;
	static Image image2 = null;
	static Image image_death = null;
	int counter;
	double velocity;
	
	//constructor
	Bird(){
		super();
		this.h = 45; //bird height (in pixels)
		this.w = 60; //bird width (in pixels)
		try{
			if(Bird.image1 == null){
				Bird.image1 = ImageIO.read(new File("bird1.png"));
			}
			if(Bird.image2 == null){
				Bird.image2 = ImageIO.read(new File("bird2.png"));
			}
			if(Bird.image_death == null){
				Bird.image_death = ImageIO.read(new File("feathers.png"));
			}
		}catch( IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	//copy constructor
	Bird(Bird that){
		this.x = that.x;
		this.y = that.y;
		this.h = that.h;
		this.w = that.w;
		this.collision = that.collision;
		this.destroy = that.destroy;
		this.counter = that.counter;
		this.velocity = that.velocity;	
	}
	
	//replicate method from abstract Sprite class
	Sprite replicate(LinkedList<Sprite> new_model_sprites){
		return new Bird(this);
	}
	
	//update method
	public void update() {
		this.counter++;
		this.velocity += 3.0;
		this.y += this.velocity;
		if(this.y > 500 || this.y + this.h < 0){
			this.collision = true;
		}
		
		if(this.collision == true){
			this.deathAnimation();
		}
	}
	
	//methods to check the type of this class (all 3 required by abstract Sprite class)
	boolean isPie(){
		return false;
	}
	
	boolean isTube(){
		return false;
	}
	
	boolean isBird(){
		return true;
	}
	
	//method to draw bird
	public void draw(Graphics g) throws IOException{
		if(this.collision == true){
			g.drawImage(Bird.image_death, this.x, this.y, null);
		}else if(this.counter%4 == 0){
			g.drawImage(Bird.image1, this.x, this.y, null);
		}else{
			g.drawImage(Bird.image2, this.x, this.y, null);
		}
	}
	
	public void flap() {
		if(this.collision == false){
			this.velocity = -30;
		}
	}
	
	//method to run the death animation when the bird collides with a tube or goes offscreen
	public void deathAnimation(){
		this.x += 10;
		if(this.x > 600){
			System.exit(0);
		}
		if(this.y >=460){
			this.velocity = -25;
		}
	}
}
