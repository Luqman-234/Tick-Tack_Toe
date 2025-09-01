package tick;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Frame extends JFrame {
	private static final ImageIcon xshape = new ImageIcon("src/tick/xshape.png"), 
			circle = new ImageIcon("src/tick/circle.png"),
			blank =  new ImageIcon("src/tick/blank.png"),
			circle_hover = new ImageIcon("src/tick/circle_hover.png"),
			xshape_hover = new ImageIcon("src/tick/xshape_hover.png"),
			blank_hover =  new ImageIcon("src/tick/blank_hover.png");
	
	private static final JPanel[] panel = {new JPanel(), new JPanel(), new JPanel()};
	private static final JLabel button[] = new JLabel[9];
	private static final JButton quiteB = new JButton("quite"), 
			menuB[] = {new JButton(), new JButton()};
	private static Container contentP;
	private static final JLabel label = new JLabel("Welcome");
	public static boolean allow = false;
	public static int picked = -1;
	public static int clicked;
	
	Frame(String s) {
		super(s);
		quick(menuB[0], 1);
		quick(menuB[1], 2);
		menuB[0].setVisible(false);
		menuB[1].setVisible(false);
		panel[1].add(menuB[0]);
		panel[1].add(menuB[1]);
		contentP = getContentPane();
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        GridBagLayout layout = new GridBagLayout();
        panel[0].setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for(int i = 0; i<9; i++) {
        	button[i] = new JLabel(blank);
        	button[i].setPreferredSize(new Dimension(100, 100));
        	button[i].setBorder(new LineBorder(Color.BLACK, 2));
        	hover(i);
        	gbc.fill = GridBagConstraints.BOTH;
        	gbc.gridx=i%3;
        	gbc.gridy=i/3;
        	panel[0].add(button[i], gbc);
        }
        panel[2].add(quiteB, BorderLayout.CENTER);
        quite(quiteB);
        panel[1].add(label, BorderLayout.NORTH);
        contentP.add(panel[0], BorderLayout.CENTER);
        contentP.add(panel[1], BorderLayout.NORTH); 
        contentP.add(panel[2], BorderLayout.SOUTH); 
        setLocationRelativeTo(null);
	}
	public boolean check() {
		ImageIcon icon;
		boolean full = false;
		for(int i = 0; i < 9; i++) {
			icon = (ImageIcon) button[i].getIcon();
			if(icon == blank || icon == blank_hover) {
				full = true;
				break;
			}
		}
		return full;
	}
	public int won() {
		ImageIcon arr[] = new ImageIcon[3];
		arr[1] = circle;
		arr[2] = xshape;
		for(int i = 1;i < 3;i++) {
			//rows
			if(row(0, 1, arr[i])) {
				return i;
			}
			if(row(3, 1, arr[i])) {
				return i;
			}
			if(row(6, 1, arr[i])) {
				return i;
			}
			
			//columns
			if(row(0, 3, arr[i])) {
				return i;
			}
			if(row(1, 3, arr[i])) {
				return i;
			}
			if(row(2, 3, arr[i])) {
				return i;
			}
			//sideways
			if(row(0, 4, arr[i])) {
				return i;
			}
			
			if(row(2, 2, arr[i])) {
				return i;
			}
		}
		return 0;
	}
	private boolean row(int i, int x, ImageIcon icon) {
		if((button[i].getIcon()==icon)&&(button[i+x].getIcon()==icon)&&(button[i+x+x].getIcon()==icon)) {
			return true;
		}
		return false;
	}
 	public void blank_out() {
		for(int i = 0; i < 9; i++) {
			button[i].setIcon(blank);
		}
	}
 	public static int options(String[] args) throws InterruptedException {
		clicked = 0;
		menuB[0].setText(args[0]);
		menuB[1].setText(args[1]);
		menuB[0].setVisible(true);
		menuB[1].setVisible(true);
		while(clicked == 0) {
			Thread.sleep(1);
		}
		menuB[0].setVisible(false);
		menuB[1].setVisible(false);
		panel[1].revalidate();
		panel[1].repaint();
		return clicked;
	}
	public static void quick(JButton b, int x) {
		b.addMouseListener(new MouseAdapter() {
	   		 @Override
	   		 public void mouseClicked(MouseEvent e) {
	   			 clicked = x;
	   		 }
	   		 @Override
	            public void mouseEntered(MouseEvent e) {
	   			b.setBackground(Color.GRAY); 
	            }
	    		 @Override
	            public void mouseExited(MouseEvent e) {
	    			 b.setBackground(null);
	            }
	       });
	}
	public void ai_turn() {
		ImageIcon icon;
		for(int i = (int)(Math.random() * 9); true; i =(int)(Math.random() * 9)) {
			icon = (ImageIcon) button[i].getIcon();
			if(icon==blank_hover|| icon == blank) {
			button[i].setIcon(xshape);
			break;
			}
		}
	}
	public void user_turn() throws InterruptedException {
		allow = true;
		while(picked == -1) {
			Thread.sleep(1);
		}
		allow = false;
		picked = -1;
	}
	public void setTopText(String line) {
		label.setText(line);
	}
	
	private static void hover(int i) {
		JLabel b = button[i];
		MouseAdapter adapt = new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
    			 if(allow == true && picked == -1) {
    				 if(b.getIcon() == blank_hover || b.getIcon() == blank) {
    						button[i].setIcon(circle_hover);
    						picked = i;
    						}
    			 }
            }
			@Override
            public void mouseEntered(MouseEvent e) {
    			 ImageIcon icon = (ImageIcon) b.getIcon();
            	if(icon == blank)
            		b.setIcon(blank_hover);
            	else if(icon == xshape)
            		b.setIcon(xshape_hover);
            	else 
            		b.setIcon(circle_hover);
            }
    		 @Override
            public void mouseExited(MouseEvent e) {
    			ImageIcon icon = (ImageIcon) b.getIcon();
            	if(icon == blank_hover)
            		b.setIcon(blank);
            	else if(icon == xshape_hover)
            		b.setIcon(xshape);
            	else 
            		b.setIcon(circle);
            }
        };
        b.addMouseListener(adapt);
        
	}
	private static void quite(JButton b) {
    	b.addMouseListener(new MouseAdapter() {
    		 @Override
    		 public void mouseClicked(MouseEvent e) {
    			 System.exit(0);
    		 }
    		 @Override
             public void mouseEntered(MouseEvent e) {
    			 b.setBackground(Color.GRAY); 
             }
     		 @Override
             public void mouseExited(MouseEvent e) {
     			b.setBackground(null);
             }
        });
	}
}
