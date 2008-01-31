package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordNotAcceptedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storage;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.ConsoleTableModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;
import de.unisiegen.gtitool.ui.popup.DefaultPopupMenu;
import de.unisiegen.gtitool.ui.popup.StatePopupMenu;
import de.unisiegen.gtitool.ui.popup.TransitionPopupMenu;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * The Panel containing the diagramm and table representing a machine
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class MachinePanel implements EditorPanel
{

  /** Signals the active mouse adapter */
  public enum ACTIVE_MOUSE_ADAPTER
  {
    /**
     * Mouse is choosen.
     */
    MOUSE,

    /**
     * Add State is choosen.
     */
    ADD_STATE,

    /**
     * Add Start State is choosen.
     */
    ADD_START_STATE,

    /**
     * Add Final State is choosen.
     */
    ADD_FINAL_STATE,

    /**
     * Add Transition is choosen.
     */
    ADD_TRANSITION;
  }


  /**
   * Do next step in word enter mode after a delay.
   * 
   * @author Benjamin Mies
   */
  private final class AutoStepTimerTask extends TimerTask
  {

    /**
     * Make next step after a delay.
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        public void run ()
        {
          MachinePanel.this.handleWordNextStep ();
        }
      } );
    }
  }


  /**
   * Selects the item with the given index in the {@link JTable} or clears the
   * selection after a delay.
   * 
   * @author Christian Fehler
   */
  private final class HighlightTimerTask extends TimerTask
  {

    /**
     * The index.
     */
    protected int index;


    /**
     * The {@link JTable}.
     */
    protected JTable table;


    /**
     * Initilizes the {@link HighlightTimerTask}.
     * 
     * @param table The {@link JTable}.
     * @param index The index.
     */
    public HighlightTimerTask ( JTable table, int index )
    {
      this.index = index;
      this.table = table;
    }


    /**
     * Selects the item with the given index in the {@link JTable} or clears the
     * selection after a delay.
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      if ( this.index == -1 )
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          public void run ()
          {
            HighlightTimerTask.this.table.getSelectionModel ()
                .clearSelection ();
            HighlightTimerTask.this.table.repaint ();
          }
        } );
      }
      else
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          public void run ()
          {
            HighlightTimerTask.this.table.getSelectionModel ()
                .setSelectionInterval ( HighlightTimerTask.this.index,
                    HighlightTimerTask.this.index );
            HighlightTimerTask.this.table.repaint ();
          }
        } );
      }
    }
  }


  /** The actual active MouseAdapter */
  private static ACTIVE_MOUSE_ADAPTER activeMouseAdapter;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  //
  // Attributes
  //
  /** the parent window */
  private MainWindowForm parent;


  /** The {@link MachinesPanelForm} */
  private MachinesPanelForm gui;


  /** The {@link DefaultMachineModel} */
  private DefaultMachineModel model;


  /** The {@link Machine} */
  private Machine machine;


  /** The {@link JGraph} containing the diagramm */
  private JGraph graph;


  /** The {@link DefaultGraphModel} for this graph */
  private DefaultGraphModel graphModel;


  /** The {@link MouseAdapter} for the mouse icon in the toolbar */
  private MouseAdapter mouse;


  /** The {@link MouseAdapter} for the add State icon in the toolbar */
  private MouseAdapter addState;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  private MouseAdapter transition;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  private MouseMotionAdapter transitionMove;


  /** The {@link MouseAdapter} for the start icon in the toolbar */
  private MouseAdapter start;


  /** The {@link MouseAdapter} for the end icon in the toolbar */
  private MouseAdapter end;


  /** The source state for a new Transition */
  private DefaultStateView firstState;


  /** The tmp state for a new Transition */
  private DefaultGraphCell tmpState;


  /** The tmp transition */
  private DefaultEdge tmpTransition;


  /** Signals if drag in progress */
  private boolean dragged;


  /** The zoom factor for this graph */
  private double zoomFactor;


  /** The {@link ConsoleTableModel} for the warning table */
  private ConsoleTableModel warningTableModel;


  /** The {@link ConsoleTableModel} for the error table */
  private ConsoleTableModel errorTableModel;


  /** The actual highlighted error states */
  private ArrayList < DefaultStateView > oldErrorStates = new ArrayList < DefaultStateView > ();


  /** The actual highlighted error transitions */
  private ArrayList < DefaultTransitionView > oldErrorTransitions = new ArrayList < DefaultTransitionView > ();


  /** The {@link JPopupMenu} */
  private JPopupMenu popup;


  /**
   * The {@link Timer} of the console table.
   */
  private Timer consoleTimer = null;


  /**
   * The {@link Timer} of the machine table.
   */
  private Timer machineTimer = null;


  /**
   * The {@link Timer} of the auto step mode.
   */
  private Timer autoStepTimer = null;


  /**
   * The File for this MachinePanel
   */
  private File file;


  /**
   * Flag that indicates if the console divider location should be stored.
   */
  private boolean setDividerLocationConsole = true;


  /**
   * Flag that indicates if the table divider location should be stored.
   */
  private boolean setDividerLocationTable = true;


  /**
   * Flag signals if we are in the enter word mode
   */
  private boolean enterWordMode = false;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The name of this {@link MachinePanel}.
   */
  private String name = null;


  /**
   * Create a new Machine Panel Object
   * 
   * @param parent The parent frame.
   * @param model The {@link DefaultMachineModel} of this panel.
   * @param file The {@link File} of this {@link MachinePanel}.
   */
  public MachinePanel ( MainWindowForm parent, DefaultMachineModel model,
      File file )
  {
    this.parent = parent;
    this.model = model;
    this.file = file;
    this.gui = new MachinesPanelForm ();
    this.gui.setMachinePanel ( this );

    intitializeMouseAdapter ();

    /*
     * Divider Location
     */
    this.gui.jSplitPaneConsole.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationConsole () );
    setVisibleConsole ( this.parent.jCheckBoxMenuItemConsole.getState () );
    this.gui.jSplitPaneConsole.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            if ( MachinePanel.this.setDividerLocationConsole )
            {
              PreferenceManager.getInstance ().setDividerLocationConsole (
                  ( ( Integer ) event.getNewValue () ).intValue () );
            }
          }
        } );
    this.gui.jSplitPaneTable.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationTable () );
    setVisibleTable ( this.parent.jCheckBoxMenuItemTable.getState () );
    this.gui.jSplitPaneTable.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            if ( MachinePanel.this.setDividerLocationTable )
            {
              PreferenceManager.getInstance ().setDividerLocationTable (
                  ( ( Integer ) event.getNewValue () ).intValue () );
            }
          }
        } );

    initialize ();
    addListener ();
    addGraphListener ();

    // Reset modify
    resetModify ();
  }


  /**
   * Add all needed listener
   */
  private void addListener ()
  {
    this.gui.jGTITableMachine.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleMachineTableValueChanged ( event );
          }

        } );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedStartState ( Color )
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedStartState ( Color newColor )
          {
            for ( Object object : DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) )
            {
              try
              {
                DefaultStateView state = ( DefaultStateView ) object;
                if ( state.getState ().isStartState () )
                  GraphConstants.setGradientColor ( state.getAttributes (),
                      newColor );

              }
              catch ( ClassCastException e )
              {
                // Do nothing
              }
            }
            MachinePanel.this.graphModel.cellsChanged ( DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) );

          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedState ( Color )
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedState ( Color newColor )
          {
            for ( Object object : DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) )
            {
              try
              {
                DefaultStateView state = ( DefaultStateView ) object;
                if ( !state.getState ().isStartState () )
                  GraphConstants.setGradientColor ( state.getAttributes (),
                      newColor );

              }
              catch ( ClassCastException e )
              {
                // Do nothing
              }
            }
            MachinePanel.this.graphModel.cellsChanged ( DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) );

          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedSymbol ( Color )
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            for ( Object object : DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) )
            {
              try
              {
                DefaultTransitionView t = ( DefaultTransitionView ) object;
                GraphConstants.setLabelColor ( t.getAttributes (),
                    PreferenceManager.getInstance ().getColorItemSymbol ()
                        .getColor () );

              }
              catch ( ClassCastException e )
              {
                // Do nothing
              }
            }
            MachinePanel.this.graphModel.cellsChanged ( DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) );

          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedTransition ( Color )
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedTransition ( Color newColor )
          {
            for ( Object object : DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) )
            {
              try
              {
                DefaultTransitionView t = ( DefaultTransitionView ) object;
                GraphConstants.setLineColor ( t.getAttributes (), newColor );

              }
              catch ( ClassCastException e )
              {
                // Do nothing
              }
            }
            MachinePanel.this.graphModel.cellsChanged ( DefaultGraphModel
                .getAll ( MachinePanel.this.graphModel ) );

          }

        } );
  }


  /**
   * Initialize the machine panel
   */
  private void initialize ()
  {
    this.machine = this.model.getMachine ();
    this.graph = this.model.getJGraph ();
    this.graphModel = this.model.getGraphModel ();
    this.zoomFactor = ( ( double ) PreferenceManager.getInstance ()
        .getZoomFactorItem ().getFactor () ) / 100;

    if ( activeMouseAdapter == null )
    {
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.MOUSE;
    }
    switch ( activeMouseAdapter )
    {

      case MOUSE :
      {
        handleToolbarMouse ( true );
        break;
      }
      case ADD_STATE :
      {
        handleToolbarAddState ( true );
        break;
      }
      case ADD_START_STATE :
      {
        handleToolbarStart ( true );
        break;
      }
      case ADD_FINAL_STATE :
      {
        handleToolbarEnd ( true );
        break;
      }
      case ADD_TRANSITION :
      {
        handleToolbarTransition ( true );
        break;
      }

    }

    this.gui.diagrammContentPanel.setViewportView ( this.graph );

    this.errorTableModel = new ConsoleTableModel ();
    this.gui.jGTITableErrors.setModel ( this.errorTableModel );
    this.gui.jGTITableErrors.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableErrors.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableErrors
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableErrors.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }

        } );
    this.warningTableModel = new ConsoleTableModel ();
    this.gui.jGTITableWarnings.setModel ( this.warningTableModel );
    this.gui.jGTITableWarnings.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableWarnings.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableWarnings
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableWarnings.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }

        } );
    this.gui.jGTITableMachine.setModel ( this.machine );
    this.gui.jGTITableMachine.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableMachine
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );

    this.gui.wordPanel.setVisible ( false );
    this.gui.wordPanel.setAlphabet ( this.machine.getAlphabet () );
  }


  /**
   * Add all needed listener to the JGraph
   */
  private void addGraphListener ()
  {
    this.graph.addKeyListener ( new KeyListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void keyPressed ( KeyEvent event )
      {
        if ( event.getKeyCode () == 27 )
        {
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
          MachinePanel.this.dragged = false;
        }

      }


      public void keyReleased ( @SuppressWarnings ( "unused" )
      KeyEvent event )
      {
        // Nothing to do here
      }


      public void keyTyped ( @SuppressWarnings ( "unused" )
      KeyEvent event )
      {
        // Nothing to do here
      }

    } );

    // ModifyStatusChangedListener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };
    this.model
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
  }


  /**
   * Add a new Error
   * 
   * @param machineException The {@link MachineException} containing the data
   */
  public final void addError ( MachineException machineException )
  {
    this.errorTableModel.addRow ( machineException );
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
   * Add a new Warning
   * 
   * @param machineException The {@link MachineException} containing the data
   */
  public final void addWarning ( MachineException machineException )
  {
    this.warningTableModel.addRow ( machineException );
  }


  /**
   * Remove all highlighting
   */
  public final void clearHighlight ()
  {
    for ( DefaultTransitionView view : this.oldErrorTransitions )
    {
      GraphConstants.setLineColor ( view.getAttributes (), PreferenceManager
          .getInstance ().getColorItemTransition ().getColor () );
    }

    for ( DefaultStateView view : this.oldErrorStates )
    {
      if ( view.getState ().isStartState () )
        GraphConstants.setGradientColor ( view.getAttributes (),
            PreferenceManager.getInstance ().getColorItemStartState ()
                .getColor () );
      else
      {
        GraphConstants.setGradientColor ( view.getAttributes (),
            PreferenceManager.getInstance ().getColorItemState ().getColor () );
      }
    }
    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
  }


  /**
   * Clear all Error and Warning messages
   */
  public final void clearValidationMessages ()
  {
    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * Create a standard Popup Menu
   * 
   * @return the new created Popup Menu
   */
  private final DefaultPopupMenu createPopupMenu ()
  {
    int factor = ( new Double ( this.zoomFactor * 100 ) ).intValue ();
    return new DefaultPopupMenu ( this, this.machine, factor );
  }


  /**
   * Create a new Popup Menu for the given State
   * 
   * @param stateView the State for to create a popup menu
   * @return the new created Popup Menu
   */
  private final StatePopupMenu createStatePopupMenu ( DefaultStateView stateView )
  {
    return new StatePopupMenu ( this.parent, this.graph, this.model, stateView );
  }


  /**
   * Create a temporary Object to paint the Transiton on Mouse move
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @return {@link DefaultGraphCell} the new created tmp Object
   */
  @SuppressWarnings ( "unchecked" )
  private final DefaultGraphCell createTmpObject ( double x, double y )
  {
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$

    DefaultGraphCell cell = new DefaultGraphCell ();

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x, y, 1, 1 ) );

    GraphConstants.setBorder ( cell.getAttributes (), BorderFactory
        .createRaisedBevelBorder () );

    // Set the line width
    GraphConstants.setLineWidth ( cell.getAttributes (), 2 );

    // Add a Floating Port
    cell.addPort ();

    return cell;

  }


  /**
   * Create a new Popup Menu for the given Transition
   * 
   * @param transitionView the Transition for to create a popup menu
   * @return the new created Popup Menu
   */
  private final TransitionPopupMenu createTransitionPopupMenu (
      DefaultTransitionView transitionView )
  {
    return new TransitionPopupMenu ( this.graph, this.gui, this.model,
        transitionView, this.machine.getAlphabet (), this.machine
            .getPushDownAlphabet () );
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
   * @see EditorPanel#getFile()
   */
  public final File getFile ()
  {
    return this.file;
  }


  /**
   * Getter for the {@link Machine}
   * 
   * @return the {@link Machine} of this panel
   */
  public final Machine getMachine ()
  {
    return this.machine;
  }


  /**
   * Returns the {@link DefaultMachineModel}.
   * 
   * @return The {@link DefaultMachineModel}.
   * @see #model
   */
  public final DefaultMachineModel getModel ()
  {
    return this.model;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getName()
   */
  public final String getName ()
  {
    return this.file == null ? this.name : this.file.getName ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getPanel()
   */
  public final JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * Handles focus lost event on the console table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleConsoleTableFocusLost ( @SuppressWarnings ( "unused" )
  FocusEvent event )
  {
    if ( this.consoleTimer != null )
    {
      this.consoleTimer.cancel ();
    }
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    this.clearHighlight ();
  }


  /**
   * Handles the mouse exited event on the console table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleConsoleTableMouseExited (
      @SuppressWarnings ( "unused" )
      MouseEvent event )
  {
    if ( this.consoleTimer != null )
    {
      this.consoleTimer.cancel ();
    }
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    this.clearHighlight ();
  }


  /**
   * Handles the mouse moved event on the console table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleConsoleTableMouseMoved ( MouseEvent event )
  {
    JTable table;
    if ( event.getSource () == this.gui.jGTITableErrors )
    {
      table = this.gui.jGTITableErrors;
    }
    else if ( event.getSource () == this.gui.jGTITableWarnings )
    {
      table = this.gui.jGTITableWarnings;
    }
    else
    {
      throw new IllegalArgumentException ( "wrong event source" ); //$NON-NLS-1$
    }
    int index = table.rowAtPoint ( event.getPoint () );
    if ( this.consoleTimer != null )
    {
      this.consoleTimer.cancel ();
    }
    this.consoleTimer = new Timer ();
    if ( index == -1 )
    {
      table.setCursor ( new Cursor ( Cursor.DEFAULT_CURSOR ) );
      this.consoleTimer.schedule ( new HighlightTimerTask ( table, -1 ), 250 );
    }
    else
    {
      table.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
      this.consoleTimer
          .schedule ( new HighlightTimerTask ( table, index ), 250 );
    }
  }


  /**
   * Handles {@link ListSelectionEvent}s on the console table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleConsoleTableValueChanged (
      @SuppressWarnings ( "unused" )
      ListSelectionEvent event )
  {
    JTable table;
    if ( event.getSource () == this.gui.jGTITableErrors.getSelectionModel () )
    {
      table = this.gui.jGTITableErrors;
    }
    else if ( event.getSource () == this.gui.jGTITableWarnings
        .getSelectionModel () )
    {
      table = this.gui.jGTITableWarnings;
    }
    else
    {
      throw new IllegalArgumentException ( "wrong event source" ); //$NON-NLS-1$
    }
    int index = table.getSelectedRow ();
    if ( index == -1 )
    {
      clearHighlight ();
    }
    else
    {
      highlightStates ( ( ( ConsoleTableModel ) table.getModel () )
          .getStates ( index ) );
      highlightTransitions ( ( ( ConsoleTableModel ) table.getModel () )
          .getTransitions ( index ) );
    }
  }


  /**
   * Handle Edit Machine button pressed
   */
  public final void handleEditMachine ()
  {
    this.gui.wordPanel.setVisible ( false );
    this.model.getJGraph ().setEnabled ( true );

    // Reset all highlightings
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemTransition ()
              .getColor () );
    }

    for ( DefaultStateView current : this.model.getStateViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemState ().getColor () );
    }

    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
  }


  /**
   * Handle Enter Word button pressed
   */
  public final void handleEnterWord ()
  {
    this.gui.wordPanel.setVisible ( true );
    this.model.getJGraph ().setEnabled ( false );
  }


  /**
   * Handles the focus lost event on the machine table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleMachineTableFocusLost ( @SuppressWarnings ( "unused" )
  FocusEvent event )
  {
    if ( this.machineTimer != null )
    {
      this.machineTimer.cancel ();
    }
    this.gui.jGTITableMachine.clearSelection ();
    this.clearHighlight ();
  }


  /**
   * Handles the mouse exited event on the machine table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleMachineTableMouseExited (
      @SuppressWarnings ( "unused" )
      MouseEvent event )
  {
    if ( this.machineTimer != null )
    {
      this.machineTimer.cancel ();
    }
    this.gui.jGTITableMachine.clearSelection ();
    this.clearHighlight ();
  }


  /**
   * Handles the mouse moved event on the machine table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleMachineTableMouseMoved ( MouseEvent event )
  {
    int index = this.gui.jGTITableMachine.rowAtPoint ( event.getPoint () );
    if ( this.machineTimer != null )
    {
      this.machineTimer.cancel ();
    }
    this.machineTimer = new Timer ();
    if ( index == -1 )
    {
      this.gui.jGTITableMachine
          .setCursor ( new Cursor ( Cursor.DEFAULT_CURSOR ) );
      this.machineTimer.schedule ( new HighlightTimerTask (
          this.gui.jGTITableMachine, -1 ), 250 );
    }
    else
    {
      this.gui.jGTITableMachine.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
      this.machineTimer.schedule ( new HighlightTimerTask (
          this.gui.jGTITableMachine, index ), 250 );
    }
  }


  /**
   * Handles {@link ListSelectionEvent}s on the machine table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleMachineTableValueChanged (
      @SuppressWarnings ( "unused" )
      ListSelectionEvent event )
  {
    int index = this.gui.jGTITableMachine.getSelectedRow ();
    if ( index == -1 )
    {
      clearHighlight ();
    }
    else
    {
      ArrayList < State > stateList = new ArrayList < State > ( 1 );
      stateList.add ( this.machine.getState ( index ) );
      highlightStates ( stateList );
    }
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSave ()
  {
    if ( this.file == null )
    {
      return handleSaveAs ();
    }
    try
    {
      Storage.getInstance ().store ( this.model, this.file );
      /*
       * JOptionPane.showMessageDialog ( this.parent, Messages .getString (
       * "MachinePanel.DataSaved" ), Messages //$NON-NLS-1$ .getString (
       * "MachinePanel.Save" ), JOptionPane.INFORMATION_MESSAGE ); //$NON-NLS-1$
       */
    }
    catch ( StoreException e )
    {
      JOptionPane.showMessageDialog ( this.parent, e.getMessage (), Messages
          .getString ( "MachinePanel.Save" ), JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSaveAs ()
  {
    try
    {
      PreferenceManager prefmanager = PreferenceManager.getInstance ();
      JFileChooser chooser = new JFileChooser ( prefmanager.getWorkingPath () );
      chooser.setMultiSelectionEnabled ( false );
      chooser.setAcceptAllFileFilterUsed ( false );
      chooser.addChoosableFileFilter ( new FileFilter ()
      {

        @SuppressWarnings ( "synthetic-access" )
        @Override
        public boolean accept ( File acceptedFile )
        {
          if ( acceptedFile.isDirectory () )
          {
            return true;
          }
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + MachinePanel.this.machine.getMachineType ().toLowerCase () ) )
          {
            return true;
          }
          return false;
        }


        @SuppressWarnings ( "synthetic-access" )
        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + MachinePanel.this.machine.getMachineType () )
              + " (*." //$NON-NLS-1$
              + MachinePanel.this.machine.getMachineType ().toLowerCase ()
              + ")"; //$NON-NLS-1$
        }
      } );
      int n = chooser.showSaveDialog ( this.parent );
      if ( n == JFileChooser.CANCEL_OPTION
          || chooser.getSelectedFile () == null )
        return null;
      if ( chooser.getSelectedFile ().exists () )
      {

        int choice = JOptionPane
            .showConfirmDialog (
                this.parent,
                Messages
                    .getString (
                        "MachinePanel.FileExists", chooser.getSelectedFile ().getName () ), Messages.getString ( //$NON-NLS-1$
                    "MachinePanel.Save" ), JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$

        if ( choice == JOptionPane.NO_OPTION )
          return null;

      }

      String filename = chooser.getSelectedFile ().toString ().matches (
          ".+\\." + this.machine.getMachineType ().toLowerCase () ) ? chooser //$NON-NLS-1$
          .getSelectedFile ().toString () : chooser.getSelectedFile ()
          .toString ()
          + "." //$NON-NLS-1$
          + this.machine.getMachineType ().toLowerCase ();

      Storage.getInstance ().store ( this.model, new File ( filename ) );
      /*
       * JOptionPane.showMessageDialog ( this.parent, Messages .getString (
       * "MachinePanel.DataSaved" ), Messages //$NON-NLS-1$ .getString (
       * "MachinePanel.Save" ), JOptionPane.INFORMATION_MESSAGE ); //$NON-NLS-1$
       */
      prefmanager.setWorkingPath ( chooser.getCurrentDirectory ()
          .getAbsolutePath () );
      this.file = new File ( filename );

    }
    catch ( StoreException e )
    {
      JOptionPane.showMessageDialog ( this.parent, e.getMessage (), Messages
          .getString ( "MachinePanel.Save" ), JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle Toolbar Add State button value changed
   * 
   * @param state The new State of the Add State Toolbar button
   */
  public final void handleToolbarAddState ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.addState );
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.ADD_STATE;
    }
    else
      this.graph.removeMouseListener ( this.addState );
  }


  /**
   * Handle Toolbar Alphabet button action event
   */
  public final void handleToolbarAlphabet ()
  {
    AlphabetDialog alphabetDialog = new AlphabetDialog ( this.parent,
        this.machine );
    alphabetDialog.show ();
  }


  /**
   * Handle Toolbar End button value changed
   * 
   * @param state The new State of the End Toolbar button
   */
  public final void handleToolbarEnd ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.end );
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.ADD_FINAL_STATE;
    }
    else
      this.graph.removeMouseListener ( this.end );
  }


  /**
   * Handle Toolbar Mouse button value changed
   * 
   * @param state The new State of the Mouse Toolbar button
   */
  public final void handleToolbarMouse ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.mouse );
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.MOUSE;
    }
    else
      this.graph.removeMouseListener ( this.mouse );
  }


  /**
   * Handle Toolbar Start button value changed
   * 
   * @param state The new State of the Start Toolbar button
   */
  public final void handleToolbarStart ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.start );
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.ADD_START_STATE;
    }
    else
      this.graph.removeMouseListener ( this.start );
  }


  /**
   * Handle Toolbar Transition button value changed
   * 
   * @param state The new State of the Transition Toolbar button
   */
  public final void handleToolbarTransition ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.transition );
      this.graph.addMouseMotionListener ( this.transitionMove );
      activeMouseAdapter = ACTIVE_MOUSE_ADAPTER.ADD_TRANSITION;

    }
    else
    {
      this.graph.removeMouseListener ( this.transition );
      this.graph.removeMouseMotionListener ( this.transitionMove );
    }
  }


  /**
   * Handle Auto Step Action in the Word Enter Mode
   * 
   * @param event
   */
  @SuppressWarnings ( "synthetic-access" )
  public final void handleWordAutoStep ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      if ( this.autoStepTimer == null )
      {
        this.autoStepTimer = new Timer ();
        int time = PreferenceManager.getInstance ().getAutoStepItem ()
            .getAutoStepInterval ();
        this.autoStepTimer.schedule ( new AutoStepTimerTask (), 0, time );
      }
    }
    else
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
    }
  }


  /**
   * Handle Next Step Action in the Word Enter Mode
   */
  public final void handleWordNextStep ()
  {
    try
    {
      TreeSet < Transition > activeTransitions = this.machine.nextSymbol ();
      ArrayList < DefaultTransitionView > inactiveTransitions = new ArrayList < DefaultTransitionView > ();
      inactiveTransitions.addAll ( this.model.getTransitionViewList () );

      for ( Transition current : activeTransitions )
      {
        DefaultTransitionView transitionView = this.model
            .getTransitionViewForTransition ( current );
        GraphConstants.setGradientColor ( transitionView.getAttributes (),
            PreferenceManager.getInstance ().getColorItemActiveTransition ()
                .getColor () );
        inactiveTransitions.remove ( transitionView );
      }

      for ( DefaultTransitionView current : inactiveTransitions )
      {
        GraphConstants.setGradientColor ( current.getAttributes (),
            PreferenceManager.getInstance ().getColorItemTransition ()
                .getColor () );
      }

      ArrayList < DefaultStateView > inactiveStates = new ArrayList < DefaultStateView > ();
      inactiveStates.addAll ( this.model.getStateViewList () );

      for ( State current : this.machine.getActiveState () )
      {
        DefaultStateView state = this.model.getStateViewForState ( current );
        GraphConstants.setGradientColor ( state.getAttributes (),
            PreferenceManager.getInstance ().getColorItemActiveState ()
                .getColor () );
        inactiveStates.remove ( state );
      }

      for ( DefaultStateView current : inactiveStates )
      {
        GraphConstants.setGradientColor ( current.getAttributes (),
            PreferenceManager.getInstance ().getColorItemState ().getColor () );
      }

      this.graphModel.cellsChanged ( DefaultGraphModel
          .getAll ( this.graphModel ) );

      try
      {
        this.gui.wordPanel.styledWordParserPanel
            .setHighlightedSymbol ( this.machine.getReadedSymbols () );
      }
      catch ( WordResetedException exc )
      {
        this.gui.wordPanel.styledWordParserPanel.setHighlightedSymbol ();
      }
    }
    catch ( WordFinishedException exc )
    {
      this.parent.getLogic ().handleAutoStepStopped ();
      JOptionPane.showMessageDialog ( this.parent, exc.getDescription (), exc
          .getMessage (), JOptionPane.INFORMATION_MESSAGE );
    }
    catch ( WordResetedException exc )
    {
      JOptionPane.showMessageDialog ( this.parent, exc.getDescription (), exc
          .getMessage (), JOptionPane.INFORMATION_MESSAGE );
    }
    catch ( WordNotAcceptedException exc )
    {
      this.parent.getLogic ().handleAutoStepStopped ();
      JOptionPane.showMessageDialog ( this.parent, exc.getDescription (), exc
          .getMessage (), JOptionPane.INFORMATION_MESSAGE );
    }
  }


  /**
   * Handle Previous Step Action in the Word Enter Mode
   */
  public final void handleWordPreviousStep ()
  {
    try
    {
      TreeSet < Transition > activeTransitions = this.machine.previousSymbol ();
      ArrayList < DefaultTransitionView > inactiveTransitions = new ArrayList < DefaultTransitionView > ();
      inactiveTransitions.addAll ( this.model.getTransitionViewList () );

      for ( Transition current : activeTransitions )
      {
        DefaultTransitionView transitionView = this.model
            .getTransitionViewForTransition ( current );
        GraphConstants.setGradientColor ( transitionView.getAttributes (),
            PreferenceManager.getInstance ().getColorItemActiveTransition ()
                .getColor () );
        inactiveTransitions.remove ( transitionView );
      }

      for ( DefaultTransitionView current : inactiveTransitions )
      {
        GraphConstants.setGradientColor ( current.getAttributes (),
            PreferenceManager.getInstance ().getColorItemTransition ()
                .getColor () );
      }

      ArrayList < DefaultStateView > inactiveStates = new ArrayList < DefaultStateView > ();
      inactiveStates.addAll ( this.model.getStateViewList () );

      for ( State current : this.machine.getActiveState () )
      {
        DefaultStateView state = this.model.getStateViewForState ( current );
        GraphConstants.setGradientColor ( state.getAttributes (),
            PreferenceManager.getInstance ().getColorItemActiveState ()
                .getColor () );
        inactiveStates.remove ( state );
      }
      for ( DefaultStateView current : inactiveStates )
      {
        GraphConstants.setGradientColor ( current.getAttributes (),
            PreferenceManager.getInstance ().getColorItemState ().getColor () );
      }
      this.graphModel.cellsChanged ( DefaultGraphModel
          .getAll ( this.graphModel ) );

      /*
       * After the last previous step the current symbol is not defined.
       */
      try
      {
        this.gui.wordPanel.styledWordParserPanel
            .setHighlightedSymbol ( this.machine.getReadedSymbols () );
      }
      catch ( WordResetedException exc )
      {
        this.gui.wordPanel.styledWordParserPanel.setHighlightedSymbol ();
      }
    }
    catch ( WordFinishedException exc )
    {
      JOptionPane.showMessageDialog ( this.parent, exc.getDescription (), exc
          .getMessage (), JOptionPane.INFORMATION_MESSAGE );
    }
    catch ( WordResetedException exc )
    {
      JOptionPane.showMessageDialog ( this.parent, exc.getDescription (), exc
          .getMessage (), JOptionPane.INFORMATION_MESSAGE );
    }
  }


  /**
   * Handle Start Action in the Word Enter Mode
   * 
   * @return true if started else false
   */
  public final boolean handleWordStart ()
  {
    if ( this.gui.wordPanel.styledWordParserPanel.getWord () == null )
    {
      JOptionPane.showMessageDialog ( this.parent, Messages
          .getString ( "MachinePanel.WordModeNoWordEntered" ), Messages //$NON-NLS-1$
          .getString ( "MachinePanel.WordModeError" ), //$NON-NLS-1$
          JOptionPane.ERROR_MESSAGE );
      return false;
    }
    // Reset all highlightings
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemTransition ()
              .getColor () );
    }

    for ( DefaultStateView current : this.model.getStateViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemState ().getColor () );
    }

    this.gui.wordPanel.styledWordParserPanel.setEditable ( false );

    this.machine.start ( this.gui.wordPanel.styledWordParserPanel.getWord () );

    DefaultStateView state = this.model.getStateViewForState ( this.machine
        .getActiveState ( 0 ) );
    GraphConstants.setGradientColor ( state.getAttributes (), PreferenceManager
        .getInstance ().getColorItemActiveState ().getColor () );
    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
    return true;
  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public final void handleWordStop ()
  {
    // Reset all highlightings
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemTransition ()
              .getColor () );
    }

    for ( DefaultStateView current : this.model.getStateViewList () )
    {
      GraphConstants.setGradientColor ( current.getAttributes (),
          PreferenceManager.getInstance ().getColorItemState ().getColor () );
    }

    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );

    this.gui.wordPanel.styledWordParserPanel.setHighlightedSymbol ();
    this.gui.wordPanel.styledWordParserPanel.setEditable ( true );
  }


  /**
   * Highlight the affected states
   * 
   * @param states list with all states that are affected
   */
  private final void highlightStates ( ArrayList < State > states )
  {
    for ( DefaultStateView view : this.oldErrorStates )
    {
      if ( view.getState ().isStartState () )
        GraphConstants.setGradientColor ( view.getAttributes (),
            PreferenceManager.getInstance ().getColorItemStartState ()
                .getColor () );
      else
      {
        GraphConstants.setGradientColor ( view.getAttributes (),
            PreferenceManager.getInstance ().getColorItemState ().getColor () );
      }
    }
    this.oldErrorStates.clear ();
    for ( State state : states )
    {
      DefaultStateView view = this.model.getStateViewForState ( state );
      this.oldErrorStates.add ( view );
      GraphConstants.setGradientColor ( view.getAttributes (),
          PreferenceManager.getInstance ().getColorItemErrorState ()
              .getColor () );
    }
    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
  }


  /**
   * Highlight the affected transitions
   * 
   * @param transitions list with all transitions that are affected
   */
  private final void highlightTransitions ( ArrayList < Transition > transitions )
  {
    for ( DefaultTransitionView view : this.oldErrorTransitions )
    {
      GraphConstants.setLineColor ( view.getAttributes (), PreferenceManager
          .getInstance ().getColorItemTransition ().getColor () );
    }
    this.oldErrorTransitions.clear ();

    for ( Transition t : transitions )
    {
      DefaultTransitionView view = this.model
          .getTransitionViewForTransition ( t );
      this.oldErrorTransitions.add ( view );
      GraphConstants.setLineColor ( view.getAttributes (), PreferenceManager
          .getInstance ().getColorItemErrorTransition ().getColor () );
    }
    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
  }


  /**
   * Initialize the Mouse Adapter of the Toolbar
   */
  @SuppressWarnings ( "synthetic-access" )
  private final void intitializeMouseAdapter ()
  {
    this.mouse = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse has been clicked on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        // return if we are in enter word mode
        if ( MachinePanel.this.enterWordMode )
          return;
        // open configuration
        if ( event.getButton () == MouseEvent.BUTTON1
            && event.getClickCount () == 2 )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
            return;
          else if ( object instanceof DefaultTransitionView )
          {
            // open transition config dialog
            DefaultTransitionView transitionView = ( DefaultTransitionView ) object;
            TransitionDialog dialog = new TransitionDialog (
                MachinePanel.this.parent, MachinePanel.this.machine
                    .getAlphabet (), MachinePanel.this.machine
                    .getPushDownAlphabet (), transitionView.getTransition ()
                    .getSymbol (), transitionView.getSourceView ().getState (),
                transitionView.getTargetView ().getState () );
            dialog.show ();
            if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
            {
              Transition newTransition = dialog.getTransition ();
              MachinePanel.this.graph.getGraphLayoutCache ()
                  .valueForCellChanged ( transitionView, newTransition );
              Transition oldTransition = transitionView.getTransition ();
              oldTransition.clear ();
              try
              {
                oldTransition.add ( newTransition );
              }
              catch ( TransitionException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
              }
            }
          }
          else
          {
            // open transition config dialog
            DefaultStateView state = ( DefaultStateView ) object;
            NewStateNameDialog dialog = new NewStateNameDialog (
                MachinePanel.this.parent, state.getState () );
            dialog.show ();
            if ( ( dialog.getStateName () != null )
                && ( !dialog.getStateName ().equals (
                    state.getState ().getName () ) ) )
            {
              try
              {
                state.getState ().setName ( dialog.getStateName () );
              }
              catch ( StateException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
              }
              MachinePanel.this.graph.getGraphLayoutCache ()
                  .valueForCellChanged ( state, dialog.getStateName () );
            }
          }
        }

        // Return if pressed Button is not the left mouse button
        if ( event.getButton () != MouseEvent.BUTTON3 )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open a new popup menu
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );
        if ( object == null )
          MachinePanel.this.popup = createPopupMenu ();
        else if ( object instanceof DefaultTransitionView )
          MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
        else
          MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );

        if ( MachinePanel.this.popup != null )
          MachinePanel.this.popup.show ( ( Component ) event.getSource (),
              event.getX (), event.getY () );
      }
    };

    this.addState = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        // if Middle Mouse Button was pressed, or we are in word enter mode,
        // return
        if ( ( event.getButton () == MouseEvent.BUTTON2 )
            || MachinePanel.this.enterWordMode )
          return;

        // if an popup menu is open close it and do nothing more
        if ( event.getButton () == MouseEvent.BUTTON1
            && MachinePanel.this.popup != null )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
            MachinePanel.this.popup = createPopupMenu ();
          else if ( object instanceof DefaultTransitionView )
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          else
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );

          if ( MachinePanel.this.popup != null )
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        if ( object instanceof DefaultStateView )
          return;

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), false, false );

          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState );
        }
        catch ( StateException e1 )
        {
          e1.printStackTrace ();
          System.exit ( 1 );
        }

        switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
        {
          case WITHOUT_RETURN_TO_MOUSE :
          {
            // Do nothing
            break;
          }
          case WITH_RETURN_TO_MOUSE :
          {
            // Return to the normal Mouse after every click
            MachinePanel.this.parent.jButtonMouse.setSelected ( true );
            break;
          }
        }

      }
    };

    this.transition = new MouseAdapter ()
    {

      @Override
      public void mouseClicked ( MouseEvent event )
      {
        // if Middle Mouse Button was pressed, or we are in word enter mode,
        // return
        if ( ( event.getButton () == MouseEvent.BUTTON2 )
            || MachinePanel.this.enterWordMode )
          return;

        // if an popup menu is open close it and do nothing more
        if ( event.getButton () == MouseEvent.BUTTON1
            && MachinePanel.this.popup != null )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3
            && MachinePanel.this.firstState == null )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
            MachinePanel.this.popup = createPopupMenu ();
          else if ( object instanceof DefaultTransitionView )
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          else
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );

          if ( MachinePanel.this.popup != null )
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          return;
        }

        TransitionItem transitionItem = PreferenceManager.getInstance ()
            .getTransitionItem ();

        // if drag in progress return
        if ( MachinePanel.this.dragged
            || transitionItem.equals ( TransitionItem.DRAG_MODE ) )
          return;

        if ( MachinePanel.this.firstState == null )
        {
          MachinePanel.this.firstState = ( DefaultStateView ) MachinePanel.this.graph
              .getSelectionCellAt ( event.getPoint () );
          if ( MachinePanel.this.firstState == null )
            return;
        }
        else
        {
          DefaultStateView target = null;
          try
          {

            target = ( DefaultStateView ) MachinePanel.this.graph
                .getNextCellForLocation ( MachinePanel.this.tmpState, event
                    .getPoint ().getX (), event.getPoint ().getY () );

            MachinePanel.this.graphModel.remove ( new Object []
            { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );

          }

          catch ( ClassCastException exc )
          {
            MachinePanel.this.graphModel.remove ( new Object []
            { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          }
          TransitionDialog dialog = new TransitionDialog (
              MachinePanel.this.parent, MachinePanel.this.machine
                  .getAlphabet (), MachinePanel.this.machine
                  .getPushDownAlphabet (), MachinePanel.this.firstState
                  .getState (), target == null ? null : target.getState () );
          dialog.show ();
          if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
          {
            Transition newTransition = dialog.getTransition ();
            if ( target == null )
            {

              try
              {
                State newState = new DefaultState ( MachinePanel.this.machine
                    .getAlphabet (), MachinePanel.this.machine
                    .getPushDownAlphabet (), false, false );
                target = MachinePanel.this.model.createStateView ( event
                    .getPoint ().x
                    / MachinePanel.this.zoomFactor, event.getPoint ().y
                    / MachinePanel.this.zoomFactor, newState );
                newTransition.setStateEnd ( target.getState () );

              }
              catch ( StateException e1 )
              {
                e1.printStackTrace ();
                System.exit ( 1 );
                return;
              }

            }

            MachinePanel.this.model.createTransitionView ( newTransition,
                MachinePanel.this.firstState, target );
            dialog.dispose ();
          }
          switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
          {
            case WITHOUT_RETURN_TO_MOUSE :
            {
              // Do nothing
              break;
            }
            case WITH_RETURN_TO_MOUSE :
            {
              // Return to the normal Mouse after every click
              MachinePanel.this.parent.jButtonMouse.setSelected ( true );
              break;
            }
          }
          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
        }
      }


      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.getButton () != MouseEvent.BUTTON1 )
          return;

        if ( !MachinePanel.this.dragged || MachinePanel.this.firstState == null )
          return;

        DefaultStateView target = null;

        try
        {
          target = ( DefaultStateView ) MachinePanel.this.graph
              .getNextCellForLocation ( MachinePanel.this.tmpState, event
                  .getPoint ().getX (), event.getPoint ().getY () );

          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );

        }
        catch ( ClassCastException exc )
        {
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
        }

        TransitionDialog dialog = new TransitionDialog (
            MachinePanel.this.parent, MachinePanel.this.machine.getAlphabet (),
            MachinePanel.this.machine.getPushDownAlphabet (),
            MachinePanel.this.firstState.getState (), target == null ? null
                : target.getState () );
        dialog.show ();
        if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
        {
          Transition newTransition = dialog.getTransition ();
          if ( target == null )
          {
            try
            {
              State newState;
              newState = new DefaultState ( MachinePanel.this.machine
                  .getAlphabet (), MachinePanel.this.machine
                  .getPushDownAlphabet (), false, false );
              target = MachinePanel.this.model.createStateView ( event
                  .getPoint ().x
                  / MachinePanel.this.zoomFactor, event.getPoint ().y
                  / MachinePanel.this.zoomFactor, newState );
              newTransition.setStateEnd ( target.getState () );
            }
            catch ( StateException e1 )
            {
              e1.printStackTrace ();
              System.exit ( 1 );
              return;
            }
          }

          MachinePanel.this.model.createTransitionView ( newTransition,
              MachinePanel.this.firstState, target );
          dialog.dispose ();

        }
        switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
        {
          case WITHOUT_RETURN_TO_MOUSE :
          {
            // Do nothing
            break;
          }
          case WITH_RETURN_TO_MOUSE :
          {
            // Return to the normal Mouse after every click
            MachinePanel.this.parent.jButtonMouse.setSelected ( true );
            break;
          }
        }
        MachinePanel.this.firstState = null;
        MachinePanel.this.tmpTransition = null;
        MachinePanel.this.tmpState = null;
        MachinePanel.this.dragged = false;
      }

    };

    this.transitionMove = new MouseMotionAdapter ()
    {

      @Override
      public void mouseDragged ( MouseEvent event )
      {
        // Return if we are in word enter mode
        if ( MachinePanel.this.enterWordMode )
          return;
        if ( PreferenceManager.getInstance ().getTransitionItem ().equals (
            TransitionItem.CLICK_MODE ) )
          return;
        double x, y;
        if ( MachinePanel.this.firstState == null )
        {
          MachinePanel.this.dragged = true;
          MachinePanel.this.firstState = ( DefaultStateView ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
        }

        else
        {
          // Remove old tmp state and transition
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          x = event.getX () / MachinePanel.this.zoomFactor;
          y = event.getY () / MachinePanel.this.zoomFactor;
          MachinePanel.this.tmpState = createTmpObject ( x, y );
          MachinePanel.this.graph.getGraphLayoutCache ().insert (
              MachinePanel.this.tmpState );

          MachinePanel.this.tmpTransition = new DefaultEdge ( "" ); //$NON-NLS-1$
          GraphConstants.setLineEnd ( MachinePanel.this.tmpTransition
              .getAttributes (), GraphConstants.ARROW_CLASSIC );
          GraphConstants.setEndFill ( MachinePanel.this.tmpTransition
              .getAttributes (), true );

          MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
              MachinePanel.this.tmpTransition,
              MachinePanel.this.firstState.getChildAt ( 0 ),
              MachinePanel.this.tmpState.getChildAt ( 0 ) );
        }
      }


      /**
       * Invoked when the mouse button has been moved on a component (with no
       * buttons no down).
       */
      @Override
      public void mouseMoved ( MouseEvent event )
      {
        double x, y;

        if ( MachinePanel.this.firstState != null )
        {
          // Remove old tmp state and transition
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );

          x = event.getX () / MachinePanel.this.zoomFactor;
          y = event.getY () / MachinePanel.this.zoomFactor;
          MachinePanel.this.tmpState = createTmpObject ( x, y );
          MachinePanel.this.graph.getGraphLayoutCache ().insert (
              MachinePanel.this.tmpState );

          MachinePanel.this.tmpTransition = new DefaultEdge ( "" ); //$NON-NLS-1$
          GraphConstants.setLineEnd ( MachinePanel.this.tmpTransition
              .getAttributes (), GraphConstants.ARROW_CLASSIC );
          GraphConstants.setEndFill ( MachinePanel.this.tmpTransition
              .getAttributes (), true );

          MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
              MachinePanel.this.tmpTransition,
              MachinePanel.this.firstState.getChildAt ( 0 ),
              MachinePanel.this.tmpState.getChildAt ( 0 ) );
        }
      }
    };

    this.start = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        // if Middle Mouse Button was pressed, or we are in word enter mode,
        // return
        if ( ( event.getButton () == MouseEvent.BUTTON2 )
            || MachinePanel.this.enterWordMode )
          return;

        // if an popup menu is open close it and do nothing more
        if ( event.getButton () == MouseEvent.BUTTON1
            && MachinePanel.this.popup != null )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
            MachinePanel.this.popup = createPopupMenu ();
          else if ( object instanceof DefaultTransitionView )
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          else
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );

          if ( MachinePanel.this.popup != null )
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        if ( object instanceof DefaultStateView )
          return;

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), true, false );
          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState );
        }
        catch ( StateException e1 )
        {
          e1.printStackTrace ();
          System.exit ( 1 );
        }

        switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
        {
          case WITHOUT_RETURN_TO_MOUSE :
          {
            // Do nothing
            break;
          }
          case WITH_RETURN_TO_MOUSE :
          {
            // Return to the normal Mouse after every click
            MachinePanel.this.parent.jButtonMouse.setSelected ( true );
            break;
          }
        }

      }
    };

    this.end = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        // if Middle Mouse Button was pressed, or we are in word enter mode,
        // return
        if ( ( event.getButton () == MouseEvent.BUTTON2 )
            || MachinePanel.this.enterWordMode )
          return;

        // if an popup menu is open close it and do nothing more
        if ( event.getButton () == MouseEvent.BUTTON1
            && MachinePanel.this.popup != null )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
            MachinePanel.this.popup = createPopupMenu ();
          else if ( object instanceof DefaultTransitionView )
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          else
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );

          if ( MachinePanel.this.popup != null )
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        if ( object instanceof DefaultStateView )
          return;

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), false, true );
          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState );
        }
        catch ( StateException e1 )
        {
          e1.printStackTrace ();
          System.exit ( 1 );
        }

        switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
        {
          case WITHOUT_RETURN_TO_MOUSE :
          {
            // Do nothing
            break;
          }
          case WITH_RETURN_TO_MOUSE :
          {
            // Return to the normal Mouse after every click
            MachinePanel.this.parent.jButtonMouse.setSelected ( true );
            break;
          }
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
    return ( this.model.isModified () ) || ( this.file == null );
  }


  /**
   * Getter for the flag if we are in word enter mode
   * 
   * @return true if we are in word enter mode, else false
   */
  public final boolean isWordEnterMode ()
  {
    return this.enterWordMode;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    this.gui.jTabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "MachinePanel.Error" ) ); //$NON-NLS-1$
    this.gui.jTabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "MachinePanel.Warning" ) ); //$NON-NLS-1$
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
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.model.resetModify ();
  }


  /**
   * Set the file for this {@link Machine Panel}.
   * 
   * @param file The file for this {@link Machine Panel}.
   */
  public final void setFileName ( File file )
  {
    this.file = file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setName(java.lang.String)
   */
  public final void setName ( String name )
  {
    this.name = name;
  }


  /**
   * Sets the visibility of the console.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisibleConsole ( boolean visible )
  {
    if ( visible )
    {
      this.gui.jSplitPaneConsole
          .setRightComponent ( this.gui.jTabbedPaneConsole );
      this.gui.jSplitPaneConsole.setDividerSize ( 3 );
      this.gui.jSplitPaneConsole.setDividerLocation ( PreferenceManager
          .getInstance ().getDividerLocationConsole () );
      this.setDividerLocationConsole = true;
    }
    else
    {
      this.setDividerLocationConsole = false;
      this.gui.jSplitPaneConsole.setRightComponent ( null );
      this.gui.jSplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * Sets the visibility of the table.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisibleTable ( boolean visible )
  {
    if ( visible )
    {
      this.gui.jSplitPaneTable.setRightComponent ( this.gui.jScrollPaneMachine );
      this.gui.jSplitPaneTable.setDividerSize ( 3 );
      this.gui.jSplitPaneTable.setDividerLocation ( PreferenceManager
          .getInstance ().getDividerLocationTable () );
      this.setDividerLocationTable = true;
    }
    else
    {
      this.setDividerLocationTable = false;
      this.gui.jSplitPaneTable.setRightComponent ( null );
      this.gui.jSplitPaneTable.setDividerSize ( 0 );
    }
  }


  /**
   * Set the value of the word Enter Mode Flag
   * 
   * @param wordEnterMode The new value
   */
  public final void setWordEnterMode ( boolean wordEnterMode )
  {
    this.enterWordMode = wordEnterMode;
    if ( !wordEnterMode )
    {
      this.gui.jSplitPaneConsole
          .setRightComponent ( this.gui.jTabbedPaneConsole );
      this.gui.jSplitPaneConsole.setDividerSize ( 3 );
      this.gui.jSplitPaneConsole.setDividerLocation ( PreferenceManager
          .getInstance ().getDividerLocationConsole () );
      this.setDividerLocationConsole = true;
    }
    else
    {
      this.setDividerLocationConsole = false;
      this.gui.jSplitPaneConsole.setRightComponent ( null );
      this.gui.jSplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * Set the zoom factor for this panel
   * 
   * @param factor the new zoom factor
   */
  public final void setZoomFactor ( double factor )
  {
    this.zoomFactor = factor;
    this.graph.setScale ( factor );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getGui()
   */
  public EditorPanelForm getGui ()
  {
    return this.gui;
  }
}
