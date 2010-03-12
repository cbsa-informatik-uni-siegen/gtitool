package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * The Grammar to LR1 conversion class
 */
public class ConvertToLR1 extends ConvertToLR
{

  /**
   * Converts a grammar to an LR1 grammar
   * 
   * @param grammar
   * @return The grammar
   */
  private static LR1Grammar convertGrammar ( final Grammar grammar )
  {
    LR1Grammar ret = new LR1Grammar ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol () );

    for ( Production prod : grammar.getProduction () )
      ret.addProduction ( prod );
    return ret;
  }


  /**
   * Constructs a converted from the mainWindow and a grammar
   * 
   * @param mainWindowForm
   * @param grammar
   * @throws AlphabetException
   */
  public ConvertToLR1 ( final MainWindowForm mainWindowForm,
      final Grammar grammar ) throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar ).getAlphabet () );

    this.lr1Grammar = convertGrammar ( grammar );
  }


  /**
   * The associated grammar
   */
  private LR1Grammar lr1Grammar;


  /**
   * The associated machine
   */
  private LR1 machine;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.ConvertToLR#getMachine()
   */
  @Override
  protected AbstractStateMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammarStateMachine#createMachine()
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
