package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ParsingTableStepByStepListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * represents the parsing table
 */
public class DefaultParsingTable implements ParsingTable
{

  /**
   * the generated serial
   */
  private static final long serialVersionUID = -1602679123085565866L;


  /**
   * the {@link CFG}
   */
  private final CFG cfg;


  /**
   * the set of terminals
   */
  private final TerminalSymbolSet terminals;


  /**
   * the set of nonterminals
   */
  private final NonterminalSymbolSet nonterminals;


  /**
   * the parsing table entries: [TerminalSymbol][NonterminalSymbol] =
   * TreeSet<Production>
   */
  private ArrayList < ArrayList < DefaultProductionSet >> parsingTable;


  // Data we need to create the {@link ParsingTable} step by step
  /**
   * the current {@link NonterminalSymbol} we're processing
   */
  private int currentNonterminalIndex;


  /**
   * the current {@link TerminalSymbol} we're processing
   */
  private int currentTerminalIndex;


  /**
   * the current {@link ProductionSet}
   */
  private ProductionSet currentProductionSet;


  /**
   * indicates whether there is a next {@link TerminalSymbol} to process
   */
  private boolean isTerminalSymbolNextStepAvailable;


  /**
   * indicates whether there is a next {@link NonterminalSymbol} to process
   */
  private boolean isNonterminalSymbolNextStepAvailable;


  /**
   * The {@link EventListener} list
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * allocates a new {@link DefaultParsingTable}
   * 
   * @param cfg the CFG from which we're creating the parsing table
   */
  public DefaultParsingTable ( final CFG cfg )
  {
    if ( cfg == null )
      throw new NullPointerException ( "cfg is null" ); //$NON-NLS-1$

    this.cfg = cfg;
    this.terminals = cfg.getTerminalSymbolSet ();
    this.nonterminals = cfg.getNonterminalSymbolSet ();

    this.currentNonterminalIndex = 0;
    this.currentTerminalIndex = 0;
    this.currentProductionSet = null;
    this.isTerminalSymbolNextStepAvailable = false;
    this.isNonterminalSymbolNextStepAvailable = false;
  }


  /**
   * {@inheritDoc}
   */
  public int getColumnCount ()
  {
    return this.terminals.size ();
  }


  /**
   * {@inheritDoc}
   */
  public int getRowCount ()
  {
    return this.nonterminals.size ();
  }


  /**
   * {@inheritDoc}
   */
  public void createParsingTableStart ()
  {
    this.parsingTable = new ArrayList < ArrayList < DefaultProductionSet >> ();
    startNonterminalSymbolRound ();
  }


