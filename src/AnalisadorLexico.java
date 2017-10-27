import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico 
{

	public static BufferedReader in ;
	public static String nomeArq = "";
	public static int linha = 1;
	public static char caractere = ' ';
	public static boolean limpar = false;
	private static String lexema;
	
	public static void inicializar(String nomeArquivo)
	{
		try 
		{
			nomeArq = new File(nomeArquivo).getName();
			in = new BufferedReader(new FileReader(nomeArquivo));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	public static RegLex analisar() throws IOException
	{
		RegLex rg = null;
		int estado = 0;
		int inteiro = 0;
		lexema = "";
		while(estado != 17) // aceitacao
		{
			//System.out.println(estado+" "+lexema);
			if (!limpar)
			{		
				inteiro = in.read();
				
				if (inteiro != -1 && estado != 17)
				{
					if (isCaractereValido(caractere))
					{	caractere = (char) inteiro;
						if (caractere == '\n')
						{
							linha++;
						}
					}
					else
					{
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_CARACTERE_INVALIDO, lexema);
					}
				}
				else
				{
					return rg;
				}
				
			}
			else
			{
				lexema = "";
				limpar = false;
			}

			switch(estado)
			{
			case 0:
				
				if(caractere == ' ' || caractere == '\r' 
						|| caractere == '\n' || caractere == '\t')
				{
					estado = 0;
				}
				
				else if (caractere == '/')
				{
					lexema+=caractere;
					estado = 1;
				}
				else if(caractere == '\'')
				{
					lexema+=caractere;
					estado = 4;
				}
				else if (caractere == '0')
				{
					lexema+=caractere;
					estado = 9;
				}
				else if (caractere == '=')
				{
					lexema+= caractere;
					estado = 15;
				}
				else if (caractere == '>')
				{
					lexema+= caractere;
					estado = 14;
				}
				else if(caractere == '!')
				{
					lexema+= caractere;
					estado = 16;
				}
				else if (caractere == '(' ||
							caractere == ')' ||
								caractere == '+'||
									caractere == '-' ||
										caractere == '*' ||
											caractere == ',' ||
												caractere == ';')
				{
					lexema +=caractere;
					estado = 17; // estado final
					
					if (caractere == '(')
					{
						rg = new RegLex(lexema, TabelaSimbolos.OPENPAR,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else if (caractere == ')')
					{
						rg = new RegLex(lexema, TabelaSimbolos.CLOSEPAR,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);


					}
					else if (caractere == '+')
					{
						rg = new RegLex(lexema, TabelaSimbolos.PLUS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else if (caractere == '-')
					{
						rg = new RegLex(lexema, TabelaSimbolos.MINUS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else if (caractere == '*')
					{
						rg = new RegLex(lexema, TabelaSimbolos.TIMES,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);


					}
					else if (caractere == ',')
					{
						rg = new RegLex(lexema, TabelaSimbolos.COMMA,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else
					{
						rg = new RegLex(lexema, TabelaSimbolos.SEMICOLON,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
					}
				}
				else if (caractere == '<')
				{
					lexema+= caractere;
					estado = 13;
				}
				else if ((""+caractere).matches("[1-9]+"))
				{
					lexema+= caractere;
					estado = 12;
				}	
				else if (caractere == '_')
				{
					lexema+=caractere;
					estado = 7;
				}
				else if ((""+caractere).matches("[a-zA-Z]+"))
				{
					lexema+=caractere;
					estado = 6;
				}
			break;	
				
				case 1:
					if (caractere == '*')
					{
						lexema+=caractere;
						estado = 2;
					}
				break;
				
				case 2:
					if (caractere == '*')
					{
						lexema+=caractere;
						estado = 3;
					}
					else if(caractere != '*')
					{
						lexema+= caractere;
						estado = 2;
					}
				break;
				
				case 3:
					
					if(caractere == '*')
					{
						lexema+=caractere;
						estado = 3;
					}
					
					else if (caractere != '/' && caractere != '*')
					{
						lexema+=caractere;
						estado = 2;
					}
					else if (caractere == '/')
					{
						lexema="";
						estado = 0;						
					}
				break;
				
				case 4:
					if (isCaractereValido(caractere) && caractere != '\'' && caractere != '\n')
					{
						lexema+= caractere;
						estado = 4;
					}
					else if (caractere == '\'')
					{
						lexema+= caractere;
						estado = 5;
					}
					else
					{
						lexema+= caractere;
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_LEXEMA_NAO_IDENTIFICADO, lexema);
					}
				break;
				
				case 5:
					if (caractere == '\'')
					{
						lexema+= caractere;
						estado = 4;
					}
					else if (caractere != '\'')
					{
						limpar = true;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_STRING,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
				break;
				
				case 6:
					if((""+caractere).matches("[a-zA-Z]+") ||
								(""+caractere).matches("[0-9]+") ||
									caractere == '_')
					{
					
						lexema+=caractere;
						estado = 6;
					}
					else
					{
						byte token = TabelaSimbolos.getToken(lexema);
						if (token != -1)
						{
							int tipo = TabelaSimbolos.TIPO_LOGICO;
							if (lexema.equals("true") || lexema.equals("false"))
							{
								rg = new RegLex(lexema, TabelaSimbolos.CONST ,tipo,
													TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							}
							else
							{
								rg = new RegLex(lexema, token,TabelaSimbolos.TIPO_VAZIO,
													TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							}
							
						}
						else
						{
							rg = new RegLex(lexema, TabelaSimbolos.ID,TabelaSimbolos.TIPO_VAZIO,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							
							TabelaSimbolos.inserirRegTabela(lexema, TabelaSimbolos.ID,TabelaSimbolos.TIPO_VAZIO,
										TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

						}
						
						limpar = true;
						estado = 17;
					}
				break;
				
				case 7:
					if (caractere == '_')
					{
						lexema+= caractere;
						estado = 7;
					}
					else if ((""+caractere).matches("[a-zA-Z]+") ||
								(""+caractere).matches("[0-9]+"))
					{
						lexema+= caractere;
						estado = 8;
					}
					else
					{
						lexema+= caractere;
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_LEXEMA_NAO_IDENTIFICADO, lexema);
					}
				break;	
				
				case 8:
					if ((""+caractere).matches("[a-zA-Z]+") ||
							(""+caractere).matches("[0-9]+") ||
								caractere == '_')
					{
				
						lexema+=caractere;
						estado = 8;
						
					}
					else
					{
						byte token = TabelaSimbolos.getToken(lexema);
						if (token != -1)
						{
							rg = new RegLex(lexema, token,TabelaSimbolos.TIPO_VAZIO,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

						}
						else
						{
							rg = new RegLex(lexema, TabelaSimbolos.ID,TabelaSimbolos.TIPO_VAZIO,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							
							TabelaSimbolos.inserirRegTabela(lexema, TabelaSimbolos.ID,TabelaSimbolos.TIPO_VAZIO,
																TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							
						}
						limpar = true;
						estado = 17;
					}
				break;
				
				case 9:
					if (caractere == 'h')
					{
						lexema+= caractere;
						estado = 10;
					}
					else
					{
						rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_BYTE,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
						limpar = true;
						estado = 17;
					}
					
				break;
				
				case 10:
					if ((""+caractere).matches("[A-F]+") ||
							(""+caractere).matches("[0-9]+"))
					{
						lexema+= caractere;
						estado = 11;
					}
					else
					{
						lexema+= caractere;
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_LEXEMA_NAO_IDENTIFICADO, lexema);
					}
				break;
				
				case 11:
					if ((""+caractere).matches("[A-F]+") ||
							(""+caractere).matches("[0-9]+"))
					{
						lexema+= caractere;
						estado = 17;
						
						String x = (""+lexema.charAt(2)+lexema.charAt(3));
						int valor = Integer.parseInt(x,16);
						if (valor >= 0 && valor <= 255)
						{
							rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_BYTE,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
						}
						else
						{
							if (valor >= -32768 && valor <= 32767)
							{
							    rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_INTEIRO,
							    					TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
							}
							else
							{
								System.out.println("["+linha+"]: constante fora dos limites permitidos <-32768 >= valor <= 32767> ["+lexema+"]");
								System.exit(0);
							}
						}
					}
					else
					{
						lexema+= caractere;
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_LEXEMA_NAO_IDENTIFICADO, lexema);
					}
				break;
				
				case 12:
					if ((""+caractere).matches("[0-9]+"))
					{
						lexema+= caractere;
						estado = 12;
					}
					else
					{
						int valor = Integer.parseInt(lexema,16);
						if (valor >= 0 && valor <= 255)
						{
							rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_BYTE,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
						}
						else if (valor >= -32768 && valor <= 32767)
						{
							rg = new RegLex(lexema, TabelaSimbolos.CONST,TabelaSimbolos.TIPO_INTEIRO,
												TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
						}
						else
						{
							
							System.out.println("["+linha+"]: constante fora dos limites permitidos <-32768 >= valor <= 32767> ["+lexema+"]");
							System.exit(0);
						}
						
						limpar = true;
						estado = 17;
					}
				break;
				
				case 13:
					if (caractere == '=')
					{
						lexema+=caractere;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.MINOREQUALS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else
					{
						rg = new RegLex(lexema, TabelaSimbolos.MINOR,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

						limpar = true;
						estado = 17;
					}
				break;
				
				case 14:
					if (caractere == '=')
					{
						lexema+=caractere;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.MAJOREQUALS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else
					{
						limpar = true;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.MAJOR,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
					}
				break;
				
				case 15:
					if (caractere == '=')
					{
						lexema+=caractere;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.COMPEQUALS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
					else
					{
						limpar = true;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.ATTEQUALS,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);

					}
				break;
				
				case 16:
					if (caractere == '=')
					{
						lexema+=caractere;
						estado = 17;
						rg = new RegLex(lexema, TabelaSimbolos.DIFF,TabelaSimbolos.TIPO_VAZIO,
											TabelaSimbolos.CLASSE_VAZIA,TabelaSimbolos.END_VAZIO);
					}
					else
					{
						// token nao identificado
						GerenciadorErros.exibirErro(linha, GerenciadorErros.ERR_LEXEMA_NAO_IDENTIFICADO, lexema);
					}
				break;	
				
				case 17:
					// estado de aceitacao
				break;
			}
		}
		
		return rg;
	}
	
	public static boolean isCaractereValido(char c)
	{
		return( (""+c).matches("[a-zA-Z\\s]+") || (""+c).matches("[0-9]+") || c == ' ' 
							|| c == '_' || c == '.' || c == ',' || c == ';' || c == '&' || c == ':'
								|| c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}'
									|| c == '+' || c == '-' || c == '\'' || c == '"' || c == '/'|| c == '\\' || c == '?'
										|| c == '!' || c == '>' || c == '<' || c == '=' || c == '*' || c == '\n')? true: false;
	}
	
}
