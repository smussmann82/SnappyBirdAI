import java.util.Iterator;
import java.util.LinkedList;

class Model
{
	Bird bird;
	LinkedList<Sprite> sprites;
	Random rng;
	int framecounter = 0;

	Model() {
		//this.rng = new Random(System.currentTimeMillis()); //seed random number generator with System.currentTimeMillis()
		this.rng = new Random(0);
		this.sprites = new LinkedList<Sprite>();
		this.bird = new Bird();
		this.sprites.add(this.bird);
		this.sprites.add(new Tube(this.rng, this.sprites));
	}
	
	//copy constructor for Model
	Model(Model that){
		this.rng = new Random(that.rng);
		this.framecounter = that.framecounter;
		
		//this is the polymorphic copy that we wrote in class
		this.sprites = new LinkedList<Sprite>();
		Iterator<Sprite> it = that.sprites.iterator();
		while(it.hasNext()){
			Sprite s = it.next();
			this.sprites.add(s.replicate(this.sprites)); //this line replicates sprites.  sprites is needed by bird copy constructor
		}
		
		//copy the bird
		Sprite sFirst = this.sprites.getFirst();
		if(!(sFirst instanceof Bird)){
			throw new IllegalArgumentException("I thought the bird would be first");
		}
		this.bird = (Bird)sFirst;
	
	}
	
	public void update(){
		Iterator<Sprite> it = sprites.iterator();
		while(it.hasNext())
		{
			Sprite s = it.next();
			s.update();
			if(s.destroy == true){
				it.remove();
			}
		}
		
		this.framecounter++;
		//I changed the threshold for adding new tubes to 30 from 24 to reduce the number of tubes on screen
		if(this.framecounter % 30 == 0){
			this.sprites.add(new Tube(this.rng, this.sprites));
		}
	}
	
	public void onClick(){
		this.bird.flap();
	}
	
	public void rightClick() {
		if(bird.collision == false){
			this.sprites.add(new Pie(this.bird));
		}
	}
	
	//enumerate the four actions that the bird can choose
	public static enum BirdAction{
		do_nothing, flap, throw_pie, flap_and_throw;
	}
	
	//recursive function to implement game AI
	public int evaluateAction(int counter, int d, int k, Model m, BirdAction a){
		counter++; //increment counter that tracks recursion depth
		if(counter == d){
			if(m.bird.collision == true){
				return 0; //return 0 if bird dies
			}else{
				return 500-Math.abs(m.bird.y-250); //else return value relating to y position of bird
			}
		}else{
			Model modelsim = new Model(m); //make a new copy of the model for simulating different actions
			if(a.equals(BirdAction.do_nothing)){
				//do nothing if do_nothing action is chosen
			}else if(a.equals(BirdAction.flap)){
				modelsim.onClick(); //flap
			}else if(a.equals(BirdAction.throw_pie)){
				modelsim.rightClick(); //throw a pie
			}else if(a.equals(BirdAction.flap_and_throw)){
				modelsim.onClick(); //flap
				modelsim.rightClick(); //throw a pie
			}
			modelsim.update(); //update the model
			
			if((counter%k) != 0){
				int returnvalue = evaluateAction(counter, d, k, modelsim, Model.BirdAction.do_nothing);
				return returnvalue;
			}else{
				//every k times this is called, evaluate all possible actions
				int returnvalue = 0;
				for(BirdAction b : BirdAction.values()){
					int actionvalue = evaluateAction(counter, d, k, modelsim, b);
					if(actionvalue > returnvalue)
					{
						returnvalue = actionvalue;
					}
				}
				return returnvalue;
			}

		}

	}
	
}