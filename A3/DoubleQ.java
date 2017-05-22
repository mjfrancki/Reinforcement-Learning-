public class DoubleQ extends Algorithm {

    protected double[][][] value2 = new double[Utils.GRID_ROWS][Utils.GRID_COLS][Utils.MOVE_SIZE];

    protected void updateValueFunction(Utils.Move move) {
        int[] newPos = world.getPos();
        int rnd = rand.nextInt(2);
        if (rnd == 0) { //update q1
            int maxInx = 0;
            for(int i = 1; i < Utils.MOVE_SIZE; i++) {
                if(value[newPos[0]][newPos[1]][i] > value[newPos[0]][newPos[1]][maxInx])
                    maxInx = i;
            }
            Utils.Move newMove = Utils.MOVES[maxInx];
            value[pos[0]][pos[1]][move.ordinal()] += stepSize * (rewards[pos[0]][pos[1]][move.ordinal()] + 
                    discountfactor * value2[newPos[0]][newPos[1]][newMove.ordinal()] - value[pos[0]][pos[1]][move.ordinal()]);
        } else { //update q2
            int maxInx = 0;
            for(int i = 1; i < Utils.MOVE_SIZE; i++) {
                if(value2[newPos[0]][newPos[1]][i] > value2[newPos[0]][newPos[1]][maxInx])
                    maxInx = i;
            }
            Utils.Move newMove = Utils.MOVES[maxInx];
            value2[pos[0]][pos[1]][move.ordinal()] += stepSize * (rewards[pos[0]][pos[1]][move.ordinal()] + 
                    discountfactor * value[newPos[0]][newPos[1]][newMove.ordinal()] - value2[pos[0]][pos[1]][move.ordinal()]);
        }
    }

    protected void postEpisodeUpdate() {}
    protected String getAlgorithmName() { return "Double Q-Learning"; }

    public static void main(String[] args) {
        Algorithm algo = new DoubleQ();
        Utils.setAlgorithmPs(algo, args);
        algo.run();
    }
}
