package de.unisiegen.gtitool.core.parser.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The parser multi exception class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ParserMultiException extends ParserException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3411147227563168312L;


  /**
   * Throws a <code>ParserMultiException</code> if the {@link Alphabet}
   * consist of {@link Symbol}s with the same name.
   * 
   * @param pNegativeSymbols The input list of {@link Symbol}s.
   */
  public static void throwAlphabetException (
      ArrayList < Symbol > pNegativeSymbols )
  {
    String [] message = new String [ pNegativeSymbols.size () ];
    int [] startOffset = new int [ pNegativeSymbols.size () ];
    int [] endOffset = new int [ pNegativeSymbols.size () ];
    for ( int j = 0 ; j < pNegativeSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.4", pNegativeSymbols //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = pNegativeSymbols.get ( j ).getParserStartOffset ();
      endOffset [ j ] = pNegativeSymbols.get ( j ).getParserEndOffset ();
    }
    throw new ParserMultiException ( startOffset, endOffset, message );
  }


  /**
   * Throws a <code>ParserMultiException</code> if the {@link Transition}
   * consist of {@link Symbol}s with the same name.
   * 
   * @param pNegativeSymbols The input list of {@link Symbol}s.
   */
  public static void throwTransitionException (
      ArrayList < Symbol > pNegativeSymbols )
  {
    String [] message = new String [ pNegativeSymbols.size () ];
    int [] startOffset = new int [ pNegativeSymbols.size () ];
    int [] endOffset = new int [ pNegativeSymbols.size () ];
    for ( int j = 0 ; j < pNegativeSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.5", pNegativeSymbols //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = pNegativeSymbols.get ( j ).getParserStartOffset ();
      endOffset [ j ] = pNegativeSymbols.get ( j ).getParserEndOffset ();
    }
    throw new ParserMultiException ( startOffset, endOffset, message );
  }


  /**
   * The array of shown messages.
   */
  private String [] messages;


  /**
   * The array of parser start offsets.
   */
  private int [] parserStartOffset;


  /**
   * The array of parser end offsets.
   */
  private int [] parserEndOffset;


  /**
   * Initializes the exception.
   * 
   * @param pParserStartOffset The array of parser start offsets.
   * @param pParserEndOffset The array of parser end offsets.
   * @param pMessages The array of shown messages.
   */
  public ParserMultiException ( int [] pParserStartOffset,
      int [] pParserEndOffset, String [] pMessages )
  {
    super ( pParserStartOffset [ 0 ], pParserEndOffset [ 0 ], pMessages [ 0 ] );
    this.messages = pMessages;
    this.parserStartOffset = pParserStartOffset;
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * Returns the messages.
   * 
   * @return The messages.
   * @see #messages
   */
  public String [] getMessages ()
  {
    return this.messages;
  }


  /**
   * Returns the endOffset.
   * 
   * @return The endOffset.
   * @see #parserEndOffset
   */
  public int [] getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * Returns the startOffset.
   * 
   * @return The startOffset.
   * @see #parserStartOffset
   */
  public int [] getParserStartOffset ()
  {
    return this.parserStartOffset;
  }
}
