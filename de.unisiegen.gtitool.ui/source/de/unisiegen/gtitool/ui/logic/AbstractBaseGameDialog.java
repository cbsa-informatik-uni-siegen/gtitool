package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ReverseReduceAction;
import de.unisiegen.gtitool.core.entities.Action.TransitionType;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.ui.i18n.Messages;
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
   * The {@link CFG}
   */
  private AbstractGrammar cfg;


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
   * The number of rows in the table
   */
  private final int rowCount;


  /**
   * The number of columns in the table minus the first "labeled" column
   */
  private final int columnCount;


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
   * @param rowCount
   * @param columnCount
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   */
  public AbstractBaseGameDialog ( final JFrame parent, final Grammar cfg,
      final int rowCount, final int columnCount )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    this.rowCount = rowCount;

    this.parent = parent;

    // setup grammar
    if ( !cfg.getTerminalSymbolSet ().contains (
        DefaultTerminalSymbol.EndMarker ) )
    {
      this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );
      this.cfg.getTerminalSymbolSet ().add ( DefaultTerminalSymbol.EndMarker );
      this.columnCount = columnCount + 1;
    }
    else
    {
      this.cfg = ( AbstractGrammar ) cfg;
      this.columnCount = columnCount;
    }

    // setup the uncover matrix
    setUncoverMatrix ( new Boolean [ this.rowCount ] [ this.columnCount ] );
    for ( int row = 0 ; row < this.rowCount ; ++row )
      for ( int col = 0 ; col < this.columnCount ; ++col )
        setUncoverMatrixEntry ( row, col, false );

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

    // setup the parsing table (frontend)
    final DefaultTableModel tableModel = new DefaultTableModel ( this.rowCount,
        this.columnCount )
    {

      /**
       * The generated serial
       */
      private static final long serialVersionUID = 609495296202823939L;


      @Override
      public PrettyString getValueAt ( int row, int col )
      {
        return getTableValueAt ( row, col );
      }


      @Override
      public boolean isCellEditable (
          @SuppressWarnings ( "unused" ) final int row,
          @SuppressWarnings ( "unused" ) final int col )
      {
        return false;
      }
    };
    getGUI ().jGTIParsingTable.setModel ( tableModel );
  }


  /**
   * Initialize the class Note: The derived class may have to initialize members
   * first before the base class can continue
   */
  protected void init ()
  {
    updateAnswers ();

    // setup the correct/wrong answers
    calculateCorrectWrongAnswers ();

    getGUI ().jGTIExistingCorrectAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelAmountCorrect", new Integer ( //$NON-NLS-1$
            getExistingCorrectAnswers () ) ) );

    getGUI ().jGTIExistingWrongAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelAmountWrong", new Integer ( //$NON-NLS-1$
            getExistingWrongAnswers () ) ) );
  }


  /**
   * Gets the table entry for a given row and column
   * 
   * @param row
   * @param column
   * @return the entry
   */
  protected abstract PrettyString getTableValueAt ( final int row,
      final int column );


  /**
   * Returns the uncover state of a table cell entry
   * 
   * @param row the row
   * @param col the col
   * @return uncover state of a table cell entry
   */
  protected boolean getUncoverMatrixEntry ( final int row, final int col )
  {
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
   * Returns the uncover matrix
   * 
   * @return the uncover matrix
   */
  protected Boolean [][] getUncoverMatrix ()
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
   * Returns the {@link DefaultCFG}
   * 
   * @return The {@link DefaultCFG}
   */
  protected AbstractGrammar getGrammar ()
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
  public final void handleUncover ( final MouseEvent evt )
  {
    final int row = getGUI ().jGTIParsingTable.getSelectedRow ();
    final int col = getGUI ().jGTIParsingTable.getSelectedColumn ();

    notifyClicked ( row, col );

    if ( evt.getClickCount () < 2 )
      return;
    // col > 1 cause the first column is the NonterminalSymbol-column
    if ( col == 0 || ( row == -1 || col == -1 )
        || getUncoverMatrixEntry ( row, col - 1 ) )
      return;

    try
    {
      final ActionSet actions = getActionSetAt ( row, col - 1 );
      if ( actions.size () == 0 )
      {
        updateStats ( false );
        updateAnswers ();
        setUncoverMatrixEntry ( row, col - 1, true );
        getGUI ().jGTIParsingTable.repaint ();
        return;
      }
      final ActionSet selectableActions = getSelectableActions ( actions );
      final ActionSet chosenActions = getUserSelection ( selectableActions );
      // nothing selected => cancel was pressed
      if ( chosenActions.size () == 0 )
        return;
      if ( !actionSetsEquals ( actions, chosenActions ) )
      {
        updateStats ( false );
        updateAnswers ();
        return;
      }
    }
    catch ( ActionSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    setUncoverMatrixEntry ( row, col - 1, true );
    updateStats ( true );
    updateAnswers ();
    updateReason ( getReasonFor ( row, col - 1 ) );
    getGUI ().jGTIParsingTable.repaint ();
  }


  /**
   * Notifies that the main table has been clicked
   * 
   * @param row
   * @param col
   */
  protected void notifyClicked ( @SuppressWarnings ( "unused" ) final int row,
      @SuppressWarnings ( "unused" ) final int col )
  {
    // do nothing
  }


  /**
   * Returns the ActionSet for a given cell
   * 
   * @param row
   * @param column
   * @return the action set
   */
  protected abstract ActionSet getActionSetAt ( int row, int column );


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
    final ChooseNextActionDialog cnad = new ChooseNextActionDialog (
        this.parent, actions );
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
              if ( this instanceof CreateParsingTableGameDialog )
                selectableActions.add ( new ReverseReduceAction ( p ) );
              else
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
  private void updateAnswers ()
  {
//    getGUI ().jGTICorrectAnswersLabel.setText ( Messages.getString (
//        "BaseGameDialog.LabelRight", new Integer ( //$NON-NLS-1$
//            this.userCorrectAnswers ) ) );
//    getGUI ().jGTIWrongAnswersLabel.setText ( Messages.getString (
//        "BaseGameDialog.LabelWrong", new Integer ( //$NON-NLS-1$
//            this.userWrongAnswers ) ) );
  }


  /**
   * Implements the logic of the "show all" button.
   */
  public final void handleShowAll ()
  {
    for ( int i = 0 ; i < this.rowCount ; ++i )
      for ( int j = 0 ; j < this.columnCount ; ++j )
        if ( !getUncoverMatrixEntry ( i, j ) )
        {
          setUncoverMatrixEntry ( i, j, true );
          updateReason ( getReasonFor ( i, j ) );
        }
    getGUI ().jGTIParsingTable.repaint ();
  }


  /**
   * Calculates the number of correct/wrong answers for the game
   */
  private void calculateCorrectWrongAnswers ()
  {
    setExistingCorrectAnswers ( 0 );
    setExistingWrongAnswers ( 0 );
  }


  /**
   * Returns the reason(s) for why an entry has been chosen
   * 
   * @param row
   * @param column
   * @return the reason(s)
   */
  public abstract ArrayList < String > getReasonFor ( int row, int column );


  /**
   * Returns how many entries are present in a given cell
   * 
   * @param row
   * @param column
   * @return the amount
   */
  protected abstract int getEntrySize ( int row, int column );
}
