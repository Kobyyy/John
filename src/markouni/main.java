package markouni;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {

	public static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	
	public static Object ConsumerMonitor = new Object();
	public static Object ProducerMonitor = new Object();
	
	public static final int Dimensione_BufferCondiviso = 10;
	public static volatile int[] buffer = new int[Dimensione_BufferCondiviso];
		
	public static int MINIMUM = 0;
	public static int MAXIMUM = 0;
	
	public static int N_ITERAZIONI = 0;
	public static int ITERAZIONE_CORRENTE = 0;

	public static String m;
	public static String n;
	
	public static volatile int in = 0;
	public static volatile int OUT = 0;

	/**
	 * Nel main faccio inserire i vari dati necessari per l'esecuzione, controllando
	 * che siano corretti secondo dei parametri impostati
	 * 
	 * Per poi creare i vari produttori e consumatori tramite due cicli for
	 */

	public static void main(String[] args) {

		System.out.println("Inserire il numero di produttori: ");
		try {
			m = keyboard.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Inserire il numero di consumatori: ");
		try {
			n = keyboard.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Numero iterazioni complessive da parte di processi: ");
		try {
			N_ITERAZIONI = Integer.parseInt(keyboard.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		do {
			System.out.println("Tempo di Sleep MASSIMO (da 1 a 4500): ");
			try {
				MAXIMUM = Integer.parseInt(keyboard.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (MAXIMUM > 4500 || MAXIMUM < 1);
		do {
			System.out.println("Tempo di Sleep MINIMO (da 1 a 1000): ");
			try {
				MINIMUM = Integer.parseInt(keyboard.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (MINIMUM < 1 || MINIMUM > 1000);


		while (MINIMUM > MAXIMUM) {
			System.out.println("IL MINIMO DEVE ESSERE MINORE DEL MASSIMO!");
			System.out.println("Tempo di Sleep MASSIMO (da 1 a 4500): ");
			try {
				MAXIMUM = Integer.parseInt(keyboard.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Tempo di Sleep MINIMO (da 1 a 1000): ");
			try {
				MINIMUM = Integer.parseInt(keyboard.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// Creazione di n e m Produttori e Consumatori per poi far partire il 
		// Thread tramite il metodo start()
		for (int i = 0; i < Integer.parseInt(m); i++) {
			new Producer(i + 1).start();
		}

		for (int j = 0; j < Integer.parseInt(n); j++) {
			new Consumer(j + 1).start();
		}

	}

}
