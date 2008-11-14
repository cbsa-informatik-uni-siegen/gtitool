package de.unisiegen.gtitool.core.regex;


import java.util.HashSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.storage.Modifyable;


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
   * The String the user has typed
   */
  private String regexString;


  /**
   * The Constructor for a {@link DefaultRegex}
   * 
   * @param a The {@link Alphabet} of this Regex
   * @param regexString
   */
  public DefaultRegex ( Alphabet a, String regexString )
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
    this.regexString = regexString;
  }


  /**
   * Sets the {@link RegexNode} initial.
   * 
   * @param regexNode The {@link RegexNode} to set.
   * @param change Indicates if a change was made
   */
  public void setRegexNode ( RegexNode regexNode, boolean change )
  {
    this.regexNode = regexNode;

    int currentPosition = 1;
    for ( RegexNode current : this.regexNode.getTokenNodes () )
    {
      if ( current instanceof LeafNode )
      {
        ( ( LeafNode ) current ).setPosition ( currentPosition );
        currentPosition++ ;
      }
    }
    
    if ( change )
    {
      if ( this.initialNode == null
          || !this.initialNode.equals ( this.regexNode ) )
      {
        fireModifyStatusChanged ( false );
      }
    }
    else
    {
      this.initialNode = this.regexNode;
    }
  }


  /**
   * Changes the {@link RegexNode}
   * 
   * @param newRegexNode The new {@link RegexNode}
   */
  public void changeRegexNode ( RegexNode newRegexNode )
  {
    this.regexNode = newRegexNode;

    int currentPosition = 1;
    for ( RegexNode current : this.regexNode.getTokenNodes () )
    {
      if ( current instanceof LeafNode )
      {
        ( ( LeafNode ) current ).setPosition ( currentPosition );
        currentPosition++ ;
      }
    }
    fireModifyStatusChanged ( false );

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
  public HashSet < RegexNode > followPos ( int pos )
  {
    HashSet < RegexNode > followPos = new HashSet < RegexNode > ();

    HashSet < RegexNode > nodeList = getAllNodes ( this.regexNode );

    for ( RegexNode node : nodeList )
    {
      if ( node instanceof ConcatenationNode )
      {
        RegexNode n1 = node.getAllChildren ().get ( 0 );
        boolean foundInLastPosN1 = false;
        for ( RegexNode searchNode : n1.lastPos () )
        {
          if ( searchNode instanceof LeafNode )
          {
            LeafNode leafNode = ( LeafNode ) searchNode;
            if ( leafNode.getPosition () == pos )
            {
              foundInLastPosN1 = true;
            }
          }
        }
        if ( foundInLastPosN1 )
        {
          RegexNode n2 = node.getAllChildren ().get ( 1 );
          followPos.addAll ( n2.firstPos () );
        }
      }
      else if ( node instanceof KleeneNode )
      {
        RegexNode n = node.getAllChildren ().get ( 0 );
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
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * TODO
   * 
   * @param forceModify
   */
  private void fireModifyStatusChanged ( final boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    if ( this.initialNode == null || this.regexNode == null )
    {
      return true;
    }
    return !this.initialNode.equals ( this.regexNode );
  }


  /**
   * Returns the initialNode.
   * 
   * @return The initialNode.
   * @see #initialNode
   */
  public RegexNode getInitialNode ()
  {
    return this.initialNode;
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
      return this.regexNode.equals ( dr.getRegexNode () );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.initialNode = this.regexNode;
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
   * Sets the regexString.
   * 
   * @param regexString The regexString to set.
   * @see #regexString
   */
  public void setRegexString ( String regexString )
  {
    this.regexString = regexString;
  }
}
