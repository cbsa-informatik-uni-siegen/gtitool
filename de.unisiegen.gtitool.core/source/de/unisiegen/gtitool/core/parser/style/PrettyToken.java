package de.unisiegen.gtitool.core.parser.style;


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
}
