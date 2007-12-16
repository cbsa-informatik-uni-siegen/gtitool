package de.unisiegen.gtitool.core.parser.word;


import java_cup.runtime.Symbol;
import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;


/**
 * The {@link de.unisiegen.gtitool.core.entities.Word} parser class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordParser extends WordAbstractParser
{

  /**
   * Allocates a new <code>WordParser</code>.
   * 
   * @param pGTIScanner The {@link GTIScanner}.
   */
  public WordParser ( GTIScanner pGTIScanner )
  {
    super ( pGTIScanner );
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
   * {@inheritDoc}
   * 
   * @see java_cup.runtime.lr_parser#syntax_error(java_cup.runtime.Symbol)
   */
  @Override
  public void syntax_error ( Symbol pSymbol )
  {
    report_error ( Messages.getString ( "Parser.1", pSymbol.value ), pSymbol ); //$NON-NLS-1$
  }
}
