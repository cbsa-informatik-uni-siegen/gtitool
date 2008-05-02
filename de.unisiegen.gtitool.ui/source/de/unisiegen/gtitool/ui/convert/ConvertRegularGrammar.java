package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert the grammar to a machine.
 */
public class ConvertRegularGrammar
{

  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * The {@link MachineType}.
   */
  private MachineType machineType;


  /**
   * The new {@link MachinePanel}.
   */
  private MachinePanel newPanel;


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The new {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The {@link NonterminalSymbol}s and there representing
   * {@link DefaultStateView}s.
   */
  private HashMap < NonterminalSymbol, DefaultStateView > states = new HashMap < NonterminalSymbol, DefaultStateView > ();


  /**
   * Used for the position of the graph.
   */
  int position = 100;


  /**
   * Allocate a new {@link ConvertRegularGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param machineType The {@link MachineType}.
   */
  public ConvertRegularGrammar ( MainWindowForm mainWindowForm, Grammar grammar,
      MachineType machineType )
  {
    this.mainWindowForm = mainWindowForm;
    this.grammar = grammar;
    this.machineType = machineType;

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

    try
    {
      this.alphabet = new DefaultAlphabet ( symbols );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    createMachinePanel ();
    performProductions ();
    addPanelToView ();

  }


  /**
   * Add the new {@link MachinePanel} to the {@link MainWindowForm}.
   */
  private void addPanelToView ()
  {
    TreeSet < String > nameList = new TreeSet < String > ();
    int count = 0;
    for ( EditorPanel current : this.mainWindowForm.editorPanelTabbedPane )
    {
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        count++ ;
      }
    }

    String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
        + "." + this.machineType.toString ().toLowerCase (); //$NON-NLS-1$
    while ( nameList.contains ( name ) )
    {
      count++ ;
      name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + this.machineType.toString ();
    }

    this.newPanel.setName ( name );
    this.mainWindowForm.editorPanelTabbedPane.addEditorPanel ( this.newPanel );
    this.newPanel.addModifyStatusChangedListener ( this.mainWindowForm
        .getLogic ().getModifyStatusChangedListener () );
    this.mainWindowForm.editorPanelTabbedPane
        .setSelectedEditorPanel ( this.newPanel );
  }


  /**
   * Create a new {@link MachinePanel}.
   */
  private void createMachinePanel ()
  {
      this.model = new DefaultMachineModel ( new DefaultENFA ( this.alphabet,
          this.alphabet, false ) );
      this.newPanel = new MachinePanel ( this.mainWindowForm, this.model, null );
  }


  /**
   * Create a new {@link DefaultStateView}.
   * 
   * @param name The name of the {@link DefaultStateView}.
   * @return A new {@link DefaultStateView}.
   */
  private DefaultStateView createStateView ( String name )
  {
    try
    {
      State state = null;
      if ( name != null )
      {
        state = new DefaultState ( this.alphabet, this.alphabet, name, false,
            false );
      }
      else
      {
        state = new DefaultState ( this.alphabet, this.alphabet, false, false );
      }
      DefaultStateView stateView = this.model.createStateView ( this.position,
          this.position, state, false );
      this.position += 50;
      return stateView;

    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    return null;
  }


  /**
   * Perform the {@link Production}s of the {@link Grammar}.
   */
  private void performProductions ()
  {

    for ( Production current : this.grammar.getProduction () )
    {
      DefaultStateView stateView;
      stateView = this.states.get ( current.getNonterminalSymbol () );
      if ( stateView == null )
      {
        stateView = createStateView ( current.getNonterminalSymbol ()
            .getName () );
        this.states.put ( current.getNonterminalSymbol (), stateView );
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
  private void performProductionWord ( DefaultStateView stateView,
      ProductionWord productionWord )
  {

    Symbol terminalSymbol = null;
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    
    for ( ProductionWordMember current : productionWord )
    {
      DefaultStateView newStateView = null;
      try
      {
        if ( current instanceof TerminalSymbol )
        {
          terminalSymbol =  new DefaultSymbol ( current.getName () );
          symbols.add ( terminalSymbol );
          if ( productionWord.size () == 1 )
          {
            newStateView = createStateView ( null );
            
            Transition transition = new DefaultTransition ( this.alphabet,
                this.alphabet, new DefaultWord (), new DefaultWord (), stateView
                    .getState (), newStateView.getState (), symbols );

            this.model.createTransitionView ( transition, stateView, newStateView,
                false, false, true );
            
            newStateView.getState ().setFinalState ( true );
            symbols.clear ();
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
          
          Transition transition = new DefaultTransition ( this.alphabet,
              this.alphabet, new DefaultWord (), new DefaultWord (), stateView
                  .getState (), newStateView.getState (), symbols );

          this.model.createTransitionView ( transition, stateView, newStateView,
              false, false, true );

        }
      }
      catch ( SymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }
  }
}
