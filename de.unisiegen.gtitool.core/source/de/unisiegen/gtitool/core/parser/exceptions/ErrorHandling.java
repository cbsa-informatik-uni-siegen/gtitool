package de.unisiegen.gtitool.core.parser.exceptions;


import java.awt.Color;
import java.util.prefs.Preferences;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;


/**
 * A helper class for the parser.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class ErrorHandling
{

  /**
   * The {@link Symbol} color.
   */
  private static String symbolColor;


  /**
   * The normal color.
   */
  private static String normalColor;


  /**
   * The symbols.
   */
  private static String symbols1 = "[a-zA-Z0-9]+"; //$NON-NLS-1$


  /**
   * The symbols with a index.
   */
  private static String symbols2 = "[a-zA-Z0-9]+_[a-zA-Z0-9]"; //$NON-NLS-1$


  /**
   * Throws an {@link ParserWarningException}.
   * 
   * @param symbol The symbol which should be completed.
   * @param insertText The text which should be inserted to complete the source
   *          code.
   * @param left The left position in the source code.
   * @param right The right position in the source code.
   * @param tokenSequence The token sequence which should be added.
   */
  public static final void expect ( String symbol, String insertText, int left,
      int right, String ... tokenSequence )
  {
    Preferences preferences = Preferences.userRoot ();
    int r = preferences.getInt ( "PreferencesDialog.ColorParserSymbolR", //$NON-NLS-1$
        PreferenceManager.DEFAULT_SYMBOL_COLOR.getRed () );
    int g = preferences.getInt ( "PreferencesDialog.ColorParserSymbolG", //$NON-NLS-1$
        PreferenceManager.DEFAULT_SYMBOL_COLOR.getGreen () );
    int b = preferences.getInt ( "PreferencesDialog.ColorParserSymbolB", //$NON-NLS-1$
        PreferenceManager.DEFAULT_SYMBOL_COLOR.getBlue () );
    symbolColor = getHexadecimalColor ( new Color ( r, g, b ) );
    normalColor = getHexadecimalColor ( Color.BLACK );
    StringBuilder result = new StringBuilder ();
    result.append ( Messages.QUOTE );
    for ( int i = 0 ; i < tokenSequence.length ; i++ )
    {
      String token = tokenSequence [ i ];
      token = token.replaceAll ( "&", "&amp" ); //$NON-NLS-1$ //$NON-NLS-2$
      token = token.replaceAll ( "<", "&lt" ); //$NON-NLS-1$//$NON-NLS-2$
      token = token.replaceAll ( ">", "&gt" ); //$NON-NLS-1$ //$NON-NLS-2$
      result.append ( syntaxHighlighting ( token ) );
    }
    result.append ( Messages.QUOTE );
    throw new ParserWarningException ( left, right, "<html>" //$NON-NLS-1$
        + Messages.getString ( "Parser.2", result.toString (), "<b>" + symbol //$NON-NLS-1$//$NON-NLS-2$
            + "</b>" ) + "<br>" + "(" + Messages.getString ( "Parser.3" ) + ")" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        + "</html>", insertText ); //$NON-NLS-1$
  }


  /**
   * Returns the color in hexadecimal formatting.
   * 
   * @param color The color which should be returned.
   * @return The color in hexadecimal formatting.
   */
  private static final String getHexadecimalColor ( Color color )
  {
    String red = Integer.toHexString ( color.getRed () );
    red = red.length () == 1 ? red = "0" + red : red; //$NON-NLS-1$
    String green = Integer.toHexString ( color.getGreen () );
    green = green.length () == 1 ? green = "0" + green : green; //$NON-NLS-1$
    String blue = Integer.toHexString ( color.getBlue () );
    blue = blue.length () == 1 ? blue = "0" + blue : blue; //$NON-NLS-1$
    return red + green + blue;
  }


  /**
   * Highlights the syntax of the given input string.
   * 
   * @param input The input string.
   * @return The highlighted the syntax string of the given input string.
   */
  private static final String syntaxHighlighting ( String input )
  {
    if ( input.matches ( symbols2 ) )
    {
      String withIndex = input;
      withIndex = withIndex.substring ( 0, withIndex.length () - 2 );
      withIndex += "<font size = \"-2\"><sub>" + input.charAt ( input.length () - 1 ) + "</sub></font>"; //$NON-NLS-1$ //$NON-NLS-2$
      return "<font color=\"#" + symbolColor + "\">" + withIndex //$NON-NLS-1$ //$NON-NLS-2$
          + "</font>"; //$NON-NLS-1$
    }
    if ( input.matches ( symbols1 ) )
    {
      return "<font color=\"#" + symbolColor + "\">" + input //$NON-NLS-1$ //$NON-NLS-2$
          + "</font>"; //$NON-NLS-1$
    }
    return "<font color=\"#" + normalColor + "\">" + input + "</font>"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  }
}
