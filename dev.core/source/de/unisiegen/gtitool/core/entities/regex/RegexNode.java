package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * Abstract class for a Node in a Regex
 * 
 * @author Simon Meurer
 * @version
 */
public abstract class RegexNode implements Entity < RegexNode >
{

  /**
   * Flag that indicates if user has used braces around the node
   */
  protected boolean braces = false;


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
  public abstract ArrayList < RegexNode > firstPos ();


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


  /**
   * Get the {@link PrettyString} for the Tooltip that contains nullable,
   * firstpos and lastpos
   * 
   * @return The {@link PrettyString} for the Tooltip
   */
  public PrettyString getToolTipString ()
  {
    // TODO Internationalize
    PrettyString ps = new PrettyString ();
    ps.add ( new PrettyToken ( "Nullable: ", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
    ps.add ( new PrettyToken ( "" + nullable (), Style.REGEX_POSITION ) ); //$NON-NLS-1$
    ps.add ( new PrettyToken ( ", Firstpos: {", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
    int i = 0;
    for ( RegexNode current : firstPos () )
    {
      if ( i > 0 )
      {
        i++ ;
        ps.add ( new PrettyToken ( ";", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
      }
      if ( current instanceof LeafNode )
      {
        ps.add ( new PrettyToken ( "" //$NON-NLS-1$
            + ( ( LeafNode ) current ).getPosition (), Style.REGEX_POSITION ) );
      }
    }
    ps.add ( new PrettyToken ( "}, Lastpos: {", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
    i = 0;
    for ( RegexNode current : lastPos () )
    {
      if ( i > 0 )
      {
        i++ ;
        ps.add ( new PrettyToken ( ";", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
      }
      if ( current instanceof LeafNode )
      {
        ps.add ( new PrettyToken ( "" //$NON-NLS-1$
            + ( ( LeafNode ) current ).getPosition (), Style.REGEX_POSITION ) );
      }
    }

    ps.add ( new PrettyToken ( "}", Style.REGEX_TOOL_TIP_TEXT ) ); //$NON-NLS-1$
    return ps;
  }


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
  
  public abstract void unmark();


  /**
   * Function lastpos as defined in the dragonbook
   * 
   * @return {@link ArrayList} of {@link RegexNode} that can be the last
   *         positions of the {@link RegexNode}
   */
  public abstract ArrayList < RegexNode > lastPos ();


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
