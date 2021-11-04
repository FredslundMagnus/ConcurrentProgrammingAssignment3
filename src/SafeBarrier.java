//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

class SafeBarrier extends Barrier {
    int arrived = 0;
    int count = 0;
    int count2 = 0;
    boolean active = false, carsArrived = false;
    boolean[] allowToPass = new boolean[9];
    boolean[] arrivedCars = new boolean[9];

    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {

        if (!active) return;
        arrivedCars[no] = true;
        arrived++;
        count++;
        carsArrived = true;
        for (int i = 0; i < allowToPass.length; i++) {
            if(!allowToPass[i]) {
                carsArrived = false;
                break;
            }
        }
        while (!carsArrived && active) {
            wait();
        }

        notifyAll();
        count--;
        count2++;
        while (count != 0 && active) {
            wait();
        }
        notifyAll();
        arrived = 0;
        count2--;
        while (count2 != 0) {
            wait();
        }
        notifyAll();
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
    public synchronized void set(int k) { 
        notifyAll();
    }  

}
