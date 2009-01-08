/**
 * The lexer file of the transition scanner.
 * 
 * @author Christian Fehler
 * @version $Id: transition.flex 1277 2008-08-20 07:43:44Z fehler $
 */
package de.unisiegen.gtitool.core.parser.transition;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;
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
	    case EPSILON:
		case SYMBOL:
		  return Style.SYMBOL;
		case ARROWUP:
		case ARROWDOWN:
		  return Style.KEYWORD;
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

LineTerminator			= \r|\n|\r\n
WhiteSpace				= {LineTerminator} | [ \t\f]

%%

<YYINITIAL>
{
	","					{ return symbol(COMMA); }
	"{"					{ return symbol(LCBRACE); }
	"}"					{ return symbol(RCBRACE); }
	"["					{ return symbol(LSBRACE); }
	"]"					{ return symbol(RSBRACE); }
	"-"					{ return symbol(MINUS); }
	"\u03B5"			{ return symbol(EPSILON); }
	"\u2191"			{ return symbol(ARROWUP); }
	"\u2193"			{ return symbol(ARROWDOWN); }
	{WhiteSpace}		{ }
	.'*					{ return symbol(SYMBOL, yytext()); }
	\".+\"				{ return symbol(SYMBOL, yytext()); }
}