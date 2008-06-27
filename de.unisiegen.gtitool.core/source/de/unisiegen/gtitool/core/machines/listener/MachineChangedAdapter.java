package de.unisiegen.gtitool.core.machines.listener;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;
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
   * @see MachineChangedListener#startEditing()
   */
  public void startEditing ()
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#stopEditing()
   */
  public void stopEditing ()
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#symbolAdded(Transition,ArrayList)
   */
  public void symbolAdded (
      @SuppressWarnings ( "unused" ) Transition transition,
      @SuppressWarnings ( "unused" ) ArrayList < Symbol > addedSymbols )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#symbolRemoved(Transition,ArrayList)
   */
  public void symbolRemoved (
      @SuppressWarnings ( "unused" ) Transition transition,
      @SuppressWarnings ( "unused" ) ArrayList < Symbol > removedSymbols )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#transitionAdded(Transition)
   */
  public void transitionAdded (
      @SuppressWarnings ( "unused" ) Transition newTransition )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see MachineChangedListener#transitionRemoved(Transition)
   */
  public void transitionRemoved (
      @SuppressWarnings ( "unused" ) Transition transition )
  {
    // Override this method if needed.
  }
}
