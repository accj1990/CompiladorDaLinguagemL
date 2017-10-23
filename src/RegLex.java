
public class RegLex 
{
	private String lexema;
	private byte token;
	private int tipo;
	private int classe;
	private int endereco;
	
	public RegLex()
	{
		 this.lexema = "";
	     this.token = -1;
	     this.tipo = -1;
	     this.classe = -1;
	     this.endereco = -1;
	}
	
	public RegLex(String lexema, byte token)
	{
		 this.lexema = lexema;
	     this.token = token;
	     this.tipo = -1;
	     this.classe = -1;
	     this.endereco = -1;
	}
	
	public RegLex(String lexema, byte token, int tipo, int classe, int endereco)
	{
		 this.lexema = lexema;
	     this.token = token;
	     this.setTipo(tipo);
	     this.setClasse(classe);
	     this.setEndereco(endereco);
	}
	
	public String getLexema() 
	{
		return lexema;
	}
	public void setLexema(String lexema) 
	{
		this.lexema = lexema;
	}
	public byte getToken() 
	{
		return token;
	}
	public void setToken(byte token) 
	{
		this.token = token;
	}
	public int getTipo()
	{
		return tipo;
	}
	
	public void setTipo(int tipo) 
	{
		this.tipo = tipo;
	}
	
	public int getClasse() 
	{
		return classe;
	}
	
	public void setClasse(int classe) 
	{
		this.classe = classe;
	}
	
	public int getEndereco() 
	{
		return endereco;
	}
	
	public void setEndereco(int endereco) 
	{
		this.endereco = endereco;
	}
}
