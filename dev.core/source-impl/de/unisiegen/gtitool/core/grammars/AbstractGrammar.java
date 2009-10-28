package de.unisiegen.gtitool.core.grammars;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarDuplicateProductionException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarNonterminalNotReachableException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarRegularGrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The abstract class for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractGrammar implements Grammar
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -445079972963576721L;


  /**
   * List of listeners
   */
  private final EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link NonterminalSymbolSet}.
   */
  private final NonterminalSymbolSet nonterminalSymbolSet;


  /**
   * The {@link TerminalSymbolSet}.
   */
  private final TerminalSymbolSet terminalSymbolSet;


  /**
   * List containing all {@link Production}s.
   */
  private ArrayList < Production > productions = new ArrayList < Production > ();


  /**
   * List containing all {@link Production}s until last save.
   */
  private final ArrayList < Production > initialProductions = new ArrayList < Production > ();


  /**
   * The start {@link NonterminalSymbol}.
   */
  private NonterminalSymbol initialStartNonterminalSymbol = null;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private final ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The validation element list.
   */
  private final ArrayList < ValidationElement > validationElementList;


  /**
   * The start symbol of this grammar.
   */
  private NonterminalSymbol startSymbol;


  /**
   * Allocate a new {@link AbstractGrammar}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param startSymbol The start symbol of this grammar.
   * @param validationElements The validation elements which indicates which
   *          validation elements should be checked during a validation.
   */
  public AbstractGrammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol,
      ValidationElement ... validationElements )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
    this.terminalSymbolSet = terminalSymbolSet;
    this.startSymbol = startSymbol;

    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };

    this.nonterminalSymbolSet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    this.terminalSymbolSet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    // validation elements
    if ( validationElements == null )
    {
      throw new NullPointerException ( "validation elements is null" ); //$NON-NLS-1$
    }
    this.validationElementList = new ArrayList < ValidationElement > ();
    for ( ValidationElement current : validationElements )
    {
      this.validationElementList.add ( current );
    }

    // reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Add a {@link Production} to this grammar.
   * 
   * @param production The {@link Production}.
   */
  public void addProduction ( Production production )
  {
    this.productions.add ( production );
    updateStartSymbol ();
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
   * Check the grammar for duplicate productions.
   * 
   * @return list containing occured errors.
   */
  private final ArrayList < GrammarException > checkDuplicateProduction ()
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();
    ArrayList < Production > foundDuplicates = new ArrayList < Production > ();
    for ( int i = 0 ; i < this.productions.size () ; i++ )
    {
      ArrayList < Production > duplicatedList = new ArrayList < Production > ();
      for ( int j = i + 1 ; j < this.productions.size () ; j++ )
      {
        if ( !foundDuplicates.contains ( this.productions.get ( i ) )
            && this.productions.get ( i ).equals ( this.productions.get ( j ) ) )
        {
          duplicatedList.add ( this.productions.get ( j ) );
        }
      }
      if ( duplicatedList.size () > 0 )
      {
        foundDuplicates.add ( this.productions.get ( i ) );
        duplicatedList.add ( this.productions.get ( i ) );
        grammarExceptionList.add ( new GrammarDuplicateProductionException (
            duplicatedList ) );
      }
    }
    return grammarExceptionList;
  }


  /**
   * Check the grammar for not reachable nonterminal symbols.
   * 
   * @return list containing occured errors.
   */
  private final ArrayList < GrammarException > checkNonterminalNotReachable ()
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();

    for ( NonterminalSymbol current : getNotReachableNonterminalSymbols () )
    {
      grammarExceptionList.add ( new GrammarNonterminalNotReachableException (
          current ) );
    }

    return grammarExceptionList;
  }


  /**
   * Check if the grammar is regular.
   * 
   * @return list containing occured errors.
   */
  private final ArrayList < GrammarException > checkRegularGrammar ()
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();

    for ( Production current : this.productions )
    {
      ArrayList < ProductionWordMember > symbols = new ArrayList < ProductionWordMember > ();

      ArrayList < ProductionWordMember > wordMemberList = new ArrayList < ProductionWordMember > ();
      for ( ProductionWordMember wordMember : current.getProductionWord () )
      {
        wordMemberList.add ( wordMember );
      }

      // Epsilon
      if ( wordMemberList.size () == 0 )
      {
        continue;
      }

      // One member and not a TerminalSymbol
      if ( wordMemberList.size () == 1 )
      {
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 0 ) );
          grammarExceptionList.add ( new GrammarRegularGrammarException (
              current, symbols ) );
        }
      }

      // Two members and not a TerminalSymbol and a NonterminalSymbol
      if ( wordMemberList.size () == 2 )
      {
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 0 ) );
        }
        if ( ! ( wordMemberList.get ( 1 ) instanceof NonterminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 1 ) );
        }
        if ( symbols.size () > 0 )
        {
          grammarExceptionList.add ( new GrammarRegularGrammarException (
              current, symbols ) );
        }
      }

      // More than two members
      if ( wordMemberList.size () > 2 )
      {
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 0 ) );
        }
        if ( ! ( wordMemberList.get ( 1 ) instanceof NonterminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 1 ) );
        }

        for ( int i = 2 ; i < wordMemberList.size () ; i++ )
        {
          symbols.add ( wordMemberList.get ( i ) );
        }

        grammarExceptionList.add ( new GrammarRegularGrammarException (
            current, symbols ) );
      }
    }
    return grammarExceptionList;
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  protected final void fireModifyStatusChanged ( boolean forceModify )
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
    TableModelListener [] tableListeners = this.listenerList
        .getListeners ( TableModelListener.class );
    for ( TableModelListener l : tableListeners )
    {
      l.tableChanged ( new TableModelEvent ( this ) );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnClass(int)
   */
  public Class < ? > getColumnClass (
      @SuppressWarnings ( "unused" ) int columnIndex )
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
  public String getColumnName ( @SuppressWarnings ( "unused" ) int columnIndex )
  {
    return ""; //$NON-NLS-1$
  }


  /**
   * Returns the {@link Grammar.GrammarType}.
   * 
   * @return The {@link Grammar.GrammarType}.
   */
  public abstract GrammarType getGrammarType ();


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
   * {@inheritDoc}
   * 
   * @see Grammar#getNotReachableNonterminalSymbols()
   */
  public final ArrayList < NonterminalSymbol > getNotReachableNonterminalSymbols ()
  {
    ArrayList < NonterminalSymbol > reachable = getReachableNonterminalSymbols ();
    ArrayList < NonterminalSymbol > notReachable = new ArrayList < NonterminalSymbol > ();

    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      notReachable.add ( current );
    }

    for ( NonterminalSymbol current : reachable )
    {
      notReachable.remove ( current );
    }
    return notReachable;
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
   * @see Grammar#getProduction()
   */
  public ArrayList < Production > getProduction ()
  {
    return this.productions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#getProductionForNonTerminal(NonterminalSymbol)
   */
  public ArrayList < Production > getProductionForNonTerminal (
      NonterminalSymbol s )
  {
    ArrayList < Production > prod = new ArrayList < Production > ();
    for ( Production p : this.productions )
    {
      if ( p.getNonterminalSymbol ().equals ( s ) )
      {
        prod.add ( p );
      }
    }
    return prod;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#getProductionAt(int)
   */
  public Production getProductionAt ( int index )
  {
    return this.productions.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#getReachableNonterminalSymbols()
   */
  public final ArrayList < NonterminalSymbol > getReachableNonterminalSymbols ()
  {
    ArrayList < NonterminalSymbol > reachable = new ArrayList < NonterminalSymbol > ();
    ArrayList < NonterminalSymbol > todoList = new ArrayList < NonterminalSymbol > ();

    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      if ( current.isStart () )
      {
        todoList.add ( current );
      }
    }

    while ( todoList.size () > 0 )
    {
      NonterminalSymbol currentNonterminalSymbol = todoList.remove ( 0 );
      reachable.add ( currentNonterminalSymbol );

      ArrayList < Production > productionList = new ArrayList < Production > ();
      for ( Production currentProduction : this.productions )
      {
        if ( currentProduction.getNonterminalSymbol ().equals (
            currentNonterminalSymbol ) )
        {
          productionList.add ( currentProduction );

          for ( ProductionWordMember currentMember : currentProduction
              .getProductionWord () )
          {
            if ( currentMember instanceof DefaultNonterminalSymbol )
            {
              DefaultNonterminalSymbol currentNonterminalMember = ( DefaultNonterminalSymbol ) currentMember;
              if ( !todoList.contains ( currentNonterminalMember )
                  && !reachable.contains ( currentNonterminalMember ) )
              {
                todoList.add ( currentNonterminalMember );
              }
            }
          }
        }
      }
    }

    return reachable;
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
   * @see Grammar#getStartSymbol()
   */
  public NonterminalSymbol getStartSymbol ()
  {
    return this.startSymbol;
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
  public Object getValueAt ( int rowIndex,
      @SuppressWarnings ( "unused" ) int columnIndex )
  {
    return this.productions.get ( rowIndex );
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#isCellEditable(int, int)
   */
  public boolean isCellEditable ( @SuppressWarnings ( "unused" ) int rowIndex,
      @SuppressWarnings ( "unused" ) int columnIndex )
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

    if ( !this.initialStartNonterminalSymbol.equals ( this.startSymbol ) )
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
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Remove a {@link Production} from this grammar.
   * 
   * @param index The index of the {@link Production}.
   */
  public void removeProduction ( int index )
  {
    this.productions.remove ( index );
    updateStartSymbol ();
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

    this.initialStartNonterminalSymbol = this.startSymbol;

    for ( Production current : this.productions )
    {
      current.resetModify ();
    }
  }


  /**
   * Sets the {@link Production}s.
   * 
   * @param productions The {@link Production}s to set.
   * @see #productions
   * @see Production
   */
  public void setProductions ( ArrayList < Production > productions )
  {
    this.productions = productions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#setStartSymbol(NonterminalSymbol)
   */
  public void setStartSymbol ( NonterminalSymbol startSymbol )
  {
    this.startSymbol = startSymbol;
    updateStartSymbol ();

    fireModifyStatusChanged ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#setValueAt(Object, int, int)
   */
  public final void setValueAt ( @SuppressWarnings ( "unused" ) Object value,
      @SuppressWarnings ( "unused" ) int rowIndex,
      @SuppressWarnings ( "unused" ) int columnIndex )
  {
    // Do nothing
  }


  /**
   * Updates the start symbol flags.
   */
  public final void updateStartSymbol ()
  {
    for ( Production currentProduction : this.productions )
    {
      currentProduction.getNonterminalSymbol ()
          .setStart (
              currentProduction.getNonterminalSymbol ().equals (
                  this.startSymbol ) );
      for ( ProductionWordMember currentMember : currentProduction
          .getProductionWord () )
      {
        if ( currentMember instanceof NonterminalSymbol )
        {
          NonterminalSymbol currentSymbol = ( NonterminalSymbol ) currentMember;
          currentSymbol.setStart ( currentSymbol.equals ( this.startSymbol ) );
        }
      }
    }
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      current.setStart ( current.equals ( this.startSymbol ) );
    }
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

    if ( this.validationElementList
        .contains ( ValidationElement.GRAMMAR_NOT_REGULAR ) )
    {
      grammarExceptionList.addAll ( checkRegularGrammar () );
    }

    // Throw the exception if a warning or an error has occurred.
    if ( grammarExceptionList.size () > 0 )
    {
      throw new GrammarValidationException ( grammarExceptionList );
    }
  }


  /**
   * {@inheritDoc}
   */
  public final FirstSet first ( final ProductionWord pw )
  {
    DefaultFirstSet fs = new DefaultFirstSet ();
    for ( ProductionWordMember X : pw )
    {
      if ( X instanceof NonterminalSymbol )
      {
        NonterminalSymbol x = ( NonterminalSymbol ) X;
        if ( !this.nonterminalSymbolSet.contains ( x ) )
        {
          ; // todo throw exception
        }
        Production prod = null;
        for ( Production p : this.productions )
        {
          if ( p.getNonterminalSymbol ().equals ( x ) )
          {
            prod = p;
          }
        }
        if ( prod == null )
        {
          ; // todo throw exception
        }
        else if ( prod.getProductionWord ().epsilon () )
        {
          fs.epsilon ( true );
        }
        else
        {
          for ( ProductionWordMember pwm : prod.getProductionWord () )
          {
            DefaultProductionWord dpw = new DefaultProductionWord ();
            dpw.add ( pwm );
            if(first ( dpw ).epsilon ())
            {
              continue;
            } else {
              try
              {
                fs.add ( new DefaultTerminalSymbol(pwm.getElement ()));
              }
              catch ( TerminalSymbolSetException exc )
              {
                exc.printStackTrace();
              }
              catch ( StoreException exc )
              {
                exc.printStackTrace();
              }
            }
          }
        }
      }
      else
      { // X is TerminalSymbol
        try
        {
          fs.add ( ( TerminalSymbol ) X );
        }
        catch ( TerminalSymbolSetException exc )
        {
          // TerminalSymbol already exists in the set => do nothing
          // exc.printStackTrace();
        }
      }
    }
    return fs;
  }


  /**
   * {@inheritDoc}
   */
  public final TerminalSymbolSet follow ( final Production p )
  {
    TerminalSymbolSet nss = new DefaultTerminalSymbolSet ();

    return nss;
  }
}
