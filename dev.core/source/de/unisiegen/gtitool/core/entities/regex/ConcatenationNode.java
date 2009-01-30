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
 * Representation of a Concatenation in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class ConcatenationNode extends TwoChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 8972113015715049815L;


  /**
   * Cached {@link ArrayList} for firstPos
   */
  private transient ArrayList < LeafNode > firstPosCache = null;


  /**
   * Cached {@link ArrayList} for lastPos
   */
  private transient ArrayList < LeafNode > lastPosCache = null;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link ConcatenationNode} in the source code.
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link ConcatenationNode}
   * 
   * @param regex1 First element of the {@link ConcatenationNode}
   * @param regex2 Second element of the {@link ConcatenationNode}
   */
  public ConcatenationNode ( RegexNode regex1, RegexNode regex2 )
  {
    super ( regex1, regex2 );
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
    return new ConcatenationNode ( this.regex1.clone (), this.regex2.clone () );
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
   * Counts the left Children of the {@link ConcatenationNode}
   * 
   * @return The leftChildren count
   */
  public int countLeftChildren ()
  {
    return 1 + this.regex1.getAllChildren ().size ();
  }


  /**
   * Counts the right Children of the {@link ConcatenationNode}
   * 
   * @return The rightChildren count
   */
  public int countRightChildren ()
  {
    return 1 + this.regex2.getAllChildren ().size ();
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
    if ( obj instanceof ConcatenationNode )
    {
      ConcatenationNode con = ( ConcatenationNode ) obj;
      return this.regex1.equals ( con.regex1 )
          && this.regex2.equals ( con.regex2 );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#firstPos()
   */
  @Override
  public ArrayList < LeafNode > firstPos ()
  {
    if ( this.firstPosCache == null )
    {
      if ( !this.regex1.nullable () )
      {
        this.firstPosCache = this.regex1.firstPos ();
        return this.firstPosCache;
      }
      this.firstPosCache = new ArrayList < LeafNode > ();
      this.firstPosCache.addAll ( this.regex1.firstPos () );
      this.firstPosCache.addAll ( this.regex2.firstPos () );
    }
    return this.firstPosCache;
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
    nodes.add ( this.regex1 );
    nodes.add ( this.regex2 );
    nodes.addAll ( this.regex1.getAllChildren () );
    nodes.addAll ( this.regex2.getAllChildren () );
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
    return 1 + this.regex1.getAllChildren ().size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    return new PrettyString ( new PrettyToken ( "·", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
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
    return 1 + this.regex2.getAllChildren ().size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    ArrayList < LeafNode > nodes = new ArrayList < LeafNode > ();
    nodes.addAll ( this.regex1.getTokenNodes () );
    nodes.addAll ( this.regex2.getTokenNodes () );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#lastPos()
   */
  @Override
  public ArrayList < LeafNode > lastPos ()
  {
    if ( this.lastPosCache == null )
    {
      if ( !this.regex2.nullable () )
      {
        this.lastPosCache = this.regex2.lastPos ();
        return this.lastPosCache;
      }
      this.lastPosCache = new ArrayList < LeafNode > ();
      this.lastPosCache.addAll ( this.regex1.lastPos () );
      this.lastPosCache.addAll ( this.regex2.lastPos () );
    }
    return this.lastPosCache;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return this.regex1.nullable () && this.regex2.nullable ();
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
   * /** {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax(boolean)
   */
  @Override
  public RegexNode toCoreSyntax ( boolean withCharacterClasses )
  {
    ConcatenationNode con = new ConcatenationNode ( this.regex1
        .toCoreSyntax ( withCharacterClasses ), this.regex2
        .toCoreSyntax ( withCharacterClasses ) );
    con.setBraces ( this.braces );
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
    if ( this.braces )
    {
      string.add ( new PrettyToken ( "(", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
    }
    string.add ( this.regex1.toPrettyString () );
    string
        .add ( new PrettyString ( new PrettyToken ( "·", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$
    string.add ( this.regex2.toPrettyString () );

    if ( this.braces )
    {
      string.add ( new PrettyToken ( ")", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
    }
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
    return this.regex1.toString () + this.regex2.toString ();
  }

}
