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


/**
 * The <code>DefaultTransition</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultTransition implements Transition
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7649068993385065572L;


  /**
   * The start offset of this <code>DefaultTransition</code> in the source
   * code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  private int parserStartOffset = NO_PARSER_OFFSET;


  /**
   * The end offset of this <code>DefaultTransition</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  private int parserEndOffset = NO_PARSER_OFFSET;


  /**
   * The {@link Alphabet} of this <code>DefaultTransition</code>.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet} of this <code>DefaultTransition</code>.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The id of this <code>DefaultTransition</code>.
   */
  private int id = ID_NOT_DEFINED;


  /**
   * The {@link State} where the <code>DefaultTransition</code> begins.
   */
  private State stateBegin;


  /**
   * The {@link State} id where the <code>DefaultTransition</code> begins.
   */
  private int stateBeginId;


  /**
   * The {@link State} where the <code>DefaultTransition</code> ends.
   */
  private State stateEnd;


  /**
   * The {@link State} id where the <code>DefaultTransition</code> ends.
   */
  private int stateEndId;


  /**
   * The list of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new <code>DefaultTransition</code>.
   */
  public DefaultTransition ()
  {
    this.symbolSet = new TreeSet < Symbol > ();
  }


  /**
   * Allocates a new <code>DefaultTransition</code>.
   * 
   * @param alphabet The {@link Alphabet} of this <code>DefaultTransition</code>.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          <code>DefaultTransition</code>.
   * @param stateBegin The {@link State} where the
   *          <code>DefaultTransition</code> begins.
   * @param stateEnd The {@link State} where the <code>DefaultTransition</code>
   *          ends.
   * @param symbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public DefaultTransition ( Alphabet alphabet, Alphabet pushDownAlphabet,
      State stateBegin, State stateEnd, Iterable < Symbol > symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Alphabet
    setAlphabet ( alphabet );
    // PushDownAlphabet
    setPushDownAlphabet ( pushDownAlphabet );
    // StateBegin
    setStateBegin ( stateBegin );
    // StateEnd
    setStateEnd ( stateEnd );
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    add ( symbols );
  }


  /**
   * Allocates a new <code>DefaultTransition</code>.
   * 
   * @param alphabet The {@link Alphabet} of this <code>DefaultTransition</code>.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          <code>DefaultTransition</code>.
   * @param stateBegin The {@link State} where the
   *          <code>DefaultTransition</code> begins.
   * @param stateEnd The {@link State} where the <code>DefaultTransition</code>
   *          ends.
   * @param symbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public DefaultTransition ( Alphabet alphabet, Alphabet pushDownAlphabet,
      State stateBegin, State stateEnd, Symbol ... symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Alphabet
    setAlphabet ( alphabet );
    // PushDownAlphabet
    setPushDownAlphabet ( pushDownAlphabet );
    // StateBegin
    setStateBegin ( stateBegin );
    // StateEnd
    setStateEnd ( stateEnd );
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    add ( symbols );
  }


  /**
   * Allocates a new <code>DefaultTransition</code>.
   * 
   * @param element The {@link Element}. *
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultTransition ( Element element )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException, SymbolException, AlphabetException,
      StoreException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "Transition" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + element.getName () //$NON-NLS-1$
          + "\" is not a transition" ); //$NON-NLS-1$
    }

    // Symbols
    this.symbolSet = new TreeSet < Symbol > ();

    // Attribute
    boolean foundId = false;
    boolean foundStateBeginId = false;
    boolean foundStateEndId = false;
    for ( Attribute current : element.getAttribute () )
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
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
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
    boolean foundPushDownAlphabet = false;
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        setAlphabet ( new DefaultAlphabet ( current ) );
        foundAlphabet = true;
      }
      else if ( current.getName ().equals ( "PushDownAlphabet" ) ) //$NON-NLS-1$
      {
        current.setName ( "Alphabet" ); //$NON-NLS-1$
        setPushDownAlphabet ( new DefaultAlphabet ( current ) );
        foundPushDownAlphabet = true;
      }
      else if ( current.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        add ( new DefaultSymbol ( current ) );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    // Not all element values found
    if ( ( !foundAlphabet ) || ( !foundPushDownAlphabet ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingElement" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Allocates a new <code>DefaultTransition</code>.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public DefaultTransition ( Iterable < Symbol > symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    add ( symbols );
  }


  /**
   * Allocates a new <code>DefaultTransition</code>.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public DefaultTransition ( Symbol ... symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbolSet = new TreeSet < Symbol > ();
    add ( symbols );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultTransition</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>DefaultTransition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public final void add ( Iterable < Symbol > symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>DefaultTransition</code>.
   * 
   * @param symbol The {@link Symbol} to be appended to this
   *          <code>DefaultTransition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public final void add ( Symbol symbol )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( ( this.alphabet != null ) && ( !this.alphabet.contains ( symbol ) ) )
    {
      ArrayList < Symbol > tmpList = new ArrayList < Symbol > ();
      tmpList.add ( symbol );
      throw new TransitionSymbolNotInAlphabetException ( this, this.alphabet,
          tmpList );
    }
    if ( this.symbolSet.contains ( symbol ) )
    {
      ArrayList < Symbol > tmpList = new ArrayList < Symbol > ();
      for ( Symbol current : this.symbolSet )
      {
        if ( symbol.equals ( current ) )
        {
          tmpList.add ( current );
          break;
        }
      }
      tmpList.add ( symbol );
      throw new TransitionSymbolOnlyOneTimeException ( this, tmpList );
    }
    this.symbolSet.add ( symbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultTransition</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>DefaultTransition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>DefaultTransition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>DefaultTransition</code> is not correct.
   */
  public final void add ( Symbol ... symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * Removes all {@link Symbol}s.
   */
  public final void clear ()
  {
    this.symbolSet.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final DefaultTransition clone ()
  {
    DefaultTransition newDefaultTransition = null;
    try
    {
      newDefaultTransition = new DefaultTransition ( this.alphabet.clone (),
          this.pushDownAlphabet.clone (), this.stateBegin, this.stateEnd );
      for ( Symbol current : this.symbolSet )
      {
        newDefaultTransition.add ( current.clone () );
      }
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
    return newDefaultTransition;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( Transition other )
  {
    return this.id < other.getId () ? -1 : ( this.id > other.getId () ? 1 : 0 );
  }


  /**
   * Returns true if the {@link Alphabet} of this <code>DefaultTransition</code>
   * contains the given {@link Symbol}. Otherwise false.
   * 
   * @param symbol The {@link Symbol}.
   * @return True if the {@link Alphabet} of this <code>DefaultTransition</code>
   *         contains the given {@link Symbol}. Otherwise false.
   */
  public final boolean contains ( Symbol symbol )
  {
    return this.symbolSet.contains ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultTransition )
    {
      DefaultTransition defaultTransition = ( DefaultTransition ) other;
      if ( ( this.id == ID_NOT_DEFINED )
          || ( defaultTransition.id == ID_NOT_DEFINED ) )
      {
        throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
      }
      return this.id == defaultTransition.id;
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
    Element newPushDownAlphabet = this.pushDownAlphabet.getElement ();
    newPushDownAlphabet.setName ( "PushDownAlphabet" ); //$NON-NLS-1$
    newElement.addElement ( newPushDownAlphabet );
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
   * {@inheritDoc}
   */
  public final int getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final int getParserStartOffset ()
  {
    return this.parserStartOffset;
  }


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   * @see #pushDownAlphabet
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Returns the {@link State} where the <code>DefaultTransition</code>
   * begins.
   * 
   * @return The {@link State} where the <code>DefaultTransition</code>
   *         begins.
   * @see #stateBegin
   */
  public final State getStateBegin ()
  {
    return this.stateBegin;
  }


  /**
   * Returns the {@link State} id where the <code>DefaultTransition</code>
   * begins.
   * 
   * @return The {@link State} id where the <code>DefaultTransition</code>
   *         begins.
   * @see #stateBeginId
   */
  public final int getStateBeginId ()
  {
    return this.stateBegin == null ? this.stateBeginId : this.stateBegin
        .getId ();
  }


  /**
   * Returns the {@link State} where the <code>DefaultTransition</code> ends.
   * 
   * @return The {@link State} where the <code>DefaultTransition</code> ends.
   * @see #stateEnd
   */
  public final State getStateEnd ()
  {
    return this.stateEnd;
  }


  /**
   * Returns the {@link State} id where the <code>DefaultTransition</code>
   * ends.
   * 
   * @return The {@link State} id where the <code>DefaultTransition</code>
   *         ends.
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
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol getSymbol ( int index )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < index ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
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
   * Returns true, if this <code>DefaultTransition</code> is a epsilon
   * <code>DefaultTransition</code>, otherwise false.
   * 
   * @return True, if this <code>DefaultTransition</code> is a epsilon
   *         <code>DefaultTransition</code>, otherwise false.
   */
  public final boolean isEpsilonTransition ()
  {
    return this.symbolSet.size () == 0;
  }


  /**
   * Returns true if the id of this <code>DefaultTransition</code> is defined,
   * otherwise false.
   * 
   * @return True if the id of this <code>DefaultTransition</code> is defined,
   *         otherwise false.
   */
  public final boolean isIdDefined ()
  {
    return this.id != ID_NOT_DEFINED;
  }


  /**
   * Returns an iterator over the {@link Symbol}s in this
   * <code>DefaultTransition</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this
   *         <code>DefaultTransition</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolSet.iterator ();
  }


  /**
   * Remove the given {@link Symbol}s from this <code>DefaultTransition</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public final void remove ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link Symbol} from this <code>DefaultTransition</code>.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public final void remove ( Symbol symbol )
  {
    if ( !this.symbolSet.contains ( symbol ) )
    {
      throw new IllegalArgumentException ( "symbol is not in this transition" ); //$NON-NLS-1$
    }
    this.symbolSet.remove ( symbol );
  }


  /**
   * Remove the given {@link Symbol}s from this <code>DefaultTransition</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public final void remove ( Symbol ... symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * Sets the {@link Alphabet} of this <code>DefaultTransition</code>.
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
   * Sets the id.
   * 
   * @param id The id to set.
   * @see #id
   */
  public final void setId ( int id )
  {
    if ( this.id != ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is already setted" ); //$NON-NLS-1$
    }
    this.id = id;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserEndOffset ( int parserEndOffset )
  {
    this.parserEndOffset = parserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserStartOffset ( int parserStartOffset )
  {
    this.parserStartOffset = parserStartOffset;
  }


  /**
   * Sets the push down {@link Alphabet} of this <code>DefaultTransition</code>.
   * 
   * @param pushDownAlphabet The push down {@link Alphabet} to set.
   */
  public final void setPushDownAlphabet ( Alphabet pushDownAlphabet )
  {
    if ( pushDownAlphabet == null )
    {
      throw new NullPointerException ( "push down alphabet is null" ); //$NON-NLS-1$
    }
    this.pushDownAlphabet = pushDownAlphabet;
  }


  /**
   * Sets the {@link State} where the <code>DefaultTransition</code> begins.
   * 
   * @param stateBegin The {@link State} to set.
   */
  public final void setStateBegin ( State stateBegin )
  {
    if ( stateBegin == null )
    {
      throw new NullPointerException ( "state begin is null" ); //$NON-NLS-1$
    }
    this.stateBegin = stateBegin;
  }


  /**
   * Sets the {@link State} id where the <code>DefaultTransition</code>
   * begins.
   * 
   * @param stateBeginId The {@link State} id to set.
   */
  private final void setStateBeginId ( int stateBeginId )
  {
    if ( this.stateBegin != null )
    {
      throw new IllegalArgumentException (
          "can not set the id if there is a state" ); //$NON-NLS-1$
    }
    this.stateBeginId = stateBeginId;
  }


  /**
   * Sets the {@link State} where the <code>DefaultTransition</code> ends.
   * 
   * @param stateEnd The {@link State} to set.
   */
  public final void setStateEnd ( State stateEnd )
  {
    if ( stateEnd == null )
    {
      throw new NullPointerException ( "state end is null" ); //$NON-NLS-1$
    }
    this.stateEnd = stateEnd;
  }


  /**
   * Sets the {@link State} id where the <code>DefaultTransition</code> ends.
   * 
   * @param stateEndId The {@link State} id to set.
   */
  private final void setStateEndId ( int stateEndId )
  {
    if ( this.stateEnd != null )
    {
      throw new IllegalArgumentException (
          "can not set the id if there is a state" ); //$NON-NLS-1$
    }
    this.stateEndId = stateEndId;
  }


  /**
   * Returns the number of {@link Symbol}s in this
   * <code>DefaultTransition</code>.
   * 
   * @return The number of {@link Symbol}s in this
   *         <code>DefaultTransition</code>.
   */
  public final int size ()
  {
    return this.symbolSet.size ();
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
