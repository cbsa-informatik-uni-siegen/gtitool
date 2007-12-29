package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogMachineChoiceForm;


/**
 * The <code>NewDialogChoice</code>.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialogMachineChoice
{

  /** Signals the user choice */
  public enum Choice
  {
    /**
     * DFA is choosen.
     */
    DFA,

    /**
     * NFA is choosen.
     */
    NFA,

    /**
     * ENFA is choosen.
     */
    ENFA,

    /**
     * PDA is choosen.
     */
    PDA
  }


  /** The parent Dialog containing this panel */
  private NewDialog parent;


  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogMachineChoiceForm gui;


  /** The actual user choice */
  Choice actualChoice = Choice.DFA;


  /**
   * Creates a new <code>NewDialogChoice</code>.
   * 
   * @param pParent The Dialog containing this panel
   */
  public NewDialogMachineChoice ( NewDialog pParent )
  {
    this.parent = pParent;
    this.gui = new NewDialogMachineChoiceForm ();
    this.gui.setLogic ( this );
  }


  /**
   * Handle DFA Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleDFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.DFA;
  }


  /**
   * Handle NFA Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleNFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.NFA;
  }


  /**
   * Handle EDFA Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleEDFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.ENFA;
  }


  /**
   * Handle StackMachine Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleStackMachineItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.PDA;
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
   * @return The {@link NewDialogMachineChoiceForm}
   */
  public NewDialogMachineChoiceForm getGui ()
  {
    return this.gui;
  }


  /**
   * Handle the next button pressed event
   */
  public void handleNextMachineChoice ()
  {
    this.parent.handleNextMachineChoice ();

  }


  /**
   * Handle the previous button pressed event
   */
  public void handlePreviousMachineChoice ()
  {
    this.parent.handlePreviousMachineChoice ();

  }


  /**
   * Handle the cancel button pressed event
   */
  public void handleCancel ()
  {
    this.parent.getGui ().dispose ();

  }

}
