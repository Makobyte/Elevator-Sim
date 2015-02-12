
import java.util.*;

//Makoto Yuan
//makobyte@bu.edu
//Collaborator: Sam Choi


public class occupant extends Thread{
	private Random rand = new Random();
	private int onFloor;
	private int occupantID;
	private int reqFloor = rand.nextInt(5);

	
	
	public occupant(int curFloor, int occupantID, int reqFloor){
		onFloor = curFloor;
		this.occupantID = occupantID;
		this.reqFloor = reqFloor;
	}
	
	public void run(){
		while(true){
		try{ controller.mutex.acquire();}
		catch(InterruptedException e){ }
		
		//Occupant "presses button" and requests elevator on current floor
		controller.request[onFloor]++;
		
		controller.mutex.release();
		
		System.out.println("Occupant "+ occupantID + " calls elevator on floor "+ onFloor);
		
		try{
			
		//Wait for elevator to arrive
		controller.floors[onFloor].acquire();
		}
		catch(InterruptedException e){
		}
		
		//Increases occupancy of elevator by 1

		/*
		try {
			elevator.capacity.acquire();
		} catch (InterruptedException e1) {
		}
		*/
		//Occupant now in elevator and requests random floor
		System.out.println("Occupant "+ occupantID + " has entered on "+ onFloor);
		
		try {
			controller.mutex.acquire();
		} catch (InterruptedException e1) {
		}
		controller.dest[reqFloor]++;
		controller.mutex.release();
		
		System.out.println("Occupant "+ occupantID + " in elevator requests floor "+ reqFloor);
		
		try{
		//Waits for elevator to reach floor
			
		controller.arrive[reqFloor].acquire();
		}
		
		catch(InterruptedException e){}
		
		//"Leave" elevator
		System.out.println("Occupant "+ occupantID + " has left on "+ reqFloor);
		
		//elevator.capacity.release();
		
		try{
			Thread.sleep(controller.c * rand.nextInt(100));
		}
		catch(InterruptedException e){
		}
		
		reqFloor = rand.nextInt(5);
		
	}
	}
}
