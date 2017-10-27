import java.io.IOException;

/*
 * Legenda da Gramatica:
 * 1. [] → opcional
 * 2. {} → obrigatorio entrar
 * 3. * → repita enquanto
 * 4. + → repita até
 * 5.{}* → 0 ou mais
 * 6.{}+ → 1 ou mais
 * 
 * Regras
 * 7. S → simbolo inicial
 * 8. D → declaracao
 * 9. C → comandos
 * 10.
 * Gramatica da Linguagem L
 * 
 * S → {D}+ main {C}+ end
 * D → DV|DC
 * C → CA|CT|CR|CL|CE|CN
 * TIPO → (byte|boolean|integer|string)
 * DV → TIPO id [ = [+ | - ] const ] {, id [ = [+ | - ] const ] }* ;
 * DC → const id = const;
 * CA → id = EXP;
 * CT → if “(” EXP “)” then ( C [ else C ] | begin C+ end [ else begin C+ end] )
 * CR → while “(“ EXP “)” ( C | begin C+ end)
 * CL → readln “(“ id “)” ;
 * CE → (write|writeln) “(” EXP {, EXP }* “)” ;
 * CN → ;
 * EXP → EXPS1  [ ( <| > | ≤ | ≥ | != | == ) EXPS2 ] 
 * EXPS → [ + | - ] T1 { (+ | - | or) T2 }*
 * T → F1 { (* |/ | and) F2 }*
 * F → not F1 | const | id | “(” EXP “)” 
 * 
 * 
 */

public class AnalisadorSintatico 
{
	public static RegLex tk_atual;
	
	public static void casaToken(byte tk_esperado) throws IOException
	{
		//System.out.println("Atual: ["+tk_atual.getLexema()+", "+tk_atual.getToken()+"] Esperando:["+tk_esperado+"]");
		if(tk_atual != null)
		{
			if (tk_atual.getToken() == tk_esperado)
			{
				tk_atual = AnalisadorLexico.analisar();
				

			}
			else
			{
				GerenciadorErros.exibirErro(AnalisadorLexico.linha, GerenciadorErros.ERR_TOKEN_NAO_ESPERADO, tk_atual.getLexema());
			}
		}
		else
		{
			GerenciadorErros.exibirErro(AnalisadorLexico.linha, GerenciadorErros.ERR_FIM_ARQ_INESPERADO, tk_atual.getLexema());
		}
	}
	
	//S → {D}+ main {C}+ end
	public static void S() throws IOException
	{
		tk_atual = AnalisadorLexico.analisar(); // chama o analisador lexico para pegar um token
		//System.out.println(" "+tk_atual.getLexema()+" "+tk_atual.getToken());
		
		if (tk_atual == null)
		{
			GerenciadorErros.exibirErro(AnalisadorLexico.linha, 
						GerenciadorErros.ERR_FIM_ARQ_INESPERADO, tk_atual.getLexema());
		}
		
		do 
		{
			
			D();
			
		}while((tk_atual.getToken() == TabelaSimbolos.BYTE) 
					|| (tk_atual.getToken() == TabelaSimbolos.BOOLEAN)
						|| (tk_atual.getToken() == TabelaSimbolos.INTEGER) 
							|| (tk_atual.getToken() == TabelaSimbolos.STRING)
								|| (tk_atual.getToken() == TabelaSimbolos.CONST));
		
		
		casaToken(TabelaSimbolos.MAIN);
		
		do
		{
			C();
			
		}while(tk_atual.getToken() == TabelaSimbolos.ID 
					|| tk_atual.getLexema().equals("while")
						|| tk_atual.getLexema().equals("if") 
							|| tk_atual.getLexema().equals("readln")
								|| tk_atual.getLexema().equals(";") 
									|| tk_atual.getLexema().equals("write")
										|| tk_atual.getLexema().equals("writeln"));
		
		casaToken(TabelaSimbolos.END);
	}
	
	//D → DV|DC
	public static void D() throws IOException
	{
		if ((tk_atual.getToken() == TabelaSimbolos.BYTE) 
				|| (tk_atual.getToken() == TabelaSimbolos.BOOLEAN)
					|| (tk_atual.getToken() == TabelaSimbolos.INTEGER) 
						|| (tk_atual.getToken() == TabelaSimbolos.STRING))
		{
			DV();
		}
		else
		{
			DC();
		}
	}
	
