package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.ParseableEntity;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Alphabet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledAlphabetParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6870722718951231990L;


  /**
   * Allocates a new <code>StyledAlphabetParserPanel</code>.
   */
  public StyledAlphabetParserPanel ()
  {
    super ( new AlphabetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireAlphabetChanged ( ( Alphabet ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public final synchronized void addAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.add ( AlphabetChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   * 
   * @param newAlphabet The new {@link Alphabet}.
   */
  private final void fireAlphabetChanged ( Alphabet newAlphabet )
  {
    AlphabetChangedListener [] listeners = this.listenerList
        .getListeners ( AlphabetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].alphabetChanged ( newAlphabet );
    }
  }


  /**
   * Returns the {@link Alphabet} for the program text within the document.
   * 
   * @return The {@link Alphabet} for the program text.
   */
  public final Alphabet getAlphabet ()
  {
    try
    {
      return ( Alphabet ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public final synchronized void removeAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.remove ( AlphabetChangedListener.class, listener );
  }


  /**
   * Sets the {@link Alphabet} of the document.
   * 
   * @param alphabet The input {@link Alphabet}.
   */
  public final void setAlphabet ( Alphabet alphabet )
  {
    getEditor ().setText ( alphabet.toString () );
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
    ParseableEntity [] entities = new ParseableEntity [ symbols.length ];
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
