
import java.util.concurrent.Semaphore;
import java.math.*;

//Makoto Yuan
//makobyte@bu.edu
//Collaborator: Sam Choi


public class elevator extends Thread {
	static volatile Semaphore capacity = new Semaphore(3, false);
	int curFloor;
	int checkFloor;
	boolean up = true;
	public void run(){
		while(true){
			if(up){
			//Checks each floor going up
				
					//IMPLEMENT CURFLOOR CHECKFLOOR
					for(int checkFloor = curFloor; checkFloor < 5; checkFloor++){
						
						//If someone inside elevator requests next floor, or if avaliable permits and someone on next floor request elevator
						if(controller.dest[checkFloor] >0 || (capacity.availablePermits() > 0 && controller.request[checkFloor] > 0)){
							//Sleep for distance of floors
							try {Thread.sleep(Math.abs(checkFloor - curFloor)*controller.c);} 
							catch (InterruptedException e) {} 
							
							curFloor = checkFloor;
							
						//Release all people in elevator trying to leave on curFloor
							while(controller.dest[curFloor] >  0){
								
								try {
									controller.mutex.acquire();
								} catch (InterruptedException e1) {
								}
								controller.dest[curFloor]--;
								controller.mutex.release();
								controller.arrive[curFloor].release();
								elevator.capacity.release();
							}
							
							try {Thread.sleep(200);} 
							catch (InterruptedException e1) {}
							
							while(capacity.availablePermits() > 0 && controller.request[curFloor] > 0){
								controller.request[curFloor]--;
								controller.floors[curFloor].release();
								try {
									elevator.capacity.acquire();
								} catch (InterruptedException e) {

								}
							}
							
					}
						
					}
					up = false;
				}
			
			
			if(!up){
				//Checks each floor going up
					
						//IMPLEMENT CURFLOOR CHECKFLOOR
						for(int checkFloor = curFloor; checkFloor >= 0; checkFloor--){
							
							//If someone inside elevator requests next floor, or if avaliable permits and someone on next floor request elevator
							if(controller.dest[checkFloor] >0 || (capacity.availablePermits() > 0 && controller.request[checkFloor] > 0)){

								try {
									Thread.sleep(Math.abs(checkFloor - curFloor) * controller.c);
								} catch (InterruptedException e) {
								} 
								curFloor = checkFloor;
								
							//Release all people in elevator trying to leave on curFloor
								while(controller.dest[curFloor] > 0){
									try {
										controller.mutex.acquire();
									} catch (InterruptedException e1) {
									}
									controller.dest[curFloor]--;
									controller.mutex.release();
									
									//elevator.capacity.release();
									controller.arrive[curFloor].release();
									elevator.capacity.release();
								}
							
								try {Thread.sleep(50);} 
								catch (InterruptedException e1) {}
								
								while(capacity.availablePermits() > 0 && controller.request[curFloor] > 0){
									controller.request[curFloor]--;
									controller.floors[curFloor].release();
									try {
										elevator.capacity.acquire();
									} catch (InterruptedException e) {

									}
								}
								
						}
							
						}
						up = true;
					}
			
			}
		}
	}
