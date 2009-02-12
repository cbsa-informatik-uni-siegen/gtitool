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
 * Representation of a Kleene Closure in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class KleeneNode extends OneChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 1206649719571638011L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link KleeneNode} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link KleeneNode}
   * 
   * @param content The {@link RegexNode} of the Kleene Closure
   */
  public KleeneNode ( RegexNode content )
  {
    super ( content );
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
    return new KleeneNode ( this.regex.clone () );
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
    if ( obj instanceof KleeneNode )
    {
      KleeneNode node = ( KleeneNode ) obj;
      return this.regex.equals ( node.regex );
    }
    return false;
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
    return new PrettyString ( new PrettyToken ( "*", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
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
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return true;
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
    KleeneNode k = new KleeneNode ( this.regex
        .toCoreSyntax ( withCharacterClasses ) );
    return k;
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString string = new PrettyString ();
    if ( this.regex.getPriority () != 5 )
    {
      string.add ( new PrettyToken ( "(", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
    }
    string.add ( this.regex.toPrettyString () );
    if ( this.regex.getPriority () != 5 )
    {
      string.add ( new PrettyToken ( ")", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
    }
    string
        .add ( new PrettyString ( new PrettyToken ( "*", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$

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
    return this.regex.toString () + "*"; //$NON-NLS-1$
  }
}
