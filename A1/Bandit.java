import java.util.Random;

public class Bandit
{

//  double[] arms = {0.90,0.80,0.70,0.60,0.50,0.40,0.30,0.20,0.10,0.10};
 double[] arms = {0.10,0.30,0.20,0.35,0.25,0.70,0.15,0.05,0.45,0.10};


    public Bandit(){
    }

//pull arm armindex
    public int pullarm(int armindex){
      int max = 100;
      int min = 0;
      Random rand = new Random();
      int rnd = rand.nextInt(max - min + 1) + min  ;

      double x = (float)rnd/(float)max ;

      if( arms[armindex] >= x ){
        return 1;
      }

      else
        return 0;

    }

//Ask bandit what the best arm is
    public int tellMeTheTRUTH(){

      int bestBandit = 0;
      double temp = 0;

      for (int j = 1; j < arms.length; j++) {
          temp = arms[j];
          if ( temp > arms[ bestBandit ] ) {
               bestBandit = j;
          }
      }

          return bestBandit;

    }


}
