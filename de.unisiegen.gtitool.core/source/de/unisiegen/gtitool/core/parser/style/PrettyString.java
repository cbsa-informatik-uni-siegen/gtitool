package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class represents a {@link PrettyString} which contains
 * {@link PrettyToken}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyString implements Iterable < PrettyToken >
{

  /**
   * The {@link PrettyString} mode enum.
   * 
   * @author Christian Fehler
   */
  public enum PrettyStringMode
  {

    /**
     * The {@link PrettyString} caching is active.
     */
    CACHING_ON,

    /**
     * The {@link PrettyString} caching is deactive.
     */
    CACHING_OFF;
  }


  /**
   * The {@link PrettyStringMode}.
   */
  public static final PrettyStringMode MODE = PrettyStringMode.CACHING_ON;


  /**
   * Flag that indicates if the short version is used.
   */
  private boolean shortVersion = false;


  /**
   * The beginning of the html.
   */
  private static final String HTML_BEGIN = "<html>"; //$NON-NLS-1$


  /**
   * The end of the html.
   */
  private static final String HTML_END = "</html>"; //$NON-NLS-1$


  /**
   * The beginning of the font.
   */
  private static final String FONT_BEGIN = "<font color=\"#"; //$NON-NLS-1$


  /**
   * After the color of the font.
   */
  private static final String FONT_AFTER_COLOR = "\">"; //$NON-NLS-1$


  /**
   * The end of the font.
   */
  private static final String FONT_END = "</font>"; //$NON-NLS-1$


  /**
   * String for the beginning of bold {@link PrettyToken}s.
   */
  private static final String BOLD_BEGIN = "<b>"; //$NON-NLS-1$


  /**
   * String for the end of bold {@link PrettyToken}s.
   */
  private static final String BOLD_END = "</b>"; //$NON-NLS-1$


  /**
   * String for the beginning of italic {@link PrettyToken}s.
   */
  private static final String ITALIC_BEGIN = "<i>"; //$NON-NLS-1$


  /**
   * String for the end of italic {@link PrettyToken}s.
   */
  private static final String ITALIC_END = "</i>"; //$NON-NLS-1$


  /**
   * Returns the {@link Color} in hexadecimal formatting.
   * 
   * @param color The {@link Color} which should be returned.
   * @return The {@link Color} in hexadecimal formatting.
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
   * The {@link PrettyToken} list.
   */
  private ArrayList < PrettyToken > prettyTokenList;


  /**
   * Allocates a new {@link PrettyString}.
   */
  public PrettyString ()
  {
    this.prettyTokenList = new ArrayList < PrettyToken > ();
  }


  /**
   * Allocates a new {@link PrettyString}.
   * 
   * @param prettyToken The {@link PrettyToken} to add.
   */
  public PrettyString ( PrettyToken prettyToken )
  {
    this ();
    addPrettyToken ( prettyToken );
  }


  /**
   * Adds the given {@link PrettyPrintable} to the list of {@link PrettyToken}s.
   * 
   * @param prettyPrintable The {@link PrettyPrintable} to add.
   */
  public final void addPrettyPrintable ( PrettyPrintable prettyPrintable )
  {
    for ( PrettyToken current : prettyPrintable.toPrettyString () )
    {
      this.prettyTokenList.add ( current );
    }
  }


  /**
   * Adds the given {@link PrettyString} to the list of {@link PrettyToken}s.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  public final void addPrettyString ( PrettyString prettyString )
  {
    for ( PrettyToken current : prettyString )
    {
      this.prettyTokenList.add ( current );
    }
  }


  /**
   * Adds the given {@link PrettyToken} to the list of {@link PrettyToken}s.
   * 
   * @param prettyToken The {@link PrettyToken} to add.
   */
  public final void addPrettyToken ( PrettyToken prettyToken )
  {
    this.prettyTokenList.add ( prettyToken );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof PrettyString )
    {
      PrettyString prettyString = ( PrettyString ) other;
      return this.prettyTokenList.equals ( prettyString.prettyTokenList );
    }

    return false;
  }


  /**
   * Returns the {@link PrettyToken} list.
   * 
   * @return The {@link PrettyToken} list.
   */
  public final ArrayList < PrettyToken > getPrettyToken ()
  {
    return this.prettyTokenList;
  }


  /**
   * Returns the {@link PrettyToken} with the given index.
   * 
   * @param index The index of the {@link PrettyToken} to return.
   * @return The {@link PrettyToken} with the given index.
   */
  public final PrettyToken getPrettyToken ( int index )
  {
    return this.prettyTokenList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.prettyTokenList.hashCode ();
  }


  /**
   * Returns true if the {@link PrettyToken} list is empty, otherwise false.
   * 
   * @return True if the {@link PrettyToken} list is empty, otherwise false.
   */
  public final boolean isEmpty ()
  {
    return this.prettyTokenList.isEmpty ();
  }


  /**
   * Returns the short version flag.
   * 
   * @return The short version flag.
   * @see #shortVersion
   */
  public final boolean isShortVersion ()
  {
    return this.shortVersion;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < PrettyToken > iterator ()
  {
    return this.prettyTokenList.iterator ();
  }


  /**
   * Overwrites the {@link Style} of every {@link PrettyToken} with the given
   * {@link Style}.
   * 
   * @param newStyle The new {@link Style}.
   */
  public final void overwriteColor ( Style newStyle )
  {
    for ( PrettyToken current : this.prettyTokenList )
    {
      current.overwrite ( newStyle.getColor () );
    }
  }


  /**
   * Removes the last {@link PrettyToken}.
   * 
   * @return The last {@link PrettyToken}.
   */
  public final PrettyToken removeLastPrettyToken ()
  {
    if ( this.prettyTokenList.size () == 0 )
    {
      throw new RuntimeException ( "list is empty" ); //$NON-NLS-1$
    }

    return this.prettyTokenList.remove ( this.prettyTokenList.size () - 1 );
  }


  /**
   * Replaces the target with the replacement.
   * 
   * @param target The target.
   * @param replacement The replacement.
   */
  public final void replace ( String target, PrettyString replacement )
  {
    for ( int i = 0 ; i < this.prettyTokenList.size () ; i++ )
    {
      PrettyToken current = this.prettyTokenList.get ( i );
      if ( current.getText ().contains ( target ) )
      {
        this.prettyTokenList.remove ( i );

        PrettyToken newToken0 = new PrettyToken ( current.getText ().substring (
            0, current.getText ().indexOf ( target ) ), current.getStyle () );
        PrettyToken newToken1 = new PrettyToken ( current.getText ().substring (
            current.getText ().indexOf ( target ) + target.length () ), current
            .getStyle () );

        int index = i;
        this.prettyTokenList.add ( index, newToken0 );
        index++ ;
        for ( PrettyToken newToken : replacement.prettyTokenList )
        {
          this.prettyTokenList.add ( index, newToken );
          index++ ;
        }
        this.prettyTokenList.add ( index, newToken1 );
        return;
      }
    }
  }


  /**
   * Sets the short version flag.
   * 
   * @param shortVersion The short version flag to set.
   * @see #shortVersion
   */
  public final void setShortVersion ( boolean shortVersion )
  {
    this.shortVersion = shortVersion;
  }


  /**
   * Returns the size.
   * 
   * @return The size.
   */
  public final int size ()
  {
    return this.prettyTokenList.size ();
  }


  /**
   * Returns the html string.
   * 
   * @return The html string.
   */
  public final String toHTMLString ()
  {
    StringBuilder result = new StringBuilder ();

    result.append ( HTML_BEGIN );
    for ( PrettyToken current : this.prettyTokenList )
    {
      String text = current.getText ();
      Style style = current.getStyle ();
      Color color = style.getColor ();
      boolean bold = style.isBold ();
      boolean italic = style.isItalic ();

      if ( bold )
      {
        result.append ( BOLD_BEGIN );
      }
      if ( italic )
      {
        result.append ( ITALIC_BEGIN );
      }
      result.append ( FONT_BEGIN );
      result.append ( getHexadecimalColor ( color ) );
      result.append ( FONT_AFTER_COLOR );
      result.append ( text );
      result.append ( FONT_END );
      if ( italic )
      {
        result.append ( ITALIC_END );
      }
      if ( bold )
      {
        result.append ( BOLD_END );
      }
    }
    result.append ( HTML_END );

    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    for ( PrettyToken current : this.prettyTokenList )
    {
      result.append ( current.getText () );
    }
    return result.toString ();
  }
}
