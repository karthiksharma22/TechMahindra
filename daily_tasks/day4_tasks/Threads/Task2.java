package day4_tasks.Threads;

public class Task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread evenThread = new Thread(() -> {
            for (int i = 2; i <= 20; i += 2) {
                System.out.println("Even: " + i);
                try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        Thread oddThread = new Thread(() -> {
            for (int i = 1; i <= 20; i += 2) {
                System.out.println("Odd: " + i);
                try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        evenThread.start();
        oddThread.start();
	}

}
