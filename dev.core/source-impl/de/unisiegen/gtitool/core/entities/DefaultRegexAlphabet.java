package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetReservedSymbolException;


/**
 * An {@link Alphabet} for {@link Regex}es that checks if Symbols are reserved
 * for Regex
 */
public class DefaultRegexAlphabet extends DefaultAlphabet
{

  /**
   * The reserved {@link Symbol}s
   */
  public static final Symbol [] RESERVED_SYMBOLS =
  {
      new DefaultSymbol ( "!" ), new DefaultSymbol ( "?" ), new DefaultSymbol ( "*" ), new DefaultSymbol ( "+" ), new DefaultSymbol ( "·" ), new DefaultSymbol ( "#" ) }; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7159328187623780150L;


  /**
   * Creates new {@link DefaultRegexAlphabet}
   * 
   * @param symbols The {@link Symbol}s of the Alphabet
   * @throws AlphabetException When a reserved symbol is in it
   */
  public DefaultRegexAlphabet ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    super ( symbols );
  }


  /**
   * Creates new {@link DefaultRegexAlphabet}
   * 
   * @param symbols The {@link Symbol}s of the Alphabet
   * @throws AlphabetException When a reserved symbol is in it
   */
  public DefaultRegexAlphabet ( Symbol ... symbols ) throws AlphabetException
  {
    super ( symbols );
  }


  /**
   * Adds a {@link Symbol} to the Alphabet
   * 
   * @param symbol The {@link Symbol} to add to the Alphabet
   * @throws AlphabetException When symbol is reserved
   */
  @Override
  public void add ( Symbol symbol ) throws AlphabetException
  {
    checkReservedSymbols ( symbol );
    super.add ( symbol );
  }


  /**
   * Checks {@link DefaultRegexAlphabet} for {@link Symbol}s that are reserved
   * 
   * @param symbol The {@link Symbol} to check
   * @throws AlphabetException Is thrown when symbol is reserved
   */
  private void checkReservedSymbols ( Symbol symbol ) throws AlphabetException
  {
    ArrayList < Symbol > list = new ArrayList < Symbol > ();
    for ( Symbol reserved : RESERVED_SYMBOLS )
    {
      if ( reserved.equals ( symbol ) )
      {
        list.add ( symbol );
        throw new AlphabetReservedSymbolException ( this, list );
      }
    }

  }

}