	//TIPO → (byte|boolean|integer|string)
	public static int TIPO() throws IOException
	{
		int tipo = TabelaSimbolos.TIPO_VAZIO;
		
		if (tk_atual.getToken() == TabelaSimbolos.BYTE)
		{
			casaToken(TabelaSimbolos.BYTE);
			tipo = TabelaSimbolos.TIPO_BYTE;
		}
		else if (tk_atual.getToken() == TabelaSimbolos.BOOLEAN)
		{
			casaToken(TabelaSimbolos.BOOLEAN);
			tipo = TabelaSimbolos.TIPO_LOGICO;
		}
		else if (tk_atual.getToken() == TabelaSimbolos.INTEGER)
		{
			casaToken(TabelaSimbolos.INTEGER);
			tipo = TabelaSimbolos.TIPO_INTEIRO;
		}
		else
		{
			casaToken(TabelaSimbolos.STRING);
			tipo = TabelaSimbolos.TIPO_STRING;
		}
		
		return tipo;
	}
	
	//DV → TIPO id [ = [+ | - ] const ] {, id [ = [+ | - ] const ] }* ;
	public static void DV() throws IOException
	{
		TIPO(); 
		casaToken(TabelaSimbolos.ID); 
	
		if (tk_atual.getToken() == TabelaSimbolos.ATTEQUALS) 
		{
			casaToken(TabelaSimbolos.ATTEQUALS); 
		
			if (tk_atual.getToken() == TabelaSimbolos.PLUS)
			{
				casaToken(TabelaSimbolos.PLUS);
				
			}
			else if (tk_atual.getToken() == TabelaSimbolos.MINUS)
			{
				casaToken(TabelaSimbolos.MINUS);
			}
			casaToken(TabelaSimbolos.CONST);
		}	
		
		
		while(tk_atual.getToken() == TabelaSimbolos.COMMA) 
		{
			casaToken(TabelaSimbolos.COMMA); 
			casaToken(TabelaSimbolos.ID); 
			
			if (tk_atual.getToken() == TabelaSimbolos.ATTEQUALS) // testa o identificador esta sendo inicializado
			{
				casaToken(TabelaSimbolos.ATTEQUALS); // se sim casa o =
				
				if (tk_atual.getToken() == TabelaSimbolos.PLUS)
				{
					casaToken(TabelaSimbolos.PLUS);
				}
				else if (tk_atual.getToken() == TabelaSimbolos.MINUS)
				{
					casaToken(TabelaSimbolos.MINUS);
				}
				
				casaToken(TabelaSimbolos.CONST);
			}		
		}
		casaToken(TabelaSimbolos.SEMICOLON); 
	}
	
	//DC → const id = EXP;
	public static void DC() throws IOException
	{
		casaToken(TabelaSimbolos.CONST);
		casaToken(TabelaSimbolos.ID);
		casaToken(TabelaSimbolos.ATTEQUALS);
		casaToken(TabelaSimbolos.CONST);
		casaToken(TabelaSimbolos.SEMICOLON);
	}
	
	//C → CA|CT|CR|CL|CE|CN
	public static void C() throws IOException
	{
		if (tk_atual.getToken() == TabelaSimbolos.ID)
		{
			CA(); 
		}
	
		else if (tk_atual.getToken() == TabelaSimbolos.WHILE)
		{
			CR(); 
		}
		
		else if (tk_atual.getToken() == TabelaSimbolos.IF)
		{
			CT(); 
		}

		else if (tk_atual.getToken() == TabelaSimbolos.READLN)
		{
			CL();
		}
	
		else if (tk_atual.getToken() == TabelaSimbolos.WRITE 
				|| tk_atual.getToken() == TabelaSimbolos.WRITELN)
		{
			CE(); 
		}
	
		else
		{
			CN(); 
		}
	}
	
	//CA → id = EXP;
	public static void CA() throws IOException
	{
		casaToken(TabelaSimbolos.ID);
		casaToken(TabelaSimbolos.ATTEQUALS);
		EXP();
		casaToken(TabelaSimbolos.SEMICOLON);
	}
	
