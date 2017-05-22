import java.util.*;

public class Utils {

    public static enum MoveResult { CORRECT, NONE, WRONG };
    public static enum Move {UP, DOWN, LEFT, RIGHT};
    public static final Move[] MOVES = Move.values();
    public static final int GRID_ROWS = 10;
    public static final int GRID_COLS = 10;
    public static final int MOVE_SIZE = Move.values().length;
    private static  Random rand = new Random();
    private static int padding = 30;

    public static void printStats(String name, long numEpisodes, long timeSteps, long computeTime, double[][][]value) {
        System.out.printf(
                String.format("%" + padding + "s","ALGORITHM:")+"%16s\n" + 
                String.format("%" + padding + "s","NUMBER OF EPISODES:")+"%16d\n" + 
                String.format("%" + padding + "s","TOTAL TIME STEPS:")+"%16d\n" +
                String.format("%" + padding + "s","AVG EPISODE STEPS:")+"%16d\n" +
                String.format("%" + padding + "s","TOTAL COMPUTATION TIME:")+"%16d ms\n" +
                String.format("%" + padding + "s","AVG EPISODE COMPUTATION TIME:")+"%16d ms\n"
                , name, numEpisodes, timeSteps, (long)(timeSteps / (double)numEpisodes), 
                computeTime,(long)(computeTime / (double)numEpisodes));
        printPolicy(value);
    }

    public static void printPolicy(double[][][] value){
        int maxInx = 0;
        for(int i = 0; i < Utils.GRID_ROWS; i++){
            for(int j = 0; j < Utils.GRID_COLS; j++){
                maxInx = 0;
                for(int k = 0; k < Utils.MOVE_SIZE; k++){
                    if(value[i][j][k] > value[i][j][maxInx])
                        maxInx = k;
                }
                System.out.print(String.format("%1$-6s",Utils.MOVES[maxInx]));
            }
            System.out.println();
        }
    }


    public static void setAlgorithmPs(Algorithm algo, String[]args) {
        double p1 = 1;
        double p2 = 0;
        if(args.length == 2){
            p1 = Double.parseDouble(args[0]);
            p2 = Double.parseDouble(args[1]);
            if (p1 + p2 > 1){
                p1 = 1;
                p2 = 0; 
                System.out.println("Bad input: default p1 = " + p1 +" , default p2 = "+ p2); 
            }
        }
        else{
            System.out.println("default p1 = " + p1 +" , default p2 = "+ p2);  
        }
        algo.setPs(p1, p2);
    }
}
