package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
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
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


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
            try
            {
              performNextStep ( true );
            }
            catch ( StateException exc )
            {
              exc.printStackTrace ();
            }
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
     * The convert Token step
     */
    CONVERT_TOKEN,

    /**
     * The convert Epsilon step
     */
    CONVERT_EPSILON,

    /**
     * The convert concat step
     */
    CONVERT_CONCAT,

    /**
     * The convert disjunction step
     */
    CONVERT_DISJUNCTION,

    /**
     * The convert kleene step
     */
    CONVERT_KLEENE,

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
        case CONVERT_CONCAT :
          return "convert concatenation";
        case CONVERT_DISJUNCTION :
          return "convert disjunction";
        case CONVERT_EPSILON :
          return "convert epsilon";
        case CONVERT_KLEENE :
          return "convert kleene";
        case CONVERT_TOKEN :
          return "convert token";
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

    private Step activeStep;


    private ArrayList < Transition > oldTransitions;


    private ArrayList < Transition > newTransitions;


    private ArrayList < State > oldStates;


    private ArrayList < State > newStates;


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
    public StepItem ( Step activeStep, ArrayList < Transition > oldTransitions,
        ArrayList < Transition > newTransitions, ArrayList < State > oldStates,
        ArrayList < State > newStates )
    {
      if ( activeStep == null )
      {
        throw new IllegalArgumentException ( "active step is null" ); //$NON-NLS-1$
      }
      this.activeStep = activeStep;
      this.newStates = newStates;
      this.oldStates = oldStates;
      this.newTransitions = newTransitions;
      this.oldTransitions = oldTransitions;
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
     * Returns the newStates.
     * 
     * @return The newStates.
     * @see #newStates
     */
    public ArrayList < State > getNewStates ()
    {
      return this.newStates;
    }


    /**
     * Returns the newTransitions.
     * 
     * @return The newTransitions.
     * @see #newTransitions
     */
    public ArrayList < Transition > getNewTransitions ()
    {
      return this.newTransitions;
    }


    /**
     * Returns the oldStates.
     * 
     * @return The oldStates.
     * @see #oldStates
     */
    public ArrayList < State > getOldStates ()
    {
      return this.oldStates;
    }


    /**
     * Returns the oldTransitions.
     * 
     * @return The oldTransitions.
     * @see #oldTransitions
     */
    public ArrayList < Transition > getOldTransitions ()
    {
      return this.oldTransitions;
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


  private RegexNode regexNode;


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
    this.regexNode = panel.getRegex ().getRegexNode ();
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
    this.modelConverted = new DefaultMachineModel ( new DefaultENFA ( a, a,
        false ) );
    this.modelOriginal = new DefaultRegexModel ( this.panel.getRegex () );
    this.modelOriginal.initializeGraph ();

    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();

    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );

    this.jGTIGraphConverted = this.modelConverted.getJGTIGraph ();
    this.jGTIGraphConverted.setEnabled ( false );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGTIGraphConverted );

    while ( !this.endReached )
    {
      try
      {
        performNextStep ( false );
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
      }
    }

    show ();
  }


  /**
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertMachineDialogBounds ();
    if ( ( rect.x == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y ) )
    {
      rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
          - ( this.gui.getWidth () / 2 );
      rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
          - ( this.gui.getHeight () / 2 );
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
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
  }


  public void handleCancel ()
  {
    this.gui.dispose ();
  }


  public void handleEndStep ()
  {
  }


  public void handleNextStep ()
  {
    try
    {
      this.modelConverted = new DefaultMachineModel ( this.panel.getRegex ()
          .getRegexNode ().toNFA ( this.panel.getRegex ().getAlphabet () ) );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }
  }


  public void handleOk ()
  {
  }


  public void handlePreviousStep ()
  {
  }


  public void handlePrint ()
  {
  }


  public void handleStop ()
  {
  }


  private final void performNextStep ( boolean b ) throws StateException
  {
    if ( !b )
    {
      String stateName = "z"; //$NON-NLS-1$
      int count = 0;

      DefaultENFA nfa = this.regexNode.toNFA ( this.panel.getRegex ()
          .getAlphabet () );
      for ( State s : nfa.getState () )
      {
        s.setName ( stateName + count++ );
        this.modelConverted.createStateView ( 0, 0, s, false );
      }
      for ( Transition t : nfa.getTransition () )
      {
        this.modelConverted.createTransitionView ( t, this.modelConverted
            .getStateViewForState ( t.getStateBegin () ), this.modelConverted
            .getStateViewForState ( t.getStateEnd () ), true, false, false );
      }
      LayoutManager manager = new LayoutManager ( this.modelConverted, null );
      manager.doLayout ();

      this.endReached = true;
      
      setStatus ();
    }
  }


  private void addStepItem() {
    
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
