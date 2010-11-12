/**
 * The lexer file of the production scanner.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
package de.unisiegen.gtitool.core.parser.production;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.style.Style;

%%

%class ProductionScanner
%extends AbstractScanner
%implements ProductionTerminals

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
		case MEMBER:
		  return Style.NONTERMINAL_SYMBOL;
		case EPSILON:
		  return Style.TERMINAL_SYMBOL;
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
	"\u03B5"			{ return symbol(EPSILON); }
	"->"|"\u2192"		{ return symbol(ARROW); }
	{WhiteSpace}		{ return symbol(WHITESPACE); }
	"\|"                { return symbol(VDASH); }
	.'*					{ return symbol(MEMBER, yytext()); }
	\"[^\"]+\"			{ return symbol(MEMBER, yytext()); }
}