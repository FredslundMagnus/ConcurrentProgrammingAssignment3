//Abstract Gate class
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021

public abstract class Gate {

    public static Gate create() {
        return new MonGate(); // Change to select implementation
    }

    public abstract void pass() throws InterruptedException;

    public abstract void open();

    public abstract void close();
}
