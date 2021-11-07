//Naive implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

class NaiveBarrier extends Barrier {
    
    int arrived = 0;
    boolean active = false;
   
    public NaiveBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException{

        if (!active) return;

        try {
 			Thread.sleep(2000);
 		} catch (InterruptedException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
        	arrived++;
            
            if (arrived < 9) { 
            	wait();
				}
             else {
                arrived = 0;
                notifyAll();
            }

    }

    @Override
    public synchronized void on() {
        active = true;
    }

    @Override
    public synchronized void off() {

        active = false;
        arrived = 0;
        notifyAll();

    }


    @Override
    // May be (ab)used for robustness testing
    // spurious wakeup
    public synchronized void set(int k) { 
    	notifyAll();
    }    
   

}
