package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;

/**
 * 
 * The Model for the {@link Machine}s
 *
 * @author Benjamin Mies
 * @version $Id$
 *
 */
public class DefaultMachineModel
{

  /** The {@link Machine} */
  private Machine machine;

  /** A list of all <code>DefaultStateView</code>s */
  ArrayList < DefaultStateView > stateViewList = new ArrayList < DefaultStateView > ();

  /** A list of all <code>DefaultTransitionView</code>s */
  ArrayList < DefaultTransitionView > transitionViewList = new ArrayList < DefaultTransitionView > ();
  
  /** The {@link MachineTableModel} */
  private MachineTableModel tableModel;

  /**
   * 
   * Allocate a new {@link DefaultMachineModel}
   *
   * @param pMachine The {@link Machine} 
   */
  public DefaultMachineModel ( Machine pMachine )
  {
    this.machine = pMachine;
    this.tableModel = new MachineTableModel ( this.machine.getAlphabet ());
  }

  /**
   * 
   * Get the {@link DefaultStateView} for a {@link State}
   *
   * @param state The {@link State}
   * @return The {@link DefaultStateView} 
   */
  public DefaultStateView getStateViewForState ( State state )
  {
    for ( DefaultStateView view : this.stateViewList )
    {
      if ( view.getState ().equals ( state ) )
        return view;
    }
    return null;
  }

  /**
   * 
   * Get the {@link DefaultTransitionView} for a {@link Transition}
   *
   * @param transition The {@link Transition}
   * @return The {@link DefaultTransitionView} 
   */
  public DefaultTransitionView getTransitionViewForTransition (
      Transition transition )
  {
    for ( DefaultTransitionView view : this.transitionViewList )
    {
      if ( view.getTransition ().equals ( transition ) )
        return view;
    }
    return null;
  }


  /**
   * Create a new State view
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @param pState the state represented via this view
   * @return {@link DefaultStateView} the new created state view
   */
  public DefaultStateView createStateView ( double x, double y, State pState )
  {
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$
    DefaultStateView cell = new DefaultStateView ( pState, pState.getName () );

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x - 35, y - 35, 70, 70 ) );

    // Set fill color
    if ( pState.isStartState () )
      GraphConstants.setGradientColor ( cell.getAttributes (),
          PreferenceManager.getInstance ().getColorItemStartState ()
              .getColor () );
    else
      GraphConstants.setGradientColor ( cell.getAttributes (),
          PreferenceManager.getInstance ().getColorItemState ().getColor () );
    GraphConstants.setOpaque ( cell.getAttributes (), true );

    // Set black border
    GraphConstants.setBorderColor ( cell.getAttributes (), Color.black );

    // Set the line width
    GraphConstants.setLineWidth ( cell.getAttributes (), 1 );

    // Add a Floating Port
    cell.addPort ();

    this.stateViewList.add ( cell );
    this.tableModel.addState ( pState );

    return cell;
  }


  /**
   * Create a new Transition
   * 
   * @param graph The {@link JGraph}
   * @param pTransition The {@link Transition}
   * @param source The source of the new Transition
   * @param target The target of the new Transition
   * @param symbols The Symbols for the new Transition
   */
  public void createTransitionView ( JGraph graph, Transition pTransition,
      DefaultStateView source, DefaultStateView target, Alphabet symbols )
  {
    DefaultTransitionView newEdge = new DefaultTransitionView ( pTransition,
        source, target, symbols != null ? symbols.toString ()
            : TransitionDialog.EPSILON );

    GraphConstants.setLineEnd ( newEdge.getAttributes (),
        GraphConstants.ARROW_CLASSIC );
    GraphConstants.setEndFill ( newEdge.getAttributes (), true );

    GraphConstants.setLineColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemTransition ().getColor () );
    GraphConstants.setLabelColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemSymbol ().getColor () );

    graph.getGraphLayoutCache ().insertEdge ( newEdge, source.getChildAt ( 0 ),
        target.getChildAt ( 0 ) );
    target.addTransition ( newEdge );
    source.addTransition ( newEdge );

    this.transitionViewList.add ( newEdge );
    this.tableModel.addTransition ( pTransition );
  }

  /**
   * 
   * Get the {@link Machine} of this model
   *
   * @return the {@link Machine}
   */
  public Machine getMachine ()
  {
    return this.machine;
  }

  /**
   * 
   * Getter for the {@link MachineTableModel}
   *
   * @return the {@link MachineTableModel} of this model
   */
  public MachineTableModel getTableModel ()
  {
    return this.tableModel;
  }

}
