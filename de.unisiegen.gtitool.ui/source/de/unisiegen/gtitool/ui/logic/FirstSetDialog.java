package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
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
 * Implementation of the logic for the FirstSetDialogForm
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
  private HashMap < ProductionWordMember, FirstSet > firstSets;


  /**
   * The reasons
   */
  private ArrayList < PrettyString > reasons;


  /**
   * The current index of the production we're currently processing
   */
  private int currentProductionIndex;


  /**
   * The current index of the nonterminal we're currently processing
   */
  private int currentProductionWordMemberIndex;


  /**
   * indicates if we're finished creating the first sets
   */
  private boolean finished;


  /**
   * indicates if the current round has finished
   */
  private boolean nextProduction;


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
    this.firstSets = new HashMap < ProductionWordMember, FirstSet > ();
    this.reasons = new ArrayList < PrettyString > ();

    for ( TerminalSymbol ts : this.cfg.getTerminalSymbolSet () )
    {
      this.firstSets.put ( ts, new DefaultFirstSet () );
      this.firstSets.get ( ts ).add ( ts );
    }
    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
    {
      this.firstSets.put ( ns, new DefaultFirstSet () );
      this.reasons.add ( new PrettyString () );
    }

    this.currentProductionWordMemberIndex = 0;
    this.currentProductionIndex = 0;

    this.finished = false;
    this.nextProduction = true;

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
    entry.add ( new Integer ( this.currentProductionWordMemberIndex ) );
    entry.add ( new Integer ( this.currentProductionIndex ) );
    entry.add ( new Boolean ( this.nextProduction ) );
    this.history.add ( entry );
  }


  /**
   * restores the last history entry
   */
  @SuppressWarnings ( "unchecked" )
  private final void restoreHistoryEntry ()
  {
    ArrayList < Object > entry = this.history.pop ();
    this.firstSets = ( HashMap < ProductionWordMember, FirstSet > ) entry
        .get ( 0 );
    this.reasons = ( ArrayList < PrettyString > ) entry.get ( 1 );
    this.currentProductionWordMemberIndex = ( ( Integer ) entry.get ( 2 ) )
        .intValue ();
    this.currentProductionIndex = ( ( Integer ) entry.get ( 3 ) ).intValue ();
    this.nextProduction = ( ( Boolean ) entry.get ( 4 ) ).booleanValue ();

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

    this.history.clear ();
    this.currentProductionIndex = 0;
    this.currentProductionWordMemberIndex = 0;
    this.finished = false;
    this.nextProduction = true;
  }


  /**
   * Handles the next button
   */
  @SuppressWarnings ( "null" )
  public void handleNext ()
  {
    // TODO: check if this condition is enough (check with this.roundFinished)
    if ( !this.finished )
    {
      createHistoryEntry ();
      Production p = null;
      if ( this.nextProduction )
      {
        /*
         * during the next few 'next'-steps we're maybe going through all
         * elements of the right side of the production. So reset this counter
         */
        this.currentProductionWordMemberIndex = 0;
        // the next production we're going to process
        p = this.cfg.getProductionAt ( this.currentProductionIndex );
        ++this.currentProductionIndex;
        /*
         * as long as we're not finished processing the next production we will
         * not proceed to the next one
         */
        this.nextProduction = false;
        /*
         * if we ran through all productions so far reset this counter. if we're
         * not finished calculating the first sets we're going to run through
         * all productions again.
         */
        if ( this.currentProductionIndex == this.cfg.getProduction ().size () - 1 )
          this.currentProductionIndex = 0;
      }
      FirstSet firstSet = this.firstSets.get ( p.getNonterminalSymbol () );
      if ( p.getProductionWord ().epsilon () )
      {
        this.finished = firstSet.epsilon ( true ) || this.finished;
        this.nextProduction = true;
      }
      else
      {
        ProductionWordMember pwm = p.getProductionWord ().get (
            this.currentProductionWordMemberIndex );
        FirstSet firstSetToAdd = this.firstSets.get ( pwm );
        // TODO: color all new added symbols red
        this.finished = firstSet.add ( firstSetToAdd );
        if ( !firstSetToAdd.epsilon ()
            || this.currentProductionWordMemberIndex == p.getProductionWord ()
                .size () - 1 )
          this.nextProduction = true;
      }
    }
    updateWordNavigation ();
    this.gui.jGTIFirstTable.repaint ();
  }


  /**
   * Handles the previous button
   */
  public void handlePrevious ()
  {
    if ( !this.history.isEmpty () )
      restoreHistoryEntry ();
    updateWordNavigation ();
    this.gui.jGTIFirstTable.repaint ();
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

    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
      this.firstSets.put ( ns, new DefaultFirstSet () );
    int reasonSize = this.reasons.size ();
    this.reasons.clear ();
    for ( int i = 0 ; i < reasonSize ; ++i )
      this.reasons.add ( new PrettyString () );
  }
}
