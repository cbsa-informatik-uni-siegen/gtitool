package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;
import java.util.TreeMap;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.dfa.LR0;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public class ConvertToLR0 extends AbstractConvertGrammar
{

  private DefaultStateView finalStateView;


  /**
   * TODO
   * 
   * @param mainWindowForm
   * @param grammar
   */

  private static LR0Grammar convertGrammar ( Grammar grammar )
  {
    LR0Grammar ret = new LR0Grammar ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol () );

    for ( Production prod : grammar.getProduction () )
      ret.addProduction ( prod );
    return ret;
  }


  public ConvertToLR0 ( MainWindowForm mainWindowForm, Grammar grammar )
      throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar )
        .makeAutomataAlphabet () );

    this.lr0Grammar = convertGrammar ( grammar );

    this.setPushDownAlphabet ( new DefaultAlphabet () );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    try
    {
      this.machine = this.lr0Grammar.makeLR0Automata ();

      // for(State state : this.machine.getState())
      // System.err.println (state.toString());

      createMachinePanel ( new LR0 ( this.machine.getAlphabet () ) );
      // createMachinePanel ( machine );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#performProductions()
   */
  private void createTransition ( Word read, Word write,
      DefaultStateView source, DefaultStateView target, Symbol symbol )
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    symbols.add ( symbol );
    createTransition ( read, write, source, target, symbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#performProductions()
   */
  @Override
  protected void performProductions ()
  {
    ArrayList < State > copy = new ArrayList < State > ( this.machine
        .getState () );

    ArrayList < Transition > transCopy = new ArrayList < Transition > (
        this.machine.getTransition () );

    TreeMap < State, DefaultStateView > stateViews = new TreeMap < State, DefaultStateView > ();

    for ( State state : copy )
      stateViews.put ( state, createStateView ( state.getName () ) );

    for ( State state : copy )
      for ( State state2 : copy )
        for ( Transition transition : transCopy )
          if ( transition.getStateBegin ().equals ( state )
              && transition.getStateEnd ().equals ( state2 ) )
            this.createTransition ( new DefaultWord (), new DefaultWord (),
                stateViews.get ( state ), stateViews.get ( state2 ),
                new ArrayList < Symbol > ( transition.getSymbol () ) );
  }


  private LR0Grammar lr0Grammar;


  private LR0 machine;
}
