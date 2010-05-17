package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultProductionSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.model.FirstSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.FirstSetTableModel;
import de.unisiegen.gtitool.ui.model.FollowSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.FollowSetTableModel;
import de.unisiegen.gtitool.ui.model.PTTableColumnModel;
import de.unisiegen.gtitool.ui.netbeans.BaseGameDialogForm;


/**
 * Implements the logic of the {@link BaseGameDialogForm}
 * 
 * @author Christian Uhrhan
 */
public class CreateParsingTableGameDialog extends AbstractBaseGameDialog
{

  /**
   * The {@link ParsingTable}
   */
  ParsingTable parsingTable;


  /**
   * The uncover matrix indicates which {@link ParsingTable} entries are already
   * uncovered
   */
  Boolean [][] uncoverMatrix;


  /**
   * Allocates a new {@link CreateParsingTableGameDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   * @param gameType The {@link AbstractBaseGameDialog.GameType}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public CreateParsingTableGameDialog ( final JFrame parent, final CFG cfg,
      final GameType gameType ) throws GrammarInvalidNonterminalException,
      TerminalSymbolSetException
  {
    super ( parent, cfg, gameType );
    // setup the first and follow table
    // FirstSetTable
    getGUI ().jGTIFirstSetTable.setModel ( new FirstSetTableModel (
        getGrammar () ) );
    getGUI ().jGTIFirstSetTable
        .setColumnModel ( new FirstSetTableColumnModel () );
    getGUI ().jGTIFirstSetTable.getTableHeader ().setReorderingAllowed ( false );
    getGUI ().jGTIFirstSetTable.setCellSelectionEnabled ( false );

    // FollowSetTable
    getGUI ().jGTIFollowSetTable.setModel ( new FollowSetTableModel (
        getGrammar () ) );
    getGUI ().jGTIFollowSetTable
        .setColumnModel ( new FollowSetTableColumnModel () );
    getGUI ().jGTIFollowSetTable.getTableHeader ()
        .setReorderingAllowed ( false );
    getGUI ().jGTIFollowSetTable.setCellSelectionEnabled ( false );

    // setup the parsing table (backend)
    this.parsingTable = new DefaultParsingTable ( getGrammar () );
    this.parsingTable.create ();

    // setup the uncover matrix
    this.uncoverMatrix = new Boolean [ getGrammar ().getNonterminalSymbolSet ()
        .size () ] [];
    int terminalSize = getGrammar ().getTerminalSymbolSet ().size ();
    for ( int row = 0 ; row < this.uncoverMatrix.length ; ++row )
    {
      this.uncoverMatrix [ row ] = new Boolean [ terminalSize ];
      for ( int col = 0 ; col < terminalSize ; ++col )
        this.uncoverMatrix [ row ] [ col ] = new Boolean ( false );
    }

    // setup the correct/wrong answers
    calculateCorrectWrongAnswers ();

    getGUI ().jGTIExistingCorrectAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelAmountCorrect", new Integer ( //$NON-NLS-1$
            getExistingCorrectAnswers () ) ) );

    getGUI ().jGTIExistingWrongAnswersLabel.setText ( Messages.getString (
        "BaseGameDialog.LabelAmountWrong", new Integer ( //$NON-NLS-1$
            getExistingWrongAnswers () ) ) );
    updateAnswers ();

    // setup the parsing table (frontend)
    DefaultTableModel tableModel = new DefaultTableModel ( this.parsingTable
        .getRowCount (), this.parsingTable.getColumnCount () )
    {

      /**
       * The generated serial
       */
      private static final long serialVersionUID = 609495296202823939L;


      @Override
      public Object getValueAt ( int row, int col )
      {
        if ( col == 0 )
          return getGrammar ().getNonterminalSymbolSet ().get ( row );
        // col - 1 cause the first column are the nonterminals but they don't
        // count
        else if ( CreateParsingTableGameDialog.this.uncoverMatrix [ row ] [ col - 1 ]
            .booleanValue () )
          return CreateParsingTableGameDialog.this.parsingTable.get ( row,
              col - 1 );
        return new PrettyString ();
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
    getGUI ().jGTIParsingTable.setColumnModel ( new PTTableColumnModel (
        getGrammar ().getTerminalSymbolSet () ) );
    getGUI ().jGTIParsingTable.getTableHeader ().setReorderingAllowed ( false );
    getGUI ().jGTIParsingTable.setCellSelectionEnabled ( true );

  }


  /**
   * Calculates the number of correct/wrong answers for the game
   */
  private void calculateCorrectWrongAnswers ()
  {
    int answers = 0;
    int empty = 0;
    for ( NonterminalSymbol ns : getGrammar ().getNonterminalSymbolSet () )
      for ( TerminalSymbol ts : getGrammar ().getTerminalSymbolSet () )
      {
        int entrySize = this.parsingTable.get ( ns, ts ).size ();
        // count the entries with one item
        if ( entrySize == 0 )
          ++empty;
        else if ( entrySize == 1 )
          ++answers;
      }
    // there are #NonterminalSymbol * #TerminalSymbol possible answers
    int possibleAnswers = getGrammar ().getNonterminalSymbolSet ().size ()
        * getGrammar ().getTerminalSymbolSet ().size ();
    switch ( getGameType () )
    {
      case GUESS_SINGLE_ENTRY :
        // user shall specify all parsing table entries with only one item in it
        // so 'answer' is the amount of correct answers...
        setExistingCorrectAnswers ( answers );
        setExistingWrongAnswers ( possibleAnswers - answers );
        return;
      case GUESS_MULTI_ENTRY :
        // ...otherwise empty + answers is the amount of wrong answers and the
        // rest of entries are the number of correct answers
        setExistingWrongAnswers ( empty + answers );
        setExistingCorrectAnswers ( possibleAnswers - ( empty + answers ) );
        return;
    }
  }


  /**
   * blub
   * 
   * @param row blub
   * @param col blub
   * @return {@link ActionSet}
   */
  private final ActionSet getActionSetAt ( final int row, final int col )
  {
    final DefaultProductionSet dps = this.parsingTable.get ( row, col );
    final ActionSet actions = new DefaultActionSet ();
    for ( Production p : dps )
      try
      {
        actions.add ( new ReduceAction ( p ) );
      }
      catch ( ActionSetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    return actions;
  }


  /**
   * handles the uncover of a parsing table cell
   * 
   * @param evt The {@link MouseEvent}
   */
  @Override
  public void handleUncover ( final MouseEvent evt )
  {
    if ( evt.getClickCount () >= 2 )
    {
      int row = getGUI ().jGTIParsingTable.getSelectedRow ();
      int col = getGUI ().jGTIParsingTable.getSelectedColumn ();
      // col > 1 cause the first column is the NonterminalSymbol-column
      if ( ( row == -1 || col == -1 )
          || this.uncoverMatrix [ row ] [ col - 1 ].booleanValue () || col == 0 )
        return;

      try
      {
        final ActionSet actions = getActionSetAt ( row, col - 1 );
        final ActionSet selectableActions = getSelectableActions ( actions );
        final ActionSet chosenActions = getUserSelection ( selectableActions );
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

      this.uncoverMatrix [ row ] [ col - 1 ] = new Boolean ( true );
      updateStats ( true );
      updateAnswers ();
      updateReason ( this.parsingTable.getReasonFor ( row, col - 1 ) );
      getGUI ().jGTIParsingTable.repaint ();
    }
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#handleShowAll()
   */
  @Override
  protected void handleShowAll ()
  {
  }
}
