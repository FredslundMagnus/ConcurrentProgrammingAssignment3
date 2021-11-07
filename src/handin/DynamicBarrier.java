//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

class DynamicBarrier extends Barrier {
    boolean active = false;
    boolean[] allowToPass = new boolean[9];
    boolean[] arrivedCars = new boolean[9];
    int treshHold = 9;
    int arrived = 0;
    boolean allowNewThreshold = false;
    int newThreshold = 9;
    
    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if (!active) return;
        arrivedCars[no] = true;
        arrived = 0;
        for (int i = 0; i < allowToPass.length; i++) {
            if(arrivedCars[i]) {
                arrived++;
            }
        }
        if (arrived >= treshHold) {
            for (int i = 0; i < allowToPass.length; i++) {
                allowToPass[i] = arrivedCars[i] || allowToPass[i];
            }
            for (int i = 0; i < allowToPass.length; i++) {
                arrivedCars[i] = false;
            }
            allowNewThreshold = true;
            treshHold = newThreshold;
            notifyAll();
        }
        while (!allowToPass[no]) {
            wait();
        }
        allowToPass[no] = false;
    }

    @Override
    public synchronized void on() {
        active = true;
    }

    @Override
    public synchronized void off() {
        active = false;
        for (int i = 0; i < allowToPass.length; i++) {
            allowToPass[i] = arrivedCars[i] || allowToPass[i];
        }
        for (int i = 0; i < allowToPass.length; i++) {
            arrivedCars[i] = false;
        }
        allowNewThreshold = true;
        treshHold = newThreshold;
        notifyAll();
    }

    @Override
    /* Set barrier threshold */
    public synchronized void set(int k) {
        newThreshold = k;
        if (k <= treshHold) {
            treshHold = k;
            arrived = 0;
            for (int i = 0; i < allowToPass.length; i++) {
                if(arrivedCars[i]) {
                    arrived++;
                }
            }
            if (arrived >= treshHold) {
                for (int i = 0; i < allowToPass.length; i++) {
                    allowToPass[i] = arrivedCars[i] || allowToPass[i];
                }
                for (int i = 0; i < allowToPass.length; i++) {
                    arrivedCars[i] = false;
                }
                notifyAll();
            }
            return;
        }
        allowNewThreshold = false;
        try {while (!allowNewThreshold) {wait();}} catch (InterruptedException e) { }
    }

}
