package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.ConfirmDialog;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.StateConfigDialog;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.StateChangedItem;


/**
 * A Popup Menu for {@link State}s
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
  private DefaultStateView state;


  /**
   * DefaultMachineModel {@link GraphModel}
   */
  private DefaultMachineModel model;


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
  private JFrame parent;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


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
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog (
            StatePopupMenu.this.parent, Messages.getString (
                "TransitionDialog.DeleteStateQuestion", //$NON-NLS-1$
                StatePopupMenu.this.state ), Messages
                .getString ( "TransitionDialog.DeleteStateTitle" ), true, true, //$NON-NLS-1$
            false );
        confirmDialog.show ();
        if ( confirmDialog.isConfirmed () )
        {
          StatePopupMenu.this.model.removeState ( StatePopupMenu.this.state,
              true );
        }
      }
    } );
    add ( this.delete );

    this.startState = new JCheckBoxMenuItem ( Messages
        .getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    // this.startState.setIcon ( new ImageIcon ( getClass ().getResource (
    // "/de/unisiegen/gtitool/ui/icon/popupMenu/start.png" ) ) ); //$NON-NLS-1$
    this.startState.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        StatePopupMenu.this.state.getState ().setStartState (
            !StatePopupMenu.this.state.getState ().isStartState () );
        if ( StatePopupMenu.this.state.getState ().isStartState () )
          GraphConstants.setBackground ( StatePopupMenu.this.state
              .getAttributes (), PreferenceManager.getInstance ()
              .getColorItemStateStart ().getColor () );
        else
          GraphConstants.setBackground ( StatePopupMenu.this.state
              .getAttributes (), PreferenceManager.getInstance ()
              .getColorItemStateBackground ().getColor () );

        StatePopupMenu.this.model.getGraphModel ().cellsChanged ( new Object []
        { StatePopupMenu.this.state } );
        StateChangedItem item = new StateChangedItem ( StatePopupMenu.this.model.getJGraph (),
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
    // this.finalState.setIcon ( new ImageIcon ( getClass ().getResource (
    // "/de/unisiegen/gtitool/ui/icon/popupMenu/final.png" ) ) ); //$NON-NLS-1$
    this.finalState.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        StatePopupMenu.this.state.getState ().setFinalState (
            !StatePopupMenu.this.state.getState ().isFinalState () );
        StatePopupMenu.this.model.getGraphModel ().cellsChanged ( new Object []
        { StatePopupMenu.this.state } );
        StateChangedItem item = new StateChangedItem ( StatePopupMenu.this.model.getJGraph (),
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
        .getString ( "MachinePanel.Configurate" ) ); //$NON-NLS-1$
    this.configurate.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/rename.png" ) ) ); //$NON-NLS-1$
    this.configurate.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        StateConfigDialog dialog = new StateConfigDialog (
            StatePopupMenu.this.parent, StatePopupMenu.this.machinePanel,
            StatePopupMenu.this.state.getState (), StatePopupMenu.this.model );
        dialog.show ();
      }
    } );
    add ( this.configurate );
  }
}
