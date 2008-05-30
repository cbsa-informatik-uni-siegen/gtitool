package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.StateConfigDialogForm;
import de.unisiegen.gtitool.ui.redoundo.StateChangedItem;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The {@link StateConfigDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StateConfigDialog implements
    LogicClass < StateConfigDialogForm >
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
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The old state name.
   */
  private String oldName;


  /**
   * The old start state flag.
   */
  private boolean oldStartState;


  /**
   * The old final state flag.
   */
  private boolean oldFinalState;


  /**
   * Allocates a new {@link StateConfigDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   * @param state The {@link State}.
   * @param model The {@link Machine}
   */
  public StateConfigDialog ( JFrame parent, MachinePanel machinePanel,
      State state, DefaultMachineModel model )
  {
    logger.debug ( "StateConfigDialog", "allocate a new new state name dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.machinePanel = machinePanel;
    this.state = state;
    this.oldName = state.getName ();
    this.oldStartState = state.isStartState ();
    this.oldFinalState = state.isFinalState ();
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
        .addParseableChangedListener ( new ParseableChangedListener < State > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( State newState )
          {
            if ( newState == null )
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
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final StateConfigDialogForm getGUI ()
  {
    return this.gui;
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
    State activeState = this.gui.styledStateParserPanel.getParsedObject ();
    this.stateName = ( activeState == null ? null : activeState.getName () );

    if ( this.stateName != null
        && !this.stateName.equals ( this.state.getName () ) )
      try
      {
        this.state.setName ( this.stateName );
        this.model.getJGraph ().getGraphLayoutCache ().valueForCellChanged (
            this.state, this.stateName );
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
      }

    this.state.setFinalState ( this.gui.jGTICheckBoxFinalState.isSelected () );
    this.state.setStartState ( this.gui.jGTICheckBoxStartState.isSelected () );
    this.model.getGraphModel ().cellsChanged ( new Object []
    { this.state } );
    if ( !this.oldName.equals ( this.state.getName () )
        || this.oldStartState != this.state.isStartState ()
        || this.oldFinalState != this.state.isFinalState () )
    {
      StateChangedItem item = new StateChangedItem ( this.model.getJGraph (),
          this.state, this.oldName, this.oldStartState, this.oldFinalState );
      this.machinePanel.getRedoUndoHandler ().addItem ( item );
    }
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
}
