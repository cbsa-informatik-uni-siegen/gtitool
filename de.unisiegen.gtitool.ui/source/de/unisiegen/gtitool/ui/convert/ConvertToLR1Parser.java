package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
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
   */
  public ConvertToLR1Parser ( final MainWindowForm mainWindowForm,
      final Grammar grammar ) throws AlphabetException
  {
    super ( mainWindowForm, grammar );

    this.grammar = new LR1Grammar ( grammar.getNonterminalSymbolSet (), grammar
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
      this.machine = new DefaultLR1Parser ( this.grammar );
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
  private LR1Grammar grammar;


  /**
   * The associated machine
   */
  private DefaultLR1Parser machine;
}
