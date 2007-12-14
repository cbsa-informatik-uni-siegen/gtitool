package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogGrammarChoiceForm;


/**
 * The <code>NewDialogChoice</code>.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialogGrammarChoice
{

  /** Signals the user choice */
  public enum Choice
  {
    /**
     * Contextfree is choosen.
     */
    Contextfree,

    /**
     * ContextSensitiv is choosen.
     */
    ContextSensitiv,

    /**
     * Regular is choosen.
     */
    Regular
  }


  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogGrammarChoiceForm gui;


  /** The actual user choice */
  Choice actualChoice = Choice.Contextfree;


  /** The parent Dialog containing this panel */
  private NewDialog parent;


  /**
   * Creates a new <code>NewDialogChoice</code>.
   * 
   * @param pParent The Dialog containing this panel
   */
  public NewDialogGrammarChoice ( NewDialog pParent )
  {
    this.parent = pParent;
    this.gui = new NewDialogGrammarChoiceForm ();
    this.gui.setLogic ( this );
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
   * @return The {@link NewDialogGrammarChoiceForm}
   */
  public NewDialogGrammarChoiceForm getGui ()
  {
    return this.gui;
  }


  /**
   * Handle the next button pressed event
   */
  public void handleNextGrammarChoice ()
  {
    this.parent.handleNextGrammarChoice ();

  }


  /**
   * Handle the previous button pressed event
   */
  public void handlePreviousGrammarChoice ()
  {
    this.parent.handlePreviousGrammarChoice ();

  }


  /**
   * Handle the cancel button pressed event
   */
  public void handleCancel ()
  {
    this.parent.getGui ().dispose ();
  }


  /**
   * Handle Regular Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleRegularGrammarItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.Regular;
  }


  /**
   * Handle Contextfree Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleContextFreeItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.Contextfree;

  }


  /**
   * Handle ContextSensitiv Item State changed
   * 
   * @param evt The {@link ItemEvent}
   */
  public void handleContextSensitivItemStateChanged ( ItemEvent evt )
  {
    if ( evt.getStateChange () == ItemEvent.SELECTED )
      this.actualChoice = Choice.ContextSensitiv;

  }

}
