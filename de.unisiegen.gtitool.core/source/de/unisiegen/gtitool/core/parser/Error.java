package de.unisiegen.gtitool.core.parser;


import java.awt.Color;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.parser.exceptions.ParserWarningException;


/**
 * A helper class for the parser.
 * 
 * @author Christian Fehler
 */
public abstract class Error
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
   * @param pSymbol The symbol which should be completed.
   * @param pInsertText The text which should be inserted to complete the source
   *          code.
   * @param pLeft The left position in the source code.
   * @param pRight The right position in the source code.
   * @param pTokenSequence The token sequence which should be added.
   */
  public static final void expect ( String pSymbol, String pInsertText,
      int pLeft, int pRight, String ... pTokenSequence )
  {
    Preferences preferences = Preferences
        .userNodeForPackage ( de.unisiegen.gtitool.core.Messages.class );
    int r = preferences.getInt ( "PreferencesDialog.ColorSymbolR", 255 ); //$NON-NLS-1$
    int g = preferences.getInt ( "PreferencesDialog.ColorSymbolG", 127 ); //$NON-NLS-1$
    int b = preferences.getInt ( "PreferencesDialog.ColorSymbolB", 0 ); //$NON-NLS-1$
    symbolColor = getHexadecimalColor ( new Color ( r, g, b ) );
    normalColor = getHexadecimalColor ( Color.BLACK );
    StringBuilder result = new StringBuilder ();
    result.append ( "\"" ); //$NON-NLS-1$
    for ( int i = 0 ; i < pTokenSequence.length ; i++ )
    {
      String token = pTokenSequence [ i ];
      token = token.replaceAll ( "&", "&amp" ); //$NON-NLS-1$ //$NON-NLS-2$
      token = token.replaceAll ( "<", "&lt" ); //$NON-NLS-1$//$NON-NLS-2$
      token = token.replaceAll ( ">", "&gt" ); //$NON-NLS-1$ //$NON-NLS-2$
      result.append ( syntaxHighlighting ( token ) );
    }
    result.append ( "\"" ); //$NON-NLS-1$
    throw new ParserWarningException ( pLeft, pRight, MessageFormat.format (
        "<html>" + Messages.getString ( "Parser.2" ) + "<br>" + "(" //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
            + Messages.getString ( "Parser.3" ) + ")" + "</html>", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        result.toString (), "<b>" + pSymbol + "</b>" ), pInsertText ); //$NON-NLS-1$//$NON-NLS-2$
  }


  /**
   * Returns the color in hexadecimal formatting.
   * 
   * @param pColor The color which should be returned.
   * @return The color in hexadecimal formatting.
   */
  private static final String getHexadecimalColor ( Color pColor )
  {
    String red = Integer.toHexString ( pColor.getRed () );
    red = red.length () == 1 ? red = "0" + red : red; //$NON-NLS-1$
    String green = Integer.toHexString ( pColor.getGreen () );
    green = green.length () == 1 ? green = "0" + green : green; //$NON-NLS-1$
    String blue = Integer.toHexString ( pColor.getBlue () );
    blue = blue.length () == 1 ? blue = "0" + blue : blue; //$NON-NLS-1$
    return red + green + blue;
  }


  /**
   * Highlights the syntax of the given input string.
   * 
   * @param pInput The input string.
   * @return The highlighted the syntax string of the given input string.
   */
  private static final String syntaxHighlighting ( String pInput )
  {
    if ( pInput.matches ( symbols2 ) )
    {
      String withIndex = pInput;
      withIndex = withIndex.substring ( 0, withIndex.length () - 2 );
      withIndex += "<font size = \"-2\"><sub>" + pInput.charAt ( pInput.length () - 1 ) + "</sub></font>"; //$NON-NLS-1$ //$NON-NLS-2$
      return "<font color=\"#" + symbolColor + "\">" + withIndex //$NON-NLS-1$ //$NON-NLS-2$
          + "</font>"; //$NON-NLS-1$
    }
    if ( pInput.matches ( symbols1 ) )
    {
      return "<font color=\"#" + symbolColor + "\">" + pInput //$NON-NLS-1$ //$NON-NLS-2$
          + "</font>"; //$NON-NLS-1$
    }
    return "<font color=\"#" + normalColor + "\">" + pInput + "</font>"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  }
}
