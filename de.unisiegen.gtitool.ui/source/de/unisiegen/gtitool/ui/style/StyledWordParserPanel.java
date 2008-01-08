package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.word.WordParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.WordChangedListener;
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
   * Allocates a new <code>StyledWordParserPanel</code>.
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
   * Let the listeners know that the {@link Word} has changed.
   * 
   * @param newWord The new {@link Word}.
   */
  private final void fireWordChanged ( Word newWord )
  {
    if ( ( newWord != null ) && ( this.alphabet != null ) )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( Symbol current : newWord )
      {
        if ( !this.alphabet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current
              .getParserStartOffset (), current.getParserEndOffset (), Messages
              .getString ( "StyledWordParserPanel.SymbolNotInAlphabet", //$NON-NLS-1$
                  current.getName (), this.alphabet ) ) );
        }
      }
      if ( exceptionList.size () > 0 )
      {
        getDocument ().setException ( exceptionList );
        WordChangedListener [] listeners = this.listenerList
            .getListeners ( WordChangedListener.class );
        for ( int n = 0 ; n < listeners.length ; ++n )
        {
          listeners [ n ].wordChanged ( null );
        }
        return;
      }
    }
    WordChangedListener [] listeners = this.listenerList
        .getListeners ( WordChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].wordChanged ( newWord );
    }
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
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
      return ( Word ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
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
