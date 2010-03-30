package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.entities.listener.ProductionSetChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * represents a set of productions
 */
public class DefaultProductionSet implements ProductionSet
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 3805646543083015563L;


  /**
   * the {@link Production}s
   */
  private ArrayList < Production > prods = new ArrayList < Production > ();


  /**
   * the {@link PrettyString}
   */
  private PrettyString prettyString = null;


  /**
   * indicates modification
   */
  private boolean modified = false;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * allocates a new {@link DefaultProductionSet}
   */
  public DefaultProductionSet ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#add(de.unisiegen.gtitool.core.entities.Production)
   */
  public boolean add ( Production p )
  {
    for ( Production old : this.prods )
      if ( old.equals ( p ) )
        return false;

    if ( this.prods.add ( p ) )
      fireChanged ();
    return this.modified;
  }


  /**
   * allocates a new {@link DefaultProductionSet}
   * 
   * @param prods the initial {@link Production}s
   */
  public DefaultProductionSet ( final Iterable < Production > prods )
  {
    if ( prods == null )
      throw new NullPointerException ( "prods is null" ); //$NON-NLS-1$
    add ( prods );
    this.modified = false;
  }


  /**
   * Construct a DefaultProductionSet from an XML node
   * 
   * @param element
   * @throws StoreException
   */
  public DefaultProductionSet ( final Element element ) throws StoreException
  {
    for ( Element prod : element.getElement () )
      add ( new DefaultProduction ( prod ) );
  }


  /**
   * Updates all listeners
   */
  private void fireChanged ()
  {
    fireModifyStatusChanged ();
    fireProductionSetChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#add(java.lang.Iterable)
   */
  public boolean add ( Iterable < Production > nprods )
  {
    for ( Production p : nprods )
      add ( p );
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#add(de.unisiegen.gtitool.core.entities.Production[])
   */
  public boolean add ( final Production ... nprods )
  {
    return add ( nprods );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#add(int,
   *      de.unisiegen.gtitool.core.entities.Production)
   */
  public boolean add ( int index, Production production )
  {
    this.prods.add ( index, production );

    fireChanged ();

    return this.modified;
  }


  /**
   * {@inheritDoc}
   */
  public boolean add ( final int index,
      final ArrayList < Production > productions )
  {
    this.prods.addAll ( index, productions );

    fireChanged ();

    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#add(int,
   *      de.unisiegen.gtitool.core.entities.ProductionSet)
   */
  public boolean add ( int index, ProductionSet set )
  {
    return add ( index, ( ArrayList < Production > ) set.getRep () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#getRep()
   */
  public Collection < Production > getRep ()
  {
    return this.prods;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#clear()
   */
  public void clear ()
  {
    this.prods.clear ();
    fireModifyStatusChanged ();
    fireProductionSetChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#remove(de.unisiegen.gtitool.core.entities.Production)
   */
  public boolean remove ( Production p )
  {
    if ( this.prods.remove ( p ) )
    {
      fireModifyStatusChanged ();
      fireProductionSetChanged ();
    }
    return this.modified;
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   */
  private final void fireProductionSetChanged ()
  {
    ProductionSetChangedListener [] listeners = this.listenerList
        .getListeners ( ProductionSetChangedListener.class );
    for ( ProductionSetChangedListener current : listeners )
      current.productionSetChanged ( this );
  }


  /**
   * Let the listeners know that the modify status has changed.
   */
  private final void fireModifyStatusChanged ()
  {
    this.modified = true;
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    for ( ModifyStatusChangedListener current : listeners )
      current.modifyStatusChanged ( this.modified );
  }


  /**
   * Let the listeners know that the {@link PrettyString} has changed.
   */
  private final void firePrettyStringChanged ()
  {
    this.prettyString = null;

    PrettyStringChangedListener [] listeners = this.listenerList
        .getListeners ( PrettyStringChangedListener.class );
    for ( PrettyStringChangedListener current : listeners )
      current.prettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#contains(de.unisiegen.gtitool.core.entities.Production)
   */
  public boolean contains ( Production p )
  {
    return this.prods.contains ( p );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#isEmpty()
   */
  public boolean isEmpty ()
  {
    return this.prods.isEmpty ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#size()
   */
  public int size ()
  {
    return this.prods.size ();
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
  }


  /**
   * TODO
   * 
   * @param listener
   */
  public void addProductionSetChangedListener (
      ProductionSetChangedListener listener )
  {
    this.listenerList.add ( ProductionSetChangedListener.class, listener );
  }


  /**
   * TODO
   * 
   * @param listener
   */
  public void removeProductionSetChangedListener (
      ProductionSetChangedListener listener )
  {
    this.listenerList.remove ( ProductionSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    if ( this.prettyString == null )
    {
      this.prettyString = new PrettyString ();
      this.prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
      if ( !isEmpty () )
      {
        Iterator < Production > p = iterator ();
        this.prettyString.add ( p.next () );
        while ( p.hasNext () )
        {
          this.prettyString.add ( new PrettyToken ( ",", Style.NONE ) ); //$NON-NLS-1$
          this.prettyString.add ( p.next () );
        }
      }
      else
        this.prettyString.add ( new PrettyToken ( " ", Style.NONE ) ); //$NON-NLS-1$
      this.prettyString.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
    }
    return this.prettyString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    StringBuilder sb = new StringBuilder ();
    sb.append ( "{" ); //$NON-NLS-1$
    if ( !isEmpty () )
    {
      Iterator < Production > p = iterator ();
      sb.append ( p.next ().toString () );
      while ( p.hasNext () )
        sb.append ( "," + p.next ().toString () ); //$NON-NLS-1$
    }
    else
      sb.append ( " " ); //$NON-NLS-1$
    sb.append ( "}" ); //$NON-NLS-1$
    return sb.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    Element newElement = new Element ( "ProductionSet" ); //$NON-NLS-1$
    for ( Production current : this.prods )
      newElement.addElement ( current );
    return newElement;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.modified = false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < Production > iterator ()
  {
    return this.prods.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#get(int)
   */
  public Production get ( final int index )
  {
    return this.prods.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#remove(int)
   */
  public void remove ( final int index )
  {
    remove ( get ( index ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#remove(de.unisiegen.gtitool.core.entities.Production[])
   */
  public boolean remove ( Production ... p )
  {
    return remove ( p );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionSet#remove(java.lang.Iterable)
   */
  public boolean remove ( Iterable < Production > p )
  {
    boolean ret = false;
    for ( Production prod : p )
      ret = ret || remove ( prod );
    return ret;
  }


  /**
   * Compares one set to another, forming a strict weak ordering
   * 
   * @param o
   * @return Smaller sets are "before" larger sets, larger sets are "after"
   *         smaller sets. Sets with the same size are compared element by
   *         element.
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( ProductionSet o )
  {
    if ( size () < o.size () )
      return -1;
    if ( size () > o.size () )
      return 1;
    for ( int i = 0 ; i < size () ; ++i )
    {
      final int comp = get ( i ).compareTo ( o.get ( i ) );

      if ( comp != 0 )
        return comp;
    }

    return 0;
  }

}
