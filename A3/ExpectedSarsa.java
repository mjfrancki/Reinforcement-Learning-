public class ExpectedSarsa extends Algorithm {

    protected void updateValueFunction(Utils.Move move) {
        int[] newPos = world.getPos();
        double sum = 0;
        for(int k = 0; k < Utils.MOVE_SIZE; k++){
            double pi =  value[newPos[0]][newPos[1]][k] / Utils.MOVE_SIZE;
            sum += pi * value[newPos[0]][newPos[1]][k];
        }

        value[pos[0]][pos[1]][move.ordinal()] += stepSize * (rewards[pos[0]][pos[1]][move.ordinal()] + 
                discountfactor * sum - value[pos[0]][pos[1]][move.ordinal()]);
    }
    protected void postEpisodeUpdate() {}
    protected String getAlgorithmName() { return "Expected Sarsa"; }

    public static void main(String[] args) {
        Algorithm algo = new ExpectedSarsa();
        Utils.setAlgorithmPs(algo, args);
        algo.run();
    }
}
