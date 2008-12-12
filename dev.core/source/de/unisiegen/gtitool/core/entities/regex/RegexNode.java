package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * Abstract class for a Node in a Regex
 * 
 * @author Simon Meurer
 * @version
 */
public abstract class RegexNode implements Entity < RegexNode >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6437803797773026712L;


  /**
   * Flag that indicates if user has used braces around the node
   */
  protected boolean braces = false;

  /**
   * Flag that indicates if the node is active
   */
  private boolean active = false;


  /**
   * Sets the active.
   * 
   * @param active The active to set.
   * @see #active
   */
  public void setActive ( boolean active )
  {
    this.active = active;
  }


  /**
   * Returns the active.
   * 
   * @return The active.
   * @see #active
   */
  public boolean isActive ()
  {
    return this.active;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public abstract boolean equals ( Object o );


  /**
   * Function firstpos as defined in the dragonbook
   * 
   * @return {@link ArrayList} of {@link RegexNode} that can be the first
   *         positions of the {@link RegexNode}
   */
  public abstract ArrayList < LeafNode > firstPos ();


  /**
   * Gets all Children of this Node
   * 
   * @return All children of this node
   */
  public abstract ArrayList < RegexNode > getAllChildren ();


  /**
   * Gets the direct children of this Node
   * 
   * @return The dircet children of this Node
   */
  public abstract ArrayList < RegexNode > getChildren ();


  /**
   * Returns the max Height of the tree
   * 
   * @return The max Height of the tree
   */
  public abstract int getHeight ();


  /**
   * Counts the left children
   * 
   * @return the left children count
   */
  public abstract int getLeftChildrenCount ();


  /**
   * Returns the next node for NFA-Creation
   * 
   * @return The next node for NFA-Creation
   */
  public abstract RegexNode getNextNodeForNFA ();


  /**
   * Get the {@link PrettyString} for the Node in the JGTIGraph
   * 
   * @return The {@link PrettyString} for the Node in the JGTIGraph
   */
  public abstract PrettyString getNodeString ();


  /**
   * Counts the right children
   * 
   * @return the right children count
   */
  public abstract int getRightChildrenCount ();


  /**
   * Gets all Tokennodes that are in this node
   * 
   * @return All Tokennodes that are in this node
   */
  public abstract ArrayList < LeafNode > getTokenNodes ();
  
  public abstract int countDisjunctions();


  /**
   * Get the maximal Width of the whole Regex
   * 
   * @return The maximal Width of the wohle Regex
   */
  public abstract int getWidth ();


  /**
   * Returns true if Regex is in CoreSyntax
   * 
   * @return True if Regex is in CoreSyntax
   */
  public abstract boolean isInCoreSyntax ();


  /**
   * Returns true if Node is marked in creation of NFA
   * 
   * @return True if Node is marked in creation of NFA
   */
  public abstract boolean isMarked ();
  
  public abstract boolean isMarkedAll ();


  /**
   * Resets the mark flag.
   */
  public abstract void unmark ();


  /**
   * Resets the mark flag of the whole regex
   */
  public abstract void unmarkAll ();


  /**
   * Function lastpos as defined in the dragonbook
   * 
   * @return {@link ArrayList} of {@link RegexNode} that can be the last
   *         positions of the {@link RegexNode}
   */
  public abstract ArrayList < LeafNode > lastPos ();


  /**
   * Function nullable as defined in the dragonbook
   * 
   * @return true, if the node can be epsilon
   */
  public abstract boolean nullable ();


  /**
   * Sets flag that indicates if user used braces around the regex
   * 
   * @param braces True if braces are used
   */
  public void setBraces ( boolean braces )
  {
    this.braces = braces;
  }


  /**
   * Translate the RegexNode to Core Syntax
   * 
   * @return the RegexNode in Core Syntax
   */
  public abstract RegexNode toCoreSyntax ();
}
