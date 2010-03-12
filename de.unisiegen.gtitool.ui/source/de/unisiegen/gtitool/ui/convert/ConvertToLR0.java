package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
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
   * @return The lr0 grammar
   */
  private static LR0Grammar convertGrammar ( Grammar grammar )
  {
    LR0Grammar ret = new LR0Grammar ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol () );

    for ( Production prod : grammar.getProduction () )
      ret.addProduction ( prod );
    return ret;
  }


  /**
   * TODO
   * 
   * @param mainWindowForm - The main window to display the result on
   * @param grammar - The grammar to convert to an LR0 automaton
   * @throws AlphabetException
   */
  public ConvertToLR0 ( final MainWindowForm mainWindowForm,
      final Grammar grammar ) throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar ).getAlphabet () );

    this.lr0Grammar = convertGrammar ( grammar );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammarStateMachine#createMachine()
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


  /**
   * TODO
   * 
   * @return the machine
   * @see de.unisiegen.gtitool.ui.convert.ConvertToLR#getMachine()
   */
  @Override
  protected AbstractStateMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * The associated grammar
   */
  private LR0Grammar lr0Grammar;


  /**
   * The associated machine
   */
  private LR0 machine;
}
