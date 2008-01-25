package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import de.unisiegen.gtitool.ui.netbeans.NewDialogChoiceForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;


/**
 * Dialog used to choose between creating a new Grammar or a new Machine
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class NewDialogChoice
{

  /** Signals the user choice */
  public enum Choice
  {
    /**
     * Machine is choosen.
     */
    MACHINE,

    /**
     * Grammar is choosen.
     */
    GRAMMAR;
  }


  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogChoiceForm gui;


  /**
   * The actual user choice.
   */
  private Choice actualChoice = Choice.MACHINE;


  /**
   * The parent Dialog containing this panel.
   */
  private NewDialog parent;


  /**
   * Creates a new {@link NewDialogChoice}.
   * 
   * @param parent The dialog containing this panel
   */
  public NewDialogChoice ( NewDialog parent )
  {
    this.parent = parent;
    this.gui = new NewDialogChoiceForm ();
    this.gui.setLogic ( this );
  }


  /**
   * Getter for the gui of this logic class.
   * 
   * @return The {@link NewDialogChoiceForm}
   */
  public final NewDialogChoiceForm getGui ()
  {
    return this.gui;
  }


  /**
   * Get the user choice.
   * 
   * @return The user choice of this panel.
   */
  public final Choice getUserChoice ()
  {
    return this.actualChoice;
  }


  /**
   * Handle the cancel button pressed event.
   */
  public final void handleCancel ()
  {
    this.parent.getGui ().dispose ();
  }


  /**
   * Handle Grammar Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public final void handleGrammarItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.GRAMMAR;
    }
  }


  /**
   * Handle machine item state changed.
   * 
   * @param evt The {@link ItemEvent}
   */
  public final void handleMachineItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.MACHINE;
    }
  }


  /**
   * Handle the next button pressed event.
   */
  public final void handleNextNewDialogChoice ()
  {
    this.parent.handleNextNewDialogChoice ();
  }
}
