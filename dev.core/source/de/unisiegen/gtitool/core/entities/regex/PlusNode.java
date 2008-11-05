package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * Representation of a Plus in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class PlusNode extends RegexNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -2857280261104753164L;


  /**
   * The {@link RegexNode} in the Plus
   */
  RegexNode content;


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
    this.content = regex;
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
   * @see Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( @SuppressWarnings ( "unused" )
  RegexNode o )
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
      return this.content.equals ( node.content );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    return this.content.firstPos ();
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
    nodes.addAll ( this.content.getAllChildren () );
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
    nodes.add ( this.content );
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
    return this.content.getLeftChildrenCount ();
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
    return this.content.getRightChildrenCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    return this.content.getTokenNodes ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    return this.content.lastPos ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return this.content.nullable ();
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
   * @see RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new ConcatenationNode ( this.content.toCoreSyntax (),
        new KleeneNode ( this.content.toCoreSyntax () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString string = this.content.toPrettyString ();
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
    return this.content.toString () + "+"; //$NON-NLS-1$
  }
}
