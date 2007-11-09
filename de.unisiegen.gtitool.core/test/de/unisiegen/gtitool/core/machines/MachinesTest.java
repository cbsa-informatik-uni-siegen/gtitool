package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.MachineValidationException;
import de.unisiegen.gtitool.core.machines.dfa.DFA;


/**
 * The test class of the machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class MachinesTest
{

  public static void main ( String [] pArguments )
  {
    Symbol a = new Symbol ( "a" );
    Symbol b = new Symbol ( "b" );
    Symbol c = new Symbol ( "c" );

    Alphabet alphabet = null;
    try
    {
      alphabet = new Alphabet ( a, b, c );
    }
    catch ( AlphabetException e )
    {
      System.err.println ( "AlphabetException" );
      System.exit ( 1 );
    }

    State z0 = new State ( alphabet, "z0", true, false );
    State z1 = new State ( alphabet, "z1", false, false );
    State z2 = new State ( alphabet, "z2", false, true );

    Transition t0 = new Transition ( alphabet, z0, z0, a, b );
    Transition t1 = new Transition ( alphabet, z0, z1, c );
    Transition t2 = new Transition ( alphabet, z1, z1, a, b );
    Transition t3 = new Transition ( alphabet, z1, z2, c );
    Transition t4 = new Transition ( alphabet, z2, z2, a, b, c );

    DFA dfa = new DFA ( alphabet );

    dfa.addStates ( z0, z1, z2 );

    dfa.addTransitions ( t0, t1, t2, t3, t4 );

    try
    {
      dfa.validate ();
    }
    catch ( MachineValidationException e )
    {
      System.out.println ( e );
    }
  }
}
