package de.unisiegen.gtitool.ui.redoundo;


import java.util.Stack;

import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Handles the redo and undo actions.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class RedoUndoHandler
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
   * Allocate a new {@link RedoUndoHandler}
   * 
   * @param mainWindow The {@link MainWindowForm}
   */
  public RedoUndoHandler ( MainWindowForm mainWindow )
  {
    this.mainWindow = mainWindow;
  }


  /**
   * Add a undo step.
   * 
   * @param item The undo step to add.
   */
  public void addUndo ( RedoUndoItem item )
  {
    this.undoSteps.push ( item );
    this.redoSteps.clear ();
    this.mainWindow.jMenuItemRedo.setEnabled ( false );
    this.mainWindow.jGTIToolBarButtonRedo.setEnabled ( false );
    this.mainWindow.jMenuItemUndo.setEnabled ( true );
    this.mainWindow.jGTIToolBarButtonUndo.setEnabled ( true );
  }


  /**
   * Signal if there are any undo steps.
   * 
   * @return true if there are any undo steps, false else.
   */
  public boolean isRedoAble ()
  {
    return !this.redoSteps.empty ();
  }


  /**
   * Signal if there are any undo steps.
   * 
   * @return true if there are any undo steps, false else.
   */
  public boolean isUndoAble ()
  {
    return !this.undoSteps.isEmpty ();
  }


  /**
   * Redo last step.
   */
  public void redo ()
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
   * Undo last step.
   */
  public void undo ()
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
