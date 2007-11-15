package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAllSymbolsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineEpsilonTransitionException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNameException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateStartException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
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
   * {@inheritDoc}}
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
      for ( Transition currentTransition : currentState
          .getTransitionBeginList () )
      {
        currentSymbolSet.addAll ( currentTransition.getSymbolSet () );
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
      if ( currentTransition.getSymbolSet ().size () == 0 )
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
        for ( Transition currentTransition : currentState
            .getTransitionBeginList () )
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

    if ( machineExceptionList.size () > 0 )
    {
      throw new MachineValidationException ( machineExceptionList );
    }
  }
}
