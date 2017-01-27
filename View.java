import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Iterator;

class View extends JPanel {
	Model model;

	View(Model m) throws IOException {
		this.model = m;
	}

	public void paintComponent(Graphics g) {
		// Draw the sprites
		try{
			Iterator<Sprite> it = model.sprites.iterator(); 
			while(it.hasNext()){
				Sprite s = it.next();
				s.draw(g);
			}	
		}catch(IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}
