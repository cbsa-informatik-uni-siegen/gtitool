package de.unisiegen.gtitool.core.machines.listener;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * An abstract adapter class for receiving {@link Machine} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class MachineChangedAdapter implements MachineChangedListener
{

  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#transitionAdded(Transition)
   */
  public void transitionAdded ( @SuppressWarnings ( "unused" )
  Transition newTransition )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#transitionRemoved(Transition)
   */
  public void transitionRemoved ( @SuppressWarnings ( "unused" )
  Transition transition )
  {
    // Override this method if needed.
  }
}
