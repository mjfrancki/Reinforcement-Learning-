import java.util.Random;
import java.util.Arrays;

public class LR{





public static void main(String [ ] args)
{

  Bandit bandit = new Bandit();
  int trueBest = bandit.tellMeTheTRUTH();
  //initial p equal prop for each arm
  double[] p ={0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10};
  //double interval ={0,0.10,0.20,0.30,0.40,0.50,0.60,0.70,0.80,0.90,0.10};

  double alpha = 0.1;
  double beta = 0.001;
   int iterations = 1000;
   int tottalTrueBest = 0;


System.out.println("---------LR-I---------");

  for(int i = 1; i < iterations+1; i++){


    int max = 1000000;
    int min = 0;
    Random rand = new Random();
    int rnd = rand.nextInt(max - min + 1) + min  ;


    double rv = (double)rnd/(double)max ;


//double x = arraySum(p,2,7);
//System.out.println( x );

int reward = 0;
int arm = 0;


//---------------LR-I-----------------------

    if( rv > arraySum(p,0,8)  ){
      reward = bandit.pullarm(9);
      arm = 9;
     }
     else if( rv > arraySum(p,0,7)  ){
       reward = bandit.pullarm(8);
         arm = 8;
     }
     else if( rv > arraySum(p,0,6)  ){
      reward = bandit.pullarm(7);
        arm = 7;
     }
     else if( rv > arraySum(p,0,5)  ){
      reward = bandit.pullarm(6);
        arm = 6;
     }
     else if( rv > arraySum(p,0,4)  ){
      reward = bandit.pullarm(5);
        arm = 5;
     }
     else if( rv > arraySum(p,0,3)  ){
      reward = bandit.pullarm(4);
        arm = 4;
     }
     else if( rv > arraySum(p,0,2)  ){
      reward = bandit.pullarm(3);
        arm = 3;
     }
     else if( rv > arraySum(p,0,1)  ){
      reward =  bandit.pullarm(2);
        arm = 2;
     }
     else if( rv > p[0]  ){
       reward =bandit.pullarm(1);
         arm = 1;
     }
     else{

        reward =bandit.pullarm(0);
        arm = 0;

     }

     if( arm == trueBest)
           tottalTrueBest++;



//updat p
if(i % 100 == 0){
System.out.println("Iteration:  "+ i );

//System.out.println("arm: " + arm );
//System.out.println("reward: " + reward );
//System.out.println(Arrays.toString(p));
}


  if(reward == 1){
    for(int k = 0; k < p.length; k++){

      if(k == arm ){
        p[k] =   p[k] +  (alpha  * ( 1.0 - p[k]) );
      }
      else
        p[k] = (1 - alpha) * p[k];

      }

    }

    if(i % 100 == 0){
        System.out.println(Arrays.toString(p));
          System.out.println("The optimal arm was picked: " + tottalTrueBest );
      }

}


//---------------LR-P-----------------------
System.out.println("---------LR-P---------");
tottalTrueBest = 0;
double[] q ={0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10,0.10};

for(int i = 1; i < iterations+1; i++){


  int max = 10000000;
  int min = 0;
  Random rand = new Random();
  int rnd = rand.nextInt(max - min + 1) + min  ;


  double rv = (double)rnd/(double)max ;

  int reward = 0;
  int arm = 0;


if( rv > arraySum(q,0,8)  ){
  reward = bandit.pullarm(9);
  arm = 9;
 }
 else if( rv > arraySum(q,0,7)  ){
   reward = bandit.pullarm(8);
     arm = 8;
 }
 else if( rv > arraySum(q,0,6)  ){
  reward = bandit.pullarm(7);
    arm = 7;
 }
 else if( rv > arraySum(q,0,5)  ){
  reward = bandit.pullarm(6);
    arm = 6;
 }
 else if( rv > arraySum(q,0,4)  ){
  reward = bandit.pullarm(5);
    arm = 5;
 }
 else if( rv > arraySum(q,0,3)  ){
  reward = bandit.pullarm(4);
    arm = 4;
 }
 else if( rv > arraySum(q,0,2)  ){
  reward = bandit.pullarm(3);
    arm = 3;
 }
 else if( rv > arraySum(q,0,1)  ){
  reward =  bandit.pullarm(2);
    arm = 2;
 }
 else if( rv > q[0]  ){
     reward =bandit.pullarm(1);
     arm = 1;
 }
 else{

    reward =bandit.pullarm(0);
    arm = 0;

 }


 if( arm == trueBest)
       tottalTrueBest++;

//updat p
if(i % 100 == 0){
System.out.println("Iteration:  "+ i );

//System.out.println("arm: " + arm );
//System.out.println("reward: " + reward );
//System.out.println(Arrays.toString(q));
}


if(reward == 1){
  for(int k = 0; k < q.length; k++){

      if(k == arm ){
          q[k] =   q[k] +  (alpha  * ( 1.0 - q[k]) );
        }
        else
          q[k] = (1 - alpha) * q[k];

  }

}


if(reward == 0){
  for(int k = 0; k < q.length; k++){

      if(k == arm ){
          q[k] =   (1.0 - beta)*q[k];
        }

        else
          q[k] = (beta/9) + (1.0 - beta) * q[k];

    }
}


        if(i % 100 == 0){
            System.out.println(Arrays.toString(q));
            System.out.println("The optimal arm was picked: " + tottalTrueBest );

          }


}


}



public static double arraySum(double[] arr, int a, int b){

 double sum = 0;


    for(int i = a; i <= b  ;i++ ){

      sum = sum + arr[i];

    }

  return sum;
}



}
