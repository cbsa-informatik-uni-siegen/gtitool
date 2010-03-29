package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
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
   * The {@link NonterminalSymbolSet}
   */
  NonterminalSymbolSet nonterminals;


  /**
   * The {@link ParsingTable}
   */
  ParsingTable parsingTable;


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
   * @param cfg The {@link CFG}
   * @param gameType The {@link GameType}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public CreateParsingTableGameDialog ( final CFG cfg, final GameType gameType )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    this.gameType = gameType;
    this.nonterminals = cfg.getNonterminalSymbolSet ();

    //
    // setup the gui
    //

    // setup the grammar panel
    this.gui.styledNonterminalSymbolSetParserPanel.setText ( cfg
        .getNonterminalSymbolSet () );
    this.gui.styledStartNonterminalSymbolParserPanel.setText ( cfg
        .getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanel.setText ( cfg
        .getTerminalSymbolSet () );

    this.gui.jGTIGrammarTable.setModel ( cfg );
    this.gui.jGTIGrammarTable.setColumnModel ( new GrammarColumnModel () );
    this.gui.jGTIGrammarTable
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTIGrammarTable.getTableHeader ().setReorderingAllowed ( false );

    // setup the parsing table (backend)
    this.parsingTable = new DefaultParsingTable ( cfg );
    this.parsingTable.create ();

    // setup the correct/wrong answers
    calculateCorrectWrongAnswers ( cfg );
    this.userCorrectAnswers = 0;
    this.userWrongAnswers = 0;

    this.gui.jGTIExistingCorrectAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelAmountCorrect", //$NON-NLS-1$
        new Integer ( this.existingCorrectAnswers ) ) );

    this.gui.jGTIExistingWrongAnswersLabel.setText ( Messages.getString (
        "CreateParsingTableGameDialog.LabelAmountWrong", //$NON-NLS-1$
        new Integer ( this.existingWrongAnswers ) ) );
    updateAnswers ();

    // setup the parsing table (frontend)
    DefaultTableModel tableModel = new DefaultTableModel ( this.parsingTable
        .getRowCount (), this.parsingTable.getColumnCount () )
    {

      /**
       * {@inheritDoc}
       * 
       * @see javax.swing.table.TableModel#getValueAt(int, int)
       */
      @Override
      public Object getValueAt ( int arg0, int arg1 )
      {
        if ( arg1 == 0 )
          return CreateParsingTableGameDialog.this.nonterminals.get ( arg0 );
        // arg1 - 1 cause we have one column more (the nonterminal/row
        // description)
        try
        {
          return CreateParsingTableGameDialog.this.parsingTable.get ( arg0,
              arg1 - 1 );
        }
        catch ( Exception e )
        {
          return new PrettyString ();
        }
      }
    };
    this.gui.jGTIParsingTable.setModel ( tableModel );
    this.gui.jGTIParsingTable.setColumnModel ( new PTTableColumnModel ( cfg
        .getTerminalSymbolSet () ) );
    this.gui.jGTIParsingTable.getTableHeader ().setReorderingAllowed ( false );
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
   * 
   * @param cfg The {@link CFG}
   */
  private void calculateCorrectWrongAnswers ( final CFG cfg )
  {
    int answers = 0;
    int empty = 0;
    for ( NonterminalSymbol ns : cfg.getNonterminalSymbolSet () )
      for ( TerminalSymbol ts : cfg.getTerminalSymbolSet () )
      {
        int entrySize = this.parsingTable.get ( ns, ts ).size ();
        // count the entries with one item
        if ( entrySize == 0 )
          ++empty;
        else if ( entrySize == 1 )
          ++answers;
      }
    // there are #NonterminalSymbol * #TerminalSymbol possible answers
    int possibleAnswers = cfg.getNonterminalSymbolSet ().size ()
        * cfg.getTerminalSymbolSet ().size ();
    switch ( this.gameType )
    {
      case GUESS_SINGLE_ENTRY :
        // user shall specify all parsing table entries with only one item in it
        // so 'answer' is the amount of correct answers...
        this.existingCorrectAnswers = answers;
        this.existingWrongAnswers = possibleAnswers
            - this.existingCorrectAnswers;
        break;
      case GUESS_MULTI_ENTRY :
        // ...otherwise empty + answers is the amount of wrong answers and the
        // rest of entries are the number of correct answers
        this.existingWrongAnswers = empty + answers;
        this.existingCorrectAnswers = possibleAnswers
            - this.existingWrongAnswers;
        break;
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
    this.gui.setVisible ( false );
  }


  /**
   * handles the uncover of a parsing table cell
   */
  public void handleUncover ( MouseEvent evt )
  {

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
