package de.unisiegen.gtitool.core.machines.pda;


import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.CancelOutAction;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ReverseReduceAction;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractStatelessMachine;


/**
 * The class for the top down parser (pda)
 * 
 *@author Christian Uhrhan
 */
public class DefaultTDP extends AbstractStatelessMachine implements TDP
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1371164970141783189L;


  /**
   * the {@link CFG} for this push down automaton
   */
  private CFG cfg;


  /**
   * the {@link ParsingTable} for this top down parser
   */
  private ParsingTable parsingTable;


  /**
   * Allocates a new {@link PDA}.
   * 
   * @param cfg The {@link CFG} of this {@link PDA}.
   * @throws AlphabetException
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public DefaultTDP ( CFG cfg ) throws AlphabetException,
      GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    super ( cfg.getAlphabet () );
    this.cfg = cfg;
    this.parsingTable = new DefaultParsingTable ( this.cfg );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  @Override
  public final void start ( final Word word )
  {
    Symbol endMarkerSymbol = new DefaultSymbol (
        DefaultTerminalSymbol.EndMarker.getName () );
    word.add ( endMarkerSymbol );
    super.start ( word );
    getStack ().push ( endMarkerSymbol );
    getStack ().push (
        new DefaultSymbol ( this.cfg.getStartSymbol ().getName () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getPossibleActions()
   */
  @Override
  public ActionSet getPossibleActions () throws ActionSetException
  {
    ActionSet actions = new DefaultActionSet ();
    Symbol inputSymbol = getWord ().getCurrentSymbol ();
    Symbol stackSymbol = getStack ().peak ();

    if ( inputSymbol.equals ( DefaultTerminalSymbol.EndMarker )
        && stackSymbol.equals ( DefaultTerminalSymbol.EndMarker ) )
      actions.add ( new AcceptAction () );
    else if ( this.cfg.getNonterminalSymbolSet ().contains (
        new DefaultNonterminalSymbol ( stackSymbol ) ) )
    {
      ProductionSet ps = this.parsingTable.get ( new DefaultNonterminalSymbol (
          stackSymbol ), new DefaultTerminalSymbol ( inputSymbol ) );
      for ( Production p : ps )
        actions.add ( new ReverseReduceAction ( p ) );
    }
    else if ( inputSymbol.equals ( stackSymbol ) )
      actions.add ( new CancelOutAction () );

    return actions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public Action autoTransit () throws MachineAmbigiousActionException
  {
    try
    {
      return assertTransit ( getPossibleActions () );
    }
    catch ( ActionSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onShift(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onShift ( @SuppressWarnings ( "unused" ) final Action action )
  {
    /*
     * shift in our case means: the push down automaton takes the actual input
     * symbol and stack symbol and cancles each other out
     */
    nextSymbol ();
    getStack ().pop ();
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onReduce(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onReduce ( final Action action )
  {
    /*
     * replace the left side of the production corresponding to the actual
     * nonterminal on the stack with its right side
     */
    getStack ().pop ();
    ProductionWord pw = action.getReduceAction ().getProductionWord ();
    for ( int i = pw.size () - 1 ; i >= 0 ; --i )
      getStack ().push ( new DefaultSymbol ( pw.get ( i ).getName () ) );
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public MachineType getMachineType ()
  {
    return MachineType.TDP;
  }


  /**
   * {@inheritDoc} s
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getGrammar()
   */
  @Override
  public CFG getGrammar ()
  {
    return this.cfg;
  }
}
