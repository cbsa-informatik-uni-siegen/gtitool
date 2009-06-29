package de.unisiegen.gtitool.ui.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.unisiegen.gtitool.ui.logic.ConvertGrammarDialog.ConvertGrammarType;
import de.unisiegen.gtitool.ui.logic.ConvertMachineDialog.ConvertMachineType;
import de.unisiegen.gtitool.ui.logic.ConvertRegexToMachineDialog.ConvertRegexType;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Helper class for loading texts from files
 */
public class TextLoader
{

  /**
   * Loads the algorithm for a given {@link ConvertRegexType}
   * 
   * @param type The {@link ConvertRegexType}
   * @return The algorithm
   */
  public String loadAlgorithm ( ConvertRegexType type )
  {
    String filename = "/de/unisiegen/gtitool/ui/algorithms/"; //$NON-NLS-1$
    filename += PreferenceManager.getInstance ().getLanguageItem ()
        .getLocale ().toString ()
        + "/" + type.toString () + ".txt"; //$NON-NLS-1$ //$NON-NLS-2$
    return loadTextFromFile ( filename );
  }


  /**
   * Loads the algorithm for minimize machine
   * 
   * @return The algorithm
   */
  public String loadMinimizeAlgorithm ()
  {
    String filename = "/de/unisiegen/gtitool/ui/algorithms/"; //$NON-NLS-1$
    filename += PreferenceManager.getInstance ().getLanguageItem ()
        .getLocale ().toString ()
        + "/MINIMIZE_MACHINE.txt"; //$NON-NLS-1$
    return loadTextFromFile ( filename );
  }


  /**
   * Loads the algorithm for a given {@link ConvertMachineType}
   * 
   * @param type The {@link ConvertMachineType}
   * @return The algorithm
   */
  public String loadAlgorithm ( ConvertMachineType type )
  {
    String filename = "/de/unisiegen/gtitool/ui/algorithms/"; //$NON-NLS-1$
    filename += PreferenceManager.getInstance ().getLanguageItem ()
        .getLocale ().toString ()
        + "/" + type.toString () + ".txt"; //$NON-NLS-1$ //$NON-NLS-2$
    return loadTextFromFile ( filename );
  }


  /**
   * Loads the algorithm for a given {@link ConvertGrammarType}
   * 
   * @param type The {@link ConvertGrammarType}
   * @return The algorithm
   */
  public String loadAlgorithm ( ConvertGrammarType type )
  {
    String filename = "/de/unisiegen/gtitool/ui/algorithms/"; //$NON-NLS-1$
    filename += PreferenceManager.getInstance ().getLanguageItem ()
        .getLocale ().toString ()
        + "/" + type.toString () + ".txt"; //$NON-NLS-1$ //$NON-NLS-2$
    return loadTextFromFile ( filename );
  }


  /**
   * Loads a text from a file with a given filename
   * 
   * @param filename The filename
   * @return The text
   */
  private String loadTextFromFile ( String filename )
  {
    String text = new String ();
    try
    {
      BufferedReader reader = new BufferedReader ( new InputStreamReader (
          getClass ().getResourceAsStream ( filename ), "UTF8" ) );//$NON-NLS-1$

      String input;
      boolean first = true;
      while ( ( input = reader.readLine () ) != null )
      {
        if ( !first )
        {
          text += "\n";//$NON-NLS-1$
        }
        first = false;
        text += input;
      }
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    return text;
  }
}
