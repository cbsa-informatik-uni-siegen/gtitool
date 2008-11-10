package de.unisiegen.gtitool.core.entities;

import java.util.ArrayList;

import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetReservedSymbolException;


/**
 * TODO
 *
 */
public class DefaultRegexAlphabet extends DefaultAlphabet
{
  
  public static final Symbol[] RESERVED_SYMBOLS = {new DefaultSymbol("!"), new DefaultSymbol("?"), new DefaultSymbol("*"), new DefaultSymbol("+"), new DefaultSymbol("·")};

  /**
   * TODO
   */
  private static final long serialVersionUID = -7159328187623780150L;
  
  
  
  /**
   * TODO
   * @throws AlphabetException
   *
   */
  public DefaultRegexAlphabet (Iterable < Symbol > symbols) throws AlphabetException
  {
    super(symbols);
  }
  
  
  /**
   * TODO
   * @throws AlphabetException
   *
   */
  public DefaultRegexAlphabet (Symbol ... symbols) throws AlphabetException
  {
    super(symbols);
  }
  
  /**
   * TODO
   *
   * @param symbol
   * @throws AlphabetException
   * @see de.unisiegen.gtitool.core.entities.DefaultAlphabet#add(de.unisiegen.gtitool.core.entities.Symbol)
   */
  @Override
  public void add ( Symbol symbol ) throws AlphabetException
  {
    checkReservedSymbols ( symbol );
    super.add ( symbol );
  }
  
  private void checkReservedSymbols(Symbol symbol) throws AlphabetException {
    ArrayList<Symbol> list = new ArrayList < Symbol >();
    for(Symbol reserved : RESERVED_SYMBOLS) {
      if(reserved.equals ( symbol )) {
        list.add ( symbol );
        throw new AlphabetReservedSymbolException(this, list);
      }
    }
    
  }

}
