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
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


/**
 * Convert the grammar to a machine.
 * 
 * @author Benjamin Mies
 * @version $Id$
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
  private final Grammar grammar;


  /**
   * The {@link MachineType}.
   */
  private MachineType machineType;


  /**
   * The {@link MainWindowForm}.
   */
  private final MainWindowForm mainWindowForm;


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
   */
  public AbstractConvertGrammar ( MainWindowForm mainWindowForm, Grammar grammar )
  {
    this.mainWindowForm = mainWindowForm;
    this.grammar = grammar;

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    for ( TerminalSymbol current : grammar.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( current.getName () ) );

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
   * Allocate a new {@link AbstractConvertGrammar}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   * @param alphabet The {@link Alphabet}
   * 
   */
  public AbstractConvertGrammar ( MainWindowForm mainWindowForm,
      Grammar grammar, Alphabet alphabet )
  {
    this.mainWindowForm = mainWindowForm;
    this.grammar = grammar;
    this.alphabet = alphabet;
  }


  /**
   * Add the new {@link MachinePanel} to the {@link MainWindowForm}.
   */
  protected final void addPanelToView ()
  {
    TreeSet < String > nameList = new TreeSet < String > ();
    int count = 0;
    for ( EditorPanel current : this.mainWindowForm.getJGTIMainSplitPane ()
        .getJGTIEditorPanelTabbedPane () )
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        count++ ;
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
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .addEditorPanel ( this.newPanel );
    this.newPanel.addModifyStatusChangedListener ( this.mainWindowForm
        .getLogic ().getModifyStatusChangedListener () );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( this.newPanel );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Converter#convert(EntityType,EntityType,boolean,boolean)
   */
  public final void convert (
      @SuppressWarnings ( "unused" ) EntityType fromEntityType,
      EntityType toEntityType, @SuppressWarnings ( "unused" ) boolean complete,
      @SuppressWarnings ( "unused" ) boolean cb )
  {
    if ( ! ( toEntityType instanceof MachineType ) )
      throw new IllegalArgumentException ( "unsopported to entity type: " //$NON-NLS-1$
          + toEntityType );

    this.machineType = ( MachineType ) toEntityType;
    createMachine ();
    performProductions ();
    addPanelToView ();

    if(this.machineType != MachineType.TDP)
      new LayoutManager ( this.model, this.newPanel.getRedoUndoHandler () )
          .doLayout ();
  }


  protected MachineType getMachineType ()
  {
    return this.machineType;
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
  protected final void createMachinePanel ( Machine machine )
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
  protected final DefaultStateView createStateView ( String name )
  {
    try
    {
      State state = null;
      if ( name != null )
        state = new DefaultState ( this.alphabet, this.pushDownAlphabet, name,
            false, false );
      else
        state = new DefaultState ( this.alphabet, this.pushDownAlphabet,
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


  protected final DefaultStateView createStateViewFromState ( State state )
  {
    DefaultStateView stateView = this.model.createStateView ( this.position,
        this.position, state, false, false );
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

      Transition transition = new DefaultTransition ( this.alphabet,
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
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the {@link Grammar}.
   * 
   * @return The {@link Grammar}.
   * @see #grammar
   */
  public final Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Returns the {@link DefaultGrammarModel}.
   * 
   * @return The {@link DefaultGrammarModel}.
   * @see #model
   */
  public final DefaultMachineModel getModel ()
  {
    return this.model;
  }


  /**
   * Returns the newPanel.
   * 
   * @return The newPanel.
   * @see #newPanel
   */
  public final MachinePanel getNewPanel ()
  {
    return this.newPanel;
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
