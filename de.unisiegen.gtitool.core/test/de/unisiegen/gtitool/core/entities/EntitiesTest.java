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
public class EntitiesTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    Symbol a = null;
    Symbol b = null;
    Symbol c = null;
    try
    {
      a = new DefaultSymbol ( "a" ); //$NON-NLS-1$
      b = new DefaultSymbol ( "b" );//$NON-NLS-1$
      c = new DefaultSymbol ( "c" );//$NON-NLS-1$
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    Stack stack = new DefaultStack ();

    // stack abc
    stack.push ( c );
    stack.push ( b );
    stack.push ( a );

    System.out.println ( stack.peak ( 2 ) );
    System.out.println ( stack );

    System.out.println ( stack.pop () );
    System.out.println ( stack );

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
      z0 = new DefaultState ( alphabet, pushDownAlphabet, "z0", true, false );//$NON-NLS-1$
      z1 = new DefaultState ( alphabet, pushDownAlphabet, "z1", false, false );//$NON-NLS-1$
      z2 = new DefaultState ( alphabet, pushDownAlphabet, "z2", false, true );//$NON-NLS-1$
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
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z0, z0, a, b );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z0, z1, c );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z1, z1, a, b );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z1, z2, c );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet,
          new DefaultWord (), new DefaultWord (), z2, z2, a, b, c );

      System.out.println ( t0 );
      System.out.println ( t1 );
      System.out.println ( t2 );
      System.out.println ( t3 );
      System.out.println ( t4 );
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
