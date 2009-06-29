package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphSelectionModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultBlackBoxState;
import de.unisiegen.gtitool.core.entities.DefaultPositionState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.regex.CharacterClassNode;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.DisjunctionNode;
import de.unisiegen.gtitool.core.entities.regex.EpsilonNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultBlackboxView;
import de.unisiegen.gtitool.ui.jgraph.DefaultNodeView;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIBlackboxGraph;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.StateView;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;
import de.unisiegen.gtitool.ui.utils.LayoutManager;
import de.unisiegen.gtitool.ui.utils.TextLoader;
import de.unisiegen.gtitool.ui.utils.XGrid;


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
            performNextStep ( true );
          }
        }
      } );
    }
  }


  /**
   * A Representation of a BlackBox with {@link String}s for the states
   */
  private class BlackBox
  {

    /**
     * The content
     */
    private RegexNode content;


    /**
     * The end state
     */
    private String end;


    /**
     * The start state
     */
    private String start;


    /**
     * Creates a new BlackBox
     * 
     * @param start The start state
     * @param end The end state
     * @param content The content
     */
    public BlackBox ( String start, String end, RegexNode content )
    {
      this.start = start;
      this.end = end;
      this.content = content;
    }


    /**
     * Returns the content.
     * 
     * @return The content.
     * @see #content
     */
    public RegexNode getContent ()
    {
      return this.content;
    }


    /**
     * Returns the end.
     * 
     * @return The end.
     * @see #end
     */
    public String getEnd ()
    {
      return this.end;
    }


    /**
     * Returns the start.
     * 
     * @return The start.
     * @see #start
     */
    public String getStart ()
    {
      return this.start;
    }
  }


  /**
   * The type of the conversation of the {@link Regex}
   */
  public enum ConvertRegexType
  {
    /**
     * The regex to dfa type
     */
    REGEX_TO_DFA,

    /**
     * The regex to enfa type
     */
    REGEX_TO_ENFA,

    /**
     * The regex to nfa type
     */
    REGEX_TO_NFA;
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
     * The convert Token step
     */
    CONVERT_CHARCLASS,

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
          return "convert concatenation"; //$NON-NLS-1$
        case CONVERT_DISJUNCTION :
          return "convert disjunction"; //$NON-NLS-1$
        case CONVERT_EPSILON :
          return "convert epsilon"; //$NON-NLS-1$
        case CONVERT_KLEENE :
          return "convert kleene"; //$NON-NLS-1$
        case CONVERT_TOKEN :
          return "convert token"; //$NON-NLS-1$
        case CONVERT_CHARCLASS :
          return "convert class"; //$NON-NLS-1$
        case INITIAL :
          return "initial"; //$NON-NLS-1$
        case FINISH :
          return "finish"; //$NON-NLS-1$
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
     * The act count
     */
    private int actCount = 0;


    /**
     * The follow pos
     */
    private ArrayList < Integer > actFollowPos;


    /**
     * The active {@link Step}
     */
    private Step activeStep;


    /**
     * The active {@link RegexNode}
     */
    private RegexNode actNode;


    /**
     * The added BlackBoxes
     */
    private ArrayList < DefaultBlackboxView > addedBlackboxes;


    /**
     * The added states
     */
    private ArrayList < String > addedStates;


    /**
     * The added Symbols to Transitions
     */
    private ArrayList < ObjectPair < DefaultTransitionView, ArrayList < Symbol > > > addedSymbolsToTransition;


    /**
     * The added {@link DefaultTransitionView}s
     */
    private ArrayList < DefaultTransitionView > addedTransitions;


    /**
     * The last controlled Symbol
     */
    private ArrayList < Symbol > controlledSymbol;


    /**
     * Is in this step the errorState created
     */
    private boolean errorCreated = false;


    /**
     * The last marked Position state for unmarking
     */
    private DefaultPositionState markedPositionState;


    /**
     * The positions of the states
     */
    private HashMap < String, Position > positions;


    /**
     * The {@link RedoUndoItem} for the concatenation
     */
    private RedoUndoItem redoUndoItem;


    /**
     * The removed black boxes
     */
    private ArrayList < BlackBox > removedBlackboxes;


    /**
     * The final states that where made normal
     */
    private ArrayList < DefaultStateView > setFinalFalse;


    /**
     * The start states that where made normal
     */
    private ArrayList < DefaultStateView > setStartFalse;


    /**
     * The {@link XGrid}
     */
    private XGrid stepXGrid;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param activeStep The active {@link Step}
     * @param addedStates The added states
     * @param redoUndoItem The {@link RedoUndoItem} for Concatenation
     * @param addedTransitions The added Transitions
     * @param setStartFalse The start states that where made normal
     * @param setFinalFalse The final states that where made normal
     * @param count The act count
     * @param actNode The active {@link RegexNode}
     * @param errorCreated Is error state created
     * @param markedPositionState The last marked position state
     * @param controlledSymbol The last controlled Symbol
     * @param addedSymbolsToTransition The added Symbols to Transitions
     * @param removedBlackBoxes The removed black boxes
     * @param addedBlackBoxes The added black boxes
     * @param positions The positions
     * @param xGrid The {@link XGrid}
     * @param actFollowPos The follow pos
     */
    public StepItem (
        Step activeStep,
        ArrayList < String > addedStates,
        RedoUndoItem redoUndoItem,
        ArrayList < DefaultTransitionView > addedTransitions,
        ArrayList < DefaultStateView > setStartFalse,
        ArrayList < DefaultStateView > setFinalFalse,
        int count,
        RegexNode actNode,
        boolean errorCreated,
        DefaultPositionState markedPositionState,
        ArrayList < Symbol > controlledSymbol,
        ArrayList < ObjectPair < DefaultTransitionView, ArrayList < Symbol > > > addedSymbolsToTransition,
        ArrayList < BlackBox > removedBlackBoxes,
        ArrayList < DefaultBlackboxView > addedBlackBoxes,
        HashMap < String, Position > positions, XGrid xGrid,
        ArrayList < Integer > actFollowPos )
    {
      if ( activeStep == null )
      {
        throw new IllegalArgumentException ( "active step is null" ); //$NON-NLS-1$
      }
      this.activeStep = activeStep;
      this.addedStates = addedStates;
      this.redoUndoItem = redoUndoItem;
      this.setFinalFalse = setFinalFalse;
      this.setStartFalse = setStartFalse;
      this.addedTransitions = addedTransitions;
      this.actCount = count;
      this.actNode = actNode;
      this.errorCreated = errorCreated;
      this.markedPositionState = markedPositionState;
      this.controlledSymbol = controlledSymbol;
      this.addedSymbolsToTransition = addedSymbolsToTransition;
      this.addedBlackboxes = addedBlackBoxes;
      this.removedBlackboxes = removedBlackBoxes;
      this.positions = positions;
      this.stepXGrid = xGrid;
      this.actFollowPos = actFollowPos;
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
     * Returns the addedBlackboxes.
     * 
     * @return The addedBlackboxes.
     * @see #addedBlackboxes
     */
    public ArrayList < DefaultBlackboxView > getAddedBlackboxes ()
    {
      return this.addedBlackboxes;
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
     * Returns the addedSymbolsToTransition.
     * 
     * @return The addedSymbolsToTransition.
     * @see #addedSymbolsToTransition
     */
    public ArrayList < ObjectPair < DefaultTransitionView, ArrayList < Symbol > >> getAddedSymbolsToTransition ()
    {
      return this.addedSymbolsToTransition;
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
     * Returns the controlledSymbol.
     * 
     * @return The controlledSymbol.
     * @see #controlledSymbol
     */
    public ArrayList < Symbol > getControlledSymbol ()
    {
      return this.controlledSymbol;
    }


    /**
     * Returns the actFollowPos.
     * 
     * @return The actFollowPos.
     * @see #actFollowPos
     */
    public ArrayList < Integer > getFollowPos ()
    {
      return this.actFollowPos;
    }


    /**
     * Returns the markedPositionState.
     * 
     * @return The markedPositionState.
     * @see #markedPositionState
     */
    public DefaultPositionState getMarkedPositionState ()
    {
      return this.markedPositionState;
    }


    /**
     * Returns the positions.
     * 
     * @return The positions.
     * @see #positions
     */
    public HashMap < String, Position > getPositions ()
    {
      return this.positions;
    }


    /**
     * Returns the redoUndoItem.
     * 
     * @return The redoUndoItem.
     * @see #redoUndoItem
     */
    public RedoUndoItem getRedoUndoItem ()
    {
      return this.redoUndoItem;
    }


    /**
     * Returns the removedBlackboxes.
     * 
     * @return The removedBlackboxes.
     * @see #removedBlackboxes
     */
    public ArrayList < BlackBox > getRemovedBlackboxes ()
    {
      return this.removedBlackboxes;
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
     * Returns the stepXGrid.
     * 
     * @return The stepXGrid.
     * @see #stepXGrid
     */
    public XGrid getXGrid ()
    {
      return this.stepXGrid;
    }


    /**
     * Returns the errorCreated.
     * 
     * @return The errorCreated.
     * @see #errorCreated
     */
    public boolean isErrorCreated ()
    {
      return this.errorCreated;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertRegexToMachineDialog.class );


  /**
   * The start x coordinate
   */
  private static double START_X = 50;


  /**
   * The start y coordinate
   */
  private static double START_Y = 100;


  /**
   * The x space between two states
   */
  private static double X_SPACE = 180;


  /**
   * The y space between two states
   */
  private static double Y_SPACE = 50;


  /**
   * / {@link HashMap} for moving states that are above the black box
   */
  private HashMap < RegexNode, HashSet < DefaultStateView > > above = new HashMap < RegexNode, HashSet < DefaultStateView > > ();


  /**
   * The last active {@link RegexNode}. For unmarking.
   */
  private RegexNode activeNode = null;


  /**
   * The actual {@link Step}
   */
  private Step actualStep = null;


  /**
   * The algorithm
   */
  private String algorithm;


  /**
   * The algorithm window
   */
  private TextWindow algorithmWindow;


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * HashMap for the controlled {@link Symbol}s for a
   * {@link DefaultPositionState}
   */
  private HashMap < DefaultPositionState, ArrayList < Symbol > > controlledSymbols = new HashMap < DefaultPositionState, ArrayList < Symbol > > ();


  /**
   * The {@link ConvertMachineTableModel}.
   */
  private ConvertMachineTableModel convertMachineTableModel;


  /**
   * The {@link ConvertRegexType}
   */
  private ConvertRegexType convertType;


  /**
   * The count of states
   */
  private int count = 0;


  /**
   * The {@link DefaultRegex}
   */
  private DefaultRegex defaultRegex;


  /**
   * Flag indicates if the empty state is already created
   */
  private boolean emptyStateCreated = false;


  /**
   * {@link HashMap} for moving states that are after the black box
   */
  private HashMap < RegexNode, HashSet < DefaultStateView > > endings = new HashMap < RegexNode, HashSet < DefaultStateView > > ();


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  /**
   * The {@link EntityType} that should be converted to.
   */
  private EntityType entityType;


  /**
   * The actual follow pos
   */
  private ArrayList < Integer > followPos;


  /**
   * The {@link ConvertRegexToMachineDialogForm}
   */
  private ConvertRegexToMachineDialogForm gui;


  /**
   * The converted {@link JGTIGraph} containing the diagramm.
   */
  private JGTIBlackboxGraph jGTIGraphConverted;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * The converted {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultRegexModel modelOriginal;


  /**
   * The {@link RegexPanel}
   */
  private RegexPanel panel;


  /**
   * The parent {@link JFrame}
   */
  private JFrame parent;


  /**
   * The Position Map containing the position of a State
   */
  private HashMap < String, Position > positionMap;


  /**
   * An {@link ArrayList} of {@link DefaultPositionState}s
   */
  private ArrayList < DefaultPositionState > positionStates;


  /**
   * A {@link HashMap} containing the {@link DefaultStateView} for a
   * {@link DefaultPositionState}
   */
  private HashMap < DefaultPositionState, DefaultStateView > positionStateViewList = new HashMap < DefaultPositionState, DefaultStateView > ();


  /**
   * The {@link RegexNode}
   */
  private RegexNode regexNode;


  /**
   * / {@link HashMap} for moving states that are at the same y coordinate the
   * black box
   */
  private HashMap < RegexNode, HashSet < DefaultStateView > > sameY = new HashMap < RegexNode, HashSet < DefaultStateView > > ();


  /**
   * The state view list containing the start and final {@link DefaultStateView}
   * for a {@link RegexNode}
   */
  private HashMap < RegexNode, ArrayList < DefaultStateView > > stateViewList = new HashMap < RegexNode, ArrayList < DefaultStateView > > ();


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The {@link ConvertMachineTableColumnModel}.
   */
  private ConvertMachineTableColumnModel tableColumnModel = new ConvertMachineTableColumnModel ();


  /**
   * / {@link HashMap} for moving states that are under the black box
   */
  private HashMap < RegexNode, HashSet < DefaultStateView > > under = new HashMap < RegexNode, HashSet < DefaultStateView > > ();


  /**
   * The {@link XGrid}
   */
  private XGrid xGrid = new XGrid ();


  /**
   * Creates new from {@link ConvertRegexToMachineDialog}
   * 
   * @param parent The parent {@link JFrame}
   * @param panel The {@link RegexPanel}
   */
  public ConvertRegexToMachineDialog ( JFrame parent, RegexPanel panel )
  {
    this.parent = parent;
    this.panel = panel;
    this.showErrorState = PreferenceManager.getInstance ().getShowErrorState ();
  }


  /**
   * Adds a outline comment.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  private final void addOutlineComment ( PrettyString prettyString )
  {
    this.convertMachineTableModel.addRow ( prettyString );
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );
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
   * {@inheritDoc}
   * 
   * @see Converter#convert(EntityType, EntityType, boolean, boolean)
   */
  public void convert (
      @SuppressWarnings ( "unused" ) EntityType fromEntityType,
      EntityType toEntityType, @SuppressWarnings ( "unused" ) boolean complete, @SuppressWarnings("unused") boolean cb )
  {
    this.entityType = toEntityType;
    this.gui = new ConvertRegexToMachineDialogForm ( this, this.parent );

    Alphabet a = this.panel.getRegex ().getAlphabet ();
    this.defaultRegex = this.panel.getRegex ().clone ();
    if ( this.entityType.equals ( MachineType.ENFA ) )
    {
      this.convertType = ConvertRegexType.REGEX_TO_ENFA;
      this.defaultRegex.setRegexNode ( this.defaultRegex.getRegexNode ()
          .toCoreSyntax ( false ).clone (), this.defaultRegex.getRegexNode ()
          .toCoreSyntax ( false ).toString () );
      this.regexNode = this.defaultRegex.getRegexNode ();
      this.regexNode.setShowPositions ( false );
      this.modelConverted = new DefaultMachineModel ( new DefaultENFA ( a, a,
          false ) );
      this.gui
          .setTitle ( Messages
              .getString (
                  "ConvertRegexToMachineDialog.Title", Messages.getString ( "ConvertRegexToMachineDialog.ENFA" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    else if ( this.entityType.equals ( MachineType.DFA )
        || this.entityType.equals ( MachineType.NFA ) )
    {
      if ( this.entityType.equals ( MachineType.DFA ) )
      {
        this.convertType = ConvertRegexType.REGEX_TO_DFA;
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA ( a, a,
            false ) );
        this.gui
            .setTitle ( Messages
                .getString (
                    "ConvertRegexToMachineDialog.Title", Messages.getString ( "ConvertRegexToMachineDialog.DFA" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
      }
      else
      {
        this.convertType = ConvertRegexType.REGEX_TO_NFA;
        this.modelConverted = new DefaultMachineModel ( new DefaultNFA ( a, a,
            false ) );
        this.gui
            .setTitle ( Messages
                .getString (
                    "ConvertRegexToMachineDialog.Title", Messages.getString ( "ConvertRegexToMachineDialog.NFA" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
      }
      this.positionStates = new ArrayList < DefaultPositionState > ();

      if ( !this.defaultRegex.getRegexNode ().isInCoreSyntax () )
      {
        this.defaultRegex
            .setRegexNode ( new ConcatenationNode ( this.defaultRegex
                .getRegexNode ().toCoreSyntax ( true ).clone (), new TokenNode (
                "#" ) ), //$NON-NLS-1$
                this.defaultRegex.getRegexNode ().toCoreSyntax ( true )
                    .toString () );
      }
      else
      {
        this.defaultRegex.setRegexNode (
            new ConcatenationNode ( this.defaultRegex.getRegexNode ().clone (),
                new TokenNode ( "#" ) ), this.defaultRegex.getRegexString () ); //$NON-NLS-1$
      }
      this.regexNode = this.defaultRegex.getRegexNode ();
      this.regexNode.setShowPositions ( true );
    }
    else
    {
      throw new RuntimeException ( "unsupported convert to Type" ); //$NON-NLS-1$
    }

    this.modelOriginal = new DefaultRegexModel ( this.defaultRegex );
    this.modelOriginal.initializeGraph ();
    this.modelOriginal.createTree ();

    this.convertMachineTableModel = new ConvertMachineTableModel ();
    this.gui.jGTITableOutline.setModel ( this.convertMachineTableModel );
    this.gui.jGTITableOutline.setColumnModel ( this.tableColumnModel );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();
    if ( this.entityType.equals ( MachineType.DFA )
        || this.entityType.equals ( MachineType.NFA ) )
    {
      this.jGTIGraphOriginal.setMoveable ( false );
      this.jGTIGraphOriginal.getSelectionModel ().setSelectionMode (
          GraphSelectionModel.SINGLE_GRAPH_SELECTION );
      this.jGTIGraphOriginal
          .addGraphSelectionListener ( new GraphSelectionListener ()
          {

            /**
             * {@inheritDoc}
             * 
             * @see GraphSelectionListener#valueChanged(org.jgraph.event.GraphSelectionEvent)
             */
            public void valueChanged (
                @SuppressWarnings ( "unused" ) GraphSelectionEvent e )
            {
              updateRegexNodeInfo ();
            }
          } );
    }
    else
    {
      this.jGTIGraphOriginal.setEnabled ( false );
    }
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );

    this.jGTIGraphConverted = ( JGTIBlackboxGraph ) this.modelConverted
        .getJGTIGraph ();
    this.jGTIGraphConverted.setEnabled ( false );
    this.jGTIGraphConverted.setScale ( JGTIBlackboxGraph.SCALE_FACTOR );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGTIGraphConverted );

    this.positionMap = new HashMap < String, Position > ();

    if ( toEntityType.equals ( MachineType.DFA )
        || toEntityType.equals ( MachineType.NFA ) )
    {
      while ( !this.endReached )
      {
        performNextStep ( false );
      }
      new LayoutManager ( this.modelConverted, null ).doLayout ();
      for ( DefaultStateView current : this.modelConverted.getStateViewList () )
      {
        int yOffset = current.getState ().isLoopTransition () ? StateView.LOOP_TRANSITION_OFFSET
            : 0;
        this.positionMap.put ( current.getState ().getName (), new Position (
            current.getPositionX (), current.getPositionY () + yOffset ) );
      }
      while ( !this.stepItemList.isEmpty () )
      {
        performPreviousStep ( false );
      }
    }

    this.endReached = this.regexNode.isMarked ();
    setStatus ();
    show ();
  }


  /**
   * Returns the convertMachineTableModel.
   * 
   * @return The convertMachineTableModel.
   * @see #convertMachineTableModel
   */
  public ConvertMachineTableModel getConvertMachineTableModel ()
  {
    return this.convertMachineTableModel;
  }


  /**
   * Returns the entityType.
   * 
   * @return The entityType.
   * @see #entityType
   */
  public EntityType getEntityType ()
  {
    return this.entityType;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public ConvertRegexToMachineDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the modelConverted.
   * 
   * @return The modelConverted.
   * @see #modelConverted
   */
  public DefaultMachineModel getModelConverted ()
  {
    return this.modelConverted;
  }


  /**
   * Returns the modelOriginal.
   * 
   * @return The modelOriginal.
   * @see #modelOriginal
   */
  public DefaultRegexModel getModelOriginal ()
  {
    return this.modelOriginal;
  }


  /**
   * Returns the next uncontrolled {@link Symbol} for a given
   * {@link DefaultPositionState}
   * 
   * @param state The {@link DefaultPositionState}
   * @return The next uncontrolled {@link Symbol} for a given
   *         {@link DefaultPositionState}
   */
  private ArrayList < Symbol > getNextUnControlledSymbol (
      DefaultPositionState state )
  {
    ArrayList < Symbol > c = this.controlledSymbols.get ( state );
    ArrayList < Symbol > rest = new ArrayList < Symbol > ();
    rest.addAll ( this.defaultRegex.getAlphabet ().get () );
    rest.removeAll ( c );
    rest.remove ( new DefaultSymbol ( "#" ) ); //$NON-NLS-1$

    ArrayList < Symbol > result = new ArrayList < Symbol > ();
    for ( Symbol a : rest )
    {
      for ( Integer p : state.getPositions () )
      {
        if ( a.getName ().equals (
            this.defaultRegex.symbolAtPosition ( p.intValue () ) ) )
        {
          result.add ( a );
          return result;
        }
      }
    }
    return rest;

  }


  /**
   * Returns the next unmarked PositionState
   * 
   * @return The next unmarked PositionState
   */
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
   * Returns the panel.
   * 
   * @return The panel.
   * @see #panel
   */
  public RegexPanel getRegexPanel ()
  {
    return this.panel;
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
   * Shows or dispose the Algorithm window
   * 
   * @param show Show or dispose
   */
  public final void handleAlgorithmWindowChanged ( boolean show )
  {
    // this.algorithm =
    // Messages.getString("ConvertGrammarDialog.CannotEliminateEntityProductions");
    if ( this.algorithm == null || this.algorithm.length () == 0 )
    {
      TextLoader loader = new TextLoader ();
      this.algorithm = loader.loadAlgorithm ( this.convertType );
    }

    if ( this.algorithmWindow == null )
    {
      this.algorithmWindow = new TextWindow ( this.gui, this.algorithm, true,
          this.gui.jGTIToggleButtonAlgorithm, this.convertType.toString () );
    }

    if ( show )
    {
      this.algorithmWindow.show ();
    }
    else
    {
      this.algorithmWindow.dispose ();
    }
  }


  /**
   * Handles the action on the auto step button.
   */
  public void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    setStatus ();

    startAutoStepTimer ();
  }


  /**
   * Handles the action on the begin step button.
   */
  public void handleBeginStep ()
  {
    logger.debug ( "handleBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.stepItemList.isEmpty () )
    {
      handlePreviousStep ();
    }
  }


  /**
   * Handles the action on the cancel button.
   */
  public void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$

    PreferenceManager.getInstance ().setConvertRegexDialogPreferences (
        this.gui );

    this.gui.dispose ();
  }


  /**
   * Handles the action on the end step button.
   */
  public void handleEndStep ()
  {
    logger.debug ( "handleEndStep", "handle end step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.endReached )
    {
      handleNextStep ();
    }
  }


  /**
   * Handles the action on the next step button.
   */
  public void handleNextStep ()
  {
    logger.debug ( "handleNextStep", "handle next step" ); //$NON-NLS-1$ //$NON-NLS-2$
    performNextStep ( true );
    setStatus ();
  }


  /**
   * Handles the action on the ok button.
   */
  public void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    PreferenceManager.getInstance ().setConvertRegexDialogPreferences (
        this.gui );

    this.gui.setVisible ( false );
    if ( this.entityType.equals ( MachineType.DFA ) )
    {
      while ( !this.stepItemList.isEmpty () )
      {
        performPreviousStep ( false );
      }
    }

    while ( !this.endReached )
    {
      performNextStep ( false );
    }
    if ( this.entityType.equals ( MachineType.ENFA ) )
    {
      String stateName = "z"; //$NON-NLS-1$
      int c = 0;
      for ( DefaultStateView s : this.modelConverted.getStateViewList () )
      {
        try
        {
          s.getState ().setName ( stateName + c++ );
          if ( s.getState () instanceof DefaultBlackBoxState )
          {
            ( ( DefaultBlackBoxState ) s.getState () ).setReady ( true );
          }
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
        }
      }
    }
    this.panel.getMainWindow ().handleNew ( this.modelConverted.getElement (),
        false );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public void handlePreviousStep ()
  {
    performPreviousStep ( true );
  }


  /**
   * Handles the action on the print button.
   */
  public void handlePrint ()
  {
    PrintDialog dialog = new PrintDialog ( this.parent, this );
    dialog.show ();
  }


  /**
   * Handles the action on the stop button.
   */
  public void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    setStatus ();
  }


  /**
   * Performs the next step.
   * 
   * @param manual Indicates if step was made manually
   */
  @SuppressWarnings ( "unchecked" )
  private final void performNextStep ( boolean manual )
  {
    for ( DefaultTransitionView t : this.modelConverted
        .getTransitionViewList () )
    {
      t.getTransition ().setActive ( false );
    }
    if ( this.activeNode != null )
    {
      this.activeNode.setActive ( false );
    }
    ArrayList < String > addedStates = new ArrayList < String > ();
    ArrayList < DefaultStateView > setFinalFalse = new ArrayList < DefaultStateView > ();
    ArrayList < DefaultStateView > setStartFalse = new ArrayList < DefaultStateView > ();
    ArrayList < DefaultTransitionView > addedTransitions = new ArrayList < DefaultTransitionView > ();
    int c = this.count;
    RedoUndoItem redoUndoItem = null;
    boolean errorCreated = false;
    DefaultPositionState markedPositionState = null;
    ArrayList < Symbol > controlledSymbol = null;
    RegexNode node = null;
    RegexNode lastActive = this.activeNode;
    ArrayList < ObjectPair < DefaultTransitionView, ArrayList < Symbol > > > addedSymbolsToTransition = new ArrayList < ObjectPair < DefaultTransitionView, ArrayList < Symbol >> > ();
    ArrayList < DefaultBlackboxView > addedBlackBoxes = new ArrayList < DefaultBlackboxView > ();
    ArrayList < BlackBox > removedBlackBoxes = new ArrayList < BlackBox > ();
    HashMap < String, Position > positions = new HashMap < String, Position > ();
    XGrid xGridClone = new XGrid ();
    xGridClone.setX_positions ( ( HashMap < String, Integer > ) this.xGrid
        .getX_positions ().clone () );
    ArrayList < Integer > lastFollowPos;
    if ( this.followPos != null )
    {
      lastFollowPos = new ArrayList < Integer > ();
      lastFollowPos.addAll ( this.followPos );
    }
    else
    {
      lastFollowPos = null;
    }

    PrettyString pretty = new PrettyString ();
    // ENFA
    if ( this.entityType.equals ( MachineType.ENFA ) )
    {
      node = this.regexNode.getNextNodeForNFA ();
      node.setActive ( true );
      this.activeNode = node;
      for ( DefaultStateView v : this.modelConverted.getStateViewList () )
      {
        positions.put ( v.getState ().getName (), new Position ( v
            .getPositionX (), v.getPositionY () ) );
      }
      // Token
      if ( node instanceof TokenNode )
      {
        this.actualStep = Step.CONVERT_TOKEN;
        TokenNode token = ( TokenNode ) node;

        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepConvertToken", token.toPrettyString () ) ); //$NON-NLS-1$

        try
        {
          DefaultStateView startView;
          DefaultStateView finView;
          if ( this.stateViewList.containsKey ( token ) )
          {
            ArrayList < DefaultStateView > views = this.stateViewList
                .get ( token );

            startView = views.get ( 0 );
            finView = views.get ( 1 );
            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( token );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );
            this.jGTIGraphConverted.removeBlackBox ( token );
          }
          else
          {
            startView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            startView.getState ().setStartState ( true );

            finView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            addedStates.add ( startView.getState ().getName () );
            addedStates.add ( finView.getState ().getName () );
            finView.getState ().setFinalState ( true );

            this.xGrid.getX_positions ().put (
                startView.getState ().getName (), new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( finView.getState ().getName (),
                new Integer ( 1 ) );
          }

          ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
          symbols.add ( new DefaultSymbol ( token.getName () ) );
          Transition t;
          Alphabet a = this.modelOriginal.getRegex ().getAlphabet ();
          t = new DefaultTransition ( a, a, new DefaultWord (),
              new DefaultWord (), startView.getState (), finView.getState (),
              symbols );

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
        catch ( StateException exc )
        {
          exc.printStackTrace ();
        }
      }
      // CharacterClass
      if ( node instanceof CharacterClassNode )
      {
        this.actualStep = Step.CONVERT_CHARCLASS;
        CharacterClassNode charClass = ( CharacterClassNode ) node;

        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepConvertClass", charClass.toPrettyString () ) ); //$NON-NLS-1$

        try
        {
          DefaultStateView startView;
          DefaultStateView finView;
          if ( this.stateViewList.containsKey ( charClass ) )
          {
            ArrayList < DefaultStateView > views = this.stateViewList
                .get ( charClass );

            startView = views.get ( 0 );
            finView = views.get ( 1 );
            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( charClass );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );
            this.jGTIGraphConverted.removeBlackBox ( charClass );
          }
          else
          {
            startView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            startView.getState ().setStartState ( true );

            finView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            addedStates.add ( startView.getState ().getName () );
            addedStates.add ( finView.getState ().getName () );
            finView.getState ().setFinalState ( true );

            this.xGrid.getX_positions ().put (
                startView.getState ().getName (), new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( finView.getState ().getName (),
                new Integer ( 1 ) );
          }

          ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
          symbols.addAll ( charClass.getCharacters () );
          Transition t;
          Alphabet a = this.modelOriginal.getRegex ().getAlphabet ();
          t = new DefaultTransition ( a, a, new DefaultWord (),
              new DefaultWord (), startView.getState (), finView.getState (),
              symbols );

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
        catch ( StateException exc )
        {
          exc.printStackTrace ();
        }
      }
      // Epsilon
      if ( node instanceof EpsilonNode )
      {
        this.actualStep = Step.CONVERT_EPSILON;
        EpsilonNode epsilon = ( EpsilonNode ) node;

        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepConvertEpsilon", epsilon.toPrettyString () ) ); //$NON-NLS-1$

        try
        {
          DefaultStateView startView;
          DefaultStateView finView;
          if ( this.stateViewList.containsKey ( epsilon ) )
          {
            ArrayList < DefaultStateView > views = this.stateViewList
                .get ( epsilon );

            startView = views.get ( 0 );
            finView = views.get ( 1 );
            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( epsilon );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );
            this.jGTIGraphConverted.removeBlackBox ( epsilon );
          }
          else
          {
            startView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            startView.getState ().setStartState ( true );

            finView = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            finView.getState ().setFinalState ( true );
            addedStates.add ( startView.getState ().getName () );
            addedStates.add ( finView.getState ().getName () );

            this.xGrid.getX_positions ().put (
                startView.getState ().getName (), new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( finView.getState ().getName (),
                new Integer ( 1 ) );
          }

          ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
          symbols.add ( new DefaultSymbol () );
          Transition t;
          Alphabet a = this.modelOriginal.getRegex ().getAlphabet ();
          t = new DefaultTransition ( a, a, new DefaultWord (),
              new DefaultWord (), startView.getState (), finView.getState (),
              symbols );

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
        catch ( StateException exc )
        {
          exc.printStackTrace ();
        }
      }
      // Disjunction
      if ( node instanceof DisjunctionNode )
      {
        this.actualStep = Step.CONVERT_DISJUNCTION;
        DisjunctionNode dis = ( DisjunctionNode ) node;
        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepConvertDisjunction", dis.getChildren ().get ( 0 ).toPrettyString (), dis.getChildren ().get ( 1 ).toPrettyString () ) ); //$NON-NLS-1$
        try
        {
          DefaultStateView start;
          DefaultStateView end;
          int startPosition = 0;
          if ( this.stateViewList.containsKey ( dis ) )
          {
            start = this.stateViewList.get ( dis ).get ( 0 );
            end = this.stateViewList.get ( dis ).get ( 1 );

            startPosition = this.xGrid.getX_positions ().get (
                start.getState ().getName () ).intValue ();

            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( dis );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );

            this.jGTIGraphConverted.removeBlackBox ( dis );
          }
          else
          {
            start = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            start.getState ().setStartState ( true );

            end = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            end.getState ().setFinalState ( true );
            addedStates.add ( start.getState ().getName () );
            addedStates.add ( end.getState ().getName () );

            this.xGrid.getX_positions ().put ( start.getState ().getName (),
                new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( end.getState ().getName (),
                new Integer ( 3 ) );
          }
          if ( this.above.containsKey ( dis ) )
          {
            start.moveRelative ( 0, Y_SPACE );
            end.moveRelative ( 0, Y_SPACE );
          }
          if ( this.under.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.under.get ( dis ) )
            {
              v.moveRelative ( 0, 2 * Y_SPACE );
            }
          }

          if ( this.sameY.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.sameY.get ( dis ) )
            {
              v.moveRelative ( 0, Y_SPACE );
            }
          }

          int regex1Width = this.jGTIGraphConverted.getGraphics ()
              .getFontMetrics ().stringWidth (
                  dis.getChildren ().get ( 0 ).toPrettyString ().toString () );
          int regex2Width = this.jGTIGraphConverted.getGraphics ()
              .getFontMetrics ().stringWidth (
                  dis.getChildren ().get ( 1 ).toPrettyString ().toString () );

          double startX = start.getPositionX () + start.getWidth () / 2;
          double startY = start.getPositionY () + start.getHeight () / 2;

          DefaultStateView start1 = this.modelConverted.createStateView (
              startX, startY,
              new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
          DefaultStateView end1 = this.modelConverted.createStateView ( startX,
              startY, new DefaultBlackBoxState ( String.valueOf ( c++ ) ),
              false );
          ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
          views.add ( start1 );
          views.add ( end1 );
          this.stateViewList.put ( dis.getChildren ().get ( 0 ), views );
          DefaultStateView start2 = this.modelConverted.createStateView (
              startX, startY,
              new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
          DefaultStateView end2 = this.modelConverted.createStateView ( startX,
              startY, new DefaultBlackBoxState ( String.valueOf ( c++ ) ),
              false );

          HashSet < DefaultStateView > childOneAbove = new HashSet < DefaultStateView > ();

          if ( this.above.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.above.get ( dis ) )
            {
              if ( v.getPositionY () < start.getPositionY () - Y_SPACE )
              {
                childOneAbove.add ( v );
              }
            }
          }

          this.above.put ( dis.getChildren ().get ( 0 ), childOneAbove );

          HashSet < DefaultStateView > childOneUnder = new HashSet < DefaultStateView > ();

          if ( this.under.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.under.get ( dis ) )
            {
              if ( v.getPositionY () > start.getPositionY () + Y_SPACE )
              {
                childOneUnder.add ( v );
              }
            }
          }

          if ( this.sameY.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.sameY.get ( dis ) )
            {
              childOneUnder.add ( v );
            }
          }
          childOneUnder.add ( start );
          childOneUnder.add ( end );
          childOneUnder.add ( start2 );
          childOneUnder.add ( end2 );
          this.under.put ( dis.getChildren ().get ( 0 ), childOneUnder );

          HashSet < DefaultStateView > childTwoAbove = new HashSet < DefaultStateView > ();

          if ( this.above.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.above.get ( dis ) )
            {
              if ( v.getPositionY () < start.getPositionY () - Y_SPACE )
              {
                childTwoAbove.add ( v );
              }
            }
          }
          if ( this.sameY.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.sameY.get ( dis ) )
            {
              childTwoAbove.add ( v );
            }
          }
          childTwoAbove.add ( start );
          childTwoAbove.add ( end );
          childTwoAbove.add ( start1 );
          childTwoAbove.add ( end1 );
          this.above.put ( dis.getChildren ().get ( 1 ), childTwoAbove );

          HashSet < DefaultStateView > childTwoUnder = new HashSet < DefaultStateView > ();

          if ( this.under.containsKey ( dis ) )
          {
            for ( DefaultStateView v : this.under.get ( dis ) )
            {
              if ( v.getPositionY () > start.getPositionY () + Y_SPACE )
              {
                childTwoUnder.add ( v );
              }
            }
          }
          this.under.put ( dis.getChildren ().get ( 1 ), childTwoUnder );

          addedStates.add ( start1.getState ().getName () );
          addedStates.add ( end1.getState ().getName () );
          addedStates.add ( start2.getState ().getName () );
          addedStates.add ( end2.getState ().getName () );

          views = new ArrayList < DefaultStateView > ();
          views.add ( start2 );
          views.add ( end2 );
          this.stateViewList.put ( dis.getChildren ().get ( 1 ), views );

          this.xGrid.getX_positions ().put ( start1.getState ().getName (),
              new Integer ( startPosition + 1 ) );
          this.xGrid.getX_positions ().put ( start2.getState ().getName (),
              new Integer ( startPosition + 1 ) );
          this.xGrid.getX_positions ().put ( end1.getState ().getName (),
              new Integer ( startPosition + 2 ) );
          this.xGrid.getX_positions ().put ( end2.getState ().getName (),
              new Integer ( startPosition + 2 ) );

          if ( this.stateViewList.containsKey ( dis ) )
          {
            int endPosition = this.xGrid.getX_positions ().get (
                end.getState ().getName () ).intValue ();

            TreeSet < DefaultStateView > keys = new TreeSet < DefaultStateView > ();
            keys.add ( end );

            if ( this.endings.containsKey ( dis ) )
            {
              keys.addAll ( this.endings.get ( dis ) );
            }

            for ( DefaultStateView v : keys )
            {
              if ( this.xGrid.getX_positions ().get ( v.getState ().getName () )
                  .intValue () >= endPosition )
              {
                this.xGrid.moveState ( v, new Integer ( this.xGrid
                    .getX_positions ().get ( v.getState ().getName () )
                    .intValue () + 2 ) );
              }
            }
          }
          start1.moveRelative ( X_SPACE, -Y_SPACE );
          end1.move ( start1.getPositionX (), start1.getPositionY () );
          end1.moveRelative ( 2 * JGTIBlackboxGraph.X_SPACE + regex1Width
              + end1.getWidth (), 0 );
          start2.moveRelative ( X_SPACE, Y_SPACE );
          end2.move ( start2.getPositionX (), start2.getPositionY () );
          end2.moveRelative ( 2 * JGTIBlackboxGraph.X_SPACE + regex2Width
              + end2.getWidth (), 0 );

          end.move ( end2.getPositionX (), end.getPositionY () );
          end.moveRelative ( X_SPACE, 0 );

          HashSet < DefaultStateView > disEndings = new HashSet < DefaultStateView > ();

          disEndings.add ( end );
          if ( this.endings.containsKey ( dis ) )
          {
            disEndings.addAll ( this.endings.get ( dis ) );
          }

          this.endings.put ( dis.getChildren ().get ( 0 ), disEndings );
          this.endings.put ( dis.getChildren ().get ( 1 ), disEndings );

          addedTransitions.add ( this.modelConverted.createTransitionView (
              new DefaultTransition ( this.defaultRegex.getAlphabet (),
                  this.defaultRegex.getAlphabet (), new DefaultWord (),
                  new DefaultWord (), start.getState (), start1.getState (),
                  new DefaultSymbol () ), start, start1, true, false, true ) );
          addedTransitions.add ( this.modelConverted.createTransitionView (
              new DefaultTransition ( this.defaultRegex.getAlphabet (),
                  this.defaultRegex.getAlphabet (), new DefaultWord (),
                  new DefaultWord (), start.getState (), start2.getState (),
                  new DefaultSymbol () ), start, start2, true, false, true ) );
          addedTransitions.add ( this.modelConverted.createTransitionView (
              new DefaultTransition ( this.defaultRegex.getAlphabet (),
                  this.defaultRegex.getAlphabet (), new DefaultWord (),
                  new DefaultWord (), end1.getState (), end.getState (),
                  new DefaultSymbol () ), end1, end, true, false, true ) );
          addedTransitions.add ( this.modelConverted.createTransitionView (
              new DefaultTransition ( this.defaultRegex.getAlphabet (),
                  this.defaultRegex.getAlphabet (), new DefaultWord (),
                  new DefaultWord (), end2.getState (), end.getState (),
                  new DefaultSymbol () ), end2, end, true, false, true ) );

          DefaultBlackboxView b1 = new DefaultBlackboxView ( start1, end1, dis
              .getChildren ().get ( 0 ) );
          DefaultBlackboxView b2 = new DefaultBlackboxView ( start2, end2, dis
              .getChildren ().get ( 1 ) );
          addedBlackBoxes.add ( b1 );
          addedBlackBoxes.add ( b2 );

        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
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
      // Concatenation
      if ( node instanceof ConcatenationNode )
      {
        this.actualStep = Step.CONVERT_CONCAT;
        ConcatenationNode con = ( ConcatenationNode ) node;
        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepConvertConcatenation", con.getChildren ().get ( 0 ).toPrettyString (), con.getChildren ().get ( 1 ).toPrettyString () ) ); //$NON-NLS-1$
        try
        {
          int regex1Width = this.jGTIGraphConverted.getGraphics ()
              .getFontMetrics ().stringWidth (
                  con.getChildren ().get ( 0 ).toPrettyString ().toString () );
          int regex2Width = this.jGTIGraphConverted.getGraphics ()
              .getFontMetrics ().stringWidth (
                  con.getChildren ().get ( 1 ).toPrettyString ().toString () );

          DefaultStateView start;
          DefaultStateView end;
          int startPosition = 0;
          if ( this.stateViewList.containsKey ( con ) )
          {
            start = this.stateViewList.get ( con ).get ( 0 );
            end = this.stateViewList.get ( con ).get ( 1 );

            startPosition = this.xGrid.getX_positions ().get (
                start.getState ().getName () ).intValue ();

            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( con );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );
            this.jGTIGraphConverted.removeBlackBox ( con );
          }
          else
          {
            start = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            start.getState ().setStartState ( true );

            end = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            end.getState ().setFinalState ( true );
            addedStates.add ( start.getState ().getName () );
            addedStates.add ( end.getState ().getName () );

            this.xGrid.getX_positions ().put ( start.getState ().getName (),
                new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( end.getState ().getName (),
                new Integer ( 2 ) );
          }
          double startX = start.getPositionX ();
          double startY = start.getPositionY () + start.getHeight () / 2;
          DefaultStateView middle = this.modelConverted.createStateView (
              startX, startY,
              new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );

          addedStates.add ( middle.getState ().getName () );
          this.xGrid.getX_positions ().put ( middle.getState ().getName (),
              new Integer ( startPosition + 1 ) );

          if ( this.stateViewList.containsKey ( con ) )
          {
            int endPosition = this.xGrid.getX_positions ().get (
                end.getState ().getName () ).intValue ();
            TreeSet < DefaultStateView > keys = new TreeSet < DefaultStateView > ();
            if ( this.endings.containsKey ( con ) )
            {
              keys.addAll ( this.endings.get ( con ) );
            }
            keys.add ( end );
            for ( DefaultStateView v : keys )
            {
              if ( this.xGrid.getX_positions ().get ( v.getState ().getName () )
                  .intValue () >= endPosition )
              {
                this.xGrid.moveState ( v, new Integer ( this.xGrid
                    .getX_positions ().get ( v.getState ().getName () )
                    .intValue () + 1 ) );
              }
            }
          }
          HashSet < DefaultStateView > childsAbove = new HashSet < DefaultStateView > ();

          if ( this.above.containsKey ( con ) )
          {
            childsAbove.addAll ( this.above.get ( con ) );
          }

          this.above.put ( con.getChildren ().get ( 0 ), childsAbove );
          this.above.put ( con.getChildren ().get ( 1 ),
              ( HashSet < DefaultStateView > ) childsAbove.clone () );

          HashSet < DefaultStateView > childsUnder = new HashSet < DefaultStateView > ();

          if ( this.under.containsKey ( con ) )
          {
            childsUnder.addAll ( this.under.get ( con ) );
          }

          this.under.put ( con.getChildren ().get ( 0 ), childsUnder );
          this.under.put ( con.getChildren ().get ( 1 ),
              ( HashSet < DefaultStateView > ) childsUnder.clone () );

          HashSet < DefaultStateView > childOneSameY = new HashSet < DefaultStateView > ();
          if ( this.sameY.containsKey ( con ) )
          {
            childOneSameY.addAll ( this.sameY.get ( con ) );
          }
          childOneSameY.add ( end );
          this.sameY.put ( con.getChildren ().get ( 0 ), childOneSameY );

          HashSet < DefaultStateView > childTwoSameY = new HashSet < DefaultStateView > ();
          if ( this.sameY.containsKey ( con ) )
          {
            childTwoSameY.addAll ( this.sameY.get ( con ) );
          }
          childTwoSameY.add ( start );
          this.sameY.put ( con.getChildren ().get ( 1 ), childTwoSameY );

          ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
          views.add ( start );
          views.add ( middle );
          this.stateViewList.put ( con.getChildren ().get ( 0 ), views );
          views = new ArrayList < DefaultStateView > ();
          views.add ( middle );
          views.add ( end );
          this.stateViewList.put ( con.getChildren ().get ( 1 ), views );

          middle.moveRelative ( 2 * JGTIBlackboxGraph.X_SPACE + regex1Width
              + middle.getWidth (), 0 );
          end.move ( middle.getPositionX (), end.getPositionY () );
          end.moveRelative ( 4 * JGTIBlackboxGraph.X_SPACE + regex1Width
              + middle.getWidth () + regex2Width, 0 );
          HashSet < DefaultStateView > conEndings = new HashSet < DefaultStateView > ();
          conEndings.add ( end );
          if ( this.endings.containsKey ( con ) )
          {
            conEndings.addAll ( this.endings.get ( con ) );
          }
          this.endings.put ( con.getChildren ().get ( 0 ), conEndings );
          this.endings.put ( con.getChildren ().get ( 1 ),
              ( HashSet < DefaultStateView > ) conEndings.clone () );

          DefaultBlackboxView b1 = new DefaultBlackboxView ( start, middle, con
              .getChildren ().get ( 0 ) );
          DefaultBlackboxView b2 = new DefaultBlackboxView ( middle, end, con
              .getChildren ().get ( 1 ) );
          addedBlackBoxes.add ( b1 );
          addedBlackBoxes.add ( b2 );
        }
        catch ( StateException e )
        {
          e.printStackTrace ();
        }

      }
      // Kleene Node
      if ( node instanceof KleeneNode )
      {
        this.actualStep = Step.CONVERT_KLEENE;
        KleeneNode k = ( KleeneNode ) node;

        try
        {
          int regexWidth = this.jGTIGraphConverted.getGraphics ()
              .getFontMetrics ().stringWidth (
                  k.getChildren ().get ( 0 ).toPrettyString ().toString () );

          DefaultStateView start;
          DefaultStateView end;
          int startPosition = 0;
          if ( this.stateViewList.containsKey ( k ) )
          {
            start = this.stateViewList.get ( k ).get ( 0 );
            end = this.stateViewList.get ( k ).get ( 1 );

            startPosition = this.xGrid.getX_positions ().get (
                start.getState ().getName () ).intValue ();

            DefaultBlackboxView bb = this.jGTIGraphConverted
                .getBlackboxViewForRegexNode ( k );
            removedBlackBoxes.add ( new BlackBox ( bb.getStartState ()
                .getState ().getName (), bb.getFinalState ().getState ()
                .getName (), bb.getContent () ) );
            this.jGTIGraphConverted.removeBlackBox ( k );
          }
          else
          {
            start = this.modelConverted.createStateView ( START_X, START_Y,
                new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
            start.getState ().setStartState ( true );

            end = this.modelConverted.createStateView ( START_X + X_SPACE,
                START_Y, new DefaultBlackBoxState ( String.valueOf ( c++ ) ),
                false );
            end.getState ().setFinalState ( true );
            addedStates.add ( start.getState ().getName () );
            addedStates.add ( end.getState ().getName () );

            this.xGrid.getX_positions ().put ( start.getState ().getName (),
                new Integer ( 0 ) );
            this.xGrid.getX_positions ().put ( end.getState ().getName (),
                new Integer ( 3 ) );
          }
          pretty
              .add ( Messages
                  .getPrettyString (
                      "ConvertRegexToMachineDialog.StepConvertKleene", k.toPrettyString () ) ); //$NON-NLS-1$

          double startX = start.getPositionX () + start.getWidth ();
          double startY = start.getPositionY () + start.getHeight () / 2;

          DefaultStateView startView = this.modelConverted.createStateView (
              startX, startY,
              new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
          DefaultStateView finView = this.modelConverted.createStateView (
              startX, startY,
              new DefaultBlackBoxState ( String.valueOf ( c++ ) ), false );
          addedBlackBoxes.add ( new DefaultBlackboxView ( startView, finView, k
              .getChildren ().get ( 0 ) ) );

          addedStates.add ( startView.getState ().getName () );
          addedStates.add ( finView.getState ().getName () );

          this.xGrid.getX_positions ().put ( startView.getState ().getName (),
              new Integer ( startPosition + 1 ) );
          this.xGrid.getX_positions ().put ( finView.getState ().getName (),
              new Integer ( startPosition + 2 ) );
          if ( this.stateViewList.containsKey ( k ) )
          {
            int endPosition = this.xGrid.getX_positions ().get (
                end.getState ().getName () ).intValue ();
            TreeSet < DefaultStateView > keys = new TreeSet < DefaultStateView > ();
            keys.add ( end );
            if ( this.endings.containsKey ( k ) )
            {
              keys.addAll ( this.endings.get ( k ) );
            }
            for ( DefaultStateView v : keys )
            {
              if ( this.xGrid.getX_positions ().get ( v.getState ().getName () )
                  .intValue () >= endPosition )
              {
                this.xGrid.moveState ( v, new Integer ( this.xGrid
                    .getX_positions ().get ( v.getState ().getName () )
                    .intValue () + 2 ) );
              }
            }
          }

          HashSet < DefaultStateView > kEndings = new HashSet < DefaultStateView > ();
          kEndings.add ( end );
          if ( this.endings.containsKey ( k ) )
          {
            kEndings.addAll ( this.endings.get ( k ) );
          }
          this.endings.put ( k.getChildren ().get ( 0 ), kEndings );

          HashSet < DefaultStateView > childsAbove = new HashSet < DefaultStateView > ();

          if ( this.above.containsKey ( k ) )
          {
            childsAbove.addAll ( this.above.get ( k ) );
          }

          this.above.put ( k.getChildren ().get ( 0 ), childsAbove );

          HashSet < DefaultStateView > childsUnder = new HashSet < DefaultStateView > ();

          if ( this.under.containsKey ( k ) )
          {
            childsUnder.addAll ( this.under.get ( k ) );
          }

          this.under.put ( k.getChildren ().get ( 0 ), childsUnder );

          HashSet < DefaultStateView > childOneSameY = new HashSet < DefaultStateView > ();
          if ( this.sameY.containsKey ( k ) )
          {
            childOneSameY.addAll ( this.sameY.get ( k ) );
          }
          childOneSameY.add ( start );
          childOneSameY.add ( end );
          this.sameY.put ( k.getChildren ().get ( 0 ), childOneSameY );

          startView.moveRelative ( 50, 15 );
          finView.moveRelative ( 50 + 2 * JGTIBlackboxGraph.X_SPACE
              + startView.getWidth () + regexWidth, 15 );

          double width = regexWidth + 2 * JGTIBlackboxGraph.X_SPACE
              + startView.getWidth () + finView.getWidth ();

          end.moveRelative ( width + 2 * X_SPACE, 0 );

          // From start to begin of N(s)
          Transition t = new DefaultTransition ();
          t.setStateBegin ( start.getState () );
          t.setStateEnd ( startView.getState () );
          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              start, startView, true, false, true ) );

          // From start to final
          t = new DefaultTransition ();
          t.setStateBegin ( start.getState () );
          t.setStateEnd ( end.getState () );
          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              start, end, true, false, true ) );

          // From end to begin of N(s)
          t = new DefaultTransition ();
          t.setStateBegin ( finView.getState () );
          t.setStateEnd ( startView.getState () );
          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              finView, startView, true, false, true ) );

          // From end to final
          t = new DefaultTransition ();
          t.setStateBegin ( finView.getState () );
          t.setStateEnd ( end.getState () );
          addedTransitions.add ( this.modelConverted.createTransitionView ( t,
              end, finView, true, false, true ) );

          ArrayList < DefaultStateView > views = new ArrayList < DefaultStateView > ();
          views.add ( startView );
          views.add ( finView );
          this.stateViewList.put ( k.getChildren ().get ( 0 ), views );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
        }
      }

      // Reset x positions
      for ( DefaultStateView v : this.modelConverted.getStateViewList () )
      {
        v.move ( START_X, v.getPositionY () );
      }

      // Order x positions in the grid
      for ( String s : this.xGrid.getX_positions ().keySet () )
      {
        DefaultStateView v = this.modelConverted.getStateViewForName ( s );
        if ( v != null )
        {
          if ( this.xGrid.getX_positions ().get ( v.getState ().getName () )
              .intValue () == 0 )
          {
            v.moveRelative ( -50, 0 );
          }
          else
          {
            v.moveRelative ( ( this.xGrid.getX_positions ().get (
                v.getState ().getName () ).intValue () )
                * X_SPACE, 0 );
          }
        }
      }

      // Add Blackboxes
      for ( DefaultBlackboxView v : addedBlackBoxes )
      {
        this.jGTIGraphConverted.addBlackBox ( v );
      }

      this.endReached = this.regexNode.isMarkedAll ();

      if ( manual )
      {
        setStatus ();
        updateGraph ();
      }
    }
    // DFA
    else if ( this.entityType.equals ( MachineType.DFA ) )
    {
      this.actualStep = Step.INITIAL;
      // Initial Step
      boolean finalStep = false;
      if ( this.positionStates.isEmpty () )
      {
        HashSet < Integer > pos = new HashSet < Integer > ();
        String name = ""; //$NON-NLS-1$
        ArrayList < LeafNode > firstPos = this.defaultRegex.getRegexNode ()
            .firstPos ();
        boolean first = true;
        for ( LeafNode tmpNode : firstPos )
        {
          pos.add ( new Integer ( tmpNode.getPosition () ) );
          if ( !first )
          {
            name += ","; //$NON-NLS-1$
          }
          else
          {
            first = false;
          }
          name += tmpNode.getPosition ();
        }
        DefaultPositionState state;
        try
        {
          state = new DefaultPositionState ( name, pos, true );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
          return;
        }
        this.positionStates.add ( state );
        state.setStartState ( true );
        DefaultStateView view = this.modelConverted.createStateView ( 0, 0,
            state, false );
        this.positionStateViewList.put ( state, view );
        addedStates.add ( state.getName () );
        Position p = getPosition ( state );
        if ( p != null )
        {
          view.move ( p.getX (), p.getY () );
        }
        pretty
            .add ( Messages
                .getPrettyString (
                    "ConvertRegexToMachineDialog.StepCreateStartState", this.regexNode.toPrettyString (), new PrettyString ( new PrettyToken ( name, Style.NONE ) ) ) ); //$NON-NLS-1$

      }
      // Now the algorithm goes
      else
      {
        DefaultPositionState positionState = getNextUnmarkedState ();
        if ( positionState != null )
        {
          if ( !this.controlledSymbols.containsKey ( positionState ) )
          {
            this.controlledSymbols.put ( positionState,
                new ArrayList < Symbol > () );
          }
          markedPositionState = positionState;
          ArrayList < Symbol > a = getNextUnControlledSymbol ( positionState );

          if ( a != null )
          {
            controlledSymbol = new ArrayList < Symbol > ();
            controlledSymbol.addAll ( a );
          }

          if ( a != null && !a.isEmpty () && controlledSymbol != null )
          {
            this.controlledSymbols.get ( positionState ).addAll ( a );
            DefaultPositionState uState;
            if ( a.size () == 1 )
            {
              if ( manual && !this.showErrorState )
              {
                ArrayList < Symbol > next = getNextUnControlledSymbol ( positionState );
                if ( next.size () > 1 )
                {
                  this.controlledSymbols.get ( positionState ).addAll ( next );
                  controlledSymbol.addAll ( next );
                }
              }
              Symbol s = a.get ( 0 );
              HashSet < Integer > u = new HashSet < Integer > ();
              for ( Integer p : positionState.getPositions () )
              {
                if ( s.getName ().equals (
                    this.defaultRegex.symbolAtPosition ( p.intValue () ) ) )
                {
                  for ( Integer n : this.defaultRegex
                      .followPos ( p.intValue () ) )
                  {
                    u.add ( n );
                  }
                }
              }
              String name = ""; //$NON-NLS-1$
              boolean first = true;
              for ( Integer i : u )
              {
                if ( !first )
                {
                  name += ","; //$NON-NLS-1$
                }
                else
                {
                  first = false;
                }
                name += i;
              }
              try
              {
                uState = new DefaultPositionState ( name, u, true );
              }
              catch ( StateException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
                return;
              }

              if ( !u.isEmpty () )
              {
                if ( !this.positionStates.contains ( uState ) )
                {
                  this.positionStates.add ( uState );
                  DefaultStateView view = this.modelConverted.createStateView (
                      0, 0, uState, false );
                  this.positionStateViewList.put ( uState, view );
                  addedStates.add ( uState.getName () );
                  Position p = getPosition ( uState );
                  if ( p != null )
                  {
                    view.move ( p.getX (), p.getY () );
                  }
                }

              }
            }
            else
            {
              try
              {
                uState = new DefaultPositionState ( "\u2205", //$NON-NLS-1$
                    new HashSet < Integer > (), false );
              }
              catch ( StateException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
                return;
              }
              if ( !this.emptyStateCreated )
              {
                errorCreated = true;
                this.positionStates.add ( uState );
                uState.mark ();
                DefaultStateView view = this.modelConverted.createStateView (
                    0, 0, uState, false );
                this.positionStateViewList.put ( uState, view );
                this.emptyStateCreated = true;
                addedStates.add ( uState.getName () );
                Position p = getPosition ( uState );
                if ( p != null )
                {
                  view.move ( p.getX (), p.getY () );
                }
                try
                {
                  addedTransitions
                      .add ( this.modelConverted
                          .createTransitionView (
                              new DefaultTransition ( this.defaultRegex
                                  .getAlphabet (), this.defaultRegex
                                  .getAlphabet (), new DefaultWord (),
                                  new DefaultWord (), uState, uState,
                                  this.defaultRegex.getAlphabet ().get () ),
                              this.modelConverted
                                  .getStateViewForState ( uState ),
                              this.modelConverted
                                  .getStateViewForState ( uState ), true,
                              false, true ) );
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

            if ( this.positionStateViewList.get ( uState ) != null
                && this.positionStateViewList.get ( positionState ) != null )
            {
              ArrayList < Symbol > s = new ArrayList < Symbol > ();
              s.addAll ( a );
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
                  Transition t = new DefaultTransition ( this.defaultRegex
                      .getAlphabet (), this.defaultRegex.getAlphabet (),
                      new DefaultWord (), new DefaultWord (),
                      this.positionStateViewList.get ( positionState )
                          .getState (), this.positionStateViewList
                          .get ( uState ).getState (), a );
                  addedTransitions.add ( this.modelConverted
                      .createTransitionView ( t, this.positionStateViewList
                          .get ( positionState ), this.positionStateViewList
                          .get ( uState ), true, false, true ) );
                  if ( a.size () == 1 )
                  {

                    pretty.add ( Messages.getPrettyString (
                        "ConvertRegexToMachineDialog.StepTransitionForSymbol", //$NON-NLS-1$
                        a.get ( 0 ).toPrettyString () ) );
                  }
                  else
                  {
                    pretty
                        .add ( Messages
                            .getPrettyString ( "ConvertRegexToMachineDialog.StepTransitionToError" ) ); //$NON-NLS-1$
                  }

                }
                else
                {
                  PrettyString symbolString = new PrettyString ();
                  boolean first = true;
                  for ( Symbol sym : a )
                  {
                    if ( !first )
                    {
                      symbolString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
                    }
                    first = false;
                    symbolString.add ( sym.toPrettyString () );
                  }
                  pretty
                      .add ( Messages
                          .getPrettyString (
                              "ConvertRegexToMachineDialog.StepSymbolToTransition", symbolString, old.getTransition ().toPrettyString () ) ); //$NON-NLS-1$
                  old.getTransition ().add ( a );
                  addedSymbolsToTransition
                      .add ( new ObjectPair < DefaultTransitionView, ArrayList < Symbol > > (
                          old, a ) );
                }
              }
              catch ( TransitionSymbolNotInAlphabetException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
                return;
              }
              catch ( TransitionSymbolOnlyOneTimeException exc )
              {
                exc.printStackTrace ();
                System.exit ( 1 );
                return;
              }
            }

          }
          else
          {
            positionState.mark ();
            pretty
                .add ( Messages
                    .getPrettyString (
                        "ConvertRegexToMachineDialog.StepMarkState", positionState.toPrettyString () ) ); //$NON-NLS-1$
            DefaultPositionState nextState = getNextUnmarkedState ();
            if ( nextState != null && manual && !this.showErrorState )
            {
              ArrayList < Symbol > next = getNextUnControlledSymbol ( nextState );
              if ( next.size () > 1 )
              {
                this.controlledSymbols.get ( nextState ).addAll ( next );
                if ( controlledSymbol == null )
                {
                  controlledSymbol = new ArrayList < Symbol > ();
                  controlledSymbol.addAll ( next );
                }
                else
                {
                  controlledSymbol.addAll ( next );
                }
              }
            }
          }
        }
        else
        {
          finalStep = true;
          LeafNode end = this.regexNode.lastPos ().get ( 0 );
          pretty
              .add ( Messages
                  .getPrettyString (
                      "ConvertRegexToMachineDialog.StepMarkFinalState", new PrettyString ( new PrettyToken ( String.valueOf ( end.getPosition () ), Style.REGEX_POSITION ) ) ) ); //$NON-NLS-1$
          for ( DefaultPositionState uState : this.positionStates )
          {
            if ( uState.getPositions ().contains (
                new Integer ( end.getPosition () ) ) )
            {
              uState.setFinalState ( true );
              setFinalFalse.add ( this.modelConverted
                  .getStateViewForState ( uState ) );
            }
          }
        }
      }

      this.endReached = finalStep;

      if ( manual )
      {
        setStatus ();
        updateGraph ();
      }
    }

    // NFA
    else if ( this.entityType.equals ( MachineType.NFA ) )
    {
      this.actualStep = Step.INITIAL;
      // Initial Step
      if ( this.positionStates.isEmpty () )
      {
        ArrayList < LeafNode > firstPos = this.defaultRegex.getRegexNode ()
            .firstPos ();
        for ( LeafNode tmpNode : firstPos )
        {
          HashSet < Integer > pos = new HashSet < Integer > ();
          String name = ""; //$NON-NLS-1$

          pos.add ( new Integer ( tmpNode.getPosition () ) );
          name += tmpNode.getPosition ();
          DefaultPositionState state;
          try
          {
            state = new DefaultPositionState ( name, pos, false );
          }
          catch ( StateException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
            return;
          }
          this.positionStates.add ( state );
          state.setStartState ( true );
          DefaultStateView view = this.modelConverted.createStateView ( 0, 0,
              state, false );
          this.positionStateViewList.put ( state, view );
          addedStates.add ( state.getName () );
          Position p = getPosition ( state );
          if ( p != null )
          {
            view.move ( p.getX (), p.getY () );
          }
        }
        if ( firstPos.size () == 1 )
        {
          pretty.add ( Messages.getPrettyString (
              "ConvertRegexToMachineDialog.StepCreateStartState", //$NON-NLS-1$
              this.regexNode.toPrettyString (), new PrettyString (
                  new PrettyToken ( String.valueOf ( firstPos.get ( 0 )
                      .getPosition () ), Style.REGEX_POSITION ) ) ) );
        }
        else
        {
          PrettyString s = new PrettyString ();
          boolean first = true;
          for ( LeafNode n : firstPos )
          {
            if ( !first )
            {
              s.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
            }
            s.add ( new PrettyToken ( String.valueOf ( n.getPosition () ),
                Style.REGEX_POSITION ) );
            first = false;
          }
          pretty
              .add ( Messages
                  .getPrettyString (
                      "ConvertRegexToMachineDialog.StepCreateStartStates", this.regexNode.toPrettyString (), s ) ); //$NON-NLS-1$
        }
      }
      else
      {
        DefaultPositionState positionState = getNextUnmarkedState ();
        if ( positionState != null )
        {
          Integer actualPosition = positionState.getPosition ();
          if ( this.followPos == null )
          {
            this.followPos = new ArrayList < Integer > ();
            this.followPos.addAll ( this.defaultRegex
                .followPos ( actualPosition.intValue () ) );
          }
          if ( !this.followPos.isEmpty () )
          {
            Integer i = this.followPos.remove ( 0 );
            HashSet < Integer > uPos = new HashSet < Integer > ();
            uPos.add ( i );
            DefaultPositionState uState = null;
            try
            {
              uState = new DefaultPositionState ( i.toString (), uPos, false );

              if ( !this.positionStates.contains ( uState ) )
              {
                this.positionStates.add ( uState );
                DefaultStateView view = this.modelConverted.createStateView (
                    0, 0, uState, false );
                this.positionStateViewList.put ( uState, view );
                addedStates.add ( uState.getName () );
                Position p = getPosition ( uState );
                if ( p != null )
                {
                  view.move ( p.getX (), p.getY () );
                }
              }
              Symbol s = new DefaultSymbol ( this.defaultRegex
                  .symbolAtPosition ( actualPosition.intValue () ) );
              Transition t = new DefaultTransition ( this.defaultRegex
                  .getAlphabet (), this.defaultRegex.getAlphabet (),
                  new DefaultWord (), new DefaultWord (),
                  this.positionStateViewList.get ( positionState ).getState (),
                  this.positionStateViewList.get ( uState ).getState (), s );
              addedTransitions
                  .add ( this.modelConverted.createTransitionView ( t,
                      this.positionStateViewList.get ( positionState ),
                      this.positionStateViewList.get ( uState ), true, false,
                      true ) );
              pretty
                  .add ( Messages
                      .getPrettyString (
                          "ConvertRegexToMachineDialog.StepTransitionFromS0toS1forSymbol", //$NON-NLS-1$
                          positionState.toPrettyString (), uState
                              .toPrettyString (), s.toPrettyString () ) );

            }
            catch ( StateException exc )
            {
              exc.printStackTrace ();
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
          else
          {
            this.followPos = null;
            markedPositionState = positionState;
            positionState.mark ();
            pretty.add ( Messages.getPrettyString (
                "ConvertRegexToMachineDialog.StepMarkState", positionState //$NON-NLS-1$
                    .toPrettyString () ) );
          }
        }
        else
        {
          LeafNode end = this.regexNode.lastPos ().get ( 0 );
          for ( DefaultPositionState uState : this.positionStates )
          {
            if ( uState.getPositions ().contains (
                new Integer ( end.getPosition () ) ) )
            {
              uState.setFinalState ( true );
              setFinalFalse.add ( this.modelConverted
                  .getStateViewForState ( uState ) );
            }
          }
          this.followPos = null;
          this.endReached = true;
          pretty.add ( Messages.getPrettyString (
              "ConvertRegexToMachineDialog.StepMarkFinalState", //$NON-NLS-1$
              new PrettyString ( new PrettyToken ( String.valueOf ( end
                  .getPosition () ), Style.REGEX_POSITION ) ) ) );
        }
      }

      if ( manual )
      {
        setStatus ();
        updateGraph ();
      }
    }
    for ( DefaultTransitionView v : addedTransitions )
    {
      v.getTransition ().setActive ( true );
    }
    addOutlineComment ( pretty );
    this.stepItemList.add ( new StepItem ( this.actualStep, addedStates,
        redoUndoItem, addedTransitions, setStartFalse, setFinalFalse,
        this.count, lastActive, errorCreated, markedPositionState,
        controlledSymbol, addedSymbolsToTransition, removedBlackBoxes,
        addedBlackBoxes, positions, xGridClone, lastFollowPos ) );
    this.count = c;
  }


  /**
   * Performs previous step
   * 
   * @param manual Indicates if step was made manually
   */
  private void performPreviousStep ( boolean manual )
  {
    this.endReached = false;
    StepItem stepItem = this.stepItemList
        .remove ( this.stepItemList.size () - 1 );

    if ( this.activeNode != null )
    {
      this.activeNode.setActive ( false );
    }

    for ( String name : stepItem.getAddedStates () )
    {
      for ( int i = 0 ; i < this.modelConverted.getStateViewList ().size () ; i++ )
      {
        if ( this.modelConverted.getStateViewList ().get ( i ).getState ()
            .getName ().equals ( name ) )
        {
          if ( this.entityType.equals ( MachineType.DFA )
              || this.entityType.equals ( MachineType.NFA ) )
          {
            this.positionStates.remove ( this.modelConverted
                .getStateViewList ().get ( i ).getState () );
          }
          this.modelConverted.removeState ( this.modelConverted
              .getStateViewList ().get ( i ), false );
        }
      }
    }

    for ( DefaultStateView view : stepItem.getSetFinalFalse () )
    {
      view.getState ().setFinalState (
          ( this.entityType.equals ( MachineType.ENFA ) ) );
    }
    for ( DefaultStateView view : stepItem.getSetStartFalse () )
    {
      view.getState ().setStartState ( true );
    }
    for ( DefaultTransitionView view : stepItem.getAddedTransitions () )
    {
      this.modelConverted.removeTransition ( view, false );
    }
    if ( stepItem.isErrorCreated () )
    {
      this.emptyStateCreated = false;
    }
    DefaultPositionState posState = stepItem.getMarkedPositionState ();
    if ( posState != null )
    {
      posState.unMark ();
    }
    ArrayList < Symbol > s = stepItem.getControlledSymbol ();
    if ( s != null )
    {
      this.controlledSymbols.get ( posState ).removeAll ( s );
    }
    for ( ObjectPair < DefaultTransitionView, ArrayList < Symbol > > pair : stepItem
        .getAddedSymbolsToTransition () )
    {
      pair.getFirst ().getTransition ().remove ( pair.getSecond () );
    }

    this.actualStep = stepItem.getActiveStep ();

    if ( this.activeNode != null )
    {
      this.activeNode.unmark ();
      this.activeNode.setActive ( false );
    }
    RegexNode actNode = stepItem.getActNode ();
    if ( actNode != null )
    {
      actNode.setActive ( true );
    }
    this.activeNode = actNode;
    for ( DefaultStateView v : this.modelConverted.getStateViewList () )
    {
      if ( stepItem.getPositions ().containsKey ( v.getState ().getName () ) )
      {
        Position p = stepItem.getPositions ().get ( v.getState ().getName () );
        v.move ( p.getX (), p.getY () );
      }
    }

    for ( DefaultBlackboxView v : stepItem.getAddedBlackboxes () )
    {
      this.jGTIGraphConverted.removeBlackBox ( v );
    }
    for ( BlackBox bb : stepItem.getRemovedBlackboxes () )
    {
      DefaultStateView start = null;
      DefaultStateView end = null;
      for ( DefaultStateView v : this.modelConverted.getStateViewList () )
      {
        if ( v.getState ().getName ().equals ( bb.getStart () ) )
        {
          start = v;
        }
        else if ( v.getState ().getName ().equals ( bb.getEnd () ) )
        {
          end = v;
        }
      }

      this.jGTIGraphConverted.addBlackBox ( new DefaultBlackboxView ( start,
          end, bb.getContent () ) );
    }
    this.xGrid = stepItem.getXGrid ();

    // outline
    this.convertMachineTableModel.removeLastRow ();
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );
    if ( !this.stepItemList.isEmpty () )
    {
      StepItem it = this.stepItemList.get ( this.stepItemList.size () - 1 );
      for ( DefaultTransitionView v : it.getAddedTransitions () )
      {
        v.getTransition ().setActive ( true );
      }

      this.count = stepItem.getActCount ();
    }
    else
    {
      this.count = 0;
    }
    this.followPos = stepItem.getFollowPos ();
    if ( manual )
    {
      setStatus ();
      updateGraph ();
    }
  }


  /**
   * The show error state flag
   */
  private boolean showErrorState = false;


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
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert regex dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertRegexDialogBounds ();
    if ( ( rect.x == PreferenceManager.DEFAULT_CONVERT_REGEX_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_CONVERT_REGEX_DIALOG_POSITION_Y ) )
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


  /**
   * Updates the RegexNode infos
   */
  protected void updateRegexNodeInfo ()
  {
    if ( this.jGTIGraphOriginal.getSelectionCell () instanceof DefaultNodeView )
    {
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setEnabled ( true );
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setEnabled ( true );
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setEnabled ( true );
      this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setEnabled ( true );

      RegexNode node = ( ( DefaultNodeView ) this.jGTIGraphOriginal
          .getSelectionCell () ).getNode ();
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setText ( String
          .valueOf ( node.nullable () ) );
      String firstpos = "{"; //$NON-NLS-1$
      for ( LeafNode n : node.firstPos () )
      {
        firstpos += n.getPosition ();
        if ( node.firstPos ().indexOf ( n ) != node.firstPos ().size () - 1 )
        {
          firstpos += "; "; //$NON-NLS-1$
        }
      }
      firstpos += "}"; //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setText ( firstpos );
      String lastpos = "{"; //$NON-NLS-1$
      for ( LeafNode n : node.lastPos () )
      {
        lastpos += n.getPosition ();
        if ( node.lastPos ().indexOf ( n ) != node.lastPos ().size () - 1 )
        {
          lastpos += "; "; //$NON-NLS-1$
        }
      }
      lastpos += "}"; //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setText ( lastpos );
      String followpos = "{"; //$NON-NLS-1$
      boolean first = true;
      LinkedList < ObjectPair < LeafNode, LeafNode > > follow = new LinkedList < ObjectPair < LeafNode, LeafNode > > ();
      follow.addAll ( node.followPos () );
      Collections.sort ( follow,
          new Comparator < ObjectPair < LeafNode, LeafNode > > ()
          {

            /**
             * @see java.util.Comparator#compare(java.lang.Object,
             *      java.lang.Object)
             */
            public int compare ( ObjectPair < LeafNode, LeafNode > o1,
                ObjectPair < LeafNode, LeafNode > o2 )
            {
              int p1 = o1.getFirst ().getPosition ()
                  - o2.getFirst ().getPosition ();
              if ( p1 != 0 )
              {
                return p1;
              }
              return o1.getSecond ().getPosition ()
                  - o2.getSecond ().getPosition ();
            }
          } );

      for ( ObjectPair < LeafNode, LeafNode > pair : follow )
      {
        if ( !first )
        {
          followpos += "; "; //$NON-NLS-1$
        }
        followpos += "(" + pair.getFirst ().getPosition () + ", " //$NON-NLS-1$ //$NON-NLS-2$
            + pair.getSecond ().getPosition () + ")"; //$NON-NLS-1$
        first = false;
      }
      followpos += "}"; //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setText ( followpos );

    }
    else
    {
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setEnabled ( false );
    }
  }
}
