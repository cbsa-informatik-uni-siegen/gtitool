package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.util.ObjectPair;


/**
 * Representation of a Plus in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class PlusNode extends OneChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -2857280261104753164L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link PlusNode} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link PlusNode}
   * 
   * @param regex The {@link RegexNode} in the Plus
   */
  public PlusNode ( RegexNode regex )
  {
    super ( regex );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#clone()
   */
  @Override
  public RegexNode clone ()
  {
    return new PlusNode ( this.regex.clone () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( @SuppressWarnings ( "unused" ) RegexNode o )
  {
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj == this )
    {
      return true;
    }
    if ( obj instanceof PlusNode )
    {
      PlusNode node = ( PlusNode ) obj;
      return this.regex.equals ( node.regex );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#followPos()
   */
  @Override
  public HashSet < ObjectPair < LeafNode, LeafNode >> followPos ()
  {
    HashSet < ObjectPair < LeafNode, LeafNode >> result = new HashSet < ObjectPair < LeafNode, LeafNode > > ();
    result.addAll ( new KleeneNode ( this.regex ).followPos () );
    for ( LeafNode last : this.regex.lastPos () )
    {
      for ( LeafNode first : this.regex.firstPos () )
      {
        result.add ( new ObjectPair < LeafNode, LeafNode > ( last, first ) );
      }
    }
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.addAll ( this.regex.getAllChildren () );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this.regex );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getLeftChildrenCount()
   */
  @Override
  public int getLeftChildrenCount ()
  {
    return this.regex.getLeftChildrenCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    return new PrettyString ( new PrettyToken ( "+", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getRightChildrenCount()
   */
  @Override
  public int getRightChildrenCount ()
  {
    return this.regex.getRightChildrenCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    return this.regex.getTokenNodes ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#isInCoreSyntax()
   */
  @Override
  public boolean isInCoreSyntax ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return this.regex.nullable ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#setParserOffset(ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax(boolean)
   */
  @Override
  public RegexNode toCoreSyntax ( boolean withCharacterClasses )
  {
    ConcatenationNode con = new ConcatenationNode ( this.regex
        .toCoreSyntax ( withCharacterClasses ), new KleeneNode ( this.regex
        .toCoreSyntax ( withCharacterClasses ) ) );
    return con;
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString string = new PrettyString ();

    string.add ( this.regex.toPrettyString () );
    string
        .add ( new PrettyString ( new PrettyToken ( "+", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$

    return string;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.regex.toString () + "+"; //$NON-NLS-1$
  }
}
