package chesstutorial;
public class EvalutionFunction {
    
    static boolean flag1 = false;
    
    //Queen
    static int value = 1; 
    
    //bishop
    static int value1 = 1; 
    
    //knight
    static int value2 = 1; 
    
    //rook
    static int value3 = 1; 
    
    static int attackValue = 100;
    
    //attribute to http://chessprogramming.wikispaces.com/Simplified+evaluation+function
    static int pawnBoard[][]={
        { 0,  0,  0,  0,  0,  0,  0,  0},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {10, 10, 20, 30, 30, 20, 10, 10},
        { 5,  5, 10, 25, 25, 10,  5,  5},
        { 0,  0,  0, 20, 20,  0,  0,  0},
        { 5, -5,-10,  0,  0,-10, -5,  5},
        { 5, 10, 10,-20,-20, 10, 10,  5},
        { 0,  0,  0,  0,  0,  0,  0,  0}};
    
    static int rookBoard[][]={
        { 0,  0,  0,  0,  0,  0,  0,  0},
        { 5, 10, 10, 10, 10, 10, 10,  5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        { 0,  0,  0,  5,  5,  0,  0,  0}};
    
    static int knightBoard[][]={
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-30,  0, 10, 15, 15, 10,  0,-30},
        {-30,  5, 15, 20, 20, 15,  5,-30},
        {-30,  0, 15, 20, 20, 15,  0,-30},
        {-30,  5, 10, 15, 15, 10,  5,-30},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}};
    
    static int bishopBoard[][]={
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}};
    
    static int queenBoard[][]={
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}};
    
    static int kingMidBoard[][]={
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        { 20, 20,  0,  0,  0,  0, 20, 20},
        { 20, 30, 10,  0,  0, 10, 30, 20}};
    
    static int kingEndBoard[][]={
        {-50,-40,-30,-20,-20,-30,-40,-50},
        {-30,-20,-10,  0,  0,-10,-20,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-30,  0,  0,  0,  0,-30,-30},
        {-50,-30,-30,-30,-30,-30,-30,-50}};
    
    public static int rating(int list, int depth) {
        int cnt=0, material=rateMaterial();
        cnt+=rateAttack();
        cnt+=material;
        cnt+=rateMoveablitly(list, depth, material);
        cnt+=ratePositional(material);
        MyChessEngine.InvertBoard();
        material=rateMaterial();
        cnt-=rateAttack();
        cnt-=material;
        cnt-=rateMoveablitly(list, depth, material);
        cnt-=ratePositional(material);
        MyChessEngine.InvertBoard();
        return -(cnt+depth*50);
    }
    
    public static int rateAttack() {
        int cnt=0;
        int tempPositionC=MyChessEngine.WhiteKing;
        for (int i=0;i<64;i++) {
            switch (MyChessEngine.Board[i/8][i%8]) {
                case "P": {MyChessEngine.WhiteKing=i; if (!MyChessEngine.kingSafe()) {
                    if(flag1){
                        cnt-= 64+attackValue;
                    }
                    else{
                    cnt-=64;}}}
                    break;
                case "R": {MyChessEngine.WhiteKing=i; if (!MyChessEngine.kingSafe()) {
                    if(flag1){
                        cnt-= 500+attackValue;
                    }
                    else{cnt-=500;}}}
                    break;
                case "K": {MyChessEngine.WhiteKing=i; if (!MyChessEngine.kingSafe()) {
                    if(flag1){
                        cnt-= 300+attackValue;
                    }
                    else{
                    cnt-=300;}}}
                    break;
                case "B": {MyChessEngine.WhiteKing=i; if (!MyChessEngine.kingSafe()) {
                    if(flag1){
                        cnt-=300+attackValue;
                    }
                    else{
                    cnt-=300;}}}
                    break;
                case "Q": {MyChessEngine.WhiteKing=i; if (!MyChessEngine.kingSafe()) {
                    if(flag1){
                        cnt-=900+attackValue;
                    }
                    else{
                    cnt-=900;}}}
                    break;
            }
        }
        MyChessEngine.WhiteKing=tempPositionC;
        if (!MyChessEngine.kingSafe()) {cnt-=200;}
        return cnt/2;
    }
    
    public static int rateMaterial() {
        int cnt=0, bishopCnt=0;
        for (int i=0;i<64;i++) {
            switch (MyChessEngine.Board[i/8][i%8]) {
                case "P": cnt+=100;
                    break;
                case "R": 
                    if(value3 == 0){
                        cnt+=350;
                        break;
                    }
                    else if(value3 == 1)
                    {
                        cnt+=500;
                        break;
                    }
                    
                    else if(value3 == 2)
                    {
                        cnt+=650;
                        break;
                    }
                    
                case "K": 
                    if(value2 == 0){
                        cnt+=200;
                        break;
                    }
                    else if(value2 == 1)
                    {
                        cnt+=300;
                        break;
                    }
                    
                    else if(value2 == 2)
                    {
                        cnt+=400;
                        break;
                    }

                case "B": bishopCnt+=1;
                    
                    if(value1 == 0){
                        cnt-=50;
                        break;
                    }
                    else if(value1 == 1)
                    {
                        
                        break;
                    }
                    
                    else if(value1 == 2)
                    {
                        cnt+=50;
                        break;
                    }

                case "Q": cnt+=900;
                    
                    if(value == 0){
                        cnt+=800;
                        break;
                    }
                    else if(value == 1)
                    {
                        cnt+=900;
                        break;
                    }
                    
                    else if(value == 2)
                    {
                        cnt+=1000;
                        break;
                    }

            }
        }
        if (bishopCnt>=2) {
            cnt+=300*bishopCnt;
        } else {
            if (bishopCnt==1) {cnt+=250;}
        }
        return cnt;
    }
    
    public static int rateMoveablitly(int listLength, int depth, int material) {
        int cnt=0;
        
        
        cnt+=listLength;
        if (listLength==0) {
            if (!MyChessEngine.kingSafe()) {
                cnt+=-200000*depth;
            } else {
                cnt+=-150000*depth;
            }
        }
        return 0;
    }
    
    public static int ratePositional(int material) {
        int cnt=0;
        for (int i=0;i<64;i++) {
            switch (MyChessEngine.Board[i/8][i%8]) {
                case "P": cnt+=pawnBoard[i/8][i%8];
                    break;
                case "R": cnt+=rookBoard[i/8][i%8];
                    break;
                case "K": cnt+=knightBoard[i/8][i%8];
                    break;
                case "B": cnt+=bishopBoard[i/8][i%8];
                    break;
                case "Q": cnt+=queenBoard[i/8][i%8];
                    break;
                case "A": if (material>=1750) {cnt+=kingMidBoard[i/8][i%8]; cnt+=MyChessEngine.posibleA(MyChessEngine.WhiteKing).length()*10;} else
                {cnt+=kingEndBoard[i/8][i%8]; cnt+=MyChessEngine.posibleA(MyChessEngine.WhiteKing).length()*30;}
                    break;
            }
        }
        return cnt;
    }
}