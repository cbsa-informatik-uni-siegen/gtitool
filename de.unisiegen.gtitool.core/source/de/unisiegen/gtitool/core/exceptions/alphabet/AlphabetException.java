package de.unisiegen.gtitool.core.exceptions.alphabet;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>AlphabetException</code> is used if the {@link Alphabet} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8267857615201989774L;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The {@link Symbol}.
   */
  private Symbol symbol;


  /**
   * Allocates a new <code>AlphabetException</code>.
   * 
   * @param pAlphabet The {@link Alphabet}.
   * @param pSymbol The {@link Symbol}.
   */
  public AlphabetException ( Alphabet pAlphabet, Symbol pSymbol )
  {
    super ();
    // Alphabet
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbol = pSymbol;
    // Message and Description
    setMessage ( Messages
        .getString ( "AlphabetException.MoreThanOneSymbolMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "AlphabetException.MoreThanOneSymbolDescription", this.symbol //$NON-NLS-1$
            .getName (), this.alphabet.toString () ) );
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the {@link Symbol}.
   * 
   * @return The {@link Symbol}.
   * @see #symbol
   */
  public final Symbol getSymbol ()
  {
    return this.symbol;
  }
}
