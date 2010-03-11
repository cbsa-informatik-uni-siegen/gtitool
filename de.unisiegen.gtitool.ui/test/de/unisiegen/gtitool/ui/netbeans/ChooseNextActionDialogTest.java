package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.CancelOutAction;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.logic.ChooseNextActionDialog;


/**
 * Test class for the {@link ChooseNextActionDialog}
 * @author Christian Uhrhan
 */
public final class ChooseNextActionDialogTest
{

  /**
   * The main entry
   * 
   * @param args The arguments
   */
  public static void main ( String [] args )
  {
    NonterminalSymbol E = new DefaultNonterminalSymbol ( "E" ); //$NON-NLS-1$

    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();

    NonterminalSymbol T = new DefaultNonterminalSymbol ( "T" ); //$NON-NLS-1$
    NonterminalSymbol F = new DefaultNonterminalSymbol ( "F" ); //$NON-NLS-1$

    try
    {
      nonterminalSet.add ( E ); // ??
      nonterminalSet.add ( T );
      nonterminalSet.add ( F );
    }
    catch ( NonterminalSymbolSetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    TerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();

    TerminalSymbol id = new DefaultTerminalSymbol ( "id" ); //$NON-NLS-1$
    TerminalSymbol lparen = new DefaultTerminalSymbol ( "(" ); //$NON-NLS-1$
    TerminalSymbol rparen = new DefaultTerminalSymbol ( ")" ); //$NON-NLS-1$
    TerminalSymbol plus = new DefaultTerminalSymbol ( "+" ); //$NON-NLS-1$
    TerminalSymbol multiplies = new DefaultTerminalSymbol ( "*" ); //$NON-NLS-1$

    try
    {
      terminalSet.add ( id );
      terminalSet.add ( lparen );
      terminalSet.add ( rparen );
      terminalSet.add ( plus );
      terminalSet.add ( multiplies );
    }
    catch ( TerminalSymbolSetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    DefaultCFG grammar = new DefaultCFG ( nonterminalSet, terminalSet, E );

    grammar.addProduction ( new DefaultProduction ( E,
        new DefaultProductionWord ( E, plus, T ) ) );

    grammar.addProduction ( new DefaultProduction ( E,
        new DefaultProductionWord ( T ) ) );

    grammar.addProduction ( new DefaultProduction ( T,
        new DefaultProductionWord ( T, multiplies, F ) ) );

    grammar.addProduction ( new DefaultProduction ( T,
        new DefaultProductionWord ( F ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( id ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( lparen, E, rparen ) ) );

    ActionSet actions = new DefaultActionSet ();
    try
    {
      actions.add ( new ReduceAction ( grammar.getProductionAt ( 0 ) ) );
      actions.add ( new ReduceAction ( grammar.getProductionAt ( 1 ) ) );
      actions.add ( new CancelOutAction () );
    }
    catch ( ActionSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    JFrame frame = new JFrame ();
    frame.setBounds ( 300, 300, 300, 300 );
    frame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    frame.setVisible ( true );

    ChooseNextActionDialog cnad = new ChooseNextActionDialog ( frame, actions );
    cnad.show ();
    if ( cnad.isConfirmed () )
    {
      Action chosenAction = cnad.getChosenAction ();
      System.out.println ( chosenAction );
    } else
      System.out.println ( "ChooseNextActionDialog was canceled"); //$NON-NLS-1$
  }

}
