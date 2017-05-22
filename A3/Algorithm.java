import java.util.*;


public abstract class Algorithm {

    protected static final int MAX_RAND = 1000000;
    protected static final int MAX_ITERATIONS = 1000;
    protected static final int MIN_ITERATIONS = 1000;
    protected static final int MAX_EPISODE_ITERATIONS = 1000;
    protected static final int MAX_REWARD_LENGTH = 100;
    protected static final int CONVERGENCE_CHECK = 1000;
    protected double[][][] value = new double[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
    protected double[][][] prevValue = new double[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
    protected double[][][] rewards = new double[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
    protected int[][][] numberOfrewards = new int[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
    protected boolean[][][] visitedThisEpisode = new boolean[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
    protected double discountfactor = 0.9;
    protected double explorationParam = 0.1;
    protected double stepSize = 0.1;
    protected Random rand = new Random(System.currentTimeMillis());
    protected Gridworld world = new Gridworld(rand.nextInt(Utils.GRID_ROWS),rand.nextInt(Utils.GRID_COLS));

    protected int[] pos = {0,0};
    protected abstract void updateValueFunction(Utils.Move move);
    protected abstract void postEpisodeUpdate();
    protected abstract String getAlgorithmName();

    public void run() {
        int startX = 0;
        int startY = 0;
        String startMove = "";
        double reward = 0;
        int moveNum = 0;
        Utils.Move move;
        long totalStartTime = System.currentTimeMillis();
        long totalTimeSteps = 0;
        int iter;
        //episode loop
        for(iter = 0; 
                (iter < MIN_ITERATIONS || (iter % CONVERGENCE_CHECK != 0 || !hasValueFunctionConverged())) && 
                iter < MAX_ITERATIONS; 
                iter++){
            if (iter % CONVERGENCE_CHECK == 0) {
                prevValue = copy3dArray(value);
            }
            startY = rand.nextInt(Utils.GRID_ROWS);
            startX = rand.nextInt(Utils.GRID_COLS);
            move = rndMove();
            world.setPos(startX ,startY) ;

            rewards = new double[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
            numberOfrewards = new int[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];
            visitedThisEpisode = new boolean[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];

            moveNum = move.ordinal();
            int startMovee = moveNum;
            rewards[startX][startY][moveNum] += world.move(move) ;
            visitedThisEpisode[startX][startY][moveNum] = true;
            int count;
            for (count = 1; !world.isDone() && count < MAX_EPISODE_ITERATIONS; count++) {
                pos = world.getPos();
                move = eGreedyMove(pos[0], pos[1]);

                reward = world.move(move) ;
                moveNum = move.ordinal();
                visitedThisEpisode[pos[0]][pos[1]][moveNum] = true;

                //discounting
                for(int i = 0; i < Utils.GRID_ROWS; i++) {
                    for(int j = 0; j < Utils.GRID_COLS; j++) {
                        for(int k = 0; k < Utils.MOVE_SIZE; k++) {
                            if (visitedThisEpisode[i][j][k] && numberOfrewards[i][j][k] < MAX_REWARD_LENGTH) {
                                numberOfrewards[i][j][k]++; 
                                rewards[i][j][k] += reward * (Math.pow(discountfactor, numberOfrewards[i][j][k]))  ;
                            }
                        }
                    }
                }
                this.updateValueFunction(move);
            }
            totalTimeSteps += count;
            this.postEpisodeUpdate();
        }
        long totalEndTime = System.currentTimeMillis();

        Utils.printStats(this.getAlgorithmName(), iter, totalTimeSteps,
                totalEndTime - totalStartTime, value);
    }

    protected Utils.Move rndMove(){
        int n = rand.nextInt(Utils.MOVE_SIZE);
        return Utils.MOVES[n];
    }

    protected Utils.Move eGreedyMove(int x, int y) {
        int n = rand.nextInt(MAX_RAND) + 1;
        double prob = ((double)n)/MAX_RAND;

        if(prob <= explorationParam) {
            return rndMove();
        } else {
            return bestMove(x,y);
        }
    }

    protected Utils.Move bestMove(int x, int y){
        int maxInx = 0;
        int rnd = 0;
        for(int i = 0; i < Utils.MOVE_SIZE; i++) {
            rnd = rand.nextInt(2);
            if( value[x][y][i] == value[x][y][maxInx] ){
                if(rnd== 0){
                    maxInx = i;
                }
            }
            if(value[x][y][i] > value[x][y][maxInx])
                maxInx = i;
        }
        return Utils.MOVES[maxInx];
    }
    private boolean hasValueFunctionConverged() {
        for(int i = 0; i < Utils.GRID_ROWS; i++) {
            for(int j = 0; j < Utils.GRID_COLS; j++) {
               Utils.Move prevMove = getMove(prevValue[i][j]);
               Utils.Move currMove = getMove(value[i][j]);
               if (prevMove != currMove) {
                   return false;
               }
            }
        }
        return true;
    }
    private double[][][] copy3dArray(double[][][] array) {
        double[][][] copy = new double[array.length][][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new double[array[i].length][];
            for (int j = 0; j < array[i].length; j++) {
                copy[i][j] = new double[array[i][j].length];
                System.arraycopy(array[i][j], 0, copy[i][j], 0, 
                        array[i][j].length);
            }
        }
        return copy;
    } 

    private Utils.Move getMove(double [] values) {
        int maxInx = 0;
        for(int i = 1; i < Utils.MOVE_SIZE; i++) {
            if(values[i] > values[maxInx])
                maxInx = i;
        }
        return Utils.MOVES[maxInx];
    }

    public void setPs(double p1, double p2) {
        world.setPs(p1, p2);
    }
}
