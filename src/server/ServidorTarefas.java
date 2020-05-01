package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	public static void main(String[] args) throws Exception {
		System.out.println("---- Iniciando Servidor ----");
	    ServerSocket servidor = new ServerSocket(12345);

	    //pool de única thread
	    //ExecutorService poolDeThreads = Executors.newSingleThreadExecutor();
	    
	    //caso queira limitar a quantidade de threads
	    //ExecutorService poolDeThreads = Executors.newFixedThreadPool(2); 
	    
	    // caso queira que a criação seja dinâmica e ainda assim use o recurso de reaproveitar as instâncias de threads
	    ExecutorService poolDeThreads = Executors.newCachedThreadPool();
	    
	    while (true) {
            Socket socket = servidor.accept();    
            System.out.println("Aceitando novo cliente na porta " + socket.getPort());
            
            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            
            poolDeThreads.execute(distribuirTarefas);
        }
    }
}
