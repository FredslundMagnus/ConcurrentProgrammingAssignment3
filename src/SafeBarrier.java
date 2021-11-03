//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

class SafeBarrier extends Barrier {
    int arrived = 0;
    boolean active = false;

    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {

        if (!active) return;
        arrived++;


        if (arrived < 9) {
            wait();
        }
        while (active) {
            wait();
        }
        arrived = 0;
        notifyAll();

    }

    @Override
    public void on() {
        active = true;
    }

    @Override
    public synchronized void off() {
        active = false;
        arrived = 0;
        notifyAll();
    }

/*    
    @Override
    // May be (ab)used for robustness testing
    public void set(int k) {
    }
*/

}
