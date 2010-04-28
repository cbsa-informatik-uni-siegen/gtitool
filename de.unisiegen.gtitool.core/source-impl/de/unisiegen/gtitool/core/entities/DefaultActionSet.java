package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Implementation of {@link ActionSet}
 */
public class DefaultActionSet implements ActionSet
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = -7941701750133085926L;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(java.lang.Iterable)
   */
  public void add ( Iterable < Action > actions )
  {
    Iterator < Action > iter = actions.iterator ();

    while ( iter.hasNext () )
      this.rep.add ( iter.next () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(de.unisiegen.gtitool.core.entities.Action)
   */
  public void add ( Action action )
  {
    this.rep.add ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#add(de.unisiegen.gtitool.core.entities.Action[])
   */
  public void add ( Action ... actions )
  {
    for ( Action action : actions )
      this.rep.add ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#contains(de.unisiegen.gtitool.core.entities.Action)
   */
  public boolean contains ( Action action )
  {
    return this.rep.contains ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#get()
   */
  public TreeSet < Action > get ()
  {
    return this.rep;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#get(int)
   */
  public Action get ( final int index )
  {
    int idx = index;
    Iterator < Action > action = iterator ();
    while ( action.hasNext () && idx-- >= 1 )
      action.next ();
    return action.next ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#size()
   */
  public int size ()
  {
    return this.rep.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    final PrettyString ret = new PrettyString ();

    ret.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$

    for ( int i = 0 ; i < size () ; ++i )
    {
      ret.add ( get ( i ).toPrettyString () );

      if ( i < size () - 1 )
        ret.add ( new PrettyToken ( ",", Style.NONE ) ); //$NON-NLS-1$
    }

    ret.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( ActionSet o )
  {
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    throw new RuntimeException ( "not yet implemented" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < Action > iterator ()
  {
    return this.rep.iterator ();
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
   * The {@link Action}-{@link TreeSet}
   */
  private TreeSet < Action > rep = new TreeSet < Action > ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ActionSet#first()
   */
  public Action first ()
  {
    return this.rep.first ();
  }
}
