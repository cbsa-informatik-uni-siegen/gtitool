package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert the grammar to a machine.
 */
public abstract class AbstractConvertGrammar implements Converter
{

  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet}.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * The {@link MachineType}.
   */
  private MachineType machineType;


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The new {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The new {@link MachinePanel}.
   */
  private MachinePanel newPanel;


  /**
   * Used for the position of the graph.
   */
  private int position = 100;


  /**
   * Allocate a new {@link AbstractConvertGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param machineType The {@link MachineType}.
   */
  public AbstractConvertGrammar ( MainWindowForm mainWindowForm,
      Grammar grammar, MachineType machineType )
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
  }


  /**
   * Add the new {@link MachinePanel} to the {@link MainWindowForm}.
   */
  protected void addPanelToView ()
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
   * Create a new {@link Machine}.
   */
  protected abstract void createMachine ();


  /**
   * Create a new {@link MachinePanel}.
   * 
   * @param machine The {@link Machine}.
   */
  protected void createMachinePanel ( Machine machine )
  {
    this.model = new DefaultMachineModel ( machine );
    this.newPanel = new MachinePanel ( this.mainWindowForm, this.model, null );
  }


  /**
   * Create a new {@link DefaultStateView}.
   * 
   * @param name The name of the {@link DefaultStateView}.
   * @return A new {@link DefaultStateView}.
   */
  protected DefaultStateView createStateView ( String name )
  {
    try
    {
      State state = null;
      if ( name != null )
      {
        state = new DefaultState ( this.alphabet, this.pushDownAlphabet, name,
            false, false );
      }
      else
      {
        state = new DefaultState ( this.alphabet, this.pushDownAlphabet, false,
            false );
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
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the {@link Grammar}.
   * 
   * @return The {@link Grammar}.
   * @see #grammar
   */
  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Returns the {@link DefaultGrammarModel}.
   * 
   * @return The {@link DefaultGrammarModel}.
   * @see #model
   */
  public DefaultMachineModel getModel ()
  {
    return this.model;
  }


  /**
   * Perform the {@link Production}s of the {@link Grammar}.
   */
  protected abstract void performProductions ();


  /**
   *
   */
  public void convert ()
  {
    createMachine ();
    performProductions ();
    addPanelToView ();
  }


  /**
   * Returns the pushDownAlphabet.
   * 
   * @return The pushDownAlphabet.
   * @see #pushDownAlphabet
   */
  public Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Sets the pushDownAlphabet.
   * 
   * @param pushDownAlphabet The pushDownAlphabet to set.
   * @see #pushDownAlphabet
   */
  public void setPushDownAlphabet ( Alphabet pushDownAlphabet )
  {
    this.pushDownAlphabet = pushDownAlphabet;
  }

  /**
   * 
   * Create a new {@link Transition}.
   *
   * @param read The word to read from stack.
   * @param write The word to write to stack.
   * @param source The source {@link DefaultStateView}.
   * @param target The target {@link DefaultStateView}.
   * @param symbols The {@link Symbol}s.
   */
  public void createTransition ( Word read, Word write,
      DefaultStateView source, DefaultStateView target,
      ArrayList < Symbol > symbols )
  {
    try
    {
      Transition transition;
      transition = new DefaultTransition ( this.alphabet,
          this.pushDownAlphabet, read, write, source.getState (),
          target.getState (), symbols );
      getModel ().createTransitionView ( transition, source, target,
          false, false, true );
    }
    catch ( TransitionSymbolNotInAlphabetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
    }

  }
}
