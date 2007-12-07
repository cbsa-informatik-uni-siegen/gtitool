package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.state.StateIllegalNameException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.storage.exceptions.StoreWarningException;


/**
 * The <code>State</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class State implements ParseableEntity, Storable
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * The serial verion uid.
   */
  private static final long serialVersionUID = -1156609272782310462L;


  /**
   * The {@link Alphabet} of this <code>State</code>.
   */
  private Alphabet alphabet = null;


  /**
   * Flag that indicates if the default name can be set.
   */
  private boolean canSetDefaultName;


  /**
   * This <code>State</code> is a final <code>State</code>.
   */
  private boolean finalState = false;


  /**
   * The id of this <code>State</code>.
   */
  private int id = ID_NOT_DEFINED;


  /**
   * The name of this <code>State</code>.
   */
  private String name;


  /**
   * The end offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  private int parserEndOffset = NO_PARSER_OFFSET;


  /**
   * The start offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  private int parserStartOffset = NO_PARSER_OFFSET;


  /**
   * This <code>State</code> is a start <code>State</code>.
   */
  private boolean startState = false;


  /**
   * The list of {@link Transition}s, which begin in this <code>State</code>.
   */
  private ArrayList < Transition > transitionBeginList;


  /**
   * The list of {@link Transition} ids, which begin in this <code>State</code>.
   */
  private ArrayList < Integer > transitionBeginIdList;


  /**
   * The list of {@link Transition}s, which end in this <code>State</code>.
   */
  private ArrayList < Transition > transitionEndList;


  /**
   * The list of {@link Transition} ids, which end in this <code>State</code>.
   */
  private ArrayList < Integer > transitionEndIdList;


  /**
   * Allocates a new <code>State</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>State</code>.
   * @param pStartState This <code>State</code> is a start <code>State</code>.
   * @param pFinalState This <code>State</code> is a final <code>State</code>.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public State ( Alphabet pAlphabet, boolean pStartState, boolean pFinalState )
      throws StateException
  {
    this ( pAlphabet, "DEFAULTNAME", pStartState, pFinalState ); //$NON-NLS-1$
    this.canSetDefaultName = true;
  }


  /**
   * Allocates a new <code>State</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>State</code>.
   * @param pName The name of this <code>State</code>.
   * @param pStartState This <code>State</code> is a start <code>State</code>.
   * @param pFinalState This <code>State</code> is a final <code>State</code>.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public State ( Alphabet pAlphabet, String pName, boolean pStartState,
      boolean pFinalState ) throws StateException
  {
    this ( pName );
    // Alphabet
    setAlphabet ( pAlphabet );
    // StartState
    setStartState ( pStartState );
    // FinalState
    setFinalState ( pFinalState );
  }


  /**
   * Allocates a new <code>State</code>.
   * 
   * @param pElement The {@link Element}.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public State ( Element pElement ) throws StateException, SymbolException,
      AlphabetException, StoreException
  {
    // Check if the element is correct
    if ( !pElement.getName ().equals ( "State" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a state" ); //$NON-NLS-1$
    }

    // TransitionBegin
    this.transitionBeginList = new ArrayList < Transition > ();
    this.transitionBeginIdList = new ArrayList < Integer > ();
    // TransitionEnd
    this.transitionEndList = new ArrayList < Transition > ();
    this.transitionEndIdList = new ArrayList < Integer > ();

    // Attribute
    boolean foundId = false;
    boolean foundName = false;
    boolean foundStartState = false;
    boolean foundFinalState = false;
    boolean foundCanSetDefaultName = false;
    boolean foundParserStartOffset = false;
    boolean foundParserEndOffset = false;
    for ( Attribute current : pElement.getAttribute () )
    {
      if ( current.getName ().equals ( "id" ) ) //$NON-NLS-1$
      {
        setId ( current.getValueInt () );
        foundId = true;
      }
      else if ( current.getName ().equals ( "name" ) ) //$NON-NLS-1$
      {
        setName ( current.getValue () );
        foundName = true;
      }
      else if ( current.getName ().equals ( "startState" ) ) //$NON-NLS-1$
      {
        setStartState ( current.getValueBoolean () );
        foundStartState = true;
      }
      else if ( current.getName ().equals ( "finalState" ) ) //$NON-NLS-1$
      {
        setFinalState ( current.getValueBoolean () );
        foundFinalState = true;
      }
      else if ( current.getName ().equals ( "canSetDefaultName" ) ) //$NON-NLS-1$
      {
        setCanSetDefaultName ( current.getValueBoolean () );
        foundCanSetDefaultName = true;
      }
      else if ( current.getName ().equals ( "parserStartOffset" ) ) //$NON-NLS-1$
      {
        setParserStartOffset ( current.getValueInt () );
        foundParserStartOffset = true;
      }
      else if ( current.getName ().equals ( "parserEndOffset" ) ) //$NON-NLS-1$
      {
        setParserEndOffset ( current.getValueInt () );
        foundParserEndOffset = true;
      }
      else
      {
        // TODO Warning
        throw new IllegalArgumentException ();
      }
    }

    // Not all attribute values found
    if ( ( !foundId ) || ( !foundName ) || ( !foundStartState )
        || ( !foundFinalState ) || ( !foundCanSetDefaultName )
        || ( !foundParserStartOffset ) || ( !foundParserEndOffset ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
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
      else if ( current.getName ().equals ( "TransitionBegin" ) ) //$NON-NLS-1$
      {
        boolean foundTransitionBeginId = false;
        for ( Attribute currentAttribute : current.getAttribute () )
        {
          if ( currentAttribute.getName ().equals ( "id" ) ) //$NON-NLS-1$
          {
            this.transitionBeginIdList.add ( new Integer ( currentAttribute
                .getValueInt () ) );
            foundTransitionBeginId = true;
          }
          else
          {
            // TODO Warning
            throw new IllegalArgumentException ();
          }
        }

        // Not all attribute values found
        if ( !foundTransitionBeginId )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
        }
      }
      else if ( current.getName ().equals ( "TransitionEnd" ) ) //$NON-NLS-1$
      {
        boolean foundTransitionEndId = false;
        for ( Attribute currentAttribute : current.getAttribute () )
        {
          if ( currentAttribute.getName ().equals ( "id" ) ) //$NON-NLS-1$
          {
            this.transitionEndIdList.add ( new Integer ( currentAttribute
                .getValueInt () ) );
            foundTransitionEndId = true;
          }
          else
          {
            // TODO Warning
            throw new IllegalArgumentException ();
          }
        }

        // Not all attribute values found
        if ( !foundTransitionEndId )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
        }
      }
      else
      {
        // TODO Warning
        throw new IllegalArgumentException ();
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
   * Allocates a new <code>State</code>.
   * 
   * @param pName The name of this <code>State</code>.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public State ( String pName ) throws StateException
  {
    // Name
    setName ( pName );
    // TransitionBegin
    this.transitionBeginList = new ArrayList < Transition > ();
    this.transitionBeginIdList = new ArrayList < Integer > ();
    // TransitionEnd
    this.transitionEndList = new ArrayList < Transition > ();
    this.transitionEndIdList = new ArrayList < Integer > ();
    // DefaultName
    this.canSetDefaultName = false;
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public final void addTransitionBegin ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionBeginList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition begins already in this state" ); //$NON-NLS-1$
    }
    if ( pTransition.getStateBegin () != this )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.add ( pTransition );
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which end
   * in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public final void addTransitionEnd ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionEndList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition ends already in this state" ); //$NON-NLS-1$
    }
    if ( pTransition.getStateEnd () != this )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.add ( pTransition );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final State clone ()
  {
    try
    {
      State newState = new State ( this.alphabet.clone (), this.name,
          this.startState, this.finalState );
      for ( Transition current : this.transitionBeginList )
      {
        newState.addTransitionBegin ( current.clone () );
      }
      for ( Transition current : this.transitionEndList )
      {
        newState.addTransitionEnd ( current.clone () );
      }
      return newState;
    }
    catch ( StateException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof State )
    {
      State other = ( State ) pOther;
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
    Element newElement = new Element ( "State" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "id", this.id ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "name", this.name ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "startState", this.startState ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "finalState", this.finalState ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "canSetDefaultName", //$NON-NLS-1$
        this.canSetDefaultName ) );
    newElement.addAttribute ( new Attribute ( "parserStartOffset", //$NON-NLS-1$
        this.parserStartOffset ) );
    newElement.addAttribute ( new Attribute ( "parserEndOffset", //$NON-NLS-1$
        this.parserEndOffset ) );
    newElement.addElement ( this.alphabet );
    for ( Transition current : this.transitionBeginList )
    {
      Element currentElement = new Element ( "TransitionBegin" ); //$NON-NLS-1$
      currentElement.addAttribute ( new Attribute ( "id", current.getId () ) ); //$NON-NLS-1$
      newElement.addElement ( currentElement );
    }
    for ( Transition current : this.transitionEndList )
    {
      Element currentElement = new Element ( "TransitionEnd" ); //$NON-NLS-1$
      currentElement.addAttribute ( new Attribute ( "id", current.getId () ) ); //$NON-NLS-1$
      newElement.addElement ( currentElement );
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
    if ( this.id == ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
    }
    return this.id;
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
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
   * Returns the {@link Transition} begin list.
   * 
   * @return The {@link Transition} begin list.
   */
  public final ArrayList < Transition > getTransitionBegin ()
  {
    return this.transitionBeginList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} begin list.
   */
  public final Transition getTransitionBegin ( int pIndex )
  {
    return this.transitionBeginList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} id begin list.
   * 
   * @return The {@link Transition} id begin list.
   */
  public final ArrayList < Integer > getTransitionBeginId ()
  {
    return this.transitionBeginIdList;
  }


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param pIndex The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id begin list.
   */
  public final int getTransitionBeginId ( int pIndex )
  {
    return this.transitionBeginIdList.get ( pIndex ).intValue ();
  }


  /**
   * Returns the {@link Transition} end list.
   * 
   * @return The {@link Transition} end list.
   */
  public final ArrayList < Transition > getTransitionEnd ()
  {
    return this.transitionEndList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} end list.
   * 
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} end list.
   */
  public final Transition getTransitionEnd ( int pIndex )
  {
    return this.transitionEndList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} id end list.
   * 
   * @return The {@link Transition} id end list.
   */
  public final ArrayList < Integer > getTransitionEndId ()
  {
    return this.transitionEndIdList;
  }


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} end list.
   * 
   * @param pIndex The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id end list.
   */
  public final int getTransitionEndId ( int pIndex )
  {
    return this.transitionEndIdList.get ( pIndex ).intValue ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
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
   * Returns the finalState.
   * 
   * @return The finalState.
   * @see #finalState
   */
  public final boolean isFinalState ()
  {
    return this.finalState;
  }


  /**
   * Returns true if the id of this <code>State</code> is defined, otherwise
   * false.
   * 
   * @return True if the id of this <code>State</code> is defined, otherwise
   *         false.
   */
  public final boolean isIdDefined ()
  {
    return this.id != ID_NOT_DEFINED;
  }


  /**
   * Returns the startState.
   * 
   * @return The startState.
   * @see #startState
   */
  public final boolean isStartState ()
  {
    return this.startState;
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public final void removeTransitionBegin ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionBeginList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.remove ( pTransition );
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * end in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public final void removeTransitionEnd ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionEndList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.remove ( pTransition );
  }


  /**
   * Sets the {@link Alphabet} of this <code>State</code>.
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
   * Sets the canSetDefaultName value.
   * 
   * @param pCanSetDefaultName The canSetDefaultName to set.
   * @see #canSetDefaultName
   */
  private final void setCanSetDefaultName ( boolean pCanSetDefaultName )
  {
    this.canSetDefaultName = pCanSetDefaultName;
  }


  /**
   * Sets the default name of this <code>State</code>.
   */
  public final void setDefaultName ()
  {
    if ( this.canSetDefaultName )
    {
      if ( this.id == ID_NOT_DEFINED )
      {
        throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$ 
      }
      try
      {
        setName ( "z" + this.id ); //$NON-NLS-1$
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }
  }


  /**
   * Sets the finalState value.
   * 
   * @param pFinalState The finalState to set.
   */
  public final void setFinalState ( boolean pFinalState )
  {
    this.finalState = pFinalState;
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
   * Sets the name of this <code>State</code>.
   * 
   * @param pName The name to set.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public final void setName ( String pName ) throws StateException
  {
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( pName.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new StateEmptyNameException ();
    }
    if ( !Character.isJavaIdentifierStart ( pName.charAt ( 0 ) ) )
    {
      throw new StateIllegalNameException ( pName );
    }
    for ( int i = 1 ; i < pName.length () ; i++ )
    {
      if ( !Character.isJavaIdentifierPart ( pName.charAt ( i ) ) )
      {
        throw new StateIllegalNameException ( pName );
      }
    }
    this.name = pName;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserEndOffset ( int pParserEndOffset )
  {
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserStartOffset ( int pParserStartOffset )
  {
    this.parserStartOffset = pParserStartOffset;
  }


  /**
   * Sets the startState value.
   * 
   * @param pStartState The startState to set.
   */
  public final void setStartState ( boolean pStartState )
  {
    this.startState = pStartState;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.name;
  }


  /**
   * Returns the debug string.
   * 
   * @return The debug string.
   */
  public final String toStringDebug ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Name:        " + this.name + lineBreak ); //$NON-NLS-1$
    result.append ( "Alphabet:    " + this.alphabet.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "Start state: " + this.startState + lineBreak ); //$NON-NLS-1$
    result.append ( "Final state: " + this.finalState + lineBreak ); //$NON-NLS-1$
    result.append ( "Transition begin:" + lineBreak ); //$NON-NLS-1$
    for ( Transition current : this.transitionBeginList )
    {
      result.append ( "- Alphabet:    " + current.getAlphabet ().toString () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Begin state: " + current.getStateBegin ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  End state:   " + current.getStateEnd ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Symbols:     " + current.getSymbol ().toString () //$NON-NLS-1$
          + lineBreak );
    }
    result.append ( "Transition end:" + lineBreak ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.transitionEndList.size () ; i++ )
    {
      result.append ( "- Alphabet:    " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getAlphabet ().toString ()
          + lineBreak );
      result.append ( "  Begin state: " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateBegin ().getName ()
          + lineBreak );
      result.append ( "  End state:   " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateEnd ().getName ()
          + lineBreak );
      result.append ( "  Symbols:     " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getSymbol ().toString () );
      if ( i < this.transitionEndList.size () - 1 )
      {
        result.append ( lineBreak );
      }
    }
    return result.toString ();
  }


  public ArrayList < StoreWarningException > getWarning ()
  {
    // TODO Auto-generated method stub
    return null;
  }


  public ArrayList < StoreWarningException > getWarning ( int pIndex )
  {
    // TODO Auto-generated method stub
    return null;
  }
}
