package chesstutorial;
import java.util.*;
import javax.swing.*;

public class MyChessEngine {
    static String[][] Board={
        {"r","k","b","q","a","b","k","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","K","B","Q","A","B","K","R"}};
    static int WhiteKing, BlackKing;
    
    //1=human as white 
    //0=human as black
    static int HumanW=-1;
    static int globalDepth=4;
    static int globalBranchingFactor = 6;
    
    public static void main(String[] args) {
        while (!"A".equals(Board[WhiteKing/8][WhiteKing%8])) {WhiteKing++;}
        while (!"a".equals(Board[BlackKing/8][BlackKing%8])) {BlackKing++;}
        
        JPanel myPanel = new JPanel();
        String[] sdepth = {"2", "3", "4", "5" };
                JComboBox sd = new JComboBox(sdepth);
                sd.setSelectedIndex(2);
                
                String[] branchingF = { "4", "5", "6", "7", "8" };
                JComboBox bf = new JComboBox(branchingF);
                bf.setSelectedIndex(2);
                
                String[] mode = {"Normal", "Moderate Attack", "Attacking"};
                JComboBox gh = new JComboBox(mode);
                gh.setSelectedIndex(0);
                
                String[] knightMode = {"Low","Medium","High"};
                JComboBox sl = new JComboBox(knightMode);
                sl.setSelectedIndex(1);
                
                String[] roockMode = {"Low","Medium","High"};
                JComboBox al = new JComboBox(roockMode);
                al.setSelectedIndex(1);
                
                String[] bishopMode = {"Low","Medium","High"};
                JComboBox bl = new JComboBox(bishopMode);
                bl.setSelectedIndex(1);
                
                String[] queenMode = {"Low","Medium","High"};
                JComboBox dl = new JComboBox(queenMode);
                dl.setSelectedIndex(1);
                
                myPanel.add(new JLabel("Search Depth: ",SwingConstants.LEFT));
                myPanel.add(sd);
                
                myPanel.add(new JLabel("Branching Factor: ",SwingConstants.LEFT));
                myPanel.add(bf);
                
                myPanel.add(new JLabel("Game Mode: ",SwingConstants.LEFT));
                myPanel.add(gh);
                
                myPanel.add(new JLabel("Roock Priority: ",SwingConstants.LEFT));
                myPanel.add(al);
                
                myPanel.add(new JLabel("Bishop Priority: ",SwingConstants.LEFT));
                myPanel.add(bl);
                
                myPanel.add(new JLabel("Knight Priority: ",SwingConstants.LEFT));
                myPanel.add(sl);
                
                myPanel.add(new JLabel("Queen Priority: ",SwingConstants.LEFT));
                myPanel.add(dl);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Game Strategies", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
		   globalDepth = Integer.parseInt(sd.getSelectedItem().toString());
                   System.out.println(globalDepth);
                   
                   globalBranchingFactor = Integer.parseInt(bf.getSelectedItem().toString());
                   System.out.println(globalBranchingFactor);
                   
                   String str = gh.getSelectedItem().toString();
                   if(str == "Attacking")
                   {
                       UserInterface.flag = false;
                   }
                   else if(str == "Moderate Attack")
                   {
                       EvalutionFunction.flag1 = true;
                   }
                   
                   String str1 = sl.getSelectedItem().toString();
                   if(str1.equals("Low"))
                   {
                       EvalutionFunction.value2 = 0;
                   }
                   else if(str1.equals("Medium"))
                   {
                       EvalutionFunction.value2 = 1;
                   }
                   else if(str1.equals("High"))
                   {
                       EvalutionFunction.value2 = 2;
                   }
                   
                   String str2 = al.getSelectedItem().toString();
                   if(str2.equals("Low"))
                   {
                       EvalutionFunction.value3 = 0;
                   }
                   else if(str2.equals("Medium"))
                   {
                       EvalutionFunction.value3 = 1;
                   }
                   else if(str2.equals("High"))
                   {
                       EvalutionFunction.value3 = 2;
                   }

                   String str3 = bl.getSelectedItem().toString();
                   if(str3.equals("Low"))
                   {
                       EvalutionFunction.value1 = 0;
                   }
                   else if(str3.equals("Medium"))
                   {
                       EvalutionFunction.value1 = 1;
                   }
                   else if(str3.equals("High"))
                   {
                       EvalutionFunction.value1 = 2;
                   }

                   String str4 = dl.getSelectedItem().toString();
                   if(str4.equals("Low"))
                   {
                       EvalutionFunction.value = 0;
                   }
                   else if(str4.equals("Medium"))
                   {
                       EvalutionFunction.value = 1;
                   }
                   else if(str4.equals("High"))
                   {
                       EvalutionFunction.value = 2;
                   }
                   
		}
        