  /**
   * {@inheritDoc}
   */
  public void createParsingTableNextStep ()
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    if ( this.isTerminalSymbolNextStepAvailable )
      this.isTerminalSymbolNextStepAvailable = terminalSymbolNext ();
    else if ( this.isNonterminalSymbolNextStepAvailable )
    {
      this.isNonterminalSymbolNextStepAvailable = nonterminalSymbolNext ();
      this.isTerminalSymbolNextStepAvailable = terminalSymbolNext ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#isPreviousStepAvailable()
   */
  public boolean isPreviousStepAvailable ()
  {
    return this.currentTerminalIndex != 0 || this.currentNonterminalIndex != 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#createParsingTablePreviousStep()
   */
  public void createParsingTablePreviousStep ()
  {
    if ( !isPreviousStepAvailable () )
      return;

    // calculate indices of the last round (cause the indices are already
    // incremented)
    if ( this.currentTerminalIndex == 0 )
    {
      this.currentTerminalIndex = this.terminals.size () - 1;
      this.isTerminalSymbolNextStepAvailable = false;
    }
    else
    {
      --this.currentTerminalIndex;
      this.isTerminalSymbolNextStepAvailable = true;
    }
    if ( this.currentNonterminalIndex > 0
        && !this.isTerminalSymbolNextStepAvailable )
    {
      --this.currentNonterminalIndex;
      this.isNonterminalSymbolNextStepAvailable = true;
    }
    
    if ( !this.parsingTable.get ( this.currentNonterminalIndex ).get (
        this.currentTerminalIndex ).isEmpty () )
      firePreviousStepRemoveEntry ();

    // delete the last round result from the parsing table
    this.parsingTable.get ( this.currentNonterminalIndex ).remove (
        this.currentTerminalIndex );
  }


  /**
   * Start a new {@link NonterminalSymbol} round that means: we start the
   * iteration through the list of {@link NonterminalSymbol}s
   */
  private void startNonterminalSymbolRound ()
  {
    this.currentNonterminalIndex = 0;
    this.isNonterminalSymbolNextStepAvailable = true;
    this.parsingTable.add ( new ArrayList < DefaultProductionSet > () );
    this.currentProductionSet = this.cfg
        .getProductionForNonTerminal ( this.nonterminals
            .get ( this.currentNonterminalIndex ) );
    startTerminalSymbolRound ();
  }


  /**
   * Proceed creating the {@link ParsingTable} with the next
   * {@link NonterminalSymbol}
   * 
   * @return true if there is a next {@link NonterminalSymbol} to proceed with,
   *         false otherwise
   */
  private boolean nonterminalSymbolNext ()
  {
    // ++this.currentNonterminalIndex;
    boolean nextAvailable = true;
    if ( this.currentNonterminalIndex + 1 == this.nonterminals.size () )
      nextAvailable = false;
    this.parsingTable.add ( new ArrayList < DefaultProductionSet > () );
    this.currentProductionSet = this.cfg
        .getProductionForNonTerminal ( this.nonterminals
            .get ( this.currentNonterminalIndex ) );
    startTerminalSymbolRound ();
    return nextAvailable;
  }


  /**
   * Checks whether a {@link Production} is to be added as a
   * {@link ParsingTable} entry
   * 
   * @param p The {@link Production}
   * @return true if the specified {@link Production} is to be added to the
   *         {@link ParsingTable}
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  private ParsingTable.EntryCause isParsingTableEntry ( final Production p )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    NonterminalSymbol ns = getCurrentNonterminalSymbol ();
    TerminalSymbol ts = getCurrentTerminalSymbol ();

    boolean tsInFirstProductionWord = this.cfg.first ( p.getProductionWord () )
        .contains ( ts );
    boolean productionWordDerivesToEpsilon = this.cfg.first (
        p.getProductionWord () ).epsilon ();
    boolean tsInFollowNS = this.cfg.follow ( ns ).contains ( ts );

    if ( tsInFirstProductionWord )
      return ParsingTable.EntryCause.TERMINAL_IN_FIRSTSET;
    else if ( productionWordDerivesToEpsilon && tsInFollowNS )
      return ParsingTable.EntryCause.EPSILON_DERIVATION_AND_FOLLOWSET;
    return ParsingTable.EntryCause.NOCAUSE;
  }


  /**
   * Starts a new {@link TerminalSymbol} round that means: we start a new
   * iteration through the list of {@link TerminalSymbol}s
   */
  private void startTerminalSymbolRound ()
  {
    // this.currentTerminalIndex = 0;
    this.isTerminalSymbolNextStepAvailable = true;
    this.parsingTable.get ( this.currentNonterminalIndex ).add (
        new DefaultProductionSet () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#isNextStepAvailable()
   */
  public boolean isNextStepAvailable ()
  {
    return this.isTerminalSymbolNextStepAvailable
        || this.isNonterminalSymbolNextStepAvailable;
  }


  /**
   * Proceed creating the {@link ParsingTable} for the current
   * {@link NonterminalSymbol} with the next {@link TerminalSymbol}
   * 
   * @return true if there is a next {@link TerminalSymbol} to proceed with,
   *         false otherwise
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  private boolean terminalSymbolNext ()
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    boolean nextAvailable = true;
    if ( this.currentTerminalIndex + 1 == this.terminals.size () )
      nextAvailable = false;

    this.parsingTable.get ( this.currentNonterminalIndex ).add (
        new DefaultProductionSet () );
    for ( Production p : this.currentProductionSet )
    {
      final ParsingTable.EntryCause cause = isParsingTableEntry ( p );
      if ( cause == ParsingTable.EntryCause.NOCAUSE )
        continue;
      this.parsingTable.get ( this.currentNonterminalIndex ).get (
          this.currentTerminalIndex ).add ( p );
      fireProductionAdded ( cause, p );
    }

    if ( nextAvailable )
      ++this.currentTerminalIndex;
    else
    {
      this.currentTerminalIndex = 0;
      if ( this.currentNonterminalIndex + 1 < this.nonterminals.size () )
        ++this.currentNonterminalIndex;
    }
    return nextAvailable;
  }


  /**
   * creates the parsing table out of a context free grammar
   * 
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public void create () throws GrammarInvalidNonterminalException,
      TerminalSymbolSetException
  {
    this.parsingTable = new ArrayList < ArrayList < DefaultProductionSet > > ();

    int row = 0;
    for ( NonterminalSymbol ns : this.nonterminals )
    {
      this.parsingTable.add ( new ArrayList < DefaultProductionSet > () );

      ProductionSet ps = this.cfg.getProductionForNonTerminal ( ns );

      int col = 0;
      for ( TerminalSymbol ts : this.terminals )
      {
        this.parsingTable.get ( row ).add ( new DefaultProductionSet () );
        for ( Production p : ps )
          if ( this.cfg.first ( p.getProductionWord () ).contains ( ts )
              || ( this.cfg.first ( p.getProductionWord () ).epsilon () && this.cfg
                  .follow ( ns ).contains ( ts ) ) )
            this.parsingTable.get ( row ).get ( col ).add ( p );
        ++col;
      }
      ++row;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#get(int, int)
   */
  public DefaultProductionSet get ( int row, int col )
  {
    return this.parsingTable.get ( row ).get ( col );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#get(de.unisiegen.gtitool.core.entities.NonterminalSymbol,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public DefaultProductionSet get ( NonterminalSymbol ns, TerminalSymbol ts )
  {
    int col = getTerminalSymbolIndex ( ts );
    int row = getNonterminalSymbolIndex ( ns );
    if ( col == -1 || row == -1 )
      return new DefaultProductionSet ();
    return get ( row, col );
  }


  /**
   * returns the corresponding index of a terminal symbol
   * 
   * @param ts the terminal symbol for which we want to determin the index
   * @return index of the terminal symbol or -1 if it is not in the set
   */
  private int getTerminalSymbolIndex ( TerminalSymbol ts )
  {
    for ( int i = 0 ; i < this.terminals.size () ; ++i )
      if ( this.terminals.get ( i ).getName ().equals ( ts.getName () ) )
        return i;
    return -1;
  }


  /**
   * returns the corresponding index of a nonterminal symbol
   * 
   * @param ns the nonterminal symbol for which we want to determin the index
   * @return index of the nonterminal symbol or -1 if it is not in the set
   */
  private int getNonterminalSymbolIndex ( NonterminalSymbol ns )
  {
    for ( int i = 0 ; i < this.nonterminals.size () ; ++i )
      if ( this.nonterminals.get ( i ).getName ().equals ( ns.getName () ) )
        return i;
    return -1;
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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( ParsingTable o )
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
   */
  public NonterminalSymbol getCurrentNonterminalSymbol ()
  {
    return this.nonterminals.get ( this.currentNonterminalIndex );
  }


  /**
   * {@inheritDoc}
   */
  public TerminalSymbol getCurrentTerminalSymbol ()
  {
    return this.terminals.get ( this.currentTerminalIndex );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#addParsingTableStepByStepListener(de.unisiegen.gtitool.core.entities.listener.ParsingTableStepByStepListener)
   */
  public void addParsingTableStepByStepListener (
      final ParsingTableStepByStepListener listener )
  {
    this.listenerList.add ( ParsingTableStepByStepListener.class, listener );
  }


  /**
   * Notifies all {@link ParsingTableStepByStepListener} that a new
   * {@link Production} was added to the {@link ParsingTable}
   * 
   * @param cause The {@link ParsingTable.EntryCause} which indicates why the
   *          {@link Production} was added
   * @param production The {@link Production} that was added
   */
  private void fireProductionAdded ( final ParsingTable.EntryCause cause,
      Production production )
  {
    ParsingTableStepByStepListener [] listener = this.listenerList
        .getListeners ( ParsingTableStepByStepListener.class );
    for ( ParsingTableStepByStepListener l : listener )
      l
          .productionAddedAsEntry ( production, getCurrentTerminalSymbol (),
              cause );
  }


  /**
   * Notifies all {@link ParsingTableStepByStepListener} that there is a
   * {@link ParsingTable} entry to be removed
   */
  private void firePreviousStepRemoveEntry ()
  {
    ParsingTableStepByStepListener [] listener = this.listenerList
        .getListeners ( ParsingTableStepByStepListener.class );
    for ( ParsingTableStepByStepListener l : listener )
      l.previousStepRemoveEntry ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#clear()
   */
  public void clear ()
  {
    this.parsingTable.clear ();
    this.currentTerminalIndex = 0;
    this.currentNonterminalIndex = 0;
  }
}
