package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;

	public ServidorTarefas() throws IOException {
		System.out.println("---- Iniciando Servidor ----");
		this.servidor = new ServerSocket(12345);
//		this.threadPool = Executors.newCachedThreadPool();
	    this.threadPool = Executors.newFixedThreadPool(4, new FabricaDeThreads());
		this.estaRodando = new AtomicBoolean(true);
	}

	public void rodar() throws IOException {

		while (this.estaRodando.get()) {

			Socket socket = this.servidor.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());

			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool,socket, this);

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

}
