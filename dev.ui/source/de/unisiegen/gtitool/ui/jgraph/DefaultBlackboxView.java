package de.unisiegen.gtitool.ui.jgraph;


import de.unisiegen.gtitool.core.entities.regex.RegexNode;


/**
 * TODO
 */
public class DefaultBlackboxView
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1281456646341612718L;


  /**
   * TODO
   */
  private RegexNode content;


  /**
   * TODO
   */
  private DefaultStateView finalState;


  /**
   * TODO
   */
  private DefaultStateView startState;


  /**
   * TODO
   * 
   * @param startState
   * @param finalState
   * @param content
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
