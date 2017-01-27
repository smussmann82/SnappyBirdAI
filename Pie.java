import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Graphics;

class Pie extends Sprite{
	
	static Image pie_image = null;
	double y_velocity;
	Bird bird;
	
	Pie(Bird b){
		super();
		this.bird = b;
		try{
			if(Pie.pie_image == null){
				Pie.pie_image = ImageIO.read(new File("pie.png"));
			}
		}catch(IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
		this.x = bird.x;
		this.y = bird.y;
		this.h = 30;
		this.w = 42;	
	}
	
	//copy constructor for Pie
	Pie(Pie that, Bird b){
		this.x = that.x;
		this.y = that.y;
		this.h = that.h;
		this.w = that.w;
		this.collision = that.collision;
		this.destroy = that.destroy;
		this.y_velocity = that.y_velocity;
		this.bird = b; //bird is copied from the new_model_sprites that is sent to the replicate method.  actual deep copy of bird is made in model class when the deep copy of the sprites LinkedList is made
	}
	
	Sprite replicate(LinkedList<Sprite> new_model_sprites){
		Sprite sFirst = new_model_sprites.getFirst();
		if(!(sFirst instanceof Bird)){
			throw new IllegalArgumentException("I thought the bird would be first");
		}
		Bird temp_bird = (Bird)sFirst;
		return new Pie(this, temp_bird);
	}
	
	public void update(){
		this.y -= 10;
		this.x += 15;
		this.y_velocity+=2.4;
		this.y += this.y_velocity;
		if(this.x > 500 || this.y > 500 ){
			this.destroy = true;
		}
	}
	
	boolean isPie(){
		return true;
	}
	
	boolean isTube(){
		return false;
	}
	
	boolean isBird(){
		return false;
	}
	
	public void draw(Graphics g) throws IOException {
		g.drawImage(Pie.pie_image, this.x, this.y, null);
	}
}