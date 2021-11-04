//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

class SafeBarrier extends Barrier {
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
        carsArrived = true;
        for (int i = 0; i < allowToPass.length; i++) {
            if(!allowToPass[i]) {
                carsArrived = false;
                break;
            }
        }
        if (carsArrived == true) {
            allowToPass = arrivedCars.clone();
            notifyAll();
        }
        while (!allowToPass[no]) {
            wait();
        }

        notifyAll();
        allowToPass[no] = false;
        arrivedCars[no] = false;
    }

    @Override
    public synchronized void on() {
        active = true;
    }

    @Override
    public synchronized void off() {
        active = false;
        allowToPass = arrivedCars.clone();
        notifyAll();
    }


    @Override
    // May be (ab)used for robustness testing
    public synchronized void set(int k) { 
        notifyAll();
    }  

}
