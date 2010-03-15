package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.lr.SLRParser;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Converts a grammar into an SLR parser
 */
public class ConvertToSLRParser extends ConvertToLRParser
{

  /**
   * Constructs a converter to an SLR parser out of the mainWindow and a grammar
   * 
   * @param mwf
   * @param grammar
   * @throws AlphabetException
   */
  public ConvertToSLRParser ( final MainWindowForm mwf, final Grammar grammar )
      throws AlphabetException
  {
    super ( mwf, grammar );

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
      this.machine = new SLRParser ( this.grammar );
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
  private SLRParser machine;
}
