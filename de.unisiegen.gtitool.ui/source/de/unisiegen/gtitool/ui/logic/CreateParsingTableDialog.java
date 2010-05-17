package de.unisiegen.gtitool.ui.logic;


import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable.EntryCause;
import de.unisiegen.gtitool.core.entities.listener.ParsingTableStepByStepListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.FirstSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.FirstSetTableModel;
import de.unisiegen.gtitool.ui.model.FollowSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.FollowSetTableModel;
import de.unisiegen.gtitool.ui.model.PTTableColumnModel;
import de.unisiegen.gtitool.ui.model.PTTableModel;
import de.unisiegen.gtitool.ui.netbeans.CreateParsingTableDialogForm;
import de.unisiegen.gtitool.ui.swing.JGTIButton;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton;


/**
 * The {@link CreateParsingTableDialog} (logic)
 * 
 * @author Christian Uhrhan
 */
public class CreateParsingTableDialog implements
    LogicClass < CreateParsingTableDialogForm >
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
   * The {@link CreateParsingTableDialogForm}
   */
  private CreateParsingTableDialogForm gui;


  /**
   * The parent {@link JFrame}
   */
  private JFrame parent;


  /**
   * The {@link CFG} for which the {@link ParsingTable} will be created
   */
  private CFG cfg;


  /**
   * The {@link ParsingTable}
   */
  private ParsingTable parsingTable;


  /**
   * The {@link ResourceBundle}
   */
  private ResourceBundle bundle;


  /**
   * Allocates a new {@link CreateParsingTableDialog}
   * 
   * @param parent
   * @param cfg
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public CreateParsingTableDialog ( final JFrame parent, final CFG cfg )
      throws TerminalSymbolSetException, NonterminalSymbolSetException,
      GrammarInvalidNonterminalException
  {
    this.parent = parent;
    this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );
    this.cfg.getTerminalSymbolSet ().add ( DefaultTerminalSymbol.EndMarker );
    this.parsingTable = new DefaultParsingTable ( this.cfg );

    this.gui = new CreateParsingTableDialogForm ( this.parent, this );
    // center the dialog
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );

    this.bundle = java.util.ResourceBundle
        .getBundle ( "de/unisiegen/gtitool/ui/i18n/messages" ); //$NON-NLS-1$

    // setup displayed follow/first sets
    getGUI ().jGTIFirstSetTable.setModel ( new FirstSetTableModel ( this.cfg ) );
    getGUI ().jGTIFirstSetTable
        .setColumnModel ( new FirstSetTableColumnModel () );
    getGUI ().jGTIFirstSetTable.getTableHeader ().setReorderingAllowed ( false );
    getGUI ().jGTIFirstSetTable.setCellSelectionEnabled ( false );

    // FollowSetTable
    getGUI ().jGTIFollowSetTable
        .setModel ( new FollowSetTableModel ( this.cfg ) );
    getGUI ().jGTIFollowSetTable
        .setColumnModel ( new FollowSetTableColumnModel () );
    getGUI ().jGTIFollowSetTable.getTableHeader ()
        .setReorderingAllowed ( false );
    getGUI ().jGTIFollowSetTable.setCellSelectionEnabled ( false );

    // setup the parsing table model
    this.gui.jGTIParsingTable.setModel ( new PTTableModel ( this.cfg,
        this.parsingTable ) );
    this.gui.jGTIParsingTable.setColumnModel ( new PTTableColumnModel (
        this.cfg.getTerminalSymbolSet () ) );
    this.gui.jGTIParsingTable.getTableHeader ().setReorderingAllowed ( false );

    // setup the description table (outline)
    DefaultListModel descriptionListmodel = new DefaultListModel ();
    this.gui.jGTIDescriptionList.setModel ( descriptionListmodel );

    // setup the ParsingTableStepByStepListener
    this.parsingTable
        .addParsingTableStepByStepListener ( new ParsingTableStepByStepListener ()
        {

          public void productionAddedAsEntry ( Production p, TerminalSymbol ts,
              EntryCause cause )
          {
            addDescription ( p, ts, cause );
          }


          public void previousStepRemoveEntry ( int amount )
          {
            removeDescription ( amount );
          }
        } );

    updateStatus ();

    this.gui
        .setTitle ( Messages.getString ( "CreateParsingTableDialog.Title" ) ); //$NON-NLS-1$
  }


  /**
   * Adds a new description line to the description list
   * 
   * @param p The {@link Production} that was added to the {@link ParsingTable}
   * @param ts The current processed {@link TerminalSymbol}
   * @param cause The {@link ParsingTable.EntryCause} which indicates why the
   *          {@link Production} was added to the {@link ParsingTable}
   */
  void addDescription ( final Production p, final TerminalSymbol ts,
      final EntryCause cause )
  {
    PrettyString description = new PrettyString ();

    switch ( cause )
    {
      case EPSILON_DERIVATION_AND_FOLLOWSET :
        description.add ( new PrettyToken ( Messages.getString (
            "CreateParsingTableDialog.EpsilonDerivationAndFollowSet", p, p //$NON-NLS-1$
                .getNonterminalSymbol (), ts, p.getProductionWord () ),
            Style.NONE ) );
        break;
      case NOCAUSE :
        return;
      case TERMINAL_IN_FIRSTSET :
        description.add ( new PrettyToken ( Messages.getString (
            "CreateParsingTableDialog.TerminalInFirstSet", p, p //$NON-NLS-1$
                .getNonterminalSymbol (), ts, p.getProductionWord () ),
            Style.NONE ) );
        break;
    }

    DefaultListModel model = ( DefaultListModel ) this.gui.jGTIDescriptionList
        .getModel ();
    model.addElement ( description );
  }


  /**
   * Remove the last description row
   * 
   * @param amount
   */
  void removeDescription ( int amount )
  {
    // remove the last description
    final DefaultListModel model = ( DefaultListModel ) this.gui.jGTIDescriptionList
        .getModel ();
    while ( amount-- > 0 )
      model.remove ( model.size () - 1 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public CreateParsingTableDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Enables a specified Button
   * 
   * @param btn the button action that is taking place
   * @param enabled button enable state
   */
  private void enableButton ( final CreateParsingTableDialog.Action btn,
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
   * Display the current processed {@link NonterminalSymbol} and
   * {@link TerminalSymbol}
   */
  private void setCurrentSymbols ()
  {
    this.parsingTable.getCurrentNonterminalSymbol ().setHighlighted ( true );
    this.parsingTable.getCurrentTerminalSymbol ().setHighlighted ( true );

    this.gui.jGTIParsingTable.getTableHeader ().repaint ();
  }


  /**
   * Clears the displayed symbols which are currently processed
   */
  private void clearCurrentSymbols ()
  {
    this.parsingTable.getCurrentNonterminalSymbol ().setHighlighted ( false );
    this.parsingTable.getCurrentTerminalSymbol ().setHighlighted ( false );

    this.gui.jGTIParsingTable.getTableHeader ().repaint ();
  }


  /**
   * Updates the word navigation buttons 'next' and 'previous'
   */
  private void updateWordNavigation ()
  {
    enableButton ( Action.NEXT, this.parsingTable.isNextStepAvailable () );
    enableButton ( Action.PREVIOUS, this.parsingTable
        .isPreviousStepAvailable () );
  }


  /**
   * handles the {@link JGTIToolBarButton} start
   */
  public void handleStart ()
  {
    this.parsingTable.createParsingTableStart ();
    enableButton ( Action.START, false );
    enableButton ( Action.STOP, true );

    setCurrentSymbols ();
    updateStatus ();
    updateWordNavigation ();
  }


  /**
   * updates the status label
   */
  private void updateStatus ()
  {
    final String status;
    if ( this.parsingTable.isNextStepAvailable () )
      status = this.bundle
          .getString ( "CreateParsingTableDialog.LabelStatusUnfinished" ); //$NON-NLS-1$
    else
      status = this.bundle
          .getString ( "CreateParsingTableDialog.LabelStatusFinished" ); //$NON-NLS-1$
    this.gui.jGTIStatusLabel.setText ( this.bundle
        .getString ( "CreateParsingTableDialog.LabelStatus" ) + status ); //$NON-NLS-1$
  }


  /**
   * handles the {@link JGTIToolBarButton} next
   */
  public void handleNext ()
  {
    try
    {
      clearCurrentSymbols ();
      this.parsingTable.createParsingTableNextStep ();
      setCurrentSymbols ();

      if ( !this.parsingTable.isNextStepAvailable () )
      {
        clearCurrentSymbols ();
        updateStatus ();
      }

      this.gui.jGTIParsingTable.repaint ();
      updateWordNavigation ();
    }
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * handles the {@link JGTIToolBarButton} previous
   */
  public void handlePrevious ()
  {
    clearCurrentSymbols ();
    this.parsingTable.createParsingTablePreviousStep ();
    setCurrentSymbols ();
    this.gui.jGTIParsingTable.repaint ();
    updateStatus ();
    updateWordNavigation ();
  }


  /**
   * handles the {@link JGTIToolBarButton} stop
   */
  public void handleStop ()
  {
    enableButton ( Action.START, true );
    enableButton ( Action.NEXT, false );
    enableButton ( Action.PREVIOUS, false );
    enableButton ( Action.STOP, false );
    clearCurrentSymbols ();
    this.parsingTable.clear ();
    this.gui.jGTIParsingTable.repaint ();
    ( ( DefaultListModel ) this.gui.jGTIDescriptionList.getModel () )
        .removeAllElements ();
  }


  /**
   * handles the {@link JGTIButton} button
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * Show up the dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
  }
}
