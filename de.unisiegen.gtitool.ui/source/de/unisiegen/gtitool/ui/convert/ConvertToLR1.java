package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public class ConvertToLR1 extends ConvertToLR
{

  /**
   * TODO
   * 
   * @param grammar
   */
  private static LR1Grammar convertGrammar ( Grammar grammar )
  {
    LR1Grammar ret = new LR1Grammar ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol () );

    for ( Production prod : grammar.getProduction () )
      ret.addProduction ( prod );
    return ret;
  }


  public ConvertToLR1 ( MainWindowForm mainWindowForm, Grammar grammar )
      throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar )
        .makeAutomatonAlphabet () );

    this.lr1Grammar = convertGrammar ( grammar );
  }


  private LR1Grammar lr1Grammar;


  private LR1 machine;


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.convert.ConvertToLR#getMachine()
   */
  @Override
  protected AbstractMachine getMachine ()
  {
    return this.machine;
  }


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
      this.machine = new LR1 ( this.lr1Grammar );

      createMachinePanel ( this.machine );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }
  }
}
