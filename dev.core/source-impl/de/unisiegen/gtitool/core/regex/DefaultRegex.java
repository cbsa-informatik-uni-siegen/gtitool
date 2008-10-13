package de.unisiegen.gtitool.core.regex;


import java.util.HashSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;


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
   * List of listeners
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The initial RegexNode
   */
  private RegexNode initialNode;


  /**
   * The root node of this regex
   */
  private RegexNode regexNode;


  /**
   * The alphabet of this {@link Regex}
   */
  private Alphabet alphabet;


  /**
   * The Constructor for a {@link DefaultRegex}
   * 
   * @param a The {@link Alphabet} of this Regex
   */
  public DefaultRegex ( Alphabet a )
  {
    this.alphabet = a;
  }


  /**
   * Sets the {@link RegexNode} initial.
   * 
   * @param regexNode The {@link RegexNode} to set.
   */
  public void setRegexNode ( RegexNode regexNode )
  {
    this.initialNode = regexNode;
    this.regexNode = regexNode;
  }


  /**
   * Changes the {@link RegexNode}
   * 
   * @param newRegexNode The new {@link RegexNode}
   */
  public void changeRegexNode ( RegexNode newRegexNode )
  {
    this.regexNode = newRegexNode;
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
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    if(this.initialNode == null || this.regexNode == null) {
      return true;
    }
    return this.initialNode.equals ( this.regexNode );
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.initialNode = this.regexNode;
  }
}
