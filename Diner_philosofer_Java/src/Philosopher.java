import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private int id;
    private int leftFork;
    private int rightFork;
    private Semaphore[] forks;

    public Philosopher(int id, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;
        this.leftFork = id % forks.length;
        this.rightFork = (id + 1) % forks.length;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Philosopher " + id + " thinking " + i + " time");

            if (id % 2 != 0) {
                forks[leftFork].acquireUninterruptibly();
                System.out.println("Philosopher " + id + " took left fork");
                forks[rightFork].acquireUninterruptibly();
                System.out.println("Philosopher " + id + " took right fork");
            } else {
                forks[rightFork].acquireUninterruptibly();
                System.out.println("Philosopher " + id + " took right fork");
                forks[leftFork].acquireUninterruptibly();
                System.out.println("Philosopher " + id + " took left fork");
            }

            System.out.println("Philosopher " + id + " eating " + i + " time");

            forks[rightFork].release();
            System.out.println("Philosopher " + id + " put right fork");
            forks[leftFork].release();
            System.out.println("Philosopher " + id + " put left fork");
        }
    }
}
