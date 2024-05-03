import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws Exception {
        // Створюємо масив семафорів для виделок
        Semaphore[] forks = new Semaphore[5];
        // Ініціалізуємо кожен семафор з одним дозволом (початково доступною однією виделкою)
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }

        // Створюємо масив філософів
        Philosopher[] philosophers = new Philosopher[5];
        // Ініціалізуємо кожного філософа та запускаємо його в окремому потоці
        for (int i = 0; i < philosophers.length; i++) {
            // Кожен філософ отримує по одному ідентифікатору (1..5) та масив семафорів (виделок)
            philosophers[i] = new Philosopher(i + 1, forks);
            philosophers[i].start(); // Запускаємо потік філософа
        }
    }
}

