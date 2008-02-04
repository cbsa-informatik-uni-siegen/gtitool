package de.unisiegen.gtitool.core.machines;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;


/**
 * The test class of the {@link Machine}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public abstract class MachineTest
{

  public static void print ( Object object )
  {
    System.out.print ( object.toString () );
  }


  public static void println ()
  {
    System.out.println ();
  }


  public static void println ( Object object )
  {
    System.out.println ( object.toString () );
  }


  public static void start ( Machine machine, Word word )
  {

    machine.start ( word );
    println ( "*** Next *** " );
    println ();
    try
    {
      while ( !machine.isFinished () )
      {
        print ( "States:      " );
        boolean first = true;
        for ( State current : machine.getActiveState () )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = false;
          print ( current );
        }
        println ();
        print ( "Transitions: " );
        machine.nextSymbol ();
        TreeSet < Transition > transitions = machine.getActiveTransition ();
        first = false;
        for ( Transition current : transitions )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = true;
          print ( current );
        }
        println ();
        try
        {
          println ( "Symbol:      " + machine.getCurrentSymbol () );
        }
        catch ( WordResetedException exc )
        {
          println ( "Symbol:      RESETED" );
        }
        catch ( WordFinishedException exc )
        {
          println ( "Symbol:      FINISHED" );
        }
        print ( "States:      " );
        for ( State current : machine.getActiveState () )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = false;
          print ( current );
        }
        println ();
        println ( "Accepted:    " + machine.isWordAccepted () );
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
      while ( !machine.isReseted () )
      {
        print ( "States:      " );
        boolean first = true;
        for ( State current : machine.getActiveState () )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = false;
          print ( current );
        }
        println ();
        try
        {
          println ( "Symbol:      " + machine.getCurrentSymbol () );
        }
        catch ( WordResetedException exc )
        {
          println ( "Symbol:      RESETED" );
        }
        catch ( WordFinishedException exc )
        {
          println ( "Symbol:      FINISHED" );
        }
        print ( "Transitions: " );
        machine.previousSymbol ();
        TreeSet < Transition > transitions = machine.getActiveTransition ();
        first = false;
        for ( Transition current : transitions )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = true;
          print ( current );
        }
        println ();
        print ( "States:      " );
        for ( State current : machine.getActiveState () )
        {
          if ( !first )
          {
            print ( ", " );
          }
          first = false;
          print ( current );
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
}
