package de.unisiegen.gtitool.core.entities;




/**
 * TODO
 */
public interface LRAction extends Comparable<LRAction>
{

  public enum TransitionType
  {
    /**
     * TODO
     */
    SHIFT, /**
     * TODO
     */
    REDUCE,
    /**
     * TODO
     */
    ACCEPT
  }


  /**
   * The transition to perform (shift or reduce)
   *
   * @return the transition
   */
  public TransitionType getTransitionType ();
  
  
  /**
   * Which reduction should be performed?
   *
   * @return null if the TransitionType is SHIFT, the production otherwise
   */
  public Production getReduceAction();
}
