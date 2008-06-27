package de.unisiegen.gtitool.core.parser.startnonterminalsymbol;


import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The start {@link NonterminalSymbol} parser class.
 * 
 * @author Christian Fehler
 * @version $Id: StartNonterminalSymbolParser.java 1032 2008-06-25 18:53:30Z
 *          fehler $
 */
public final class StartNonterminalSymbolParser extends
    StartNonterminalSymbolAbstractParser
{

  /**
   * Allocates a new {@link StartNonterminalSymbolParser}.
   * 
   * @param gtiScanner The {@link GTIScanner}.
   */
  public StartNonterminalSymbolParser ( GTIScanner gtiScanner )
  {
    super ( gtiScanner );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#report_error(java.lang.String,
   *      java.lang.Object)
   */
  @Override
  public void report_error ( String message, Object info )
  {
    Symbol symbol = ( Symbol ) info;
    String messageText = message;
    if ( symbol.sym == EOF_sym () )
    {
      messageText = Messages.getString ( "Parser.0" ); //$NON-NLS-1$
    }
    throw new ParserException ( symbol.left, symbol.right, messageText );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#report_fatal_error(java.lang.String,
   *      java.lang.Object)
   */
  @Override
  public void report_fatal_error ( String message, Object info )
  {
    report_error ( message, info );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#syntax_error(java_cup.runtime.Symbol)
   */
  @Override
  public void syntax_error ( Symbol symbol )
  {
    report_error ( Messages.getString ( "Parser.1", symbol.value ), symbol ); //$NON-NLS-1$
  }
}
