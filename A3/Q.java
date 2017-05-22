public class Q extends Algorithm {

    protected void updateValueFunction(Utils.Move move) {
        int[] newPos = world.getPos();
        int maxInx = 0;
        for(int i = 1; i < Utils.MOVE_SIZE; i++) {
            if(value[newPos[0]][newPos[1]][i] > value[newPos[0]][newPos[1]][maxInx])
                maxInx = i;
        }
        Utils.Move newMove = Utils.MOVES[maxInx];
        value[pos[0]][pos[1]][move.ordinal()] += stepSize * (rewards[pos[0]][pos[1]][move.ordinal()] + 
                discountfactor * value[newPos[0]][newPos[1]][newMove.ordinal()] - value[pos[0]][pos[1]][move.ordinal()]);
    }

    protected void postEpisodeUpdate() {}
    protected String getAlgorithmName() { return "Q-Learning"; }

    public static void main(String[] args) {
        Algorithm algo = new Q();
        Utils.setAlgorithmPs(algo, args);
        algo.run();
    }
}
