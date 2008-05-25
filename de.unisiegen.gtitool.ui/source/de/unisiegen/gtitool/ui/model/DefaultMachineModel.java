package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.util.JGraphpadParallelSplineRouter;

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
import de.unisiegen.gtitool.core.machines.listener.MachineChangedListener;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.EdgeRenderer;
import de.unisiegen.gtitool.ui.jgraph.GPCellViewFactory;
import de.unisiegen.gtitool.ui.jgraph.StateSetView;
import de.unisiegen.gtitool.ui.jgraph.StateView;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.MultiItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;
import de.unisiegen.gtitool.ui.redoundo.StateAddedItem;
import de.unisiegen.gtitool.ui.redoundo.StateMovedItem;
import de.unisiegen.gtitool.ui.redoundo.StatePositionChangedListener;
import de.unisiegen.gtitool.ui.redoundo.StateRemovedItem;
import de.unisiegen.gtitool.ui.redoundo.TransitionAddedItem;
import de.unisiegen.gtitool.ui.redoundo.TransitionChangedItem;
import de.unisiegen.gtitool.ui.redoundo.TransitionRemovedItem;


/**
 * The Model for the {@link Machine}s
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultMachineModel implements DefaultModel, Storable,
    Modifyable
{

  /**
   * Signals the machine type.
   */
  public enum MachineType
  {
    /**
     * The machine type is DFA
     */
    DFA,

    /**
     * The machine type is ENFA
     */
    ENFA,

    /**
     * The machine type is NFA
     */
    NFA,

    /**
     * The machine type is PDA
     */
    PDA;
  }


  /**
   * The {@link Machine} version.
   */
  private static final int MACHINE_VERSION = 513;


  /**
   * The {@link DefaultGraphModel} for this model.
   */
  private DefaultGraphModel graphModel;


  /**
   * The {@link JGraph} containing the diagramm
   */
  private JGraph jGraph;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link Machine}
   */
  private Machine machine;


  /**
   * The {@link MachineChangedListener}.
   */
  private MachineChangedListener machineChangedListener;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The {@link MultiItem}.
   */
  private MultiItem multiItem;


  /**
   * The {@link RedoUndoHandler}
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * The {@link StatePositionChangedListener}.
   */
  private StatePositionChangedListener statePositionChangedListener;


  /**
   * A list of all {@link DefaultStateView}s
   */
  private ArrayList < DefaultStateView > stateViewList = new ArrayList < DefaultStateView > ();


  /**
   * The {@link PDATableModel}.
   */
  private PDATableModel tableModel;


  /**
   * A list of all {@link DefaultTransitionView}s
   */
  private ArrayList < DefaultTransitionView > transitionViewList = new ArrayList < DefaultTransitionView > ();


  /**
   * Flag that indicates if the {@link StateSetView} should be used.
   */
  private boolean useStateSetView = false;


  /**
   * Allocates a new {@link DefaultMachineModel}.
   * 
   * @param element The {@link Element}.
   * @param overwrittenMachineType The overwritten machine type which is used
   *          instead of the loaded machine type if it is not null.
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
  public DefaultMachineModel ( Element element, String overwrittenMachineType )
      throws StoreException, StateException, SymbolException,
      AlphabetException, TransitionException,
      TransitionSymbolOnlyOneTimeException
  {
    this.tableModel = new PDATableModel ();

    // Check if the element is correct
    if ( !element.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element " + Messages.QUOTE //$NON-NLS-1$
          + element.getName () + Messages.QUOTE + " is not a machine model" ); //$NON-NLS-1$
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
        if ( overwrittenMachineType == null )
        {
          machineType = attribute.getValue ();
        }
        else
        {
          machineType = overwrittenMachineType;
        }
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
    initializeStatePositionChangedListener ();
    initializeGraph ();
    initializeMachineChangedListener ();

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
          {
            state = new DefaultState ( childElement );
            state.setAlphabet ( alphabet );
            state.setPushDownAlphabet ( pushDownAlphabet );
          }
        }
        createStateView ( x + 35, y + 35, state, false );
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
        transition.setAlphabet ( alphabet );
        transition.setPushDownAlphabet ( pushDownAlphabet );

        DefaultStateView source = getStateById ( transition.getStateBeginId () );
        DefaultStateView target = getStateById ( transition.getStateEndId () );

        createTransitionView ( transition, source, target, false, false, true );
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
    this.tableModel = new PDATableModel ();
    initializeModifyStatusChangedListener ();
    initializeStatePositionChangedListener ();
    initializeGraph ();
    initializeMachineChangedListener ();

    // Reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
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
   * @param createUndoStep Flag signals if an undo step should be created.
   */
  @SuppressWarnings ( "unchecked" )
  public final DefaultStateView createStateView ( double x, double y,
      State state, boolean createUndoStep )
  {
    this.machine.addState ( state );
    DefaultStateView stateView = new DefaultStateView ( this.graphModel, state );

    String viewClass;
    if ( this.useStateSetView )
    {
      viewClass = StateSetView.class.getName ();

      // check position of the new state
      double xPosition = x < ( StateSetView.WIDTH / 2 ) ? ( StateSetView.WIDTH / 2 )
          : x;
      double yPostition = y < ( StateSetView.HEIGHT / 2 ) ? ( StateSetView.HEIGHT / 2 )
          : y;

      // Set bounds
      GraphConstants.setBounds ( stateView.getAttributes (),
          new Rectangle2D.Double ( xPosition - ( StateSetView.WIDTH / 2 ),
              yPostition - ( StateSetView.HEIGHT / 2 ), StateSetView.WIDTH,
              StateSetView.HEIGHT ) );
    }
    else
    {
      viewClass = StateView.class.getName ();

      // check position of the new state
      double xPosition = x < ( StateView.WIDTH / 2 ) ? ( StateView.WIDTH / 2 )
          : x;
      double yPostition = y < ( StateView.HEIGHT / 2 ) ? ( StateView.HEIGHT / 2 )
          : y;

      // Set bounds
      GraphConstants.setBounds ( stateView.getAttributes (),
          new Rectangle2D.Double ( xPosition - ( StateView.WIDTH / 2 ),
              yPostition - ( StateView.HEIGHT / 2 ), StateView.WIDTH,
              StateView.HEIGHT ) );
    }

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( stateView.getAttributes (), viewClass );

    // Opaque
    GraphConstants.setOpaque ( stateView.getAttributes (), true );

    // Gradient
    GraphConstants.setGradientColor ( stateView.getAttributes (), Color.WHITE );

    // Set black border
    GraphConstants.setBorderColor ( stateView.getAttributes (), Color.BLACK );

    // Set the line width
    GraphConstants.setLineWidth ( stateView.getAttributes (), 1 );

    // Add a floating port
    stateView.addPort ();

    this.jGraph.getGraphLayoutCache ().insert ( stateView );
    this.stateViewList.add ( stateView );

    // Reset modify
    stateView.resetModify ();

    stateView
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    stateView
        .addStatePositionChangedListener ( this.statePositionChangedListener );

    if ( ( this.redoUndoHandler != null ) && createUndoStep )
    {
      RedoUndoItem item = new StateAddedItem ( this, stateView, null );
      this.redoUndoHandler.addItem ( item );
    }

    return stateView;
  }


  /**
   * Create a new {@link Transition}.
   * 
   * @param transition The {@link Transition}.
   * @param source The source of the new {@link Transition}.
   * @param target The target of the new {@link Transition}.
   * @param targetCreated Flag signals if target {@link DefaultStateView} is
   *          created via the new {@link DefaultTransitionView}.
   * @param createUndoStep Flag signals if an undo step should be created.
   * @param addToMachine Flag which signals if the {@link Transition} should be
   *          added to the {@link Machine}. The default value should be true,
   *          only if the {@link Machine} has added the {@link Transition}
   *          before, it should be false.
   * @return The new created {@link DefaultTransitionView}.
   */
  public final DefaultTransitionView createTransitionView (
      Transition transition, DefaultStateView source, DefaultStateView target,
      boolean targetCreated, boolean createUndoStep, boolean addToMachine )
  {
    if ( addToMachine )
    {
      this.machine.addTransition ( transition );
      this.tableModel.addRow ( transition );
    }
    DefaultTransitionView transitionView = new DefaultTransitionView (
        transition, source, target );

    GraphConstants.setLineEnd ( transitionView.getAttributes (),
        GraphConstants.ARROW_CLASSIC );
    GraphConstants.setEndFill ( transitionView.getAttributes (), true );

    // Set the parallel routing
    JGraphpadParallelSplineRouter.getSharedInstance ().setEdgeSeparation ( 25 );
    GraphConstants.setRouting ( transitionView.getAttributes (),
        JGraphpadParallelSplineRouter.getSharedInstance () );

    this.jGraph.getGraphLayoutCache ().insertEdge ( transitionView,
        source.getChildAt ( 0 ), target.getChildAt ( 0 ) );

    this.transitionViewList.add ( transitionView );

    if ( ( this.redoUndoHandler != null ) && createUndoStep )
    {
      RedoUndoItem item = new TransitionAddedItem ( this, transitionView,
          targetCreated ? target : null );
      this.redoUndoHandler.addItem ( item );
    }

    return transitionView;
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
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
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
      {
        return view;
      }
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
      {
        return view;
      }
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
   * Returns the tableModel.
   * 
   * @return The tableModel.
   * @see #tableModel
   */
  public PDATableModel getTableModel ()
  {
    return this.tableModel;
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
      {
        return view;
      }
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
    this.graphModel = new DefaultGraphModel ();

    this.jGraph = new JGraph ( this.graphModel );
    this.jGraph.setDoubleBuffered ( false );
    this.jGraph.getGraphLayoutCache ().setFactory ( new GPCellViewFactory () );
    this.jGraph.setInvokesStopCellEditing ( true );
    this.jGraph.setJumpToDefaultPort ( true );
    this.jGraph.setSizeable ( false );
    this.jGraph.setConnectable ( false );
    this.jGraph.setDisconnectable ( false );
    this.jGraph.setEdgeLabelsMovable ( false );
    this.jGraph.setEditable ( false );
    this.jGraph.setHandleSize ( 0 );
    this.jGraph.setXorEnabled ( false );

    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChanged ()
          {
            DefaultMachineModel.this.jGraph.repaint ();
          }
        } );

    double zoomFactor = ( ( double ) PreferenceManager.getInstance ()
        .getZoomFactorItem ().getFactor () ) / 100;

    // Set the zoom factor of this graph
    this.jGraph.setScale ( this.jGraph.getScale () * zoomFactor );

    EdgeView.renderer = new EdgeRenderer ();
    EdgeView.renderer.setForeground ( Color.MAGENTA );
  }


  /**
   * Initialize the {@link MachineChangedListener}.
   */
  private final void initializeMachineChangedListener ()
  {
    this.machineChangedListener = new MachineChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void startEditing ()
      {
        DefaultMachineModel.this.multiItem = new MultiItem ();
      }


      @SuppressWarnings ( "synthetic-access" )
      public void stopEditing ()
      {
        if ( DefaultMachineModel.this.redoUndoHandler != null )
        {
          DefaultMachineModel.this.redoUndoHandler
              .addItem ( DefaultMachineModel.this.multiItem );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      public void symbolAdded ( Transition transition,
          ArrayList < Symbol > addedSymbols )
      {
        TreeSet < Symbol > oldSymbols = new TreeSet < Symbol > ();
        oldSymbols.addAll ( transition.getSymbol () );
        oldSymbols.removeAll ( addedSymbols );
        TransitionChangedItem item = new TransitionChangedItem ( transition,
            transition.getPushDownWordRead (), transition
                .getPushDownWordWrite (), oldSymbols );
        DefaultMachineModel.this.multiItem.addItem ( item );
      }


      @SuppressWarnings ( "synthetic-access" )
      public void symbolRemoved ( Transition transition,
          ArrayList < Symbol > removedSymbols )
      {
        TreeSet < Symbol > oldSymbols = new TreeSet < Symbol > ();
        oldSymbols.addAll ( transition.getSymbol () );
        oldSymbols.addAll ( removedSymbols );
        TransitionChangedItem item = new TransitionChangedItem ( transition,
            transition.getPushDownWordRead (), transition
                .getPushDownWordWrite (), oldSymbols );
        DefaultMachineModel.this.multiItem.addItem ( item );
      }


      @SuppressWarnings ( "synthetic-access" )
      public void transitionAdded ( Transition newTransition )
      {
        createTransitionView ( newTransition,
            getStateViewForState ( newTransition.getStateBegin () ),
            getStateViewForState ( newTransition.getStateEnd () ), false,
            false, false );
        TransitionAddedItem item = new TransitionAddedItem (
            DefaultMachineModel.this,
            getTransitionViewForTransition ( newTransition ), null );
        DefaultMachineModel.this.multiItem.addItem ( item );
      }


      @SuppressWarnings ( "synthetic-access" )
      public void transitionRemoved ( Transition transition )
      {
        DefaultTransitionView transitionView = getTransitionViewForTransition ( transition );
        removeTransition ( transitionView, false );
        RedoUndoItem item = new TransitionRemovedItem (
            DefaultMachineModel.this, transitionView );
        DefaultMachineModel.this.multiItem.addItem ( item );
      }
    };
    this.machine.addMachineChangedListener ( this.machineChangedListener );
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
   * Initialize the {@link StatePositionChangedListener}.
   */
  private final void initializeStatePositionChangedListener ()
  {
    this.statePositionChangedListener = new StatePositionChangedListener ()
    {

      /**
       * {@inheritDoc}
       * 
       * @see StatePositionChangedListener#statePositionChanged(DefaultStateView,
       *      double, double, double, double)
       */
      @SuppressWarnings ( "synthetic-access" )
      public void statePositionChanged ( DefaultStateView stateView,
          double oldX, double oldY, double newX, double newY )
      {
        if ( ( DefaultMachineModel.this.redoUndoHandler != null )
            && ( ( oldX != newX ) || ( oldY != newY ) ) )
        {
          RedoUndoItem item = new StateMovedItem ( DefaultMachineModel.this,
              stateView, oldX, oldY, newX, newY );
          DefaultMachineModel.this.redoUndoHandler.addItem ( item );
        }
      }

    };
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
   * Returns the useStateSetView.
   * 
   * @return The useStateSetView.
   * @see #useStateSetView
   */
  public final boolean isUseStateSetView ()
  {
    return this.useStateSetView;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Removes a {@link DefaultStateView}.
   * 
   * @param stateView The {@link DefaultStateView} that should be removed.
   * @param createUndoStep Flag signals if an undo step should be created.
   */
  public final void removeState ( DefaultStateView stateView,
      boolean createUndoStep )
  {
    ArrayList < DefaultTransitionView > removeList = new ArrayList < DefaultTransitionView > ();
    for ( DefaultTransitionView current : this.transitionViewList )
    {
      if ( ( current.getTransition ().getStateBegin ().equals ( stateView
          .getState () ) )
          || ( current.getTransition ().getStateEnd ().equals ( stateView
              .getState () ) ) )
      {
        removeList.add ( current );
      }
    }

    for ( DefaultTransitionView current : removeList )
    {
      removeTransition ( current, false );
    }

    this.graphModel.remove ( new Object []
    { stateView } );
    this.machine.removeState ( stateView.getState () );
    this.stateViewList.remove ( stateView );
    stateView
        .removeModifyStatusChangedListener ( this.modifyStatusChangedListener );

    if ( ( this.redoUndoHandler != null ) && createUndoStep )
    {
      RedoUndoItem item = new StateRemovedItem ( this, stateView, removeList );
      this.redoUndoHandler.addItem ( item );
    }
  }


  /**
   * Removes a {@link DefaultTransitionView}.
   * 
   * @param transitionView The {@link DefaultTransitionView} that should be
   *          removed.
   * @param createUndoStep Flag signals if an undo step should be created
   */
  public final void removeTransition ( DefaultTransitionView transitionView,
      boolean createUndoStep )
  {
    this.graphModel.remove ( new Object []
    { transitionView } );
    this.machine.removeTransition ( transitionView.getTransition () );
    this.transitionViewList.remove ( transitionView );

    if ( ( this.redoUndoHandler != null ) && createUndoStep )
    {
      RedoUndoItem item = new TransitionRemovedItem ( this, transitionView );
      this.redoUndoHandler.addItem ( item );
    }
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


  /**
   * Set the {@link PDATableModel}.
   * 
   * @param tableModel The new {@link PDATableModel}.
   */
  public void setPdaTableModel ( PDATableModel tableModel )
  {
    this.tableModel = tableModel;
  }


  /**
   * Set a new {@link RedoUndoHandler}
   * 
   * @param redoUndoHandler the new {@link RedoUndoHandler}
   */
  public final void setRedoUndoHandler ( RedoUndoHandler redoUndoHandler )
  {
    this.redoUndoHandler = redoUndoHandler;
  }


  /**
   * Sets the useStateSetView.
   * 
   * @param useStateSetView The useStateSetView to set.
   * @see #useStateSetView
   */
  public final void setUseStateSetView ( boolean useStateSetView )
  {
    this.useStateSetView = useStateSetView;
  }
}
