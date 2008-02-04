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
   * Allocates a new {@link AlphabetItem}.
   * 
   * @param alphabet The {@link Alphabet} of this item.
   * @param standardAlphabet The standard {@link Alphabet} of this item.
   */
  public AlphabetItem ( Alphabet alphabet, Alphabet standardAlphabet )
  {
    // Alphabet
    setAlphabet ( alphabet );
    // StandardAlphabet
    setStandardAlphabet ( standardAlphabet );
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
  public final boolean equals ( Object other )
  {
    if ( other instanceof AlphabetItem )
    {
      AlphabetItem alphabetItem = ( AlphabetItem ) other;
      return ( ( this.alphabet.equals ( alphabetItem.alphabet ) ) && ( this.standardAlphabet
          .equals ( alphabetItem.standardAlphabet ) ) );
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
   * @param alphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet alphabet )
  {
    if ( alphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = alphabet;
  }


  /**
   * Sets the standard {@link Alphabet}.
   * 
   * @param standardAlphabet The standard {@link Alphabet} to set.
   */
  public final void setStandardAlphabet ( Alphabet standardAlphabet )
  {
    if ( standardAlphabet == null )
    {
      throw new NullPointerException ( "standardalphabet is null" ); //$NON-NLS-1$
    }
    this.standardAlphabet = standardAlphabet;
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
