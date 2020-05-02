package server;

import java.io.PrintStream;

public class ComandoC2 implements Runnable {

	private PrintStream saida;

    public ComandoC2(PrintStream saida) {
        this.saida = saida;
    }

    @Override
    public void run() {

        System.out.println("Executando comando c2");

        try {
            // faz algo bem demorado
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //devolvendo resposta para o cliente
        saida.println("Comando c2 executado com sucesso!");
        
        throw new RuntimeException("Exception no comando c2");
    }

}
