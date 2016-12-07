import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Chess{
	public static void main(String [] args){
		new Layout();
	}
}
 
class Layout extends JFrame implements Runnable , ActionListener{
	public static MyButton btn[][] = new MyButton[8][8];
	public static Player p1,p2;
	ImageIcon bg;
	JPanel pan;
	JPanel pan1;
	JPanel pan2;
	int btnsStatus;
	MyButton clickedBtn;
	
	Layout(){
		super("CHESS");
		btnsStatus=1;
		clickedBtn=null;
		
		setResizable(true);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setPreferredSize(new Dimension(size.width,size.height));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);
		JMenu file = new JMenu("Game");
		JMenuItem item1, item2, item3;
		file.add(item1 = new JMenuItem("New Game"));
		file.add(item2 = new JMenuItem("Resign"));
		file.add(item3 = new JMenuItem("Exit"));

		JMenu file1 = new JMenu("Help");
//		file.add(item1 = new JMenuItem("New Game"));
//		file.add(item2 = new JMenuItem("Resign"));
//		file.add(item3 = new JMenuItem("Exit"));
		jmb.add(file);
		jmb.add(file1);
		
		
		
		setLayout(new BorderLayout());
		//bg = new ImageIcon("C:/javafolder/Project1/background.jpg");
		try{
			Image img = ImageIO.read(Chess.class.getResource("imgs/background.jpg"));
			Image newimg = img.getScaledInstance(getWidth()-20,getHeight()-20,java.awt.Image.SCALE_SMOOTH);
			bg = new ImageIcon(newimg);
			setContentPane(new JLabel(bg)); 
		}catch(IOException e){
			
		}

		setLayout(new FlowLayout());
		
		
		int i,j;
		pan = new JPanel();
		pan.setPreferredSize(new Dimension(getHeight()-50,getHeight()-50));
		for(i=0;i<8;i++){
			for(j=0;j<8;j++){
				btn[i][j] = new MyButton(i,j);
				if((i%2==0&&j%2==0)||(i%2==1&&j%2==1)){
					btn[i][j].setBackground(Color.ORANGE);
				}else{
					btn[i][j].setBackground(Color.BLUE);
				}
				pan.add(btn[i][j]);
			}
		}
		pan.setLayout(new GridLayout(8,8,-1,-1));
		JLabel lab = new JLabel("invalid move!");
		add(pan);
		setVisible(true);

		p1 = new Player(0,0,false,this);
		p2 = new Player(7,0,true,this);
		
		for(i=0;i<8;i++){
			for(j=0;j<8;j++){
				btn[i][j].addActionListener(this);
			}
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		MyButton m = (MyButton)ae.getSource();
		
		int active = p1.OnOff==false ? 2:1;
		if(btnsStatus==1 || (btnsStatus==2 && m.player==active)){
			btnsStatus=1;
			clickedBtn=null; 
			remove();
			
			if(active == m.player){
				clickedBtn=m;
				if(m.imgName == 1){
					p1.new King().move(m.i,m.j,this);
				}else if(m.imgName == 2){
					p1.new Queen().move(m.i,m.j,this);
				}else if(m.imgName == 3){
					p1.new Bishop().move(m.i,m.j,this);
				}else if(m.imgName == 4){
					p1.new Knight().move(m.i,m.j,this);
				}else if(m.imgName == 5){
					p1.new Rook().move(m.i,m.j,this);
				}else if(m.imgName == 6){
					p1.new Pawn().move(m.i,m.j,this);
				}
			}
		
			if(active != m.player && m.player != 0){
				
			}
			
			if(m.player == 0){
				
			}
		}
		
		else if(btnsStatus==2){
			if(m.getBackground() == Color.GREEN){
				remove();
				if(m.player !=0&&m.player != active){
					if(m.player == 1){
						if(m.imgName==1){
							if(active == 1)
								JOptionPane.showMessageDialog(null,String.format("Player 1 won",ae.getActionCommand())); 
							else	
								JOptionPane.showMessageDialog(null,String.format("Player 2 won",ae.getActionCommand())); 
						}
					}else{
						//System.out.println(p2.deadsholdier.length);
					}
				}
				m.setImage(clickedBtn.player,clickedBtn.imgName);
				btn[clickedBtn.i][clickedBtn.j].setImage(0,0);
				btn[clickedBtn.i][clickedBtn.j].moved=true;
				if(active==1){
					p1.OnOff=false;
					p2.OnOff=true;
				} else{
					p2.OnOff=false;
					p1.OnOff=true;
				}
			}else {
				remove();
			}
			btnsStatus=1;
			clickedBtn=null;
		}
	}
	
	public void remove(){
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((i%2==0&&j%2==0)||(i%2==1&&j%2==1))
					btn[i][j].setBackground(Color.ORANGE);
				else
					btn[i][j].setBackground(Color.BLUE);
	}
	
	public void run(){
	
	}

}


class MyButton extends JButton{
	int imgName;
	ImageIcon icon;
	int i,j;
	int player;
	boolean moved;
	
	MyButton(int i,int j){
		super();
		this.i = i;
		this.j = j;
		imgName = 0;
		player = 0;
		setIcon(null);
		moved = true;
	}

