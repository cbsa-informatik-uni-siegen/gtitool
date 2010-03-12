package de.unisiegen.gtitool.core.entities;


/**
 * Represents the {@link CancelOutAction}
 */
public class CancelOutAction extends ShiftActionBase
{
  /**
   * The {@link Symbol} which was canceled out
   */
  private Symbol symbol;
  
  /**
   * Allocates a new {@link CancelOutAction}
   *
   */
  public CancelOutAction()
  {
    this.symbol = new DefaultSymbol ();
  }
  
  /**
   * Allocates a new {@link CancelOutAction}
   *
   * @param symbol The {@link Symbol}
   */
  public CancelOutAction(final Symbol symbol)
  {
    this.symbol = symbol;
  }
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
    if(this.symbol.getName ().equals ( "" )) //$NON-NLS-1$
      return "CancleOut"; //$NON-NLS-1$
    return "match " + this.symbol.getName (); //$NON-NLS-1$
  }
}
