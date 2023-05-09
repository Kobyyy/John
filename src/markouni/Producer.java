package markouni;

import java.util.Random;

public class Producer extends Thread {
	private int ID;
	private boolean help = false;

	public Producer(int ID) {
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
			synchronized (main.ProducerMonitor) {
				if ((main.in + 1) % main.Dimensione_BufferCondiviso != main.OUT) {
					help = true;
					
					int index = main.in;
					int value = 1 + new Random().nextInt(454);
					main.buffer[main.in] = value;
					main.in = (main.in + 1) % main.Dimensione_BufferCondiviso;
					
					System.out.println("Producer " + ID + " Ha prodotto: " + value + " Buffer:" + "[" + index + "]");
				} else {
					try {
						System.out.println("Buffer totalmente pieno!");
						main.ConsumerMonitor.wait();
					} catch (InterruptedException e) {
					}
				}
				try {
					Thread.sleep(new Random().nextInt(main.MAXIMUM - main.MINIMUM + 1) + main.MINIMUM);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				main.ITERAZIONE_CORRENTE++;
				
				try {
					main.ProducerMonitor.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(help) {
				synchronized(main.ConsumerMonitor) {
					main.ConsumerMonitor.notify();
				}
				help = false;
				
			}
		}

	}

}
