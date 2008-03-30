package de.unisiegen.gtitool.core.grammars;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarDuplicateProductionException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarNonterminalNotReachableException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
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
   * The validation element list.
   */
  private ArrayList < ValidationElement > validationElementList;


  /**
   * Allocate a new {@link AbstractGrammar}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param validationElements The validation elements which indicates which
   *          validation elements should be checked during a validation.
   */
  public AbstractGrammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet,
      ValidationElement ... validationElements )
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

    // Validation elements
    if ( validationElements == null )
    {
      throw new NullPointerException ( "validation elements is null" ); //$NON-NLS-1$
    }
    this.validationElementList = new ArrayList < ValidationElement > ();
    for ( ValidationElement current : validationElements )
    {
      this.validationElementList.add ( current );
    }
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
   * Add a {@link Production} to this grammar
   * 
   * @param production The {@link Production}
   */
  public void addProduction ( Production production )
  {
    this.productions.add ( production );
    production
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( false );
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
   * Check the grammar for duplicate productions
   * 
   * @return list containing occured errors
   */
  private final ArrayList < GrammarException > checkDuplicateProduction ()
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();
    ArrayList < Production > foundDuplicates = new ArrayList < Production > ();

    for ( Production current : this.productions )
    {
      if ( !foundDuplicates.contains ( current ) )
        for ( Production other : this.productions )
        {
          if ( ! ( current == other ) && current.equals ( other ) )
          {
            grammarExceptionList.add ( new GrammarDuplicateProductionException (
                current ) );
            foundDuplicates.add ( current );
          }
        }
    }
    return grammarExceptionList;
  }


  /**
   * Check the grammar for not reachable nonterminal symbols
   * 
   * @return list containing occured errors
   */
  private final ArrayList < GrammarException > checkNonterminalNotReachable ()
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();

    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      boolean used = false;
      loop : for ( Production production : this.productions )
      {
        for ( ProductionWordMember symbol : production.getProductionWord () )
        {
          if ( current.equals ( symbol ) )
          {
            used = true;
            break loop;
          }
        }
      }
      if ( !used )
      {
        grammarExceptionList.add ( new GrammarNonterminalNotReachableException (
            current ) );
      }
    }

    return grammarExceptionList;
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
   * Returns the {@link NonterminalSymbol}s which are not removeable from the
   * {@link NonterminalSymbolSet}.
   * 
   * @return The {@link NonterminalSymbol}s which are not removeable from the
   *         {@link NonterminalSymbolSet}.
   */
  public final TreeSet < NonterminalSymbol > getNotRemoveableNonterminalSymbolsFromNonterminalSymbol ()
  {
    TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols = new TreeSet < NonterminalSymbol > ();
    for ( Production current : this.productions )
    {
      notRemoveableNonterminalSymbols.add ( current.getNonterminalSymbol () );
      for ( ProductionWordMember currentMember : current.getProductionWord () )
      {
        if ( currentMember instanceof NonterminalSymbol )
        {
          notRemoveableNonterminalSymbols
              .add ( ( NonterminalSymbol ) currentMember );
        }
      }
    }
    return notRemoveableNonterminalSymbols;
  }


  /**
   * Returns the {@link TerminalSymbol}s which are not removeable from the
   * {@link TerminalSymbolSet}.
   * 
   * @return The {@link TerminalSymbol}s which are not removeable from the
   *         {@link TerminalSymbolSet}.
   */
  public final TreeSet < TerminalSymbol > getNotRemoveableTerminalSymbolsFromTerminalSymbol ()
  {
    TreeSet < TerminalSymbol > notRemoveableTerminalSymbols = new TreeSet < TerminalSymbol > ();
    for ( Production current : this.productions )
    {
      for ( ProductionWordMember currentMember : current.getProductionWord () )
      {
        if ( currentMember instanceof TerminalSymbol )
        {
          notRemoveableTerminalSymbols.add ( ( TerminalSymbol ) currentMember );
        }
      }
    }
    return notRemoveableTerminalSymbols;
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
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.productions.size ();
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
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( this.productions.size () != this.initialProductions.size () )
    {
      return true;
    }
    for ( int i = 0 ; i < this.productions.size () ; i++ )
    {
      if ( !this.productions.get ( i ).equals (
          this.initialProductions.get ( i ) ) )
      {
        return true;
      }
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
   * Remove a {@link Production} from this grammar
   * 
   * @param production The {@link Production}
   */
  public void removeProduction ( Production production )
  {
    this.productions.remove ( production );
    fireModifyStatusChanged ( false );
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
   * Validates that everything in the {@link AbstractMachine} is correct.
   * 
   * @throws GrammarValidationException If the validation fails.
   */
  public final void validate () throws GrammarValidationException
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();

    if ( this.validationElementList
        .contains ( ValidationElement.DUPLICATE_PRODUCTION ) )
    {
      grammarExceptionList.addAll ( checkDuplicateProduction () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.NONTERMINAL_NOT_REACHABLE ) )
    {
      grammarExceptionList.addAll ( checkNonterminalNotReachable () );
    }

    // Throw the exception if a warning or an error has occurred.
    if ( grammarExceptionList.size () > 0 )
    {
      throw new GrammarValidationException ( grammarExceptionList );
    }
  }
}
