package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.lractionset.LRActionSetException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * TODO
 */
public class DefaultLRActionSet implements LRActionSet
{

  /**
   * TODO
   * 
   * @param actions
   * @throws LRActionSetException
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#add(java.lang.Iterable)
   */
  public void add ( Iterable < LRAction > actions ) throws LRActionSetException
  {
    Iterator < LRAction > iter = actions.iterator ();

    while ( iter.hasNext () )
      this.rep.add ( iter.next () );
  }


  /**
   * TODO
   * 
   * @param actions
   * @throws LRActionSetException
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#add(de.unisiegen.gtitool.core.entities.LRAction)
   */
  public void add ( LRAction action ) throws LRActionSetException
  {
    this.rep.add ( action );
  }


  /**
   * TODO
   * 
   * @param actions
   * @throws LRActionSetException
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#add(de.unisiegen.gtitool.core.entities.LRAction[])
   */
  public void add ( LRAction ... actions )
  {
    for ( LRAction action : actions )
      this.rep.add ( action );
  }


  /**
   * TODO
   * 
   * @param action
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#contains(de.unisiegen.gtitool.core.entities.LRAction)
   */
  public boolean contains ( LRAction action )
  {
    return this.rep.contains ( action );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#get()
   */
  public TreeSet < LRAction > get ()
  {
    return this.rep;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.LRActionSet#size()
   */
  public int size ()
  {
    return rep.size ();
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
  public int compareTo ( LRActionSet o )
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


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < LRAction > iterator ()
  {
    return this.rep.iterator ();
  }


  private TreeSet < LRAction > rep = new TreeSet < LRAction > ();

}
