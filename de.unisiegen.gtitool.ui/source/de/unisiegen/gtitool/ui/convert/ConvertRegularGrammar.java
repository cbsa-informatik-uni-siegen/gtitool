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
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.rg.RG;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link RG} to a {@link ENFA}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class ConvertRegularGrammar extends AbstractConvertGrammar
{

  /**
   * The final {@link DefaultStateView}.
   */
  private DefaultStateView finalStateView;


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
   */
  public ConvertRegularGrammar ( MainWindowForm mainWindowForm, Grammar grammar )
  {
    super ( mainWindowForm, grammar );
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
    StateMachine machine = new DefaultENFA ( getAlphabet (), getAlphabet (), false );
    createMachinePanel ( machine );
  }


  /**
   * Perform the {@link Production}s of the {@link Grammar}.
   */
  @Override
  protected void performProductions ()
  {

    this.finalStateView = createStateView ( null );
    this.finalStateView.getState ().setFinalState ( true );
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
      if ( current instanceof TerminalSymbol )
      {
        symbols.add ( new DefaultSymbol ( current.getName () ) );
        if ( productionWord.size () == 1 )
        {
          boolean symbolAdded = false;
          for ( Transition transition : this.finalStateView.getState ()
              .getTransitionEnd () )
          {
            if ( transition.getStateBegin ().equals ( stateView.getState () ) )
            {
              try
              {
                transition.add ( symbols );
              }
              catch ( TransitionSymbolNotInAlphabetException exc )
              {
                // Nothing to do
              }
              catch ( TransitionSymbolOnlyOneTimeException exc )
              {
                // Nothing to do
              }
              symbolAdded = true;
            }
          }
          if ( !symbolAdded )
          {
            createTransition ( new DefaultWord (), new DefaultWord (),
                stateView, this.finalStateView, symbols );
          }
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
        else
        {
          for ( Transition transition : stateView.getState ()
              .getTransitionBegin () )
          {
            if ( transition.getStateEnd ().equals ( newStateView.getState () ) )
            {
              try
              {
                transition.add ( symbols );
              }
              catch ( TransitionSymbolNotInAlphabetException exc )
              {
                // Do nothing
              }
              catch ( TransitionSymbolOnlyOneTimeException exc )
              {
                // Do nothing
              }
              return;
            }
          }
        }

        createTransition ( new DefaultWord (), new DefaultWord (), stateView,
            newStateView, symbols );

      }
    }
  }
}
