package de.unisiegen.gtitool.core.machines.pda;


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
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.MachineTest;


/**
 * The test class of the {@link DefaultPDA}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class PDATest extends MachineTest
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
      a = new DefaultSymbol ( "a" );
      b = new DefaultSymbol ( "b" );
      c = new DefaultSymbol ( "c" );
      d = new DefaultSymbol ( "d" );
      e = new DefaultSymbol ( "e" );
      f = new DefaultSymbol ( "f" );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // Alphabet
    try
    {
      alphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // PushDownAlphabet
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
      z0 = new DefaultState ( alphabet, pushDownAlphabet, true, false );
      z1 = new DefaultState ( alphabet, pushDownAlphabet, false, false );
      z2 = new DefaultState ( alphabet, pushDownAlphabet, false, true );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    // Transitions
    try
    {
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet, z0, z0, b, c );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet, z0, z1, a );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet, z1, z1, a, c );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet, z1, z2, b );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet, z2, z2, a, b, c );
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

    PDA pda = new DefaultPDA ( alphabet, pushDownAlphabet );
    pda.addState ( z0, z1, z2 );
    pda.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new DefaultWord ( b, c, a, c, b, a, b );

    start ( pda, word );
  }
}
