package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The {@link Alphabet} item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetItem implements Cloneable
{

  /**
   * The {@link Alphabet} of this item.
   */
  private Alphabet alphabet;


  /**
   * The standard {@link Alphabet} of this item.
   */
  private Alphabet standardAlphabet;


  /**
   * Allocates a new <code>ColorItem</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this item.
   * @param pStandardAlphabet The standard {@link Alphabet} of this item.
   */
  public AlphabetItem ( Alphabet pAlphabet, Alphabet pStandardAlphabet )
  {
    // Alphabet
    setAlphabet ( pAlphabet );
    // StandardAlphabet
    setStandardAlphabet ( pStandardAlphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final AlphabetItem clone ()
  {
    return new AlphabetItem ( this.alphabet.clone (), this.standardAlphabet
        .clone () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof AlphabetItem )
    {
      AlphabetItem other = ( AlphabetItem ) pOther;
      return ( ( this.alphabet.equals ( other.alphabet ) ) && ( this.standardAlphabet
          .equals ( other.standardAlphabet ) ) );
    }
    return false;
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
   * Returns the standard {@link Alphabet}.
   * 
   * @return The standard {@link Alphabet}.
   * @see #standardAlphabet
   */
  public final Alphabet getStandardAlphabet ()
  {
    return this.standardAlphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.alphabet.hashCode () + this.standardAlphabet.hashCode ();
  }


  /**
   * Restores the default {@link Alphabet} of this item.
   */
  public final void restore ()
  {
    this.alphabet = this.standardAlphabet;
  }


  /**
   * Sets the {@link Alphabet}.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet pAlphabet )
  {
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
  }


  /**
   * Sets the standard {@link Alphabet}.
   * 
   * @param pStandardAlphabet The standard {@link Alphabet} to set.
   */
  public final void setStandardAlphabet ( Alphabet pStandardAlphabet )
  {
    if ( pStandardAlphabet == null )
    {
      throw new NullPointerException ( "standardalphabet is null" ); //$NON-NLS-1$
    }
    this.standardAlphabet = pStandardAlphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.alphabet.toString ();
  }
}
