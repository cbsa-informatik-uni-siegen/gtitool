package de.unisiegen.gtitool.ui.storage;


import java.io.File;

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
import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.storage.Storage;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The test class of the storage.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class StorageTest
{

  public static void main ( String [] arguments )
  {
    switch ( 6 )
    {
      case 0 :
        test ();
        break;
      case 1 :
        testAlphabet ();
        break;
      case 2 :
        testState ();
        break;
      case 3 :
        testSymbol ();
        break;
      case 4 :
        testTransition ();
        break;
      case 5 :
        testWord ();
        break;
      case 6 :
        testMachine ();
        break;
    }
  }


  public static void out ()
  {
    System.out.println ();
  }


  public static void out ( Object object )
  {
    System.out.println ( object.toString () );
  }


  public static void test ()
  {
    Symbol a = null;
    Symbol b = null;
    Symbol c = null;
    Symbol d = null;
    Symbol e = null;
    Symbol f = null;
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

    Alphabet alphabet = null;
    try
    {
      alphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    Alphabet pushDownAlphabet = null;
    try
    {
      pushDownAlphabet = new DefaultAlphabet ( a, b, c );
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
      z0 = new DefaultState ( alphabet, pushDownAlphabet, true, false );
      z1 = new DefaultState ( alphabet, pushDownAlphabet, false, false );
      z2 = new DefaultState ( alphabet, pushDownAlphabet, false, true );
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
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z0,
          z0, b, c );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z0,
          z1, a );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z1,
          z1, a, c );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z1,
          z2, b );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z2,
          z2, a, b, c );
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

    DFA dfa = new DefaultDFA ( alphabet, pushDownAlphabet, true );
    dfa.addState ( z0, z1, z2 );
    dfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new DefaultWord ( b, c, a, c, b, a, b );
    try
    {
      Storage.getInstance ().store ( alphabet,
          new File ( "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" ) );
      Alphabet loadedAlphabet = ( Alphabet ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" ),
          Alphabet.class );
      out ( loadedAlphabet );

      Storage.getInstance ().store ( z1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/state.xml" ) );
      State loadedState = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/state.xml" ),
          State.class );
      out ( loadedState );

      Storage.getInstance ().store ( a,
          new File ( "test/de/unisiegen/gtitool/ui/storage/symbol.xml" ) );
      Symbol loadedSymbol = ( Symbol ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/symbol.xml" ),
          Symbol.class );
      out ( loadedSymbol );

      Storage.getInstance ().store ( t1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/transition.xml" ) );
      Transition loadedTransition = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/transition.xml" ),
          Transition.class );
      out ( loadedTransition );

      Storage.getInstance ().store ( word,
          new File ( "test/de/unisiegen/gtitool/ui/storage/word.xml" ) );
      Word loadedWord = ( Word ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/word.xml" ),
          Word.class );
      out ( loadedWord );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testMachine ()
  {
    Symbol a = null;
    Symbol b = null;
    Symbol c = null;
    Symbol d = null;
    Symbol e = null;
    Symbol f = null;
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

    Alphabet alphabet = null;
    try
    {
      alphabet = new DefaultAlphabet ( a, b, c );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    Alphabet pushDownAlphabet = null;
    try
    {
      pushDownAlphabet = new DefaultAlphabet ( a, b, c );
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
      z0 = new DefaultState ( alphabet, pushDownAlphabet, true, false );
      z1 = new DefaultState ( alphabet, pushDownAlphabet, false, false );
      z2 = new DefaultState ( alphabet, pushDownAlphabet, false, true );
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
      t0 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z0,
          z0, b, c );
      t1 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z0,
          z1, a );
      t2 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z1,
          z1, a, c );
      t3 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z1,
          z2, b );
      t4 = new DefaultTransition ( alphabet, pushDownAlphabet, null, null, z2,
          z2, a, b, c );
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

    DFA dfa = new DefaultDFA ( alphabet, pushDownAlphabet, true );
    dfa.addState ( z0, z1, z2 );
    dfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new DefaultWord ( b, c, a, c, b, a, b );
    try
    {
      Storage.getInstance ().store ( z0,
          new File ( "test/de/unisiegen/gtitool/ui/storage/z0.xml" ) );
      State z0loaded = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/z0.xml" ),
          State.class );

      Storage.getInstance ().store ( z1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/z1.xml" ) );
      State z1loaded = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/z1.xml" ),
          State.class );

      Storage.getInstance ().store ( z2,
          new File ( "test/de/unisiegen/gtitool/ui/storage/z2.xml" ) );
      State z2loaded = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/z2.xml" ),
          State.class );

      Storage.getInstance ().store ( t0,
          new File ( "test/de/unisiegen/gtitool/ui/storage/t0.xml" ) );
      Transition t0loaded = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/t0.xml" ),
          Transition.class );

      Storage.getInstance ().store ( t1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/t1.xml" ) );
      Transition t1loaded = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/t1.xml" ),
          Transition.class );

      Storage.getInstance ().store ( t2,
          new File ( "test/de/unisiegen/gtitool/ui/storage/t2.xml" ) );
      Transition t2loaded = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/t2.xml" ),
          Transition.class );

      Storage.getInstance ().store ( t3,
          new File ( "test/de/unisiegen/gtitool/ui/storage/t3.xml" ) );
      Transition t3loaded = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/t3.xml" ),
          Transition.class );

      Storage.getInstance ().store ( t4,
          new File ( "test/de/unisiegen/gtitool/ui/storage/t4.xml" ) );
      Transition t4loaded = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/t4.xml" ),
          Transition.class );

      DFA dfaloaded = new DefaultDFA ( alphabet, pushDownAlphabet, true );
      dfaloaded.addState ( z0loaded, z1loaded, z2loaded );
      dfaloaded.addTransition ( t0loaded, t1loaded, t2loaded, t3loaded,
          t4loaded );

      Word word_loaded = new DefaultWord ( b, c, a, c, b, a, b );

      dfaloaded.start ( word_loaded );
      out ( "*** Next *** " );
      out ();
      try
      {
        while ( !word_loaded.isFinished () )
        {
          out ( "State:      "
              + dfaloaded.getActiveState ().first ().getName () );
          dfaloaded.nextSymbol ();
          out ( "Transition: "
              + dfaloaded.getActiveTransition ().first ().getSymbol () );
          out ( "Symbol:     " + dfaloaded.getCurrentSymbol () );
          out ( "State:      "
              + dfaloaded.getActiveState ().first ().getName () );
          out ( "Accepted:   " + dfaloaded.isWordAccepted () );
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
        while ( !word_loaded.isReseted () )
        {
          out ( "State:      "
              + dfaloaded.getActiveState ().first ().getName () );
          out ( "Symbol:     " + dfaloaded.getCurrentSymbol () );
          dfaloaded.previousSymbol ();
          out ( "Transition: "
              + dfaloaded.getActiveTransition ().first ().getSymbol () );
          out ( "State:      "
              + dfaloaded.getActiveState ().first ().getName () );
          out ();
        }
      }
      catch ( WordException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testAlphabet ()
  {
    try
    {
      Alphabet alphabet1 = ( Alphabet ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" ),
          Alphabet.class );
      out ( alphabet1 );
      Storage.getInstance ().store ( alphabet1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ) );
      Alphabet alphabet2 = ( Alphabet ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ),
          Alphabet.class );
      out ( alphabet2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testState ()
  {
    try
    {
      State state1 = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/state.xml" ),
          State.class );
      out ( state1 );
      Storage.getInstance ().store ( state1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ) );
      State state2 = ( State ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ),
          State.class );
      out ( state2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testSymbol ()
  {
    try
    {
      Symbol symbol1 = ( Symbol ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/symbol.xml" ),
          Symbol.class );
      out ( symbol1 );
      Storage.getInstance ().store ( symbol1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ) );
      Symbol symbol2 = ( Symbol ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ),
          Symbol.class );
      out ( symbol2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testTransition ()
  {
    try
    {
      Transition transition1 = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/transition.xml" ),
          Transition.class );
      out ( transition1 );
      Storage.getInstance ().store ( transition1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ) );
      Transition transition2 = ( Transition ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ),
          Transition.class );
      out ( transition2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }


  public static void testWord ()
  {
    try
    {
      Word word1 = ( Word ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/word.xml" ),
          Word.class );
      out ( word1 );
      Storage.getInstance ().store ( word1,
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ) );
      Word word2 = ( Word ) Storage.getInstance ().load (
          new File ( "test/de/unisiegen/gtitool/ui/storage/store.xml" ),
          Word.class );
      out ( word2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }
}
