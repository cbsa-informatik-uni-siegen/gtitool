package de.unisiegen.gtitool.core.entities;




/**
 * TODO
 */
public interface LRAction extends Comparable<LRAction>
{

  /**
   * The LR action to do
   *
   */
  public enum TransitionType
  {
    /**
     * Shift the next character onto the stack
     */
    SHIFT,
    /**
     * Reduce the current symbols on the top of the stack
     */
    REDUCE,
    /**
     * Accept the whole input
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
