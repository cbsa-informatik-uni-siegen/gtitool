package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;


/**
 * The <code>Transition</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Transition implements Entity
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7649068993385065572L;


  /**
   * The {@link Alphabet} of this <code>Transition</code>.
   */
  private Alphabet alphabet;


  /**
   * The {@link State} where the <code>Transition</code> begins.
   */
  private State stateBegin;


  /**
   * The {@link State} where the <code>Transition</code> ends.
   */
  private State stateEnd;


  /**
   * The list of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new <code>Transition</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>Transition</code>.
   * @param pStateBegin The {@link State} where the <code>Transition</code>
   *          begins.
   * @param pStateEnd The {@link State} where the <code>Transition</code>
   *          ends.
   * @param pSymbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public Transition ( Alphabet pAlphabet, State pStateBegin, State pStateEnd,
      Symbol ... pSymbols ) throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Alphabet
    setAlphabet ( pAlphabet );
    // StateBegin
    setStateBegin ( pStateBegin );
    // StateEnd
    setStateEnd ( pStateEnd );
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    addSymbols ( pSymbols );
  }
  
  //TODOChristian check this please
  /**
   * Allocates a new <code>Transition</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>Transition</code>.
   * @param pStateBegin The {@link State} where the <code>Transition</code>
   *          begins.
   * @param pStateEnd The {@link State} where the <code>Transition</code>
   *          ends.
   * @param pSymbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public Transition ( Alphabet pAlphabet, State pStateBegin, State pStateEnd,
      TreeSet < Symbol > pSymbols ) throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Alphabet
    setAlphabet ( pAlphabet );
    // StateBegin
    setStateBegin ( pStateBegin );
    // StateEnd
    setStateEnd ( pStateEnd );
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    addSymbols ( pSymbols );
  }


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public final void addSymbol ( Symbol pSymbol )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( !this.alphabet.containsSymbol ( pSymbol ) )
    {
      throw new TransitionSymbolNotInAlphabetException ( this, this.alphabet,
          pSymbol );
    }
    if ( this.symbolSet.contains ( pSymbol ) )
    {
      throw new TransitionSymbolOnlyOneTimeException ( this, pSymbol );
    }
    this.symbolSet.add ( pSymbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public final void addSymbols ( Iterable < Symbol > pSymbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      addSymbol ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public final void addSymbols ( Symbol ... pSymbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      addSymbol ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final Transition clone ()
  {
    Transition newTransition = null;
    for ( Symbol current : this.symbolSet )
    {
      try
      {
        newTransition = new Transition ( this.alphabet.clone (),
            this.stateBegin.clone (), this.stateEnd.clone () );
        newTransition.addSymbol ( current.clone () );
      }
      catch ( TransitionSymbolNotInAlphabetException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TransitionSymbolOnlyOneTimeException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
    }
    return newTransition;
  }


  /**
   * Returns true if the {@link Alphabet} of this <code>Transition</code>
   * contains the given {@link Symbol}. Otherwise false.
   * 
   * @param pSymbol The {@link Symbol}.
   * @return True if the {@link Alphabet} of this <code>Transition</code>
   *         contains the given {@link Symbol}. Otherwise false.
   */
  public final boolean containsSymbol ( Symbol pSymbol )
  {
    return this.symbolSet.contains ( pSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof Transition )
    {
      Transition other = ( Transition ) pOther;
      return ( ( this.alphabet.equals ( other.alphabet ) )
          && ( this.stateBegin.equals ( other.stateBegin ) )
          && ( this.stateEnd.equals ( other.stateEnd ) ) && ( this.symbolSet
          .equals ( other.symbolSet ) ) );
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
   * Returns the {@link State} where the <code>Transition</code> begins.
   * 
   * @return The {@link State} where the <code>Transition</code> begins.
   * @see #stateBegin
   */
  public final State getStateBegin ()
  {
    return this.stateBegin;
  }


  /**
   * Returns the {@link State} where the <code>Transition</code> ends.
   * 
   * @return The {@link State} where the <code>Transition</code> ends.
   * @see #stateEnd
   */
  public final State getStateEnd ()
  {
    return this.stateEnd;
  }


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol getSymbol ( int pIndex )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < pIndex ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * Returns the symbolSet.
   * 
   * @return The symbolSet.
   * @see #symbolSet
   */
  public final TreeSet < Symbol > getSymbolSet ()
  {
    return this.symbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.alphabet.hashCode () + this.stateBegin.hashCode ()
        + this.stateEnd.hashCode () + this.symbolSet.hashCode ();
  }


  /**
   * Returns true, if this <code>Transition</code> is a epsilon
   * <code>Transition</code>, otherwise false.
   * 
   * @return True, if this <code>Transition</code> is a epsilon
   *         <code>Transition</code>, otherwise false.
   */
  public final boolean isEpsilonTransition ()
  {
    return this.symbolSet.size () == 0;
  }


  /**
   * Sets the {@link Alphabet} of this <code>Transition</code>.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  private final void setAlphabet ( Alphabet pAlphabet )
  {
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
  }


  /**
   * Sets the {@link State} where the <code>Transition</code> begins.
   * 
   * @param pStateBegin The {@link State} to set.
   */
  private final void setStateBegin ( State pStateBegin )
  {
    if ( pStateBegin == null )
    {
      throw new NullPointerException ( "state begin is null" ); //$NON-NLS-1$
    }
    this.stateBegin = pStateBegin;
  }


  /**
   * Sets the {@link State} where the <code>Transition</code> ends.
   * 
   * @param pStateEnd The {@link State} to set.
   */
  private final void setStateEnd ( State pStateEnd )
  {
    if ( pStateEnd == null )
    {
      throw new NullPointerException ( "state end is null" ); //$NON-NLS-1$
    }
    this.stateEnd = pStateEnd;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Alphabet:    " + this.alphabet.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "Begin state: " + this.stateBegin.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "End state:   " + this.stateEnd.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "Symbols:     " + this.symbolSet.toString () ); //$NON-NLS-1$
    return result.toString ();
  }
}
