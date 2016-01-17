package chesstutorial;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
    static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize=80;
    static boolean flag = true;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.yellow);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {
            g.setColor(new Color(34,47,233));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(11,213,253));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        Image chessImage;
        chessImage=new ImageIcon("Chess.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (MyChessEngine.Board[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=2; k=0;
                    break;
                case "r": j=2; k=1;
                    break;
                case "K": j=4; k=0;
                    break;
                case "k": j=4; k=1;
                    break;
                case "B": j=3; k=0;
                    break;
                case "b": j=3; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "A": j=0; k=0;
                    break;
                case "a": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }
       
    }
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            
            newMouseX=e.getX();
            newMouseY=e.getY();
            if (e.getButton()==MouseEvent.BUTTON1) {
                String dragMove;
                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(MyChessEngine.Board[mouseY/squareSize][mouseX/squareSize])) {
                    
                    dragMove=""+mouseX/squareSize+newMouseX/squareSize+MyChessEngine.Board[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                } else {
                    
                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+MyChessEngine.Board[newMouseY/squareSize][newMouseX/squareSize];
                }
                String userPosibilities=MyChessEngine.MovesPossible();
                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
                    
                    MyChessEngine.MoveMaking(dragMove);
                    
                    MyChessEngine.InvertBoard();
                    if(flag == false)
                    {
                        System.out.println("test 1");
                        MyChessEngine.MoveMaking(MyChessEngine.alphaBetaAttack(MyChessEngine.globalDepth, 1000000, -1000000, "", 0));
                    }
                    else{
                        System.out.println("test 2");
                        MyChessEngine.MoveMaking(MyChessEngine.alphaBetaMain(MyChessEngine.globalDepth, 1000000, -1000000, "", 0));
                    }
                    
                    MyChessEngine.InvertBoard();
                    String k = MyChessEngine.MovesPossible();
                    repaint();
                    if(k.equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "Check mate,  Black is the Winner", k, JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
}