	void setImage(int p,int q){
		player = p;
		imgName = q;
		
		String B_or_W = p==2 ? "w":"b";
		Image img;
		try{
			//img = ImageIO.read(Chess.class.getResource("imgs/background.jpg"));


		if(q==1)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"king.png"));
		else if(q==2)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"queen.png"));
		else if(q==3)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"bishop.png"));
		else if(q==4)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"knight.png"));
		else if(q==5)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"rook.png"));
		else if(q==6)
			img = ImageIO.read(Chess.class.getResource("imgs/"+B_or_W+"pawn.png"));
		else
			img = null;
		if(img!=null){
			Image newimg = img.getScaledInstance(getHeight(),getWidth(),java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newimg);
		}else{
			icon = null;
		}
		}catch(IOException e){
			System.out.println(e);
		}
		setIcon(icon);
	}
}

class Player{
	public boolean OnOff;
	public MyButton deadsholdier[] = new MyButton[16];
	
	Player(int x,int y,boolean OnOff,Layout l){
		this.OnOff = OnOff;
		int B_or_W = x==7 ? 2:1;
		l.btn[x][0].setImage(B_or_W,5);
		l.btn[x][1].setImage(B_or_W,4);
		l.btn[x][2].setImage(B_or_W,3);
		l.btn[x][3].setImage(B_or_W,2);
		l.btn[x][4].setImage(B_or_W,1);
		l.btn[x][5].setImage(B_or_W,3);
		l.btn[x][6].setImage(B_or_W,4);
		l.btn[x][7].setImage(B_or_W,5);

		x = Math.abs(-x+1);
		for(int i=0;i<8;i++){
			l.btn[x][i].setImage(B_or_W,6);
			l.btn[x][i].moved=false;
		}
	}
	
	class King{
		public void move(int x,int y,Layout l){
			int i1,j1;
			int active = l.p1.OnOff==false ? 2:1;
			
			for(int i=0;i<3;i++){
				i1=x+1; j1=y+(i-1);
				if(i1>=0&&j1>=0&&i1<8&&j1<8){
					if(active != l.btn[i1][j1].player){
						l.btnsStatus=2;
						l.btn[i1][j1].setBackground(Color.GREEN);
					}
				}
			}

			i1=x; j1=y-1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1=x; j1=y+1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}

			
			for(int i=0;i<3;i++){
				i1=x-1; j1=y+(i-1);
				if(i1>=0&&j1>=0&&i1<8&&j1<8){
					if(active != l.btn[i1][j1].player){
						l.btnsStatus=2;
						l.btn[i1][j1].setBackground(Color.GREEN);
					}
				}
			}
		}
	}  
	
	class Queen{
		public void move(int x,int y,Layout l){
			int i1,j1,ones = 0;
			int active = l.p1.OnOff==false ? 2:1;
			
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player){
					break;
				}
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);
			}	
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}			
		
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}			
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}			
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}			
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
		}
	}

	class Bishop{
		public void move(int x,int y,Layout l){
			int i1,j1,ones = 0;
			int active = l.p1.OnOff==false ? 2:1;
			
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player){
					break;
				}
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
		}
	}

	class Knight{
		public void move(int x,int y,Layout l){
			int i1,j1;
			int active = l.p1.OnOff==false ? 2:1;
			
			i1 = x+1; j1= y+2;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x+1; j1= y-2;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x+2; j1= y+1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x+2; j1= y-1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x-1; j1= y+2;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x-1; j1= y-2;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x-2; j1= y+1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			i1 = x-2; j1= y-1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(active != l.btn[i1][j1].player){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
		}
	}
	
	class Rook{
		public void move(int x,int y,Layout l){
			int i1,j1,ones;
			int active = l.p1.OnOff==false ? 2:1;
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x; j1 = y+i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x; j1 = y-i;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x-i; j1 = y;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
			
			ones = 0;
			for(int i=1;i<9;i++){
				i1 = x+i; j1 = y;
				if(i1<0||j1<0||i1>7||j1>7)
					break;
				if(active == l.btn[i1][j1].player)
					break;
				if(active != l.btn[i1][j1].player && l.btn[i1][j1].player !=0){
					if(ones == 1)
						break;
					ones = 1;
				}
				l.btnsStatus=2;
				l.btn[i1][j1].setBackground(Color.GREEN);				
			}
		}
	}
	
	class Pawn{
		public void move(int x,int y,Layout l){
			int i1,j1;
			int active = l.p1.OnOff==false ? 2:1;
			
			i1 = active==1 ? x+1 :x-1;
			j1=y;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(l.btn[i1][j1].player == 0){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
					if(l.btn[x][y].moved==false){
						int i2 = active==1 ? x+2 :x-2;
						l.btn[i2][j1].setBackground(Color.GREEN);
					}
				}
			}
			
			j1 = y+1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(l.btn[i1][j1].player != active && l.btn[i1][j1].player != 0){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
			
			j1 = y-1;
			if(i1>=0&&j1>=0&&i1<8&&j1<8){
				if(l.btn[i1][j1].player != active && l.btn[i1][j1].player != 0){
					l.btnsStatus=2;
					l.btn[i1][j1].setBackground(Color.GREEN);
				}
			}
		}
	}
}