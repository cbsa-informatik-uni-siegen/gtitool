package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;


/**
 * This class represents a {@link PrettyToken}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyToken
{

  /**
   * The text.
   */
  private String text;


  /**
   * The {@link Style}.
   */
  private Style style;


  /**
   * The overwritten {@link Color}.
   */
  private Color overwrittenColor = null;


  /**
   * Allocates a new {@link PrettyToken}.
   * 
   * @param text The text.
   * @param style The {@link Style}.
   */
  public PrettyToken ( char text, Style style )
  {
    this ( String.valueOf ( text ), style );
  }


  /**
   * Allocates a new {@link PrettyToken}.
   * 
   * @param text The text.
   * @param style The {@link Style}.
   */
  public PrettyToken ( String text, Style style )
  {
    this.text = text;
    this.style = style;
  }


  /**
   * Returns the char array.
   * 
   * @return The char array.
   */
  public final char [] getChar ()
  {
    return this.text.toCharArray ();
  }


  /**
   * Returns the {@link Color}.
   * 
   * @return The {@link Color}.
   */
  public final Color getColor ()
  {
    if ( this.overwrittenColor != null )
    {
      return this.overwrittenColor;
    }
    return this.style.getColor ();
  }


  /**
   * Returns the {@link Style}.
   * 
   * @return The {@link Style}.
   */
  public final Style getStyle ()
  {
    return this.style;
  }


  /**
   * Returns the text.
   * 
   * @return The text.
   */
  public final String getText ()
  {
    return this.text;
  }


  /**
   * Returns the bold value.
   * 
   * @return The bold value.
   */
  public final boolean isBold ()
  {
    return this.style.isBold ();
  }


  /**
   * Returns the italic value.
   * 
   * @return The italic value.
   */
  public final boolean isItalic ()
  {
    return this.style.isItalic ();
  }


  /**
   * Overwrites the {@link Color} with the given {@link Color}.
   * 
   * @param newColor The new {@link Color}.
   */
  public final void overwrite ( Color newColor )
  {
    this.overwrittenColor = newColor;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.text;
  }
}
