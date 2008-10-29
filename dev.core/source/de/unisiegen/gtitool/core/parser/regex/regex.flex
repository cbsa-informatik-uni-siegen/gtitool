/**
 * The lexer file of the regex scanner.
 * 
 * @author Simon Meurer
 */
package de.unisiegen.gtitool.core.parser.regex;

import java.io.StringReader;
import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.style.Style;

%%

%class RegexScanner
%extends AbstractScanner
%implements RegexTerminals

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
	  	case STAR:
	  	case PLUS:
	  	case OR:
	  	case CONCAT:
	  	case EPSILON:
	  	case QUESTION:
	  	case LBRACE:
	  	case RBRACE:
	  	case SLBRACE:
	  	case SRBRACE:
	  	case MINUS:
	  	  return Style.REGEX_SYMBOL;
		case IDENT:
		  return Style.TOKEN;
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
Ident					= [:jletterdigit:]

%%

<YYINITIAL>
{
	"*"					{ return symbol(STAR); }
	"+"					{ return symbol(PLUS); }
	"|"					{ return symbol(OR); }
	"·"					{ return symbol(CONCAT); }
	"Epsilon"			{ return symbol(EPSILON); }
	"?"					{ return symbol(QUESTION); }
	"("					{ return symbol(LBRACE); }
	")"					{ return symbol(RBRACE); }
	"["					{ return symbol(SLBRACE); }
	"]"					{ return symbol(SRBRACE); }
	"-"					{ return symbol(MINUS); }
	{Ident}				{ return symbol(IDENT, yytext()); }
	{WhiteSpace}		{ }
}