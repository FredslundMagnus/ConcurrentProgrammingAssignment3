//Basic implementation of Alley class (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021

public class BasicAlley extends Alley {

    boolean isGoingUp;
    int insideAlley;

    BasicAlley() {
        insideAlley = 0;
        isGoingUp = true;
    }

    @Override
    /* Block until car no. may enter alley */
    public synchronized void enter(int no) throws InterruptedException {
        if (no < 5) {
            while (insideAlley > 0 && isGoingUp) {
                wait();
            }
            isGoingUp = false;
            insideAlley++;
        } else {
            while (insideAlley > 0 && !isGoingUp) {
                wait();
            }
            isGoingUp = true;
            insideAlley++;
        }
    }

    @Override
    /* Register that car no. has left the alley */
    public synchronized void leave(int no) {
        insideAlley--;
        if (insideAlley == 0) {
            notifyAll();
        }
    }

}
