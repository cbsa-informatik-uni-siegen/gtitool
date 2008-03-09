package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;

/**
 * The Model for the {@link Grammar}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class DefaultGrammarModel implements DefaultModel, Storable, Modifyable,
    TableModel
{

  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * List containing all {@link Production}s
   */
  private ArrayList < Production > productions = new ArrayList < Production > ();


  /**
   * List of listeners
   */
  private EventListenerList listenerList = new EventListenerList ();


  
  /**
   * Allocate a new {@link DefaultGrammarModel}.
   * 
   * @param grammar The {@link Grammar}
   */
  public DefaultGrammarModel ( Grammar grammar )
  {
    this.grammar = grammar;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Add a new production to list.
   * 
   * @param production The production to add.
   */
  public void addProduction ( Production production )
  {
    this.productions.add ( production );

  }

  /**
   * {@inheritDoc}
   * 
   * @see TableModel#addTableModelListener(TableModelListener)
   */
  public final void addTableModelListener ( TableModelListener listener )
  {
    this.listenerList.add ( TableModelListener.class, listener );
  }

  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnClass(int)
   */
  public Class < ? > getColumnClass ( @SuppressWarnings("unused")
  int columnIndex )
  {
    return DefaultProduction.class;
  }

  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnName(int)
   */
  public String getColumnName ( @SuppressWarnings("unused")
  int columnIndex )
  {
    // TODO Auto-generated method stub
    return "Productions";
  }

  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public Element getElement ()
  {
    // TODO Auto-generated method stub
    return null;
  }


  /**
   * Returns the {@link Grammar}.
   * 
   * @return the {@link Grammar}.
   */
  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Returns the production at the given index.
   * 
   * @param index the index of the production of interest.
   * 
   * @return the production at the given index.
   */
  public Production getProductionAt ( int index )
  {
    if ( this.productions.size () > index )
      return this.productions.get ( index );
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.productions.size ();
  }

  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, @SuppressWarnings("unused")
  int columnIndex )
  {
    return this.productions.get ( rowIndex );
  }


  /**
   * Signals if cell is editable.
   * 
   * @param rowIndex the row index of the cell
   * @param columnIndex the column index of the cell
   * 
   * @return false (our table is read only)
   */
  public boolean isCellEditable ( int rowIndex, int columnIndex )
  {
    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    // TODO Auto-generated method stub
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Remove the given production from list.
   * 
   * @param production The production to remove.
   */
  public void removeProduction ( Production production )
  {
    this.productions.remove ( production );

  }

  
  /**
   * {@inheritDoc}
   * 
   * @see TableModel#removeTableModelListener(TableModelListener)
   */
  public final void removeTableModelListener ( TableModelListener listener )
  {
    this.listenerList.remove ( TableModelListener.class, listener );
  }

  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public void resetModify ()
  {
    // TODO Auto-generated method stub

  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#setValueAt(Object, int, int)
   */
  public final void setValueAt ( @SuppressWarnings ( "unused" )
  Object value, @SuppressWarnings ( "unused" )
  int rowIndex, @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    // Do nothing
  }

  /**
   * Returns the index for the given {@link Production}.
   * 
   * @param production The {@link Production}.
   * 
   * @return the index for the given {@link Production}.
   */
  public int getIndexOf ( Production production )
  {
    return this.productions.indexOf ( production );
  }

  /**
   * Add a new production add a specified index to list.
   * 
   * @param index The specified index to add the production. 
   * @param production The production to add.
   */
  public void addProduction ( int index, Production production )
  {
    this.productions.add ( index, production );
    
  }
}
