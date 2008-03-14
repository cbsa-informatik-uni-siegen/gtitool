package de.unisiegen.gtitool.core.grammars;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The abstract class for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractGrammar implements Grammar
{

  /**
   * List of listeners
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet;


  /**
   * The {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbolSet;


  /**
   * List containing all {@link Production}s.
   */
  private ArrayList < Production > productions = new ArrayList < Production > ();


  /**
   * List containing all {@link Production}s until last save.
   */
  private ArrayList < Production > initialProductions = new ArrayList < Production > ();

  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;

  /**
   * Allocate a new {@link AbstractGrammar}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   */
  public AbstractGrammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
    this.terminalSymbolSet = terminalSymbolSet;
    
    // ModifyStatusChangedListener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };
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
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  public abstract String getGrammarType ();


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return the {@link NonterminalSymbolSet}.
   */
  public NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    return this.nonterminalSymbolSet;
  }


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}.
   */
  public TerminalSymbolSet getTerminalSymbolSet ()
  {
    return this.terminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( !this.productions.equals ( this.initialProductions ) )
    {
      return true;
    }
    if ( this.nonterminalSymbolSet.isModified () )
    {
      return true;
    }
    if ( this.terminalSymbolSet.isModified () )
    {
      return true;
    }
    for ( Production current : this.productions )
    {
      if ( current.isModified () )
      {
        return true;
      }
    }
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
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialProductions.clear ();
    this.initialProductions.addAll ( this.productions );
    this.nonterminalSymbolSet.resetModify ();
    this.terminalSymbolSet.resetModify ();

    for ( Production current : this.productions )
    {
      current.resetModify ();
    }
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
   * @see javax.swing.table.TableModel#getColumnClass(int)
   */
  public Class < ? > getColumnClass ( @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    return Production.class;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnName(int)
   */
  public String getColumnName ( @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    return ""; //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.productions.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    return this.productions.get ( rowIndex );
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#isCellEditable(int, int)
   */
  public boolean isCellEditable ( @SuppressWarnings ( "unused" )
  int rowIndex, @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
   */
  public void removeTableModelListener ( TableModelListener listener )
  {
    this.listenerList.remove ( TableModelListener.class, listener );
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
   * Add a {@link Production} to this grammar
   * 
   * @param production The {@link Production}
   */
  public void addProduction ( Production production )
  {
    this.productions.add ( production );
    production.addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( true );
  }


  /**
   * Remove a {@link Production} from this grammar
   * 
   * @param production The {@link Production}
   */
  public void removeProduction ( Production production )
  {
    this.productions.remove ( production );
    fireModifyStatusChanged ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.grammars.Grammar#getProductionAt(int)
   */
  public Production getProductionAt ( int index )
  {
    return this.productions.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.grammars.Grammar#getProductions()
   */
  public ArrayList < Production > getProductions ()
  {
    return this.productions;
  }
  
  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  private final void fireModifyStatusChanged ( boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener element : listeners )
      {
        element.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener element : listeners )
      {
        element.modifyStatusChanged ( newModifyStatus );
      }
    }
  }
}
