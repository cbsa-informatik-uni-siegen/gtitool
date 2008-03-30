package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.core.parser.state.StateParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link State} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledStateParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 257507642715920652L;


  /**
   * Allocates a new {@link StyledStateParserPanel}.
   */
  public StyledStateParserPanel ()
  {
    super ( new StateParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireStateChanged ( ( State ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public final synchronized void addStateChangedListener (
      StateChangedListener listener )
  {
    this.listenerList.add ( StateChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link State} has changed.
   * 
   * @param newState The new {@link State}.
   */
  private final void fireStateChanged ( State newState )
  {
    StateChangedListener [] listeners = this.listenerList
        .getListeners ( StateChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stateChanged ( newState );
    }
  }


  /**
   * Returns the {@link State} for the program text within the document.
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final State parse ()
  {
    return ( State ) super.parse ();
  }


  /**
   * Removes the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public final synchronized void removeStateChangedListener (
      StateChangedListener listener )
  {
    this.listenerList.remove ( StateChangedListener.class, listener );
  }


  /**
   * Sets the {@link State} of the document.
   * 
   * @param state The input {@link State}.
   */
  public final void setText ( State state )
  {
    getEditor ().setText ( state.toString () );
  }
}
