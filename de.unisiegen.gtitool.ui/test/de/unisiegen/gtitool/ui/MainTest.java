package de.unisiegen.gtitool.ui;


import java.math.BigInteger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.ui.exchange.encryption.RSAPublicKeyImpl;


/**
 * This class can be used for simple test.
 * 
 * @author Christian Fehler
 */
public final class MainTest
{

  /**
   * The main method.
   * 
   * @param arguments The command line arguments.
   */
  public static void main ( String [] arguments )
  {
    new MainTest ();
  }


  /**
   * Allocates a new {@link MainTest}.
   */
  public MainTest ()
  {
    testCompare ();
  }


  /**
   * Tests the compare.
   */
  public final void testCompare ()
  {
    try
    {
      Symbol a = new DefaultSymbol ( "a" ); //$NON-NLS-1$
      Symbol b = new DefaultSymbol ( "b" ); //$NON-NLS-1$
      Symbol c = new DefaultSymbol ( "c" ); //$NON-NLS-1$
      Symbol d = new DefaultSymbol ( "d" ); //$NON-NLS-1$
      Symbol e = new DefaultSymbol ( "e" ); //$NON-NLS-1$
      Symbol f = new DefaultSymbol ( "f" ); //$NON-NLS-1$

      Alphabet alphabet1 = new DefaultAlphabet ( a, b, c, f );
      Alphabet alphabet2 = new DefaultAlphabet ( a, b, d, e );

      System.out.println ( alphabet1.compareTo ( alphabet2 ) );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Tests the keys.
   */
  public final void testKeys ()
  {
    RSAPublicKeyImpl key1 = new RSAPublicKeyImpl ( BigInteger
        .valueOf ( 263487692 ), BigInteger.valueOf ( 892647263 ) );
    System.out.println ( key1 );

    byte [] encoded = key1.getEncoded ();

    RSAPublicKeyImpl key2 = new RSAPublicKeyImpl ( encoded );
    System.out.println ( key2 );
  }
}
