package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.model.LRSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.LRSetTableModel;
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
   * @param machine
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   */
  public CreateLRParserTableGameDialog ( final JFrame parent, final CFG cfg,
      final GameType gameType, final AbstractLRMachine machine )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    super ( parent, cfg, gameType, machine.getAutomaton ().getState ().size (),
        machine.getGrammar ().getTerminalSymbolSet ().size () );

    //this.getGUI ().jGTIFirstSetTable.setVisible ( false );

    this.machine = machine;

    this.strings = machine.getTableCellStrings ();

    this.lrSetTableColumnModel = new LRSetTableColumnModel ();

    getGUI ().jGTIFollowSetTable.setModel ( new LRSetTableModel (
        this.lrSetTableColumnModel ) );

    getGUI ().jGTIParsingTable.setColumnModel ( new LRTableColumnModel ( this
        .getGrammar ().getTerminalSymbolSet () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getTableValueAt(int,
   *      int)
   */
  @Override
  protected final PrettyString getTableValueAt ( final int row, final int column )
  {
    if ( column == 0 )
      return new PrettyString ( new PrettyToken ( ( ( LRState ) getMachine ()
          .getAutomaton ().getState ( row ) ).getName (), Style.NONE ) );
    else if ( getUncoverMatrixEntry ( row, column - 1 ) )
      return this.strings.get ( column - 1 ).get ( row );
    return new PrettyString ();
  }


  /**
   * The string entries for all cells
   */
  private ArrayList < ArrayList < PrettyString >> strings;


  /**
   * Getter for the LR parser machine
   * 
   * @return the parser
   */
  AbstractLRMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getActionSetAt(int,
   *      int)
   */
  @Override
  protected final ActionSet getActionSetAt ( final int row, final int col )
  {
    return this.machine.actionsByIndex ( row, col );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getReasonFor(int,
   *      int)
   */
  @Override
  public ArrayList < String > getReasonFor ( final int row, final int column )
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getEntrySize(int,
   *      int)
   */
  @Override
  protected int getEntrySize ( final int row, final int column )
  {
    return getActionSetAt ( row, column ).size ();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void notifyClicked ( final int row,
      @SuppressWarnings ( "unused" ) final int col )
  {
    final State state = this.machine.getAutomaton ().getState ( row );

    state.setSelected ( !state.isSelected () );

    this.lrSetTableColumnModel.stateChanged ( state );
    
    this.getGUI ().jGTIFollowSetTable.repaint ();
  }


  /**
   * The associated LR parser machine
   */
  private AbstractLRMachine machine;


  private LRSetTableColumnModel lrSetTableColumnModel;
}
