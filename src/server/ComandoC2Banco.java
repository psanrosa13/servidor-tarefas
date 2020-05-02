package server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2Banco implements Callable<String> {

	private PrintStream saida;

    public ComandoC2Banco(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public String call() throws Exception {

    	System.out.println("Servidor recebeu comando c2 - BANCO");

        saida.println("Processando comando c2 - BANCO");

        Thread.sleep(15000);

        int numero = new Random().nextInt(100) + 1;

        System.out.println("Servidor finalizou comando c2 - BANCO");

        return Integer.toString(numero);
    }

}
