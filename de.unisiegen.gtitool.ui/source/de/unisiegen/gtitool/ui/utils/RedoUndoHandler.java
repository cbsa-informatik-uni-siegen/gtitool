package de.unisiegen.gtitool.ui.utils;


import java.util.Stack;

import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Handles the redo and undo actions.
 * 
 * @author Benjamin Mies
 * 
 */
public class RedoUndoHandler
{

  /** The saved undo steps */
  private Stack < RedoUndoItem > undoSteps = new Stack < RedoUndoItem > ();


  /** The saved redo steps */
  private Stack < RedoUndoItem > redoSteps = new Stack < RedoUndoItem > ();


  /** The {@link DefaultMachineModel} */
  private DefaultMachineModel model;
  
  /** The {@link MainWindowForm} */
  private MainWindowForm mainWindow;


  /**
   * Allocate a new {@link RedoUndoHandler}
   * @param model The {@link DefaultMachineModel}
   * @param mainWindow The {@link MainWindowForm}
   */
  public RedoUndoHandler ( DefaultMachineModel model, MainWindowForm mainWindow )
  {
    this.model = model;
    this.mainWindow = mainWindow;
  }


  /**
   * Add a undo step
   * 
   * @param item The undo step to add
   */
  public void addUndo ( RedoUndoItem item )
  {
    this.undoSteps.push ( item );
    this.redoSteps.clear ();
    this.mainWindow.jMenuItemRedo.setEnabled ( false );
    this.mainWindow.jMenuItemUndo.setEnabled ( true );
  }

  /**
   * Undo last step
   */
  public void undo ()
  {
    RedoUndoItem step = this.undoSteps.pop ();
    this.mainWindow.jMenuItemRedo.setEnabled ( true );
    this.mainWindow.jMenuItemUndo.setEnabled ( !this.undoSteps.isEmpty () );
    this.redoSteps.push ( step );

    switch ( step.getAction () )
    {
      case STATE_ADDED :
      {
        // remove state
        this.model.removeState ( step.getStateView (), false );
        break;
      }

      case TRANSITION_ADDED :
      {
        // remove transition
        for ( DefaultTransitionView current : step.getTransitions () )
          this.model.removeTransition ( current, false );
        break;
      }

      case STATE_REMOVED :
      {
        // add state
        this.model.createStateView ( step.getStateView ().getXPosition (), step
            .getStateView ().getYPosition (), step.getStateView ().getState (), false );

        // add transitions
        for ( DefaultTransitionView current : step.getTransitions () )
        {
          this.model.createTransitionView ( current.getTransition (), current
              .getSourceView (), current.getTargetView (), false );
        }
        break;

      }

      case TRANSITION_REMOVED :
      {
        // add transitions
        for ( DefaultTransitionView current : step.getTransitions () )
        {
          this.model.createTransitionView ( current.getTransition (), current
              .getSourceView (), current.getTargetView (), false );
        }
        break;
      }
    }
  }


  /**
   * Redo last step
   */
  public void redo ()
  {
    RedoUndoItem step = this.redoSteps.pop ();

    this.mainWindow.jMenuItemRedo.setEnabled ( !this.redoSteps.isEmpty () );
    this.mainWindow.jMenuItemUndo.setEnabled ( true );
    this.undoSteps.push ( step );

    switch ( step.getAction () )
    {
      case STATE_ADDED :
      {
        // add state
        this.model.createStateView ( step.getStateView ().getXPosition (), step
            .getStateView ().getYPosition (), step.getStateView ().getState (), false );

        // add transitions
        for ( DefaultTransitionView current : step.getTransitions () )
        {
          this.model.createTransitionView ( current.getTransition (), current
              .getSourceView (), current.getTargetView (), false );
        }
        break;
      }

      case TRANSITION_ADDED :
      {

        // add transitions
        for ( DefaultTransitionView current : step.getTransitions () )
        {
          this.model.createTransitionView ( current.getTransition (), current
              .getSourceView (), current.getTargetView (), false );
        }
        break;
      }

      case STATE_REMOVED :
      {

        // remove state
        this.model.removeState ( step.getStateView (), false );
        break;

      }

      case TRANSITION_REMOVED :
      {
        // remove transition
        for ( DefaultTransitionView current : step.getTransitions () )
          this.model.removeTransition ( current, false );
        break;

      }
    }
  }

}
