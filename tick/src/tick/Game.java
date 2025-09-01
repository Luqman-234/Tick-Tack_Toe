package tick;

public class Game {
	private static boolean end(Frame frame) throws InterruptedException {
		if(!frame.check()) {
			frame.blank_out();
			frame.setTopText("Ends in a tie");
			Thread.sleep(1000);
			return true;
		}
		int x = frame.won();
		if(x == 1) {
			frame.blank_out();
			frame.setTopText("User wins");
			Thread.sleep(1000);
			return true;
		}
		else if(x == 2) {
			frame.blank_out();
			frame.setTopText("AI wins");
			Thread.sleep(1000);
			return true;
		}
		else
		return false;
	}
    public static void main(String[] args) throws InterruptedException {
    	int first_turn;
    	Frame frame = new Frame("Tick Tack Toe");
    	frame.setVisible(true);
    	String[] arg = {"easy", "hard"};
    	while(true) {
    		frame.setTopText("Who do you want to start first");
    		arg[0] = "You";
    		arg[1] = "AI";
    		first_turn = Frame.options(arg);
    		frame.setTopText("Play");
    		Thread.sleep(2000);
    		for(boolean contin = true;contin;) {
    			if(first_turn == 1) {
    				frame.user_turn();
    				Thread.sleep(1000);
    				if(end(frame))
    					break;
    				frame.ai_turn();
    				if(end(frame))
    					break;
    			}
    			else {
    				frame.ai_turn();
    				if(end(frame))
    					break;
    				frame.user_turn();
    				if(end(frame))
    					break;
    				Thread.sleep(1000);
    			}
    		}
    	}
    }
}
