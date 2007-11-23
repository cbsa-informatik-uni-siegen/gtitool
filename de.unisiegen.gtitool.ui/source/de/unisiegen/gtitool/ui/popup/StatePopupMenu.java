package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * A Popup Menu for {@link State}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class StatePopupMenu extends JPopupMenu
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
   * The {@link JGraph}
   */
  private JGraph graph;


  /**
   * {@link GraphModel}
   */
  private DefaultGraphModel model;


  /**
   * The {@link Machine}
   */
  private Machine machine;


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
  private JMenuItem rename;


  /**
   * Allocates a new <code>StatePopupMenu</code>.
   * 
   * @param pGraph the JGraph containing the state
   * @param pModel the model containing the state
   * @param pState the state to open the popup menu
   * @param pMachine The {@link Machine}
   */
  public StatePopupMenu ( JGraph pGraph, DefaultGraphModel pModel,
      DefaultStateView pState, Machine pMachine )
  {
    this.machine = pMachine;
    this.graph = pGraph;
    this.model = pModel;
    this.state = pState;
    populateMenues ();

  }


  /**
   * Populates the menues of this popup menu.
   */
  protected void populateMenues ()
  {

    this.delete = new JMenuItem ( Messages.getString ( "MachinePanel.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {

        int choice = JOptionPane.NO_OPTION;
        String message = "Soll der Zustand \"" //$NON-NLS-1$
            + StatePopupMenu.this.state.toString ()
            + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
        choice = JOptionPane.showConfirmDialog ( null, message,
            "Zustand löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
        if ( choice == JOptionPane.YES_OPTION )
        {
          StatePopupMenu.this.model.remove ( StatePopupMenu.this.state
              .getRemoveObjects () );
          StatePopupMenu.this.machine.removeState ( StatePopupMenu.this.state
              .getState () );
        }

      }
    } );
    add ( this.delete );

    this.startState = new JCheckBoxMenuItem ( Messages
        .getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    this.startState.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/start.png" ) ) ); //$NON-NLS-1$
    this.startState.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        StatePopupMenu.this.state.getState ().setStartState (
            !StatePopupMenu.this.state.getState ().isStartState () );
        if ( StatePopupMenu.this.state.getState ().isStartState () )
          GraphConstants.setGradientColor ( StatePopupMenu.this.state
              .getAttributes (), PreferenceManager.getInstance ()
              .getColorItemStartState ().getColor () );
        else
          GraphConstants.setGradientColor ( StatePopupMenu.this.state
              .getAttributes (), PreferenceManager.getInstance ()
              .getColorItemState ().getColor () );

        StatePopupMenu.this.model.cellsChanged ( new Object []
        { StatePopupMenu.this.state } );
      }
    } );
    this.startState.setSelected ( this.state.getState ().isStartState () );
    add ( this.startState );

    this.finalState = new JCheckBoxMenuItem ( Messages
        .getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
    this.finalState.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/final.png" ) ) ); //$NON-NLS-1$
    this.finalState.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        StatePopupMenu.this.state.getState ().setFinalState (
            !StatePopupMenu.this.state.getState ().isFinalState () );
        StatePopupMenu.this.model.cellsChanged ( new Object []
        { StatePopupMenu.this.state } );
      }
    } );
    this.finalState.setSelected ( this.state.getState ().isFinalState () );
    add ( this.finalState );

    this.rename = new JMenuItem ( Messages.getString ( "MachinePanel.Rename" ) ); //$NON-NLS-1$
    this.rename.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/rename.png" ) ) ); //$NON-NLS-1$
    this.rename.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {
        while ( true )
        {
          String name = JOptionPane
              .showInputDialog ( Messages
                  .getString (
                      "MachinePanel.RenameText", StatePopupMenu.this.state.getState ().getName () ) ); //$NON-NLS-1$
          try
          {
            if ( name != null )
            {
              StatePopupMenu.this.state.getState ().setName ( name );
              StatePopupMenu.this.graph.getGraphLayoutCache ()
                  .valueForCellChanged ( StatePopupMenu.this.state, name );
            }
            break;
          }
          catch ( StateException e1 )
          {
            // Nothing to do
          }
        }
      }
    } );
    add ( this.rename );
  }
}
