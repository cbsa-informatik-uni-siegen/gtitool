/**
 * The lexer file of the state set scanner.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
package de.unisiegen.gtitool.core.parser.stateset;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.style.Style;

/**
 * This is the lexer class for a state set.
 */
%%

%class StateSetScanner
%extends AbstractScanner
%implements StateSetTerminals

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
	private Symbol symbol(int id)
	{
	  return symbol(id, yychar, yychar + yylength(), yytext());
	}
	
	private Symbol symbol(int id, Object value)
	{
	  return symbol(id, yychar, yychar + yylength(), value);
	}

	@Override
	public Style getStyleBySymbolId(int id)
	{
	  switch (id)
	  {
		case STATE:
		  return Style.STATE;
		default:
		  return Style.NONE;
	  }
	}
	
	public void restart(String text)
	{
	  if (text == null)
	  {
		throw new NullPointerException("text is null");
	  }
	  yyreset(new StringReader(text));
	}
%}

LineTerminator		= \r|\n|\r\n
WhiteSpace			= {LineTerminator} | [ \t\f]
State				= [:jletter:][:jletterdigit:]*

%%

<YYINITIAL>
{
	","					{ return symbol(COMMA); }
	"{"					{ return symbol(LCBRACE); }
	"}"					{ return symbol(RCBRACE); }
	{State}				{ return symbol(STATE, yytext()); }
	{WhiteSpace}		{ }
}

.|\n					{ throw new ScannerException(yychar, yychar + yylength(), Messages.getString ( "Parser.1", yytext() ) ); }