package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * TODO
 */
public class LR1ItemSet implements LRItemSet, Entity < LR1ItemSet >, Storable,
    Modifyable, Iterable < LR1Item >
{

  public LR1ItemSet ()
  {

  }


  public LR1ItemSet ( LR1ItemSet items )
  {
    this.rep = new TreeSet < LR1Item > ( items.get () );
  }


  public LR1ItemSet ( Iterable < LR1Item > items )
  {
    add ( items );
  }


  public void add ( Iterable < LR1Item > items )
  {
    for ( LR1Item item : items )
      this.rep.add ( item );
  }


  public void add ( LR1Item item )
  {
    this.rep.add ( item );
  }


  public int size ()
  {
    return this.rep.size ();
  }


  public boolean contains ( LR1Item item )
  {
    return this.rep.contains ( item );
  }


  public TreeSet < LR1Item > get ()
  {
    return this.rep;
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


  public boolean equals ( LR1ItemSet o )
  {
    return this.rep.equals ( o.rep );
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( LR1ItemSet o )
  {
    return 0;
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
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
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
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
  }


  public String toString ()
  {
    return this.rep.toString ();
  }


  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( toString (), Style.NONE ) );
  }


  private TreeSet < LR1Item > rep = new TreeSet < LR1Item > ();


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRItemSet#stringEntries()
   */
  public ArrayList < PrettyString > stringEntries ()
  {
    ArrayList < PrettyString > ret = new ArrayList < PrettyString > ();

    for ( LR1Item item : this.rep )
      ret.add ( item.toPrettyString () );

    return ret;
  }


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < LR1Item > iterator ()
  {
    return this.rep.iterator ();
  }
}
