package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * TODO
 */
public class ConvertRegexToMachineDialog implements
    LogicClass < ConvertRegexToMachineDialogForm >, Converter
{

  /**
   * Does the next step after a delay.
   * 
   * @author Christian Fehler
   */
  private final class AutoStepTimerTask extends TimerTask
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
          if ( ConvertRegexToMachineDialog.this.endReached )
          {
            handleStop ();
          }
          else
          {
            performNextStep ( true );
          }
        }
      } );
    }
  }


  /**
   * The {@link Position}.
   * 
   * @author Christian Fehler
   */
  private class Position
  {

    /**
     * The x position.
     */
    private double x;


    /**
     * The y position.
     */
    private double y;


    /**
     * Allocates a new {@link Position}.
     * 
     * @param x The x position.
     * @param y The y position.
     */
    public Position ( double x, double y )
    {
      this.x = x;
      this.y = y;
    }


    /**
     * Returns the x position.
     * 
     * @return The x position.
     * @see #x
     */
    public final double getX ()
    {
      return this.x;
    }


    /**
     * Returns the y position.
     * 
     * @return The y position.
     * @see #y
     */
    public final double getY ()
    {
      return this.y;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Object#toString()
     */
    @Override
    public final String toString ()
    {
      return "x: " + this.x + ", y: " + this.y; //$NON-NLS-1$//$NON-NLS-2$
    }
  }


  /**
   * The {@link Step} enum.
   * 
   * @author Christian Fehler
   */
  private enum Step
  {
    /**
     * The activate new closure {@link State}s step.
     */
    ACTIVATE_NEW_CLOSURE_STATES,

    /**
     * The activate new {@link State}s step.
     */
    ACTIVATE_NEW_STATES,

    /**
     * The activate old closure {@link State} step.
     */
    ACTIVATE_OLD_CLOSURE_STATE,

    /**
     * The activate old {@link State} step.
     */
    ACTIVATE_OLD_STATE,

    /**
     * The activate start closure {@link State} step.
     */
    ACTIVATE_START_CLOSURE_STATE,

    /**
     * The activate start {@link State} step.
     */
    ACTIVATE_START_STATE,

    /**
     * The activate {@link Symbol}s step.
     */
    ACTIVATE_SYMBOLS,

    /**
     * The add start {@link State} step.
     */
    ADD_START_STATE,

    /**
     * The add {@link State} and {@link Transition} step.
     */
    ADD_STATE_AND_TRANSITION,

    /**
     * The finish step.
     */
    FINISH;

    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case ACTIVATE_START_STATE :
        {
          return "activate start state"; //$NON-NLS-1$
        }
        case ACTIVATE_START_CLOSURE_STATE :
        {
          return "activate start closure state"; //$NON-NLS-1$
        }
        case ADD_START_STATE :
        {
          return "add start state"; //$NON-NLS-1$
        }
        case ACTIVATE_OLD_STATE :
        {
          return "activate old state"; //$NON-NLS-1$
        }
        case ACTIVATE_OLD_CLOSURE_STATE :
        {
          return "activate old closure state"; //$NON-NLS-1$
        }
        case ACTIVATE_SYMBOLS :
        {
          return "activate symbols"; //$NON-NLS-1$
        }
        case ACTIVATE_NEW_STATES :
        {
          return "activate new states"; //$NON-NLS-1$
        }
        case ACTIVATE_NEW_CLOSURE_STATES :
        {
          return "activate new closure states"; //$NON-NLS-1$
        }
        case ADD_STATE_AND_TRANSITION :
        {
          return "add state and transition"; //$NON-NLS-1$
        }
        case FINISH :
        {
          return "finish"; //$NON-NLS-1$
        }
      }
      throw new RuntimeException ( "unsupported step" );//$NON-NLS-1$
    }
  }


  /**
   * The {@link StepItem}.
   * 
   * @author Christian Fehler
   */
  private class StepItem
  {

    /**
     * The current active {@link State}.
     */
    private State activeState;


    /**
     * The active {@link State}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesConverted;


    /**
     * The active {@link State}s of the original {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesOriginal;


    /**
     * The active {@link Step}.
     */
    private Step activeStep;


    /**
     * The current active {@link Symbol}.
     */
    private Symbol activeSymbol;


    /**
     * The active {@link Symbol}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsConverted;


    /**
     * The active {@link Symbol}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Transition}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsConverted;


    /**
     * The active {@link Transition}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The added {@link DefaultStateView}.
     */
    private DefaultStateView addedDefaultStateView = null;


    /**
     * The added {@link DefaultTransitionView}.
     */
    private DefaultTransitionView addedDefaultTransitionView = null;


    /**
     * The added {@link Symbol}.
     */
    private ObjectPair < Transition, Symbol > addedSymbol = null;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param activeStep The active {@link Step}.
     * @param currentActiveSymbol The current active {@link Symbol}.
     * @param currentActiveState The current active {@link State}.
     * @param activeStatesOriginal The active {@link State}s of the original
     *          {@link JGTIGraph}.
     * @param activeStatesConverted The active {@link State}s of the converted
     *          {@link JGTIGraph}.
     * @param activeTransitionsOriginal The active {@link Transition}s of the
     *          original {@link JGTIGraph}.
     * @param activeTransitionsConverted The active {@link Transition}s of the
     *          converted {@link JGTIGraph}.
     * @param activeSymbolsOriginal The active {@link Symbol}s of the original
     *          {@link JGTIGraph}.
     * @param activeSymbolsConverted The active {@link Symbol}s of the
     *          converted {@link JGTIGraph}.
     */
    public StepItem ( Step activeStep, Symbol currentActiveSymbol,
        State currentActiveState, ArrayList < State > activeStatesOriginal,
        ArrayList < State > activeStatesConverted,
        ArrayList < Transition > activeTransitionsOriginal,
        ArrayList < Transition > activeTransitionsConverted,
        ArrayList < Symbol > activeSymbolsOriginal,
        ArrayList < Symbol > activeSymbolsConverted )
    {
      if ( activeStep == null )
      {
        throw new IllegalArgumentException ( "active step is null" ); //$NON-NLS-1$
      }
      if ( currentActiveSymbol == null )
      {
        throw new IllegalArgumentException ( "active symbol is null" ); //$NON-NLS-1$
      }

      this.activeStep = activeStep;
      this.activeSymbol = currentActiveSymbol;
      this.activeState = currentActiveState;
      this.activeStatesOriginal = activeStatesOriginal;
      this.activeStatesConverted = activeStatesConverted;
      this.activeTransitionsOriginal = activeTransitionsOriginal;
      this.activeTransitionsConverted = activeTransitionsConverted;
      this.activeSymbolsOriginal = activeSymbolsOriginal;
      this.activeSymbolsConverted = activeSymbolsConverted;
    }


    /**
     * Returns the activeState.
     * 
     * @return The activeState.
     * @see #activeState
     */
    public final State getActiveState ()
    {
      return this.activeState;
    }


    /**
     * Returns the activeStatesConverted.
     * 
     * @return The activeStatesConverted.
     * @see #activeStatesConverted
     */
    public final ArrayList < State > getActiveStatesConverted ()
    {
      return this.activeStatesConverted;
    }


    /**
     * Returns the activeStatesOriginal.
     * 
     * @return The activeStatesOriginal.
     * @see #activeStatesOriginal
     */
    public final ArrayList < State > getActiveStatesOriginal ()
    {
      return this.activeStatesOriginal;
    }


    /**
     * Returns the activeStep.
     * 
     * @return The activeStep.
     * @see #activeStep
     */
    public final Step getActiveStep ()
    {
      return this.activeStep;
    }


    /**
     * Returns the activeSymbol.
     * 
     * @return The activeSymbol.
     * @see #activeSymbol
     */
    public final Symbol getActiveSymbol ()
    {
      return this.activeSymbol;
    }


    /**
     * Returns the activeSymbolsConverted.
     * 
     * @return The activeSymbolsConverted.
     * @see #activeSymbolsConverted
     */
    public final ArrayList < Symbol > getActiveSymbolsConverted ()
    {
      return this.activeSymbolsConverted;
    }


    /**
     * Returns the activeSymbolsOriginal.
     * 
     * @return The activeSymbolsOriginal.
     * @see #activeSymbolsOriginal
     */
    public final ArrayList < Symbol > getActiveSymbolsOriginal ()
    {
      return this.activeSymbolsOriginal;
    }


    /**
     * Returns the activeTransitionsConverted.
     * 
     * @return The activeTransitionsConverted.
     * @see #activeTransitionsConverted
     */
    public final ArrayList < Transition > getActiveTransitionsConverted ()
    {
      return this.activeTransitionsConverted;
    }


    /**
     * Returns the activeTransitionsOriginal.
     * 
     * @return The activeTransitionsOriginal.
     * @see #activeTransitionsOriginal
     */
    public final ArrayList < Transition > getActiveTransitionsOriginal ()
    {
      return this.activeTransitionsOriginal;
    }


    /**
     * Returns the addedDefaultStateView.
     * 
     * @return The addedDefaultStateView.
     * @see #addedDefaultStateView
     */
    public final DefaultStateView getAddedDefaultStateView ()
    {
      return this.addedDefaultStateView;
    }


    /**
     * Returns the addedDefaultTransitionView.
     * 
     * @return The addedDefaultTransitionView.
     * @see #addedDefaultTransitionView
     */
    public final DefaultTransitionView getAddedDefaultTransitionView ()
    {
      return this.addedDefaultTransitionView;
    }


    /**
     * Returns the addedSymbol.
     * 
     * @return The addedSymbol.
     * @see #addedSymbol
     */
    public final ObjectPair < Transition, Symbol > getAddedSymbol ()
    {
      return this.addedSymbol;
    }


    /**
     * Sets the addedDefaultStateView.
     * 
     * @param addedDefaultStateView The addedDefaultStateView to set.
     * @see #addedDefaultStateView
     */
    public final void setAddedDefaultStateView (
        DefaultStateView addedDefaultStateView )
    {
      this.addedDefaultStateView = addedDefaultStateView;
    }


    /**
     * Sets the addedDefaultTransitionView.
     * 
     * @param addedDefaultTransitionView The addedDefaultTransitionView to set.
     * @see #addedDefaultTransitionView
     */
    public final void setAddedDefaultTransitionView (
        DefaultTransitionView addedDefaultTransitionView )
    {
      this.addedDefaultTransitionView = addedDefaultTransitionView;
    }


    /**
     * Sets the addedSymbol.
     * 
     * @param addedSymbol The addedSymbol to set.
     * @see #addedSymbol
     */
    public final void setAddedSymbol (
        ObjectPair < Transition, Symbol > addedSymbol )
    {
      this.addedSymbol = addedSymbol;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertRegexToMachineDialog.class );


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  private ConvertRegexToMachineDialogForm gui;


  /**
   * The converted {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphConverted;


  /**
   * The converted {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultRegexModel modelOriginal;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  private RegexPanel panel;


  private JFrame parent;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * TODO
   */
  public ConvertRegexToMachineDialog ( JFrame parent, RegexPanel panel )
  {
    this.parent = parent;
    this.panel = panel;
  }


  /**
   * Cancels the auto step timer.
   */
  private final void cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
    }
  }


  /**
   * TODO
   * 
   * @param fromEntityType
   * @param toEntityType
   * @param complete
   * @see de.unisiegen.gtitool.ui.convert.Converter#convert(de.unisiegen.gtitool.core.entities.InputEntity.EntityType,
   *      de.unisiegen.gtitool.core.entities.InputEntity.EntityType, boolean)
   */
  public void convert ( EntityType fromEntityType, EntityType toEntityType,
      boolean complete )
  {
    this.gui = new ConvertRegexToMachineDialogForm ( this, this.parent );

    Alphabet a = this.panel.getRegex ().getAlphabet ();
    this.modelConverted = new DefaultMachineModel ( new DefaultNFA ( a, a,
        false ) );
    this.modelOriginal = new DefaultRegexModel ( this.panel.getRegex () );
    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();
    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );

  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ConvertRegexToMachineDialogForm getGUI ()
  {
    return this.gui;
  }


  public void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    setStatus ();

    startAutoStepTimer ();
  }


  public void handleBeginStep ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handleCancel ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handleEndStep ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handleNextStep ()
  {
    try
    {
      this.modelConverted = new DefaultMachineModel(this.panel.getRegex ().getRegexNode ().toNFA ( this.panel.getRegex ().getAlphabet () ));
    }
    catch ( StateException exc )
    {
      exc.printStackTrace();
    }
  }


  public void handleOk ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handlePreviousStep ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handlePrint ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  public void handleStop ()
  {
    throw new UnsupportedOperationException ( "Not yet implemented" );
  }


  private final void performNextStep ( boolean b )
  {

  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    if ( this.gui.jGTIToolBarToggleButtonAutoStep.isSelected () )
    {
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonStop.setEnabled ( true );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( false );
    }
    else
    {
      boolean beginReached = this.stepItemList.isEmpty ();
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarButtonStop.setEnabled ( false );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( !this.endReached );
    }
  }


  /**
   * Starts the auto step timer.
   */
  @SuppressWarnings ( "synthetic-access" )
  private final void startAutoStepTimer ()
  {
    cancelAutoStepTimer ();

    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), time, time );
  }
}
