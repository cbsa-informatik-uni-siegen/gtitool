package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.machines.pda.DefaultPDA;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link CFG} to a {@link PDA}.
 * 
 * @author Benjamin Mies
 * @version $Id: ConvertContextFreeGrammar.java 910 2008-05-16 00:31:21Z fehler
 *          $
 */
public class ConvertContextFreeGrammar extends AbstractConvertGrammar
{

  /**
   * Allocate a new {@link ConvertContextFreeGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   */
  public ConvertContextFreeGrammar ( MainWindowForm mainWindowForm,
      Grammar grammar )
  {
    super ( mainWindowForm, grammar );

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    for ( TerminalSymbol current : grammar.getTerminalSymbolSet () )
    {
      symbols.add ( new DefaultSymbol ( current.getName () ) );
    }

    for ( NonterminalSymbol current : grammar.getNonterminalSymbolSet () )
    {
      symbols.add ( new DefaultSymbol ( current.getName () ) );
    }

    try
    {
      setPushDownAlphabet ( new DefaultAlphabet ( symbols ) );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    StateMachine machine = new DefaultPDA ( getAlphabet (), getPushDownAlphabet (),
        true );
    createMachinePanel ( machine );

  }


  /**
   * Create a new {@link Transition}.
   * 
   * @param read The word to read from stack.
   * @param write The word to write to stack.
   * @param source The source {@link DefaultStateView}.
   * @param target The target {@link DefaultStateView}.
   * @param symbol The {@link Symbol}
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

    DefaultStateView start = createStateView ( "s" ); //$NON-NLS-1$
    start.getState ().setStartState ( true );
    DefaultStateView stateView = createStateView ( "f" ); //$NON-NLS-1$
    stateView.getState ().setFinalState ( true );

    Symbol symbol = new DefaultSymbol ( getGrammar ().getStartSymbol ()
        .getName () );

    DefaultWord word = new DefaultWord ( symbol );

    createTransition ( new DefaultWord (), word, start, stateView,
        new ArrayList < Symbol > () );

    for ( TerminalSymbol current : getGrammar ().getTerminalSymbolSet () )
    {
      symbol = new DefaultSymbol ( current.getName () );
      word = new DefaultWord ( symbol );

      createTransition ( word, new DefaultWord (), stateView, stateView, symbol );
    }

    for ( Production currentProduction : getGrammar ().getProduction () )
    {
      Word read = new DefaultWord ( new DefaultSymbol ( currentProduction
          .getNonterminalSymbol ().getName () ) );

      Word write = new DefaultWord ();

      for ( ProductionWordMember currentMember : currentProduction
          .getProductionWord () )
      {
        write.add ( new DefaultSymbol ( currentMember.getName () ) );
      }
      createTransition ( read, write, stateView, stateView,
          new ArrayList < Symbol > () );
    }
  }
}
