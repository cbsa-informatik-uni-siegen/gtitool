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
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;

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
	private int yycommentChar = 0;
	
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
		case SYMBOL:
		  return Style.TOKEN;
		case COMMENT:
		  return Style.COMMENT;
		case LANGUAGE:
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

%state YYCOMMENT, YYCOMMENTEOF, YYCOMMENTSINGLE

%%

<YYINITIAL>
{
	"*"							{ return symbol(STAR); }
	"+"							{ return symbol(PLUS); }
	"|"							{ return symbol(OR); }
	"·"							{ return symbol(CONCAT); }
	"Epsilon"|"\u03B5"			{ return symbol(EPSILON); }
	"?"							{ return symbol(QUESTION); }
	"("							{ return symbol(LBRACE); }
	")"							{ return symbol(RBRACE); }
	"["							{ return symbol(SLBRACE); }
	"]"							{ return symbol(SRBRACE); }
	"-"							{ return symbol(MINUS); }
	"'"							{ return symbol(LANGUAGE, "");}
	"(*"						{ yycommentChar = yychar; yybegin(YYCOMMENT); }
	"#"							{ yycommentChar = yychar; yybegin(YYCOMMENTSINGLE); }
	{WhiteSpace}				{ }
	.'*							{ return symbol(SYMBOL, yytext()); }
	\".+\"						{ return symbol(SYMBOL, yytext()); }
}

<YYCOMMENT> 
{
	<<EOF>>				{ yybegin(YYCOMMENTEOF); return symbol(COMMENT, yycommentChar, yychar, yytext()); }
	"*)"				{ yybegin(YYINITIAL); return symbol(COMMENT, yycommentChar, yychar + yylength(), yytext()); }
	.|\n				{ /* Ignore */ }
}

<YYCOMMENTEOF> 
{
	<<EOF>>				{ ParserException.throwCommentException(yycommentChar,yychar); }
}
<YYCOMMENTSINGLE>
{
	<<EOF>>				{ yybegin(YYINITIAL);return symbol(COMMENT, yycommentChar, yychar + yylength(), yytext());}
	\n					{ yybegin(YYINITIAL);return symbol(COMMENT, yycommentChar, yychar + yylength(), yytext());}
	.					{ /* Ignore */ }
}

