package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.DisjunctionNode;
import de.unisiegen.gtitool.core.entities.regex.EpsilonNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
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
     * The initial step
     */
    INITIAL,

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
        case INITIAL :
          return "initial";
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


    private ArrayList < DefaultTransitionView > oldTransitions;


    private ArrayList < DefaultTransitionView > newTransitions;


    private ArrayList < DefaultStateView > oldStates;


    private ArrayList < DefaultStateView > newStates;


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
    public StepItem ( Step activeStep,
        ArrayList < DefaultTransitionView > oldTransitions,
        ArrayList < DefaultTransitionView > newTransitions,
        ArrayList < DefaultStateView > oldStates,
        ArrayList < DefaultStateView > newStates )
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
    public ArrayList < DefaultStateView > getNewStates ()
    {
      return this.newStates;
    }


    /**
     * Returns the newTransitions.
     * 
     * @return The newTransitions.
     * @see #newTransitions
     */
    public ArrayList < DefaultTransitionView > getNewTransitions ()
    {
      return this.newTransitions;
    }


    /**
     * Returns the oldStates.
     * 
     * @return The oldStates.
     * @see #oldStates
     */
    public ArrayList < DefaultStateView > getOldStates ()
    {
      return this.oldStates;
    }


    /**
     * Returns the oldTransitions.
     * 
     * @return The oldTransitions.
     * @see #oldTransitions
     */
    public ArrayList < DefaultTransitionView > getOldTransitions ()
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


  private MainWindowForm mainWindowForm;


  /**
   * TODO
   */
  public ConvertRegexToMachineDialog ( JFrame parent, RegexPanel panel )
  {
    this.parent = parent;
    this.panel = panel;
    this.mainWindowForm = panel.getMainWindowForm ();
    this.regexNode = panel.getRegex ().getRegexNode ();
    if ( !this.regexNode.isInCoreSyntax () )
    {
      this.regexNode = this.regexNode.toCoreSyntax ();
    }
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
    for ( int i = this.stepItemList.indexOf ( this.actualStepItem ) ; i > 0 ; i-- )
    {
      handlePreviousStep ();
    }
  }


  public void handleCancel ()
  {
    this.gui.dispose ();
  }


  public void handleEndStep ()
  {
    for ( int i = this.stepItemList.indexOf ( this.actualStepItem ) ; i < this.stepItemList
        .size () ; i++ )
    {
      handleNextStep ();
    }
  }


  public void handleNextStep ()
  {
    try
    {
      performNextStep ( true );
      setStatus ();
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }
  }


  public void handleOk ()
  {
    EditorPanel newEditorPanel = new MachinePanel ( this.mainWindowForm,
        this.modelConverted, null );
    TreeSet < String > nameList = new TreeSet < String > ();
    int count = 0;
    for ( EditorPanel current : this.mainWindowForm.getJGTIMainSplitPane ()
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        count++ ;
      }
    }

    String newName = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
        + "." + MachineType.ENFA.getFileEnding (); //$NON-NLS-1$
    while ( nameList.contains ( newName ) )
    {
      count++ ;
      newName = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + MachineType.ENFA.getFileEnding (); //$NON-NLS-1$
    }

    newEditorPanel.setName ( newName );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .addEditorPanel ( newEditorPanel );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( newEditorPanel );

    this.gui.dispose ();
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


  private HashMap < RegexNode, ArrayList < DefaultStateView > > stateViewList = new HashMap < RegexNode, ArrayList < DefaultStateView > > ();


  /**
   * TODO
   * 
   * @param b
   * @throws StateException
   */
  private final void performNextStep ( boolean b ) throws StateException
  {
    if ( !b )
    {
      int count = 0;

      RegexNode node = this.regexNode.getNextNodeForNFA ();

      System.err.println ( node );
      if ( node instanceof TokenNode )
      {
        this.actualStep = Step.CONVERT_TOKEN;
        TokenNode token = ( TokenNode ) node;
        DefaultState start = new DefaultState ( "s" + token.getPosition () );
        start.setStartState ( true );
        start.setFinalState ( false );
        // this.modelConverted.getMachine ().addState ( start );
        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );

        DefaultState fin = new DefaultState ( "f" + token.getPosition () );
        fin.setFinalState ( true );
        fin.setStartState ( false );
        // this.modelConverted.getMachine ().addState ( fin );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );
        this.stateViewList.put ( token, views );

        ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
        symbols.add ( new DefaultSymbol ( token.getName () ) );
        Transition t;
        Alphabet a = this.modelOriginal.getRegex ().getAlphabet ();
        try
        {
          t = new DefaultTransition ( a, a, new DefaultWord (),
              new DefaultWord (), start, fin, symbols );

          this.modelConverted.createTransitionView ( t, startView, finView,
              true, false, true );
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
      if ( node instanceof EpsilonNode )
      {
        this.actualStep = Step.CONVERT_EPSILON;
        EpsilonNode token = ( EpsilonNode ) node;
        DefaultState start = new DefaultState ( "s" + token.getPosition () );
        start.setStartState ( true );
        start.setFinalState ( false );
        this.modelConverted.getMachine ().addState ( start );
        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );

        DefaultState fin = new DefaultState ( "f" + token.getPosition () );
        fin.setFinalState ( true );
        fin.setStartState ( false );
        this.modelConverted.getMachine ().addState ( fin );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );
        this.stateViewList.put ( token, views );

        ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
        symbols.add ( new DefaultSymbol () );
        Transition t;
        Alphabet a = this.modelOriginal.getRegex ().getAlphabet ();
        try
        {
          t = new DefaultTransition ( a, a, new DefaultWord (),
              new DefaultWord (), start, fin, symbols );

          this.modelConverted.createTransitionView ( t, startView, finView,
              true, false, true );
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
      if ( node instanceof DisjunctionNode )
      {
        this.actualStep = Step.CONVERT_DISJUNCTION;
        DisjunctionNode dis = ( DisjunctionNode ) node;
        DefaultStateView start1 = this.stateViewList.get (
            dis.getChildren ().get ( 0 ) ).get ( 0 );
        DefaultStateView start2 = this.stateViewList.get (
            dis.getChildren ().get ( 1 ) ).get ( 0 );
        DefaultStateView final1 = this.stateViewList.get (
            dis.getChildren ().get ( 0 ) ).get ( 1 );
        DefaultStateView final2 = this.stateViewList.get (
            dis.getChildren ().get ( 1 ) ).get ( 1 );

        if ( final1 == null || final2 == null || start1 == null
            || start2 == null )
        {
          throw new IllegalArgumentException ( "A State is null" );
        }

        DefaultState start = new DefaultState ( "s" + count );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );

        DefaultState fin = new DefaultState ( "f" + count++ );
        fin.setFinalState ( true );
        // this.modelConverted.getMachine ().addState ( fin );

        DefaultTransition startStart1 = new DefaultTransition ();
        startStart1.setStateBegin ( start );
        startStart1.setStateEnd ( start1.getState () );
        DefaultTransition startStart2 = new DefaultTransition ();
        startStart2.setStateBegin ( start );
        startStart2.setStateEnd ( start2.getState () );
        DefaultTransition final1Final = new DefaultTransition ();
        final1Final.setStateBegin ( final1.getState () );
        final1Final.setStateEnd ( fin );
        DefaultTransition final2Final = new DefaultTransition ();
        final2Final.setStateBegin ( final2.getState () );
        final2Final.setStateEnd ( fin );

        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );

        start1.getState ().setStartState ( false );
        start2.getState ().setStartState ( false );
        final1.getState ().setFinalState ( false );
        final2.getState ().setFinalState ( false );

        this.modelConverted.createTransitionView ( startStart1, startView,
            start1, true, false, true );
        this.modelConverted.createTransitionView ( startStart2, startView,
            start2, true, false, true );
        this.modelConverted.createTransitionView ( final1Final, final1,
            finView, true, false, true );
        this.modelConverted.createTransitionView ( final2Final, final2,
            finView, true, false, true );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );

        this.stateViewList.put ( dis, views );
      }
      if ( node instanceof ConcatenationNode )
      {
        this.actualStep = Step.CONVERT_CONCAT;
        ConcatenationNode con = ( ConcatenationNode ) node;
        DefaultStateView start1 = this.stateViewList.get (
            con.getChildren ().get ( 0 ) ).get ( 0 );
        DefaultStateView start2 = this.stateViewList.get (
            con.getChildren ().get ( 1 ) ).get ( 0 );
        DefaultStateView final1 = this.stateViewList.get (
            con.getChildren ().get ( 0 ) ).get ( 1 );
        DefaultStateView final2 = this.stateViewList.get (
            con.getChildren ().get ( 1 ) ).get ( 1 );

        final1.getState ().setFinalState ( false );
        for ( Transition t : start2.getState ().getTransitionBegin () )
        {
          try
          {
            this.modelConverted.createTransitionView ( new DefaultTransition (
                t.getAlphabet (), t.getAlphabet (), t.getPushDownWordRead (), t
                    .getPushDownWordWrite (), final1.getState (), t
                    .getStateEnd (), t.getSymbol () ), final1,
                this.modelConverted.getStateViewForState ( t.getStateEnd () ),
                true, false, true );
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
        this.modelConverted.removeState ( start2, false );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( start1 );
        views.add ( final2 );

        this.stateViewList.put ( con, views );
      }
      if ( node instanceof KleeneNode )
      {
        this.actualStep = Step.CONVERT_KLEENE;
        KleeneNode k = ( KleeneNode ) node;

        DefaultStateView start1 = this.stateViewList.get (
            k.getChildren ().get ( 0 ) ).get ( 0 );
        DefaultStateView final1 = this.stateViewList.get (
            k.getChildren ().get ( 0 ) ).get ( 1 );

        DefaultState start = new DefaultState ( "s" + count );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );

        DefaultState fin = new DefaultState ( "f" + count++ );
        fin.setFinalState ( true );
        // this.modelConverted.getMachine ().addState ( fin );

        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );

        // From start to final
        Transition t = new DefaultTransition ();
        t.setStateBegin ( start );
        t.setStateEnd ( fin );
        this.modelConverted.createTransitionView ( t, startView, finView, true,
            false, true );

        // From start to begin of N(s)
        t = new DefaultTransition ();
        t.setStateBegin ( start );
        t.setStateEnd ( start1.getState () );
        this.modelConverted.createTransitionView ( t, startView, start1, true,
            false, true );

        // From end to begin of N(s)
        t = new DefaultTransition ();
        t.setStateBegin ( final1.getState () );
        t.setStateEnd ( start1.getState () );
        this.modelConverted.createTransitionView ( t, final1, start1, true,
            false, true );

        // From end of N(s) to final
        t = new DefaultTransition ();
        t.setStateBegin ( final1.getState () );
        t.setStateEnd ( fin );
        this.modelConverted.createTransitionView ( t, final1, finView, true,
            false, true );

        start1.getState ().setStartState ( false );
        final1.getState ().setFinalState ( false );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );

        this.stateViewList.put ( k, views );
      }
      if ( this.stepItemList.size () < 1 )
      {
        addStepItem ( new StepItem ( Step.INITIAL,
            new ArrayList < DefaultTransitionView > (),
            new ArrayList < DefaultTransitionView > (),
            new ArrayList < DefaultStateView > (),
            new ArrayList < DefaultStateView > () ) );
      }
      else
      {
        addStepItem ( this.stepItemList.get ( this.stepItemList.size () - 1 ) );
      }
      this.endReached = this.regexNode.isMarked ();

      setStatus ();

    }
  }

  private StepItem actualStepItem = null;

  private Step actualStep = null;


  /**
   * TODO
   * 
   * @param lastStepItem
   */
  private void addStepItem ( StepItem lastStepItem )
  {
    ArrayList < DefaultTransitionView > oldTransitions = lastStepItem
        .getNewTransitions ();
    ArrayList < DefaultTransitionView > newTransitions = this.modelConverted
        .getTransitionViewList ();
    ArrayList < DefaultStateView > oldStates = lastStepItem.getNewStates ();
    ArrayList < DefaultStateView > newStates = this.modelConverted
        .getStateViewList ();

    StepItem it = new StepItem ( this.actualStep, oldTransitions,
        newTransitions, oldStates, newStates );
    this.actualStepItem = it;
    this.stepItemList.add ( it );
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {

    LayoutManager manager = new LayoutManager ( this.modelConverted, null );
    manager.doLayout ();
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
