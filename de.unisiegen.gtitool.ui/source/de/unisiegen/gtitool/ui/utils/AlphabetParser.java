package de.unisiegen.gtitool.ui.utils;


import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * This class checks the input of an {@link Alphabet}.
 * 
 * @author Christian Fehler
 * @version $Id: ColorItem.java 90 2007-11-04 16:20:27Z fehler $
 */
public abstract class AlphabetParser
{

  /**
   * Checks the {@link Alphabet} input and returns true if everything is
   * correct, otherwise false.
   * 
   * @param pKey The key which should be checked.
   * @return True if everything is correct, otherwise false.
   */
  public static final boolean checkInput ( char pKey )
  {
    char [] allowedKeys = new char []
    { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
        'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', ' ', ',' };
    boolean found = false;
    for ( char current : allowedKeys )
    {
      if ( pKey == current )
      {
        found = true;
        break;
      }
    }
    if ( ( !found ) && ( pKey != 8 ) && ( pKey != 127 ) )
    {
      return false;
    }
    return true;
  }


  /**
   * Creates the output string of the given {@link Alphabet}.
   * 
   * @param pAlphabet The input {@link Alphabet}.
   * @return The output string of the given {@link Alphabet}.
   */
  public static String createString ( Alphabet pAlphabet )
  {
    StringBuilder alphabetText = new StringBuilder ();
    for ( int i = 0 ; i < pAlphabet.symbolSize () ; i++ )
    {
      if ( i != 0 )
      {
        alphabetText.append ( ", " ); //$NON-NLS-1$
      }
      alphabetText.append ( pAlphabet.getSymbol ( i ) );
    }
    return alphabetText.toString ();
  }
}
