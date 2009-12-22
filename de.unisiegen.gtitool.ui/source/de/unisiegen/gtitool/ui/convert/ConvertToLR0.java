package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public class ConvertToLR0 extends AbstractConvertGrammar
{

  private DefaultStateView finalStateView;


  /**
   * TODO
   * 
   * @param mainWindowForm
   * @param grammar
   */

  private static LR0Grammar convertGrammar ( Grammar grammar )
  {
    LR0Grammar ret = new LR0Grammar ( grammar.getNonterminalSymbolSet (), grammar
        .getTerminalSymbolSet (), grammar.getStartSymbol () );
    
    for(Production prod : grammar.getProduction())
      ret.addProduction ( prod );
    return ret;
  }


  public ConvertToLR0 ( MainWindowForm mainWindowForm, Grammar grammar )
      throws AlphabetException
  {
    super ( mainWindowForm, grammar, convertGrammar ( grammar )
        .makeAutomataAlphabet () );

    this.lr0Grammar = convertGrammar ( grammar );

    this.setPushDownAlphabet ( new DefaultAlphabet () );
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
      createMachinePanel ( this.lr0Grammar.makeLR0Automata () );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }

    this.finalStateView = createStateView ( null );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#performProductions()
   */
  @Override
  protected void performProductions ()
  {
    
  }


  private LR0Grammar lr0Grammar;
}