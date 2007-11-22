package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

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
   * The list of {@link StateChangedListener}.
   */
  private ArrayList < StateChangedListener > stateChangedListenerList = new ArrayList < StateChangedListener > ();


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
    this.stateChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the {@link State} has changed.
   * 
   * @param pNewState The new {@link State}.
   */
  private final void fireStateChanged ( State pNewState )
  {
    for ( StateChangedListener current : this.stateChangedListenerList )
    {
      current.stateChanged ( pNewState );
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
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeStateChangedListener (
      StateChangedListener pListener )
  {
    return this.stateChangedListenerList.remove ( pListener );
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
}
