//Implementation of Alley class with inner alley (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021

public class DoubleAlley extends Alley {

    int insideAlleyUp;
    int insideAlleyDown;
    int insideInnerAlley;

    DoubleAlley() {
        insideAlleyUp = 0;
        insideAlleyDown = 0;
        insideInnerAlley = 0;
    }

    @Override
    /* Block until car no. may enter alley */
    public synchronized void enter(int no) throws InterruptedException {
        if (no < 3) {
            while (insideInnerAlley > 0) {
                wait();
            }
            insideAlleyDown++;
        } else if (no < 5) {
            while (insideAlleyUp > 0) {
                wait();
            }
            insideAlleyDown++;
        } else {
            while (insideAlleyDown > 0) {
                wait();
            }
            insideAlleyUp++;
            insideInnerAlley++;
        }
    }

    @Override
    /* Register that car no. has left the alley */
    public synchronized void leave(int no) {
        if (no < 5) {
            insideAlleyDown--;
            if (insideAlleyDown == 0) {
                notifyAll();
            }
        } else {
            insideAlleyUp--;
            if (insideAlleyUp == 0) {
                notifyAll();
            }
        }
    }

    @Override
    /* Register that car no. has left the inner alley */
    public synchronized void leaveInner(int no) {
        insideInnerAlley--;
        if (insideInnerAlley == 0) {
            notifyAll();
        }
    }

}
