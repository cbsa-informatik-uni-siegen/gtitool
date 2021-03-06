package de.unisiegen.gtitool.ui.redoundo;


import java.util.Stack;

import de.unisiegen.gtitool.ui.logic.MainWindow.ButtonState;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Handles the redo and undo actions.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class RedoUndoHandler
{

  /**
   * The saved undo steps.
   */
  private Stack < RedoUndoItem > undoSteps = new Stack < RedoUndoItem > ();


  /**
   * The saved redo steps.
   */
  private Stack < RedoUndoItem > redoSteps = new Stack < RedoUndoItem > ();


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * Allocates a new {@link RedoUndoHandler}
   * 
   * @param mainWindowForm The {@link MainWindowForm}
   */
  public RedoUndoHandler ( MainWindowForm mainWindowForm )
  {
    this.mainWindowForm = mainWindowForm;
  }


  /**
   * Adds a undo step.
   * 
   * @param item The undo step to add.
   */
  public final void addItem ( RedoUndoItem item )
  {
    this.undoSteps.push ( item );
    this.redoSteps.clear ();
    this.mainWindowForm.getLogic ().removeButtonState (
        ButtonState.ENABLED_REDO );
    this.mainWindowForm.getLogic ().addButtonState ( ButtonState.ENABLED_UNDO );
  }


  /**
   * Signals if there are any undo steps.
   * 
   * @return true if there are any undo steps, false else.
   */
  public final boolean isRedoAble ()
  {
    return !this.redoSteps.empty ();
  }


  /**
   * Signals if there are any undo steps.
   * 
   * @return true if there are any undo steps, false else.
   */
  public final boolean isUndoAble ()
  {
    return !this.undoSteps.isEmpty ();
  }


  /**
   * Redos last step.
   */
  public final void redo ()
  {
    RedoUndoItem step = this.redoSteps.pop ();

    if ( !this.redoSteps.isEmpty () )
    {
      this.mainWindowForm.getLogic ()
          .addButtonState ( ButtonState.ENABLED_REDO );
    }
    else
    {
      this.mainWindowForm.getLogic ().removeButtonState (
          ButtonState.ENABLED_REDO );
    }

    this.mainWindowForm.getLogic ().addButtonState ( ButtonState.ENABLED_UNDO );

    this.undoSteps.push ( step );

    step.redo ();
  }


  /**
   * Undos last step.
   */
  public final void undo ()
  {
    RedoUndoItem step = this.undoSteps.pop ();

    this.mainWindowForm.getLogic ().addButtonState ( ButtonState.ENABLED_REDO );

    if ( !this.undoSteps.isEmpty () )
    {
      this.mainWindowForm.getLogic ()
          .addButtonState ( ButtonState.ENABLED_UNDO );
    }
    else
    {
      this.mainWindowForm.getLogic ().removeButtonState (
          ButtonState.ENABLED_UNDO );
    }

    this.redoSteps.push ( step );

    step.undo ();
  }
}
