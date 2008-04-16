package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.Entity;
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
public final class StyledStateParserPanel extends StyledParserPanel < State >
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
    super
        .addParseableChangedListener ( new ParseableChangedListener < State > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( State newState )
          {
            fireStateChanged ( newState );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final State checkParsedObject ( State state )
  {
    return state;
  }


  /**
   * Let the listeners know that the {@link State} has changed.
   * 
   * @param newState The new {@link State}.
   */
  private final void fireStateChanged ( State newState )
  {
    State checkedState = checkParsedObject ( newState );
    StateChangedListener [] listeners = this.listenerList
        .getListeners ( StateChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stateChanged ( checkedState );
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
    State state = ( State ) super.parse ();
    return checkParsedObject ( state );
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
}
