
import java.util.concurrent.Semaphore;
import java.util.*;

//Makoto Yuan
//makobyte@bu.edu
//Collaborator: Sam Choi

public class controller {
	//array of eleveator call requests
	static volatile int[] request = new int[5];
	//array of destinations
	static volatile int[] dest = new int[5];
	static volatile Semaphore[] floors = new Semaphore[5];
	static volatile Semaphore[] arrive = new Semaphore[5];
	static volatile Semaphore mutex = new Semaphore(1, false);
	static int c = 5;
	static Random rand = new Random();
	static occupant[] p = new occupant[20];
	public static void main(String[] args){

		
	for(int x = 0; x < 5; x++){
		floors[x] = new Semaphore(0,false);
		arrive[x] = new Semaphore(0,false);
	}

	
	for(int i = 0; i < 20; i++){
		p[i] = new occupant(rand.nextInt(5), i, rand.nextInt(5));
		p[i].start();
	}

	elevator elephant = new elevator();
	elephant.start();
	
	}
}
