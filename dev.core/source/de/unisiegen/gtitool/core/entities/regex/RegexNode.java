package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * Abstract class for a Node in a Regex
 * 
 * @author Simon Meurer
 * @version
 */
public abstract class RegexNode implements Entity < RegexNode >, Storable
{

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

  public abstract int getRightChildrenCount();
  public abstract int getLeftChildrenCount();


  /**
   * Gets all Tokennodes that are in this node
   * 
   * @return All Tokennodes that are in this node
   */
  public abstract ArrayList < TokenNode > getTokenNodes ();


  /**
   * Function nullable as defined in the dragonbook
   * 
   * @return true, if the node can be epsilon
   */
  public abstract boolean nullable ();


  /**
   * Function firstpos as defined in the dragonbook
   * 
   * @return {@link ArrayList} of {@link RegexNode} that can be the first
   *         positions of the {@link RegexNode}
   */
  public abstract ArrayList < RegexNode > firstPos ();


  /**
   * Function lastpos as defined in the dragonbook
   * 
   * @return {@link ArrayList} of {@link RegexNode} that can be the last
   *         positions of the {@link RegexNode}
   */
  public abstract ArrayList < RegexNode > lastPos ();


  /**
   * Translate the RegexNode to Core Syntax
   * 
   * @return the RegexNode in Core Syntax
   */
  public abstract RegexNode toCoreSyntax ();


  /**
   * Get the {@link PrettyString} for the Node in the JGTIGraph
   * 
   * @return The {@link PrettyString} for the Node in the JGTIGraph
   */
  public abstract PrettyString getNodeString ();

}
