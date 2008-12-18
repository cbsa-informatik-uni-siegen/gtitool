package de.unisiegen.gtitool.ui.jgraph;


import de.unisiegen.gtitool.core.entities.regex.RegexNode;


/**
 * Representation of a BlackBox
 */
public class DefaultBlackboxView
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1281456646341612718L;


  /**
   * The {@link RegexNode} that is in the {@link DefaultBlackboxView}
   */
  private RegexNode content;


  /**
   * The final state
   */
  private DefaultStateView finalState;


  /**
   * The start state
   */
  private DefaultStateView startState;


  /**
   * Creates new of {@link DefaultBlackboxView}
   * 
   * @param startState The start state
   * @param finalState The final state
   * @param content The {@link RegexNode}
   */
  public DefaultBlackboxView ( DefaultStateView startState,
      DefaultStateView finalState, RegexNode content )
  {
    this.startState = startState;
    this.finalState = finalState;
    this.content = content;
  }


  /**
   * Returns the content.
   * 
   * @return The content.
   * @see #content
   */
  public RegexNode getContent ()
  {
    return this.content;
  }


  /**
   * Returns the finalState.
   * 
   * @return The finalState.
   * @see #finalState
   */
  public DefaultStateView getFinalState ()
  {
    return this.finalState;
  }


  /**
   * Returns the startState.
   * 
   * @return The startState.
   * @see #startState
   */
  public DefaultStateView getStartState ()
  {
    return this.startState;
  }

}
