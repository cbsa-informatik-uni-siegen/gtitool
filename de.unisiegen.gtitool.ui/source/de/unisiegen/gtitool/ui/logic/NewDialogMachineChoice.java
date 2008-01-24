package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogMachineChoiceForm;


/**
 * The {@link NewDialogChoice}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class NewDialogMachineChoice
{

  /**
   * Signals the user choice
   */
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
    PDA;
  }


  /**
   * The parent Dialog containing this panel.
   */
  private NewDialog parent;


  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogMachineChoiceForm gui;


  /**
   * The actual user choice.
   */
  private Choice actualChoice = Choice.DFA;


  /**
   * Creates a new {@link NewDialogMachineChoice}.
   * 
   * @param parent The Dialog containing this panel.
   */
  public NewDialogMachineChoice ( NewDialog parent )
  {
    this.parent = parent;
    this.gui = new NewDialogMachineChoiceForm ();
    this.gui.setLogic ( this );
  }


  /**
   * Getter for the gui of this logic class.
   * 
   * @return The {@link NewDialogMachineChoiceForm}.
   */
  public final NewDialogMachineChoiceForm getGui ()
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
   * Handle DFA Item State changed.
   * 
   * @param evt The {@link ItemEvent}.
   */
  public final void handleDFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.DFA;
    }
  }


  /**
   * Handle EDFA Item State changed.
   * 
   * @param evt The {@link ItemEvent}.
   */
  public final void handleEDFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.ENFA;
  }


  /**
   * Handle the next button pressed event.
   */
  public final void handleNextMachineChoice ()
  {
    this.parent.handleNextMachineChoice ();
  }


  /**
   * Handle NFA Item State changed.
   * 
   * @param evt The {@link ItemEvent}.
   */
  public final void handleNFAItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.NFA;
    }
  }


  /**
   * Handle the previous button pressed event.
   */
  public final void handlePreviousMachineChoice ()
  {
    this.parent.handlePreviousMachineChoice ();
  }


  /**
   * Handle StackMachine Item State changed.
   * 
   * @param evt The {@link ItemEvent}.
   */
  public final void handleStackMachineItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.PDA;
  }
}
