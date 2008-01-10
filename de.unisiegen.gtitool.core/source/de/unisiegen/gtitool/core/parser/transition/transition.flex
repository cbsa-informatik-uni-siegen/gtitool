/**
 * The lexer file of the transition scanner.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
package de.unisiegen.gtitool.core.parser.transition;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.style.Style;

%%

%class TransitionScanner
%extends AbstractScanner
%implements TransitionTerminals

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
		case SYMBOL:
		  return Style.SYMBOL;
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

LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
Symbol			= [:jletterdigit:] | \"[:jletterdigit:]+\"

%%

<YYINITIAL>
{
	","					{ return symbol(COMMA); }
	"{"					{ return symbol(LCBRACE); }
	"}"					{ return symbol(RCBRACE); }
	"\u03B5"			{ return symbol(EPSILON); }
	{Symbol}			{ return symbol(SYMBOL, yytext()); }
	{WhiteSpace}		{ }
}

.|\n					{ throw new ScannerException(yychar, yychar + yylength(), Messages.getString ( "Parser.1", yytext() ) ); }