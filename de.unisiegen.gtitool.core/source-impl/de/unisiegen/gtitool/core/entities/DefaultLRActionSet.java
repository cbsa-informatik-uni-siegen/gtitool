package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * TODO
 */
public class DefaultLRActionSet implements ActionSet
{

  /**
   * TODO
   * 
   * @param actions
   * @throws ActionSetException
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(java.lang.Iterable)
   */
  public void add ( Iterable < Action > actions ) throws ActionSetException
  {
    Iterator < Action > iter = actions.iterator ();

    while ( iter.hasNext () )
      this.rep.add ( iter.next () );
  }


  /**
   * TODO
   * 
   * @param actions
   * @throws ActionSetException
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(de.unisiegen.gtitool.core.entities.Action)
   */
  public void add ( Action action ) throws ActionSetException
  {
    this.rep.add ( action );
  }


  /**
   * TODO
   * 
   * @param actions
   * @throws ActionSetException
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(de.unisiegen.gtitool.core.entities.Action[])
   */
  public void add ( Action ... actions )
  {
    for ( Action action : actions )
      this.rep.add ( action );
  }


  /**
   * Check if the action is an element of this set
   * 
   * @param action
   * @return if the element is found
   * @see de.unisiegen.gtitool.core.entities.ActionSet#contains(de.unisiegen.gtitool.core.entities.Action)
   */
  public boolean contains ( Action action )
  {
    return this.rep.contains ( action );
  }


  /**
   * Get the underlying tree set
   * 
   * @return the underlying tree set
   * @see de.unisiegen.gtitool.core.entities.ActionSet#get()
   */
  public TreeSet < Action > get ()
  {
    return this.rep;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.ActionSet#size()
   */
  public int size ()
  {
    return this.rep.size ();
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
  public int compareTo ( ActionSet o )
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
  public Iterator < Action > iterator ()
  {
    return this.rep.iterator ();
  }


  public String toString ()
  {
    return this.rep.toString ();
  }


  /**
   * TODO
   */
  private TreeSet < Action > rep = new TreeSet < Action > ();


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.ActionSet#first()
   */
  public Action first ()
  {
    return this.rep.first ();
  }

}
