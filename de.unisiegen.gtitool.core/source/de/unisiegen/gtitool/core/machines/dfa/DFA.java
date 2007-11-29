package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAllSymbolsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineEpsilonTransitionException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateFinalException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNameException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateStartException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>DFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DFA extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new <code>DFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>DFA</code>.
   */
  public DFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#nextSymbol()
   */
  @Override
  public final ArrayList < Transition > nextSymbol () throws WordException
  {
    if ( getActiveStateList ().size () == 0 )
    {
      throw new IllegalArgumentException (
          "no active state: machine must be started first" ); //$NON-NLS-1$
    }
    State activeState = getActiveState ( 0 );
    Symbol symbol = getWord ().nextSymbol ();
    for ( Transition current : activeState.getTransitionBegin () )
    {
      if ( current.containsSymbol ( symbol ) )
      {
        setActiveStates ( current.getStateEnd () );
        ArrayList < Transition > result = new ArrayList < Transition > ();
        result.add ( current );
        addHistory ( current );
        return result;
      }
    }
    throw new IllegalArgumentException ( "symbol not found" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#previousSymbol()
   */
  @Override
  public final ArrayList < Transition > previousSymbol () throws WordException
  {
    if ( getActiveStateList ().size () == 0 )
    {
      throw new IllegalArgumentException (
          "no active state: machine must be started first" ); //$NON-NLS-1$
    }
    getWord ().previousSymbol ();
    ArrayList < Transition > result = removeHistory ();
    Transition transition = result.get ( 0 );
    setActiveStates ( transition.getStateBegin () );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#start(Word)
   */
  @Override
  public final void start ( Word pWord ) throws MachineValidationException
  {
    // Word
    if ( pWord == null )
    {
      throw new NullPointerException ( "word is null" ); //$NON-NLS-1$
    }
    validate ();
    setWord ( pWord );
    pWord.start ();
    clearHistory ();
    // Set active start
    loop : for ( State current : this.getStateList () )
    {
      if ( current.isStartState () )
      {
        setActiveStates ( current );
        break loop;
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#validate()
   */
  @Override
  public final void validate () throws MachineValidationException
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();

    // Start state
    ArrayList < State > startStateList = new ArrayList < State > ();
    for ( State current : this.getStateList () )
    {
      if ( current.isStartState () )

      {
        startStateList.add ( current );
      }
    }
    if ( ( startStateList.size () == 0 ) || ( startStateList.size () >= 2 ) )
    {
      machineExceptionList.add ( new MachineStateStartException (
          startStateList ) );
    }

    // State name
    ArrayList < State > duplicatedListAll = new ArrayList < State > ();
    firstLoop : for ( int i = 0 ; i < this.getStateList ().size () ; i++ )
    {
      if ( duplicatedListAll.contains ( this.getStateList ().get ( i ) ) )
      {
        continue firstLoop;
      }
      ArrayList < State > duplicatedListOne = new ArrayList < State > ();
      for ( int j = i + 1 ; j < this.getStateList ().size () ; j++ )
      {
        if ( this.getStateList ().get ( i ).getName ().equals (
            this.getStateList ().get ( j ).getName () ) )
        {
          duplicatedListOne.add ( this.getStateList ().get ( j ) );
        }
      }
      if ( duplicatedListOne.size () > 0 )
      {
        duplicatedListOne.add ( this.getStateList ().get ( i ) );
        for ( State current : duplicatedListOne )
        {
          duplicatedListAll.add ( current );
        }
        machineExceptionList.add ( new MachineStateNameException (
            duplicatedListOne ) );
      }
    }

    // All symbols
    for ( State currentState : this.getStateList () )
    {
      TreeSet < Symbol > currentSymbolSet = new TreeSet < Symbol > ();
      for ( Transition currentTransition : currentState.getTransitionBegin () )
      {
        currentSymbolSet.addAll ( currentTransition.getSymbol () );
      }
      TreeSet < Symbol > notUsedSymbolSet = new TreeSet < Symbol > ();
      for ( Symbol currentSymbol : this.getAlphabet () )
      {
        notUsedSymbolSet.add ( currentSymbol );
      }
      for ( Symbol currentSymbol : currentSymbolSet )
      {
        notUsedSymbolSet.remove ( currentSymbol );
      }
      if ( notUsedSymbolSet.size () > 0 )
      {
        machineExceptionList.add ( new MachineAllSymbolsException (
            currentState, notUsedSymbolSet ) );
      }
    }

    // Epsilon transition
    for ( Transition currentTransition : this.getTransitionList () )
    {
      if ( currentTransition.getSymbol ().size () == 0 )
      {
        machineExceptionList.add ( new MachineEpsilonTransitionException (
            currentTransition ) );
      }
    }

    // Symbol only one time
    for ( State currentState : this.getStateList () )
    {
      for ( Symbol currentSymbol : this.getAlphabet () )
      {
        ArrayList < Transition > transitions = new ArrayList < Transition > ();
        for ( Transition currentTransition : currentState.getTransitionBegin () )
        {
          if ( currentTransition.containsSymbol ( currentSymbol ) )
          {
            transitions.add ( currentTransition );
          }
        }
        if ( transitions.size () > 1 )
        {
          machineExceptionList.add ( new MachineSymbolOnlyOneTimeException (
              currentState, currentSymbol, transitions ) );
        }
      }
    }

    // Warning: final state
    boolean found = false;
    loop : for ( State currentState : this.getStateList () )
    {
      if ( currentState.isFinalState () )
      {
        found = true;
        break loop;
      }
    }
    if ( !found )
    {
      machineExceptionList.add ( new MachineStateFinalException () );
    }

    // Throw the exception if a warning or a error has occurred.
    if ( machineExceptionList.size () > 0 )
    {
      throw new MachineValidationException ( machineExceptionList );
    }
  }
}
