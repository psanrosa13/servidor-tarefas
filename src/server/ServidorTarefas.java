package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {
	public static void main(String[] args) throws Exception {
		System.out.println("---- Iniciando Servidor ----");
	    ServerSocket servidor = new ServerSocket(12345);

	    while (true) {
            Socket socket = servidor.accept();    
            System.out.println("Aceitando novo cliente na porta " + socket.getPort());
            
            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            
            new Thread(distribuirTarefas).start();
        }
    }
}
