package de.unisiegen.gtitool.core.entities;


/**
 * Represents the {@link CancleOutAction}
 */
public class CancleOutAction extends ShiftActionBase
{
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.CANCLE;
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
