package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.i18n.Messages;


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
    return Messages.getString ( "Entities.Actions.Shift" ); //$NON-NLS-1$
  }
}
