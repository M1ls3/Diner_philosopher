using System;
using System.Threading.Tasks;

namespace Diner_philosopher
{
    class Philosopher
    {
        private int id;
        private int leftFork;
        private int rightFork;
        private Semaphore[] forks;

        public Philosopher(int id, Semaphore[] forks)
        {
            this.id = id;
            this.forks = forks;
            this.leftFork = id % forks.Length;
            //this.rightFork = ((id + 1) % forks.Length) == 0 ? forks.Length - 1 : (id + 1) % forks.Length;
            this.rightFork = (id + 1) % forks.Length;
        }

        public void Run()
        {
            for (int i = 1; i <= 10; i++)
            {
                Console.WriteLine("Philosopher " + id + " thinking " + i + " time");

                if (id % 2 != 0)
                {
                    forks[leftFork].WaitOne(); // error here
                    Console.WriteLine("Philosopher " + id + " took left fork");
                    forks[rightFork].WaitOne();
                    Console.WriteLine("Philosopher " + id + " took right fork");
                }
                else
                {
                    forks[rightFork].WaitOne();
                    Console.WriteLine("Philosopher " + id + " took right fork");
                    forks[leftFork].WaitOne();
                    Console.WriteLine("Philosopher " + id + " took left fork");
                }

                Console.WriteLine("Philosopher " + id + " eating " + i + " time");

                forks[rightFork].Release();
                Console.WriteLine("Philosopher " + id + " put right fork");
                forks[leftFork].Release();
                Console.WriteLine("Philosopher " + id + " put left fork");
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            int forksAmount = 5;
            int philAmount = 5;
            Semaphore[] forks = new Semaphore[forksAmount];
            for (int i = 0; i < forksAmount; i++)
            {
                forks[i] = new Semaphore(1, 1);
            }

            Philosopher[] philosophers = new Philosopher[philAmount];
            for (int i = 0; i < philAmount; i++)
            {
                philosophers[i] = new Philosopher(i + 1, forks);
                Thread philosopherThread = new Thread(philosophers[i].Run);
                philosopherThread.Start();
            }
        }
    }
}