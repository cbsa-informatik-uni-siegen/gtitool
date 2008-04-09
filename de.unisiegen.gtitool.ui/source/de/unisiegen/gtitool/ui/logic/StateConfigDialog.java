package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.StateConfigDialogForm;


/**
 * The {@link StateConfigDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StateConfigDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( StateConfigDialog.class );


  /**
   * The {@link StateConfigDialogForm}.
   */
  private StateConfigDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The result stateName ;
   */
  private String stateName;


  /**
   * The {@link State}
   */
  private State state;


  /**
   * The {@link DefaultMachineModel}
   */
  private DefaultMachineModel model;


  /**
   * Allocates a new {@link StateConfigDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param state The {@link State}.
   * @param model The {@link Machine}
   */
  public StateConfigDialog ( JFrame parent, State state,
      DefaultMachineModel model )
  {
    logger.debug ( "StateConfigDialog", "allocate a new new state name dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.state = state;
    this.model = model;
    this.stateName = null;
    this.gui = new StateConfigDialogForm ( this, parent );
    this.gui.jGTICheckBoxFinalState.setSelected ( this.state.isFinalState () );
    this.gui.jGTICheckBoxStartState.setSelected ( this.state.isStartState () );
    this.gui.styledStateParserPanel.setText ( state );
    this.gui.jGTILabelRename.setText ( Messages.getString (
        "NewStateNameDialog.RenameText", state ) ); //$NON-NLS-1$

    /*
     * State changed listener
     */
    this.gui.styledStateParserPanel
        .addStateChangedListener ( new StateChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void stateChanged ( State pNewState )
          {
            if ( pNewState == null )
            {
              StateConfigDialog.this.gui.jGTIButtonOk.setEnabled ( false );
            }
            else
            {
              StateConfigDialog.this.gui.jGTIButtonOk.setEnabled ( true );
            }
          }
        } );
  }


  /**
   * Returns the stateName.
   * 
   * @return The stateName.
   * @see #stateName
   */
  public final String getStateName ()
  {
    return this.stateName;
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.setVisible ( false );
    this.stateName = null;
    this.gui.dispose ();
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.setVisible ( false );
    State activeState = this.gui.styledStateParserPanel.getState ();
    this.stateName = ( activeState == null ? null : activeState.getName () );
    this.gui.dispose ();
  }


  /**
   * Shows the {@link StateConfigDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the new state name dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }


  /**
   * Handle status of the final state checkbox changed
   * 
   * @param status the new final state status
   */
  public void finalStateValueChanged ( boolean status )
  {
    this.state.setFinalState ( status );
    this.model.getGraphModel ().cellsChanged ( new Object []
    { this.state } );

  }


  /**
   * Handle status of the start state checkbox changed
   * 
   * @param status the new start state status
   */
  public void startStateValueChanged ( boolean status )
  {
    this.state.setStartState ( status );
    this.model.getGraphModel ().cellsChanged ( new Object []
    { this.state } );

  }
}
