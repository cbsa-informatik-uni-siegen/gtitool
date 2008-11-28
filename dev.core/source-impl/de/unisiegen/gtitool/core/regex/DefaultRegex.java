package de.unisiegen.gtitool.core.regex;


import java.util.HashSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;


/**
 * 
 */
public class DefaultRegex implements Regex
{

  /**
   * Signals the regex type.
   * 
   * @author Simon Meurer
   */
  public enum RegexType implements EntityType
  {
    /**
     * Regex
     */
    REGEX;

    /**
     * The file ending.
     * 
     * @return The file ending.
     */
    public final String getFileEnding ()
    {
      return toString ().toLowerCase ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case REGEX :
        {
          return "REGEX"; //$NON-NLS-1$
        }
      }
      throw new IllegalArgumentException ( "unsupported regex type" ); //$NON-NLS-1$
    }
  }


  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 5624201944777732080L;


  /**
   * The root node of this regex
   */
  private RegexNode regexNode;


  /**
   * The alphabet of this {@link Regex}
   */
  private Alphabet alphabet;


  /**
   * The String the user has typed
   */
  private String regexString;


  /**
   * The Constructor for a {@link DefaultRegex}
   * 
   * @param a The {@link Alphabet} of this Regex
   */
  public DefaultRegex ( Alphabet a )
  {
    this.alphabet = a;

    try
    {
      if ( !this.alphabet.contains ( new DefaultSymbol ( "#" ) ) ) //$NON-NLS-1$
      {
        this.alphabet.add ( new DefaultSymbol ( "#" ) ); //$NON-NLS-1$
      }
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * Sets the {@link RegexNode} initial.
   * 
   * @param regexNode The {@link RegexNode} to set.
   * @param regexString The new {@link String} for the regex
   */
  public void setRegexNode ( RegexNode regexNode, String regexString )
  {
    this.regexNode = regexNode;
    this.regexString = regexString;

    int currentPosition = 1;
    for ( LeafNode current : this.regexNode.getTokenNodes () )
    {
      current.setPosition ( currentPosition );
      currentPosition++ ;
    }
  }


  /**
   * Gets the Symbol at Position in the Regex
   *
   * @param position The position
   * @return The Symbol at Position in the Regex or null if position is empty
   */
  public String symbolAtPosition ( int position )
  {
    String s = null;
    for ( LeafNode current : this.regexNode.getTokenNodes () )
    {
      if ( current.getPosition () == position )
      {
        s = current.getNodeString ().toString ();
      }
    }
    return s;
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
      for ( RegexNode node : root.getAllChildren () )
      {
        nodeList.addAll ( getAllNodes ( node ) );
      }
    }
    return nodeList;
  }


  /**
   * Returns the regexNode.
   * 
   * @return The regexNode.
   * @see #regexNode
   */
  public RegexNode getRegexNode ()
  {
    return this.regexNode;
  }


  /**
   * The followPos function as defined in the dragon book
   * 
   * @param pos The position
   * @return followPos as defined in the dragon book
   */
  public HashSet < Integer > followPos ( int pos )
  {
    HashSet < Integer > followPos = new HashSet < Integer > ();

    HashSet < RegexNode > nodeList = getAllNodes ( this.regexNode );

    for ( RegexNode node : nodeList )
    {
      if ( node instanceof ConcatenationNode )
      {
        RegexNode n1 = node.getChildren ().get ( 0 );
        boolean foundPosInLastPosN1 = false;
        for ( RegexNode searchNode : n1.lastPos () )
        {
          if ( searchNode instanceof LeafNode )
          {
            LeafNode leafNode = ( LeafNode ) searchNode;
            if ( leafNode.getPosition () == pos )
            {
              foundPosInLastPosN1 = true;
            }
          }
        }
        if ( foundPosInLastPosN1 )
        {
          RegexNode n2 = node.getChildren ().get ( 1 );
          for ( RegexNode firstPosNode : n2.firstPos () )
          {
            followPos.add ( new Integer ( ( ( LeafNode ) firstPosNode )
                .getPosition () ) );
          }
        }
      }
      else if ( node instanceof KleeneNode )
      {
        RegexNode n = node.getChildren ().get ( 0 );
        boolean foundInLastPosN = false;
        for ( RegexNode searchNode : n.lastPos () )
        {
          if ( searchNode instanceof LeafNode )
          {
            LeafNode leafNode = ( LeafNode ) searchNode;
            if ( leafNode.getPosition () == pos )
            {
              foundInLastPosN = true;
            }
          }
        }
        if ( foundInLastPosN )
        {
          for ( RegexNode firstPosNode : n.firstPos () )
          {
            followPos.add ( new Integer ( ( ( LeafNode ) firstPosNode )
                .getPosition () ) );
          }
        }
      }
    }
    return followPos;
  }


  /**
   * Returns the alphabet.
   * 
   * @return The alphabet.
   * @see #alphabet
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj == this )
    {
      return true;
    }
    if ( obj instanceof DefaultRegex )
    {
      DefaultRegex dr = ( DefaultRegex ) obj;
      if ( this.regexString == null )
      {
        return dr.getRegexString () != null;
      }
      return this.regexString.equals ( dr.getRegexString () )
          && this.regexNode.equals ( dr.getRegexNode () );
    }
    return false;
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.regexNode.toString ();
  }


  /**
   * Returns the regexString.
   * 
   * @return The regexString.
   * @see #regexString
   */
  public String getRegexString ()
  {
    return this.regexString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public DefaultRegex clone ()
  {
    DefaultRegex r = new DefaultRegex ( this.alphabet );
    if ( this.regexNode != null && this.regexString != null )
    {
      r.setRegexNode ( getRegexNode (), getRegexString () );
    }
    return r;
  }
}
