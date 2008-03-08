package de.unisiegen.gtitool.ui.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;


/**
 * This class is used to calculate the current svn version. Note: Do not edit
 * the system output, because it is used by the build script.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SVNVersion
{

  /**
   * The svn pattern.
   */
  private static Pattern pattern = Pattern
      .compile ( ".*[$]Id: .* .* [0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9]Z .* [$]" ); //$NON-NLS-1$


  /**
   * Returns the current svn version of all projects.
   * 
   * @return The current svn version of all projects.
   */
  private final static int getVersion ()
  {
    int version = -1;
    int newVersion;

    // core
    newVersion = getVersion ( new File ( "../de.unisiegen.gtitool.core" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    // ui
    newVersion = getVersion ( new File ( "../de.unisiegen.gtitool.ui" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    // htdocs
    newVersion = getVersion ( new File ( "../gtitool.htdocs" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    // javacup
    newVersion = getVersion ( new File ( "../gtitool.javacup" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    // jflex
    newVersion = getVersion ( new File ( "../gtitool.jflex" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    // jgraph
    newVersion = getVersion ( new File ( "../gtitool.jgraph" ) ); //$NON-NLS-1$
    version = newVersion > version ? newVersion : version;

    return version;
  }


  /**
   * Returns the current svn version of the given file and all sub files if the
   * file is a directory.
   * 
   * @param file The input file.
   * @return The current svn version of the given file and all sub files if the
   *         file is a directory.
   */
  private final static int getVersion ( File file )
  {
    int version = -1;
    if ( file.isDirectory () )
    {
      File [] files = file.listFiles ();
      for ( int i = 0 ; i < files.length ; i++ )
      {
        int newVersion = getVersion ( files [ i ] );
        if ( newVersion > version )
        {
          version = newVersion;
        }
      }
    }
    else
    {
      return getVersionFile ( file );
    }
    return version;
  }


  /**
   * Returns the current svn version of the given file.
   * 
   * @param file The input file.
   * @return The current svn version of the given file.
   */
  private final static int getVersionFile ( File file )
  {
    try
    {
      FileReader fileReader = new FileReader ( file );
      BufferedReader bufferedReader = new BufferedReader ( fileReader );
      String line;
      while ( ( line = bufferedReader.readLine () ) != null )
      {
        if ( pattern.matcher ( line ).matches () )
        {
          char [] chars = line.toCharArray ();
          for ( int i = 0 ; i < line.length () ; i++ )
          {
            char currentChar = chars [ i ];
            if ( currentChar == '0' || currentChar == '1' || currentChar == '2'
                || currentChar == '3' || currentChar == '4'
                || currentChar == '5' || currentChar == '6'
                || currentChar == '7' || currentChar == '8'
                || currentChar == '9' )
            {
              String s = String.valueOf ( currentChar );
              for ( int j = i + 1 ; j < line.length () ; j++ )
              {
                char currentNextChar = chars [ j ];
                if ( currentNextChar == '0' || currentNextChar == '1'
                    || currentNextChar == '2' || currentNextChar == '3'
                    || currentNextChar == '4' || currentNextChar == '5'
                    || currentNextChar == '6' || currentNextChar == '7'
                    || currentNextChar == '8' || currentNextChar == '9' )
                {
                  s += String.valueOf ( currentNextChar );
                }
                else
                {
                  break;
                }
              }
              return Integer.parseInt ( s );
            }
          }
        }
      }
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
    return -1;
  }


  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public final static void main ( String [] arguments )
  {
    int version = getVersion ();

    // svn version
    System.out.println ( version );
  }
}
