package de.unisiegen.gtitool.core.parser.alphabet;

import java.io.Reader;
import java.text.MessageFormat;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.parser.AbstractScanner;
import de.unisiegen.gtitool.core.parser.ParserSymbol;
import de.unisiegen.gtitool.core.parser.PrettyStyle;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;

/**
 * This is the lexer class for an alphabet.
 */
%%

%class AlphabetScanner
%extends AbstractScanner
%implements AlphabetTerminals

%function nextSymbol
%type ParserSymbol
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
	private ParserSymbol symbol(String pName, int pId)
	{
	  return symbol(pName, pId, yychar, yychar + yylength(), yytext());
	}
	
	private ParserSymbol symbol(String pName, int pId, Object pValue)
	{
	  return symbol(pName, pId, yychar, yychar + yylength(), pValue);
	}

	@Override
	public PrettyStyle getStyleBySymbolId(int pId)
	{
	  switch (pId)
	  {
		case SYMBOL:
		  return PrettyStyle.SYMBOL;
		default:
		  return PrettyStyle.NONE;
	  }
	}
	
	public void restart(Reader pReader)
	{
	  if (pReader == null)
	  {
		throw new NullPointerException("Reader is null");
	  }
	  yyreset(pReader);
	}
%}

LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
Symbol			= [a-zA-Z] [a-zA-Z0-9]*

%%

<YYINITIAL>
{
	","					{ return symbol("COMMA", COMMA); }
	"{"					{ return symbol("LCBRACE", LCBRACE); }
	"}"					{ return symbol("RCBRACE", RCBRACE); }
	{Symbol}			{ return symbol("SYMBOL", SYMBOL, yytext()); }
	{WhiteSpace}		{ /* Ignore */ }
}

.|\n					{ throw new ScannerException(yychar, yychar + yylength(), MessageFormat.format ( Messages.getString ( "Parser.1" ), yytext() ) ); }