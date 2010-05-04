package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
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
  private boolean modified;


  /**
   * indicates if the current round has finished
   */
  private boolean nextProduction;


  /**
   * last was epsilon
   */
  private boolean lastWasEpsilon;


  /**
   * The step by step history
   */
  private Stack < ArrayList < Object > > history;


  /**
   * model for the list of reasons
   */
  private DefaultListModel reasonListModel;


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
    // center the dialog
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );

    // setup the step by step data
    this.firstSets = new HashMap < ProductionWordMember, FirstSet > ();

    for ( TerminalSymbol ts : this.cfg.getTerminalSymbolSet () )
    {
      this.firstSets.put ( ts, new DefaultFirstSet () );
      this.firstSets.get ( ts ).add ( ts );
    }
    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
      this.firstSets.put ( ns, new DefaultFirstSet () );

    this.currentProductionWordMemberIndex = 0;
    this.currentProductionIndex = 0;

    this.modified = false;
    this.nextProduction = false;
    this.lastWasEpsilon = true;

    this.history = new Stack < ArrayList < Object > > ();

    // setup the gui
    this.gui.setTitle ( Messages.getString ( "FirstSetDialog.Caption" ) ); //$NON-NLS-1$

    // setup the table
    this.gui.jGTIFirstTable.setModel ( new FirstSetStepByStepTableModel (
        this.cfg, this.firstSets ) );
    this.gui.jGTIFirstTable
        .setColumnModel ( new FirstSetStepByStepTableColumnModel () );
    this.gui.jGTIFirstTable.getTableHeader ().setReorderingAllowed ( false );

    this.reasonListModel = new DefaultListModel ();
    this.gui.jGTIReasonList.setModel ( this.reasonListModel );
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

    HashMap < ProductionWordMember, FirstSet > currentFirstSets = new HashMap < ProductionWordMember, FirstSet > ();
    for ( Entry < ProductionWordMember, FirstSet > e : this.firstSets
        .entrySet () )
      if ( e.getKey () instanceof NonterminalSymbol )
        currentFirstSets.put ( new DefaultNonterminalSymbol (
            ( DefaultNonterminalSymbol ) e.getKey () ), new DefaultFirstSet ( e
            .getValue () ) );
      else
        currentFirstSets.put ( new DefaultTerminalSymbol (
            ( DefaultTerminalSymbol ) e.getKey () ), new DefaultFirstSet ( e
            .getValue () ) );
    entry.add ( currentFirstSets );
    entry.add ( new Integer ( this.currentProductionWordMemberIndex ) );
    entry.add ( new Integer ( this.currentProductionIndex ) );
    entry.add ( new Boolean ( this.nextProduction ) );
    entry.add ( new Boolean ( this.lastWasEpsilon ) );
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
    this.currentProductionWordMemberIndex = ( ( Integer ) entry.get ( 1 ) )
        .intValue ();
    this.currentProductionIndex = ( ( Integer ) entry.get ( 2 ) ).intValue ();
    this.nextProduction = ( ( Boolean ) entry.get ( 3 ) ).booleanValue ();
    this.lastWasEpsilon = ( ( Boolean ) entry.get ( 4 ) ).booleanValue ();

    if ( this.modified )
      this.modified = false;

    ( ( FirstSetStepByStepTableModel ) this.gui.jGTIFirstTable.getModel () )
        .setAll ( this.firstSets );
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
    boolean allFinished = this.modified
        || this.currentProductionIndex != this.cfg.getProduction ().size () - 1;
    enableButton ( Action.NEXT, allFinished );
  }


  /**
   * Handles the ok button
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * shows this dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
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
    this.modified = false;
    this.nextProduction = false;
  }


  /**
   * Handles the next button
   */
  public void handleNext ()
  {
    createHistoryEntry ();
    Production p = null;
    if ( this.nextProduction )
    {
      int old = this.currentProductionIndex;
      /*
       * if we ran through all productions so far reset this counter. if we're
       * not finished calculating the first sets we're going to run through all
       * productions again.
       */
      if ( this.currentProductionIndex == this.cfg.getProduction ().size () - 1 )
      {
        this.currentProductionIndex = -1;
        this.modified = false;
      }
      /*
       * during the next few 'next'-steps we're maybe going through all elements
       * of the right side of the production. So reset this counter
       */
      this.currentProductionWordMemberIndex = 0;
      // remove the old reason
      p = this.cfg.getProductionAt ( old );
      // the next production we're going to process
      ++this.currentProductionIndex;
      p = this.cfg.getProductionAt ( this.currentProductionIndex );
      /*
       * as long as we're not finished processing the next production we will
       * not proceed to the next one
       */
      this.nextProduction = false;
    }
    else
      p = this.cfg.getProductionAt ( this.currentProductionIndex );

    FirstSet firstSet = this.firstSets.get ( p.getNonterminalSymbol () );
    FirstSet firstSetToAdd;
    // case 3
    if ( p.getProductionWord ().epsilon () )
    {
      this.modified = firstSet.epsilon ( true ) || this.modified;
      firstSet.get ( firstSet.size () - 1 ).setHighlighted ( true );
      this.nextProduction = true;

      // setup the reason
      this.reasonListModel.addElement(
          new PrettyString(
              new PrettyToken(
                    Messages.getString(
                          "FirstSetDialog.ReasonEpsilon", //$NON-NLS-1$
                          p.getNonterminalSymbol (),
                          p
                        ), Style.NONE
                  )
              )
          );
    }
    else
    {
      ProductionWordMember pwm = p.getProductionWord ().get (
          this.currentProductionWordMemberIndex );
      firstSetToAdd = new DefaultFirstSet(this.firstSets.get ( pwm ));
      // color all new added symbols red
      firstSet.unmarkAll ();
      for ( TerminalSymbol ts : firstSetToAdd )
        if ( firstSet.size () != 0 )
        {
          if ( !firstSet.contains ( ts ) )
            ts.setHighlighted ( true );
        }
        else
          ts.setHighlighted ( true );

      // case 1 and 2
      if ( !firstSetToAdd.epsilon () )
        this.lastWasEpsilon = false;
      else
        for ( TerminalSymbol ts : firstSetToAdd )
          if ( ts.equals ( new DefaultTerminalSymbol ( new DefaultSymbol () ) ) )
            firstSetToAdd.remove ( ts );
      
      this.modified = firstSet.add ( firstSetToAdd ) || this.modified;

      // setup the reason
      ProductionWord prefixOfPWM = getAlpha(p.getProductionWord ());
      if(!prefixOfPWM.epsilon () && pwm instanceof NonterminalSymbol)
        this.reasonListModel.addElement(
            new PrettyString(
                new PrettyToken(
                      Messages.getString(
                            "FirstSetDialog.ReasonX", //$NON-NLS-1$
                            p.getNonterminalSymbol (),
                            pwm,
                            p,
                            prefixOfPWM,
                            firstSetToAdd
                          ), Style.NONE
                    )
                )
            );
      else if(pwm instanceof NonterminalSymbol)
        this.reasonListModel.addElement(
            new PrettyString(
                new PrettyToken(
                      Messages.getString(
                            "FirstSetDialog.ReasonXEpsilon", //$NON-NLS-1$
                            p.getNonterminalSymbol (),
                            pwm,
                            p,
                            prefixOfPWM,
                            firstSetToAdd
                          ), Style.NONE
                    )
                )
            );
      else if(!prefixOfPWM.epsilon ())
        this.reasonListModel.addElement(
            new PrettyString(
                new PrettyToken(
                      Messages.getString(
                            "FirstSetDialog.Reasona", //$NON-NLS-1$
                            p.getNonterminalSymbol (),
                            firstSetToAdd,
                            p,
                            prefixOfPWM
                          ), Style.NONE
                    )
                )
            );
      else
        this.reasonListModel.addElement(
            new PrettyString(
                new PrettyToken(
                      Messages.getString(
                            "FirstSetDialog.ReasonaEpsilon", //$NON-NLS-1$
                            p.getNonterminalSymbol (),
                            firstSetToAdd,
                            p
                          ), Style.NONE
                    )
                )
            );

      /*
       * break condition
       */
      if ( this.currentProductionWordMemberIndex == p.getProductionWord ()
          .size () - 1
          || !this.lastWasEpsilon )
      {
        if ( this.lastWasEpsilon )
          // case 2.2
          this.modified = firstSet.epsilon ( true ) || this.modified;
        else
          // case 2.1
          this.lastWasEpsilon = true;
        this.nextProduction = true;
      }
      else
        ++this.currentProductionWordMemberIndex;
    }
    updateWordNavigation ();
    this.gui.jGTIFirstTable.repaint ();
  }


  /**
   * Production A -> \alphaD returns \alpha
   * 
   * @param pw The {@link ProductionWord}
   * @return Return \alpha for a production A -> \alphaD
   */
  private ProductionWord getAlpha ( final ProductionWord pw )
  {
    ArrayList < ProductionWordMember > pwm = new ArrayList < ProductionWordMember > ();
    for ( int i = 0 ; i < this.currentProductionWordMemberIndex ; ++i )
      pwm.add ( pw.get ( i ) );
    return new DefaultProductionWord ( pwm );
  }


  /**
   * Handles the previous button
   */
  public void handlePrevious ()
  {
    if ( !this.history.isEmpty () )
    {
      restoreHistoryEntry ();
      this.reasonListModel.remove ( this.reasonListModel.size () - 1 );
    }
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
    this.reasonListModel.removeAllElements ();

    this.gui.jGTIFirstTable.repaint ();
  }
}
