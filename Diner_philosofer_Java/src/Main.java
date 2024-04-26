import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws Exception {
        Semaphore[] forks = new Semaphore[5];
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i + 1, forks);
            philosophers[i].start();
        }
    }
}
