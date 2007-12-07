package de.unisiegen.gtitool.ui.storage;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
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

  public static void main ( String [] pArguments )
  {
    switch ( 0 )
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

    DFA dfa = new DFA ( alphabet );
    dfa.addState ( z0, z1, z2 );
    dfa.addTransition ( t0, t1, t2, t3, t4 );

    Word word = new Word ( b, c, a, c, b, a, b );
    try
    {
      Storage.getInstance ().store ( alphabet,
          "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" );
      Alphabet loadedAlphabet = ( Alphabet ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" );
      out ( loadedAlphabet );

      Storage.getInstance ().store ( z1,
          "test/de/unisiegen/gtitool/ui/storage/state.xml" );
      State loadedState = ( State ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/state.xml" );
      out ( loadedState );

      Storage.getInstance ().store ( a,
          "test/de/unisiegen/gtitool/ui/storage/symbol.xml" );
      Symbol loadedSymbol = ( Symbol ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/symbol.xml" );
      out ( loadedSymbol );

      Storage.getInstance ().store ( t1,
          "test/de/unisiegen/gtitool/ui/storage/transition.xml" );
      Transition loadedTransition = ( Transition ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/transition.xml" );
      out ( loadedTransition );

      Storage.getInstance ().store ( word,
          "test/de/unisiegen/gtitool/ui/storage/word.xml" );
      Word loadedWord = ( Word ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/word.xml" );
      out ( loadedWord );
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
          "test/de/unisiegen/gtitool/ui/storage/alphabet.xml" );
      out ( alphabet1 );
      Storage.getInstance ().store ( alphabet1,
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      Alphabet alphabet2 = ( Alphabet ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
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
          "test/de/unisiegen/gtitool/ui/storage/state.xml" );
      out ( state1 );
      Storage.getInstance ().store ( state1,
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      State state2 = ( State ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
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
          "test/de/unisiegen/gtitool/ui/storage/symbol.xml" );
      out ( symbol1 );
      Storage.getInstance ().store ( symbol1,
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      Symbol symbol2 = ( Symbol ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
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
          "test/de/unisiegen/gtitool/ui/storage/transition.xml" );
      out ( transition1 );
      Storage.getInstance ().store ( transition1,
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      Transition transition2 = ( Transition ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
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
          "test/de/unisiegen/gtitool/ui/storage/word.xml" );
      out ( word1 );
      Storage.getInstance ().store ( word1,
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      Word word2 = ( Word ) Storage.getInstance ().load (
          "test/de/unisiegen/gtitool/ui/storage/store.xml" );
      out ( word2 );
    }
    catch ( StoreException exc )
    {
      System.err.println ( "StoreException" );
      exc.printStackTrace ();
    }
  }
}
