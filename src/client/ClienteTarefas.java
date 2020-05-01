package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
        System.out.println("Conexão Estabelecida");

        PrintStream saida = new PrintStream(socket.getOutputStream());
        saida.println("print");

        Scanner teclado = new Scanner(System.in);
        teclado.nextLine();

        saida.close();
        teclado.close();
        socket.close();
    }
}
