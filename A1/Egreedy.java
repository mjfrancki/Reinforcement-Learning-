import java.util.Random;
import java.util.Arrays;

public class Egreedy
{

  public static void main(String [ ] args)
  {



Bandit bandit = new Bandit();

int tottalBest=0;
int tottalRnd=0;
int percentRndPick=0;

int trueBest = bandit.tellMeTheTRUTH();
int tottalTrueBest =0;



double epsilon = 0.1;
int reward = 0;
int[] totalRewards = {0,0,0,0,0,0,0,0,0,0};
int[] totalPlays = {0,0,0,0,0,0,0,0,0,0};
double[] estRewards = {0,0,0,0,0,0,0,0,0,0};

int iterations = 1000;

for(int i = 1; i < iterations+1; i++){

  int max = 100;
  int min = 0;
  Random rand = new Random();
  int rnd = rand.nextInt(max - min + 1) + min  ;
  double epsPick = (double)rnd/(double)max ;


//Pick greedy or exploritory move
if(epsPick >= epsilon) //greedy
  {
  //  System.out.println("GREEDY");
    tottalBest++;

    int bestBandit = 0;

    for (int j = 1; j < estRewards.length; j++) {
        double temp = estRewards[j];
        if ( temp > estRewards[ bestBandit ] ) {
             bestBandit = j;
        }
    }
        //System.out.println("Arm " +  bestBandit  +" Picked" );


      //Play the best bandit arm
      reward = bandit.pullarm(bestBandit);
      if( bestBandit == trueBest)
            tottalTrueBest++;

       //System.out.println("Reward was  " + reward );

      totalPlays[bestBandit] ++;
      totalRewards[bestBandit] = reward + totalRewards[bestBandit];
      estRewards[bestBandit] = (double) totalRewards[bestBandit] / (double)totalPlays[bestBandit];





  }
  else{ //exploritory
  //System.out.println("Exploritory");
  tottalRnd++;
  int rndBandit = rand.nextInt(9 - 0 + 1) + 0  ;
    //System.out.println("Arm " + rndBandit +" Picked" );
  //play Random bandit arm
  reward = bandit.pullarm(rndBandit);
  if( rndBandit == trueBest)
        tottalTrueBest++;

//   System.out.println("Reward was  " + reward );

  totalPlays[rndBandit]++;
  totalRewards[rndBandit] = reward + totalRewards[rndBandit];
  estRewards[rndBandit] = (double)totalRewards[rndBandit] / (double)totalPlays[rndBandit];
}


if(i % 100 == 0){
  System.out.println("Iteration: " + i);
  //System.out.println(Arrays.toString(totalRewards));
  //System.out.println(Arrays.toString(totalPlays));
//  System.out.println(Arrays.toString(estRewards));
System.out.println("Average Rewards: ");
for(int k=0; k < estRewards.length; k++){

  System.out.print("Arm " + k + ": ");
  System.out.println(String.format( "%.2f", estRewards[k] ));
}


  System.out.println("The optimal arm was picked: " + tottalTrueBest );
}

}

//System.out.println("Picked Best(Estimate): " + tottalBest);
//System.out.println("Picked Random: " + tottalRnd);
//System.out.println("Percentage Random Pick: " + ((double)tottalRnd / iterations)*100 +"%");

System.out.println("The True best arm is: " + trueBest);
System.out.println("it was picked: " + ( (double)tottalTrueBest / iterations)*100 +"%");

//System.out.println("Percentage TRUE Best Pick: " + (double)tottalRnd / 1000.0);



}


}
