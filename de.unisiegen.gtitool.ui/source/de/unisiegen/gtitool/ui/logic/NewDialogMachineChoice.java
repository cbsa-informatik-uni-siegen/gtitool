package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.core.machines.nfa.NFA;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogMachineChoiceForm;


/**
 * The {@link NewDialogChoice}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class NewDialogMachineChoice implements
    LogicClass < NewDialogMachineChoiceForm >
{

  /**
   * The user choice.
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
    this.gui = new NewDialogMachineChoiceForm ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final NewDialogMachineChoiceForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the user choice.
   * 
   * @return The user choice of this panel.
   */
  public final Choice getUserChoice ()
  {
    return this.actualChoice;
  }


  /**
   * Handles the cancel button pressed event.
   */
  public final void handleCancel ()
  {
    this.parent.getGUI ().dispose ();
  }


  /**
   * Handles {@link DFA} item state changed events.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleDFAItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.DFA;
    }
  }


  /**
   * Handles {@link ENFA} item state changed events.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleENFAItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.ENFA;
    }
  }


  /**
   * Handles the next button pressed event.
   */
  public final void handleNextMachineChoice ()
  {
    this.parent.handleNextMachineChoice ();
  }


  /**
   * Handles {@link NFA} item state changed events.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleNFAItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.NFA;
    }
  }


  /**
   * Handles {@link PDA} item state changed events.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handlePDAItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      this.actualChoice = Choice.PDA;
    }
  }


  /**
   * Handles the previous button pressed event.
   */
  public final void handlePreviousMachineChoice ()
  {
    this.parent.handlePreviousMachineChoice ();
  }
}
