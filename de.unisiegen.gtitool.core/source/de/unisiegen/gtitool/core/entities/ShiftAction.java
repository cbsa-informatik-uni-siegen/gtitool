package de.unisiegen.gtitool.core.entities;


/**
 * Represents the {@link ShiftAction} of a push down automaton
 */
public class ShiftAction extends ShiftActionBase
{
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.SHIFT;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Shift"; //$NON-NLS-1$
  }
}
