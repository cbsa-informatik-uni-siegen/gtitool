package de.unisiegen.gtitool.core.entities;


/**
 * Represents the {@link CancelOutAction}
 */
public class CancelOutAction extends ShiftActionBase
{
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.CANCEL;
  }
  
  /**
   * 
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "CancleOut"; //$NON-NLS-1$
  }
}
