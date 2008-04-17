package de.unisiegen.gtitool.core.machines.listener;


import java.util.ArrayList;
import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The listener interface for receiving {@link Machine} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface MachineChangedListener extends EventListener
{

  /**
   * Invoked when the editing is started.
   */
  public void startEditing ();


  /**
   * Invoked when the editing is stopped.
   */
  public void stopEditing ();


  /**
   * Invoked when the {@link Symbol}s were added to the {@link Transition}.
   * 
   * @param transition The modified {@link Transition}.
   * @param addedSymbols The added {@link Symbol}s.
   */
  public void symbolAdded ( Transition transition,
      ArrayList < Symbol > addedSymbols );


  /**
   * Invoked when the {@link Symbol}s were removed from the {@link Transition}.
   * 
   * @param transition The modified {@link Transition}.
   * @param removedSymbols The removed {@link Symbol}s.
   */
  public void symbolRemoved ( Transition transition,
      ArrayList < Symbol > removedSymbols );


  /**
   * Invoked when a new {@link Transition} is added.
   * 
   * @param newTransition The new {@link Transition}.
   */
  public void transitionAdded ( Transition newTransition );


  /**
   * Invoked when the {@link Transition} is removed.
   * 
   * @param transition The removed {@link Transition}.
   */
  public void transitionRemoved ( Transition transition );
}