	//CT → if “(” EXP “)” then ( C [ else C ] | begin C+ end [ else begin C+ end] )
	public static void CT() throws IOException
	{
		casaToken(TabelaSimbolos.IF);
		casaToken(TabelaSimbolos.OPENPAR);
		EXP();
		casaToken(TabelaSimbolos.CLOSEPAR);
		casaToken(TabelaSimbolos.THEN);
		if (tk_atual.getToken() == TabelaSimbolos.BEGIN)
		{
			
			casaToken(TabelaSimbolos.BEGIN);
			
			do 
			{
				C();
				
			}while(tk_atual.getToken() == TabelaSimbolos.ID 
					|| tk_atual.getLexema().equals("while")
					|| tk_atual.getLexema().equals("if") 
						|| tk_atual.getLexema().equals("readln")
							|| tk_atual.getLexema().equals(";") 
								|| tk_atual.getLexema().equals("write")
									|| tk_atual.getLexema().equals("writeln"));
			
			casaToken(TabelaSimbolos.END);
			
			if (tk_atual.getToken() == TabelaSimbolos.ELSE)
			{
				casaToken(TabelaSimbolos.ELSE);
				casaToken(TabelaSimbolos.BEGIN);
				
				do 
				{
					C();
					
				}while(tk_atual.getToken() == TabelaSimbolos.ID 
						|| tk_atual.getLexema().equals("while")
						|| tk_atual.getLexema().equals("if") 
							|| tk_atual.getLexema().equals("readln")
								|| tk_atual.getLexema().equals(";") 
									|| tk_atual.getLexema().equals("write")
										|| tk_atual.getLexema().equals("writeln"));
				
				casaToken(TabelaSimbolos.END);
			}
		}
		else
		{
			if (tk_atual.getToken() == TabelaSimbolos.ELSE)
			{
				casaToken(TabelaSimbolos.ELSE);
			
			}
			
			C();
		}
	}
	
	//CR → while “(“ EXP “)” ( C | begin C+ end)
	public static void CR() throws IOException
	{
		casaToken(TabelaSimbolos.WHILE);
		casaToken(TabelaSimbolos.OPENPAR);
		EXP();
		casaToken(TabelaSimbolos.CLOSEPAR);
		if (tk_atual.getToken() == TabelaSimbolos.BEGIN)
		{
			casaToken(TabelaSimbolos.BEGIN);
			
			do 
			{
				C();
				
			}while(tk_atual.getToken() == TabelaSimbolos.ID 
					|| tk_atual.getLexema().equals("while")
					|| tk_atual.getLexema().equals("if") 
						|| tk_atual.getLexema().equals("readln")
							|| tk_atual.getLexema().equals(";") 
								|| tk_atual.getLexema().equals("write")
									|| tk_atual.getLexema().equals("writeln"));
			
			casaToken(TabelaSimbolos.END);
		}
		else
		{
			C();
		}
	}
	
	// CL → readln “(“ id “)” ;
	public static void CL() throws IOException
	{
		casaToken(TabelaSimbolos.READLN);
		casaToken(TabelaSimbolos.OPENPAR);
		casaToken(TabelaSimbolos.ID);
		casaToken(TabelaSimbolos.CLOSEPAR);
		casaToken(TabelaSimbolos.SEMICOLON);

	}
	
	//CE → (write|writeln) “(” EXP {, EXP }* “)” ;
	public static void CE() throws IOException
	{
		if (tk_atual.getToken() == TabelaSimbolos.WRITE)
		{
			casaToken(TabelaSimbolos.WRITE);
		}
		else
		{
			casaToken(TabelaSimbolos.WRITELN);
		}
		
		casaToken(TabelaSimbolos.OPENPAR);
		EXP();
		
		while(tk_atual.getToken() == TabelaSimbolos.COMMA)
		{
			casaToken(TabelaSimbolos.COMMA);
			EXP();
		}
		
		casaToken(TabelaSimbolos.CLOSEPAR);
		casaToken(TabelaSimbolos.SEMICOLON);
		
	}
	
	//CN → ;
	public static void CN() throws IOException
	{
		casaToken(TabelaSimbolos.SEMICOLON);
	}
	
