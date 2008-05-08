package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;
import java.util.HashMap;

import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.rg.RG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link RG} to a {@link ENFA}.
 */
public class ConvertRegularGrammar extends AbstractConvertGrammar
{

  /**
   * The {@link NonterminalSymbol}s and there representing
   * {@link DefaultStateView}s.
   */
  private HashMap < NonterminalSymbol, DefaultStateView > states = new HashMap < NonterminalSymbol, DefaultStateView > ();


  /**
   * Allocate a new {@link ConvertRegularGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param machineType The {@link MachineType}.
   */
  public ConvertRegularGrammar ( MainWindowForm mainWindowForm,
      Grammar grammar, MachineType machineType )
  {
    super ( mainWindowForm, grammar, machineType );
    setPushDownAlphabet ( getAlphabet () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    Machine machine = new DefaultENFA ( getAlphabet (), getAlphabet (), false );
    createMachinePanel ( machine );
  }


  /**
   * Perform the {@link Production}s of the {@link Grammar}.
   */
  @Override
  protected void performProductions ()
  {
    for ( Production current : getGrammar ().getProduction () )
    {
      DefaultStateView stateView;
      stateView = this.states.get ( current.getNonterminalSymbol () );
      if ( stateView == null )
      {
        stateView = createStateView ( current.getNonterminalSymbol ()
            .getName () );
        this.states.put ( current.getNonterminalSymbol (), stateView );
        if ( current.getNonterminalSymbol ().equals (
            getGrammar ().getStartSymbol () ) )
        {
          stateView.getState ().setStartState ( true );
        }
      }
      performProductionWord ( stateView, current.getProductionWord () );
    }

  }


  /**
   * Perform the {@link ProductionWord}.
   * 
   * @param stateView The {@link DefaultStateView}.
   * @param productionWord The {@link ProductionWord}.
   */
  protected void performProductionWord ( DefaultStateView stateView,
      ProductionWord productionWord )
  {

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    if ( productionWord.size () == 0 )
    {
      stateView.getState ().setFinalState ( true );

      return;
    }

    for ( ProductionWordMember current : productionWord )
    {
      DefaultStateView newStateView = null;
      try
      {
        if ( current instanceof TerminalSymbol )
        {
          symbols.add ( new DefaultSymbol ( current.getName () ) );
          if ( productionWord.size () == 1 )
          {
            newStateView = createStateView ( null );

            createTransition ( new DefaultWord (), new DefaultWord (),
                stateView, newStateView, symbols );

            newStateView.getState ().setFinalState ( true );
          }
        }
        else
        {
          newStateView = this.states.get ( current );
          if ( newStateView == null )
          {
            newStateView = createStateView ( current.getName () );
            this.states.put ( ( NonterminalSymbol ) current, newStateView );
          }

          createTransition ( new DefaultWord (), new DefaultWord (), stateView,
              newStateView, symbols );

        }
      }
      catch ( SymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }
  }
}
