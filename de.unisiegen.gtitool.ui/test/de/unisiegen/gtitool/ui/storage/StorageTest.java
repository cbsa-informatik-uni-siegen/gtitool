package de.unisiegen.gtitool.ui.storage;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Word;


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
    testAlphabet ();
    testSymbol ();
    testWord ();
  }


  public static void out ()
  {
    System.out.println ();
  }


  public static void out ( Object pObject )
  {
    System.out.println ( pObject.toString () );
  }


  public static void testAlphabet ()
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


  public static void testSymbol ()
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


  public static void testWord ()
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
}
