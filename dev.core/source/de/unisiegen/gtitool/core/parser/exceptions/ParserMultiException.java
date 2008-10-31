package de.unisiegen.gtitool.core.parser.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The parser multi exception class.
 * 
 * @author Christian Fehler
 * @version $Id: ParserMultiException.java 946 2008-05-30 14:27:24Z fehler $
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
   * Throws a {@link ParserMultiException} if the {@link NonterminalSymbolSet}
   * consist of {@link NonterminalSymbol}s with the same name.
   * 
   * @param negativeNonterminalSymbols The input list of
   *          {@link NonterminalSymbol}s.
   */
  public static void throwNonterminalSymbolSetException (
      ArrayList < NonterminalSymbol > negativeNonterminalSymbols )
  {
    String [] message = new String [ negativeNonterminalSymbols.size () ];
    int [] startOffset = new int [ negativeNonterminalSymbols.size () ];
    int [] endOffset = new int [ negativeNonterminalSymbols.size () ];
    for ( int j = 0 ; j < negativeNonterminalSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString (
          "Parser.7", negativeNonterminalSymbols //$NON-NLS-1$
              .get ( j ) );
      startOffset [ j ] = negativeNonterminalSymbols.get ( j )
          .getParserOffset ().getStart ();
      endOffset [ j ] = negativeNonterminalSymbols.get ( j ).getParserOffset ()
          .getEnd ();
    }
    throw new ParserMultiException ( startOffset, endOffset, message );
  }


  /**
   * Throws a {@link ParserMultiException} if the {@link StateSet} consist of
   * {@link State}s with the same name.
   * 
   * @param negativeStates The input list of {@link State}s.
   */
  public static void throwStateSetException ( ArrayList < State > negativeStates )
  {
    String [] message = new String [ negativeStates.size () ];
    int [] startOffset = new int [ negativeStates.size () ];
    int [] endOffset = new int [ negativeStates.size () ];
    for ( int j = 0 ; j < negativeStates.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.11", negativeStates //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = negativeStates.get ( j ).getParserOffset ()
          .getStart ();
      endOffset [ j ] = negativeStates.get ( j ).getParserOffset ().getEnd ();
    }
    throw new ParserMultiException ( startOffset, endOffset, message );
  }


  /**
   * Throws a {@link ParserMultiException} if the {@link TerminalSymbolSet}
   * consist of {@link TerminalSymbol}s with the same name.
   * 
   * @param negativeTerminalSymbols The input list of {@link TerminalSymbol}s.
   */
  public static void throwTerminalSymbolSetException (
      ArrayList < TerminalSymbol > negativeTerminalSymbols )
  {
    String [] message = new String [ negativeTerminalSymbols.size () ];
    int [] startOffset = new int [ negativeTerminalSymbols.size () ];
    int [] endOffset = new int [ negativeTerminalSymbols.size () ];
    for ( int j = 0 ; j < negativeTerminalSymbols.size () ; j++ )
    {
      message [ j ] = Messages.getString ( "Parser.8", negativeTerminalSymbols //$NON-NLS-1$
          .get ( j ) );
      startOffset [ j ] = negativeTerminalSymbols.get ( j ).getParserOffset ()
          .getStart ();
      endOffset [ j ] = negativeTerminalSymbols.get ( j ).getParserOffset ()
          .getEnd ();
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
