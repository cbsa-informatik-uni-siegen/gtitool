package de.unisiegen.gtitool.core.entities;


/**
 * TODO
 */
public class LRAcceptAction implements LRAction
{

  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.ACCEPT;
  }


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
  public int compareTo ( LRAction o )
  {
    return 0;
  }

}
