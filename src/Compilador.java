import java.io.IOException;

public class Compilador 
{

	public static void main(String[] args) throws IOException 
	{
		TabelaSimbolos.inicializarTabela();
		AnalisadorLexico.inicializar(args[0]);
		AnalisadorSintatico.S();
	}

}
