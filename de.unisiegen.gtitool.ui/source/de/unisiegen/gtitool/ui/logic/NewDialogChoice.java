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
public class NewDialogChoice
{

  /** Signals the user choice */
  public enum Choice
  {
    /**
     * Machine is choosen.
     */
    Machine,

    /**
     * Grammar is choosen.
     */
    Grammar,
  }


  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogChoiceForm gui;


  /** The actual user choice */
  private Choice actualChoice = Choice.Machine;


  /** The parent Dialog containing this panel */
  private NewDialog parent;


  /**
   * Creates a new <code>NewDialogChoice</code>.
   * 
   * @param pParent The Dialog containing this panel
   */
  public NewDialogChoice ( NewDialog pParent )
  {
    this.parent = pParent;
    this.gui = new NewDialogChoiceForm ();
    this.gui.setLogic ( this );

  }


  /**
   * Handle Grammar Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleGrammarItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.Grammar;

  }


  /**
   * Handle Machine Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleMachineItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.Machine;

  }


  /**
   * Get the UserChoice
   * 
   * @return The user choice of this panel
   */
  public Choice getUserChoice ()
  {
    return this.actualChoice;
  }


  /**
   * Getter for the gui of this logic class
   * 
   * @return The {@link NewDialogChoiceForm}
   */
  public NewDialogChoiceForm getGui ()
  {
    return this.gui;
  }


  /**
   * Handle the cancel button pressed event
   */
  public void handleCancel ()
  {
    this.parent.getGui ().dispose ();
  }


  /**
   * Handle the next button pressed event
   */
  public void handleNextNewDialogChoice ()
  {
    this.parent.handleNextNewDialogChoice ();
  }

}
