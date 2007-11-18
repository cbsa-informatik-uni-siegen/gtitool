/**
 * The lexer file of the state scanner.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
package de.unisiegen.gtitool.core.parser.state;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.style.Style;

/**
 * This is the lexer class for a state.
 */
%%

%class StateScanner
%extends AbstractScanner
%implements StateTerminals

%function nextSymbol
%type Symbol
%yylexthrow ScannerException
%eofclose
%eofval{
	return null;
%eofval}

%unicode
%line
%column
%char

%{
	private Symbol symbol(int pId)
	{
	  return symbol(pId, yychar, yychar + yylength(), yytext());
	}
	
	private Symbol symbol(int pId, Object pValue)
	{
	  return symbol(pId, yychar, yychar + yylength(), pValue);
	}

	@Override
	public Style getStyleBySymbolId(int pId)
	{
	  switch (pId)
	  {
		case STATE:
		  return Style.STATE;
		default:
		  return Style.NONE;
	  }
	}
	
	public void restart(String pText)
	{
	  if (pText == null)
	  {
		throw new NullPointerException("text is null");
	  }
	  yyreset(new StringReader(pText));
	}
%}

LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
State			= [a-zA-Z0-9]+

%%

<YYINITIAL>
{
	{State}				{ return symbol(STATE, yytext()); }
	{WhiteSpace}		{ }
}

.|\n					{ throw new ScannerException(yychar, yychar + yylength(), Messages.getString ( "Parser.1", yytext() ) ); }