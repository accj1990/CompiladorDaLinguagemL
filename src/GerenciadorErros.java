
public class GerenciadorErros 
{
	// erro sintatico
	public static final String ERR_TOKEN_NAO_ESPERADO = "token nao esperado";
	
	// erro lexico
	public static final String ERR_FIM_ARQ_INESPERADO = "fim de arquivo nao esperado";
	public static final String ERR_LEXEMA_NAO_IDENTIFICADO = "lexema nao identificado";
	public static final String ERR_CARACTERE_INVALIDO = "caractere invalido";

	// erros semanticos
	public static final String ERR_TIPOS_INCOMPATIVEIS = "tipos incompativeis";
	public static final String ERR_ID_NAO_DECLARADO = "identificador nao declarado";
	public static final String ERR_CLASSE_INCOMPATIVEL = "classe de identificador incompativel";
	public static final String ERR_ID_JA_DECLARADO = "identificador ja declarado";
	
	public static void exibirErro(int linha, String erro, String lexema)
	{
		System.err.println("["+linha+"]: "+erro+". ["+lexema+"]");
		System.exit(0);
	}
	
}
