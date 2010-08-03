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
 * Represents the {@link LR0ItemSet}
 */
public class LR0ItemSet implements LRItemSet, Entity < LR0ItemSet >, Storable,
    Modifyable, Iterable < LR0Item >
{

  /**
   * The serial
   */
  private static final long serialVersionUID = -7532179608903209213L;


  /**
   * Allocates a new {@link LR0ItemSet}
   */
  public LR0ItemSet ()
  {
    // Nothing
  }


  /**
   * Allocates a new {@link LR0ItemSet}
   * 
   * @param items The {@link LR0ItemSet}
   */
  public LR0ItemSet ( LR0ItemSet items )
  {
    this.rep = new TreeSet < LR0Item > ( items.get () );
  }

  /**
   * 
   * Allocates a new {@link LR0ItemSet}
   *
   * @param items The {@link LR0Item}s
   */
  public LR0ItemSet ( Iterable < LR0Item > items )
  {
    add ( items );
  }


  /**
   * Adds the specified list of {@link LR0Item}s
   * 
   * @param items The {@link LR0Item}s
   */
  public void add ( Iterable < LR0Item > items )
  {
    for ( LR0Item item : items )
      this.rep.add ( item );
  }


  /**
   * Adds the specified {@link LR0Item}
   * 
   * @param item The {@link LR0Item}
   */
  public void add ( LR0Item item )
  {
    this.rep.add ( item );
  }


  /**
   * Returns the number of {@link LR0Item}s
   * 
   * @return the number of {@link LR0Item}s
   */
  public int size ()
  {
    return this.rep.size ();
  }


  /**
   * Checks if the specified {@link LR0Item} is already in this list
   * 
   * @param item The {@link LR0Item}
   * @return true if the item is already in the list, false otherwise
   */
  public boolean contains ( LR0Item item )
  {
    return this.rep.contains ( item );
  }


  public TreeSet < LR0Item > get ()
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


  public boolean equals ( LR0ItemSet o )
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
  public int compareTo ( LR0ItemSet o )
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


  @Override
  public String toString ()
  {
    return this.rep.toString ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRItemSet#stringEntries()
   */
  public ArrayList < PrettyString > stringEntries ()
  {
    ArrayList < PrettyString > ret = new ArrayList < PrettyString > ();

    for ( LR0Item item : this.rep )
      ret.add ( item.toPrettyString () );

    return ret;
  }


  public ArrayList < LRItem > baseList ()
  {
    final ArrayList < LRItem > ret = new ArrayList < LRItem > ();
    for ( LR0Item item : this.rep )
      ret.add ( item );

    return ret;
  }


  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( toString (), Style.NONE ) );
  }


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < LR0Item > iterator ()
  {
    return this.rep.iterator ();
  }


  private TreeSet < LR0Item > rep = new TreeSet < LR0Item > ();
}
