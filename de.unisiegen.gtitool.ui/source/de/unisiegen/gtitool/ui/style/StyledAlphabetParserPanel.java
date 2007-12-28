package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.parser.alphabet.AlphabetParseable;
import de.unisiegen.gtitool.ui.style.listener.AlphabetChangedListener;
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
   * The {@link AlphabetChangedListener} for the other
   * <code>StyledAlphabetParserPanel</code>.
   */
  private AlphabetChangedListener alphabetChangedListenerOther;


  /**
   * The {@link AlphabetChangedListener} for this
   * <code>StyledAlphabetParserPanel</code>.
   */
  private AlphabetChangedListener alphabetChangedListenerThis;


  /**
   * Allocates a new <code>StyledAlphabetParserPanel</code>.
   */
  public StyledAlphabetParserPanel ()
  {
    super ( new AlphabetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireAlphabetChanged ( ( Alphabet ) pNewObject );
      }
    } );
  }


  /**
   * Adds the given {@link AlphabetChangedListener}.
   * 
   * @param pListener The {@link AlphabetChangedListener}.
   */
  public final synchronized void addAlphabetChangedListener (
      AlphabetChangedListener pListener )
  {
    this.listenerList.add ( AlphabetChangedListener.class, pListener );
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   * 
   * @param pNewAlphabet The new {@link Alphabet}.
   */
  private final void fireAlphabetChanged ( Alphabet pNewAlphabet )
  {
    AlphabetChangedListener [] listeners = this.listenerList
        .getListeners ( AlphabetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].alphabetChanged ( pNewAlphabet );
    }
  }


  /**
   * Returns the {@link Alphabet} for the program text within the document.
   * Throws an exception if a parsing error occurred.
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
   * @param pListener The {@link AlphabetChangedListener}.
   */
  public final synchronized void removeAlphabetChangedListener (
      AlphabetChangedListener pListener )
  {
    this.listenerList.remove ( AlphabetChangedListener.class, pListener );
  }


  /**
   * Sets the {@link Alphabet} of the document.
   * 
   * @param pAlphabet The input {@link Alphabet}.
   */
  public final void setAlphabet ( Alphabet pAlphabet )
  {
    getEditor ().setText ( pAlphabet.toString () );
  }


  /**
   * Synchronizes this <code>StyledAlphabetParserPanel</code> with the given
   * <code>StyledAlphabetParserPanel</code>.
   * 
   * @param pStyledAlphabetParserPanel The other
   *          <code>StyledAlphabetParserPanel</code> which should be
   *          synchronized.
   */
  public final void synchronize (
      final StyledAlphabetParserPanel pStyledAlphabetParserPanel )
  {
    this.alphabetChangedListenerOther = new AlphabetChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void alphabetChanged ( @SuppressWarnings ( "unused" )
      Alphabet pNewAlphabet )
      {
        removeAlphabetChangedListener ( StyledAlphabetParserPanel.this.alphabetChangedListenerThis );
        getEditor ().setText (
            pStyledAlphabetParserPanel.getEditor ().getText () );
        addAlphabetChangedListener ( StyledAlphabetParserPanel.this.alphabetChangedListenerThis );
      }
    };
    this.alphabetChangedListenerThis = new AlphabetChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void alphabetChanged ( @SuppressWarnings ( "unused" )
      Alphabet pNewAlphabet )
      {
        pStyledAlphabetParserPanel
            .removeAlphabetChangedListener ( StyledAlphabetParserPanel.this.alphabetChangedListenerOther );
        pStyledAlphabetParserPanel.getEditor ().setText (
            getEditor ().getText () );
        pStyledAlphabetParserPanel
            .addAlphabetChangedListener ( StyledAlphabetParserPanel.this.alphabetChangedListenerOther );
      }
    };
    pStyledAlphabetParserPanel
        .addAlphabetChangedListener ( this.alphabetChangedListenerOther );
    addAlphabetChangedListener ( this.alphabetChangedListenerThis );
  }
}
