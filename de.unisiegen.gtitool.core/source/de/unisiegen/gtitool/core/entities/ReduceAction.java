package de.unisiegen.gtitool.core.entities;


/**
 * Represents a {@link ReduceAction}
 */
public class ReduceAction extends ReplaceAction
{

  /**
   * Allocates a new {@link ReduceAction}
   * 
   * @param production The {@link Production}
   */
  public ReduceAction ( final Production production )
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
    return Action.TransitionType.REDUCE;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Reduce: " + this.production.toString (); //$NON-NLS-1$
  }
}
