package de.unisiegen.gtitool.core.entities;


/**
 * TODO
 */
public class LRReduceAction implements LRAction
{

  /**
   * TODO
   * 
   * @param production
   */
  public LRReduceAction ( Production production )
  {
    this.production = production;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getReduceAction()
   */
  public Production getReduceAction ()
  {
    return this.production;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRAction#getTransitionType()
   */
  public TransitionType getTransitionType ()
  {
    return LRAction.TransitionType.REDUCE;
  }


  /**
   * The production associated with this reduce action
   */
  private Production production;


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( LRAction o )
  {
    // ReduceActions are above everything
    if ( ! ( o instanceof LRReduceAction ) )
      return 1;
    return this.production.compareTo ( o.getReduceAction () );
  }


  /**
   * Returns the string
   * 
   * @return The string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "Reduce: " + this.production.toString (); //$NON-NLS-1$
  }
}
