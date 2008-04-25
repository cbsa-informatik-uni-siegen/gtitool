package de.unisiegen.gtitool.ui.redoundo;


import java.util.Stack;

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
  private MainWindowForm mainWindow;


  /**
   * Allocates a new {@link RedoUndoHandler}
   * 
   * @param mainWindow The {@link MainWindowForm}
   */
  public RedoUndoHandler ( MainWindowForm mainWindow )
  {
    this.mainWindow = mainWindow;
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
    this.mainWindow.jMenuItemRedo.setEnabled ( false );
    this.mainWindow.jGTIToolBarButtonRedo.setEnabled ( false );
    this.mainWindow.jMenuItemUndo.setEnabled ( true );
    this.mainWindow.jGTIToolBarButtonUndo.setEnabled ( true );
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

    this.mainWindow.jMenuItemRedo.setEnabled ( !this.redoSteps.isEmpty () );
    this.mainWindow.jGTIToolBarButtonRedo.setEnabled ( !this.redoSteps
        .isEmpty () );
    this.mainWindow.jMenuItemUndo.setEnabled ( true );
    this.mainWindow.jGTIToolBarButtonUndo.setEnabled ( true );
    this.undoSteps.push ( step );

    step.redo ();
  }


  /**
   * Undos last step.
   */
  public final void undo ()
  {
    RedoUndoItem step = this.undoSteps.pop ();
    this.mainWindow.jMenuItemRedo.setEnabled ( true );
    this.mainWindow.jGTIToolBarButtonRedo.setEnabled ( true );
    this.mainWindow.jMenuItemUndo.setEnabled ( !this.undoSteps.isEmpty () );
    this.mainWindow.jGTIToolBarButtonUndo.setEnabled ( !this.undoSteps
        .isEmpty () );
    this.redoSteps.push ( step );

    step.undo ();
  }
}
