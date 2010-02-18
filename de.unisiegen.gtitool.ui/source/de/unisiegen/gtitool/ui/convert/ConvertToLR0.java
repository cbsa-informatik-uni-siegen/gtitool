package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR0;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public class ConvertToLR0 extends ConvertToLR
{

  /**
   * TODO
   * 
   * @param grammar
   */
  private static LR0Grammar convertGrammar ( Grammar grammar )
  {
    LR0Grammar ret = new LR0Grammar ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol () );

    for ( Production prod : grammar.getProduction () )
      ret.addProduction ( prod );
    return ret;
  }


  public ConvertToLR0 ( MainWindowForm mainWindowForm, Grammar grammar )
      throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar )
        .makeAutomatonAlphabet () );

    this.lr0Grammar = convertGrammar ( grammar );
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
      this.machine = new LR0 ( this.lr0Grammar );

      createMachinePanel ( this.machine );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }
  }


  protected AbstractMachine getMachine ()
  {
    return this.machine;
  }


  private LR0Grammar lr0Grammar;


  private LR0 machine;
}
