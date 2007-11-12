package de.unisiegen.gtitool.core.parser.alphabet;


import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.ScannerInterface;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;


/**
 * The {@link Alphabet} parser class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetParser extends AlphabetAbstractParser
{

  /**
   * Allocates a new <code>AlphabetParser</code>.
   * 
   * @param pLanguageScanner
   */
  public AlphabetParser ( ScannerInterface pLanguageScanner )
  {
    super ( pLanguageScanner );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#report_error(java.lang.String,
   *      java.lang.Object)
   */
  @Override
  public void report_error ( String pMessage, Object pInfo )
  {
    Symbol symbol = ( Symbol ) pInfo;
    String message = pMessage;
    if ( symbol.sym == EOF_sym () )
    {
      message = Messages.getString ( "Parser.0" ); //$NON-NLS-1$
    }
    throw new ParserException ( symbol.left, symbol.right, message );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#report_fatal_error(java.lang.String,
   *      java.lang.Object)
   */
  @Override
  public void report_fatal_error ( String pMessage, Object pInfo )
      throws Exception
  {
    report_error ( pMessage, pInfo );
  }


  /**
   * {@inheritDoc}}
   * 
   * @see java_cup.runtime.lr_parser#syntax_error(java_cup.runtime.Symbol)
   */
  @Override
  public void syntax_error ( Symbol pSymbol )
  {
    report_error ( Messages.getString ( "Parser.1", pSymbol.value ), pSymbol ); //$NON-NLS-1$
  }
}
