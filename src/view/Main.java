package view;
import java.util.concurrent.Semaphore;
import controller.Cozinha;
public class Main {

	public static void main(String[] args) {
		
		Semaphore semaforo = new Semaphore(1);
		
		for(int i = 0; i < 5; i++) {
			Thread t = new Cozinha(semaforo);
			t.start();
		}
	}
}