package controller;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cozinha extends Thread {
	
	Semaphore semaforo;
	int min, max;
	
	public Cozinha(Semaphore semaforo) {
		this.semaforo = semaforo;
	}
	
	private int prato() {
		return (int) threadId();
	}
	
	private String nomePrato() {
		int prato = prato();
		
		if(prato % 2 == 0) {
			return "lasanha";
		}else {
			return "sopa de cebola";
		}
	}
	
	private void cozimento() {
		int tempoMax = tempo();
		double porcen = 0;
		int cozimentoTotal = 0;
		
		double ciclo = 0.1;
		while(cozimentoTotal < tempoMax) {
			
			try {
				sleep(100);
				while(cozimentoTotal < tempoMax*ciclo) {
					cozimentoTotal += (tempoMax*0.01);
					porcen = (cozimentoTotal/(double)tempoMax * 100);
					porcen = (int)porcen;
				}
				ciclo += 0.1;
				System.out.println("Percentual da " + nomePrato() + ": " + porcen + "%");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("A " + nomePrato() + " está pronta para entrega");
	}

	private void entrega() {
		try {
			sleep(500);
			System.out.println(nomePrato() + " foi entregue");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int tempo() {
		int prato = prato();
		
		if(prato % 2 == 0) {
			min = 600;
			max = 1200;
			System.out.println("Pedido: Lasanha");
		}else {
			min = 500;
			max = 800;
			System.out.println("Pedido: Sopa de Cebola");
		}
		
		Random rand = new Random();
		prato = rand.nextInt(min, max);
		
		return prato;
	}
	
	@Override
	public void run() {
		cozimento();
		
		try {
			semaforo.acquire();
			entrega();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}
}