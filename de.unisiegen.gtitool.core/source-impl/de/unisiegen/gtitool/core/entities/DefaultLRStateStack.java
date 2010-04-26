package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;

import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * TODO
 */
public class DefaultLRStateStack implements LRStateStack
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -3414231218661954960L;


  /**
   * Constructs an empty stack
   */
  public DefaultLRStateStack ()
  {
    // do nothing
  }


  /**
   * Copies the given stack
   * 
   * @param stack
   */
  public DefaultLRStateStack ( final LRStateStack stack )
  {
    for ( LRState state : stack )
      this.rep.push ( state );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRStateStack#iterator()
   */
  public Iterator < LRState > iterator ()
  {
    return this.rep.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.LRStateStack#pop()
   */
  public LRState pop ()
  {
    return this.rep.pop ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.LRStateStack#push(de.unisiegen.gtitool.core.entities.LRState)
   */
  public void push ( final LRState state )
  {
    this.rep.push ( state );
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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( toString (), Style.NONE ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.rep.toString ();
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( LRStateStack o )
  {
    return 0;
  }


  /**
   * The internal representation
   */
  private java.util.Stack < LRState > rep = new java.util.Stack < LRState > ();


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.entities.LRStateStack#clear()
   */
  public void clear ()
  {
    this.rep.clear ();
  }
}