	//EXP → EXPS1  [ ( <| > | ≤ | ≥ | != | == ) EXPS2 ] 
	public static void EXP() throws IOException
	{
		EXPS();
		
		if (tk_atual.getToken() == TabelaSimbolos.MAJOR)
		{
			casaToken(TabelaSimbolos.MAJOR);
			EXPS();
		}
		else if (tk_atual.getToken() == TabelaSimbolos.MINOR)
		{
			casaToken(TabelaSimbolos.MINOR);
			EXPS();
		}
		else if (tk_atual.getToken() == TabelaSimbolos.MINOREQUALS)
		{
			casaToken(TabelaSimbolos.MINOREQUALS);
			EXPS();
		}
		else if(tk_atual.getToken() == TabelaSimbolos.MAJOREQUALS)
		{
			casaToken(TabelaSimbolos.MAJOREQUALS);
			EXPS();
		}
		else if(tk_atual.getToken() == TabelaSimbolos.DIFF)
		{
			casaToken(TabelaSimbolos.DIFF);
			EXPS();
		}
		else if(tk_atual.getToken() == TabelaSimbolos.COMPEQUALS)
		{
			casaToken(TabelaSimbolos.COMPEQUALS);
			EXPS();
		}
		
		
	}
	
	//EXPS → [ + | - ] T1 { (+ | - | or) T2 }*
	public static void EXPS() throws IOException
	{
		if (tk_atual.getToken() == TabelaSimbolos.PLUS)
		{
			casaToken(TabelaSimbolos.PLUS);
		}
		else if(tk_atual.getToken() == TabelaSimbolos.MINUS)
		{
			casaToken(TabelaSimbolos.MINUS);
		}
		
		T();
		
		
		while(tk_atual.getToken() == TabelaSimbolos.PLUS 
				||tk_atual.getToken() == TabelaSimbolos.MINUS
					|| tk_atual.getToken() == TabelaSimbolos.OR)
		{
			if (tk_atual.getToken() == TabelaSimbolos.PLUS)
			{
				casaToken(TabelaSimbolos.PLUS);
			}
			else if(tk_atual.getToken() == TabelaSimbolos.MINUS)
			{
				casaToken(TabelaSimbolos.MINUS);
			}
			else
			{
				casaToken(TabelaSimbolos.OR);
			}
			
			T();
		}
	}
	
	//T → F1 {(* |/ | and) F2}*
	public static void T() throws IOException
	{
		F();
		
		while(tk_atual.getToken() == TabelaSimbolos.TIMES
				||tk_atual.getToken() == TabelaSimbolos.DIVIDE
					|| tk_atual.getToken() == TabelaSimbolos.AND)
		{
			if (tk_atual.getToken() == TabelaSimbolos.TIMES)
			{
				casaToken(TabelaSimbolos.TIMES);
			}
			else if(tk_atual.getToken() == TabelaSimbolos.DIVIDE)
			{
				casaToken(TabelaSimbolos.DIVIDE);
			}
			else
			{
				casaToken(TabelaSimbolos.AND);
			}
			
			F();
		}
		
		
	}
	
	//F → not F1 | const | id | “(” EXP “)”
	public static void F() throws IOException
	{
		if (tk_atual.getToken() == TabelaSimbolos.NOT)
		{
			casaToken(TabelaSimbolos.NOT);
			F();
		}
		else if (tk_atual.getToken() == TabelaSimbolos.CONST
				|| tk_atual.getToken() == TabelaSimbolos.TRUE
					|| tk_atual.getToken() == TabelaSimbolos.FALSE
						|| tk_atual.getToken() == TabelaSimbolos.STRING
							|| tk_atual.getToken() == TabelaSimbolos.BOOLEAN)
		{
			
			if (tk_atual.getToken() == TabelaSimbolos.TRUE)
				casaToken(TabelaSimbolos.TRUE);
			else if (tk_atual.getToken() == TabelaSimbolos.FALSE)
				casaToken(TabelaSimbolos.FALSE);
			else if (tk_atual.getToken() == TabelaSimbolos.STRING)
				casaToken(TabelaSimbolos.STRING);
			else if (tk_atual.getToken() == TabelaSimbolos.BOOLEAN)
				casaToken(TabelaSimbolos.BOOLEAN);
			else
				casaToken(TabelaSimbolos.CONST);
			
			
		}
		else if(tk_atual.getToken() == TabelaSimbolos.ID)
		{
			casaToken(TabelaSimbolos.ID);
		}
		else
		{
			casaToken(TabelaSimbolos.OPENPAR);
			EXP();
			casaToken(TabelaSimbolos.CLOSEPAR);
		}
	}
	
}
