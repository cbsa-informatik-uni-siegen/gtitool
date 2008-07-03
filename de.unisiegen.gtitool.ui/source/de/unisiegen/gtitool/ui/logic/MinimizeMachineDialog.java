package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.MinimizeMachineTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.MinimizeMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.utils.Minimizer;


/**
 * The {@link MinimizeMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MinimizeMachineDialog implements
    LogicClass < MinimizeMachineDialogForm >
{

  /**
   * Perform auto step.
   * 
   * @author Benjamin Mies
   */
  protected final class AutoStepTimerTask extends TimerTask
  {

    /**
     * {@inheritDoc}
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
          handleNextStep ();
          if ( MinimizeMachineDialog.this.endReached )
          {
            handleStop ();
          }

        }
      } );
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( MinimizeMachineDialog.class );


  /**
   * Flag indicates if autostep is in progress.
   */
  private boolean autoStep = false;


  /**
   * Flag that indicates if the begin is reached.
   */
  private boolean beginReached = true;


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  /**
   * Returns the endReached.
   * 
   * @return The endReached.
   * @see #endReached
   */
  public boolean isEndReached ()
  {
    return this.endReached;
  }


  /**
   * The {@link MinimizeMachineDialogForm}.
   */
  private MinimizeMachineDialogForm gui;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * The original {@link Machine}.
   */
  private Machine machine;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The parent {@link JFrame}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link MinimizeMachineTableModel}.
   */
  private MinimizeMachineTableModel minimizeMachineTableModel;


  /**
   * The {@link Minimizer}.
   */
  private Minimizer minimizer;


  /**
   * The new created {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelMinimized;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * Flag indicates if we want to handle the devider location event.
   */
  private boolean setDeviderLocation = true;


  /**
   * The new created states.
   */
  private HashMap < State, DefaultStateView > states = new HashMap < State, DefaultStateView > ();


  /**
   * The {@link ConvertMachineTableColumnModel}.
   */
  private ConvertMachineTableColumnModel tableColumnModel = new ConvertMachineTableColumnModel ();


  /**
   * The {@link Timer}.
   */
  private Timer timer = null;


  /**
   * Allocates a new {@link MinimizeMachineDialog}.
   * 
   * @param mainWindowForm The parent {@link MainWindowForm}.
   * @param machinePanel The {@link MachinePanel}.
   */
  public MinimizeMachineDialog ( MainWindowForm mainWindowForm,
      MachinePanel machinePanel )
  {
    logger.debug ( "MinimizeMachineDialog", //$NON-NLS-1$
        "allocate a new minimize machine dialog" ); //$NON-NLS-1$

    this.mainWindowForm = mainWindowForm;
    this.machinePanel = machinePanel;
    this.gui = new MinimizeMachineDialogForm ( this, mainWindowForm );

    this.setDeviderLocation = false;
    this.gui.jGTISplitPaneGraph.setRightComponent ( null );

    try
    {
      this.modelOriginal = new DefaultMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
      this.minimizeMachineTableModel = new MinimizeMachineTableModel ();
      this.minimizer = new Minimizer ( this.modelOriginal, this );
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StoreException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();
    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );
    this.machine = this.modelOriginal.getMachine ();

    handleStart ();
  }


  /**
   * Adds a outline comment.
   * 
   * @param prettyString The {@link PrettyString}.
   * @param transitions List with the {@link Transition}s.
   */
  public final void addOutlineComment ( PrettyString prettyString,
      ArrayList < Transition > transitions )
  {
    this.minimizeMachineTableModel.addRow ( prettyString, transitions );
    this.gui.jGTITableOutline.changeSelection ( this.minimizeMachineTableModel
        .getRowCount () - 1, MinimizeMachineTableModel.OUTLINE_COLUMN, false,
        false );
  }


  /**
   * Build the minimal {@link Machine}.
   */
  private void buildMinimalMachine ()
  {
    this.states.clear ();
    this.modelMinimized = new DefaultMachineModel ( new DefaultDFA (
        this.machine.getAlphabet (), this.machine.getPushDownAlphabet (),
        this.machine.isUsePushDownAlphabet () ) );

    try
    {
      for ( ArrayList < DefaultStateView > current : this.minimizer
          .getGroups () )
      {
        boolean startState = false;
        String name = "{"; //$NON-NLS-1$
        int count = 0;
        for ( DefaultStateView defaultStateView : current )
        {
          if ( defaultStateView.getState ().isStartState () )
          {
            startState = true;
          }
          if ( count > 0 )
          {
            name += ", "; //$NON-NLS-1$
          }
          name += defaultStateView.toString ();
          count++ ;
        }
        name += "}"; //$NON-NLS-1$

        DefaultState state = new DefaultState ( name );
        state.setStartState ( startState );
        state.setFinalState ( current.get ( 0 ).getState ().isFinalState () );
        DefaultStateView stateView = this.modelMinimized.createStateView (
            current.get ( 0 ).getPositionX (), current.get ( 0 )
                .getPositionY (), state, false );
        stateView.setGroupColor ( current.get ( 0 ).getGroupColor () );
        this.states.put ( current.get ( 0 ).getState (), stateView );
      }
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }

    createTransitions ();
  }


  /**
   * Create the transitions of the {@link Machine}.
   */
  private void createTransitions ()
  {
    for ( State current : this.states.keySet () )
    {
      HashMap < State, Transition > transitions = new HashMap < State, Transition > ();
      for ( Transition transition : current.getTransitionBegin () )
      {

        try
        {
          DefaultStateView target = getTargetStateView ( transition
              .getStateEnd () );
          Transition newTransition;
          if ( transitions.containsKey ( target.getState () ) )
          {
            transitions.get ( target.getState () ).add (
                transition.getSymbol () );
          }
          else
          {
            newTransition = new DefaultTransition ( transition.getAlphabet (),
                transition.getPushDownAlphabet (), transition
                    .getPushDownWordRead (),
                transition.getPushDownWordWrite (), this.states.get ( current )
                    .getState (), target.getState (), transition.getSymbol () );
            this.modelMinimized.createTransitionView ( newTransition,
                this.states.get ( current ), target, false, false, true );
          }

        }
        catch ( TransitionSymbolNotInAlphabetException exc )
        {
          exc.printStackTrace ();
        }
        catch ( TransitionSymbolOnlyOneTimeException exc )
        {
          exc.printStackTrace ();
        }

      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public MinimizeMachineDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the machinePanel.
   * 
   * @return The machinePanel.
   * @see #machinePanel
   */
  public MachinePanel getMachinePanel ()
  {
    return this.machinePanel;
  }


  /**
   * Returns the minimizeMachineTableModel.
   * 
   * @return The minimizeMachineTableModel.
   * @see #minimizeMachineTableModel
   */
  public MinimizeMachineTableModel getMinimizeMachineTableModel ()
  {
    return this.minimizeMachineTableModel;
  }


  /**
   * Returns the modelMinimized.
   * 
   * @return The modelMinimized.
   * @see #modelMinimized
   */
  public DefaultMachineModel getModelMinimized ()
  {
    return this.modelMinimized;
  }


  /**
   * Returns the modelOriginal.
   * 
   * @return The modelOriginal.
   * @see #modelOriginal
   */
  public DefaultMachineModel getModelOriginal ()
  {
    return this.modelOriginal;
  }


  /**
   * Returns the tableColumnModel.
   * 
   * @return The tableColumnModel.
   * @see #tableColumnModel
   */
  public ConvertMachineTableColumnModel getTableColumnModel ()
  {
    return this.tableColumnModel;
  }


  /**
   * Get the target state view representing the group.
   * 
   * @param state The {@link State}.
   * @return The {@link DefaultStateView} representing the group.
   */
  private DefaultStateView getTargetStateView ( State state )
  {
    DefaultStateView target = null;
    for ( ArrayList < DefaultStateView > current : this.minimizer.getGroups () )
    {
      target = this.states.get ( current.get ( 0 ).getState () );
      for ( DefaultStateView defaultStateView : current )
      {
        if ( state.equals ( defaultStateView.getState () ) )
        {
          return target;
        }
      }
    }
    return target;
  }


  /**
   * Handles the action on the auto step button.
   */
  public final void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.autoStep = true;

    setStatus ();

    this.timer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.timer.schedule ( new AutoStepTimerTask (), time, time );
  }


  /**
   * Handles the action on the begin step button.
   */
  public void handleBeginStep ()
  {
    logger.debug ( "handleBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( this.timer != null )
    {
      this.timer.cancel ();
      this.timer = null;
    }

    while ( !this.beginReached )
    {
      handlePreviousStep ();
    }
    this.endReached = false;
    setStatus ();
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {

    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    if ( this.timer != null )
    {
      this.timer.cancel ();
      this.timer = null;
    }

    PreferenceManager.getInstance ().setMinimizeMachineDialogPreferences (
        this.gui );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the end step button.
   */
  public void handleEndStep ()
  {
    logger.debug ( "handleEndStep", "handle end step" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( this.timer != null )
    {
      this.timer.cancel ();
      this.timer = null;
    }

    while ( !this.endReached )
    {
      handleNextStep ();
    }
    this.beginReached = false;
    this.endReached = true;
    setStatus ();
  }


  /**
   * Handles the action on the next step button.
   */
  public final void handleNextStep ()
  {
    logger.debug ( "handleNextStep", "handle next step" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.minimizer.nextStep ();
    this.endReached = this.minimizer.isFinished ();
    this.beginReached = false;
    setStatus ();

    if ( this.endReached )
    {
      buildMinimalMachine ();
      JGTIGraph graph = this.modelMinimized.getJGTIGraph ();
      graph.setEnabled ( false );
      this.gui.jGTIScrollPaneConverted.setViewportView ( graph );

    }
    highlightTransitions ( this.gui.jGTITableOutline.getSelectedRow () );

    if ( this.endReached )
    {
      this.setDeviderLocation = false;
      this.gui.jGTISplitPaneGraph
          .setRightComponent ( this.gui.jGTIScrollPaneConverted );
    }
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( this.timer != null )
    {
      this.timer.cancel ();
      this.timer = null;
    }

    this.gui.setVisible ( false );

    while ( !this.endReached )
    {
      handleNextStep ();
    }

    buildMinimalMachine ();

    this.machinePanel.getMainWindow ().handleNew ( this.modelMinimized );

    PreferenceManager.getInstance ().setMinimizeMachineDialogPreferences (
        this.gui );

    this.gui.dispose ();

  }


  /**
   * Handles the action on the previous step button.
   */
  public final void handlePreviousStep ()
  {
    logger.debug ( "handlePreviousStep", "handle previous step" ); //$NON-NLS-1$ //$NON-NLS-2$

    if ( this.endReached )
    {
      this.setDeviderLocation = false;
      this.gui.jGTISplitPaneGraph.setRightComponent ( null );
    }
    this.minimizer.previousStep ();
    this.beginReached = this.minimizer.isBegin ();
    this.endReached = false;
    setStatus ();
    this.gui.jGTIScrollPaneConverted.setViewportView ( null );

    this.minimizeMachineTableModel.removeLastRow ();
    int index = this.minimizeMachineTableModel.getRowCount () - 1;
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionInterval (
        index, index );
    highlightTransitions ( this.gui.jGTITableOutline.getSelectedRow () );
  }


  /**
   * Handles the start action.
   */
  private final void handleStart ()
  {
    this.minimizer.initialize ();
    setStatus ();
  }


  /**
   * Handles the action on the stop button.
   */
  public final void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.timer.cancel ();
    this.timer = null;

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    this.autoStep = false;
    setStatus ();
  }


  /**
   * Highlight the {@link Transition}s.
   * 
   * @param selectedRow The actual selected table row.
   */
  public void highlightTransitions ( int selectedRow )
  {
    for ( DefaultTransitionView current : this.modelOriginal
        .getTransitionViewList () )
    {
      current.getTransition ().setActive ( false );
    }
    if ( selectedRow > -1 )
    {
      ArrayList < Transition > transitionList = this.minimizeMachineTableModel
          .getTransitionsAt ( selectedRow );
      for ( Transition current : transitionList )
      {
        current.setActive ( true );
        this.modelOriginal.getGraphModel ().cellsChanged (
            DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
      }
    }
  }


  /**
   * Minimize the given {@link Machine}.
   */
  public final void minimize ()
  {
    this.gui.jGTITableOutline.setModel ( this.minimizeMachineTableModel );
    this.gui.jGTITableOutline.setColumnModel ( this.tableColumnModel );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    this.gui.jGTISplitPaneGraph.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationMinimizeMachine () );
    this.gui.jGTISplitPaneGraph.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            if ( MinimizeMachineDialog.this.setDeviderLocation )
            {
              PreferenceManager.getInstance ()
                  .setDividerLocationMinimizeMachine (
                      ( ( Integer ) event.getNewValue () ).intValue () );
            }
            else
            {
              MinimizeMachineDialog.this.setDeviderLocation = true;
            }
          }
        } );
    this.gui.jGTISplitPaneOutline.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationMinimizeMachineOutline () );
    this.gui.jGTISplitPaneOutline.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          public void propertyChange ( PropertyChangeEvent event )
          {
            PreferenceManager.getInstance ()
                .setDividerLocationMinimizeMachineOutline (
                    ( ( Integer ) event.getNewValue () ).intValue () );
          }
        } );

    show ();
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( !this.beginReached
        && !this.autoStep );
    this.gui.jGTIToolBarButtonBeginStep.setEnabled ( !this.beginReached
        && !this.autoStep );
    this.gui.jGTIToolBarButtonNextStep.setEnabled ( !this.endReached
        && !this.autoStep );
    this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( !this.endReached
        && !this.autoStep );
    this.gui.jGTIToolBarButtonEndStep.setEnabled ( !this.endReached
        && !this.autoStep );
    this.gui.jGTIToolBarButtonStop.setEnabled ( this.autoStep );
  }


  /**
   * Shows the {@link MinimizeMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the minimize machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    Rectangle rect = PreferenceManager.getInstance ()
        .getMinimizeMachineDialogBounds ();
    if ( ( rect.x == PreferenceManager.DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_Y ) )
    {
      rect.x = this.mainWindowForm.getBounds ().x
          + ( this.mainWindowForm.getWidth () / 2 )
          - ( this.gui.getWidth () / 2 );
      rect.y = this.mainWindowForm.getBounds ().y
          + ( this.mainWindowForm.getHeight () / 2 )
          - ( this.gui.getHeight () / 2 );
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
  }


  /**
   * Handle print action.
   */
  public void handlePrint ()
  {
    PrintDialog dialog = new PrintDialog ( this.mainWindowForm, this );
    dialog.show ();
  }
}
