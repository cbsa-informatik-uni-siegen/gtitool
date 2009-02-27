package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * TODO
 */
public class UnfinishedNode extends LeafNode
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7119354254164008482L;


  private int position;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.LeafNode#getPosition()
   */
  @Override
  public int getPosition ()
  {
    return this.position;
  }


  /**
   * TODO
   * 
   * @param p
   * @see de.unisiegen.gtitool.core.entities.regex.LeafNode#setPosition(int)
   */
  @Override
  public void setPosition ( int p )
  {
    this.position = p;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#clone()
   */
  @Override
  public RegexNode clone ()
  {
    return new UnfinishedNode ( this.s0, this.s1, this.k );
  }


  private String name;


  private State s0;


  private State s1;


  private int k;


  /**
   * TODO
   */
  public UnfinishedNode ( State s0, State s1, int k )
  {
    this.s0 = s0;
    this.s1 = s1;
    this.k = k;
    this.name = "L" + s0.getName () + s1.getName () + k;
  }

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getNextUnfinishedNode()
   */
  @Override
  public UnfinishedNode getNextUnfinishedNode ()
  {
    return this;
  }
  
  
  /**
   * Returns the s0.
   *
   * @return The s0.
   * @see #s0
   */
  public State getS0 ()
  {
    return this.s0;
  }
  
  /**
   * Returns the s1.
   *
   * @return The s1.
   * @see #s1
   */
  public State getS1 ()
  {
    return this.s1;
  }
  
  /**
   * Returns the k.
   *
   * @return The k.
   * @see #k
   */
  public int getK ()
  {
    return this.k;
  }

  /**
   * TODO
   * 
   * @param o
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object o )
  {
    if ( o == this )
    {
      return true;
    }
    if ( o instanceof UnfinishedNode )
    {
      return this.name.equals ( ( ( UnfinishedNode ) o ).getName () );
    }
    return false;
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public String getName ()
  {
    return this.name;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    return new ArrayList < RegexNode > ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    return new ArrayList < RegexNode > ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getLeftChildrenCount()
   */
  @Override
  public int getLeftChildrenCount ()
  {
    return 0;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    PrettyString s = new PrettyString ();
    s.add ( new PrettyToken ( this.name, Style.REGEX_SYMBOL ) );
    return s;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getRightChildrenCount()
   */
  @Override
  public int getRightChildrenCount ()
  {
    return 0;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#isInCoreSyntax()
   */
  @Override
  public boolean isInCoreSyntax ()
  {
    return true;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @param withCharacterClasses
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax(boolean)
   */
  @Override
  public RegexNode toCoreSyntax (
      @SuppressWarnings ( "unused" ) boolean withCharacterClasses )
  {
    return this;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * The offset of this {@link TokenNode} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * TODO
   * 
   * @param parserOffset
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
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
   * @see PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString s = new PrettyString ();
    s.add ( new PrettyToken ( this.name, Style.REGEX_SYMBOL ) );
    return s;
  }
  
  /**
   * TODO
   *
   * @return
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.name;
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( RegexNode o )
  {
    return 0;
  }

}
