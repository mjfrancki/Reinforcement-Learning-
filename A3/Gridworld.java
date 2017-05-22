import java.util.*;

public class Gridworld {

    private Random rand = new Random();
    private int[] pos = {0,0};
    private int[] ter = {0,9};
    private String[][] walls ={
        {"O","O","O","O","O","R","L","O","O","O"},
        {"O","O","O","O","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","R","L","O","O","O"},
        {"O","O","R","L","O","O","O","O","O","O"},
        {"O","O","R","L","O","O","O","O","O","O"}};
    private double pCorrectMove = 1.0;
    private double pDontMove = 0.0;
    private double pWrongMove = (1 - pCorrectMove - pDontMove);
    private int[][] reward = new int[Utils.GRID_ROWS][Utils.GRID_COLS];

    public Gridworld(int xPos, int yPos){
        pos[0] = xPos;
        pos[1] = yPos;
        for(int i = 0; i < Utils.GRID_ROWS; i++){
            for(int j = 0; j < Utils.GRID_COLS; j++){
                if (i == ter[0] && j == ter[1]) {
                    reward[i][j] = 500;
                } else {
                    reward[i][j] = -1;
                }
            }
        }
    }

    public int getReward(int x, int y) {
        return reward[x][y];
    }

    public void setPos(int x, int y){
        pos[0] = x;
        pos[1] = y;
    }

    public void setPs(double p1, double p2){
        pCorrectMove = p1;
        pDontMove = p2;
        pWrongMove = (1 - pCorrectMove - pDontMove);
    }

    public int[] getPos(){
        return pos;
    }

    public boolean isDone(){
        int xPos = pos[0] ;
        int yPos = pos[1] ;
        int xTer = ter[0] ;
        int yTer = ter[1] ;
        return (xPos == xTer && yPos == yTer);
    }

    //Decides if it moves correctly
    //stays still
    //slides
    public Utils.MoveResult moveTypeDecider() {
        int max = 1000000;
        int  n = rand.nextInt(max) + 1;
        double prob = ((double)n)/max;

        if (prob <= pCorrectMove) {
            return Utils.MoveResult.CORRECT;
        }
        else if (prob <= pCorrectMove + pDontMove) {
            return Utils.MoveResult.NONE;
        }
        else { 
            return Utils.MoveResult.WRONG;
        }
    }

    //Decides after hitting a wall if
    //stays in same position
    //bounces to a  adjacesnt position
    public void wallbounceDecider(){

        int max = 1000000;
        int  n = rand.nextInt(max) + 1;

        double prob = ((double)n)/max;

        //stay in same position
        if (prob <= (pCorrectMove + pDontMove)) {
            //  System.out.println("Stayed in same location");
            return;
        }

        n = rand.nextInt(2);
        if(n==0){
            this.move(Utils.Move.UP);
        }
        else {
            this.move(Utils.Move.DOWN);
        }
    }

    //decided what to do when trying to move off the grid
    //No change in state  OR
    //Move to adjacesnt state

    //TODO concider corners more closly
    public void fallingoffDecider(Utils.Move move){

        int max = 1000000;
        int  n = rand.nextInt(max) + 1;

        double prob = ((double)n)/max;

        //stay in same position
        if (prob <= (pCorrectMove + pDontMove)) {
            return;
        }

        n = rand.nextInt(2);
        if(move == Utils.Move.UP || move == Utils.Move.DOWN){
            if(n==0){
                this.move(Utils.Move.LEFT);//TODO dont call, increment manually
            }
            else {
                this.move(Utils.Move.RIGHT);
            }
        }
        else if(move == Utils.Move.LEFT || move == Utils.Move.RIGHT ){
            if(n==0){
                this.move(Utils.Move.DOWN);
            }
            else {
                this.move(Utils.Move.UP);
            }
        }
    }

    //might want to code in the moves here b/c these move can fail or slip to

    public void slipDecider(Utils.Move move){

        int n = rand.nextInt(2);
        if(move == Utils.Move.UP || move == Utils.Move.DOWN){
            if(n==0){
                this.move(Utils.Move.LEFT);
            }
            else {
                this.move(Utils.Move.RIGHT);
            }
        }

        if(move == Utils.Move.LEFT || move == Utils.Move.RIGHT){
            if(n==0){
                this.move(Utils.Move.DOWN);
            }
            else {
                this.move(Utils.Move.UP);
            }
        }
    }

    public int move(Utils.Move dir){
        //System.out.println(dir);

        Utils.MoveResult movetype  = this.moveTypeDecider();
        //correct move p1
        if (movetype == Utils.MoveResult.CORRECT){
            if (dir == Utils.Move.RIGHT) {
                if (walls[pos[0]][pos[1]] != "R")
                    pos[1]++;
                else{
                    this.wallbounceDecider();
                }
            }
            if (dir == Utils.Move.DOWN) {
                pos[0]++;
            }
            if (dir == Utils.Move.LEFT) {
                if (walls[pos[0]][pos[1]] != "L")
                    pos[1]--;
                else{
                    this.wallbounceDecider();
                }
            }
            if (dir == Utils.Move.UP) {
                pos[0]--;
            }

            //put back on grid
            //might not put back "stright"
            if(pos[0] < 0){
                pos[0]++;
                fallingoffDecider(dir);
            }
            if(pos[0] >= Utils.GRID_ROWS){
                pos[0]--;
                fallingoffDecider(dir);
            }
            if(pos[1] < 0){
                pos[1]++;
                fallingoffDecider(dir);
            }
            if(pos[1] >= Utils.GRID_COLS){
                pos[1]--;
                fallingoffDecider(dir);
            }
        }

        if (movetype == Utils.MoveResult.NONE){
            //no move p2
        }
        //wrong move
        if (movetype == Utils.MoveResult.WRONG){
            this.slipDecider(dir);
        }
        //fix later
        return reward[pos[0]][pos[1]];
    }

    public void printGrid(){

        for(int i=0 ; i < Utils.GRID_ROWS ; i++){
            for(int j=0 ; j < Utils.GRID_COLS ; j++){
                if( pos[0] == i &&  pos[1] == j  ){
                    System.out.print("*");
                }
                else if( ter[0] == i &&  ter[1] == j  ){
                    System.out.print("X"); 
                }
                else{
                    System.out.print("O"); 
                }
                if ( walls[i][j] == "R"){
                    System.out.print("|");
                } 
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    } 
}
