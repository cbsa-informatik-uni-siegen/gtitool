package de.unisiegen.gtitool.ui.logic;


import java.util.ResourceBundle;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
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
   * The {@link ResourceBundle}
   */
  private ResourceBundle bundle;


  /**
   * The {@link CFG} for which the {@link ParsingTable} will be created
   */
  private CFG cfg;


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
    this.bundle = java.util.ResourceBundle
        .getBundle ( "de/unisiegen/gtitool/ui/i18n/messages" ); //$NON-NLS-1$
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


  public void handleOk ()
  {

  }


  public void handleStart ()
  {

  }


  public void handleNext ()
  {

  }


  public void handlePrevious ()
  {

  }


  public void handleStop ()
  {

  }
}
