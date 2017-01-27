import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;

public abstract class Sprite
{
	int x; 
	int y;
	int h;
	int w;
	boolean collision = false;
	boolean destroy = false;
    
	Sprite(){
    	
	}

	public abstract void update();
	
	public abstract void draw(Graphics g) throws IOException;
	
	abstract boolean isPie();
	
	abstract boolean isTube();
	
	abstract boolean isBird();
	
	abstract Sprite replicate(LinkedList<Sprite> new_model_sprites);
	
	//hit detection methods.  Hit detection is carried out in the Tube class
	public boolean doesIntersect(Sprite a, Sprite b){
		if((a.x + a.w) < b.x){
			return false;
		}
	
		if(a.x > (b.x + b.w)){
			return false;
		}
		
		if((a.y + a.h) < b.y){
			return false;
		}
		
		if(a.y > (b.y + b.h)){
		
			return false;
		}
		return true;
	}
}
