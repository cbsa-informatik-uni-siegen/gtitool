package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import javax.swing.border.LineBorder;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.WordChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.word.WordParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Word} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledWordParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2677286061045400139L;


  /**
   * Every {@link Symbol} in the {@link Word} has to be in this {@link Alphabet}.
   */
  private Alphabet alphabet = null;


  /**
   * Allocates a new {@link StyledWordParserPanel}.
   */
  public StyledWordParserPanel ()
  {
    super ( new WordParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireWordChanged ( ( Word ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link WordChangedListener}.
   * 
   * @param listener The {@link WordChangedListener}.
   */
  public final synchronized void addWordChangedListener (
      WordChangedListener listener )
  {
    this.listenerList.add ( WordChangedListener.class, listener );
  }


  /**
   * Checks the given {@link Word}.
   * 
   * @param word The {@link Word} to check.
   * @return The input {@link Word} or null, if a {@link Symbol} in the
   *         {@link Word} is not in the {@link Alphabet}.
   */
  private final Word checkWord ( Word word )
  {
    if ( this.alphabet == null )
    {
      throw new RuntimeException ( "alphabet is not set" ); //$NON-NLS-1$
    }

    Word checkedWord = word;
    if ( checkedWord != null )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( Symbol current : checkedWord )
      {
        if ( !this.alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                  current.getName (), this.alphabet ) ) );
        }
      }
      // Check for exceptions
      if ( exceptionList.size () > 0 )
      {
        checkedWord = null;
        this.jScrollPane.setBorder ( new LineBorder ( ERROR_COLOR ) );
        getDocument ().setException ( exceptionList );
      }
    }
    return checkedWord;
  }


  /**
   * Let the listeners know that the {@link Word} has changed.
   * 
   * @param newWord The new {@link Word}.
   */
  private final void fireWordChanged ( Word newWord )
  {
    Word checkedWord = checkWord ( newWord );
    WordChangedListener [] listeners = this.listenerList
        .getListeners ( WordChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].wordChanged ( checkedWord );
    }
  }


  /**
   * Returns the {@link Word} for the program text within the document.
   * 
   * @return The {@link Word} for the program text.
   */
  public final Word getWord ()
  {
    try
    {
      Word word = ( Word ) getParsedObject ();
      return checkWord ( word );
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final Word parse ()
  {
    Word word = ( Word ) super.parse ();
    return checkWord ( word );
  }


  /**
   * Removes the given {@link WordChangedListener}.
   * 
   * @param listener The {@link WordChangedListener}.
   */
  public final synchronized void removeWordChangedListener (
      WordChangedListener listener )
  {
    this.listenerList.remove ( WordChangedListener.class, listener );
  }


  /**
   * Sets the {@link Alphabet}. Every {@link Symbol} in the {@link Word} has to
   * be in the {@link Alphabet}.
   * 
   * @param alphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet alphabet )
  {
    this.alphabet = alphabet;
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Iterable < Symbol > symbols )
  {
    setHighlightedParseableEntity ( symbols );
  }


  /**
   * Sets the {@link Symbol}s which should be highlighted.
   * 
   * @param symbols The {@link Symbol}s which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol ... symbols )
  {
    Entity [] entities = new Entity [ symbols.length ];
    for ( int i = 0 ; i < symbols.length ; i++ )
    {
      entities [ i ] = symbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link Symbol} which should be highlighted.
   * 
   * @param symbol The {@link Symbol} which should be highlighted.
   */
  public final void setHighlightedSymbol ( Symbol symbol )
  {
    setHighlightedParseableEntity ( symbol );
  }


  /**
   * Sets the {@link Word} of the document.
   * 
   * @param word The input {@link Word}.
   */
  public final void setWord ( Word word )
  {
    getEditor ().setText ( word.toString () );
  }
}