        JCheckBox g = new JCheckBox("Hello");
        g.setVisible(true);
        JFrame f=new JFrame("Chess Tutorial");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ai=new UserInterface();
        f.add(ai);
        f.setSize(655, 680);
        
        f.setVisible(true);
                
    }
    
    public static String alphaBetaMain(int depth, int beta, int alpha, String move, int player) {
       String list=MovesPossible();
        if (depth==0 || list.length()==0) {
            System.out.println("move possible = " + list + "lenth = ");
            return move+(EvalutionFunction.rating(list.length(), depth)*(player*2-1));}
        list=sortMoves(list);
        player=1-player;
        for (int i=0;i<list.length();i+=5) {
            MoveMaking(list.substring(i,i+5));
            InvertBoard();
            String returnString=alphaBetaMain(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            InvertBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta;} else {return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    
    public static void InvertBoard() {
        String temp;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(Board[r][c].charAt(0))) {
                temp=Board[r][c].toLowerCase();
            } else {
                temp=Board[r][c].toUpperCase();
            }
            if (Character.isUpperCase(Board[7-r][7-c].charAt(0))) {
                Board[r][c]=Board[7-r][7-c].toLowerCase();
            } else {
                Board[r][c]=Board[7-r][7-c].toUpperCase();
            }
            Board[7-r][7-c]=temp;
        }
        int kingTemp=WhiteKing;
        WhiteKing=63-BlackKing;
        BlackKing=63-kingTemp;
    }
    
    public static void MoveMaking(String givenMove) {
        
        if(givenMove.charAt(0)=='-')
        {
             JOptionPane.showMessageDialog(null, "Check mate, White wins", givenMove, JOptionPane.INFORMATION_MESSAGE);
        }
        else{
        if (givenMove.charAt(4)!='P') {
            Board[Character.getNumericValue(givenMove.charAt(2))][Character.getNumericValue(givenMove.charAt(3))]=Board[Character.getNumericValue(givenMove.charAt(0))][Character.getNumericValue(givenMove.charAt(1))];
            Board[Character.getNumericValue(givenMove.charAt(0))][Character.getNumericValue(givenMove.charAt(1))]=" ";
            if ("A".equals(Board[Character.getNumericValue(givenMove.charAt(2))][Character.getNumericValue(givenMove.charAt(3))])) {
                WhiteKing=8*Character.getNumericValue(givenMove.charAt(2))+Character.getNumericValue(givenMove.charAt(3));
            }
        } else {
            
            Board[1][Character.getNumericValue(givenMove.charAt(0))]=" ";
            Board[0][Character.getNumericValue(givenMove.charAt(1))]=String.valueOf(givenMove.charAt(3));
        }
        }
        
    }
    
    public static void undoMove(String givenMove) {
        if (givenMove.charAt(4)!='P') {
            Board[Character.getNumericValue(givenMove.charAt(0))][Character.getNumericValue(givenMove.charAt(1))]=Board[Character.getNumericValue(givenMove.charAt(2))][Character.getNumericValue(givenMove.charAt(3))];
            Board[Character.getNumericValue(givenMove.charAt(2))][Character.getNumericValue(givenMove.charAt(3))]=String.valueOf(givenMove.charAt(4));
            if ("A".equals(Board[Character.getNumericValue(givenMove.charAt(0))][Character.getNumericValue(givenMove.charAt(1))])) {
                WhiteKing=8*Character.getNumericValue(givenMove.charAt(0))+Character.getNumericValue(givenMove.charAt(1));
            }
        } else {
            
            Board[1][Character.getNumericValue(givenMove.charAt(0))]="P";
            Board[0][Character.getNumericValue(givenMove.charAt(1))]=String.valueOf(givenMove.charAt(2));
        }
    }
    
    public static String MovesPossible() {
        String list="";
        for (int i=0; i<64; i++) {
            switch (Board[i/8][i%8]) {
                case "P": list+=posiblePawn(i);
                    break;
                case "R": list+=posibleR(i);
                    break;
                case "K": list+=posibleK(i);
                    break;
                case "B": list+=posibleB(i);
                    break;
                case "Q": list+=posibleQ(i);
                    break;
                case "A": list+=posibleA(i);
                    break;
            }
        }
        return list;
    }
    
    public static String posiblePawn(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {
                if (Character.isLowerCase(Board[r-1][c+j].charAt(0)) && i>=16) {
                    oldPiece=Board[r-1][c+j];
                    Board[r][c]=" ";
                    Board[r-1][c+j]="P";
                    if (kingSafe()) {
                        list=list+r+c+(r-1)+(c+j)+oldPiece;
                    }
                    Board[r][c]="P";
                    Board[r-1][c+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {
                if (Character.isLowerCase(Board[r-1][c+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=Board[r-1][c+j];
                        Board[r][c]=" ";
                        Board[r-1][c+j]=temp[k];
                        if (kingSafe()) {
                            
                            list=list+c+(c+j)+oldPiece+temp[k]+"P";
                        }
                        Board[r][c]="P";
                        Board[r-1][c+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {
            if (" ".equals(Board[r-1][c]) && i>=16) {
                oldPiece=Board[r-1][c];
                Board[r][c]=" ";
                Board[r-1][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-1)+c+oldPiece;
                }
                Board[r][c]="P";
                Board[r-1][c]=oldPiece;
            }
        } catch (Exception e) {}
        try {
            if (" ".equals(Board[r-1][c]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=Board[r-1][c];
                    Board[r][c]=" ";
                    Board[r-1][c]=temp[k];
                    if (kingSafe()) {
                           list=list+c+c+oldPiece+temp[k]+"P";
                    }
                    Board[r][c]="P";
                    Board[r-1][c]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {
            if (" ".equals(Board[r-1][c]) && " ".equals(Board[r-2][c]) && i>=48) {
                oldPiece=Board[r-2][c];
                Board[r][c]=" ";
                Board[r-2][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-2)+c+oldPiece;
                }
                Board[r][c]="P";
                Board[r-2][c]=oldPiece;
            }
        } catch (Exception e) {}
        return list;
    }
    
    public static String posibleR(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(Board[r][c+temp*j]))
                {
                    oldPiece=Board[r][c+temp*j];
                    Board[r][c]=" ";
                    Board[r][c+temp*j]="R";
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    Board[r][c]="R";
                    Board[r][c+temp*j]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(Board[r][c+temp*j].charAt(0))) {
                    oldPiece=Board[r][c+temp*j];
                    Board[r][c]=" ";
                    Board[r][c+temp*j]="R";
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    Board[r][c]="R";
                    Board[r][c+temp*j]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(Board[r+temp*j][c]))
                {
                    oldPiece=Board[r+temp*j][c];
                    Board[r][c]=" ";
                    Board[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    Board[r][c]="R";
                    Board[r+temp*j][c]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(Board[r+temp*j][c].charAt(0))) {
                    oldPiece=Board[r+temp*j][c];
                    Board[r][c]=" ";
                    Board[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    Board[r][c]="R";
                    Board[r+temp*j][c]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
        }
        return list;
    }
    
    public static String posibleK(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(Board[r+j][c+k*2].charAt(0)) || " ".equals(Board[r+j][c+k*2])) {
                        oldPiece=Board[r+j][c+k*2];
                        Board[r][c]=" ";
                        if (kingSafe()) {
                            list=list+r+c+(r+j)+(c+k*2)+oldPiece;
                        }
                        Board[r][c]="K";
                        Board[r+j][c+k*2]=oldPiece;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(Board[r+j*2][c+k].charAt(0)) || " ".equals(Board[r+j*2][c+k])) {
                        oldPiece=Board[r+j*2][c+k];
                        Board[r][c]=" ";
                        if (kingSafe()) {
                            list=list+r+c+(r+j*2)+(c+k)+oldPiece;
                        }
                        Board[r][c]="K";
                        Board[r+j*2][c+k]=oldPiece;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }
    
    public static String posibleB(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(Board[r+temp*j][c+temp*k]))
                    {
                        oldPiece=Board[r+temp*j][c+temp*k];
                        Board[r][c]=" ";
                        Board[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        Board[r][c]="B";
                        Board[r+temp*j][c+temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(Board[r+temp*j][c+temp*k].charAt(0))) {
                        oldPiece=Board[r+temp*j][c+temp*k];
                        Board[r][c]=" ";
                        Board[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        Board[r][c]="B";
                        Board[r+temp*j][c+temp*k]=oldPiece;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        return list;
    }
    
    public static String posibleQ(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(Board[r+temp*j][c+temp*k]))
                        {
                            oldPiece=Board[r+temp*j][c+temp*k];
                            Board[r][c]=" ";
                            Board[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            Board[r][c]="Q";
                            Board[r+temp*j][c+temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(Board[r+temp*j][c+temp*k].charAt(0))) {
                            oldPiece=Board[r+temp*j][c+temp*k];
                            Board[r][c]=" ";
                            Board[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            Board[r][c]="Q";
                            Board[r+temp*j][c+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return list;
    }
    
    public static String posibleA(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(Board[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(Board[r-1+j/3][c-1+j%3])) {
                        oldPiece=Board[r-1+j/3][c-1+j%3];
                        Board[r][c]=" ";
                        Board[r-1+j/3][c-1+j%3]="A";
                        int kingTemp=WhiteKing;
                        WhiteKing=i+(j/3)*8+j%3-9;
                        if (kingSafe()) {
                            list=list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
                        }
                        Board[r][c]="A";
                        Board[r-1+j/3][c-1+j%3]=oldPiece;
                        WhiteKing=kingTemp;
                    }
                } catch (Exception e) {}
            }
        }
        
        return list;
    }
    
    public static String sortMoves(String list) {
        int[] score=new int [list.length()/5];
        for (int i=0;i<list.length();i+=5) {
            MoveMaking(list.substring(i, i+5));
            score[i/5]=-EvalutionFunction.rating(-1, 0);
            undoMove(list.substring(i, i+5));
        }
        String newListA="", newListB=list;
        for (int i=0;i<Math.min(globalBranchingFactor, list.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<list.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=list.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }
    
    public static boolean kingSafe() {
        
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(Board[WhiteKing/8+temp*i][WhiteKing%8+temp*j])) {temp++;}
                    if ("b".equals(Board[WhiteKing/8+temp*i][WhiteKing%8+temp*j]) ||
                            "q".equals(Board[WhiteKing/8+temp*i][WhiteKing%8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(Board[WhiteKing/8][WhiteKing%8+temp*i])) {temp++;}
                if ("r".equals(Board[WhiteKing/8][WhiteKing%8+temp*i]) ||
                        "q".equals(Board[WhiteKing/8][WhiteKing%8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(Board[WhiteKing/8+temp*i][WhiteKing%8])) {temp++;}
                if ("r".equals(Board[WhiteKing/8+temp*i][WhiteKing%8]) ||
                        "q".equals(Board[WhiteKing/8+temp*i][WhiteKing%8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("k".equals(Board[WhiteKing/8+i][WhiteKing%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("k".equals(Board[WhiteKing/8+i*2][WhiteKing%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
 
        if (WhiteKing>=16) {
            try {
                if ("p".equals(Board[WhiteKing/8-1][WhiteKing%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(Board[WhiteKing/8-1][WhiteKing%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
 
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(Board[WhiteKing/8+i][WhiteKing%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
    
    public static String alphaBetaAttack(int depth, int beta, int alpha, String move, int player) {
 
        String list=MovesPossible();
        
        if (depth==0 || list.length()==0) {
            
            return move+(EvalutionFunction.rating(list.length(), depth)*(player*2-1));}
        list=sortMoves(list);
        player=1-player;
        String desired = "";
        for (int i=0;i<list.length();i+=5) {
            if(list.charAt(i+4) != ' ')
            {
                desired+= list.substring(i,i+5);
            }    
                         
        }
        if(desired.equals(""))
        {
            desired = list;
            list = desired;
        }
        else{
            list = desired;
        }
        
        System.out.println("Desired attacking= "+ desired );
        System.out.println("Printing list " + list );
        for (int i=0;i<list.length();i+=5) {
            MoveMaking(list.substring(i,i+5));
            InvertBoard();
            String returnString=alphaBetaMain(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            InvertBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta;} else {return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    
}