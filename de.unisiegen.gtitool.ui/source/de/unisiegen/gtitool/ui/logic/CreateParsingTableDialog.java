package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.CreateParsingTableDialogForm;
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
    STOP,

    /**
     * autostep button
     */
    AUTOSTEP;
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
   * Allocates a new {@link CreateParsingTableDialog}
   * 
   * @param parent
   * @param cfg
   */
  public CreateParsingTableDialog ( final JFrame parent, final CFG cfg )
  {
    this.parent = parent;
    this.cfg = cfg;
    this.parsingTable = new DefaultParsingTable ( this.cfg );
    this.gui = new CreateParsingTableDialogForm ( this.parent, this );
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
      case AUTOSTEP :
        this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( enabled );
        break;
    }
  }


  /**
   * handles the {@link JGTIToolBarButton} start
   */
  public void handleStart ()
  {
    this.parsingTable.createParsingTableStart ();
    enableButton ( Action.START, false );
  }


  /**
   * handles the {@link JGTIToolBarButton} next
   */
  public void handleNext ()
  {
    try
    {
      boolean nextAvailable = this.parsingTable.createParsingTableNextStep ();
      enableButton ( Action.NEXT, nextAvailable );
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
    boolean previousAvailable = this.parsingTable
        .createParsingTablePreviousStep ();
    enableButton ( Action.PREVIOUS, previousAvailable );
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
    enableButton ( Action.AUTOSTEP, false );
  }


  /**
   * handles the {@link JGTIToolBarButton} auto step
   */
  public void handleAutoStep ()
  {

  }

}
