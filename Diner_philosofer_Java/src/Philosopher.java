import java.util.concurrent.Semaphore;

// Клас, що представляє філософа як окремий потік
class Philosopher extends Thread {
    private int id; // Ідентифікатор філософа
    private int leftFork; // Індекс лівої виделки в масиві
    private int rightFork; // Індекс правої виделки в масиві
    private Semaphore[] forks; // Масив семафорів, що представляють виделки
    public Philosopher(int id, Semaphore[] forks) {
        this.id = id; // Ініціалізація ідентифікатора філософа
        this.forks = forks; // Ініціалізація масиву виделок
        this.leftFork = id % forks.length; // Вибір лівої виделки за допомогою остачі від ділення
        this.rightFork = (id + 1) % forks.length; // Вибір правої виделки (наступної в масиві)
    }

    // Метод, що виконується у потоці філософа
    @Override
    public void run() {
        // Цикл, що представляє послідовні дії філософа
        for (int i = 1; i <= 10; i++) {
            // Друк повідомлення про думки філософа
            System.out.println("Philosopher " + id + " thinking " + i + " time");

            // Перевірка, чи ідентифікатор філософа парний чи непарний
            if (id % 2 != 0) {
                // Якщо непарний, філософ бере спочатку ліву, потім праву виделку
                forks[leftFork].acquireUninterruptibly(); // Блокування лівої виделки
                System.out.println("Philosopher " + id + " took left fork");
                forks[rightFork].acquireUninterruptibly(); // Блокування правої виделки
                System.out.println("Philosopher " + id + " took right fork");
            } else {
                // Якщо парний, філософ бере спочатку праву, потім ліву виделку
                forks[rightFork].acquireUninterruptibly(); // Блокування правої виделки
                System.out.println("Philosopher " + id + " took right fork");
                forks[leftFork].acquireUninterruptibly(); // Блокування лівої виделки
                System.out.println("Philosopher " + id + " took left fork");
            }

            System.out.println("Philosopher " + id + " eating " + i + " time");

            // Звільнення правої виделки
            forks[rightFork].release();
            System.out.println("Philosopher " + id + " put right fork");
            // Звільнення лівої виделки
            forks[leftFork].release();
            System.out.println("Philosopher " + id + " put left fork");
        }
    }
}
