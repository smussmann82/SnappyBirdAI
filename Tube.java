import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

class Tube extends Sprite
{
	boolean direction;
	static Image tube_up = null;
	static Image tube_down = null;
	Random rng;
	LinkedList<Sprite> sprites;
      
	Tube(Random r, LinkedList<Sprite> s){
		super();
		sprites = s;
  	  	try{
  	  		Tube.tube_up = ImageIO.read(new File("tube_up.png"));
  	  		Tube.tube_down = ImageIO.read(new File("tube_down.png"));
  	  	}catch( IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
  	  	}
		this.rng = r;
		this.w = 55; //tube width
		this.h = 400; //tube height
		this.direction = rng.nextBoolean();
		this.x = 600;
		if(this.direction == true){
			this.y = rng.nextInt(350-200)+200;
		}else if(this.direction == false){
			this.y = rng.nextInt(350-200)-200;
		}
	}
	
	Tube(Tube that, LinkedList<Sprite> new_model_sprites){
		this.x = that.x; 
		this.y = that.y;
		this.h = that.h;
		this.w = that.w;
		this.collision = that.collision;
		this.destroy = that.destroy;
		this.direction = that.direction;
		this.rng = that.rng;
		this.sprites = new_model_sprites;
	}
	
	Sprite replicate(LinkedList<Sprite> new_model_sprites)
	{
		return new Tube(this, new_model_sprites);
	}
	
	public void update() {
		// animate the tube moving left
		this.x -= 10; //was 15
		this.detectCol();
    	  
		// animate the tube to retract when hit by pie
		if(this.collision == true){
			if(this.direction == true){
				this.y += 10;
			}else if(this.direction == false){
				this.y -= 10;
			}
		}
    	  
		// determine if the tube should be destroyed
		if(this.direction == true){
			if(this.x < -100 || this.y < 0){
				this.destroy = true;
			}
		}else if(this.direction == false){
			if(this.x < -100 || this.y > 500){
				this.destroy = true;
			}
		}
	}
	
	boolean isPie(){
		return false;
	}
	
	boolean isTube(){
		return true;
	}
	
	boolean isBird(){
		return false;
	}
      
	public void draw(Graphics g) throws IOException{
		if(direction == true){
			g.drawImage(Tube.tube_up, this.x, this.y, null);
		}else if(direction == false){
			g.drawImage(Tube.tube_down, this.x, this.y, null);
		}
	}
	
	public void detectCol(){
		Iterator<Sprite> it = sprites.iterator();
		while(it.hasNext()){
			Sprite s = it.next();
			//check if sprite is Tube class
			if(s.isPie() == true){
				//check if pie intersects with tube
				this.collision = super.doesIntersect(s, this);
				if(this.collision == true){
					s.destroy = true;
				}
			}
			//check if sprite is Bird class
			if(s.isBird() == true){
				if(s.collision == false){
					s.collision = super.doesIntersect(s, this);
				}
			}
		}
	}

}


