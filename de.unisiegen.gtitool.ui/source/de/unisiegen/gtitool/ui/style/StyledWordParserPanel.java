package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

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
public final class StyledWordParserPanel extends StyledParserPanel < Word >
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
    super
        .addParseableChangedListener ( new ParseableChangedListener < Word > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Word newWord )
          {
            fireWordChanged ( newWord );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final Word checkParsedObject ( Word word )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.alphabet != null ) && ( word != null ) )
    {
      for ( Symbol current : word )
      {
        if ( !this.alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                  current.getName (), this.alphabet ) ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return word;
  }


  /**
   * Let the listeners know that the {@link Word} has changed.
   * 
   * @param newWord The new {@link Word}.
   */
  private final void fireWordChanged ( Word newWord )
  {
    Word checkedWord = checkParsedObject ( newWord );
    WordChangedListener [] listeners = this.listenerList
        .getListeners ( WordChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].wordChanged ( checkedWord );
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
    return checkParsedObject ( word );
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
}
