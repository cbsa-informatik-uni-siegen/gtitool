package de.unisiegen.gtitool.core.regex;


import java.util.HashSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * 
 */
public class DefaultRegex implements Regex, Storable
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
    this.regexString = regexString;
  }


  /**
   * TODO
   * 
   * @param e
   * @param regexString
   */
  public DefaultRegex ( Element e, String regexString )
  {
    this.regexString = regexString;
  }


  /**
   * Sets the {@link RegexNode} initial.
   * 
   * @param regexNode The {@link RegexNode} to set.
   */
  public void setRegexNode ( RegexNode regexNode, boolean change )
  {
    this.regexNode = new ConcatenationNode ( regexNode, new TokenNode ( "#" ) );
    this.initialNode = regexNode;
    
    if(change && !this.initialNode.equals ( this.regexNode )) {
      fireModifyStatusChanged ( true );
    }
    
    int currentPosition = 1;
    for ( RegexNode current : this.regexNode.getTokenNodes () )
    {
      if ( current instanceof LeafNode )
      {
        ( ( LeafNode ) current ).setPosition ( currentPosition );
        currentPosition++ ;
      }
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

    if(!this.initialNode.equals ( this.regexNode )) {
      fireModifyStatusChanged ( true );
    }
    
    int currentPosition = 1;
    for ( RegexNode current : this.regexNode.getTokenNodes () )
    {
      if ( current instanceof LeafNode )
      {
        ( ( LeafNode ) current ).setPosition ( currentPosition );
        currentPosition++ ;
      }
    }
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


  private void fireModifyStatusChanged ( boolean forceModify )
  {
    System.err.println ("lala");
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
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


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param parserOffset
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( Regex o )
  {
    return 0;
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
