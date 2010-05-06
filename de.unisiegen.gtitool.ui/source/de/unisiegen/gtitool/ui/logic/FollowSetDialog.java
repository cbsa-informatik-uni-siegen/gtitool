package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.i18n.Messages;
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
    this.cfg.calculateAllFollowSets ();
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

    System.err.println ( "Steps: " + this.followHistory.size () );
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
  }


  /**
   * next step in calculating the follow sets
   */
  @SuppressWarnings ( "unchecked" )
  public void handleNext ()
  {
    ArrayList < Object > followData = this.followHistory
        .get ( this.historyIndex );

    this.followSets = ( HashMap < NonterminalSymbol, TerminalSymbolSet > ) followData
        .get ( 0 );
    final NonterminalSymbol ns = ( NonterminalSymbol ) followData.get ( 1 );
    final Production p = ( Production ) followData.get ( 2 );
    final ProductionWord rightSide = ( ProductionWord ) followData.get ( 3 );
    final int rightSideIndex = ( ( Integer ) followData.get ( 4 ) ).intValue ();
    final ProductionWord rest = ( ProductionWord ) followData.get ( 5 );
    final int cause = ( ( Integer ) followData.get ( 6 ) ).intValue ();
    printReason ( ns, p, rightSide, rightSideIndex, rest, cause );

    this.followSetsTableModel.setFollowSet ( this.followSets );
    ++this.historyIndex;
    updateWordNavigation ();
    this.gui.jGTIFollowTable.repaint ();
  }


  /**
   * prints the reason that causes the last action in calculating the follow
   * sets to take place
   * 
   * @param ns The {@link NonterminalSymbol} currently processed
   * @param p The {@link Production}
   * @param rightSide The {@link ProductionWord}
   * @param rightSideIndex Index of currently processed element of the right
   *          side of p
   * @param beta The {@link ProductionWord}
   * @param cause Which cases causes the last action to take place
   */
  private void printReason ( final NonterminalSymbol ns, final Production p,
      final ProductionWord rightSide, final int rightSideIndex,
      final ProductionWord beta, final int cause )
  {
    switch ( cause )
    {
      case 1 :
        this.reasonModel.addElement ( Messages.getString (
            "FollowSetDialog.ReasonCaseOne", ns ) ); //$NON-NLS-1$
        break;
      case 2 :
        this.reasonModel.addElement ( Messages.getString (
            "FollowSetDialog.ReasonCaseTwo", //$NON-NLS-1$
            p.getNonterminalSymbol (), ns,
            getAlpha ( rightSide, rightSideIndex ), beta ) );
        break;
      case 3 :
        this.reasonModel.addElement ( Messages.getString (
            "FollowSetDialog.ReasonCaseThree", //$NON-NLS-1$
            p.getNonterminalSymbol (), ns,
            getAlpha ( rightSide, rightSideIndex ) ) );
        break;
      case 4 :
        this.reasonModel.addElement ( Messages.getString (
            "FollowSetDialog.ReasonCaseFour", //$NON-NLS-1$
            p.getNonterminalSymbol (), ns,
            getAlpha ( rightSide, rightSideIndex ), beta ) );
        break;
    }
  }


  /**
   * A -> \alphaB\beta => returns \alpha
   * 
   * @param rightSide The {@link ProductionWord}
   * @param indexOfProcessedNonterminal
   * @return A -> \alphaB\beta => returns \alpha
   */
  private ProductionWord getAlpha ( final ProductionWord rightSide,
      final int indexOfProcessedNonterminal )
  {
    ArrayList < ProductionWordMember > pwm = new ArrayList < ProductionWordMember > ();
    for ( int i = 0 ; i < indexOfProcessedNonterminal ; ++i )
      pwm.add ( rightSide.get ( i ) );
    return new DefaultProductionWord ( pwm );
  }


  /**
   * previous step in calculating the follow sets
   */
  @SuppressWarnings ( "unchecked" )
  public void handlePrevious ()
  {
    if ( this.historyIndex > 0 )
    {
      --this.historyIndex;

      // TODO: works if the rest of this.reasonModel is implemented
      // this.reasonModel.remove ( this.reasonModel.size () - 1 );

      ArrayList < Object > previousEntry = this.followHistory
          .get ( this.historyIndex );
      this.followSets = ( HashMap < NonterminalSymbol, TerminalSymbolSet > ) previousEntry
          .get ( 0 );
      this.followSetsTableModel.setFollowSet ( this.followSets );

      this.gui.jGTIFollowTable.repaint ();
    }
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
      this.followSets.put ( ns, new DefaultTerminalSymbolSet () );
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
