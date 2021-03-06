package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.StateConfigDialog;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.redoundo.StateChangedItem;


/**
 * A {@link JPopupMenu} for {@link State}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class StatePopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -9006449214284785143L;


  /**
   * The {@link DefaultStateView}
   */
  protected DefaultStateView state;


  /**
   * DefaultMachineModel {@link GraphModel}
   */
  protected DefaultMachineModel model;


  /**
   * The delete item
   */
  private JMenuItem delete;


  /**
   * The start state checkbox item
   */
  private JCheckBoxMenuItem startState;


  /**
   * The final state checkbox item
   */
  private JCheckBoxMenuItem finalState;


  /**
   * The rename checkbox item
   */
  private JMenuItem configurate;


  /**
   * The parent {@link JFrame}.
   */
  protected JFrame parent;


  /**
   * The {@link MachinePanel}.
   */
  protected MachinePanel machinePanel;


  /**
   * Allocates a new {@link StatePopupMenu}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   * @param model the model containing the state
   * @param state the state to open the popup menu
   */
  public StatePopupMenu ( JFrame parent, MachinePanel machinePanel,
      DefaultMachineModel model, DefaultStateView state )
  {
    this.parent = parent;
    this.machinePanel = machinePanel;
    this.model = model;
    this.state = state;
    populateMenues ();
  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {
    this.delete = new JMenuItem ( Messages.getString ( "MachinePanel.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        StatePopupMenu.this.machinePanel
            .deleteState ( StatePopupMenu.this.state );
      }
    } );
    add ( this.delete );

    this.startState = new JCheckBoxMenuItem ( Messages
        .getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    this.startState.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        StatePopupMenu.this.state.getState ().setStartState (
            !StatePopupMenu.this.state.getState ().isStartState () );

        StatePopupMenu.this.model.getGraphModel ().cellsChanged ( new Object []
        { StatePopupMenu.this.state } );

        StateChangedItem item = new StateChangedItem (
            StatePopupMenu.this.model.getJGTIGraph (),
            StatePopupMenu.this.state.getState (), StatePopupMenu.this.state
                .getState ().getName (), !StatePopupMenu.this.state.getState ()
                .isStartState (), StatePopupMenu.this.state.getState ()
                .isFinalState () );
        StatePopupMenu.this.machinePanel.getRedoUndoHandler ().addItem ( item );
      }
    } );
    this.startState.setSelected ( this.state.getState ().isStartState () );
    add ( this.startState );

    this.finalState = new JCheckBoxMenuItem ( Messages
        .getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
    this.finalState.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        StatePopupMenu.this.state.getState ().setFinalState (
            !StatePopupMenu.this.state.getState ().isFinalState () );

        StatePopupMenu.this.model.getGraphModel ().cellsChanged ( new Object []
        { StatePopupMenu.this.state } );

        StateChangedItem item = new StateChangedItem (
            StatePopupMenu.this.model.getJGTIGraph (),
            StatePopupMenu.this.state.getState (), StatePopupMenu.this.state
                .getState ().getName (), StatePopupMenu.this.state.getState ()
                .isStartState (), !StatePopupMenu.this.state.getState ()
                .isFinalState () );
        StatePopupMenu.this.machinePanel.getRedoUndoHandler ().addItem ( item );
      }
    } );
    this.finalState.setSelected ( this.state.getState ().isFinalState () );
    add ( this.finalState );

    this.configurate = new JMenuItem ( Messages
        .getString ( "MachinePanel.Properties" ) ); //$NON-NLS-1$
    this.configurate.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/rename.png" ) ) ); //$NON-NLS-1$
    this.configurate.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        StateConfigDialog dialog = new StateConfigDialog (
            StatePopupMenu.this.parent, StatePopupMenu.this.machinePanel,
            StatePopupMenu.this.state.getState (), StatePopupMenu.this.model );
        dialog.show ();

        // Update the machine table status
        StatePopupMenu.this.machinePanel.updateMachineTableStatus ();
      }
    } );
    add ( this.configurate );
  }
}
