public class Sarsa extends Algorithm {

    protected void updateValueFunction(Utils.Move move) {
        int[] newPos = world.getPos();
        Utils.Move newMove = eGreedyMove(newPos[0], newPos[1]);
        value[pos[0]][pos[1]][move.ordinal()] += stepSize * (rewards[pos[0]][pos[1]][move.ordinal()] + 
                discountfactor * value[newPos[0]][newPos[1]][newMove.ordinal()] - value[pos[0]][pos[1]][move.ordinal()]);
    }
    protected void postEpisodeUpdate() {}
    protected String getAlgorithmName() { return "Sarsa"; }

    public static void main(String[] args) {
        Algorithm algo = new Sarsa();
        Utils.setAlgorithmPs(algo, args);
        algo.run();
    }
}
