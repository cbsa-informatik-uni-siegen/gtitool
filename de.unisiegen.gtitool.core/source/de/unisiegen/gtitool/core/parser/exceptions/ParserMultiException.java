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
   * Throws a {@link ParserMultiException} if the {@link Alphabet} consist of
   * {@link Symbol}s with the same name.
   * 
   * @param negativeSymbols The input list of {@link Symbol}s.
   */
  public static void throwAlphabetException (
      ArrayList < Symbol > negativeSymbols )
  {
    String [] message = new String [ negativeSymbols.size () ];
    int [] startOffset = new int [ negativeSymbols.size () ];
    int [] endOffset = new int [ negativeSymbols.size () ];
    for ( int j = 0 ; j < negativeSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.4", negativeSymbols //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = negativeSymbols.get ( j ).getParserOffset ()
          .getStart ();
      endOffset [ j ] = negativeSymbols.get ( j ).getParserOffset ().getEnd ();
    }
    throw new ParserMultiException ( startOffset, endOffset, message );
  }


  /**
   * Throws a {@link ParserMultiException} if the {@link Transition} consist of
   * {@link Symbol}s with the same name.
   * 
   * @param negativeSymbols The input list of {@link Symbol}s.
   */
  public static void throwTransitionException (
      ArrayList < Symbol > negativeSymbols )
  {
    String [] message = new String [ negativeSymbols.size () ];
    int [] startOffset = new int [ negativeSymbols.size () ];
    int [] endOffset = new int [ negativeSymbols.size () ];
    for ( int j = 0 ; j < negativeSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.5", negativeSymbols //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = negativeSymbols.get ( j ).getParserOffset ()
          .getStart ();
      endOffset [ j ] = negativeSymbols.get ( j ).getParserOffset ().getEnd ();
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
   * @param parserStartOffset The array of parser start offsets.
   * @param parserEndOffset The array of parser end offsets.
   * @param messages The array of shown messages.
   */
  public ParserMultiException ( int [] parserStartOffset,
      int [] parserEndOffset, String [] messages )
  {
    super ( parserStartOffset [ 0 ], parserEndOffset [ 0 ], messages [ 0 ] );
    this.messages = messages;
    this.parserStartOffset = parserStartOffset;
    this.parserEndOffset = parserEndOffset;
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
