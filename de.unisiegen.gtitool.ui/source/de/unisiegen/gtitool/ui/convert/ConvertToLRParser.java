package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public abstract class ConvertToLRParser extends AbstractConvertGrammarStatelessMachine
{

  /**
   * TODO
   * 
   * @param mwf
   * @param grammar
   * @throws AlphabetException
   */
  public ConvertToLRParser ( MainWindowForm mwf, Grammar grammar )
      throws AlphabetException
  {
    super ( mwf, grammar );
  }

}
