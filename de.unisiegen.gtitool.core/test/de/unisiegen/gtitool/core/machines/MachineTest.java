package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;


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
    while ( machine.isNextSymbolAvailable () )
    {
      print ( "States:      " );
      boolean first = true;

      for ( State current : machine.getState () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
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
      first = false;
      for ( Transition current : machine.getTransition () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
        if ( !first )
        {
          print ( ", " );
        }
        first = true;
        print ( current );
      }
      println ();
      print ( "States:      " );
      for ( State current : machine.getState () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
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
    println ( "*** Previous *** " );
    println ();
    while ( machine.isPreviousSymbolAvailable () )
    {
      print ( "States:      " );
      boolean first = true;
      for ( State current : machine.getState () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
        if ( !first )
        {
          print ( ", " );
        }
        first = false;
        print ( current );
      }
      println ();
      print ( "Transitions: " );
      machine.previousSymbol ();
      first = false;
      for ( Transition current : machine.getTransition () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
        if ( !first )
        {
          print ( ", " );
        }
        first = true;
        print ( current );
      }
      println ();
      print ( "States:      " );
      for ( State current : machine.getState () )
      {
        if ( !current.isActive () )
        {
          continue;
        }
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
}
