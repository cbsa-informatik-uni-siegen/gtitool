package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultProductionSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ReverseReduceAction;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
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
   * Allocates a new {@link CreateParsingTableGameDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   * @throws NonterminalSymbolSetException
   */
  public CreateParsingTableGameDialog ( final JFrame parent, final CFG cfg )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException,
      NonterminalSymbolSetException
  {
    super ( parent, cfg, cfg.getNonterminalSymbolSet ().size (), cfg
        .getTerminalSymbolSet ().size () );
    // setup the first and follow table
    // FirstSetTable
    getGUI ().jGTIFirstSetTable.setModel ( new FirstSetTableModel (
        getGrammar (), true ) );
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

    getGUI ().jGTIParsingTable.setColumnModel ( new PTTableColumnModel (
        getGrammar ().getTerminalSymbolSet () ) );
    getGUI ().jGTIParsingTable.getTableHeader ().setReorderingAllowed ( false );
    getGUI ().jGTIParsingTable.setCellSelectionEnabled ( true );

    init ();
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
      return getGrammar ().getNonterminalSymbolSet ().get ( row )
          .toPrettyString ();
    // col - 1 cause the first column are the nonterminals but they don't
    // count
    else if ( getUncoverMatrixEntry ( row, column - 1 ) )
      return CreateParsingTableGameDialog.this.parsingTable.get ( row,
          column - 1 ).toPrettyString ();
    return new PrettyString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#getSelectableActions()
   */
  @Override
  protected final ActionSet getSelectableActions ()
  {
    final ActionSet selectableActions = new DefaultActionSet ();
    final NonterminalSymbol ns = getGrammar ().getNonterminalSymbolSet ().get (
        getGUI ().jGTIParsingTable.getSelectedRow () );
    final ProductionSet dps = getGrammar ().getProductionForNonTerminal ( ns );
    for ( Production p : dps )
      try
      {
        selectableActions.add ( new ReverseReduceAction ( p ) );
      }
      catch ( ActionSetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    return selectableActions;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected final ActionSet getActionSetAt ( final int row, final int col )
  {
    final DefaultProductionSet dps = this.parsingTable.get ( row, col );
    final ActionSet actions = new DefaultActionSet ();
    for ( Production p : dps )
      try
      {
        actions.add ( new ReverseReduceAction ( p ) );
      }
      catch ( ActionSetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    return actions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.AbstractBaseGameDialog#actionSetsEquals(de.unisiegen.gtitool.core.entities.ActionSet,
   *      de.unisiegen.gtitool.core.entities.ActionSet)
   */
  @Override
  protected boolean actionSetsEquals ( final ActionSet actionSet1,
      final ActionSet actionSet2 )
  {
    boolean totalEquality = false;
    this.actionSetsPartialEqual = false;
    ActionSet cmpSource;
    ActionSet cmpTarget;
    if ( actionSet1.size () < actionSet2.size () )
    {
      cmpSource = actionSet1;
      cmpTarget = actionSet2;
    }
    else if ( actionSet2.size () < actionSet1.size () )
    {
      cmpSource = actionSet2;
      cmpTarget = actionSet1;
    }
    else
    {
      cmpSource = actionSet1;
      cmpTarget = actionSet2;
      totalEquality = true;
    }
    
    if(cmpSource.size () == 0 && cmpTarget.size () > 0)
      return false;

    for ( Action actionSrc : cmpSource )
    {
      ReverseReduceAction rraSrc = ( ReverseReduceAction ) actionSrc;
      boolean contained = false;
      for ( Action actionTgt : cmpTarget )
      {
        ReverseReduceAction rraTgt = ( ReverseReduceAction ) actionTgt;
        contained = contained
            || rraSrc.getReduceAction ().getProductionWord ().equals (
                rraTgt.getReduceAction ().getProductionWord () );
      }
      if ( !contained )
        return false;
    }
    if(!totalEquality)
      this.actionSetsPartialEqual = true;
    return true;
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
    return this.parsingTable.getReasonFor ( row, column );
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
    return this.parsingTable.get (
        getGrammar ().getNonterminalSymbolSet ().get ( row ),
        getGrammar ().getTerminalSymbolSet ().get ( column ) ).size ();
  }
}
