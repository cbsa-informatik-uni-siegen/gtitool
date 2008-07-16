package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.GraphSelectionModel;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultEdge;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.GPCellViewFactory;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.StateView;
import de.unisiegen.gtitool.ui.logic.MainWindow.ButtonState;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.MachineConsoleTableModel;
import de.unisiegen.gtitool.ui.model.PDATableColumnModel;
import de.unisiegen.gtitool.ui.model.PDATableModel;
import de.unisiegen.gtitool.ui.netbeans.MachinePanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.popup.DefaultPopupMenu;
import de.unisiegen.gtitool.ui.popup.EnterWordModePopupMenu;
import de.unisiegen.gtitool.ui.popup.StatePopupMenu;
import de.unisiegen.gtitool.ui.popup.TransitionPopupMenu;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.listener.PDAModeChangedListener;
import de.unisiegen.gtitool.ui.redoundo.MultiItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.StateChangedItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.style.StyledStateSetParserPanel;
import de.unisiegen.gtitool.ui.style.editor.ParserTableCellEditor;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.AcceptedStatus;


/**
 * The Panel containing the diagramm and table representing a machine
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachinePanel implements LogicClass < MachinePanelForm >,
    EditorPanel

{

  /**
   * Signals the active mouse adapter.
   */
  public enum ActiveMouseAdapter
  {
    /**
     * Add Final State is choosen.
     */
    ADD_FINAL_STATE,

    /**
     * Add Start State is choosen.
     */
    ADD_START_STATE,

    /**
     * Add State is choosen.
     */
    ADD_STATE,

    /**
     * Add Transition is choosen.
     */
    ADD_TRANSITION,

    /**
     * Mouse is choosen.
     */
    MOUSE;
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

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          if ( MachinePanel.this.machine.isNextSymbolAvailable () )
          {
            handleWordNextStep ();
          }
          else
          {
            MachinePanel.this.mainWindowForm.getLogic ().removeButtonState (
                ButtonState.SELECTED_AUTO_STEP );
            MachinePanel.this.mainWindowForm.getLogic ()
                .updateWordNavigationStates ();
            cancelAutoStepTimer ();
          }
        }
      } );
    }
  }


  /**
   * The {@link Machine} mode.
   * 
   * @author Christian Fehler
   */
  public enum MachineMode
  {
    /**
     * The edit machine mode.
     */
    EDIT_MACHINE,

    /**
     * The enter word mode.
     */
    ENTER_WORD,

    /**
     * The word navigation mode.
     */
    WORD_NAVIGATION;
  }


  /**
   * The actual active MouseAdapter
   */
  private static ActiveMouseAdapter activeMouseAdapter;


  /**
   * The {@link MouseAdapter} for the end icon in the toolbar.
   */
  private MouseAdapter addEndState;


  /**
   * The {@link MouseAdapter} for the start icon in the toolbar.
   */
  private MouseAdapter addStartState;


  /**
   * The {@link MouseAdapter} for the add State icon in the toolbar.
   */
  private MouseAdapter addState;


  /**
   * The {@link MouseAdapter} for the transition icon in the toolbar.
   */
  private MouseAdapter addTransition;


  /**
   * The {@link Timer} of the auto step mode.
   */
  private Timer autoStepTimer = null;


  /**
   * Flag that indicates if a cell is edited.
   */
  private boolean cellEditingMode = false;


  /**
   * Signals if drag in progress.
   */
  private boolean dragged;


  /**
   * The {@link MachineMode}.
   */
  private MachineMode machineMode = MachineMode.EDIT_MACHINE;


  /**
   * The {@link MouseAdapter} for the enter word mode.
   */
  private MouseAdapter enterWordModeMouse;


  /**
   * The {@link MachineConsoleTableModel} for the error table.
   */
  private MachineConsoleTableModel errorTableModel;


  /**
   * The {@link File} for this {@link MachinePanel}.
   */
  private File file;


  /**
   * The source state for a new {@link Transition}.
   */
  private DefaultStateView firstState;


  /**
   * The {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraph;


  /**
   * The {@link DefaultGraphModel} for this graph.
   */
  private DefaultGraphModel graphModel;


  /**
   * The {@link MachinePanelForm}.
   */
  private MachinePanelForm gui;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link Machine}.
   */
  private Machine machine;


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * Flag signals if mouse button is down.
   */
  private boolean mouseDown = false;


  /**
   * The name of this {@link MachinePanel}.
   */
  private String name = null;


  /**
   * The {@link MouseAdapter} for the mouse icon in the toolbar.
   */
  private MouseAdapter normalMouse;


  /**
   * The {@link JPopupMenu}.
   */
  private JPopupMenu popup;


  /**
   * The {@link RedoUndoHandler}
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * Flag that indicates if the console divider location should be stored.
   */
  private boolean setDividerLocationConsole = true;


  /**
   * Flag that indicates if the table divider location should be stored.
   */
  private boolean setDividerLocationTable = true;


  /**
   * Flag that indicates if the {@link PDA} table divider location should be
   * stored.
   */
  private boolean setDividerLocationPDATable = true;


  /**
   * The tmp state for a new Transition.
   */
  private DefaultGraphCell tmpState;


  /**
   * The tmp transition.
   */
  private DefaultEdge tmpTransition;


  /**
   * The {@link MouseAdapter} for the transition icon in the toolbar.
   */
  private MouseMotionAdapter transitionMove;


  /**
   * The {@link MachineConsoleTableModel} for the warning table.
   */
  private MachineConsoleTableModel warningTableModel;


  /**
   * The zoom factor for this graph .
   */
  private double zoomFactor;


  /**
   * The {@link PDATableColumnModel}.
   */
  private PDATableColumnModel pdaTableColumnModel = new PDATableColumnModel ();


  /**
   * Create a new Machine Panel Object
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param model The {@link DefaultMachineModel} of this panel.
   * @param file The {@link File} of this {@link MachinePanel}.
   */
  public MachinePanel ( MainWindowForm mainWindowForm,
      DefaultMachineModel model, File file )
  {
    this.mainWindowForm = mainWindowForm;
    this.model = model;
    this.file = file;
    this.gui = new MachinePanelForm ( this );

    this.redoUndoHandler = new RedoUndoHandler ( this.mainWindowForm );
    this.model.setRedoUndoHandler ( this.redoUndoHandler );

    intitializeMouseAdapter ();

    /*
     * Divider Location
     */
    this.gui.jGTISplitPaneConsole.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationConsole () );
    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );
    this.gui.jGTISplitPaneConsole.addPropertyChangeListener (
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
            MachinePanel.this.setDividerLocationConsole = true;
          }
        } );

    this.gui.jGTISplitPaneTable.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationTable () );
    setVisibleTable ( this.mainWindowForm.getJCheckBoxMenuItemTable ()
        .getState () );
    this.gui.jGTISplitPaneTable.addPropertyChangeListener (
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
            MachinePanel.this.setDividerLocationTable = true;
          }
        } );

    this.gui.jGTISplitPanePDATable.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationPDATable () );
    this.gui.jGTISplitPanePDATable.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            if ( MachinePanel.this.setDividerLocationPDATable )
            {
              PreferenceManager.getInstance ().setDividerLocationPDATable (
                  ( ( Integer ) event.getNewValue () ).intValue () );
            }
            MachinePanel.this.setDividerLocationPDATable = true;
          }
        } );

    initialize ();
    initializeMachineTable ();
    initializePDATable ();
    initializeSecondView ();

    addListener ();
    addGraphListener ();

    this.gui.wordPanel.styledWordParserPanel.parse ();

    // Update the machine table status
    updateMachineTableStatus ();

    // Reset modify
    resetModify ();
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
   * Add all needed listener to the {@link JGTIGraph}.
   */
  private final void addGraphListener ()
  {
    this.jGTIGraph.addKeyListener ( new KeyAdapter ()
    {

      @Override
      @SuppressWarnings ( "synthetic-access" )
      public void keyPressed ( KeyEvent event )
      {
        if ( event.getKeyCode () == KeyEvent.VK_ESCAPE )
        {
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
          MachinePanel.this.dragged = false;
        }
        else if ( !MachinePanel.this.mouseDown
            && ( event.getKeyCode () == KeyEvent.VK_DELETE ) )
        {
          Object object = MachinePanel.this.jGTIGraph.getSelectionCell ();

          if ( object instanceof DefaultStateView )
          {
            deleteState ( ( DefaultStateView ) object );
          }
          else if ( object instanceof DefaultTransitionView )
          {
            deleteTransition ( ( DefaultTransitionView ) object );
          }
        }
      }
    } );

    this.jGTIGraph.addGraphSelectionListener ( new GraphSelectionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void valueChanged (
          @SuppressWarnings ( "unused" ) GraphSelectionEvent event )
      {
        updateSelected ();
      }
    } );

    this.jGTIGraph.addMouseListener ( new MouseAdapter ()
    {

      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( @SuppressWarnings ( "unused" ) MouseEvent e )
      {
        MachinePanel.this.mouseDown = true;
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      @Override
      @SuppressWarnings ( "synthetic-access" )
      public void mouseReleased ( @SuppressWarnings ( "unused" ) MouseEvent e )
      {
        MachinePanel.this.mouseDown = false;
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
   * Add all needed listener.
   */
  private final void addListener ()
  {
    this.gui.jGTITableMachine.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleMachineTableValueChanged ( event );
          }
        } );

    this.gui.jGTITableMachinePDA.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleMachinePDATableValueChanged ( event );
          }
        } );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
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
   * Add a new Warning
   * 
   * @param machineException The {@link MachineException} containing the data
   */
  public final void addWarning ( MachineException machineException )
  {
    this.warningTableModel.addRow ( machineException );
  }


  /**
   * Cancels the auto step {@link Timer}.
   * 
   * @return Returns true if the timer was canceled, otherwise false.
   */
  public final boolean cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
      return true;
    }
    return false;
  }


  /**
   * Clears the highlight.
   */
  public final void clearHighlight ()
  {
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      Transition transition = current.getTransition ();
      transition.setError ( false );
      transition.setActive ( false );

      // Reset the symbols
      for ( Symbol currentSymbol : transition.getSymbol () )
      {
        currentSymbol.setError ( false );
        currentSymbol.setActive ( false );
      }
      for ( Symbol currentSymbol : transition.getPushDownWordRead () )
      {
        currentSymbol.setError ( false );
        currentSymbol.setActive ( false );
      }
      for ( Symbol currentSymbol : transition.getPushDownWordWrite () )
      {
        currentSymbol.setError ( false );
        currentSymbol.setActive ( false );
      }
    }

    for ( DefaultStateView current : this.model.getStateViewList () )
    {
      State state = current.getState ();
      state.setError ( false );
      state.setActive ( false );
      current.setOverwrittenColor ( null );
    }

    performCellsChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#clearValidationMessages()
   */
  public final void clearValidationMessages ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "MachinePanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "MachinePanel.Warning" ) ); //$NON-NLS-1$

    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * Create a enter word mode Popup Menu
   * 
   * @return the new created Popup Menu
   */
  private final EnterWordModePopupMenu createEnterWordModePopupMenu ()
  {
    return new EnterWordModePopupMenu ( this, this.mainWindowForm );
  }


  /**
   * Create a standard Popup Menu
   * 
   * @return the new created Popup Menu
   */
  private final DefaultPopupMenu createPopupMenu ()
  {
    int factor = ( new Double ( this.zoomFactor * 100 ) ).intValue ();
    return new DefaultPopupMenu ( this, factor );
  }


  /**
   * Create a new Popup Menu for the given State
   * 
   * @param stateView the State for to create a popup menu
   * @return the new created Popup Menu
   */
  private final StatePopupMenu createStatePopupMenu ( DefaultStateView stateView )
  {
    return new StatePopupMenu ( this.mainWindowForm, this, this.model,
        stateView );
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
    String viewClass = StateView.class.getName ();

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
    return new TransitionPopupMenu ( this.gui, transitionView, this.machine
        .getAlphabet (), this.machine.getPushDownAlphabet () );
  }


  /**
   * Delete the given {@link DefaultStateView}.
   * 
   * @param state the {@link DefaultStateView} to delete.
   */
  public final void deleteState ( DefaultStateView state )
  {
    ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
        Messages.getString ( "TransitionDialog.DeleteStateQuestion", //$NON-NLS-1$
            state ),
        Messages.getString ( "TransitionDialog.DeleteStateTitle" ), true, true, //$NON-NLS-1$
        false );
    confirmDialog.show ();
    if ( confirmDialog.isConfirmed () )
    {
      this.model.removeState ( state, true );
    }
  }


  /**
   * Delete the given {@link DefaultTransitionView}.
   * 
   * @param transition the {@link DefaultTransitionView} to delete.
   */
  public final void deleteTransition ( DefaultTransitionView transition )
  {
    ConfirmDialog confirmedDialog = new ConfirmDialog ( this.mainWindowForm,
        Messages.getString ( "TransitionDialog.DeleteTransitionQuestion", //$NON-NLS-1$
            transition ), Messages
            .getString ( "TransitionDialog.DeleteTransitionTitle" ), true, //$NON-NLS-1$
        true, false );
    confirmedDialog.show ();
    if ( confirmedDialog.isConfirmed () )
    {
      this.model.removeTransition ( transition, true );
      performCellsChanged ();
    }
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  private final void fireModifyStatusChanged ( boolean forceModify )
  {
    clearValidationMessages ();
    clearHighlight ();

    // is needed if a cell is deleted
    updateSelected ();

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
   * @see EditorPanel#getConverter()
   */
  public final Converter getConverter ()
  {
    if ( this.machine.getMachineType ().equals ( MachineType.NFA ) )
    {
      return new ConvertMachineDialog ( this.mainWindowForm, this );
    }
    else if ( this.machine.getMachineType ().equals ( MachineType.ENFA ) )
    {
      return new ConvertMachineDialog ( this.mainWindowForm, this );
    }
    else
    {
      throw new RuntimeException ( "unsupported machine type" ); //$NON-NLS-1$
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
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public final String getFileEnding ()
  {
    return "." + this.machine.getMachineType ().getFileEnding (); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final MachinePanelForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the {@link JGTIGraph}.
   * 
   * @return The {@link JGTIGraph}.
   * @see #jGTIGraph
   */
  public final JGTIGraph getJGTIGraph ()
  {
    return this.jGTIGraph;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jGTITabbedPaneConsole;
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
   * Returns the {@link MachineMode}.
   * 
   * @return The {@link MachineMode}.
   */
  public final MachineMode getMachineMode ()
  {
    return this.machineMode;
  }


  /**
   * Returns the {@link TableColumnModel} of the machine.
   * 
   * @return The {@link TableColumnModel} of the machine.
   */
  public final TableColumnModel getMachineTableColumnModel ()
  {
    return this.machine.getTableColumnModel ();
  }


  /**
   * Returns the {@link TableModel} of the machine.
   * 
   * @return The {@link TableModel} of the machine.
   */
  public final TableModel getMachineTableModel ()
  {
    return this.machine;
  }


  /**
   * Returns the {@link MainWindow}.
   * 
   * @return The {@link MainWindow}.
   * @see #mainWindowForm
   */
  public final MainWindow getMainWindow ()
  {
    return this.mainWindowForm.getLogic ();
  }


  /**
   * Returns the {@link MainWindowForm}.
   * 
   * @return The {@link MainWindowForm}.
   * @see #mainWindowForm
   */
  public final MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
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
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getPanel()
   */
  public final JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * Returns the pdaTableColumnModel.
   * 
   * @return The pdaTableColumnModel.
   * @see #pdaTableColumnModel
   */
  public final PDATableColumnModel getPdaTableColumnModel ()
  {
    return this.pdaTableColumnModel;
  }


  /**
   * Returns the {@link PDATableModel}.
   * 
   * @return The {@link PDATableModel}.
   */
  public final PDATableModel getPDATableModel ()
  {
    return this.model.getPDATableModel ();
  }


  /**
   * Returns the redoUndoHandler.
   * 
   * @return The redoUndoHandler.
   * @see #redoUndoHandler
   */
  public final RedoUndoHandler getRedoUndoHandler ()
  {
    return this.redoUndoHandler;
  }


  /**
   * Handles the {@link CellEditor} start cell editing event.
   * 
   * @param row The edited row.
   * @param column The edited column.
   */
  public final void handleCellEditorStartCellEditing ( int row, int column )
  {
    // Clear highlight
    clearHighlight ();
    this.jGTIGraph.clearSelection ();
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      Transition transition = current.getTransition ();
      transition.setError ( false );
      transition.setActive ( false );
    }

    State beginState = this.machine.getState ( row );

    // states
    ArrayList < State > stateList = new ArrayList < State > ();
    stateList.add ( beginState );
    highlightStateActive ( stateList );

    Symbol symbol;
    if ( column == Machine.EPSILON_COLUMN )
    {
      symbol = new DefaultSymbol ();
    }
    else
    {
      symbol = this.machine.getAlphabet ().get ( column - 2 );
    }

    for ( Transition currentTransition : this.machine.getTransition () )
    {
      if ( currentTransition.contains ( symbol )
          && currentTransition.getStateBegin ().equals ( beginState ) )
      {
        for ( Symbol currentSymbol : currentTransition.getSymbol () )
        {
          currentSymbol.setError ( false );
          currentSymbol.setActive ( currentSymbol.equals ( symbol ) );
        }
        currentTransition.setActive ( true );
      }
    }

    this.cellEditingMode = true;
    this.gui.jGTITableMachine.repaint ();
  }


  /**
   * Handles focus lost event on the console table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleConsoleTableFocusLost (
      @SuppressWarnings ( "unused" ) FocusEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    clearHighlight ();
  }


  /**
   * Handles the mouse exited event on the console table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleConsoleTableMouseExited (
      @SuppressWarnings ( "unused" ) MouseEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    clearHighlight ();
  }


  /**
   * Handles {@link ListSelectionEvent}s on the console table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleConsoleTableValueChanged ( ListSelectionEvent event )
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

    this.model.getJGTIGraph ().clearSelection ();
    clearHighlight ();

    int index = table.getSelectedRow ();
    if ( index != -1 )
    {
      highlightStateError ( ( ( MachineConsoleTableModel ) table.getModel () )
          .getStates ( index ) );
      highlightTransitionError ( ( ( MachineConsoleTableModel ) table
          .getModel () ).getTransitions ( index ) );
      highlightSymbolError ( ( ( MachineConsoleTableModel ) table.getModel () )
          .getSymbols ( index ) );
    }
  }


  /**
   * Handles the edit {@link Machine} event.
   */
  public final void handleEditMachine ()
  {
    handleWordStop ();

    this.machineMode = MachineMode.EDIT_MACHINE;
    this.jGTIGraph.removeMouseListener ( this.enterWordModeMouse );
    this.gui.wordPanel.setVisible ( false );
    this.model.getJGTIGraph ().setEnabled ( true );

    clearHighlight ();

    performCellsChanged ();
  }


  /**
   * Handles the enter {@link Word} event.
   */
  public final void handleEnterWord ()
  {
    clearHighlight ();
    this.machineMode = MachineMode.ENTER_WORD;

    if ( !this.machine.getMachineType ().equals ( MachineType.PDA ) )
    {
      if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.SHOW ) )
      {
        // do nothing
      }
      else if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.HIDE ) )
      {
        this.gui.wordPanel.jGTILabelStack.setEnabled ( false );
        this.gui.wordPanel.styledStackParserPanel.setEnabled ( false );
        this.gui.wordPanel.jGTILabelPushDownAlphabet.setEnabled ( false );
        this.gui.wordPanel.styledAlphabetParserPanelPushDown
            .setEnabled ( false );
      }
      else
      {
        throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$
      }
    }

    this.jGTIGraph.clearSelection ();
    this.gui.wordPanel.setVisible ( true );
    this.jGTIGraph.setEnabled ( false );
    this.jGTIGraph.addMouseListener ( this.enterWordModeMouse );
    this.gui.wordPanel.requestFocus ();
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    ExchangeDialog exchangeDialog = new ExchangeDialog ( this.mainWindowForm
        .getLogic (), this.model.getElement (), this.file );
    exchangeDialog.show ();
  }


  /**
   * Handles the export picture event.
   */
  public final void handleExportPicture ()
  {
    FileFilter fileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File acceptedFile )
      {
        if ( acceptedFile.isDirectory () )
        {
          return true;
        }
        if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\.png" ) ) //$NON-NLS-1$
        {
          return true;
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        return Messages.getString ( "MachinePanel.ExportPicturePNG" ) //$NON-NLS-1$
            + " (*.png)"; //$NON-NLS-1$
      }
    };

    SaveDialog saveDialog = new SaveDialog ( this.mainWindowForm,
        PreferenceManager.getInstance ().getWorkingPath (), fileFilter,
        fileFilter );
    saveDialog.show ();

    if ( ( !saveDialog.isConfirmed () )
        || ( saveDialog.getSelectedFile () == null ) )
    {
      return;
    }

    if ( saveDialog.getSelectedFile ().exists () )
    {
      ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
          Messages.getString ( "MachinePanel.FileExists", saveDialog //$NON-NLS-1$
              .getSelectedFile ().getName () ), Messages
              .getString ( "MachinePanel.ExportPicture" ), true, true, false ); //$NON-NLS-1$
      confirmDialog.show ();
      if ( confirmDialog.isNotConfirmed () )
      {
        return;
      }
    }

    String filename = saveDialog.getSelectedFile ().toString ().toLowerCase ()
        .matches ( ".+\\.png" ) ? saveDialog.getSelectedFile ().toString () //$NON-NLS-1$
        : saveDialog.getSelectedFile ().toString () + ".png"; //$NON-NLS-1$

    PreferenceManager.getInstance ().setWorkingPath (
        saveDialog.getCurrentDirectory ().getAbsolutePath () );

    Rectangle usedBounds = this.jGTIGraph.getUsedBounds ();

    int inset = 20;
    int width = usedBounds.width + 2 * inset;
    int height = usedBounds.height + 2 * inset;

    BufferedImage image = new BufferedImage ( width, height,
        BufferedImage.TYPE_INT_RGB );
    Graphics graphics = image.getGraphics ();

    graphics.fillRect ( 0, 0, width, height );
    graphics.translate ( inset - usedBounds.x, inset - usedBounds.y );
    this.jGTIGraph.paintAll ( graphics );
    graphics.dispose ();

    try
    {
      ImageIO.write ( image, "PNG", new File ( filename ) ); //$NON-NLS-1$
    }
    catch ( IOException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, Messages
          .getString ( "MachinePanel.ExportPictureError" ), Messages//$NON-NLS-1$
          .getString ( "MachinePanel.ExportPictureErrorTitle" ) );//$NON-NLS-1$
      infoDialog.show ();
    }
  }


  /**
   * Handles the history event.
   */
  public final void handleHistory ()
  {
    if ( !this.machineMode.equals ( MachineMode.WORD_NAVIGATION ) )
    {
      throw new RuntimeException ( "the word navigation is not in progress" ); //$NON-NLS-1$
    }

    HistoryDialog historyDialog = new HistoryDialog ( this.mainWindowForm,
        this.machine, this );
    historyDialog.show ();
  }


  /**
   * Handles the focus lost event on the machine pda table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleMachinePDATableFocusLost (
      @SuppressWarnings ( "unused" ) FocusEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachinePDA.clearSelection ();
      clearHighlight ();
    }
  }


  /**
   * Handles the mouse exited event on the machine pda table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleMachinePDATableMouseExited (
      @SuppressWarnings ( "unused" ) MouseEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachinePDA.clearSelection ();
      clearHighlight ();
    }
  }


  /**
   * Handles {@link ListSelectionEvent}s on the machine pda table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleMachinePDATableValueChanged (
      @SuppressWarnings ( "unused" ) ListSelectionEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      clearHighlight ();

      int index = this.gui.jGTITableMachinePDA.getSelectedRow ();
      if ( index != -1 )
      {
        ArrayList < Transition > transitionList = new ArrayList < Transition > (
            1 );
        Transition transition = this.model.getPDATableModel ().getTransition (
            index );
        transitionList.add ( transition );
        highlightTransitionActive ( transitionList );

        ArrayList < Symbol > symbolList = new ArrayList < Symbol > ( transition
            .size () );
        symbolList.addAll ( transition.getSymbol () );
        highlightSymbolActive ( symbolList );
      }
    }
  }


  /**
   * Handles the focus lost event on the machine table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleMachineTableFocusLost (
      @SuppressWarnings ( "unused" ) FocusEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachine.clearSelection ();
      clearHighlight ();
    }
  }


  /**
   * Handles the mouse exited event on the machine table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleMachineTableMouseExited (
      @SuppressWarnings ( "unused" ) MouseEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      this.gui.jGTITableMachine.clearSelection ();
      clearHighlight ();
    }
  }


  /**
   * Handles {@link ListSelectionEvent}s on the machine table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleMachineTableValueChanged (
      @SuppressWarnings ( "unused" ) ListSelectionEvent event )
  {
    if ( this.machineMode.equals ( MachineMode.EDIT_MACHINE )
        && !this.cellEditingMode )
    {
      clearHighlight ();

      int index = this.gui.jGTITableMachine.getSelectedRow ();
      if ( index != -1 )
      {
        State state = this.machine.getState ( index );
        ArrayList < State > stateList = new ArrayList < State > ( 1 );
        stateList.add ( state );
        highlightStateActive ( stateList );

        ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
        highlightTransitionActive ( state.getTransitionBegin () );
        for ( Transition currentTransition : state.getTransitionBegin () )
        {
          for ( Symbol currentSymbol : currentTransition )
          {
            symbolList.add ( currentSymbol );
          }
        }
        highlightSymbolActive ( symbolList );
      }
    }
  }


  /**
   * Handles the reachable states event.
   */
  public final void handleReachableStates ()
  {
    ReachableStatesDialog reachableStatesDialog = new ReachableStatesDialog (
        this.mainWindowForm, this );
    reachableStatesDialog.show ();
  }


  /**
   * Handle redo button pressed
   */
  public final void handleRedo ()
  {
    this.redoUndoHandler.redo ();
    fireModifyStatusChanged ( false );
    performCellsChanged ();
  }


  /**
   * Handles the reorder state names event.
   */
  public final void handleReorderStateNames ()
  {
    MultiItem item = new MultiItem ();
    String stateName = "z"; //$NON-NLS-1$
    int count = 0;
    for ( State current : this.machine.getState () )
    {
      try
      {

        String oldName = current.getName ();

        current.setName ( stateName + count );

        item.addItem ( new StateChangedItem ( this.jGTIGraph, current, oldName,
            current.isStartState (), current.isFinalState () ) );
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
        return;
      }
      count++ ;
    }

    this.redoUndoHandler.addItem ( item );
    performCellsChanged ();
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
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
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
      FileFilter fileFilter = new FileFilter ()
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
              + MachinePanel.this.machine.getMachineType ().getFileEnding () ) )
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
              + MachinePanel.this.machine.getMachineType ().toString () )
              + " (*." //$NON-NLS-1$
              + MachinePanel.this.machine.getMachineType ().getFileEnding ()
              + ")"; //$NON-NLS-1$
        }
      };

      SaveDialog saveDialog = new SaveDialog ( this.mainWindowForm,
          PreferenceManager.getInstance ().getWorkingPath (), fileFilter,
          fileFilter );
      saveDialog.show ();

      if ( ( !saveDialog.isConfirmed () )
          || ( saveDialog.getSelectedFile () == null ) )
      {
        return null;
      }

      if ( saveDialog.getSelectedFile ().exists () )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
            Messages.getString (
                "MachinePanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
                    .getName () ), Messages.getString ( "MachinePanel.Save" ), //$NON-NLS-1$
            true, true, false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
        {
          return null;
        }
      }

      String filename = saveDialog.getSelectedFile ().toString ().matches (
          ".+\\." + this.machine.getMachineType ().getFileEnding () ) ? saveDialog //$NON-NLS-1$
          .getSelectedFile ().toString ()
          : saveDialog.getSelectedFile ().toString ()
              + "." + this.machine.getMachineType ().getFileEnding (); //$NON-NLS-1$

      Storage.getInstance ().store ( this.model, new File ( filename ) );

      PreferenceManager.getInstance ().setWorkingPath (
          saveDialog.getCurrentDirectory ().getAbsolutePath () );
      this.file = new File ( filename );

    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
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
      this.jGTIGraph.addMouseListener ( this.addState );
      activeMouseAdapter = ActiveMouseAdapter.ADD_STATE;
    }
    else
    {
      this.jGTIGraph.removeMouseListener ( this.addState );
    }
  }


  /**
   * Handle Toolbar Alphabet button action event
   */
  public final void handleToolbarEditDocument ()
  {
    AlphabetDialog alphabetDialog = new AlphabetDialog ( this.mainWindowForm,
        this, this.machine );
    alphabetDialog.show ();
    initializeMachineTable ();
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
      this.jGTIGraph.addMouseListener ( this.addEndState );
      activeMouseAdapter = ActiveMouseAdapter.ADD_FINAL_STATE;
    }
    else
    {
      this.jGTIGraph.removeMouseListener ( this.addEndState );
    }
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
      this.jGTIGraph.addMouseListener ( this.normalMouse );
      activeMouseAdapter = ActiveMouseAdapter.MOUSE;
    }
    else
    {
      this.jGTIGraph.removeMouseListener ( this.normalMouse );
    }
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
      this.jGTIGraph.addMouseListener ( this.addStartState );
      activeMouseAdapter = ActiveMouseAdapter.ADD_START_STATE;
    }
    else
    {
      this.jGTIGraph.removeMouseListener ( this.addStartState );
    }
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
      this.jGTIGraph.addMouseListener ( this.addTransition );
      this.jGTIGraph.addMouseMotionListener ( this.transitionMove );
      activeMouseAdapter = ActiveMouseAdapter.ADD_TRANSITION;
    }
    else
    {
      this.jGTIGraph.removeMouseListener ( this.addTransition );
      this.jGTIGraph.removeMouseMotionListener ( this.transitionMove );
    }
  }


  /**
   * Handle undo button pressed
   */
  public final void handleUndo ()
  {
    this.redoUndoHandler.undo ();
    fireModifyStatusChanged ( false );
    performCellsChanged ();
  }


  /**
   * Handle Auto Step Action in the Word Enter Mode
   * 
   * @param event
   */
  public final void handleWordAutoStep ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      cancelAutoStepTimer ();
      startAutoStepTimer ( false );
    }
    else
    {
      cancelAutoStepTimer ();
    }

    updateAcceptedState ();

    this.mainWindowForm.getLogic ().updateWordNavigationStates ();
  }


  /**
   * Handles next step action in the word enter mode.
   */
  public final void handleWordNextStep ()
  {
    try
    {
      if ( this.machine.isUserInputNeeded () )
      {
        boolean running = cancelAutoStepTimer ();

        ChooseTransitionDialog dialog = new ChooseTransitionDialog (
            this.mainWindowForm, this.machine.getPossibleTransitions () );
        dialog.show ();

        if ( running )
        {
          startAutoStepTimer ( true );
        }

        this.machine.nextSymbol ( dialog.getChoosenTransition () );
      }
      else
      {
        this.machine.nextSymbol ();
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machine.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }

      // update stack
      this.gui.wordPanel.styledStackParserPanel.setText ( this.machine
          .getStack () );

      // update button states
      this.mainWindowForm.getLogic ().updateWordNavigationStates ();

      // update graph
      performCellsChanged ();

      try
      {
        this.gui.wordPanel.styledWordParserPanel
            .setHighlightedParseableEntity ( this.machine.getReadedSymbols () );
      }
      catch ( WordResetedException exc )
      {
        this.gui.wordPanel.styledWordParserPanel
            .setHighlightedParseableEntity ();
      }
    }
    catch ( WordFinishedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }

    updateAcceptedState ();
  }


  /**
   * Handles previous step action in the word enter mode.
   */
  public final void handleWordPreviousStep ()
  {
    this.machine.previousSymbol ();

    this.gui.wordPanel.styledStackParserPanel.setText ( this.machine
        .getStack () );

    this.mainWindowForm.getLogic ().updateWordNavigationStates ();
    performCellsChanged ();

    // after the last previous step the current symbol is not defined
    try
    {
      this.gui.wordPanel.styledWordParserPanel
          .setHighlightedParseableEntity ( this.machine.getReadedSymbols () );
    }
    catch ( WordResetedException exc )
    {
      this.gui.wordPanel.styledWordParserPanel.setHighlightedParseableEntity ();
    }
    catch ( WordFinishedException exc )
    {
      this.gui.wordPanel.styledWordParserPanel.setHighlightedParseableEntity ();
    }

    updateAcceptedState ();
  }


  /**
   * Handles the word start action in the word enter mode.
   * 
   * @return true if started else false
   */
  public final boolean handleWordStart ()
  {
    if ( this.gui.wordPanel.styledWordParserPanel.getParsedObject () == null )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, Messages
          .getString ( "MachinePanel.WordModeNoWordEntered" ), Messages //$NON-NLS-1$
          .getString ( "MachinePanel.WordModeError" ) ); //$NON-NLS-1$
      infoDialog.show ();
      return false;
    }

    this.machineMode = MachineMode.WORD_NAVIGATION;
    clearHighlight ();

    this.gui.wordPanel.styledWordParserPanel.setEditable ( false );
    this.gui.wordPanel.styledAlphabetParserPanelInput.setCopyable ( false );
    this.gui.wordPanel.styledAlphabetParserPanelPushDown.setCopyable ( false );

    this.machine.start ( this.gui.wordPanel.styledWordParserPanel
        .getParsedObject () );

    this.gui.wordPanel.styledStackParserPanel.setText ( this.machine
        .getStack () );

    this.mainWindowForm.getLogic ().updateWordNavigationStates ();
    performCellsChanged ();

    updateAcceptedState ();

    return true;
  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public final void handleWordStop ()
  {
    cancelAutoStepTimer ();

    this.machineMode = MachineMode.ENTER_WORD;
    clearHighlight ();

    this.machine.stop ();

    this.gui.wordPanel.styledStackParserPanel.setText ( this.machine
        .getStack () );

    this.mainWindowForm.getLogic ().removeButtonState (
        ButtonState.SELECTED_AUTO_STEP );
    this.mainWindowForm.getLogic ().addButtonState (
        ButtonState.ENABLED_NAVIGATION_START );
    performCellsChanged ();

    updateAcceptedState ();

    this.gui.wordPanel.styledWordParserPanel.setHighlightedParseableEntity ();
    this.gui.wordPanel.styledWordParserPanel.setEditable ( true );
    this.gui.wordPanel.styledAlphabetParserPanelInput.setCopyable ( true );
    this.gui.wordPanel.styledAlphabetParserPanelPushDown.setCopyable ( true );
  }


  /**
   * Highlight the affected active {@link State}s with the active highlight.
   * 
   * @param states list with all {@link State}s that are affected.
   */
  private final void highlightStateActive ( ArrayList < State > states )
  {
    for ( State current : states )
    {
      current.setActive ( true );
    }

    performCellsChanged ();
  }


  /**
   * Highlight the affected error {@link State}s with the error highlight.
   * 
   * @param states List with all {@link State}s that are affected.
   */
  private final void highlightStateError ( ArrayList < State > states )
  {
    for ( State current : states )
    {
      current.setError ( true );
    }

    performCellsChanged ();
  }


  /**
   * Highlight the affected {@link Symbol}s with the active highlight.
   * 
   * @param symbols List with all {@link Symbol}s that are affected.
   */
  private final void highlightSymbolActive ( ArrayList < Symbol > symbols )
  {
    for ( Symbol current : symbols )
    {
      current.setActive ( true );
    }

    performCellsChanged ();
  }


  /**
   * Highlight the affected {@link Symbol}s with the error highlight.
   * 
   * @param symbols List with all {@link Symbol}s that are affected.
   */
  private final void highlightSymbolError ( ArrayList < Symbol > symbols )
  {
    for ( Symbol current : symbols )
    {
      current.setError ( true );
    }

    performCellsChanged ();
  }


  /**
   * Highlight the affected {@link Transition}s with the active highlight.
   * 
   * @param transitions List with all {@link Transition}s that are affected.
   */
  private final void highlightTransitionActive (
      ArrayList < Transition > transitions )
  {
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      Transition transition = current.getTransition ();
      transition.setError ( false );
      transition.setActive ( false );
    }

    for ( Transition current : transitions )
    {
      current.setActive ( true );
    }

    performCellsChanged ();
  }


  /**
   * Highlight the affected {@link Transition}s with the error highlight.
   * 
   * @param transitions List with all {@link Transition}s that are affected.
   */
  private final void highlightTransitionError (
      ArrayList < Transition > transitions )
  {
    for ( DefaultTransitionView current : this.model.getTransitionViewList () )
    {
      Transition transition = current.getTransition ();
      transition.setError ( false );
      transition.setActive ( false );
    }

    for ( Transition current : transitions )
    {
      current.setError ( true );
    }

    performCellsChanged ();
  }


  /**
   * Initializes the {@link MachinePanel}.
   */
  private final void initialize ()
  {
    this.machine = this.model.getMachine ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.getSelectionModel ().setSelectionMode (
        GraphSelectionModel.SINGLE_GRAPH_SELECTION );
    this.graphModel = this.model.getGraphModel ();
    this.zoomFactor = ( ( double ) PreferenceManager.getInstance ()
        .getZoomFactorItem ().getFactor () ) / 100;

    if ( activeMouseAdapter == null )
    {
      activeMouseAdapter = ActiveMouseAdapter.MOUSE;
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

    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );

    this.errorTableModel = new MachineConsoleTableModel ();
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
    this.warningTableModel = new MachineConsoleTableModel ();
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

    this.gui.wordPanel.setVisible ( false );
    this.gui.wordPanel.setAlphabet ( this.machine.getAlphabet () );
    this.gui.wordPanel.setPushDownAlphabet ( this.machine
        .getPushDownAlphabet () );
  }


  /**
   * Initializes the {@link Machine} table.
   */
  public final void initializeMachineTable ()
  {
    this.gui.jGTITableMachine.setModel ( this.machine );
    this.gui.jGTITableMachine.setColumnModel ( this.machine
        .getTableColumnModel () );
    this.gui.jGTITableMachine.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableMachine
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );

    for ( int i = 1 ; i < this.gui.jGTITableMachine.getColumnModel ()
        .getColumnCount () ; i++ )
    {
      TableColumn current = this.gui.jGTITableMachine.getColumnModel ()
          .getColumn ( i );

      StyledStateSetParserPanel parserPanel = new StyledStateSetParserPanel ();
      parserPanel.setStateList ( this.machine.getState () );
      final ParserTableCellEditor < StateSet > cellEditor = new ParserTableCellEditor < StateSet > (
          this, parserPanel );

      cellEditor.addCellEditorListener ( new CellEditorListener ()
      {

        @SuppressWarnings ( "synthetic-access" )
        public void editingCanceled (
            @SuppressWarnings ( "unused" ) ChangeEvent event )
        {
          MachinePanel.this.cellEditingMode = false;
          clearHighlight ();
          MachinePanel.this.jGTIGraph.clearSelection ();
        }


        @SuppressWarnings ( "synthetic-access" )
        public void editingStopped (
            @SuppressWarnings ( "unused" ) ChangeEvent event )
        {
          MachinePanel.this.cellEditingMode = false;
          clearHighlight ();
          MachinePanel.this.jGTIGraph.clearSelection ();
        }
      } );

      current.setCellEditor ( cellEditor );

      parserPanel.addFocusListener ( new FocusAdapter ()
      {

        @Override
        public void focusLost ( @SuppressWarnings ( "unused" ) FocusEvent event )
        {
          cellEditor.cancelCellEditing ();
        }
      } );

      parserPanel.addKeyListener ( new KeyAdapter ()
      {

        @Override
        public void keyPressed ( KeyEvent event )
        {
          if ( event.getKeyCode () == KeyEvent.VK_ENTER )
          {
            cellEditor.stopCellEditing ();
            event.consume ();
          }
        }
      } );
    }
  }


  /**
   * Initializes the {@link PDA} table.
   */
  private final void initializePDATable ()
  {
    this.gui.jGTITableMachinePDA.setModel ( this.model.getPDATableModel () );
    this.gui.jGTITableMachinePDA.setColumnModel ( this.pdaTableColumnModel );
    this.gui.jGTITableMachinePDA.getTableHeader ()
        .setReorderingAllowed ( false );
    this.gui.jGTITableMachinePDA
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );

    if ( !this.machine.getMachineType ().equals ( MachineType.PDA ) )
    {
      if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.SHOW ) )
      {
        setVisiblePDATable ( true );
      }
      else if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.HIDE ) )
      {
        setVisiblePDATable ( false );
      }
      else
      {
        throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$
      }

      PreferenceManager.getInstance ().addPDAModeChangedListener (
          new PDAModeChangedListener ()
          {

            @SuppressWarnings ( "synthetic-access" )
            public void pdaModeChanged ( PDAModeItem newValue )
            {
              if ( newValue.equals ( PDAModeItem.SHOW ) )
              {
                setVisiblePDATable ( true );

                MachinePanel.this.gui.wordPanel.jGTILabelStack
                    .setEnabled ( true );
                MachinePanel.this.gui.wordPanel.styledStackParserPanel
                    .setEnabled ( true );
                MachinePanel.this.gui.wordPanel.jGTILabelPushDownAlphabet
                    .setEnabled ( true );
                MachinePanel.this.gui.wordPanel.styledAlphabetParserPanelPushDown
                    .setEnabled ( true );
              }
              else if ( newValue.equals ( PDAModeItem.HIDE ) )
              {
                setVisiblePDATable ( false );

                MachinePanel.this.gui.wordPanel.jGTILabelStack
                    .setEnabled ( false );
                MachinePanel.this.gui.wordPanel.styledStackParserPanel
                    .setEnabled ( false );
                MachinePanel.this.gui.wordPanel.jGTILabelPushDownAlphabet
                    .setEnabled ( false );
                MachinePanel.this.gui.wordPanel.styledAlphabetParserPanelPushDown
                    .setEnabled ( false );
              }
              else
              {
                throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$
              }
            }
          } );
    }
  }


  /**
   * Initializes the second view .
   */
  private final void initializeSecondView ()
  {
    MouseListener mouseListener = new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        MachinePanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    MouseMotionListener mouseMotionListener = new MouseMotionAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseDragged ( MouseEvent event )
      {
        MachinePanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    this.jGTIGraph.addMouseListener ( mouseListener );
    this.jGTIGraph.addMouseMotionListener ( mouseMotionListener );
    this.gui.jGTIScrollPaneGraph.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneGraph.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITableMachine.addMouseListener ( mouseListener );
    this.gui.jGTITableMachine.getTableHeader ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneMachine.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneMachine.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITableMachinePDA.addMouseListener ( mouseListener );
    this.gui.jGTITableMachinePDA.getTableHeader ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneMachinePDA.getHorizontalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.jGTIScrollPaneMachinePDA.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITabbedPaneConsole.addMouseListener ( mouseListener );

    this.gui.jGTITableErrors.addMouseListener ( mouseListener );
    this.gui.jGTITableErrors.getTableHeader ()
        .addMouseListener ( mouseListener );
    this.gui.jGTIScrollPaneErrors.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneErrors.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITableWarnings.addMouseListener ( mouseListener );
    this.gui.jGTITableWarnings.getTableHeader ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneWarnings.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneWarnings.getVerticalScrollBar ().addMouseListener (
        mouseListener );
  }


  /**
   * Initializes the mouse adapter of the toolbar.
   */
  private final void intitializeMouseAdapter ()
  {
    this.normalMouse = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse has been clicked on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        // open configuration
        if ( event.getButton () == MouseEvent.BUTTON1 )
        {
          updateSelected ( event );

          if ( event.getClickCount () >= 2 )
          {
            DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
                .getFirstCellForLocation ( event.getPoint ().getX (), event
                    .getPoint ().getY () );
            if ( cell == null )
            {
              return;
            }
            else if ( cell instanceof DefaultTransitionView )
            {
              DefaultTransitionView transitionView = ( DefaultTransitionView ) cell;

              // open transition config dialog
              Transition usedTransition = transitionView.getTransition ();
              TransitionDialog transitionDialog = new TransitionDialog (
                  MachinePanel.this.mainWindowForm, MachinePanel.this,
                  MachinePanel.this.machine.getAlphabet (),
                  MachinePanel.this.machine.getPushDownAlphabet (),
                  usedTransition );
              transitionDialog.show ();

              performCellsChanged ();
            }
            else if ( cell instanceof DefaultStateView )
            {
              DefaultStateView stateView = ( DefaultStateView ) cell;

              // open state config dialog
              StateConfigDialog dialog = new StateConfigDialog (
                  MachinePanel.this.mainWindowForm, MachinePanel.this,
                  stateView.getState (), MachinePanel.this.model );
              dialog.show ();

              // Update the machine table status
              updateMachineTableStatus ();
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
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );
        if ( object == null )
        {
          MachinePanel.this.popup = createPopupMenu ();
        }
        else if ( object instanceof DefaultTransitionView )
        {
          MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
        }
        else
        {
          MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );
        }

        if ( MachinePanel.this.popup != null )
        {
          MachinePanel.this.popup.show ( ( Component ) event.getSource (),
              event.getX (), event.getY () );
        }
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
        if ( event.getButton () == MouseEvent.BUTTON2 )
        {
          return;
        }
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        if ( event.getButton () == MouseEvent.BUTTON1 )
        {
          updateSelected ( event );
        }

        // if an popup menu is open close it and do nothing more
        if ( ( event.getButton () == MouseEvent.BUTTON1 )
            && ( MachinePanel.this.popup != null ) )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          if ( object == null )
          {
            MachinePanel.this.popup = createPopupMenu ();
          }
          else if ( object instanceof DefaultTransitionView )
          {
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          }
          else
          {
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );
          }

          if ( MachinePanel.this.popup != null )
          {
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          }
          return;
        }

        if ( object != null )
        {
          return;
        }

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), false, false );

          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState, true );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
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
            // Return to the normal mouse after every click.
            MachinePanel.this.mainWindowForm.getLogic ().addButtonState (
                ButtonState.SELECTED_MOUSE );
            break;
          }
        }
      }
    };

    this.addTransition = new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        if ( event.getButton () == MouseEvent.BUTTON2 )
        {
          return;
        }
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        if ( event.getButton () == MouseEvent.BUTTON1 )
        {
          updateSelected ( event );
        }

        // if an popup menu is open close it and do nothing more
        if ( ( event.getButton () == MouseEvent.BUTTON1 )
            && ( MachinePanel.this.popup != null ) )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if left button was pressed
        if ( ( event.getButton () == MouseEvent.BUTTON3 )
            && ( MachinePanel.this.firstState == null ) )
        {
          DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
              .getFirstCellForLocation ( event.getPoint ().getX (), event
                  .getPoint ().getY () );
          if ( object == null )
          {
            MachinePanel.this.popup = createPopupMenu ();
          }
          else if ( object instanceof DefaultTransitionView )
          {
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          }
          else
          {
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );
          }

          if ( MachinePanel.this.popup != null )
          {
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          }
          return;
        }

        TransitionItem transitionItem = PreferenceManager.getInstance ()
            .getTransitionItem ();

        // if drag in progress return
        if ( MachinePanel.this.dragged
            || transitionItem.equals ( TransitionItem.DRAG_MODE ) )
        {
          return;
        }

        if ( MachinePanel.this.firstState == null )
        {
          MachinePanel.this.firstState = ( DefaultStateView ) MachinePanel.this.jGTIGraph
              .getSelectionCellAt ( event.getPoint () );
          if ( MachinePanel.this.firstState == null )
          {
            return;
          }
        }
        else
        {
          DefaultStateView target = null;
          try
          {
            target = ( DefaultStateView ) MachinePanel.this.jGTIGraph
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
          TransitionDialog transitionDialog = new TransitionDialog (
              MachinePanel.this.mainWindowForm, MachinePanel.this,
              MachinePanel.this.model,
              MachinePanel.this.machine.getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (),
              MachinePanel.this.firstState.getState (), target == null ? null
                  : target.getState (), new DefaultWord (), new DefaultWord (),
              new TreeSet < Symbol > (), event.getPoint ().x,
              event.getPoint ().y, MachinePanel.this.zoomFactor );
          transitionDialog.show ();

          performCellsChanged ();

          switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
          {
            case WITHOUT_RETURN_TO_MOUSE :
            {
              // Do nothing
              break;
            }
            case WITH_RETURN_TO_MOUSE :
            {
              // Return to the normal mouse after every click.
              MachinePanel.this.mainWindowForm.getLogic ().addButtonState (
                  ButtonState.SELECTED_MOUSE );
              break;
            }
          }
          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.getButton () != MouseEvent.BUTTON1 )
        {
          return;
        }

        if ( !MachinePanel.this.dragged
            || ( MachinePanel.this.firstState == null ) )
        {
          return;
        }

        DefaultStateView target = null;

        try
        {
          target = ( DefaultStateView ) MachinePanel.this.jGTIGraph
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

        TransitionDialog transitionDialog = new TransitionDialog (
            MachinePanel.this.mainWindowForm, MachinePanel.this,
            MachinePanel.this.model, MachinePanel.this.machine.getAlphabet (),
            MachinePanel.this.machine.getPushDownAlphabet (),
            MachinePanel.this.firstState.getState (), target == null ? null
                : target.getState (), new DefaultWord (), new DefaultWord (),
            new TreeSet < Symbol > (), event.getPoint ().x,
            event.getPoint ().y, MachinePanel.this.zoomFactor );
        transitionDialog.show ();

        performCellsChanged ();

        switch ( PreferenceManager.getInstance ().getMouseSelectionItem () )
        {
          case WITHOUT_RETURN_TO_MOUSE :
          {
            // Do nothing
            break;
          }
          case WITH_RETURN_TO_MOUSE :
          {
            // Return to the normal mouse after every click.
            MachinePanel.this.mainWindowForm.getLogic ().addButtonState (
                ButtonState.SELECTED_MOUSE );
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

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseDragged ( MouseEvent event )
      {
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }
        if ( PreferenceManager.getInstance ().getTransitionItem ().equals (
            TransitionItem.CLICK_MODE ) )
        {
          return;
        }

        double x, y;
        if ( MachinePanel.this.firstState == null )
        {
          Object cell = MachinePanel.this.jGTIGraph.getFirstCellForLocation (
              event.getPoint ().getX (), event.getPoint ().getY () );
          if ( cell instanceof DefaultStateView )
          {
            MachinePanel.this.dragged = true;
            MachinePanel.this.firstState = ( DefaultStateView ) cell;
          }
        }

        else
        {
          // Remove old tmp state and transition
          MachinePanel.this.graphModel.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          x = event.getX () / MachinePanel.this.zoomFactor;
          y = event.getY () / MachinePanel.this.zoomFactor;
          MachinePanel.this.tmpState = createTmpObject ( x, y );
          MachinePanel.this.jGTIGraph.getGraphLayoutCache ().insert (
              MachinePanel.this.tmpState );

          MachinePanel.this.tmpTransition = new DefaultEdge ( "" ); //$NON-NLS-1$
          GraphConstants.setLineEnd ( MachinePanel.this.tmpTransition
              .getAttributes (), GraphConstants.ARROW_CLASSIC );
          GraphConstants.setEndFill ( MachinePanel.this.tmpTransition
              .getAttributes (), true );

          MachinePanel.this.jGTIGraph.getGraphLayoutCache ().insertEdge (
              MachinePanel.this.tmpTransition,
              MachinePanel.this.firstState.getChildAt ( 0 ),
              MachinePanel.this.tmpState.getChildAt ( 0 ) );
        }
      }


      /**
       * Invoked when the mouse button has been moved on a component (with no
       * buttons no down).
       */
      @SuppressWarnings ( "synthetic-access" )
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
          MachinePanel.this.jGTIGraph.getGraphLayoutCache ().insert (
              MachinePanel.this.tmpState );

          MachinePanel.this.tmpTransition = new DefaultEdge ( "" ); //$NON-NLS-1$
          GraphConstants.setLineEnd ( MachinePanel.this.tmpTransition
              .getAttributes (), GraphConstants.ARROW_CLASSIC );
          GraphConstants.setEndFill ( MachinePanel.this.tmpTransition
              .getAttributes (), true );

          MachinePanel.this.jGTIGraph.getGraphLayoutCache ().insertEdge (
              MachinePanel.this.tmpTransition,
              MachinePanel.this.firstState.getChildAt ( 0 ),
              MachinePanel.this.tmpState.getChildAt ( 0 ) );
        }
      }
    };

    this.addStartState = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        if ( event.getButton () == MouseEvent.BUTTON2 )
        {
          return;
        }
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        if ( event.getButton () == MouseEvent.BUTTON1 )
        {
          updateSelected ( event );
        }

        // if an popup menu is open close it and do nothing more
        if ( ( event.getButton () == MouseEvent.BUTTON1 )
            && ( MachinePanel.this.popup != null ) )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          if ( object == null )
          {
            MachinePanel.this.popup = createPopupMenu ();
          }
          else if ( object instanceof DefaultTransitionView )
          {
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          }
          else
          {
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );
          }

          if ( MachinePanel.this.popup != null )
          {
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          }
          return;
        }

        if ( object != null )
        {
          return;
        }

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), true, false );
          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState, true );
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
            // Return to the normal mouse after every click.
            MachinePanel.this.mainWindowForm.getLogic ().addButtonState (
                ButtonState.SELECTED_MOUSE );
            break;
          }
        }

      }
    };

    this.addEndState = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        if ( event.getButton () == MouseEvent.BUTTON2 )
        {
          return;
        }
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        if ( event.getButton () == MouseEvent.BUTTON1 )
        {
          updateSelected ( event );
        }

        // if an popup menu is open close it and do nothing more
        if ( ( event.getButton () == MouseEvent.BUTTON1 )
            && ( MachinePanel.this.popup != null ) )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // check if there is another stateview under this point
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
            .getFirstCellForLocation ( event.getPoint ().getX (), event
                .getPoint ().getY () );

        // Open popup menu if left button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          if ( object == null )
          {
            MachinePanel.this.popup = createPopupMenu ();
          }
          else if ( object instanceof DefaultTransitionView )
          {
            MachinePanel.this.popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
          }
          else
          {
            MachinePanel.this.popup = createStatePopupMenu ( ( DefaultStateView ) object );
          }

          if ( MachinePanel.this.popup != null )
          {
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          }
          return;
        }

        if ( object != null )
        {
          return;
        }

        try
        {
          State newState = new DefaultState ( MachinePanel.this.machine
              .getAlphabet (),
              MachinePanel.this.machine.getPushDownAlphabet (), false, true );
          MachinePanel.this.model.createStateView ( event.getPoint ().x
              / MachinePanel.this.zoomFactor, event.getPoint ().y
              / MachinePanel.this.zoomFactor, newState, true );
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
            // Return to the normal mouse after every click.
            MachinePanel.this.mainWindowForm.getLogic ().addButtonState (
                ButtonState.SELECTED_MOUSE );
            break;
          }
        }
      }
    };

    this.enterWordModeMouse = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent event )
      {
        if ( event.getButton () != MouseEvent.BUTTON3 )
        {
          return;
        }
        if ( !MachinePanel.this.machineMode.equals ( MachineMode.EDIT_MACHINE ) )
        {
          return;
        }

        // if an popup menu is open close it and do nothing more
        if ( ( event.getButton () == MouseEvent.BUTTON1 )
            && ( MachinePanel.this.popup != null ) )
        {
          MachinePanel.this.popup = null;
          return;
        }

        // Open popup menu if right button was pressed
        if ( event.getButton () == MouseEvent.BUTTON3 )
        {
          MachinePanel.this.popup = createEnterWordModePopupMenu ();

          if ( MachinePanel.this.popup != null )
          {
            MachinePanel.this.popup.show ( ( Component ) event.getSource (),
                event.getX (), event.getY () );
          }
          return;
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
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public boolean isRedoAble ()
  {
    return this.redoUndoHandler.isRedoAble ();
  }


  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public boolean isUndoAble ()
  {
    return this.redoUndoHandler.isUndoAble ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "MachinePanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "MachinePanel.Warning" ) ); //$NON-NLS-1$
  }


  /**
   * Performs the cells changed on the {@link GraphModel}.
   */
  public final void performCellsChanged ()
  {
    this.graphModel
        .cellsChanged ( DefaultGraphModel.getAll ( this.graphModel ) );
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
      if ( this.gui.jGTISplitPaneConsole.getRightComponent () == null )
      {
        this.setDividerLocationConsole = false;
        this.gui.jGTISplitPaneConsole
            .setRightComponent ( this.gui.jGTITabbedPaneConsole );
        this.gui.jGTISplitPaneConsole.setDividerSize ( 3 );
        this.gui.jGTISplitPaneConsole.setDividerLocation ( PreferenceManager
            .getInstance ().getDividerLocationConsole () );
      }
    }
    else
    {
      if ( this.gui.jGTISplitPaneConsole.getRightComponent () != null )
      {
        this.setDividerLocationConsole = false;
        this.gui.jGTISplitPaneConsole.setRightComponent ( null );
        this.gui.jGTISplitPaneConsole.setDividerSize ( 0 );
      }
    }
  }


  /**
   * Sets the visibility of the {@link PDA} table.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisiblePDATable ( boolean visible )
  {
    if ( visible )
    {
      if ( this.gui.jGTISplitPanePDATable.getRightComponent () == null )
      {
        this.setDividerLocationPDATable = false;
        this.gui.jGTISplitPanePDATable
            .setRightComponent ( this.gui.jGTIScrollPaneMachinePDA );
        this.gui.jGTISplitPanePDATable.setDividerSize ( 3 );
        this.gui.jGTISplitPanePDATable.setDividerLocation ( PreferenceManager
            .getInstance ().getDividerLocationPDATable () );
      }
    }
    else
    {
      if ( this.gui.jGTISplitPanePDATable.getRightComponent () != null )
      {
        this.setDividerLocationPDATable = false;
        this.gui.jGTISplitPanePDATable.setRightComponent ( null );
        this.gui.jGTISplitPanePDATable.setDividerSize ( 0 );
      }
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
      if ( this.gui.jGTISplitPaneTable.getRightComponent () == null )
      {
        this.setDividerLocationTable = false;
        this.gui.jGTISplitPaneTable
            .setRightComponent ( this.gui.jGTISplitPanePDATable );
        this.gui.jGTISplitPaneTable.setDividerSize ( 3 );
        this.gui.jGTISplitPaneTable.setDividerLocation ( PreferenceManager
            .getInstance ().getDividerLocationTable () );
      }
    }
    else
    {
      if ( this.gui.jGTISplitPaneTable.getRightComponent () != null )
      {
        this.setDividerLocationTable = false;
        this.gui.jGTISplitPaneTable.setRightComponent ( null );
        this.gui.jGTISplitPaneTable.setDividerSize ( 0 );
      }
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
    this.jGTIGraph.setScale ( factor );
  }


  /**
   * Starts the auto step {@link Timer}.
   * 
   * @param delayed Flag that indicates that the timer should start after a
   *          delay.
   */
  @SuppressWarnings ( "synthetic-access" )
  private final void startAutoStepTimer ( boolean delayed )
  {
    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), delayed ? time : 0,
        time );
  }


  /**
   * Updates the {@link AcceptedStatus}.
   */
  private final void updateAcceptedState ()
  {
    if ( this.machineMode.equals ( MachineMode.WORD_NAVIGATION ) )
    {
      if ( this.machine.isWordAccepted () )
      {
        this.gui.wordPanel.styledWordParserPanel
            .setAcceptedStatus ( AcceptedStatus.ACCEPTED );
      }
      else
      {
        this.gui.wordPanel.styledWordParserPanel
            .setAcceptedStatus ( AcceptedStatus.NOT_ACCEPTED );
      }
    }
    else
    {
      this.gui.wordPanel.styledWordParserPanel
          .setAcceptedStatus ( AcceptedStatus.NONE );
    }
  }


  /**
   * Updates the {@link Machine} table status.
   */
  public final void updateMachineTableStatus ()
  {
    // disable if the names are not unique or the special pda with more than one
    // loop transition is used
    if ( this.machine.isEveryStateUnique () )
    {
      int number;
      boolean found = false;
      for ( State current : this.machine.getState () )
      {
        number = 0;
        for ( Transition currentTransition : current.getTransitionBegin () )
        {
          if ( currentTransition.getStateEnd () == current )
          {
            number++ ;
          }
        }
        if ( number >= 2 )
        {
          found = true;
          break;
        }
      }

      this.gui.jGTITableMachine.setEnabled ( !found );
    }
    else
    {
      this.gui.jGTITableMachine.setEnabled ( false );
    }
  }


  /**
   * Updates the selected {@link Transition}s and {@link State}s or clears the
   * selected.
   */
  private final void updateSelected ()
  {
    Object cell = this.jGTIGraph.getSelectionCell ();

    if ( cell == null )
    {
      this.machine.clearSelectedTransition ();
    }
    else if ( cell instanceof DefaultStateView )
    {
      DefaultStateView stateView = ( DefaultStateView ) cell;
      State state = stateView.getState ();
      this.machine.setSelectedState ( state );
    }
    else if ( cell instanceof DefaultTransitionView )
    {
      DefaultTransitionView transitionView = ( DefaultTransitionView ) cell;
      Transition transition = transitionView.getTransition ();
      ArrayList < Transition > transitionList = new ArrayList < Transition > ();
      transitionList.add ( transition );
      this.machine.setSelectedTransition ( transitionList );
    }

    this.gui.jGTITableMachine.repaint ();
    this.gui.jGTITableMachinePDA.repaint ();
  }


  /**
   * Updates the selected {@link Transition}s and {@link State}s.
   * 
   * @param event The {@link MouseEvent}.
   */
  private final void updateSelected ( MouseEvent event )
  {
    DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
        .getFirstCellForLocation ( event.getPoint ().getX (), event.getPoint ()
            .getY () );

    if ( cell == null )
    {
      MachinePanel.this.machine.clearSelectedTransition ();

      MachinePanel.this.gui.jGTITableMachine.repaint ();
      MachinePanel.this.gui.jGTITableMachinePDA.repaint ();
    }
    else if ( cell instanceof DefaultTransitionView )
    {
      DefaultTransitionView transitionView = ( DefaultTransitionView ) cell;
      ArrayList < Transition > transitionList = new ArrayList < Transition > ();

      transitionList.add ( transitionView.getTransition () );

      DefaultGraphCell nextCell = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
          .getNextCellForLocation ( cell, event.getPoint ().getX (), event
              .getPoint ().getY () );

      while ( nextCell != cell )
      {
        if ( nextCell instanceof DefaultTransitionView )
        {
          transitionList.add ( ( ( DefaultTransitionView ) nextCell )
              .getTransition () );
        }
        nextCell = ( DefaultGraphCell ) MachinePanel.this.jGTIGraph
            .getNextCellForLocation ( nextCell, event.getPoint ().getX (),
                event.getPoint ().getY () );
      }

      MachinePanel.this.machine.setSelectedTransition ( transitionList );

      MachinePanel.this.gui.jGTITableMachine.repaint ();
      MachinePanel.this.gui.jGTITableMachinePDA.repaint ();
    }
    else if ( cell instanceof DefaultStateView )
    {
      DefaultStateView stateView = ( DefaultStateView ) cell;
      MachinePanel.this.machine.setSelectedState ( stateView.getState () );

      MachinePanel.this.gui.jGTITableMachine.repaint ();
      MachinePanel.this.gui.jGTITableMachinePDA.repaint ();
    }
  }
}
