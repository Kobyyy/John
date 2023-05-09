package markouni;

import java.util.Random;

public class Consumer extends Thread {

	private int ID;
	private boolean help;

	public Consumer(int ID) {
		this.ID = ID;
	}

	public void run() {
		/*
		 * Abbiamo un ciclo While che itera secondo il numero di iterazioni massimo
		 * inserito dall'utente nel main ed imagazzinato nella variabile N_ITERAZIONI,
		 * una volta che la variabile ITERAZIONE_COPRRENTE raggiunge il valore di
		 * iterazioni impostato dall'utente il ciclo si interrompe
		 */
		while (main.ITERAZIONE_CORRENTE < main.N_ITERAZIONI) {
			/*
			 * Utilizziamo un blocco di istruzioni da eseguire in mutua esclusione,
			 * utilizziamo l'ggetto ProduceMonitor come monitor, questo oggetto è condiviso
			 * tra tutti i Produttori Creati
			 */
			synchronized (main.ConsumerMonitor) {

				if (main.in != main.OUT) {
					help = true;
					int index = main.OUT;
					int value = main.buffer[main.OUT];
					
					main.OUT = (main.OUT + 1) % main.Dimensione_BufferCondiviso;
					
					System.out.println("Consumer " + ID + " Ha consumato: " + value + " Buffer:" + "[" + index + "]");
				} else {
	
					try {
						main.ProducerMonitor.wait();
						System.out.println("Buffer totalmente vuoto!");
					} catch (InterruptedException e) {
					}
				}
				try {
					Thread.sleep(new Random().nextInt(main.MAXIMUM - main.MINIMUM + 1) + main.MINIMUM);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				main.ITERAZIONE_CORRENTE++;
				
				if(help) {
					synchronized(main.ProducerMonitor) {
						main.ConsumerMonitor.notify();

					}
					help = false;
				}
			}
		}

	}
}
