package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.LRItem;
import de.unisiegen.gtitool.core.entities.LRItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.lrreasons.LRReasonMaker;
import de.unisiegen.gtitool.ui.model.FollowSetTableColumnModel;
import de.unisiegen.gtitool.ui.model.FollowSetTableModel;
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
   * @param machine
   * @param reasonMaker
   * @param machineType
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public CreateLRParserTableGameDialog ( final JFrame parent, final CFG cfg,
      final AbstractLRMachine machine, final LRReasonMaker reasonMaker,
      final MachineType machineType ) throws TerminalSymbolSetException,
      NonterminalSymbolSetException, GrammarInvalidNonterminalException
  {
    super ( parent, cfg, machine.getAutomaton ().getState ().size (), machine
        .getGrammar ().getTerminalSymbolSet ().size () );

    this.machine = machine;

    this.reasonMaker = reasonMaker;

    this.strings = machine.getTableCellStrings ();

    this.lrSetTableColumnModel = new LRSetTableColumnModel ();

    getGUI ().jGTIParsingTable.setColumnModel ( new LRTableColumnModel (
        getGrammar ().getTerminalSymbolSet () ) );

    getGUI ().jGTIFollowSetTable.setModel ( new LRSetTableModel (
        this.lrSetTableColumnModel ) );

    getGUI ().jGTIFollowSetTable.setColumnModel ( this.lrSetTableColumnModel );

    if ( machineType.equals ( MachineType.SLR ) )
    {
      getGUI ().jGTIFirstSetTable.setModel ( new FollowSetTableModel (
          getGrammar () ) );
      getGUI ().jGTIFirstSetTable
          .setColumnModel ( new FollowSetTableColumnModel () );
    }
    else
      getGUI ().jScrollPane1.setVisible ( false );

    getGUI ().setTitle (
        Messages.getString (
            "BaseGameDialog.Caption2", toLRCaption ( machineType ) ) ); //$NON-NLS-1$

    init ();
  }


  /**
   * Converts the machineType to a readable title for the dialog
   * 
   * @param machineType
   * @return the title's part
   */
  private static String toLRCaption ( final MachineType machineType )
  {
    switch ( machineType )
    {
      case LR0Parser :
        return "LR(0)"; //$NON-NLS-1$
      case SLR :
        return "SLR"; //$NON-NLS-1$
      case LALR1Parser :
        return "LALR(1)"; //$NON-NLS-1$
      case LR1Parser :
        return "LR(1)"; //$NON-NLS-1$
      default :
        System.err.println ( "Invalid LR game type!" ); //$NON-NLS-1$
        System.exit ( 1 );
        return ""; //$NON-NLS-1$
    }
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
    final ArrayList < String > ret = new ArrayList < String > ();
    for ( Action action : getActionSetAt ( row, column ) )
      ret.add ( this.reasonMaker.reason ( ( LRState ) this.machine
          .getAutomaton ().getState ( row ), getGrammar ()
          .getTerminalSymbolSet ().get ( column ), action ) );
    return ret;
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
    if ( this.lastSelectedState != null )
    {
      this.lastSelectedState.setSelected ( false );
      this.lrSetTableColumnModel.stateChanged ( this.lastSelectedState );
    }

    final State state = this.machine.getAutomaton ().getState ( row );

    state.setSelected ( true );

    this.lastSelectedState = state;

    this.lrSetTableColumnModel.stateChanged ( state );

    getGUI ().jGTIFollowSetTable.repaint ();
  }


  /**
   * The associated LR parser machine
   */
  private AbstractLRMachine machine;


  /**
   * The reason maker
   */
  private LRReasonMaker reasonMaker;


  /**
   * The lr set table column model
   */
  private LRSetTableColumnModel lrSetTableColumnModel;


  /**
   * The state that was last selected in the main table
   */
  private State lastSelectedState = null;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getSelectableActions(int
   *      row, int col)
   */
  @Override
  protected ActionSet getSelectableActions ( final int row, final int col )
  {
    // if (A \rightarrow \alpha \dot) \in I_{row} then add
    // reduce (A \rightarrow \alpha) as a possible action
    final ActionSet selectableActions = new DefaultActionSet ();

    final LRState state = ( LRState ) this.machine.getAutomaton ().getState (
        row );

    final LRItemSet items = state.getItems ();

    for ( LRItem item : items.baseList () )
    {
      if ( item.dotIsAtEnd () )
        try
        {
          selectableActions.add ( new ReduceAction ( new DefaultProduction (
              item.getNonterminalSymbol (), item.getProductionWord () ) ) );
        }
        catch ( ActionSetException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }

    }

    // always add the shift action
    selectableActions.addIfNonExistant ( new ShiftAction () );

    // add the accept action if this is the "$ column"
    if ( col == 0 ) // we should read the table's caption
      selectableActions.addIfNonExistant ( new AcceptAction () );

    return selectableActions;
  }
}
