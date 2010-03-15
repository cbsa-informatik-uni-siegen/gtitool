package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public abstract class ConvertToLRParser extends
    AbstractConvertGrammarStatelessMachine
{

  /**
   * TODO
   * 
   * @param mwf
   * @param grammar
   * @throws AlphabetException
   */
  public ConvertToLRParser ( final MainWindowForm mwf, final Grammar grammar )
      throws AlphabetException
  {
    super ( mwf, grammar );

    grammar.getTerminalSymbolSet ().addIfNonexistent (
        DefaultTerminalSymbol.EndMarker );
  }

}
