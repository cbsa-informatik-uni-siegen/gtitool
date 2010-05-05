package de.unisiegen.gtitool.core.grammars;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProductionSet;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarDuplicateProductionException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarNonterminalNotReachableException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarRegularGrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.storage.Element;
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
  private ProductionSet productions = new DefaultProductionSet ();


  /**
   * List containing all {@link Production}s until last save.
   */
  private ProductionSet initialProductions = new DefaultProductionSet ();


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
   * calculated first sets
   */
  private HashMap < ProductionWordMember, FirstSet > firstSets = null;


  /**
   * calculated follow sets
   */
  private HashMap < NonterminalSymbol, TerminalSymbolSet > followSets = null;


  /**
   * history of all follow sets after each step
   */
  private ArrayList < ArrayList < Object > > followSetsHistory = null;


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
      throw new NullPointerException ( "validation elements is null" ); //$NON-NLS-1$
    this.validationElementList = new ArrayList < ValidationElement > ();
    for ( ValidationElement current : validationElements )
      this.validationElementList.add ( current );

    // reset modify
    resetModify ();

    this.followSetsHistory = new ArrayList < ArrayList < Object > > ();
  }


  /**
   * Allocates a new {@link AbstractGrammar}
   * 
   * @param element
   * @param validationElements
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   * @throws StoreException
   */
  public AbstractGrammar ( final Element element,
      final ValidationElement ... validationElements )
      throws NonterminalSymbolSetException, TerminalSymbolSetException,
      StoreException
  {
    this ( new DefaultNonterminalSymbolSet ( element
        .getElementByName ( "NonterminalSymbolSet" ) ), //$NON-NLS-1$
        new DefaultTerminalSymbolSet ( element
            .getElementByName ( "TerminalSymbolSet" ) ), //$NON-NLS-1$
        new DefaultNonterminalSymbol ( element
            .getElementByName ( "StartSymbol" ).getElement ( 0 ) ), //$NON-NLS-1$
        validationElements );

    setProductions ( new DefaultProductionSet ( element
        .getElementByName ( "ProductionSet" ) ) ); //$NON-NLS-1$
  }


  /**
   * Copy constructor
   * 
   * @param other The {@link AbstractGrammar}
   * @throws NonterminalSymbolSetException
   */
  public AbstractGrammar ( final AbstractGrammar other )
      throws NonterminalSymbolSetException
  {
    this.initialProductions = new DefaultProductionSet (
        other.initialProductions );
    this.initialStartNonterminalSymbol = new DefaultNonterminalSymbol (
        ( DefaultNonterminalSymbol ) other.getStartSymbol () );
    this.terminalSymbolSet = new DefaultTerminalSymbolSet ( other
        .getTerminalSymbolSet () );
    this.nonterminalSymbolSet = new DefaultNonterminalSymbolSet ( other
        .getNonterminalSymbolSet () );
    this.validationElementList = other.validationElementList;
    this.modifyStatusChangedListener = null;
    this.startSymbol = new DefaultNonterminalSymbol (
        ( DefaultNonterminalSymbol ) other.getStartSymbol () );
    this.productions = new DefaultProductionSet ( other.productions );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addModifyStatusChangedListener(ModifyStatusChangedListener)
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
        if ( !foundDuplicates.contains ( this.productions.get ( i ) )
            && this.productions.get ( i ).equals ( this.productions.get ( j ) ) )
          duplicatedList.add ( this.productions.get ( j ) );
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
      grammarExceptionList.add ( new GrammarNonterminalNotReachableException (
          current ) );

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
        wordMemberList.add ( wordMember );

      // Epsilon
      if ( wordMemberList.size () == 0 )
        continue;

      // One member and not a TerminalSymbol
      if ( wordMemberList.size () == 1 )
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
        {
          symbols.add ( wordMemberList.get ( 0 ) );
          grammarExceptionList.add ( new GrammarRegularGrammarException (
              current, symbols ) );
        }

      // Two members and not a TerminalSymbol and a NonterminalSymbol
      if ( wordMemberList.size () == 2 )
      {
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
          symbols.add ( wordMemberList.get ( 0 ) );
        if ( ! ( wordMemberList.get ( 1 ) instanceof NonterminalSymbol ) )
          symbols.add ( wordMemberList.get ( 1 ) );
        if ( symbols.size () > 0 )
          grammarExceptionList.add ( new GrammarRegularGrammarException (
              current, symbols ) );
      }

      // More than two members
      if ( wordMemberList.size () > 2 )
      {
        if ( ! ( wordMemberList.get ( 0 ) instanceof TerminalSymbol ) )
          symbols.add ( wordMemberList.get ( 0 ) );
        if ( ! ( wordMemberList.get ( 1 ) instanceof NonterminalSymbol ) )
          symbols.add ( wordMemberList.get ( 1 ) );

        for ( int i = 2 ; i < wordMemberList.size () ; i++ )
          symbols.add ( wordMemberList.get ( i ) );

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
      for ( ModifyStatusChangedListener element : listeners )
        element.modifyStatusChanged ( true );
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener element : listeners )
        element.modifyStatusChanged ( newModifyStatus );
    }
    TableModelListener [] tableListeners = this.listenerList
        .getListeners ( TableModelListener.class );
    for ( TableModelListener l : tableListeners )
      l.tableChanged ( new TableModelEvent ( this ) );
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
      notReachable.add ( current );

    for ( NonterminalSymbol current : reachable )
      notReachable.remove ( current );
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
        if ( currentMember instanceof NonterminalSymbol )
          notRemoveableNonterminalSymbols
              .add ( ( NonterminalSymbol ) currentMember );
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
      for ( ProductionWordMember currentMember : current.getProductionWord () )
        if ( currentMember instanceof TerminalSymbol )
          notRemoveableTerminalSymbols.add ( ( TerminalSymbol ) currentMember );
    return notRemoveableTerminalSymbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#getProduction()
   */
  public ProductionSet getProduction ()
  {
    return this.productions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Grammar#getProductionForNonTerminal(NonterminalSymbol)
   */
  public ProductionSet getProductionForNonTerminal ( NonterminalSymbol s )
  {
    ProductionSet prod = new DefaultProductionSet ();
    for ( Production p : this.productions )
      if ( p.getNonterminalSymbol ().equals ( s ) )
        prod.add ( p );
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
      if ( current.isStart () )
        todoList.add ( current );

    while ( todoList.size () > 0 )
    {
      NonterminalSymbol currentNonterminalSymbol = todoList.remove ( 0 );
      reachable.add ( currentNonterminalSymbol );

      ArrayList < Production > productionList = new ArrayList < Production > ();
      for ( Production currentProduction : this.productions )
        if ( currentProduction.getNonterminalSymbol ().equals (
            currentNonterminalSymbol ) )
        {
          productionList.add ( currentProduction );

          for ( ProductionWordMember currentMember : currentProduction
              .getProductionWord () )
            if ( currentMember instanceof DefaultNonterminalSymbol )
            {
              DefaultNonterminalSymbol currentNonterminalMember = ( DefaultNonterminalSymbol ) currentMember;
              if ( !todoList.contains ( currentNonterminalMember )
                  && !reachable.contains ( currentNonterminalMember ) )
                todoList.add ( currentNonterminalMember );
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
      return true;
    for ( int i = 0 ; i < this.productions.size () ; i++ )
      if ( !this.productions.get ( i ).equals (
          this.initialProductions.get ( i ) ) )
        return true;

    if ( this.nonterminalSymbolSet.isModified () )
      return true;

    if ( this.terminalSymbolSet.isModified () )
      return true;

    if ( !this.initialStartNonterminalSymbol.equals ( this.startSymbol ) )
      return true;

    for ( Production current : this.productions )
      if ( current.isModified () )
        return true;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
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
    this.initialProductions.add ( this.productions );

    this.nonterminalSymbolSet.resetModify ();
    this.terminalSymbolSet.resetModify ();

    this.initialStartNonterminalSymbol = this.startSymbol;

    for ( Production current : this.productions )
      current.resetModify ();
  }


  /**
   * Sets the {@link Production}s.
   * 
   * @param productions The {@link Production}s to set.
   * @see #productions
   * @see Production
   */
  public void setProductions ( ProductionSet productions )
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
        if ( currentMember instanceof NonterminalSymbol )
        {
          NonterminalSymbol currentSymbol = ( NonterminalSymbol ) currentMember;
          currentSymbol.setStart ( currentSymbol.equals ( this.startSymbol ) );
        }
    }
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
      current.setStart ( current.equals ( this.startSymbol ) );
  }


  /**
   * Validates that everything in the {@link AbstractStateMachine} is correct.
   * 
   * @throws GrammarValidationException If the validation fails.
   */
  public final void validate () throws GrammarValidationException
  {
    ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();

    if ( this.validationElementList
        .contains ( ValidationElement.DUPLICATE_PRODUCTION ) )
      grammarExceptionList.addAll ( checkDuplicateProduction () );

    if ( this.validationElementList
        .contains ( ValidationElement.NONTERMINAL_NOT_REACHABLE ) )
      grammarExceptionList.addAll ( checkNonterminalNotReachable () );

    if ( this.validationElementList
        .contains ( ValidationElement.GRAMMAR_NOT_REGULAR ) )
      grammarExceptionList.addAll ( checkRegularGrammar () );

    // Throw the exception if a warning or an error has occurred.
    if ( grammarExceptionList.size () > 0 )
      throw new GrammarValidationException ( grammarExceptionList );
  }


  /**
   * calculates first set for a ProductionWord
   * 
   * @param pw the ProductionWord
   * @return the first set for the given ProductionWord
   */
  public final FirstSet first ( final ProductionWord pw )
  {
    DefaultFirstSet firstSet = new DefaultFirstSet ();

    if ( this.firstSets == null )
      calculateAllFirstSets ();

    if ( pw.epsilon () )
    {
      firstSet.epsilon ( true );
      return firstSet;
    }

    boolean lastContainsEpsilon = true;
    for ( ProductionWordMember pwm : pw )
    {
      if ( !this.firstSets.get ( pwm ).epsilon () )
      {
        firstSet.add ( this.firstSets.get ( pwm ) );
        lastContainsEpsilon = false;
        break;
      }
      for ( TerminalSymbol ts : this.firstSets.get ( pwm ) )
        if ( !ts.equals ( new DefaultTerminalSymbol ( new DefaultSymbol () ) ) )
          firstSet.add ( ts );
    }
    if ( lastContainsEpsilon )
      firstSet.epsilon ( true );

    return firstSet;
  }


  /**
   * initializes the first sets
   */
  private final void initializeFirstSets ()
  {
    this.firstSets = new HashMap < ProductionWordMember, FirstSet > ();
    for ( final TerminalSymbol ts : this.terminalSymbolSet )
      this.firstSets.put ( ts, new DefaultFirstSet () );
    for ( final NonterminalSymbol ns : this.nonterminalSymbolSet )
      this.firstSets.put ( ns, new DefaultFirstSet () );
  }


  /**
   * calculates all first sets for the nonterminals
   */
  private final void calculateAllFirstSets ()
  {
    initializeFirstSets ();
    for ( TerminalSymbol ts : this.terminalSymbolSet )
      this.firstSets.get ( ts ).add ( ts );
    boolean modified;
    do
    {
      modified = false;
      for ( Production p : this.productions )
      {
        FirstSet firstSet = this.firstSets.get ( p.getNonterminalSymbol () );

        // case 3
        if ( p.getProductionWord ().epsilon () )
          modified = firstSet.epsilon ( true ) || modified;

        // run through all elements of the right side
        boolean lastContainsEpsilon = true;
        for ( ProductionWordMember pwm : p.getProductionWord () )
        {
          // case 1, 2.1, 2.2
          if ( !this.firstSets.get ( pwm ).epsilon () )
          {
            modified = firstSet.add ( this.firstSets.get ( pwm ) ) || modified;
            lastContainsEpsilon = false;
            break;
          }
          TerminalSymbol epsilon = new DefaultTerminalSymbol (
              new DefaultSymbol () );
          for ( TerminalSymbol ts : this.firstSets.get ( pwm ) )
            if ( !ts.equals ( epsilon ) )
              modified = firstSet.add ( this.firstSets.get ( pwm ) )
                  || modified;
        }

        if ( lastContainsEpsilon )
          modified = firstSet.epsilon ( true ) || modified;
      }
    }
    while ( modified );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.grammars.Grammar#first(de.unisiegen.gtitool.core.entities.NonterminalSymbol)
   */
  public FirstSet first ( final NonterminalSymbol ns )
  {
    if ( this.firstSets == null )
      calculateAllFirstSets ();
    return this.firstSets.get ( ns );
  }


  /**
   * reset first
   */
  public void resetFirst ()
  {
    this.firstSets = null;
  }


  /**
   * extracts the rest of the {@link ProductionWord} right after the
   * {@link NonterminalSymbol} {@code ns}
   * 
   * @param index the index to start copying from
   * @param pw the {@link ProductionWord}
   * @return rest of the the {@link ProductionWord} right after the
   *         {@link NonterminalSymbol}
   */
  private final ProductionWord getProductionWordAfter ( final int index,
      final ProductionWord pw )
  {
    final ArrayList < ProductionWordMember > rest = new ArrayList < ProductionWordMember > ();
    for ( int i = index + 1 ; i < pw.size () ; ++i )
      rest.add ( pw.get ( i ) );
    return new DefaultProductionWord ( rest );
  }


  /**
   * returns all {@link Production}s containing a specified
   * {@link NonterminalSymbol}
   * 
   * @param ns the {@link NonterminalSymbol}
   * @return set of {@link Production}s containing the {@link NonterminalSymbol}
   *         {@code ns}
   */
  private final ProductionSet getProductionContainingNonterminal (
      final NonterminalSymbol ns )
  {
    ProductionSet result = new DefaultProductionSet ();
    for ( Production p : this.productions )
      if ( p.getProductionWord ().contains ( ns ) )
        result.add ( p );
    return result;
  }


  /**
   * Check if this grammar contains the nonterminal
   * 
   * @param ns
   * @return if the grammar contains the nonterminal
   */
  private final boolean validateNonterminalSymbol ( final NonterminalSymbol ns )
  {
    for ( NonterminalSymbol other : this.nonterminalSymbolSet )
      if ( other.equals ( ns ) )
        return true;
    return false;
  }


  /**
   * creates a new history entry per round
   * 
   * @param ns The {@link NonterminalSymbol}
   * @param p The {@link Production}
   * @param rightSide The {@link ProductionWord}
   * @param rightSideIndex the current rightSideIndex
   * @param rest The {@link ProductionWord}
   * @param cause The case which cases the last action to take place
   */
  private final void createFollowSetHistoryEntry ( final NonterminalSymbol ns,
      final Production p, final ProductionWord rightSide,
      final int rightSideIndex, final ProductionWord rest, final int cause )
  {
    HashMap < NonterminalSymbol, TerminalSymbolSet > tmp = new HashMap < NonterminalSymbol, TerminalSymbolSet > ();
    for ( Entry < NonterminalSymbol, TerminalSymbolSet > e : this.followSets
        .entrySet () )
      tmp.put ( e.getKey (), e.getValue () );
    ArrayList < Object > entry = new ArrayList < Object > ();
    entry.add ( tmp );
    entry.add ( ns );
    entry.add ( p );
    entry.add ( rightSide );
    entry.add ( new Integer ( rightSideIndex ) );
    entry.add ( rest );
    entry.add ( new Integer ( cause ) );
    this.followSetsHistory.add ( entry );
  }


  /**
   * Returns the follow set history
   * 
   * @return the follow set history
   */
  public final ArrayList < ArrayList < Object >> getFollowHistory ()
  {
    return this.followSetsHistory;
  }


  /**
   * calculates follow sets for each NonterminalSymbol
   */
  public final void calculateAllFollowSets ()
  {
    boolean modified;
    boolean start = false;
    do
    {
      modified = false;
      for ( NonterminalSymbol ns : this.nonterminalSymbolSet )
      {
        // case 1
        if ( ns.isStart () && !start )
        {
          this.followSets.get ( ns ).addIfNonexistent (
              DefaultTerminalSymbol.EndMarker );
          start = true;
          createFollowSetHistoryEntry ( ns, null, null, -1, null, 1 );
        }

        // case 2 and 3
        final ProductionSet prods = getProductionContainingNonterminal ( ns );

        for ( Production p : prods )
        {
          final ProductionWord rightSide = p.getProductionWord ();

          for ( int index = 0 ; index < rightSide.size () ; ++index )
          {
            final ProductionWordMember member = rightSide.get ( index );

            if ( !member.equals ( ns ) )
              continue;

            final ProductionWord rest = getProductionWordAfter ( index,
                rightSide );

            // case 2
            if ( rest.size () > 0 )
            {
              modified = this.followSets.get ( ns ).addIfNonexistent (
                  first ( rest ) )
                  || modified;
              createFollowSetHistoryEntry ( ns, p, rightSide, index, rest, 2 );
            }
            // case 3
            if ( rest.size () == 0 || first ( rest ).epsilon () )
            {
              modified = this.followSets.get ( ns ).addIfNonexistent (
                  this.followSets.get ( p.getNonterminalSymbol () ) )
                  || modified;
              createFollowSetHistoryEntry ( ns, p, rightSide, index, rest, 3 );
            }
          }
        }
      }
    }
    while ( modified );
  }


  /**
   * initializes the follow sets
   */
  private void initFollowSets ()
  {
    this.followSets = new HashMap < NonterminalSymbol, TerminalSymbolSet > ();
    for ( NonterminalSymbol ns : this.nonterminalSymbolSet )
      this.followSets.put ( ns, new DefaultTerminalSymbolSet () );
  }


  /**
   * {@inheritDoc}
   * 
   * @throws GrammarInvalidNonterminalException
   */
  public final TerminalSymbolSet follow ( final NonterminalSymbol ns )
      throws GrammarInvalidNonterminalException
  {
    if ( !validateNonterminalSymbol ( ns ) )
      throw new GrammarInvalidNonterminalException ( ns,
          this.nonterminalSymbolSet );
    if ( this.followSets == null )
    {
      initFollowSets ();
      calculateAllFollowSets ();
    }
    return this.followSets.get ( ns );
  }// end follow


  /**
   * Creates an associated alphabet, suitable for automata
   * 
   * @return the alphabet
   * @throws AlphabetException
   * @see de.unisiegen.gtitool.core.grammars.Grammar#getAlphabet()
   */
  public Alphabet getAlphabet () throws AlphabetException
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    for ( TerminalSymbol symbol : getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    return new DefaultAlphabet ( symbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    final Element newElement = new Element ( "Grammar" ); //$NON-NLS-1$
    try
    {
      newElement.addElement ( getAlphabet ().getElement () );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    final Element elemStartSymbol = new Element ( "StartSymbol" ); //$NON-NLS-1$
    elemStartSymbol.addElement ( getStartSymbol ().getElement () );
    newElement.addElement ( elemStartSymbol );
    newElement.addElement ( getTerminalSymbolSet ().getElement () );
    newElement.addElement ( getNonterminalSymbolSet ().getElement () );
    newElement.addElement ( getProduction () );
    return newElement;
  }
}
