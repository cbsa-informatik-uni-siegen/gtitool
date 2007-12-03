package de.unisiegen.gtitool.ui.storage;


import de.unisiegen.gtitool.core.entities.Alphabet;


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
    Alphabet alphabet1 = ( Alphabet ) Storage.getInstance ().load (
        "test/de/unisiegen/gtitool/ui/storage/load.xml" );
    out ( alphabet1 );
    Storage.getInstance ().store ( alphabet1,
        "test/de/unisiegen/gtitool/ui/storage/store.xml" );
    Alphabet alphabet2 = ( Alphabet ) Storage.getInstance ().load (
        "test/de/unisiegen/gtitool/ui/storage/store.xml" );
    out ( alphabet2 );
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
