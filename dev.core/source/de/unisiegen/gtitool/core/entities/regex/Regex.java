package de.unisiegen.gtitool.core.entities.regex;


import java.util.HashSet;


/**
 * Representation of a whole Regex
 * 
 * @author Simon Meurer
 */
public class Regex
{

  /**
   * The root node of this regex
   */
  private RegexNode regexNode;


  /**
   * The Constructor for a Regex
   * 
   * @param regexNode The root node of this regex
   */
  public Regex ( RegexNode regexNode )
  {
    this.regexNode = regexNode;
  }


  /**
   * Gets all Nodes in this {@link Regex} recursivly
   * 
   * @param root The root of the Regex
   * @return All Nodes in the regex
   */
  private HashSet < RegexNode > getAllNodes ( RegexNode root )
  {
    HashSet < RegexNode > nodeList = new HashSet < RegexNode > ();
    nodeList.add ( root );
    if ( ! ( root instanceof TokenNode ) )
    {
      for ( RegexNode node : root.getChildren () )
      {
        nodeList.addAll ( getAllNodes ( node ) );
      }
    }
    return nodeList;
  }


  /**
   * The followPos function as defined in the dragon book
   * 
   * @param pos The position
   * @return followPos as defined in the dragon book
   */
  public HashSet < RegexNode > followPos ( int pos )
  {
    HashSet < RegexNode > followPos = new HashSet < RegexNode > ();

    HashSet < RegexNode > nodeList = getAllNodes ( this.regexNode );

    for ( RegexNode node : nodeList )
    {
      if ( node instanceof ConcatenationNode )
      {
        RegexNode n1 = node.getChildren ().get ( 0 );
        boolean foundInLastPosN1 = false;
        for ( RegexNode searchNode : n1.lastPos () )
        {
          if ( searchNode instanceof TokenNode )
          {
            TokenNode tokenNode = ( TokenNode ) searchNode;
            if ( tokenNode.getPosition () == pos )
            {
              foundInLastPosN1 = true;
            }
          }
        }
        if ( foundInLastPosN1 )
        {
          RegexNode n2 = node.getChildren ().get ( 1 );
          followPos.addAll ( n2.firstPos () );
        }
      }
      else if ( node instanceof KleeneNode )
      {
        RegexNode n = node.getChildren ().get ( 0 );
        boolean foundInLastPosN = false;
        for ( RegexNode searchNode : n.lastPos () )
        {
          if ( searchNode instanceof TokenNode )
          {
            TokenNode tokenNode = ( TokenNode ) searchNode;
            if ( tokenNode.getPosition () == pos )
            {
              foundInLastPosN = true;
            }
          }
        }
        if ( foundInLastPosN )
        {
          followPos.addAll ( n.firstPos () );
        }
      }
    }
    return followPos;
  }
}
