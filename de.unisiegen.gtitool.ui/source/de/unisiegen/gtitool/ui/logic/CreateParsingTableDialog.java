package de.unisiegen.gtitool.ui.logic;


import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;

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
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
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
   */
  public CreateParsingTableDialog ( final JFrame parent, final CFG cfg )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    this.parent = parent;
    this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );
    this.cfg.getTerminalSymbolSet ().add ( DefaultTerminalSymbol.EndMarker );
    this.parsingTable = new DefaultParsingTable ( this.cfg );
    this.gui = new CreateParsingTableDialogForm ( this.parent, this );

    this.bundle = java.util.ResourceBundle
        .getBundle ( "de/unisiegen/gtitool/ui/i18n/messages" ); //$NON-NLS-1$

    // setup the displayed nonterminals,terminals and start symbol
    this.gui.styledNonterminalSymbolSetParserPanel.setText ( this.cfg
        .getNonterminalSymbolSet () );
    this.gui.styledStartNonterminalSymbolParserPanel.setText ( this.cfg
        .getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanel.setText ( this.cfg
        .getTerminalSymbolSet () );

    // setup the grammar table
    this.gui.jGTIGrammarTable.setModel ( this.cfg );
    this.gui.jGTIGrammarTable.setColumnModel ( new GrammarColumnModel () );
    this.gui.jGTIGrammarTable
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTIGrammarTable.getTableHeader ().setReorderingAllowed ( false );

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


          public void previousStepRemoveEntry ()
          {
            removeDescription ();
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
        // description.add ( new PrettyToken ( Messages.getString (
        //            "CreateParsingTableDialog.TerminalInFirstSet", ts, p //$NON-NLS-1$
        // .getProductionWord () ), Style.NONE ) );
        description.add ( new PrettyToken ( Messages.getString (
            "CreateParsingTableDialog.TerminalInFirstSet", p, p //$NON-NLS-1$
                .getNonterminalSymbol (), ts, p.getProductionWord () ),
            Style.NONE ) );
        break;
    }

    DefaultListModel model = ( DefaultListModel ) this.gui.jGTIDescriptionList
        .getModel ();
    model.addElement ( description );

    this.gui.jGTIParsingTable.repaint ();
  }


  /**
   * Remove the last description row
   */
  void removeDescription ()
  {
    // remove the last description
    final DefaultListModel model = ( DefaultListModel ) this.gui.jGTIDescriptionList
        .getModel ();
    model.remove ( model.size () - 1 );

    // repaint the parsing table cause it was modified
    this.gui.jGTIParsingTable.repaint ();
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
    this.gui.jGTICurrentNonterminalLabel.setText ( this.bundle
        .getString ( "CreateParsingTableDialog.CurrentNonterminal" ) //$NON-NLS-1$
        + " " + this.parsingTable.getCurrentNonterminalSymbol () ); //$NON-NLS-1$
    this.gui.jGTICurrentTerminalLabel.setText ( this.bundle
        .getString ( "CreateParsingTableDialog.CurrentTerminal" ) //$NON-NLS-1$
        + " " + this.parsingTable.getCurrentTerminalSymbol () ); //$NON-NLS-1$
  }


  /**
   * Clears the displayed symbols which are currently processed
   */
  private void clearCurrentSymbols ()
  {
    this.gui.jGTICurrentNonterminalLabel.setText ( this.bundle
        .getString ( "CreateParsingTableDialog.CurrentNonterminal" ) ); //$NON-NLS-1$
    this.gui.jGTICurrentTerminalLabel.setText ( this.bundle
        .getString ( "CreateParsingTableDialog.CurrentTerminal" ) ); //$NON-NLS-1$
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
    this.parsingTable.createParsingTablePreviousStep ();
    this.gui.jGTIParsingTable.repaint ();
    setCurrentSymbols ();
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
