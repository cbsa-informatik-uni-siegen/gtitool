package de.unisiegen.gtitool.ui;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The messages test class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class MessagesTest
{

  /**
   * The readed line list en.
   */
  private static ArrayList < String > readedLineListEn = new ArrayList < String > (
      1000 );


  /**
   * The readed split line list en.
   */
  private static ArrayList < String [] > readedLineSplitListEn = new ArrayList < String [] > (
      1000 );


  /**
   * The readed line list de.
   */
  private static ArrayList < String > readedLineListDe = new ArrayList < String > (
      1000 );


  /**
   * The readed split line list de.
   */
  private static ArrayList < String [] > readedLineSplitListDe = new ArrayList < String [] > (
      1000 );


  /**
   * The error list.
   */
  private static ArrayList < String > errorList = new ArrayList < String > ();


  /**
   * The allowed chars.
   */
  private static char [] allowedChars =
  { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
      'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
      'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
      '5', '6', '7', '8', '9', ' ', ',', '{', '}', '-', '\\', '>', '(', ')',
      '.', ':', '[', ']' };


  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    // core
    System.out.println ( "*** core ***" ); //$NON-NLS-1$
    String fileNameEnCore = MessagesTest.class.getResource (
        "/de/unisiegen/gtitool/core/messages.properties" ).getFile (); //$NON-NLS-1$
    String fileNameDeCore = MessagesTest.class.getResource (
        "/de/unisiegen/gtitool/core/messages_de.properties" ).getFile (); //$NON-NLS-1$
    testFiles ( fileNameEnCore, fileNameDeCore );

    // ui
    System.out.println ();
    System.out.println ( "*** ui ***" ); //$NON-NLS-1$
    String fileNameEnUi = MessagesTest.class.getResource (
        "/de/unisiegen/gtitool/ui/messages.properties" ).getFile (); //$NON-NLS-1$
    String fileNameDeUi = MessagesTest.class.getResource (
        "/de/unisiegen/gtitool/ui/messages_de.properties" ).getFile (); //$NON-NLS-1$
    testFiles ( fileNameEnUi, fileNameDeUi );
  }


  /**
   * Tests the given files.
   * 
   * @param fileNameEn The english file.
   * @param fileNameDe The german file.
   */
  private static void testFiles ( String fileNameEn, String fileNameDe )
  {
    try
    {
      FileReader fileReaderEn = new FileReader ( fileNameEn );

      BufferedReader bufferedReaderEn = new BufferedReader ( fileReaderEn );
      String inputEn;
      while ( ( inputEn = bufferedReaderEn.readLine () ) != null )
      {
        readedLineListEn.add ( inputEn );
        readedLineSplitListEn.add ( inputEn.split ( "=" ) ); //$NON-NLS-1$
      }
      bufferedReaderEn.close ();

      FileReader fileReaderDe = new FileReader ( fileNameDe );
      BufferedReader bufferedReaderDe = new BufferedReader ( fileReaderDe );
      String inputDe;
      while ( ( inputDe = bufferedReaderDe.readLine () ) != null )
      {
        readedLineListDe.add ( inputDe );
        readedLineSplitListDe.add ( inputDe.split ( "=" ) ); //$NON-NLS-1$
      }
      bufferedReaderDe.close ();

      // Check for different keys
      for ( int i = 0 ; i < readedLineListEn.size () ; i++ )
      {
        String [] splitLineEn = readedLineSplitListEn.get ( i );

        // Key found
        if ( splitLineEn.length >= 2 )
        {
          if ( i >= readedLineListDe.size () )
          {
            break;
          }
          String [] splitLineDe = readedLineSplitListDe.get ( i );
          if ( splitLineDe.length >= 2 )
          {
            if ( !splitLineEn [ 0 ].equals ( splitLineDe [ 0 ] ) )
            {
              errorList.add ( formatString ( i + 1 )
                  + ": different keys ('EN': '" //$NON-NLS-1$
                  + splitLineEn [ 0 ] + "' 'DE': '" + splitLineDe [ 0 ] + "')" ); //$NON-NLS-1$ //$NON-NLS-2$
            }
          }
          else
          {
            errorList.add ( formatString ( i + 1 ) + ": no key found in 'DE'" ); //$NON-NLS-1$
          }
        }
      }

      // Duplicated keys En
      for ( int i = 0 ; i < readedLineListEn.size () ; i++ )
      {
        for ( int j = i + 1 ; j < readedLineListEn.size () ; j++ )
        {
          String [] splitLine0 = readedLineSplitListEn.get ( i );
          String [] splitLine1 = readedLineSplitListEn.get ( j );

          if ( splitLine0.length >= 2 && splitLine1.length >= 2 )
          {
            if ( splitLine0 [ 0 ].equals ( splitLine1 [ 0 ] ) )
            {
              errorList.add ( formatString ( i + 1 )
                  + ": 'EN': same key found in line " + ( j + 1 ) + ": '" //$NON-NLS-1$//$NON-NLS-2$
                  + splitLine1 [ 0 ] + "'" ); //$NON-NLS-1$
            }
          }
        }
      }

      // Duplicated keys De
      for ( int i = 0 ; i < readedLineListDe.size () ; i++ )
      {
        for ( int j = i + 1 ; j < readedLineListDe.size () ; j++ )
        {
          String [] splitLine0 = readedLineSplitListDe.get ( i );
          String [] splitLine1 = readedLineSplitListDe.get ( j );

          if ( splitLine0.length >= 2 && splitLine1.length >= 2 )
          {
            if ( splitLine0 [ 0 ].equals ( splitLine1 [ 0 ] ) )
            {
              errorList.add ( formatString ( i + 1 )
                  + ": 'DE': same key found in line " + ( j + 1 ) + ": '" //$NON-NLS-1$ //$NON-NLS-2$
                  + splitLine1 [ 0 ] + "'" ); //$NON-NLS-1$
            }
          }
        }
      }

      // Not allowed keys En
      for ( int i = 0 ; i < readedLineListEn.size () ; i++ )
      {
        String [] splitLineEn = readedLineSplitListEn.get ( i );
        // Key found
        if ( splitLineEn.length >= 2 )
        {
          String value = splitLineEn [ 1 ];
          for ( int k = 0 ; k < value.length () ; k++ )
          {
            boolean found = false;
            for ( int j = 0 ; j < allowedChars.length ; j++ )
            {
              if ( value.charAt ( k ) == allowedChars [ j ] )
              {
                found = true;
              }
            }

            if ( !found )
            {
              errorList.add ( formatString ( i + 1 )
                  + ": 'EN': not allowed char: " + value.charAt ( k ) ); //$NON-NLS-1$
            }
          }
        }
      }

      // Not allowed keys De
      for ( int i = 0 ; i < readedLineListDe.size () ; i++ )
      {
        String [] splitLineDe = readedLineSplitListDe.get ( i );
        // Key found
        if ( splitLineDe.length >= 2 )
        {
          String value = splitLineDe [ 1 ];
          for ( int k = 0 ; k < value.length () ; k++ )
          {
            boolean found = false;
            for ( int j = 0 ; j < allowedChars.length ; j++ )
            {
              if ( value.charAt ( k ) == allowedChars [ j ] )
              {
                found = true;
              }
            }

            if ( !found )
            {
              errorList.add ( formatString ( i + 1 )
                  + ": 'DE': not allowed char: " + value.charAt ( k ) ); //$NON-NLS-1$
            }
          }
        }
      }

      // Not the same number of lines
      if ( readedLineListEn.size () != readedLineListDe.size () )
      {
        errorList.add ( "different line numbers" ); //$NON-NLS-1$
      }

      System.out.println ( "Readed lines 'EN': " + readedLineListEn.size () ); //$NON-NLS-1$
      System.out.println ( "Readed lines 'DE': " + readedLineListDe.size () ); //$NON-NLS-1$

      if ( errorList.size () > 0 )
      {
        System.err.println ();
        System.err.println ( "=> Errors found:" ); //$NON-NLS-1$
        for ( String current : errorList )
        {
          System.err.println ( current );
        }
      }
      else
      {
        System.out.println ( "=> No errors found!" ); //$NON-NLS-1$
      }
    }
    catch ( FileNotFoundException exc )
    {
      exc.printStackTrace ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
    }
    readedLineListEn.clear ();
    readedLineSplitListEn.clear ();
    readedLineListDe.clear ();
    readedLineSplitListDe.clear ();
  }


  /**
   * Returns the formatted string.
   * 
   * @param number The input number.
   * @return The formatted string.
   */
  private static String formatString ( int number )
  {
    String result = String.valueOf ( number );
    int max = readedLineListEn.size () > readedLineListDe.size () ? readedLineListEn
        .size ()
        : readedLineListDe.size ();
    for ( int i = String.valueOf ( number ).length () ; i < String.valueOf (
        max ).length () ; i++ )
    {
      result = " " + result; //$NON-NLS-1$
    }
    return result;
  }
}
