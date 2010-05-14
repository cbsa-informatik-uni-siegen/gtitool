package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.FollowSetStepByStepTableColumnModel;
import de.unisiegen.gtitool.ui.model.FollowSetStepByStepTableModel;
import de.unisiegen.gtitool.ui.netbeans.FollowSetDialogForm;


/**
 * Represents the logic class for {@link FollowSetDialogForm}
 */
public class FollowSetDialog implements LogicClass < FollowSetDialogForm >
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
   * The {@link FollowSetDialogForm}
   */
  private FollowSetDialogForm gui;


  /**
   * The {@link CFG}
   */
  private DefaultCFG cfg;


  /**
   * The step history index
   */
  private int historyIndex;


  /**
   * blub
   */
  private ArrayList < ArrayList < Object >> followHistory;


  /**
   * follow sets
   */
  private HashMap < NonterminalSymbol, TerminalSymbolSet > followSets;


  /**
   * reason model
   */
  private DefaultListModel reasonModel;


  /**
   * follow set table model
   */
  private FollowSetStepByStepTableModel followSetsTableModel;


  /**
   * Allocates a new {@link FollowSetDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   */
  public FollowSetDialog ( final JFrame parent, final CFG cfg )
  {
    this.cfg = ( DefaultCFG ) cfg;
    this.cfg.calculateAllFollowSets2 ();
    this.gui = new FollowSetDialogForm ( parent, this );
    this.historyIndex = 0;
    this.followHistory = this.cfg.getFollowHistory ();
    this.followSets = new HashMap < NonterminalSymbol, TerminalSymbolSet > ();

    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
      this.followSets.put ( ns, new DefaultTerminalSymbolSet () );

    // center the dialog
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );

    // setup the table
    this.followSetsTableModel = new FollowSetStepByStepTableModel (
        this.followSets, this.cfg );
    this.gui.jGTIFollowTable.setModel ( this.followSetsTableModel );
    this.gui.jGTIFollowTable
        .setColumnModel ( new FollowSetStepByStepTableColumnModel () );
    this.gui.jGTIFollowTable.getTableHeader ().setReorderingAllowed ( false );

    // reason list
    this.reasonModel = new DefaultListModel ();
    this.gui.jGTIReasonList.setModel ( this.reasonModel );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public FollowSetDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * shows the dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
  }


  /**
   * hides the dialog
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * start calculating the follow sets
   */
  @SuppressWarnings ( "unchecked" )
  public void handleStart ()
  {
    enableButton ( Action.START, false );
    enableButton ( Action.PREVIOUS, false );
    enableButton ( Action.NEXT, true );
    enableButton ( Action.STOP, true );

    this.historyIndex = 0;

    ArrayList < Object > followData = this.followHistory.get ( 0 );
    this.followSets = ( HashMap < NonterminalSymbol, TerminalSymbolSet > ) followData
        .get ( 0 );
    this.followSetsTableModel.setFollowSet ( this.followSets );
    this.gui.jGTIFollowTable.repaint ();
  }


  /**
   * hightlights the current processed nonterminal
   */
  private void hightlightCurrentProcessedNonterminal ()
  {
    final NonterminalSymbol currentNonterminal = ( NonterminalSymbol ) this.followHistory
        .get ( this.historyIndex ).get ( 1 );
    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
      if ( ns.equals ( currentNonterminal ) )
        ns.setHighlighted ( true );
      else
        ns.setHighlighted ( false );
    this.gui.jGTIFollowTable.repaint ();
  }


  /**
   * next step in calculating the follow sets
   */
  @SuppressWarnings ( "unchecked" )
  public void handleNext ()
  {
    ++this.historyIndex;
    hightlightCurrentProcessedNonterminal ();
    ArrayList < Object > followData = this.followHistory
        .get ( this.historyIndex );

    this.followSets = ( HashMap < NonterminalSymbol, TerminalSymbolSet > ) followData
        .get ( 0 );
    final String reason = ( String ) followData.get ( 7 );
    this.reasonModel.addElement ( reason );

    this.followSetsTableModel.setFollowSet ( this.followSets );
    updateWordNavigation ();
    this.gui.jGTIFollowTable.repaint ();
  }


  /**
   * previous step in calculating the follow sets
   */
  @SuppressWarnings ( "unchecked" )
  public void handlePrevious ()
  {
    --this.historyIndex;
    this.reasonModel.remove ( this.reasonModel.size () - 1 );

    ArrayList < Object > previousEntry = this.followHistory
        .get ( this.historyIndex );
    this.followSets = ( HashMap < NonterminalSymbol, TerminalSymbolSet > ) previousEntry
        .get ( 0 );
    this.followSetsTableModel.setFollowSet ( this.followSets );
    hightlightCurrentProcessedNonterminal ();

    this.gui.jGTIFollowTable.repaint ();
    updateWordNavigation ();
  }


  /**
   * stops calculating the follow sets
   */
  public void handleStop ()
  {
    enableButton ( Action.START, true );
    enableButton ( Action.PREVIOUS, false );
    enableButton ( Action.NEXT, false );
    enableButton ( Action.STOP, false );

    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
    {
      this.followSets.put ( ns, new DefaultTerminalSymbolSet () );
      ns.setHighlighted ( false );
    }
    this.reasonModel.removeAllElements ();

    this.historyIndex = 0;

    this.gui.jGTIFollowTable.repaint ();
  }


  /**
   * Enables a specified Button
   * 
   * @param btn the button action that is taking place
   * @param enabled button enable state
   */
  private void enableButton ( final FollowSetDialog.Action btn,
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
    enableButton ( Action.PREVIOUS, this.historyIndex != 0 );
    boolean allFinished = this.historyIndex != this.followHistory.size () - 1;
    enableButton ( Action.NEXT, allFinished );
  }
}
