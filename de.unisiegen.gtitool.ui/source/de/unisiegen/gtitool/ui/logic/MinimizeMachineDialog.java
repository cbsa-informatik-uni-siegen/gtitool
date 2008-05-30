package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.MinimizeMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.utils.LayoutManager;
import de.unisiegen.gtitool.ui.utils.Minimizer;


/**
 * The {@link MinimizeMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id: ConvertMachineDialog.java 919 2008-05-19 23:49:26Z fehler $
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
   * The {@link MinimizeMachineDialogForm}.
   */
  private MinimizeMachineDialogForm gui;


  /**
   * The original {@link JGraph} containing the diagramm.
   */
  private JGraph jGraphOriginal;


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
   * The {@link Minimizer}.
   */
  private Minimizer minimizer;


  /**
   * The new created {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * The new created states.
   */
  private HashMap < State, DefaultStateView > states = new HashMap < State, DefaultStateView > ();


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

    try
    {
      this.modelOriginal = new DefaultMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
      this.minimizer = new Minimizer ( this.modelOriginal );
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
    catch ( SymbolException exc )
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
    this.jGraphOriginal = this.modelOriginal.getJGraph ();
    this.jGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGraphOriginal );
    this.machine = this.modelOriginal.getMachine ();

    handleStart ();
  }


  /**
   * build the minimal {@link Machine}.
   */
  private void buildMinimalMachine ()
  {
    this.states.clear ();
    this.model = new DefaultMachineModel ( new DefaultDFA ( this.machine
        .getAlphabet (), this.machine.getPushDownAlphabet (), this.machine
        .isUsePushDownAlphabet () ) );

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
          count++;
        }
        name += "}"; //$NON-NLS-1$

        DefaultState state = new DefaultState ( name );
        state.setStartState ( startState );
        state.setFinalState ( current.get ( 0 ).getState ().isFinalState () );
        DefaultStateView stateView = this.model.createStateView ( 100, 100,
            state, false );

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
            this.model.createTransitionView ( newTransition, this.states
                .get ( current ), target, false, false, true );
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
    // this.beginReached = true;
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

    this.machinePanel.getMainWindow ().handleNew ( this.model );

    new LayoutManager ( this.model, new RedoUndoHandler ( this.mainWindowForm ) )
        .doLayout ();

    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public final void handlePreviousStep ()
  {
    logger.debug ( "handlePreviousStep", "handle previous step" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.minimizer.previousStep ();
    this.beginReached = this.minimizer.isBegin ();
    this.endReached = false;
    setStatus ();
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
   * Minimize the given {@link Machine}.
   */
  public final void minimize ()
  {
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
    int x = this.mainWindowForm.getBounds ().x
        + ( this.mainWindowForm.getWidth () / 2 ) - ( this.gui.getWidth () / 2 );
    int y = this.mainWindowForm.getBounds ().y
        + ( this.mainWindowForm.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
