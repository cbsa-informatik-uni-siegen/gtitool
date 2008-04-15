package de.unisiegen.gtitool.core.machines.listener;


import java.util.EventListener;

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
   * Invoked when a new {@link Transition} is added.
   * 
   * @param newTransition The new {@link Object}.
   */
  public void transitionAdded ( Transition newTransition );


  /**
   * Invoked when a {@link Transition} is removed.
   * 
   * @param transition The new {@link Object}.
   */
  public void transitionRemoved ( Transition transition );

}
