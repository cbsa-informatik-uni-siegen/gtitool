package de.unisiegen.gtitool.core.parser.exceptions;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The parser exception class.
 * 
 * @author Christian Fehler
 * @version $Id: ParserException.java 946 2008-05-30 14:27:24Z fehler $
 */
public class ParserException extends ScannerException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6750690466685873423L;


  /**
   * Throws a {@link ParserException} if the {@link TerminalSymbol} name is not
   * correct.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param illegalName The illegal name.
   */
  public static void throwNonterminalSymbolException ( int left, int right,
      String illegalName )
  {
    throw new ParserException ( left, right, Messages.getString ( "Parser.9", //$NON-NLS-1$
        illegalName ) );
  }


  /**
   * Throws a {@link ParserException} if the {@link State} name is not correct.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param illegalName The illegal name.
   */
  public static void throwStateException ( int left, int right,
      String illegalName )
  {
    throw new ParserException ( left, right, Messages.getString ( "Parser.12", //$NON-NLS-1$
        illegalName ) );
  }


  /**
   * Throws a {@link ParserException} if the {@link Symbol} name is not correct.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param illegalName The illegal name.
   */
  public static void throwSymbolException ( int left, int right,
      String illegalName )
  {
    throw new ParserException ( left, right, Messages.getString ( "Parser.6", //$NON-NLS-1$
        illegalName ) );
  }


  /**
   * Throws a {@link ParserException} if the {@link TerminalSymbol} name is not
   * correct.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param illegalName The illegal name.
   */
  public static void throwTerminalSymbolException ( int left, int right,
      String illegalName )
  {
    throw new ParserException ( left, right, Messages.getString ( "Parser.10", //$NON-NLS-1$
        illegalName ) );
  }


  /**
   * TODO
   * 
   * @param c1
   * @param c2
   * @param pos1
   * @param pos2
   */
  public static void throwCharacterClassException ( char c1, char c2, int pos1,
      int pos2 )
  {
    throw new ParserException ( pos1, pos2, Messages.getString (
        "Parser.14", new Character ( c1 ), new Character ( c2 ) ) ); //$NON-NLS-1$
  }


  /**
   * Allocates a new {@link ParserException}.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param message The message of the parser.
   */
  public ParserException ( int left, int right, String message )
  {
    super ( left, right, message );
  }
}
