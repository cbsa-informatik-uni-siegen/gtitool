package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.storage.Storable;


/**
 * Abstract class for a Node in a Regex
 * 
 * @author Simon Meurer
 * @version
 */
public abstract class RegexNode implements Storable
{

  /**
   * Gets all Children of this Node
   * 
   * @return All children of this node
   */
  public abstract ArrayList < RegexNode > getChildren ();


  /**
   * Gets all Tokennodes that are in this node
   * 
   * @return All Tokennodes that are in this node
   */
  public abstract ArrayList < RegexNode > getTokenNodes ();


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

}
