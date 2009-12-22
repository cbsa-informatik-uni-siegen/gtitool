package de.unisiegen.gtitool.core.entities;

import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;



/**
 * TODO
 *
 */
public class LR0ItemSet implements Entity < LR0ItemSet >,
Storable, Modifyable, Iterable < LR0Item >
{
  public LR0ItemSet()
  {
    
  }
  
  public LR0ItemSet(LR0ItemSet items)
  {
    this.rep = new TreeSet<LR0Item>(items.get());
  }
  
  public LR0ItemSet(Iterable<LR0Item> items)
  {
    add(items);
  }

  public void add(Iterable<LR0Item> items)
  {
    for(LR0Item item : items)
      this.rep.add ( item );
  }
  
  public void add(LR0Item item)
  {
    this.rep.add(item);
  }
  
  public int size()
  {
    return this.rep.size ();
  }
  
  public boolean contains(LR0Item item)
  {
    return this.rep.contains ( item );
  }
  
  public TreeSet<LR0Item> get()
  {
    return this.rep;
  }
  
  public final Iterator < LR0Item > iterator ()
  {
    return this.rep.iterator ();
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

  public boolean equals(LR0ItemSet o)
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
  
  private TreeSet<LR0Item> rep = new TreeSet<LR0Item>(); 
}