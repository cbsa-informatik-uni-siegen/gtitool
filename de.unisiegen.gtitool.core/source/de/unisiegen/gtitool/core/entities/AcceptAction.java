package de.unisiegen.gtitool.core.entities;


/**
 * The LR Accept action
 */
public class AcceptAction implements Action
{

  /**
   * The transition type
   * 
   * @return ACCEPT
   * @see de.unisiegen.gtitool.core.entities.Action#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return Action.TransitionType.ACCEPT;
  }


  /**
   * The reduce action
   * 
   * @return null (nothing to reduce)
   * @see de.unisiegen.gtitool.core.entities.Action#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( final Action o )
  {
    // AcceptActions are below everything
    if ( o instanceof AcceptAction )
      return 0;
    return -1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Accept"; //$NON-NLS-1$
  }

}
