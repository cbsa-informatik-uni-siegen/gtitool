package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;


/**
 * Represents the next automaton action to be taken
 */
public interface Action extends Comparable < Action >, PrettyPrintable
{

  /**
   * The LR action to do
   */
  public enum TransitionType
  {
    /**
     * Shift the next character onto the stack
     */
    SHIFT,
    /**
     * Cancle out the input character with the character on the top of the stack
     */
    CANCEL,
    /**
     * Reduce the current symbols on the top of the stack
     */
    REDUCE,
    /**
     * Reduce the current symbols on the top of the stack in reverse order
     * (right side of a {@link Production} is going to be replaced with the left
     * side of the {@link Production}
     */
    REVERSEREDUCE,
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
  public Production getReduceAction ();
}
