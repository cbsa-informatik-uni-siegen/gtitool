package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR0Parser;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Converts a grammar into an LR0 Parser
 */
public class ConvertToLR0Parser extends ConvertToLRParser
{

  /**
   * Construct the converter out of the mainWindow and a grammar
   * 
   * @param mainWindowForm
   * @param grammar
   * @throws AlphabetException
   */
  public ConvertToLR0Parser ( final MainWindowForm mainWindowForm,
      final Grammar grammar ) throws AlphabetException
  {
    super ( mainWindowForm, grammar );

    this.grammar = new LR0Grammar ( grammar.getNonterminalSymbolSet (), grammar
        .getTerminalSymbolSet (), grammar.getStartSymbol (), grammar
        .getProduction () );
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
      this.machine = new DefaultLR0Parser ( this.grammar );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    createMachinePanel ( this.machine );
  }


  /**
   * The associated grammar
   */
  private LR0Grammar grammar;


  /**
   * The associated machine
   */
  private DefaultLR0Parser machine;
}
