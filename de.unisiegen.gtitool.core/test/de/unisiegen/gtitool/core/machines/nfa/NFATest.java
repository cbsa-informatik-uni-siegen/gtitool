package de.unisiegen.gtitool.core.machines.nfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
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


  private static Alphabet pushDownAlphabet = null;


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
    // symbols
    a = new DefaultSymbol ( "a" );
    b = new DefaultSymbol ( "b" );
    c = new DefaultSymbol ( "c" );
    d = new DefaultSymbol ( "d" );
    e = new DefaultSymbol ( "e" );
    f = new DefaultSymbol ( "f" );

    // alphabet
    try
    {
      alphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    // push down alphabet
    try
    {
      pushDownAlphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // States
    try
    {
      z0 = new DefaultState ( alphabet, pushDownAlphabet, "z0", true, false );
      z1 = new DefaultState ( alphabet, pushDownAlphabet, "z1", false, false );
      z2 = new DefaultState ( alphabet, pushDownAlphabet, "z2", false, false );
      z3 = new DefaultState ( alphabet, pushDownAlphabet, "z3", false, true );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // Transitions
    try
    {
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z0, z1, a );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z0, z2, a );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z1, z3, b );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z2, z3, c );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z3, z3, a, b, c );
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


  public static void main ( String [] arguments )
  {
    initEntities ();

    NFA nfa = new DefaultNFA ( alphabet, pushDownAlphabet, true );
    nfa.addState ( z0, z1, z2, z3 );
    nfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new DefaultWord ( a, b, c );

    start ( nfa, word );
  }
}
