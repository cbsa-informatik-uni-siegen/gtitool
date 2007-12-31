package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The Model for the {@link Machine}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class DefaultMachineModel implements Storable
{

  /** The {@link Machine} */
  private Machine machine;


  /** The {@link JGraph} containing the diagramm */
  private JGraph jGraph;


  /** The {@link DefaultGraphModel} for this graph */
  private DefaultGraphModel graphModel;


  /** A list of all <code>DefaultStateView</code>s */
  ArrayList < DefaultStateView > stateViewList = new ArrayList < DefaultStateView > ();


  /** A list of all <code>DefaultTransitionView</code>s */
  ArrayList < DefaultTransitionView > transitionViewList = new ArrayList < DefaultTransitionView > ();


  /** The {@link MachineTableModel} */
  private MachineTableModel tableModel;


  /**
   * Allocate a new {@link DefaultMachineModel}
   * 
   * @param pMachine The {@link Machine}
   */
  public DefaultMachineModel ( Machine pMachine )
  {
    this.machine = pMachine;
    this.tableModel = new MachineTableModel ( this.machine.getAlphabet () );
    initializeGraph ();
  }


  /**
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pElement The {@link Element}.
   * @throws StoreException
   * @throws AlphabetException
   * @throws SymbolException
   * @throws StateException
   * @throws TransitionSymbolOnlyOneTimeException
   * @throws TransitionException
   */
  public DefaultMachineModel ( Element pElement ) throws StoreException,
      StateException, SymbolException, AlphabetException, TransitionException,
      TransitionSymbolOnlyOneTimeException
  {
    boolean machineTypeMissing = true;
    String machineType = ""; //$NON-NLS-1$

    // Check if the element is correct
    if ( !pElement.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a machine model" ); //$NON-NLS-1$
    }

    // Attribute
    for ( Attribute attribute : pElement.getAttribute () )
    {
      if ( attribute.getName ().equals ( "MachineType" ) ) //$NON-NLS-1$
      {
        machineTypeMissing = false;
        machineType = attribute.getValue ();
      }
    }

    if ( machineTypeMissing )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }
    if ( pElement.getAttribute ().size () > 1 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    Alphabet alphabet = null;
    boolean foundAlphabet = false;
    for ( Element current : pElement.getElement () )
    {
      if ( current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        alphabet = new Alphabet ( current );
        foundAlphabet = true;
      }
      else if ( ( !current.getName ().equals ( "StateView" ) ) //$NON-NLS-1$
          && ( !current.getName ().equals ( "TransitionView" ) ) ) //$NON-NLS-1$
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }
    if ( !foundAlphabet )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingElement" ) ); //$NON-NLS-1$
    }
    // initialize this models elements
    this.machine = AbstractMachine.createMachine ( machineType, alphabet );
    this.tableModel = new MachineTableModel ( this.machine.getAlphabet () );
    initializeGraph ();

    // Load the states
    for ( Element current : pElement.getElement () )
    {
      if ( current.getName ().equals ( "StateView" ) ) //$NON-NLS-1$
      {
        double x = 0;
        double y = 0;
        State state = null;
        boolean xValueLoaded = false;
        boolean yValueLoaded = false;
        for ( Attribute attribute : current.getAttribute () )
        {
          if ( attribute.getName ().equals ( "x" ) ) { //$NON-NLS-1$
            x = attribute.getValueDouble ();
            xValueLoaded = true;
          }
          if ( attribute.getName ().equals ( "y" ) ) { //$NON-NLS-1$
            y = attribute.getValueDouble ();
            yValueLoaded = true;
          }
        }
        if ( ! ( xValueLoaded && yValueLoaded ) )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
        }
        for ( Element element : current.getElement () )
        {
          if ( element.getName ().equals ( "State" ) ) //$NON-NLS-1$
            state = new State ( element );
        }
        createStateView ( x + 35, y + 35, state );
      }
      else if ( ( !current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
          && ( !current.getName ().equals ( "TransitionView" ) ) ) //$NON-NLS-1$
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    // Load the transitions
    for ( Element current : pElement.getElement () )
    {
      if ( current.getName ().equals ( "TransitionView" ) ) //$NON-NLS-1$
      {
        Transition transition = new Transition ( current.getElement ( 0 ) );
        DefaultStateView source = getStateById ( transition.getStateBeginId () );
        DefaultStateView target = getStateById ( transition.getStateEndId () );

        createTransitionView ( transition, source, target );
      }
      else if ( ( !current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
          && ( !current.getName ().equals ( "StateView" ) ) ) //$NON-NLS-1$
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }
  }


  /**
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
   * Get the {@link DefaultStateView} for an id
   * 
   * @param id the id
   * @return The {@link DefaultStateView}
   */
  public DefaultStateView getStateById ( int id )
  {
    for ( DefaultStateView view : this.stateViewList )
    {
      if ( view.getState ().getId () == id )
        return view;
    }
    return null;
  }


  /**
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
    this.machine.addState ( pState );
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$
    DefaultStateView stateView = new DefaultStateView ( pState, pState
        .getName () );

    // check position of the new state
    double xPosition = x < 35 ? 35 : x;
    double yPostition = y < 35 ? 35 : y;

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( stateView.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( stateView.getAttributes (),
        new Rectangle2D.Double ( xPosition - 35, yPostition - 35, 70, 70 ) );

    // Set fill color
    if ( pState.isStartState () )
      GraphConstants.setGradientColor ( stateView.getAttributes (),
          PreferenceManager.getInstance ().getColorItemStartState ()
              .getColor () );
    else
      GraphConstants.setGradientColor ( stateView.getAttributes (),
          PreferenceManager.getInstance ().getColorItemState ().getColor () );
    GraphConstants.setOpaque ( stateView.getAttributes (), true );

    // Set black border
    GraphConstants.setBorderColor ( stateView.getAttributes (), Color.black );

    // Set the line width
    GraphConstants.setLineWidth ( stateView.getAttributes (), 1 );

    // Add a Floating Port
    stateView.addPort ();

    this.jGraph.getGraphLayoutCache ().insert ( stateView );
    this.stateViewList.add ( stateView );
    this.tableModel.addState ( pState );

    return stateView;
  }


  /**
   * Create a new Transition
   * 
   * @param pTransition The {@link Transition}
   * @param source The source of the new Transition
   * @param target The target of the new Transition
   */
  public void createTransitionView ( Transition pTransition,
      DefaultStateView source, DefaultStateView target )
  {
    this.machine.addTransition ( pTransition );
    DefaultTransitionView newEdge = new DefaultTransitionView ( pTransition,
        source, target, pTransition.toString () );

    GraphConstants.setLineEnd ( newEdge.getAttributes (),
        GraphConstants.ARROW_CLASSIC );
    GraphConstants.setEndFill ( newEdge.getAttributes (), true );

    GraphConstants.setLineColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemTransition ().getColor () );
    GraphConstants.setLabelColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemSymbol ().getColor () );

    this.jGraph.getGraphLayoutCache ().insertEdge ( newEdge,
        source.getChildAt ( 0 ), target.getChildAt ( 0 ) );
    target.addTransition ( newEdge );
    source.addTransition ( newEdge );

    this.transitionViewList.add ( newEdge );
    this.tableModel.addTransition ( pTransition );

  }


  /**
   * Get the {@link Machine} of this model
   * 
   * @return the {@link Machine}
   */
  public Machine getMachine ()
  {
    return this.machine;
  }


  /**
   * Getter for the {@link MachineTableModel}
   * 
   * @return the {@link MachineTableModel} of this model
   */
  public MachineTableModel getTableModel ()
  {
    return this.tableModel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public Element getElement ()
  {
    Element newElement = new Element ( "MachineModel" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "MachineType", this.machine //$NON-NLS-1$
        .getMachineType () ) );
    newElement.addElement ( this.machine.getAlphabet ().getElement () );
    for ( DefaultStateView stateView : this.stateViewList )
    {
      newElement.addElement ( stateView.getElement () );
    }
    for ( DefaultTransitionView transitionView : this.transitionViewList )
    {
      newElement.addElement ( transitionView.getElement () );
    }
    return newElement;
  }


  /**
   * Initialize this JGraph
   */
  private void initializeGraph ()
  {
    // Construct Model and Graph
    this.graphModel = new DefaultGraphModel ();

    this.jGraph = new JGraph ( this.graphModel );

    this.jGraph.getGraphLayoutCache ().setFactory ( new GPCellViewFactory () );

    // Control-drag should clone selection
    this.jGraph.setCloneable ( true );

    // Enable edit without final RETURN keystroke
    this.jGraph.setInvokesStopCellEditing ( true );

    // When over a cell, jump to its default port (we only have one, anyway)
    this.jGraph.setJumpToDefaultPort ( true );

    // Set states to not sizeable
    this.jGraph.setSizeable ( false );

    // Set states to not connectable and disconnectable
    // So Transitions are not moveable
    this.jGraph.setConnectable ( false );
    this.jGraph.setDisconnectable ( false );

    // Set the labels of the Transitions to not moveable
    this.jGraph.setEdgeLabelsMovable ( false );

    // Set states to not editable
    this.jGraph.setEditable ( false );

    double zoomFactor = ( ( double ) PreferenceManager.getInstance ()
        .getZoomFactorItem ().getFactor () ) / 100;

    // Set the zoom factor of this graph
    this.jGraph.setScale ( this.jGraph.getScale () * zoomFactor );

    EdgeView.renderer.setForeground ( Color.magenta );
  }


  /**
   * Getter for the {@link DefaultGraphModel}
   * 
   * @return the {@link DefaultGraphModel}
   */
  public DefaultGraphModel getGraphModel ()
  {
    return this.graphModel;
  }


  /**
   * Getter for the {@link JGraph}
   * 
   * @return the {@link JGraph}
   */
  public JGraph getJGraph ()
  {
    return this.jGraph;
  }


  /**
   * Remove a state
   * 
   * @param state that should be removed
   */
  public void removeState ( DefaultStateView state )
  {
    this.graphModel.remove ( state.getRemoveObjects () );
    this.machine.removeState ( state.getState () );
    this.tableModel.removeState ( state.getState () );

  }


  /**
   * Remove a transition
   * 
   * @param transition that should be removed
   */
  public void removeTransition ( DefaultTransitionView transition )
  {
    this.graphModel.remove ( new Object []
    { transition } );
    this.machine.removeTransition ( transition.getTransition () );
    this.tableModel.removeTransition ( transition.getTransition () );

  }


  /**
   * Update data for transition
   * 
   * @param oldTransition the old transition object
   * @param transition the new transition object
   */
  public void transitionChanged ( Transition oldTransition,
      Transition transition )
  {
    this.tableModel.removeTransition ( oldTransition );
    this.tableModel.addTransition ( transition );

  }

}
