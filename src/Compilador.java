
public class Compilador 
{

	public static void main(String[] args) 
	{
		TabelaSimbolos.inicializarTabela();
		AnalisadorLexico.inicializar(args[0]);
		TabelaSimbolos.mostrarTabela();
	}

}
