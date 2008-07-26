package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;


/**
 * This class represents a {@link PrettyToken}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyToken implements Cloneable
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
  public PrettyToken ( String text, Style style )
  {
    if ( text == null )
    {
      throw new IllegalArgumentException ( "text is null" ); //$NON-NLS-1$
    }
    if ( style == null )
    {
      throw new IllegalArgumentException ( "style is null" ); //$NON-NLS-1$
    }

    this.text = text;
    this.style = style;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final PrettyToken clone ()
  {
    PrettyToken newPrettyToken = new PrettyToken ( this.text, this.style );
    newPrettyToken.setOverwrittenColor ( this.overwrittenColor );
    return newPrettyToken;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof PrettyToken )
    {
      PrettyToken prettyToken = ( PrettyToken ) other;
      return this.text.equals ( prettyToken.text )
          && this.style.equals ( prettyToken.style );
    }

    return false;
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
   * Returns the overwritten {@link Color}.
   * 
   * @return The overwritten {@link Color}.
   */
  public final Color getOverwrittenColor ()
  {
    return this.overwrittenColor;
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
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return 11 * this.text.hashCode () + 13 * this.style.hashCode ();
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
   * Sets the overwritten {@link Color}.
   * 
   * @param overwrittenColor The overwritten {@link Color}.
   */
  public final void setOverwrittenColor ( Color overwrittenColor )
  {
    this.overwrittenColor = overwrittenColor;
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
