package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR1Parser;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a grammar into an LR1 parser
 */
public class ConvertToLR1Parser extends ConvertToLRParser
{

  /**
   * Constructs a converted out of the mainWindowForm and a grammar
   * 
   * @param mainWindowForm
   * @param grammar
   * @throws AlphabetException
   * @throws NonterminalSymbolSetException
   */
  public ConvertToLR1Parser ( final MainWindowForm mainWindowForm,
      final Grammar grammar ) throws AlphabetException,
      NonterminalSymbolSetException
  {
    super ( mainWindowForm, new LR1Grammar ( grammar ) );

    this.grammar = ( LR1Grammar ) super.getGrammar ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    try
    {
      this.machine = new DefaultLR1Parser ( this.grammar );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionSymbolNotInAlphabetException exc )
    {
      exc.printStackTrace();
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace();
    }
    catch ( StateException exc )
    {
      exc.printStackTrace();
    }

    createMachinePanel ( this.machine );
  }


  /**
   * The associated grammar
   */
  private LR1Grammar grammar;


  /**
   * The associated machine
   */
  private DefaultLR1Parser machine;
}
