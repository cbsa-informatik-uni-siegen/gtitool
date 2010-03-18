package de.unisiegen.gtitool.ui.convert;


import java.util.ArrayList;

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
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.StateMachinePanel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultStateMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


/**
 * Convert the grammar to a machine.
 * 
 * @author Benjamin Mies
 * @version $Id: AbstractConvertGrammarStateMachine.java 1663 2010-02-22
 *          13:30:05Z uhrhan $
 */
public abstract class AbstractConvertGrammarStateMachine extends
    AbstractConvertGrammar
{

  /**
   * The push down {@link Alphabet}.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The new {@link DefaultStateMachineModel}.
   */
  private DefaultStateMachineModel model;


  /**
   * Used for the position of the graph.
   */
  private int position = 100;


  /**
   * The {@link StateMachinePanel}
   */
  private StateMachinePanel panel;


  /**
   * Allocate a new {@link AbstractConvertGrammarStateMachine}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   */
  public AbstractConvertGrammarStateMachine ( MainWindowForm mainWindowForm,
      Grammar grammar )
  {
    super ( mainWindowForm, grammar );

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    for ( TerminalSymbol current : grammar.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( current.getName () ) );

    try
    {
      setAlphabet ( new DefaultAlphabet ( symbols ) );
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
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#getNewPanel()
   */
  @Override
  public final MachinePanel getNewPanel ()
  {
    return this.panel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#setNewPanel(de.unisiegen.gtitool.ui.logic.MachinePanel)
   */
  @Override
  protected final void setNewPanel ( final MachinePanel panel )
  {
    this.panel = ( StateMachinePanel ) panel;
  }


  /**
   * Allocate a new {@link AbstractConvertGrammarStateMachine}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param alphabet The {@link Alphabet}
   */
  public AbstractConvertGrammarStateMachine ( MainWindowForm mainWindowForm,
      Grammar grammar, Alphabet alphabet )
  {
    super ( mainWindowForm, grammar, alphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Converter#convert(EntityType,EntityType,boolean,boolean)
   */
  @Override
  public final void convert ( EntityType fromEntityType,
      EntityType toEntityType, boolean complete, boolean cb )
  {
    super.convert ( fromEntityType, toEntityType, complete, cb );

    performProductions ();
    new LayoutManager ( this.model, getNewPanel ().getRedoUndoHandler () )
        .doLayout ();
  }


  /**
   * Create a new {@link StateMachinePanel}.
   * 
   * @param machine The {@link StateMachine}.
   */
  @Override
  protected void createMachinePanel ( final Machine machine )
  {
    doCreateMachinePanel ( machine, true );
  }


  /**
   * Create a new {@link StateMachinePanel} and tell it if we want to show the
   * Stack
   * 
   * @param machine
   * @param showPDA - show the stack
   */
  protected void doCreateMachinePanel ( final Machine machine,
      final boolean showPDA )
  {
    this.model = new DefaultStateMachineModel ( ( StateMachine ) machine );
    this.model.setGrammar ( getGrammar () );
    setNewPanel ( new StateMachinePanel ( getMainWindowForm (), this.model,
        null, showPDA ) );
  }


  /**
   * Create a new {@link DefaultStateView}.
   * 
   * @param name The name of the {@link DefaultStateView}.
   * @return A new {@link DefaultStateView}.
   */
  protected final DefaultStateView createStateView ( String name )
  {
    try
    {
      State state = null;
      if ( name != null )
        state = new DefaultState ( getAlphabet (), this.pushDownAlphabet, name,
            false, false );
      else
        state = new DefaultState ( getAlphabet (), this.pushDownAlphabet,
            this.model.getMachine ().getNextStateName (), false, false );
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
   * creates a StateView out of a State
   * 
   * @param state the {@link State}
   * @return a {@link DefaultStateView}
   */
  protected final DefaultStateView createStateViewFromState ( final State state )
  {
    DefaultStateView stateView = this.model.createStateView ( this.position,
        this.position, state, false, false, false );
    this.position += 50;
    return stateView;
  }


  /**
   * Create a new {@link Transition}.
   * 
   * @param read The word to read from stack.
   * @param write The word to write to stack.
   * @param source The source {@link DefaultStateView}.
   * @param target The target {@link DefaultStateView}.
   * @param symbols The {@link Symbol}s.
   */
  public final void createTransition ( Word read, Word write,
      DefaultStateView source, DefaultStateView target,
      ArrayList < Symbol > symbols )
  {
    try
    {
      ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
      symbolList.addAll ( symbols );
      if ( symbolList.size () == 0 )
        symbolList.add ( new DefaultSymbol () );

      Transition transition = new DefaultTransition ( getAlphabet (),
          this.pushDownAlphabet, read, write, source.getState (), target
              .getState (), symbolList );
      getModel ().createTransitionView ( transition, source, target, false,
          false, true );
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


  /**
   * Returns the {@link DefaultGrammarModel}.
   * 
   * @return The {@link DefaultGrammarModel}.
   * @see #model
   */
  public final DefaultStateMachineModel getModel ()
  {
    return this.model;
  }


  /**
   * Returns the pushDownAlphabet.
   * 
   * @return The pushDownAlphabet.
   * @see #pushDownAlphabet
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Perform the {@link Production}s of the {@link Grammar}.
   */
  protected abstract void performProductions ();


  /**
   * Sets the pushDownAlphabet.
   * 
   * @param pushDownAlphabet The pushDownAlphabet to set.
   * @see #pushDownAlphabet
   */
  public final void setPushDownAlphabet ( Alphabet pushDownAlphabet )
  {
    this.pushDownAlphabet = pushDownAlphabet;
  }
}
