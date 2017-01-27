import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class Controller implements MouseListener
{
	Model model;
	Controller(Model m) {
		this.model = m;
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			this.model.onClick();
		}else if(e.getButton() == MouseEvent.BUTTON3){
			this.model.rightClick();
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	//update method for 
	public void update(){
		int bestresult = 0; //default value of evaluateAction
		Model.BirdAction b = Model.BirdAction.do_nothing; //default action to choose if nothing better is returned by evaluateAction
		
		for(Model.BirdAction a : Model.BirdAction.values()){
			int counter = 0; //track the recursion depth
			//I set d=18 and k=6 for assignment submission.  The bird dies about 1 minute 40 seconds into the game with these settings.  
			//At d=20 and k=4 I haven't seen the bird die yet, but it slows down the game considerably.
			int d = 18; //iteration max depth
			int k = 6; //check every k 
			int result = model.evaluateAction(counter, d, k, model, a); //call evaluateAction and return the result
			if(result > bestresult){
				bestresult = result; //set the bestresult to result if evaluateAction returned better action
				b = a; //also set the associated action used for calling evaluateAction to b
			}
		}
		System.out.println(b); //print the chosen action to the console
		
		//execute the chosen action
		if(b.equals(Model.BirdAction.do_nothing)){

		}else if(b.equals(Model.BirdAction.flap)){
			model.onClick();
		}else if(b.equals(Model.BirdAction.throw_pie)){
			model.rightClick();
		}else if(b.equals(Model.BirdAction.flap_and_throw)){
			model.onClick();
			model.rightClick();
		}
		
	}
	
}
