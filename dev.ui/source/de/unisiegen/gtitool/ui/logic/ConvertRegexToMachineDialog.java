package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultPositionState;
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
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.StateView;
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
 * The LogicClass for the RegexToMachine Converter
 * 
 * @author Simon Meurer
 */
public class ConvertRegexToMachineDialog implements
    LogicClass < ConvertRegexToMachineDialogForm >, Converter
{

  /**
   * Does the next step after a delay.
   * 
   * @author Simon Meurer
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
   * @author Simon Meurer
   */
  private enum Step
  {

    /**
     * The convert concat step
     */
    CONVERT_CONCAT,

    /**
     * The convert disjunction step
     */
    CONVERT_DISJUNCTION,

    /**
     * The convert Epsilon step
     */
    CONVERT_EPSILON,

    /**
     * The convert kleene step
     */
    CONVERT_KLEENE,

    /**
     * The convert Token step
     */
    CONVERT_TOKEN,

    /**
     * The finish step.
     */
    FINISH,

    /**
     * The initial step
     */
    INITIAL;

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

    /**
     * TODO
     */
    private Step activeStep;


    /**
     * TODO
     */
    private ArrayList < String > addedStates;


    /**
     * TODO
     */
    private ArrayList < DefaultStateView > setFinalFalse;


    /**
     * TODO
     */
    private ArrayList < DefaultStateView > setStartFalse;


    /**
     * TODO
     */
    private ArrayList < DefaultTransitionView > addedTransitions;


    private ArrayList < ObjectPair < Symbol, DefaultStateView >> removedTransitions;


    private String removeStateName;


    /**
     * TODO
     */
    private RegexNode actNode;


    /**
     * TODO
     */
    private int actCount = 0;


    /**
     * Allocates a new {@link StepItem}.
     */
    public StepItem (
        Step activeStep,
        ArrayList < String > addedStates,
        String removedStateName,
        ArrayList < DefaultTransitionView > addedTransitions,
        ArrayList < ObjectPair < Symbol, DefaultStateView > > removedTransitions,
        ArrayList < DefaultStateView > setStartFalse,
        ArrayList < DefaultStateView > setFinalFalse, int count,
        RegexNode actNode )
    {
      if ( activeStep == null )
      {
        throw new IllegalArgumentException ( "active step is null" ); //$NON-NLS-1$
      }
      this.activeStep = activeStep;
      this.addedStates = addedStates;
      this.removeStateName = removedStateName;
      this.setFinalFalse = setFinalFalse;
      this.setStartFalse = setStartFalse;
      this.addedTransitions = addedTransitions;
      this.removedTransitions = removedTransitions;
      this.actCount = count;
      this.actNode = actNode;
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
     * Returns the actCount.
     * 
     * @return The actCount.
     * @see #actCount
     */
    public int getActCount ()
    {
      return this.actCount;
    }


    /**
     * Returns the addedStates.
     * 
     * @return The addedStates.
     * @see #addedStates
     */
    public ArrayList < String > getAddedStates ()
    {
      return this.addedStates;
    }


    /**
     * Returns the removeStateName.
     * 
     * @return The removeStateName.
     * @see #removeStateName
     */
    public String getRemoveStateName ()
    {
      return this.removeStateName;
    }


    /**
     * Returns the setFinalFalse.
     * 
     * @return The setFinalFalse.
     * @see #setFinalFalse
     */
    public ArrayList < DefaultStateView > getSetFinalFalse ()
    {
      return this.setFinalFalse;
    }


    /**
     * Returns the setStartFalse.
     * 
     * @return The setStartFalse.
     * @see #setStartFalse
     */
    public ArrayList < DefaultStateView > getSetStartFalse ()
    {
      return this.setStartFalse;
    }


    /**
     * Returns the actNode.
     * 
     * @return The actNode.
     * @see #actNode
     */
    public RegexNode getActNode ()
    {
      return this.actNode;
    }


    /**
     * Returns the addedTransitions.
     * 
     * @return The addedTransitions.
     * @see #addedTransitions
     */
    public ArrayList < DefaultTransitionView > getAddedTransitions ()
    {
      return this.addedTransitions;
    }


    /**
     * Returns the removedTransitions.
     * 
     * @return The removedTransitions.
     * @see #removedTransitions
     */
    public ArrayList < ObjectPair < Symbol, DefaultStateView >> getRemovedTransitions ()
    {
      return this.removedTransitions;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertRegexToMachineDialog.class );


  private Step actualStep = null;


  private StepItem actualStepItem = null;


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
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  private MainWindowForm mainWindowForm;


  /**
   * The converted {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultRegexModel modelOriginal;


  private RegexPanel panel;


  private JFrame parent;


  private boolean emptyStateCreated = false;


  private HashMap < String, Position > positionMap;


  private RegexNode regexNode;


  private ArrayList < DefaultPositionState > positionStates;


  private HashMap < RegexNode, ArrayList < DefaultStateView > > stateViewList = new HashMap < RegexNode, ArrayList < DefaultStateView > > ();


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  private DefaultRegex defaultRegex;


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


  private EntityType toEntityType;


  /**
   * TODO
   * 
   * @param fromEntityType
   * @param toEntityType
   * @param complete
   * @see de.unisiegen.gtitool.ui.convert.Converter#convert(de.unisiegen.gtitool.core.entities.InputEntity.EntityType,
   *      de.unisiegen.gtitool.core.entities.InputEntity.EntityType, boolean)
   */
  public void convert ( @SuppressWarnings ( "unused" )
  EntityType fromEntityType, EntityType toEntityType, boolean complete )
  {
    this.gui = new ConvertRegexToMachineDialogForm ( this, this.parent );

    this.toEntityType = toEntityType;
    Alphabet a = this.panel.getRegex ().getAlphabet ();
    this.defaultRegex = this.panel.getRegex ().clone ();
    if ( this.toEntityType.equals ( MachineType.ENFA ) )
    {
      this.modelConverted = new DefaultMachineModel ( new DefaultENFA ( a, a,
          false ) );
    }
    else if ( this.toEntityType.equals ( MachineType.DFA ) )
    {
      this.modelConverted = new DefaultMachineModel ( new DefaultDFA ( a, a,
          false ) );
      this.positionStates = new ArrayList < DefaultPositionState > ();

      this.defaultRegex.setRegexNode ( new ConcatenationNode (
          this.defaultRegex.getRegexNode (), new TokenNode ( "#" ) ),
          this.defaultRegex.getRegexString () );
      this.regexNode = this.defaultRegex.getRegexNode ();
    }
    this.modelOriginal = new DefaultRegexModel ( this.defaultRegex );
    this.modelOriginal.initializeGraph ();
    this.modelOriginal.createTree ();

    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();

    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );

    this.jGTIGraphConverted = this.modelConverted.getJGTIGraph ();
    this.jGTIGraphConverted.setEnabled ( false );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGTIGraphConverted );

    this.positionMap = new HashMap < String, Position > ();

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
    new LayoutManager ( this.modelConverted, null ).doLayout ();
    for ( DefaultStateView current : this.modelConverted.getStateViewList () )
    {
      int yOffset = current.getState ().isLoopTransition () ? StateView.LOOP_TRANSITION_OFFSET
          : 0;
      this.positionMap.put ( current.getState ().getName (), new Position (
          current.getPositionX (), current.getPositionY () + yOffset ) );
    }
    if ( this.toEntityType.equals ( MachineType.ENFA ) )
    {
      while ( !this.stepItemList.isEmpty () )
      {
        performPreviousStep ( false );
      }
      this.endReached = this.regexNode.isMarked ();
    }

    setStatus ();
    show ();
  }


  /**
   * Returns the position of the {@link State} or null if the position is not
   * defined.
   * 
   * @param state The {@link State}.
   * @return The position of the {@link State} or null if the position is not
   *         defined.
   */
  private final Position getPosition ( State state )
  {
    return this.positionMap.get ( state.getName () );
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
    while ( !this.stepItemList.isEmpty () )
    {
      handlePreviousStep ();
    }
  }


  public void handleCancel ()
  {
    this.regexNode.unmarkAll ();
    this.gui.dispose ();
  }


  public void handleEndStep ()
  {
    while ( !this.endReached )
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
    TreeSet < String > nameList = new TreeSet < String > ();
    int fileCount = 0;
    for ( EditorPanel current : this.mainWindowForm.getJGTIMainSplitPane ()
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        fileCount++ ;
      }
    }

    String newName;
    if ( this.toEntityType.equals ( MachineType.ENFA ) )
    {
      newName = Messages.getString ( "MainWindow.NewFile" ) + fileCount //$NON-NLS-1$
          + "." + MachineType.ENFA.getFileEnding (); //$NON-NLS-1$
      while ( nameList.contains ( newName ) )
      {
        fileCount++ ;
        newName = Messages.getString ( "MainWindow.NewFile" ) + fileCount //$NON-NLS-1$
            + "." + MachineType.ENFA.getFileEnding (); //$NON-NLS-1$
      }
    }
    else
    {

      newName = Messages.getString ( "MainWindow.NewFile" ) + fileCount //$NON-NLS-1$
          + "." + MachineType.DFA.getFileEnding (); //$NON-NLS-1$
      while ( nameList.contains ( newName ) )
      {
        fileCount++ ;
        newName = Messages.getString ( "MainWindow.NewFile" ) + fileCount //$NON-NLS-1$
            + "." + MachineType.DFA.getFileEnding (); //$NON-NLS-1$
      }
    }
    EditorPanel newEditorPanel = null;
    try
    {
      newEditorPanel = new MachinePanel ( this.mainWindowForm,
          new DefaultMachineModel ( this.modelConverted.getElement (),
              this.toEntityType.toString () ), null );
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
    }
    catch ( StoreException exc )
    {
      exc.printStackTrace ();
    }

    newEditorPanel.setName ( newName );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .addEditorPanel ( newEditorPanel );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( newEditorPanel );

    this.regexNode.unmarkAll ();
    this.gui.dispose ();
  }


  /**
   * TODO
   */
  public void handlePreviousStep ()
  {
    performPreviousStep ( true );
  }


  /**
   * TODO
   */
  public void handlePrint ()
  {
    // TODO
  }


  /**
   * TODO
   */
  public void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    setStatus ();
  }


  /**
   * TODO
   */
  private int count = 0;


  /**
   * TODO
   * 
   * @param b
   * @throws StateException
   */
  private final void performNextStep ( boolean b ) throws StateException
  {
    if ( this.toEntityType.equals ( MachineType.ENFA ) )
    {
      ArrayList < String > addedStates = new ArrayList < String > ();
      ArrayList < DefaultStateView > setFinalFalse = new ArrayList < DefaultStateView > ();
      ArrayList < DefaultStateView > setStartFalse = new ArrayList < DefaultStateView > ();
      String removedStateName = null;
      ArrayList < DefaultTransitionView > addedTransitions = new ArrayList < DefaultTransitionView > ();
      ArrayList < ObjectPair < Symbol, DefaultStateView > > removedTransitions = new ArrayList < ObjectPair < Symbol, DefaultStateView > > ();
      int c = this.count;

      RegexNode node = this.regexNode.getNextNodeForNFA ();

      // Token
      if ( node instanceof TokenNode )
      {
        this.actualStep = Step.CONVERT_TOKEN;
        TokenNode token = ( TokenNode ) node;
        DefaultState start = new DefaultState ( "s" + token.getPosition () );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );
        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );
        Position p = getPosition ( start );
        if ( p != null )
        {
          startView.move ( p.getX (), p.getY () );
        }

        DefaultState fin = new DefaultState ( "f" + token.getPosition () );
        fin.setFinalState ( true );
        // this.modelConverted.getMachine ().addState ( fin );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );
        p = getPosition ( fin );
        if ( p != null )
        {
          finView.move ( p.getX (), p.getY () );
        }
        addedStates.add ( startView.getState ().getName () );
        addedStates.add ( finView.getState ().getName () );

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

          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              startView, finView, true, false, true ) );
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
      // Epsilon
      if ( node instanceof EpsilonNode )
      {
        this.actualStep = Step.CONVERT_EPSILON;
        EpsilonNode token = ( EpsilonNode ) node;
        DefaultState start = new DefaultState ( "s" + token.getPosition () );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );
        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );
        Position p = getPosition ( start );
        if ( p != null )
        {
          startView.move ( p.getX (), p.getY () );
        }

        DefaultState fin = new DefaultState ( "f" + token.getPosition () );
        fin.setFinalState ( true );
        // this.modelConverted.getMachine ().addState ( fin );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );
        p = getPosition ( fin );
        if ( p != null )
        {
          finView.move ( p.getX (), p.getY () );
        }
        addedStates.add ( startView.getState ().getName () );
        addedStates.add ( finView.getState ().getName () );

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

          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              startView, finView, true, false, true ) );
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
      // Disjunction
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

        DefaultState start = new DefaultState ( "s" + this.count );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );

        DefaultState fin = new DefaultState ( "f" + this.count++ );
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
        Position p = getPosition ( start );
        if ( p != null )
        {
          startView.move ( p.getX (), p.getY () );
        }
        p = getPosition ( fin );
        if ( p != null )
        {
          finView.move ( p.getX (), p.getY () );
        }
        addedStates.add ( startView.getState ().getName () );
        addedStates.add ( finView.getState ().getName () );

        start1.getState ().setStartState ( false );
        start2.getState ().setStartState ( false );
        final1.getState ().setFinalState ( false );
        final2.getState ().setFinalState ( false );
        setStartFalse.add ( start1 );
        setStartFalse.add ( start2 );
        setFinalFalse.add ( final1 );
        setFinalFalse.add ( final2 );

        addedTransitions.add ( this.modelConverted.createTransitionView (
            startStart1, startView, start1, true, false, true ) );
        addedTransitions.add ( this.modelConverted.createTransitionView (
            startStart2, startView, start2, true, false, true ) );
        addedTransitions.add ( this.modelConverted.createTransitionView (
            final1Final, final1, finView, true, false, true ) );
        addedTransitions.add ( this.modelConverted.createTransitionView (
            final2Final, final2, finView, true, false, true ) );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );

        this.stateViewList.put ( dis, views );
      }
      // Concatenation
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
        setFinalFalse.add ( final1 );

        for ( Transition t : start2.getState ().getTransitionBegin () )
        {
          try
          {
            addedTransitions.add ( this.modelConverted.createTransitionView (
                new DefaultTransition ( t.getAlphabet (), t.getAlphabet (), t
                    .getPushDownWordRead (), t.getPushDownWordWrite (), final1
                    .getState (), t.getStateEnd (), t.getSymbol () ), final1,
                this.modelConverted.getStateViewForState ( t.getStateEnd () ),
                true, false, true ) );

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
        removedStateName = start2.getState ().getName ();

        this.modelConverted.removeState ( start2, false );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( start1 );
        views.add ( final2 );

        this.stateViewList.put ( con, views );
      }
      // Kleene Node
      if ( node instanceof KleeneNode )
      {
        this.actualStep = Step.CONVERT_KLEENE;
        KleeneNode k = ( KleeneNode ) node;

        DefaultStateView start1 = this.stateViewList.get (
            k.getChildren ().get ( 0 ) ).get ( 0 );
        DefaultStateView final1 = this.stateViewList.get (
            k.getChildren ().get ( 0 ) ).get ( 1 );

        DefaultState start = new DefaultState ( "s" + this.count );
        start.setStartState ( true );
        // this.modelConverted.getMachine ().addState ( start );

        DefaultState fin = new DefaultState ( "f" + this.count++ );
        fin.setFinalState ( true );
        // this.modelConverted.getMachine ().addState ( fin );

        DefaultStateView startView = this.modelConverted.createStateView ( 0,
            0, start, false );
        DefaultStateView finView = this.modelConverted.createStateView ( 0, 0,
            fin, false );
        Position p = getPosition ( start );
        if ( p != null )
        {
          startView.move ( p.getX (), p.getY () );
        }
        p = getPosition ( fin );
        if ( p != null )
        {
          finView.move ( p.getX (), p.getY () );
        }
        addedStates.add ( startView.getState ().getName () );
        addedStates.add ( finView.getState ().getName () );

        // From start to final
        Transition t = new DefaultTransition ();
        t.setStateBegin ( start );
        t.setStateEnd ( fin );
        addedTransitions.add ( this.modelConverted.createTransitionView ( t,
            startView, finView, true, false, true ) );

        // From start to begin of N(s)
        t = new DefaultTransition ();
        t.setStateBegin ( start );
        t.setStateEnd ( start1.getState () );
        addedTransitions.add ( this.modelConverted.createTransitionView ( t,
            startView, start1, true, false, true ) );

        // From end to begin of N(s)
        t = new DefaultTransition ();
        t.setStateBegin ( final1.getState () );
        t.setStateEnd ( start1.getState () );
        addedTransitions.add ( this.modelConverted.createTransitionView ( t,
            final1, start1, true, false, true ) );

        // From end of N(s) to final
        t = new DefaultTransition ();
        t.setStateBegin ( final1.getState () );
        t.setStateEnd ( fin );
        addedTransitions.add ( this.modelConverted.createTransitionView ( t,
            final1, finView, true, false, true ) );

        start1.getState ().setStartState ( false );
        final1.getState ().setFinalState ( false );
        setStartFalse.add ( start1 );
        setFinalFalse.add ( final1 );

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( startView );
        views.add ( finView );

        this.stateViewList.put ( k, views );
      }
      this.stepItemList.add ( new StepItem ( this.actualStep, addedStates,
          removedStateName, addedTransitions, removedTransitions,
          setStartFalse, setFinalFalse, c, node ) );

      this.endReached = this.regexNode.isMarked ();

      if ( b )
      {
        setStatus ();
        updateGraph ();
      }
    }
    else if ( this.toEntityType.equals ( MachineType.DFA ) )
    {
      if ( this.positionStates.isEmpty () )
      {
        HashSet < Integer > positions = new HashSet < Integer > ();
        String name = "";
        ArrayList < RegexNode > firstPos = this.defaultRegex.getRegexNode ()
            .firstPos ();
        for ( RegexNode node : firstPos )
        {
          LeafNode leaf = ( LeafNode ) node;
          positions.add ( new Integer ( leaf.getPosition () ) );
          name += leaf.getPosition ();

        }
        DefaultPositionState state = new DefaultPositionState ( name, positions );
        this.positionStates.add ( state );
        state.setStartState ( true );
        this.positionStateViewList.put ( state, this.modelConverted
            .createStateView ( 0, 0, state, false ) );
      }
      else
      {
        DefaultPositionState positionState = getNextUnmarkedState ();
        if ( positionState == null )
        {
          throw new RuntimeException ( "Da ging wohl was schief!" );
        }
        positionState.mark ();
        for ( Symbol a : this.defaultRegex.getAlphabet () )
        {
          HashSet < Integer > u = new HashSet < Integer > ();
          for ( Integer p : positionState.getPositions () )
          {
            if ( a.getName ().equals (
                this.defaultRegex.symbolAtPosition ( p.intValue () ) ) )
            {
              for ( Integer n : this.defaultRegex.followPos ( p.intValue () ) )
              {
                u.add ( n );
              }
            }
          }
          String name = "";
          for ( Integer i : u )
          {
            name += i;
          }
          DefaultPositionState uState = new DefaultPositionState ( name, u );

          if ( !u.isEmpty () )
          {
            if ( !this.positionStates.contains ( uState ) )
            {
              LeafNode end = ( LeafNode ) this.regexNode.lastPos ().get ( 0 );
              if ( uState.getPositions ().contains (
                  new Integer ( end.getPosition () ) ) )
              {
                uState.setFinalState ( true );
              }
              this.positionStates.add ( uState );
              this.positionStateViewList.put ( uState, this.modelConverted
                  .createStateView ( 0, 0, uState, false ) );
            }

          }
          else
          {
            uState.setName ( "error" );
            if ( !this.emptyStateCreated )
            {
              this.positionStates.add ( uState );
              this.positionStateViewList.put ( uState, this.modelConverted
                  .createStateView ( 0, 0, uState, false ) );
              this.emptyStateCreated = true;
            }
          }
          if ( this.positionStateViewList.get ( uState ) != null
              && this.positionStateViewList.get ( positionState ) != null )
          {
            ArrayList < Symbol > s = new ArrayList < Symbol > ();
            s.add ( a );
            try
            {
              DefaultTransitionView old = null;
              for ( DefaultTransitionView t : this.modelConverted
                  .getTransitionViewList () )
              {
                if ( t.getSourceView ().equals (
                    this.positionStateViewList.get ( positionState ) )
                    && t.getTargetView ().equals (
                        this.positionStateViewList.get ( uState ) ) )
                {
                  old = t;
                }
              }
              if ( old == null )
              {
                this.modelConverted.createTransitionView (
                    new DefaultTransition ( this.defaultRegex.getAlphabet (),
                        this.defaultRegex.getAlphabet (), new DefaultWord (),
                        new DefaultWord (), this.positionStateViewList.get (
                            positionState ).getState (),
                        this.positionStateViewList.get ( uState ).getState (),
                        a ), this.positionStateViewList.get ( positionState ),
                    this.positionStateViewList.get ( uState ), true, false,
                    true );
              } else {
                old.getTransition ().add ( a );
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
      boolean ready = true;
      for ( DefaultPositionState state : this.positionStates )
      {
        if ( !state.isMarked () )
        {
          ready = false;
        }
      }
      if ( this.positionStates.isEmpty () )
      {
        ready = false;
      }

      this.endReached = ready;

      if ( b )
      {
        setStatus ();
        updateGraph ();
      }
    }
  }


  private boolean isEmptyStateCreated = false;


  private HashMap < DefaultPositionState, DefaultStateView > positionStateViewList = new HashMap < DefaultPositionState, DefaultStateView > ();


  private DefaultPositionState getNextUnmarkedState ()
  {
    for ( DefaultPositionState state : this.positionStates )
    {
      if ( !state.isMarked () )
      {
        return state;
      }
    }
    return null;
  }


  /**
   * TODO
   * 
   * @param manual
   */
  private void performPreviousStep ( boolean manual )
  {
    StepItem stepItem = this.stepItemList
        .remove ( this.stepItemList.size () - 1 );

    System.err.println ( "StepItem: " + stepItem.getActiveStep () );
    for ( String name : stepItem.getAddedStates () )
    {
      System.err.println ( name );
      for ( int i = 0 ; i < this.modelConverted.getStateViewList ().size () ; i++ )
      {
        if ( this.modelConverted.getStateViewList ().get ( i ).getState ()
            .getName ().equals ( name ) )
        {
          this.modelConverted.removeState ( this.modelConverted
              .getStateViewList ().get ( i ), false );
        }
      }
    }

    for ( DefaultStateView view : stepItem.getSetFinalFalse () )
    {
      view.getState ().setFinalState ( true );
    }
    for ( DefaultStateView view : stepItem.getSetStartFalse () )
    {
      view.getState ().setStartState ( true );
    }
    if ( !stepItem.getActiveStep ().equals ( Step.CONVERT_CONCAT ) )
    {
      for ( DefaultTransitionView view : stepItem.getAddedTransitions () )
      {
        this.modelConverted.removeTransition ( view, false );
      }
    }
    if ( stepItem.getActiveStep ().equals ( Step.CONVERT_CONCAT ) )
    {
      try
      {
        DefaultStateView start2 = this.modelConverted.createStateView ( 0, 0,
            new DefaultState ( stepItem.getRemoveStateName () ), false );
        start2.getState ().setStartState ( true );
        for ( DefaultTransitionView transitionView : stepItem
            .getAddedTransitions () )
        {
          transitionView.setSource ( start2 );
        }

        ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
        views.add ( start2 );
        views
            .add ( this.stateViewList.get ( stepItem.getActNode () ).get ( 1 ) );
        this.stateViewList.put ( stepItem.getActNode (), views );

      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
      }
    }
    this.actualStep = stepItem.getActiveStep ();
    stepItem.getActNode ().unmark ();

    this.count = stepItem.getActCount ();

    System.err.println ( "Step complete!" );
    if ( manual )
    {
      setStatus ();
      updateGraph ();
    }
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    if ( this.toEntityType.equals ( MachineType.ENFA ) )
    {
      this.endReached = this.regexNode.isMarked ();
    }
    else
    {
      boolean ready = true;
      for ( DefaultPositionState state : this.positionStates )
      {
        if ( !state.isMarked () )
        {
          ready = false;
        }
      }
      if ( this.positionStates.isEmpty () )
      {
        ready = false;
      }

      this.endReached = ready;
    }
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


  /**
   * Updates the {@link JGTIGraph}s.
   */
  private final void updateGraph ()
  {
    this.modelOriginal.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
    this.modelConverted.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
  }
}
