package de.unisiegen.gtitool.core.machines.sm;


import java.util.ArrayList;

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
 * The test class of the {@link SM}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class SMTest
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
      t0 = new Transition ( alphabet, z0, z0, b, c );
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

    SM sm = new SM ( alphabet );
    sm.addState ( z0, z1, z2 );
    sm.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new Word ( b, c, a, c, b, a, b );

    try
    {
      sm.start ( word );
    }
    catch ( MachineValidationException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    println ( "*** Next *** " );
    println ();
    try
    {
      while ( !sm.isFinished () )
      {
        print ( "States:      " );
        for ( int i = 0 ; i < sm.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( sm.getActiveState ( i ) );
        }
        println ();
        print ( "Transitions: " );
        ArrayList < Transition > transitions = sm.nextSymbol ();
        for ( int i = 0 ; i < transitions.size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( transitions.get ( i ) );
        }
        println ();
        println ( "Symbol:      " + sm.getCurrentSymbol () );
        print ( "States:      " );
        for ( int i = 0 ; i < sm.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( sm.getActiveState ( i ) );
        }
        println ();
        println ( "Accepted:    " + sm.isWordAccepted () );
        println ();
        println ();
      }
    }
    catch ( WordException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    println ( "*** Previous *** " );
    println ();
    try
    {
      while ( !sm.isReseted () )
      {
        print ( "States:      " );
        for ( int i = 0 ; i < sm.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( sm.getActiveState ( i ) );
        }
        println ();
        println ( "Symbol:      " + sm.getCurrentSymbol () );
        print ( "Transitions: " );
        ArrayList < Transition > transitions = sm.previousSymbol ();
        for ( int i = 0 ; i < transitions.size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( transitions.get ( i ) );
        }
        println ();
        print ( "States:      " );
        for ( int i = 0 ; i < sm.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( sm.getActiveState ( i ) );
        }
        println ();
        println ();
      }
    }
    catch ( WordException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  public static void print ( Object pObject )
  {
    System.out.print ( pObject.toString () );
  }


  public static void println ()
  {
    System.out.println ();
  }


  public static void println ( Object pObject )
  {
    System.out.println ( pObject.toString () );
  }
}
