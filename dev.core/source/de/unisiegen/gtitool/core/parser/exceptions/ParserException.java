package de.unisiegen.gtitool.core.parser.exceptions;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


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
   * Thrown when second character is greater than the first.
   * 
   * @param c1 Greater char
   * @param c2 Lower char
   * @param pos1 first pos
   * @param pos2 last pos
   */
  public static void throwCharacterClassException ( char c1, char c2, int pos1,
      int pos2 )
  {
    throw new ParserException ( pos1, pos2, Messages.getPrettyString (
        "Parser.14", //$NON-NLS-1$
        new PrettyString ( new PrettyToken ( Character.toString ( c1 ),
            Style.REGEX_SYMBOL ) ),
        new PrettyString ( new PrettyToken ( Character.toString ( c2 ),
            Style.REGEX_SYMBOL ) ) ).toHTMLString () );
  }


  /**
   * Throws new Comment Exception
   * 
   * @param pos1 First position
   * @param pos2 Last position
   */
  public static void throwCommentException ( int pos1, int pos2 )
  {
    throw new ParserException ( pos1, pos2, Messages.getString ( "Parser.17" ) ); //$NON-NLS-1$
  }


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
