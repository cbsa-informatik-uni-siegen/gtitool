package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Point;
import java.awt.Rectangle;

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
    if ( startState == null )
    {
      throw new IllegalArgumentException ( "startState is null" ); //$NON-NLS-1$
    }
    if ( finalState == null )
    {
      throw new IllegalArgumentException ( "finalState is null" ); //$NON-NLS-1$
    }
    this.startState = startState;
    this.finalState = finalState;
    this.content = content;
  }


  /**
   * Returns true if the {@link Point} is in the Blackbox.
   * 
   * @param p The {@link Point}
   * @return True if the {@link Point} is in the Blackbox.
   */
  public boolean containsPoint ( Point p )
  {
    int x0 = ( int ) ( getStartState ().getPositionX ()
        - JGTIBlackboxGraph.X_SPACE - this.startState.getWidth () / 2 - JGTIBlackboxGraph.X_SPACE );
    int y0 = ( int ) ( getStartState ().getPositionY () - JGTIBlackboxGraph.Y_SPACE );
    int y1 = ( int ) ( getStartState ().getHeight () + 2 * JGTIBlackboxGraph.Y_SPACE );

    int x1 = ( int ) ( this.finalState.getPositionX ()
        - this.startState.getPositionX () + 4 * JGTIBlackboxGraph.X_SPACE );
    Rectangle r = new Rectangle ( x0, y0, x1, y1 );
    return r.contains ( p );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj instanceof DefaultBlackboxView )
    {
      DefaultBlackboxView other = ( DefaultBlackboxView ) obj;
      return this.startState.equals ( other.startState )
          && this.finalState.equals ( other.finalState );
    }
    return false;
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
   * Returns the content {@link String}
   * 
   * @return The content {@link String}
   */
  public String getContentString ()
  {
    if ( this.content.toPrettyString ().toString ().length () > 15 )
    {
      String s = this.content.toPrettyString ().toString ().substring ( 0, 10 );
      s += "..."; //$NON-NLS-1$
      return s;
    }
    return this.content.toPrettyString ().toString ();
  }


  /**
   * Returns true if Tooltip is needed
   * 
   * @return True if Tooltip is needed
   */
  public boolean needsToolTip ()
  {
    return this.content.toPrettyString ().toString ().length () > 15;
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


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.startState.hashCode () + this.finalState.hashCode ()
        + this.content.hashCode ();
  }

}
