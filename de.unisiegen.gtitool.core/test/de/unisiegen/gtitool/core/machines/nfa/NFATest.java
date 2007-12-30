package de.unisiegen.gtitool.core.machines.nfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.MachineTest;


/**
 * The test class of the {@link DefaultNFA}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class NFATest extends MachineTest
{

  private static Symbol a = null;


  private static Symbol b = null;


  private static Symbol c = null;


  private static Symbol d = null;


  private static Symbol e = null;


  private static Symbol f = null;


  private static Alphabet alphabet = null;


  private static State z0 = null;


  private static State z1 = null;


  private static State z2 = null;


  private static State z3 = null;


  private static Transition t0 = null;


  private static Transition t1 = null;


  private static Transition t2 = null;


  private static Transition t3 = null;


  private static Transition t4 = null;


  private static void initEntities ()
  {
    // Symbols
    try
    {
      a = new Symbol ( "a" );
      b = new Symbol ( "b" );
      c = new Symbol ( "c" );
      d = new Symbol ( "d" );
      e = new Symbol ( "e" );
      f = new Symbol ( "f" );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // Alphabet
    try
    {
      alphabet = new Alphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // States
    try
    {
      z0 = new State ( alphabet, true, false );
      z1 = new State ( alphabet, false, false );
      z2 = new State ( alphabet, false, false );
      z3 = new State ( alphabet, false, true );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // Transitions
    try
    {
      t0 = new Transition ( alphabet, z0, z1, a );
      t1 = new Transition ( alphabet, z0, z2, a );
      t2 = new Transition ( alphabet, z1, z3, b );
      t3 = new Transition ( alphabet, z2, z3, c );
      t4 = new Transition ( alphabet, z3, z3, a, b, c );
    }
    catch ( TransitionSymbolNotInAlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  public static void main ( String [] pArguments )
  {
    initEntities ();

    NFA nfa = new DefaultNFA ( alphabet );
    nfa.addState ( z0, z1, z2, z3 );
    nfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new Word ( a, b, c );

    start ( nfa, word );
  }
}
