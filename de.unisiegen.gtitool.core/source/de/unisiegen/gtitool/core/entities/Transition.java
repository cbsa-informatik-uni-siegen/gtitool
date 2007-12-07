package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.storage.exceptions.StoreWarningException;


/**
 * The <code>Transition</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Transition implements Entity, Storable
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7649068993385065572L;


  /**
   * The {@link Alphabet} of this <code>Transition</code>.
   */
  private Alphabet alphabet;


  /**
   * The id of this <code>Transition</code>.
   */
  private int id = ID_NOT_DEFINED;


  /**
   * The {@link State} where the <code>Transition</code> begins.
   */
  private State stateBegin;


  /**
   * The {@link State} id where the <code>Transition</code> begins.
   */
  private int stateBeginId;


  /**
   * The {@link State} where the <code>Transition</code> ends.
   */
  private State stateEnd;


  /**
   * The {@link State} id where the <code>Transition</code> ends.
   */
  private int stateEndId;


  /**
   * The list of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * The warning list.
   */
  private ArrayList < StoreWarningException > warningList;


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
      Iterable < Symbol > pSymbols )
      throws TransitionSymbolNotInAlphabetException,
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
    addSymbol ( pSymbols );
  }


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
    addSymbol ( pSymbols );
  }


  /**
   * Allocates a new <code>Transition</code>.
   * 
   * @param pElement The {@link Element}. *
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public Transition ( Element pElement )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException, SymbolException, AlphabetException,
      StoreException
  {
    // Check if the element is correct
    if ( !pElement.getName ().equals ( "Transition" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a transition" ); //$NON-NLS-1$
    }

    // Symbols
    this.symbolSet = new TreeSet < Symbol > ();

    // Attribute
    boolean foundId = false;
    boolean foundStateBeginId = false;
    boolean foundStateEndId = false;
    for ( Attribute current : pElement.getAttribute () )
    {
      if ( current.getName ().equals ( "id" ) ) //$NON-NLS-1$
      {
        setId ( current.getValueInt () );
        foundId = true;
      }
      else if ( current.getName ().equals ( "stateBeginId" ) ) //$NON-NLS-1$
      {
        setStateBeginId ( current.getValueInt () );
        foundStateBeginId = true;
      }
      else if ( current.getName ().equals ( "stateEndId" ) ) //$NON-NLS-1$
      {
        setStateEndId ( current.getValueInt () );
        foundStateEndId = true;
      }
      else
      {
        this.warningList.add ( new StoreWarningException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ) ); //$NON-NLS-1$
      }
    }

    // Not all attribute values found
    if ( ( !foundId ) || ( !foundStateBeginId ) || ( !foundStateEndId ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingElement" ) ); //$NON-NLS-1$
    }

    // Element
    boolean foundAlphabet = false;
    for ( Element current : pElement.getElement () )
    {
      if ( current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        setAlphabet ( new Alphabet ( current ) );
        foundAlphabet = true;
      }
      else if ( current.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        addSymbol ( new Symbol ( current ) );
      }
      else
      {
        this.warningList.add ( new StoreWarningException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ) ); //$NON-NLS-1$
      }
    }

    // Not all element values found
    if ( !foundAlphabet )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingElement" ) ); //$NON-NLS-1$
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
  public final void addSymbol ( Iterable < Symbol > pSymbols )
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
    if ( !this.alphabet.contains ( pSymbol ) )
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
  public final void addSymbol ( Symbol ... pSymbols )
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
    try
    {
      newTransition = new Transition ( this.alphabet.clone (), this.stateBegin
          .clone (), this.stateEnd.clone () );
      for ( Symbol current : this.symbolSet )
      {
        newTransition.addSymbol ( current.clone () );
      }
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return null;
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
      if ( ( this.id == ID_NOT_DEFINED ) || ( other.id == ID_NOT_DEFINED ) )
      {
        throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
      }
      return this.id == other.id;
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
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "Transition" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "id", this.id ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "stateBeginId", //$NON-NLS-1$
        getStateBeginId () ) );
    newElement.addAttribute ( new Attribute ( "stateEndId", getStateEndId () ) ); //$NON-NLS-1$
    newElement.addElement ( this.alphabet );
    for ( Symbol current : this.symbolSet )
    {
      newElement.addElement ( current );
    }
    return newElement;
  }


  /**
   * Returns the id.
   * 
   * @return The id.
   * @see #id
   */
  public final int getId ()
  {
    return this.id;
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
   * Returns the {@link State} id where the <code>Transition</code> begins.
   * 
   * @return The {@link State} id where the <code>Transition</code> begins.
   * @see #stateBeginId
   */
  public final int getStateBeginId ()
  {
    return this.stateBegin == null ? this.stateBeginId : this.stateBegin
        .getId ();
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
   * Returns the {@link State} id where the <code>Transition</code> ends.
   * 
   * @return The {@link State} id where the <code>Transition</code> ends.
   * @see #stateEndId
   */
  public final int getStateEndId ()
  {
    return this.stateEnd == null ? this.stateEndId : this.stateEnd.getId ();
  }


  /**
   * Returns the symbolSet.
   * 
   * @return The symbolSet.
   * @see #symbolSet
   */
  public final TreeSet < Symbol > getSymbol ()
  {
    return this.symbolSet;
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
   * {@inheritDoc}
   * 
   * @see Storable#getWarning()
   */
  public ArrayList < StoreWarningException > getWarning ()
  {
    return this.warningList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getWarning(int)
   */
  public StoreWarningException getWarning ( int pIndex )
  {
    return this.warningList.get ( pIndex );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    if ( this.id == ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
    }
    return this.id;
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
   * Returns true if the id of this <code>Transition</code> is defined,
   * otherwise false.
   * 
   * @return True if the id of this <code>Transition</code> is defined,
   *         otherwise false.
   */
  public final boolean isIdDefined ()
  {
    return this.id != ID_NOT_DEFINED;
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
   * Sets the id.
   * 
   * @param pId The id to set.
   * @see #id
   */
  public final void setId ( int pId )
  {
    if ( this.id != ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is already setted" ); //$NON-NLS-1$
    }
    this.id = pId;
  }


  /**
   * Sets the {@link State} where the <code>Transition</code> begins.
   * 
   * @param pStateBegin The {@link State} to set.
   */
  public final void setStateBegin ( State pStateBegin )
  {
    if ( pStateBegin == null )
    {
      throw new NullPointerException ( "state begin is null" ); //$NON-NLS-1$
    }
    this.stateBegin = pStateBegin;
  }


  /**
   * Sets the {@link State} id where the <code>Transition</code> begins.
   * 
   * @param pStateBeginId The {@link State} id to set.
   */
  private final void setStateBeginId ( int pStateBeginId )
  {
    if ( this.stateBegin != null )
    {
      throw new IllegalArgumentException (
          "can not set the id if there is a state" ); //$NON-NLS-1$
    }
    this.stateBeginId = pStateBeginId;
  }


  /**
   * Sets the {@link State} where the <code>Transition</code> ends.
   * 
   * @param pStateEnd The {@link State} to set.
   */
  public final void setStateEnd ( State pStateEnd )
  {
    if ( pStateEnd == null )
    {
      throw new NullPointerException ( "state end is null" ); //$NON-NLS-1$
    }
    this.stateEnd = pStateEnd;
  }


  /**
   * Sets the {@link State} id where the <code>Transition</code> ends.
   * 
   * @param pStateEndId The {@link State} id to set.
   */
  private final void setStateEndId ( int pStateEndId )
  {
    if ( this.stateEnd != null )
    {
      throw new IllegalArgumentException (
          "can not set the id if there is a state" ); //$NON-NLS-1$
    }
    this.stateEndId = pStateEndId;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    if ( this.symbolSet.size () == 0 )
    {
      result.append ( "\u03B5" ); //$NON-NLS-1$
    }
    else
    {
      result.append ( "{" ); //$NON-NLS-1$
      Iterator < Symbol > iterator = this.symbolSet.iterator ();
      boolean first = true;
      while ( iterator.hasNext () )
      {
        if ( !first )
        {
          result.append ( ", " ); //$NON-NLS-1$
        }
        first = false;
        result.append ( iterator.next () );
      }
      result.append ( "}" ); //$NON-NLS-1$
    }
    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toStringDebug()
   */
  public final String toStringDebug ()
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
