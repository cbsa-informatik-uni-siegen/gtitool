package de.unisiegen.gtitool.core.grammars;


import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The abstract class for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id: AbstractMachine.java 398 2008-01-02 23:13:39Z fehler $
 */
public abstract class AbstractGrammar implements Grammar
{

  /**
   * List of listeners
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * {@inheritDoc}
   * 
   * @see Machine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Returns the <code>Grammar</code> type.
   * 
   * @return The <code>Grammar</code> type.
   */
  public abstract String getGrammarType ();


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    // TODO
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    // TODO
  }
}
