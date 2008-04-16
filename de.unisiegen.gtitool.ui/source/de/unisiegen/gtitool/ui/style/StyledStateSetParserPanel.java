package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.entities.listener.StateSetChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.stateset.StateSetParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link StateSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledStateSetParserPanel extends
    StyledParserPanel < StateSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -177852679422657040L;


  /**
   * Every {@link State} in the {@link StateSet} has to be in this {@link State}
   * list.
   */
  private ArrayList < State > stateList = null;


  /**
   * Allocates a new {@link StyledStateSetParserPanel}.
   */
  public StyledStateSetParserPanel ()
  {
    super ( new StateSetParseable () );
    super
        .addParseableChangedListener ( new ParseableChangedListener < StateSet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( StateSet newStateSet )
          {
            fireStateSetChanged ( newStateSet );
          }
        } );
  }


  /**
   * Adds the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
   */
  public final synchronized void addStateSetChangedListener (
      StateSetChangedListener listener )
  {
    this.listenerList.add ( StateSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final StateSet checkParsedObject ( StateSet stateSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.stateList != null ) && ( stateSet != null ) )
    {
      for ( State current : stateSet )
      {
        boolean found = false;
        for ( State currentState : this.stateList )
        {
          if ( current.getName ().equals ( currentState.getName () ) )
          {
            found = true;
            break;
          }
        }

        if ( !found )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "StyledStateSetParserPanel.StateNotInStateSet", //$NON-NLS-1$
                  current.getName (), this.stateList ) ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return stateSet;
  }


  /**
   * Let the listeners know that the {@link StateSet} has changed.
   * 
   * @param newStateSet The new {@link StateSet}.
   */
  private final void fireStateSetChanged ( StateSet newStateSet )
  {
    StateSet checkedStateSet = checkParsedObject ( newStateSet );
    StateSetChangedListener [] listeners = this.listenerList
        .getListeners ( StateSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stateSetChanged ( checkedStateSet );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final StateSet parse ()
  {
    StateSet stateSet = ( StateSet ) super.parse ();
    return checkParsedObject ( stateSet );
  }


  /**
   * Removes the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
   */
  public final synchronized void removeStateSetChangedListener (
      StateSetChangedListener listener )
  {
    this.listenerList.remove ( StateSetChangedListener.class, listener );
  }


  /**
   * Sets the {@link State}s which should be highlighted.
   * 
   * @param states The {@link State}s which should be highlighted.
   */
  public final void setHighlightedState ( Iterable < State > states )
  {
    setHighlightedParseableEntity ( states );
  }


  /**
   * Sets the {@link State}s which should be highlighted.
   * 
   * @param states The {@link State}s which should be highlighted.
   */
  public final void setHighlightedState ( State ... states )
  {
    Entity [] entities = new Entity [ states.length ];
    for ( int i = 0 ; i < states.length ; i++ )
    {
      entities [ i ] = states [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link State} which should be highlighted.
   * 
   * @param state The {@link State} which should be highlighted.
   */
  public final void setHighlightedState ( State state )
  {
    setHighlightedParseableEntity ( state );
  }


  /**
   * Sets the {@link State} list. Every {@link State} in the {@link StateSet}
   * has to be in the {@link State} list.
   * 
   * @param statesList The {@link State} list to set.
   */
  public final void setStateList ( ArrayList < State > statesList )
  {
    this.stateList = statesList;
  }
}
