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
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.pda.DefaultPDA;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link CFG} to a {@link PDA}.
 */
public class ConvertContextFreeGrammar extends AbstractConvertGrammar
{

  /**
   * Allocate a new {@link ConvertContextFreeGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param machineType The {@link MachineType}.
   */
  public ConvertContextFreeGrammar ( MainWindowForm mainWindowForm,
      Grammar grammar, MachineType machineType )
  {
    super ( mainWindowForm, grammar, machineType );

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    for ( TerminalSymbol current : grammar.getTerminalSymbolSet () )
    {
      try
      {
        symbols.add ( new DefaultSymbol ( current.getName () ) );
      }
      catch ( SymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }

    for ( NonterminalSymbol current : grammar.getNonterminalSymbolSet () )
    {
      try
      {
        symbols.add ( new DefaultSymbol ( current.getName () ) );
      }
      catch ( SymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
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
    Machine machine = new DefaultPDA ( getAlphabet (), getPushDownAlphabet (),
        true );
    createMachinePanel ( machine );
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
    
    
    try
    {
      Symbol symbol = new DefaultSymbol ( getGrammar ().getStartSymbol ()
          .getName () );

      DefaultWord word = new DefaultWord ( symbol );

      createTransition ( new DefaultWord (), word, start, stateView,
          new ArrayList < Symbol > () );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
    }

    for ( Production current : getGrammar ().getProduction () )
    {
      try
      {
        Word read = new DefaultWord ( new DefaultSymbol ( current
            .getNonterminalSymbol ().getName () ) );

        Word write = new DefaultWord ();

        for ( ProductionWordMember symbol : current.getProductionWord () )
        {
          write.add ( new DefaultSymbol ( symbol.getName () ) );
        }
        createTransition ( read, write, stateView, stateView, new ArrayList < Symbol >() );
      }
      catch ( SymbolException exc )
      {
        exc.printStackTrace ();
      }
      
      
    }
  }
}