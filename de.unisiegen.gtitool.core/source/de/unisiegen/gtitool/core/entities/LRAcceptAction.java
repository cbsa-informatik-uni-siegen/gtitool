package de.unisiegen.gtitool.core.entities;


/**
 * The LR Accept action
 */
public class LRAcceptAction implements LRAction
{

  /**
   * The transition type
   * 
   * @return ACCEPT
   * @see de.unisiegen.gtitool.core.entities.LRAction#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.ACCEPT;
  }


  /**
   * The reduce action
   * 
   * @return null (nothing to reduce)
   * @see de.unisiegen.gtitool.core.entities.LRAction#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( final LRAction o )
  {
    // AcceptActions are below everything
    if ( o instanceof LRAcceptAction )
      return 0;
    return -1;
  }


  /**
   * The name
   * 
   * @return the name
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Accept"; //$NON-NLS-1$
  }

}
