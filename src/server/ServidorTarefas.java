package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;
	private BlockingQueue<String> filaComandos;

	public ServidorTarefas() throws IOException {
		System.out.println("---- Iniciando Servidor ----");
		this.servidor = new ServerSocket(12345);
//		this.threadPool = Executors.newCachedThreadPool();
		this.threadPool = Executors.newFixedThreadPool(4, new FabricaDeThreads());
		this.estaRodando = new AtomicBoolean(true);
		this.filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}

	public void rodar() throws IOException {

		while (this.estaRodando.get()) {

			Socket socket = this.servidor.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());

			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, filaComandos, socket, this);

			this.threadPool.execute(distribuirTarefas);
		}
	}

	public void parar() throws IOException {
		this.estaRodando.set(false);
		this.threadPool.shutdown();
		this.servidor.close();
	}

	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();

	}

	// pool de única thread
	// ExecutorService poolDeThreads = Executors.newSingleThreadExecutor();

	// caso queira limitar a quantidade de threads
	// ExecutorService poolDeThreads = Executors.newFixedThreadPool(2);

	// caso queira que a criação seja dinâmica e ainda assim use o recurso de
	// reaproveitar as instâncias de threads

	private void iniciarConsumidores() {
		int qtdConsumidores = 2;
		for (int i = 0; i < qtdConsumidores; i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);
		}
	}

}
