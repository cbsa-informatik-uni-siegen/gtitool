package de.unisiegen.gtitool.core.entities;


/**
 * Represents a {@link ReverseReduceAction}
 */
public class ReverseReduceAction extends ReplaceAction
{
  /**
   * Allocates a new {@link ReverseReduceAction}
   * 
   * @param production The {@link Production}
   */
  public ReverseReduceAction ( Production production )
  {
    super ( production );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  @Override
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.REVERSEREDUCE;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Reverse-Reduce: " + this.production.toString (); //$NON-NLS-1$
  }
}
