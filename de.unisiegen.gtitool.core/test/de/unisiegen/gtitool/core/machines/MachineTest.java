package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;

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
        for ( int i = 0 ; i < machine.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( machine.getActiveState ( i ) );
        }
        println ();
        print ( "Transitions: " );
        ArrayList < Transition > transitions = machine.nextSymbol ();
        for ( int i = 0 ; i < transitions.size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( transitions.get ( i ) );
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
        for ( int i = 0 ; i < machine.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( machine.getActiveState ( i ) );
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
        for ( int i = 0 ; i < machine.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( machine.getActiveState ( i ) );
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
        ArrayList < Transition > transitions = machine.previousSymbol ();
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
        for ( int i = 0 ; i < machine.getActiveState ().size () ; i++ )
        {
          if ( i > 0 )
          {
            print ( ", " );
          }
          print ( machine.getActiveState ( i ) );
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
