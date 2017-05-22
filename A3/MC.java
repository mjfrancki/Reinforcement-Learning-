public class MC extends Algorithm {

    protected void updateValueFunction(Utils.Move move) { }

    protected void postEpisodeUpdate() {
        for(int i = 0; i < Utils.GRID_ROWS; i++){
            for(int j = 0; j < Utils.GRID_COLS; j++){
                for(int k = 0; k < Utils.MOVE_SIZE; k++){
                    value[i][j][k] = value[i][j][k] + (stepSize * (rewards[i][j][k] - value[i][j][k])) ;
                }
            }
        }
    }
    protected String getAlgorithmName() { return "Monte Carlo"; }

    public static void main(String[] args) {
        Algorithm algo = new MC();
        Utils.setAlgorithmPs(algo, args);
        algo.run();
    }
}
