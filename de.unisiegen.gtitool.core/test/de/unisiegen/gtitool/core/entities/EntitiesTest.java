package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;


/**
 * The test class of the entities.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class EntitiesTest
{

  public static void main ( String [] pArguments )
  {
    Symbol a = null;
    Symbol b = null;
    Symbol c = null;
    try
    {
      a = new DefaultSymbol ( "a" );
      b = new DefaultSymbol ( "b" );
      c = new DefaultSymbol ( "c" );
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    Alphabet alphabet = null;
    try
    {
      alphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    Alphabet pushDownAlphabet = null;
    try
    {
      pushDownAlphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    State z0 = null;
    State z1 = null;
    State z2 = null;
    try
    {
      z0 = new DefaultState ( alphabet, "z0", true, false );
      z1 = new DefaultState ( alphabet, "z1", false, false );
      z2 = new DefaultState ( alphabet, "z2", false, true );
    }
    catch ( StateException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    Transition t0 = null;
    Transition t1 = null;
    Transition t2 = null;
    Transition t3 = null;
    Transition t4 = null;
    try
    {
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet, z0, z0, a, b );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet, z0, z1, c );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet, z1, z1, a, b );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet, z1, z2, c );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet, z2, z2, a, b, c );
    }
    catch ( TransitionSymbolNotInAlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionSymbolOnlyOneTimeException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
