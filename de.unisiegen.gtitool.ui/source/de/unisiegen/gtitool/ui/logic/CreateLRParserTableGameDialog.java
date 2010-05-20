package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.model.LRTableColumnModel;


/**
 * TODO
 */
public class CreateLRParserTableGameDialog extends AbstractBaseGameDialog
{

  /**
   * TODO
   * 
   * @param parent
   * @param cfg
   * @param gameType
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   */
  public CreateLRParserTableGameDialog ( final JFrame parent, final CFG cfg,
      final GameType gameType, final AbstractLRMachine machine )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    super ( parent, cfg, gameType );

    this.getGUI ().jGTIFirstSetTable.setVisible ( false );

    this.machine = machine;

    super
        .setUncoverMatrix ( new Boolean [ this.machine.getItems ().size () ] [ this.machine
            .getGrammar ().getTerminalSymbolSet ().size () ] );

    for ( int row = 0 ; row < getUncoverMatrix ().length ; ++row )
      for ( int col = 0 ; col < this.machine.getGrammar ()
          .getTerminalSymbolSet ().size () ; ++col )
        setUncoverMatrixEntry ( row, col, false );

    DefaultTableModel tableModel = new DefaultTableModel ( this.machine
        .getItems ().size (), this.machine.getGrammar ()
        .getTerminalSymbolSet ().size () )
    {

      /**
       * The generated serial
       */
      private static final long serialVersionUID = 609495296202823939L;


      private ArrayList < ArrayList < PrettyString >> strings = getMachine ()
          .getTableCellStrings ();


      @Override
      public PrettyString getValueAt ( final int row, final int col )
      {
        if ( col == 0 )
          return new PrettyString ( new PrettyToken (
              ( ( LRState ) getMachine ().getAutomaton ().getState ( row ) )
                  .getName (), Style.NONE ) );
        else if ( getUncoverMatrixEntry ( row, col - 1 ) )
          return this.strings.get ( row ).get ( col - 1 );
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

    getGUI ().jGTIParsingTable.setColumnModel ( new LRTableColumnModel ( this
        .getGrammar ().getTerminalSymbolSet () ) );
  }


  /**
   * TODO
   * 
   * @return
   */
  AbstractLRMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#handleShowAll()
   */
  @Override
  public void handleShowAll ()
  {
    /*
    for ( int i = 0 ; i < this.parsingTable.getRowCount () ; ++i )
      for ( int j = 0 ; j < this.parsingTable.getColumnCount () ; ++j )
        if ( !getUncoverMatrixEntry ( i, j ) )
        {
          setUncoverMatrixEntry ( i, j, true );
          updateReason ( this.parsingTable.getReasonFor ( i, j ) );
        }
    getGUI ().jGTIParsingTable.repaint ();*/
  }


  @Override
  public void handleUncover ( final MouseEvent evt )
  {
    if ( evt.getClickCount () >= 2 )
    {
      int row = getGUI ().jGTIParsingTable.getSelectedRow ();
      int col = getGUI ().jGTIParsingTable.getSelectedColumn ();
      // col > 1 cause the first column is the NonterminalSymbol-column
      if ( ( row == -1 || col == -1 ) || getUncoverMatrixEntry ( row, col - 1 )
          || col == 0 )
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
      updateReason ( new ArrayList < String > () );
      // updateReason ( this.parsingTable.getReasonFor ( row, col - 1 ) );
      getGUI ().jGTIParsingTable.repaint ();
    }
  }


  private final ActionSet getActionSetAt ( final int row, final int col )
  {
    return this.machine.actionsByIndex ( col, row );
  }


  AbstractLRMachine machine;
}
