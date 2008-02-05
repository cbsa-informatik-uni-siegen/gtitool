package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
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
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultMachineModel implements Storable, Modifyable
{

  /**
   * The {@link Machine} version.
   */
  private static final int MACHINE_VERSION = 513;


  /**
   * The {@link Machine}
   */
  private Machine machine;


  /**
   * The {@link JGraph} containing the diagramm
   */
  private JGraph jGraph;


  /**
   * The {@link DefaultGraphModel} for this model.
   */
  private DefaultGraphModel graphModel;


  /**
   * A list of all {@link DefaultStateView}s
   */
  private ArrayList < DefaultStateView > stateViewList = new ArrayList < DefaultStateView > ();


  /**
   * A list of all {@link DefaultTransitionView}s
   */
  private ArrayList < DefaultTransitionView > transitionViewList = new ArrayList < DefaultTransitionView > ();


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * Allocates a new {@link DefaultMachineModel}.
   * 
   * @param element The {@link Element}.
   * @throws StoreException If the {@link Element} can not be parsed.
   * @throws AlphabetException If something with the {@link DefaultAlphabet} is
   *           not correct.
   * @throws SymbolException If something with the {@link Symbol} is not
   *           correct.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link DefaultTransition} is not correct.
   * @throws TransitionException If something with the {@link DefaultTransition}
   *           is not correct.
   */
  public DefaultMachineModel ( Element element ) throws StoreException,
      StateException, SymbolException, AlphabetException, TransitionException,
      TransitionSymbolOnlyOneTimeException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + element.getName () //$NON-NLS-1$
          + "\" is not a machine model" ); //$NON-NLS-1$
    }

    // Attribute
    boolean foundMachineType = false;
    boolean foundMachineVersion = false;
    boolean foundUsePushDownAlphabet = false;
    String machineType = null;
    boolean usePushDownAlphabet = true;
    for ( Attribute attribute : element.getAttribute () )
    {
      if ( attribute.getName ().equals ( "machineType" ) ) //$NON-NLS-1$
      {
        foundMachineType = true;
        machineType = attribute.getValue ();
      }
      else if ( attribute.getName ().equals ( "machineVersion" ) ) //$NON-NLS-1$
      {
        foundMachineVersion = true;
        if ( MACHINE_VERSION != attribute.getValueInt () )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.IncompatibleVersion" ) ); //$NON-NLS-1$
        }
      }
      else if ( attribute.getName ().equals ( "usePushDownAlphabet" ) ) //$NON-NLS-1$
      {
        foundUsePushDownAlphabet = true;
        usePushDownAlphabet = attribute.getValueBoolean ();
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
      }
    }

    if ( ( !foundMachineType ) || ( !foundMachineVersion )
        || ( !foundUsePushDownAlphabet ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    Alphabet alphabet = null;
    Alphabet pushDownAlphabet = null;
    boolean foundAlphabet = false;
    boolean foundPushDownAlphabet = false;
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        alphabet = new DefaultAlphabet ( current );
        foundAlphabet = true;
      }
      else if ( current.getName ().equals ( "PushDownAlphabet" ) ) //$NON-NLS-1$
      {
        current.setName ( "Alphabet" ); //$NON-NLS-1$
        pushDownAlphabet = new DefaultAlphabet ( current );
        foundPushDownAlphabet = true;
      }
      else if ( ( !current.getName ().equals ( "StateView" ) ) //$NON-NLS-1$
          && ( !current.getName ().equals ( "TransitionView" ) ) ) //$NON-NLS-1$
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }
    if ( ( !foundAlphabet ) || ( !foundPushDownAlphabet ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingElement" ) ); //$NON-NLS-1$
    }
    // initialize this model elements
    this.machine = AbstractMachine.createMachine ( machineType, alphabet,
        pushDownAlphabet, usePushDownAlphabet );
    initializeModifyStatusChangedListener ();
    initializeGraph ();

    // Load the states
    for ( Element current : element.getElement () )
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
        for ( Element childElement : current.getElement () )
        {
          if ( childElement.getName ().equals ( "State" ) ) //$NON-NLS-1$
            state = new DefaultState ( childElement );
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
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "TransitionView" ) ) //$NON-NLS-1$
      {
        Transition transition = new DefaultTransition ( current.getElement ( 0 ) );
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

    // Reset modify
    resetModify ();
  }


  /**
   * Allocate a new {@link DefaultMachineModel}.
   * 
   * @param machine The {@link Machine}.
   */
  public DefaultMachineModel ( Machine machine )
  {
    this.machine = machine;
    initializeModifyStatusChangedListener ();
    initializeGraph ();

    // Reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Create a new State view
   * 
   * @param x The x position of the new state view.
   * @param y The y position of the new state view.
   * @param state The state represented via this view.
   * @return The new created {@link DefaultStateView}.
   */
  @SuppressWarnings ( "unchecked" )
  public final DefaultStateView createStateView ( double x, double y,
      State state )
  {
    this.machine.addState ( state );
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$
    DefaultStateView stateView = new DefaultStateView ( this.graphModel, state );

    // check position of the new state
    double xPosition = x < 35 ? 35 : x;
    double yPostition = y < 35 ? 35 : y;

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( stateView.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( stateView.getAttributes (),
        new Rectangle2D.Double ( xPosition - 35, yPostition - 35, 70, 70 ) );

    // Set fill color
    if ( state.isStartState () )
    {
      GraphConstants.setBackground ( stateView.getAttributes (),
          PreferenceManager.getInstance ().getColorItemStateStart ()
              .getColor () );
      GraphConstants.setOpaque ( stateView.getAttributes (), true );
    }
    else
    {
      GraphConstants.setBackground ( stateView.getAttributes (),
          PreferenceManager.getInstance ().getColorItemStateBackground ()
              .getColor () );
      GraphConstants.setOpaque ( stateView.getAttributes (), true );
    }
    
    GraphConstants.setGradientColor ( stateView.getAttributes (),
        Color.white );

    // Set black border
    GraphConstants.setBorderColor ( stateView.getAttributes (), Color.black );

    // Set the line width
    GraphConstants.setLineWidth ( stateView.getAttributes (), 1 );

    // Add a Floating Port
    stateView.addPort ();

    this.jGraph.getGraphLayoutCache ().insert ( stateView );
    this.stateViewList.add ( stateView );

    // Reset modify
    stateView.resetModify ();

    stateView
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    return stateView;
  }


  /**
   * Create a new {@link Transition}.
   * 
   * @param transition The {@link Transition}.
   * @param source The source of the new {@link Transition}.
   * @param target The target of the new {@link Transition}.
   */
  public final void createTransitionView ( Transition transition,
      DefaultStateView source, DefaultStateView target )
  {
    this.machine.addTransition ( transition );
    DefaultTransitionView newEdge = new DefaultTransitionView ( transition,
        source, target );

    GraphConstants.setLineEnd ( newEdge.getAttributes (),
        GraphConstants.ARROW_CLASSIC );
    GraphConstants.setEndFill ( newEdge.getAttributes (), true );

    GraphConstants.setLineColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemTransition ().getColor () );
    GraphConstants.setLabelColor ( newEdge.getAttributes (), PreferenceManager
        .getInstance ().getColorItemTransition ().getColor () );

    this.jGraph.getGraphLayoutCache ().insertEdge ( newEdge,
        source.getChildAt ( 0 ), target.getChildAt ( 0 ) );

    this.transitionViewList.add ( newEdge );
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  private final void fireModifyStatusChanged ( boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( int n = 0 ; n < listeners.length ; ++n )
      {
        listeners [ n ].modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( int n = 0 ; n < listeners.length ; ++n )
      {
        listeners [ n ].modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "MachineModel" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "machineType", this.machine //$NON-NLS-1$
        .getMachineType () ) );
    newElement.addAttribute ( new Attribute ( "machineVersion", //$NON-NLS-1$
        MACHINE_VERSION ) );
    newElement.addAttribute ( new Attribute ( "usePushDownAlphabet", //$NON-NLS-1$
        this.machine.isUsePushDownAlphabet () ) );
    newElement.addElement ( this.machine.getAlphabet ().getElement () );
    Element newPushDownAlphabet = this.machine.getPushDownAlphabet ()
        .getElement ();
    newPushDownAlphabet.setName ( "PushDownAlphabet" ); //$NON-NLS-1$
    newElement.addElement ( newPushDownAlphabet );
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
   * Getter for the {@link DefaultGraphModel}
   * 
   * @return the {@link DefaultGraphModel}
   */
  public final DefaultGraphModel getGraphModel ()
  {
    return this.graphModel;
  }


  /**
   * Getter for the {@link JGraph}
   * 
   * @return the {@link JGraph}
   */
  public final JGraph getJGraph ()
  {
    return this.jGraph;
  }


  /**
   * Get the {@link Machine} of this model
   * 
   * @return the {@link Machine}
   */
  public final Machine getMachine ()
  {
    return this.machine;
  }


  /**
   * Get the {@link DefaultStateView} for an id
   * 
   * @param id the id
   * @return The {@link DefaultStateView}
   */
  public final DefaultStateView getStateById ( int id )
  {
    for ( DefaultStateView view : this.stateViewList )
    {
      if ( view.getState ().getId () == id )
        return view;
    }
    return null;
  }


  /**
   * Get the {@link DefaultStateView} for a {@link State}
   * 
   * @param state The {@link State}
   * @return The {@link DefaultStateView}
   */
  public final DefaultStateView getStateViewForState ( State state )
  {
    for ( DefaultStateView view : this.stateViewList )
    {
      if ( view.getState ().equals ( state ) )
        return view;
    }
    return null;
  }


  /**
   * Get a list with all {@link DefaultStateView}s of this Model
   * 
   * @return DefaultStateView list
   */
  public final ArrayList < DefaultStateView > getStateViewList ()
  {
    return this.stateViewList;
  }


  /**
   * Get the {@link DefaultTransitionView} for a {@link Transition}
   * 
   * @param transition The {@link Transition}
   * @return The {@link DefaultTransitionView}
   */
  public final DefaultTransitionView getTransitionViewForTransition (
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
   * Get a list with all {@link DefaultTransitionView}s of this Model
   * 
   * @return DefaultTransitionView list
   */
  public final ArrayList < DefaultTransitionView > getTransitionViewList ()
  {
    return this.transitionViewList;
  }


  /**
   * Initialize the {@link JGraph}.
   */
  private final void initializeGraph ()
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

    // The Handles of the transitions are not visible
    this.jGraph.setHandleSize ( 0 );

    this.jGraph.setHighlightColor ( PreferenceManager.getInstance ()
        .getColorItemTransitionSelected ().getColor () );
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedState ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            DefaultMachineModel.this.jGraph.repaint ();
          }


          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedStateSelected ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            DefaultMachineModel.this.jGraph.repaint ();
          }


          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedTransitionSelected ( Color newColor )
          {
            DefaultMachineModel.this.jGraph.setHighlightColor ( newColor );
            DefaultMachineModel.this.jGraph.repaint ();
          }
        } );

    double zoomFactor = ( ( double ) PreferenceManager.getInstance ()
        .getZoomFactorItem ().getFactor () ) / 100;

    // Set the zoom factor of this graph
    this.jGraph.setScale ( this.jGraph.getScale () * zoomFactor );

    EdgeView.renderer.setForeground ( Color.magenta );
  }


  /**
   * Initialize the {@link ModifyStatusChangedListener}.
   */
  private final void initializeModifyStatusChangedListener ()
  {
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };
    this.machine
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( this.machine.isModified () )
    {
      return true;
    }
    for ( DefaultStateView current : this.stateViewList )
    {
      if ( current.isModified () )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Removes a {@link DefaultStateView}.
   * 
   * @param stateView The {@link DefaultStateView} that should be removed.
   */
  public final void removeState ( DefaultStateView stateView )
  {
    ArrayList < DefaultTransitionView > removeList = new ArrayList < DefaultTransitionView > ();
    for ( DefaultTransitionView current : this.transitionViewList )
    {
      if ( current.getTransition ().getStateBegin ().equals (
          stateView.getState () ) )
      {
        this.graphModel.remove ( new Object []
        { current } );
        removeList.add ( current );
      }
      else if ( current.getTransition ().getStateEnd ().equals (
          stateView.getState () ) )
      {
        this.graphModel.remove ( new Object []
        { current } );
        removeList.add ( current );
      }
    }

    for ( DefaultTransitionView current : removeList )
    {
      this.transitionViewList.remove ( current );
    }

    this.graphModel.remove ( new Object []
    { stateView } );
    this.machine.removeState ( stateView.getState () );
    this.stateViewList.remove ( stateView );
    stateView
        .removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
  }


  /**
   * Removes a {@link DefaultTransitionView}.
   * 
   * @param transitionView The {@link DefaultTransitionView} that should be
   *          removed.
   */
  public final void removeTransition ( DefaultTransitionView transitionView )
  {
    this.graphModel.remove ( new Object []
    { transitionView } );
    this.machine.removeTransition ( transitionView.getTransition () );
    this.transitionViewList.remove ( transitionView );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    for ( DefaultStateView current : this.stateViewList )
    {
      current.resetModify ();
    }
    this.machine.resetModify ();
  }
}
