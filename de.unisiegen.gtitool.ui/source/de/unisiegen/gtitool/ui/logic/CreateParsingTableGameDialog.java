package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.model.PTTableColumnModel;
import de.unisiegen.gtitool.ui.netbeans.CreateParsingTableGameDialogForm;


/**
 * Implements the logic of the {@link CreateParsingTableGameDialogForm}
 * 
 * @author Christian Uhrhan
 */
public class CreateParsingTableGameDialog implements
    LogicClass < CreateParsingTableGameDialogForm >
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
   * The {@link CreateParsingTableGameDialogForm}
   */
  private CreateParsingTableGameDialogForm gui;


  /**
   * The {@link GameType}
   */
  private GameType gameType;


  /**
   * The {@link DefaultCFG}
   */
  CFG cfg;


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
   * Allocates a new {@link CreateParsingTableGameDialog}
   * 
   * @param frame The {@link JFrame}
   * @param cfg The {@link CFG}
   * @param gameType The {@link GameType}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   * @throws NonterminalSymbolSetException
   */
  public CreateParsingTableGameDialog ( final JFrame frame, final CFG cfg,
      final GameType gameType ) throws GrammarInvalidNonterminalException,
      TerminalSymbolSetException, NonterminalSymbolSetException
  {
    this.gameType = gameType;
    this.cfg = new DefaultCFG ( ( DefaultCFG ) cfg );
    this.cfg.getTerminalSymbolSet ().add ( DefaultTerminalSymbol.EndMarker );

    //
    // setup the gui
    //
    this.gui = new CreateParsingTableGameDialogForm ( frame, this );

    // setup the grammar panel
    this.gui.styledNonterminalSymbolSetParserPanel.setText ( this.cfg
        .getNonterminalSymbolSet () );
    this.gui.styledStartNonterminalSymbolParserPanel.setText ( this.cfg
        .getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanel.setText ( this.cfg
        .getTerminalSymbolSet () );

    this.gui.jGTIGrammarTable.setModel ( this.cfg );
    this.gui.jGTIGrammarTable.setColumnModel ( new GrammarColumnModel () );
    this.gui.jGTIGrammarTable
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTIGrammarTable.getTableHeader ().setReorderingAllowed ( false );

    // setup the parsing table (backend)
    this.parsingTable = new DefaultParsingTable ( this.cfg );
    this.parsingTable.create ();

    // setup the uncover matrix
    this.uncoverMatrix = new Boolean [ this.cfg.getNonterminalSymbolSet ()
        .size () ] [];
    int terminalSize = this.cfg.getTerminalSymbolSet ().size ();
    for ( int row = 0 ; row < this.uncoverMatrix.length ; ++row )
    {
      this.uncoverMatrix [ row ] = new Boolean [ terminalSize ];
      for ( int col = 0 ; col < terminalSize ; ++col )
        this.uncoverMatrix [ row ] [ col ] = new Boolean ( false );
    }

    // setup the correct/wrong answers
    calculateCorrectWrongAnswers ();
    this.userCorrectAnswers = 0;
    this.userWrongAnswers = 0;

    this.gui.jGTIExistingCorrectAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelAmountCorrect", new Integer ( //$NON-NLS-1$
            this.existingCorrectAnswers ) ) );

    this.gui.jGTIExistingWrongAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelAmountWrong", new Integer ( //$NON-NLS-1$
            this.existingWrongAnswers ) ) );
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
          return CreateParsingTableGameDialog.this.cfg
              .getNonterminalSymbolSet ().get ( row );
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
    this.gui.jGTIParsingTable.setModel ( tableModel );
    this.gui.jGTIParsingTable.setColumnModel ( new PTTableColumnModel (
        this.cfg.getTerminalSymbolSet () ) );
    this.gui.jGTIParsingTable.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTIParsingTable.setCellSelectionEnabled ( true );

  }


  /**
   * Updates the gui correct/wrong answer labels
   */
  private void updateAnswers ()
  {
    this.gui.jGTICorrectAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelRight", new Integer ( //$NON-NLS-1$
            this.userCorrectAnswers ) ) );
    this.gui.jGTIWrongAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelWrong", new Integer ( //$NON-NLS-1$
            this.userWrongAnswers ) ) );
  }


  /**
   * Calculates the number of correct/wrong answers for the game
   */
  private void calculateCorrectWrongAnswers ()
  {
    int answers = 0;
    int empty = 0;
    for ( NonterminalSymbol ns : this.cfg.getNonterminalSymbolSet () )
      for ( TerminalSymbol ts : this.cfg.getTerminalSymbolSet () )
      {
        int entrySize = this.parsingTable.get ( ns, ts ).size ();
        // count the entries with one item
        if ( entrySize == 0 )
          ++empty;
        else if ( entrySize == 1 )
          ++answers;
      }
    // there are #NonterminalSymbol * #TerminalSymbol possible answers
    int possibleAnswers = this.cfg.getNonterminalSymbolSet ().size ()
        * this.cfg.getTerminalSymbolSet ().size ();
    switch ( this.gameType )
    {
      case GUESS_SINGLE_ENTRY :
        // user shall specify all parsing table entries with only one item in it
        // so 'answer' is the amount of correct answers...
        this.existingCorrectAnswers = answers;
        this.existingWrongAnswers = possibleAnswers
            - this.existingCorrectAnswers;
        return;
      case GUESS_MULTI_ENTRY :
        // ...otherwise empty + answers is the amount of wrong answers and the
        // rest of entries are the number of correct answers
        this.existingWrongAnswers = empty + answers;
        this.existingCorrectAnswers = possibleAnswers
            - this.existingWrongAnswers;
        return;
    }
  }


  /**
   * updates the game statistics
   * 
   * @param entry The {@link ProductionSet} entry which was just uncovered
   */
  private void updateStats ( final ProductionSet entry )
  {
    switch ( this.gameType )
    {
      case GUESS_SINGLE_ENTRY :
        if ( entry.size () == 1 )
          ++this.userCorrectAnswers;
        else
          ++this.userWrongAnswers;
        return;
      case GUESS_MULTI_ENTRY :
        if ( entry.size () > 1 )
          ++this.userCorrectAnswers;
        else
          ++this.userWrongAnswers;
        return;
    }
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
  public void handleUncover ( final MouseEvent evt )
  {
    if ( evt.getClickCount () >= 2 )
    {
      int row = this.gui.jGTIParsingTable.getSelectedRow ();
      int col = this.gui.jGTIParsingTable.getSelectedColumn ();
      // col > 1 cause the first column is the NonterminalSymbol-column
      if ( ( row == -1 || col == -1 )
          || this.uncoverMatrix [ row ] [ col - 1 ].booleanValue () || col == 0 )
        return;
      this.uncoverMatrix [ row ] [ col - 1 ] = new Boolean ( true );
      updateStats ( this.parsingTable.get ( row, col - 1 ) );
      updateAnswers ();
      this.gui.jGTIParsingTable.repaint ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public CreateParsingTableGameDialogForm getGUI ()
  {
    return this.gui;
  }

}
