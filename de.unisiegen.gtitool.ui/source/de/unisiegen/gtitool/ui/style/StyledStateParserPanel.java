package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.parser.state.StateParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.listener.StateChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link State} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledStateParserPanel.java 159 2007-11-15 12:00:39Z fehler $
 */
public final class StyledStateParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 257507642715920652L;


  /**
   * The {@link StateChangedListener} for the other
   * <code>StyledStateParserPanel</code>.
   */
  private StateChangedListener stateChangedListenerOther;


  /**
   * The {@link StateChangedListener} for this
   * <code>StyledStateParserPanel</code>.
   */
  private StateChangedListener stateChangedListenerThis;


  /**
   * Allocates a new <code>StyledStateParserPanel</code>.
   */
  public StyledStateParserPanel ()
  {
    super ( new StateParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireStateChanged ( ( State ) pNewObject );
      }
    } );
  }


  /**
   * Adds the given {@link StateChangedListener}.
   * 
   * @param pListener The {@link StateChangedListener}.
   */
  public final synchronized void addStateChangedListener (
      StateChangedListener pListener )
  {
    this.listenerList.add ( StateChangedListener.class, pListener );
  }


  /**
   * Let the listeners know that the {@link State} has changed.
   * 
   * @param pNewState The new {@link State}.
   */
  private final void fireStateChanged ( State pNewState )
  {
    StateChangedListener [] listeners = this.listenerList
        .getListeners ( StateChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stateChanged ( pNewState );
    }
  }


  /**
   * Returns the {@link State} for the program text within the document. Throws
   * an exception if a parsing error occurred.
   * 
   * @return The {@link State} for the program text.
   */
  public final State getState ()
  {
    try
    {
      return ( State ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * Removes the given {@link StateChangedListener}.
   * 
   * @param pListener The {@link StateChangedListener}.
   */
  public final synchronized void removeStateChangedListener (
      StateChangedListener pListener )
  {
    this.listenerList.remove ( StateChangedListener.class, pListener );
  }


  /**
   * Sets the {@link State} of the document.
   * 
   * @param pState The input {@link State}.
   */
  public final void setState ( State pState )
  {
    getEditor ().setText ( pState.toString () );
  }


  /**
   * Synchronizes this <code>StyledStateParserPanel</code> with the given
   * <code>StyledStateParserPanel</code>.
   * 
   * @param pStyledStateParserPanel The other
   *          <code>StyledStateParserPanel</code> which should be
   *          synchronized.
   */
  public final void synchronize (
      final StyledStateParserPanel pStyledStateParserPanel )
  {
    this.stateChangedListenerOther = new StateChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void stateChanged ( @SuppressWarnings ( "unused" )
      State pNewState )
      {
        removeStateChangedListener ( StyledStateParserPanel.this.stateChangedListenerThis );
        getEditor ().setText ( pStyledStateParserPanel.getEditor ().getText () );
        addStateChangedListener ( StyledStateParserPanel.this.stateChangedListenerThis );
      }
    };
    this.stateChangedListenerThis = new StateChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void stateChanged ( @SuppressWarnings ( "unused" )
      State pNewState )
      {
        pStyledStateParserPanel
            .removeStateChangedListener ( StyledStateParserPanel.this.stateChangedListenerOther );
        pStyledStateParserPanel.getEditor ().setText ( getEditor ().getText () );
        pStyledStateParserPanel
            .addStateChangedListener ( StyledStateParserPanel.this.stateChangedListenerOther );
      }
    };
    pStyledStateParserPanel
        .addStateChangedListener ( this.stateChangedListenerOther );
    addStateChangedListener ( this.stateChangedListenerThis );
  }
}
