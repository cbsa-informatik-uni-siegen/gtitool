package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.Action.TransitionType;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.ChooseNextActionDialog.SelectionMode;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.BaseGameDialogForm;


/**
 * Abstract logic class for BaseGameDialog
 * 
 * @author Christian Uhrhan
 */
public abstract class AbstractBaseGameDialog implements
    LogicClass < BaseGameDialogForm >
{

  /**
   * Defines the type of our game
   */
  public enum GameType
  {
    /**
     * The user should find the {@link ParsingTable} entry where only one item
     * is recorded
     */
    GUESS_SINGLE_ENTRY,

    /**
     * The user should find the {@link ParsingTable} entry where multiple items
     * are recoreded
     */
    GUESS_MULTI_ENTRY;
  }


  /**
   * The {@link CFG}
   */
  private DefaultCFG cfg;


  /**
   * The {@link GameType}
   */
  private GameType gameType;


  /**
   * The {@link JFrame} parent
   */
  private JFrame parent;


  /**
   * The {@link BaseGameDialogForm}
   */
  private BaseGameDialogForm gui;


  /**
   * The amount of existing correct answers
   */
  private int existingCorrectAnswers;


  /**
   * The amount of existing wrong answers
   */
  private int existingWrongAnswers;


  /**
   * The amount of the correct answers given by the user so far
   */
  private int userCorrectAnswers;


  /**
   * The amount of the wrong answers given by the user so far
   */
  private int userWrongAnswers;


  /**
   * list model
   */
  private DefaultListModel reasonModel;


  /**
   * The uncover matrix indicates which data entries are already uncovered
   */
  private Boolean [][] uncoverMatrix;


  /**
   * Allocates a new {@link AbstractBaseGameDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   * @param gameType The {@link GameType}
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   */
  public AbstractBaseGameDialog ( final JFrame parent, final CFG cfg,
      final GameType gameType ) throws TerminalSymbolSetException,
      NonterminalSymbolSetException
  {
    this.parent = parent;
    // setup grammar
    this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );
    this.cfg.getTerminalSymbolSet ().addIfNonexistent (
        DefaultTerminalSymbol.EndMarker );

    // setup game type
    this.gameType = gameType;

    // setup gui
    this.gui = new BaseGameDialogForm ( parent, this );
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );

    this.existingCorrectAnswers = 0;
    this.existingWrongAnswers = 0;
    this.userCorrectAnswers = 0;
    this.userWrongAnswers = 0;

    this.reasonModel = new DefaultListModel ();
    this.gui.jGTIListReason.setModel ( this.reasonModel );
  }


  /**
   * Returns the uncover state of a table cell entry
   * 
   * @param row the row
   * @param col the col
   * @return uncover state of a table cell entry
   */
  protected boolean getUncoverMatrixEntry ( final int row, final int col )
  {
    System.err.println(row + " " + col);
    return this.uncoverMatrix [ row ] [ col ].booleanValue ();
  }


  /**
   * Set the uncover state of a table cell entry
   * 
   * @param row the row
   * @param col the col
   * @param value boolean value -> uncover state
   */
  protected void setUncoverMatrixEntry ( final int row, final int col,
      final boolean value )
  {
    this.uncoverMatrix [ row ] [ col ] = new Boolean ( value );
  }


  /**
   * sets the uncover matrix
   * 
   * @param uncoverMatrix new uncover matrix
   */
  protected void setUncoverMatrix ( final Boolean [][] uncoverMatrix )
  {
    this.uncoverMatrix = uncoverMatrix;
  }
  
  /**
   * 
   * Returns the uncover matrix
   *
   * @return the uncover matrix
   */
  protected Boolean[][] getUncoverMatrix()
  {
    return this.uncoverMatrix;
  }


  /**
   * update the reason list
   * 
   * @param reasons list of reasons
   */
  protected void updateReason ( final ArrayList < String > reasons )
  {
    for ( String reason : reasons )
      this.reasonModel.addElement ( reason );
  }


  /**
   * Returns the {@link GameType}
   * 
   * @return The {@link GameType}
   */
  protected GameType getGameType ()
  {
    return this.gameType;
  }


  /**
   * Returns the {@link DefaultCFG}
   * 
   * @return The {@link DefaultCFG}
   */
  protected DefaultCFG getGrammar ()
  {
    return this.cfg;
  }


  /**
   * Shows the dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
  }


  /**
   * closes the dialog
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * handles the uncover of a parsing table cell
   * 
   * @param evt The {@link MouseEvent}
   */
  public abstract void handleUncover ( final MouseEvent evt );


  /**
   * implements the logic to handle the 'show all' button
   */
  public abstract void handleShowAll ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public BaseGameDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Asks for user input
   * 
   * @param actions The {@link ActionSet}
   * @return {@link ActionSet}
   * @throws ActionSetException
   */
  protected ActionSet getUserSelection ( final ActionSet actions )
      throws ActionSetException
  {
    final SelectionMode selMode = this.gameType == GameType.GUESS_SINGLE_ENTRY ? SelectionMode.SINGLE_SELECTION
        : SelectionMode.MULTIPLE_SELECTION;
    final ChooseNextActionDialog cnad = new ChooseNextActionDialog (
        this.parent, actions, selMode );
    cnad.show ();
    if ( cnad.isConfirmed () )
      return cnad.getChosenAction ();
    return new DefaultActionSet ();
  }


  /**
   * compares two {@link ActionSet}s
   * 
   * @param actionSet1 blub
   * @param actionSet2 blub
   * @return blub
   */
  protected boolean actionSetsEquals ( final ActionSet actionSet1,
      final ActionSet actionSet2 )
  {
    if ( actionSet1.size () != actionSet2.size () )
      return false;
    boolean contains = true;
    for ( Action p : actionSet2 )
      contains = contains && actionSet1.contains ( p );
    return contains;
  }


  /**
   * Returns a reasonable set of {@link Action}s the user can select from
   * 
   * @param actions The {@link ActionSet}
   * @return {@link ActionSet}
   */
  protected final ActionSet getSelectableActions ( final ActionSet actions )
  {
    final TreeSet < NonterminalSymbol > nss = new TreeSet < NonterminalSymbol > ();
    final ActionSet selectableActions = new DefaultActionSet ();
    try
    {
      for ( Action a : actions )
        if ( a.getTransitionType () == TransitionType.REDUCE )
        {
          final NonterminalSymbol ns = a.getReduceAction ()
              .getNonterminalSymbol ();
          if ( nss.add ( ns ) )
          {
            ProductionSet ps = this.cfg.getProductionForNonTerminal ( ns );
            for ( Production p : ps )
              selectableActions.add ( new ReduceAction ( p ) );
          }
        }
        else
          selectableActions.add ( a );
    }
    catch ( final ActionSetException a )
    {
      a.printStackTrace ();
      System.exit ( 1 );
    }
    return selectableActions;
  }


  /**
   * updates the game statistics
   * 
   * @param answerWasCorrect blub
   */
  protected void updateStats ( final boolean answerWasCorrect )
  {
    if ( answerWasCorrect )
      ++this.userCorrectAnswers;
    else
      ++this.userWrongAnswers;
    return;
  }


  /**
   * blub
   * 
   * @return blub
   */
  protected int getUserCorrectAnswers ()
  {
    return this.userCorrectAnswers;
  }


  /**
   * blub
   * 
   * @param correctAnswers blub
   */
  protected void setUserCorrectAnswers ( final int correctAnswers )
  {
    this.userCorrectAnswers = correctAnswers;
  }


  /**
   * blub
   * 
   * @return blub
   */
  protected int getUserWrongAnswers ()
  {
    return this.userWrongAnswers;
  }


  /**
   * blub
   * 
   * @param wrongAnswers blub
   */
  protected void setUserWrongAnswers ( final int wrongAnswers )
  {
    this.userWrongAnswers = wrongAnswers;
  }


  /**
   * blub
   * 
   * @return blub
   */
  protected int getExistingCorrectAnswers ()
  {
    return this.existingCorrectAnswers;
  }


  /**
   * blub
   * 
   * @param correctAnswers blub
   */
  protected void setExistingCorrectAnswers ( final int correctAnswers )
  {
    this.existingCorrectAnswers = correctAnswers;
  }


  /**
   * blub
   * 
   * @return blub
   */
  protected int getExistingWrongAnswers ()
  {
    return this.existingWrongAnswers;
  }


  /**
   * blub
   * 
   * @param wrongAnswers blub
   */
  protected void setExistingWrongAnswers ( final int wrongAnswers )
  {
    this.existingWrongAnswers = wrongAnswers;
  }


  /**
   * Updates the gui correct/wrong answer labels
   */
  protected void updateAnswers ()
  {
    getGUI ().jGTICorrectAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelRight", new Integer ( //$NON-NLS-1$
            this.userCorrectAnswers ) ) );
    getGUI ().jGTIWrongAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelWrong", new Integer ( //$NON-NLS-1$
            this.userWrongAnswers ) ) );
  }
}
