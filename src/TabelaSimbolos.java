import java.util.HashMap;

public class TabelaSimbolos 
{
	 	public static final byte CONST = 0;
	    public static final byte INTEGER = 1;
	    public static final byte BYTE = 2;
	    public static final byte STRING = 3;
	    public static final byte WHILE = 4;
	    public static final byte IF = 5;
	    public static final byte ELSE = 6;
	    public static final byte AND = 7;
	    public static final byte OR = 8;
	    public static final byte NOT = 9;
	    public static final byte ATTEQUALS = 10;
	    public static final byte COMPEQUALS = 11;
	    public static final byte OPENPAR = 12;
	    public static final byte CLOSEPAR = 13;
	    public static final byte MINOR = 14;
	    public static final byte MAJOR = 15;
	    public static final byte DIFF = 16;
	    public static final byte MAJOREQUALS = 17;
	    public static final byte MINOREQUALS = 18;
	    public static final byte COMMA = 19;
	    public static final byte PLUS = 20;
	    public static final byte MINUS = 21;
	    public static final byte TIMES = 22;
	    public static final byte DIVIDE = 23;
	    public static final byte SEMICOLON = 24;
	    public static final byte BEGIN = 25;
	    public static final byte END = 26;
	    public static final byte THEN = 27;
	    public static final byte READLN = 28;
	    public static final byte MAIN = 29;
	    public static final byte WRITE = 30;
	    public static final byte WRITELN = 31;
	    public static final byte TRUE = 32;
	    public static final byte FALSE = 33;
	    public static final byte BOOLEAN = 34;
	    public static final byte NUMBER = 35;
	    public static final byte ID = 36;
	    public static final byte HEX = 37;
	    
	    // tipos
	    public static final int TIPO_INTEIRO = 0;
	    public static final int TIPO_LOGICO = 1;
	    public static final int TIPO_BYTE = 2;
	    public static final int TIPO_STRING = 3;
	    public static final int TIPO_VAZIO = -1;

	    // classes
	    public static final int CLASSE_VAR = 4;
	    public static final int CLASSE_CONST = 5;
	    public static final int CLASSE_VAZIA = -1;

	    // endereco vazio
	    public static final int END_VAZIO = -1;
	    
	    public static HashMap <String, RegLex> tabela;
	    
	    public static void inicializarTabela()
	    {
	        tabela = new HashMap<String, RegLex>();
	        inserirTokens();
	    }
	    
	    public static void inserirRegTabela(String lexema, byte token, int tipo, int classe, int endereco)
	    {
	    	RegLex reg = new RegLex(lexema, token, tipo, classe, endereco);
	        tabela.put(lexema, reg);
	    }
	    
	    public static void inserirTokens()
	    {
	    	inserirRegTabela("const", CONST,-1,-1,-1);
	    	inserirRegTabela("integer", INTEGER,-1,-1,-1);
	    	inserirRegTabela("byte", BYTE,-1,-1,-1);
	    	inserirRegTabela("string", STRING,-1,-1,-1);
	    	inserirRegTabela("while", WHILE,-1,-1,-1);
	    	inserirRegTabela("if", IF,-1,-1,-1);
	    	inserirRegTabela("else", ELSE,-1,-1,-1);
	    	inserirRegTabela("and", AND,-1,-1,-1);
	    	inserirRegTabela("or", OR,-1,-1,-1);
	    	inserirRegTabela("not", NOT,-1,-1,-1);
	    	inserirRegTabela("=", ATTEQUALS,-1,-1,-1);
	    	inserirRegTabela("==", COMPEQUALS,-1,-1,-1);
	    	inserirRegTabela("(", OPENPAR,-1,-1,-1);
	    	inserirRegTabela(")", CLOSEPAR,-1,-1,-1);
	    	inserirRegTabela("<", MINOR,-1,-1,-1);
	    	inserirRegTabela(">", MAJOR,-1,-1,-1);
	    	inserirRegTabela("!=", DIFF,-1,-1,-1);
	    	inserirRegTabela(">=", MAJOREQUALS,-1,-1,-1);
	    	inserirRegTabela("<=", MINOREQUALS,-1,-1,-1);
	    	inserirRegTabela(",", COMMA,-1,-1,-1);
	    	inserirRegTabela("+", PLUS,-1,-1,-1);
	    	inserirRegTabela("-", MINUS,-1,-1,-1);
	    	inserirRegTabela("*", TIMES,-1,-1,-1);
	    	inserirRegTabela("/", DIVIDE,-1,-1,-1);
	        inserirRegTabela(";", SEMICOLON,-1,-1,-1);
	        inserirRegTabela("begin", BEGIN,-1,-1,-1);
	        inserirRegTabela("end", END,-1,-1,-1);
	        inserirRegTabela("then", THEN,-1,-1,-1);
	        inserirRegTabela("readln", READLN,-1,-1,-1);
	        inserirRegTabela("main", MAIN,-1,-1,-1);
	        inserirRegTabela("write", WRITE,-1,-1,-1);
	        inserirRegTabela("writeln", WRITELN,-1,-1,-1);
	        inserirRegTabela("true", TRUE,-1,-1,-1);
	        inserirRegTabela("false", FALSE,-1,-1,-1);
	        inserirRegTabela("boolean", BOOLEAN,-1,-1,-1);
	    }
	    
	    public static byte getToken(String lexema)
	    {
	    	RegLex reg = tabela.get(lexema);
	        byte token = -1;
	        if (reg!= null)
	        	token = reg.getToken();
	        return token;
	    }
	    
	    public static RegLex getSimbolo(String lexema){
	    	RegLex reg = tabela.get(lexema);
	        return reg; 
	    }
	      
	    public static void setClasse(String lexema, int classe)
	    {
	    	RegLex reg = tabela.get(lexema);
	        if (reg!= null)
	        	tabela.get(lexema).setClasse(classe);
	    }
	    
	    public static void setTipo(String lexema, int tipo)
	    {
	    	RegLex reg = tabela.get(lexema);
	        if (reg!= null)
	        	tabela.get(lexema).setTipo(tipo);
	    }
	    
	    public static void setEndereco(String lexema, int endereco)
	    {
	        RegLex reg = tabela.get(lexema); 
	        if (reg!= null)
	        	tabela.get(lexema).setEndereco(endereco);
	    }
	    
	    public static void mostrarTabela()
	    {
	    	for (String e: tabela.keySet())
	    	{
	            System.out.println("lexema = ["+e+"] , token = ["+tabela.get(e).getToken()+"], tipo = ["+tabela.get(e).getTipo()+"]");
	    	}
	    }
}
