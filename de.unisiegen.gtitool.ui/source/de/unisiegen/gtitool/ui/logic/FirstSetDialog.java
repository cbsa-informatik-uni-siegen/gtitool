package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.FirstSetStepByStepTableColumnModel;
import de.unisiegen.gtitool.ui.model.FirstSetStepByStepTableModel;
import de.unisiegen.gtitool.ui.netbeans.FirstSetDialogForm;


/**
 * TODO
 */
public class FirstSetDialog implements LogicClass < FirstSetDialogForm >
{

  /**
   * Which Button is pressed
   */
  private enum Action
  {
    /**
     * start button
     */
    START,

    /**
     * previous button
     */
    PREVIOUS,

    /**
     * next button
     */
    NEXT,

    /**
     * stop button
     */
    STOP;
  }


  /**
   * The {@link FirstSetDialogForm}
   */
  private FirstSetDialogForm gui;


  /**
   * The {@link JFrame}
   */
  private JFrame parent;


  /**
   * The {@link CFG} for which the {@link ParsingTable} will be created
   */
  private CFG cfg;


  // ///////////////////////////////////////////////////////////////////////////
  // ///////////////Data we use to create the first sets step by step///////////
  // ///////////////////////////////////////////////////////////////////////////
  /**
   * The {@link FirstSet}s
   */
  private ArrayList < FirstSet > firstSets;


  /**
   * The reasons
   */
  private ArrayList < PrettyString > reasons;


  /**
   * The current index of the production we're currently processing
   */
  private Integer currentProductionIndex;


  /**
   * The current index of the nonterminal we're currently processing
   */
  private Integer currentNonterminalIndex;


  /**
   * indicates if we're finished creating the first sets
   */
  private boolean finished;


  /**
   * The step by step history
   */
  private Stack < ArrayList < Object > > history;


  /**
   * Allocates a new {@link FirstSetDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   */
  public FirstSetDialog ( final JFrame parent, final CFG cfg )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    this.parent = parent;
    this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );

    this.gui = new FirstSetDialogForm ( this.parent, this );

    // setup the step by step data
    this.firstSets = new ArrayList < FirstSet > ();
    this.reasons = new ArrayList < PrettyString > ();

    for ( int i = 0 ; i < this.cfg.getNonterminalSymbolSet ().size () ; ++i )
    {
      this.firstSets.add ( new DefaultFirstSet () );
      this.reasons.add ( new PrettyString () );
    }

    this.currentNonterminalIndex = new Integer ( 0 );
    this.currentProductionIndex = new Integer ( 0 );

    this.finished = false;

    this.history = new Stack < ArrayList < Object > > ();

    // setup the gui
    this.gui.setTitle ( Messages.getString ( "FirstSetDialog.Caption" ) ); //$NON-NLS-1$

    // setup the table
    this.gui.jGTIFirstTable.setModel ( new FirstSetStepByStepTableModel (
        this.cfg, this.firstSets, this.reasons ) );
    this.gui.jGTIFirstTable
        .setColumnModel ( new FirstSetStepByStepTableColumnModel () );
    this.gui.jGTIFirstTable.getTableHeader ().setReorderingAllowed ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public FirstSetDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * creates a history entry
   */
  private final void createHistoryEntry ()
  {
    ArrayList < Object > entry = new ArrayList < Object > ();
    entry.add ( this.firstSets.clone () );
    entry.add ( this.reasons.clone () );
    entry.add ( this.currentNonterminalIndex );
    entry.add ( this.currentProductionIndex );
    this.history.add ( entry );
  }


  /**
   * restores the last history entry
   */
  @SuppressWarnings ( "unchecked" )
  private final void restoreHistoryEntry ()
  {
    ArrayList < Object > entry = this.history.pop ();
    this.firstSets = ( ArrayList < FirstSet > ) entry.get ( 0 );
    this.reasons = ( ArrayList < PrettyString > ) entry.get ( 1 );
    this.currentNonterminalIndex = ( Integer ) entry.get ( 2 );
    this.currentProductionIndex = ( Integer ) entry.get ( 3 );

    if ( this.finished )
      this.finished = false;

    this.gui.jGTIFirstTable.repaint ();
  }


  /**
   * Enables a specified Button
   * 
   * @param btn the button action that is taking place
   * @param enabled button enable state
   */
  private void enableButton ( final FirstSetDialog.Action btn,
      final boolean enabled )
  {
    switch ( btn )
    {
      case START :
        this.gui.jGTIToolBarButtonStart.setEnabled ( enabled );
        break;
      case PREVIOUS :
        this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( enabled );
        break;
      case NEXT :
        this.gui.jGTIToolBarButtonNextStep.setEnabled ( enabled );
        break;
      case STOP :
        this.gui.jGTIToolBarButtonStop.setEnabled ( enabled );
        break;
    }
  }


  /**
   * Updates the word navigation buttons 'next' and 'previous'
   */
  private void updateWordNavigation ()
  {
    enableButton ( Action.PREVIOUS, !this.history.isEmpty () );
    enableButton ( Action.NEXT, this.finished );
  }


  /**
   * Handles the ok button
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * Handles the start button
   */
  public void handleStart ()
  {
    enableButton ( Action.START, false );
    enableButton ( Action.PREVIOUS, false );
    enableButton ( Action.NEXT, true );
    enableButton ( Action.STOP, true );
  }


  /**
   * Handles the next button
   */
  public void handleNext ()
  {
    createHistoryEntry ();
    // TODO: implement
    updateWordNavigation ();
  }


  /**
   * Handles the previous button
   */
  public void handlePrevious ()
  {
    if ( !this.history.isEmpty () )
      restoreHistoryEntry ();
    updateWordNavigation ();
  }


  /**
   * Handles the stop button
   */
  public void handleStop ()
  {
    enableButton ( Action.START, true );
    enableButton ( Action.PREVIOUS, false );
    enableButton ( Action.NEXT, false );
    enableButton ( Action.STOP, false );
  }
}
