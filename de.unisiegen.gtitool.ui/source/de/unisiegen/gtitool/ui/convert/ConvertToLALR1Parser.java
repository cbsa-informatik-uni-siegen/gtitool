package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR1Parser;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert an LR1 parser to an LALR1 parser The result might contain ambigious
 * actions
 */
public class ConvertToLALR1Parser extends ConvertToLRParser
{

  /**
   * TODO
   * 
   * @param mainWindow
   * @param grammar
   * @throws AlphabetException
   * @throws NonterminalSymbolSetException
   */
  public ConvertToLALR1Parser ( final MainWindowForm mainWindow,
      final Grammar grammar ) throws AlphabetException,
      NonterminalSymbolSetException
  {
    super ( mainWindow, new LR1Grammar ( grammar ) );
  }


  /**
   * The resulting parser
   */
  private DefaultLR1Parser result;


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    try
    {
      final LR1Grammar lr1Grammar = ( LR1Grammar ) super.getGrammar ();

      final LR1 oldAutomaton = new LR1 ( lr1Grammar );

      final LR1 newAutomaton = oldAutomaton.toLALR1 ();

      this.result = new DefaultLR1Parser ( newAutomaton, lr1Grammar );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionSymbolNotInAlphabetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
    }

    createMachinePanel ( this.result );
  }
}
