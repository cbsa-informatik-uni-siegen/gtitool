package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
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
   * TODO
   * 
   * @param o
   * @return
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


  public abstract int getHeight ();

  /**
   * Counts the left children
   * 
   * @return the left children count
   */
  public abstract int getLeftChildrenCount ();


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
    PrettyString ps = new PrettyString ();
    ps.add ( new PrettyToken ( "Nullable: ", Style.REGEX_TOOL_TIP_TEXT ) );
    ps.add ( new PrettyToken ( "" + nullable (), Style.REGEX_POSITION ) );
    ps.add ( new PrettyToken ( ", Firstpos: {", Style.REGEX_TOOL_TIP_TEXT ) );
    int i = 0;
    for ( RegexNode current : firstPos () )
    {
      if ( i > 0 )
      {
        i++ ;
        ps.add ( new PrettyToken ( ";", Style.REGEX_TOOL_TIP_TEXT ) );
      }
      if ( current instanceof LeafNode )
      {
        ps
            .add ( new PrettyToken ( ""
                + ( ( LeafNode ) current ).getPosition (), Style.REGEX_POSITION ) );
      }
    }
    ps.add ( new PrettyToken ( "}, Lastpos: {", Style.REGEX_TOOL_TIP_TEXT ) );
    i = 0;
    for ( RegexNode current : lastPos () )
    {
      if ( i > 0 )
      {
        i++ ;
        ps.add ( new PrettyToken ( ";", Style.REGEX_TOOL_TIP_TEXT ) );
      }
      if ( current instanceof LeafNode )
      {
        ps
            .add ( new PrettyToken ( ""
                + ( ( LeafNode ) current ).getPosition (), Style.REGEX_POSITION ) );
      }
    }

    ps.add ( new PrettyToken ( "}", Style.REGEX_TOOL_TIP_TEXT ) );
    return ps;
  }


  /**
   * Get the maximal Width of the whole Regex
   * 
   * @return The maximal Width of the wohle Regex
   */
  public abstract int getWidth ();


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
   * Translate the RegexNode to Core Syntax
   * 
   * @return the RegexNode in Core Syntax
   */
  public abstract RegexNode toCoreSyntax ();
  
  public abstract DefaultENFA toNFA(Alphabet a) throws StateException;

  public abstract boolean isInCoreSyntax();
}
