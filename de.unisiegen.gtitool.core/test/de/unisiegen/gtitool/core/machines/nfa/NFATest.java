package de.unisiegen.gtitool.core.machines.nfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.word.WordException;


/**
 * The test class of the {@link NFA}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class NFATest
{

  public static void main ( String [] pArguments )
  {
    Symbol a = null;
    Symbol b = null;
    Symbol c = null;
    Symbol d = null;
    Symbol e = null;
    Symbol f = null;
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

    Alphabet alphabet = null;
    try
    {
      alphabet = new Alphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    State z0 = null;
    State z1 = null;
    State z2 = null;
    try
    {
      z0 = new State ( alphabet, true, false );
      z1 = new State ( alphabet, false, false );
      z2 = new State ( alphabet, false, true );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    Transition t0 = null;
    Transition t1 = null;
    Transition t2 = null;
    Transition t3 = null;
    Transition t4 = null;
    try
    {
      t0 = new Transition ( alphabet, z0, z0, b );
      t1 = new Transition ( alphabet, z0, z1, a );
      t2 = new Transition ( alphabet, z1, z1, a, c );
      t3 = new Transition ( alphabet, z1, z2, b );
      t4 = new Transition ( alphabet, z2, z2, a, b, c );
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

    NFA nfa = new NFA ( alphabet );
    nfa.addState ( z0, z1, z2 );
    nfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new Word ( b, a, c, b, a, b );

    try
    {
      nfa.start ( word );
    }
    catch ( MachineValidationException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    out ( "*** Next *** " );
    out ();
    try
    {
      while ( !word.isFinished () )
      {
        out ( "State:      " + nfa.getActiveState ( 0 ).getName () );
        out ( "Transition: " + nfa.nextSymbol ().get ( 0 ).getSymbol () );
        out ( "Symbol:     " + nfa.getCurrentSymbol () );
        out ( "State:      " + nfa.getActiveState ( 0 ).getName () );
        out ( "Accepted:   " + nfa.isWordAccepted () );
        out ();
      }
    }
    catch ( WordException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    out ( "*** Previous *** " );
    out ();
    try
    {
      while ( !word.isReseted () )
      {
        out ( "State:      " + nfa.getActiveState ( 0 ).getName () );
        out ( "Symbol:     " + nfa.getCurrentSymbol () );
        out ( "Transition: " + nfa.previousSymbol ().get ( 0 ).getSymbol () );
        out ( "State:      " + nfa.getActiveState ( 0 ).getName () );
        out ();
      }
    }
    catch ( WordException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  public static void out ()
  {
    System.out.println ();
  }


  public static void out ( Object pObject )
  {
    System.out.println ( pObject.toString () );
  }
}
