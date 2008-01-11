package de.unisiegen.gtitool.ui.preferences.item;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


/**
 * The color item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ColorItem implements Cloneable, Comparable < ColorItem >
{

  /**
   * The {@link Color} of this item.
   */
  private Color color;


  /**
   * The caption of this item.
   */
  private String caption;


  /**
   * The description of this item.
   */
  private String description;


  /**
   * The standard {@link Color} of this item.
   */
  private Color standardColor;


  /**
   * Allocates a new <code>ColorItem</code>.
   * 
   * @param color The {@link Color} of this item.
   * @param caption The caption of this item.
   * @param description The description of this item.
   * @param standardColor The standard {@link Color} of this item.
   */
  public ColorItem ( Color color, String caption, String description,
      Color standardColor )
  {
    // Color
    setColor ( color );
    // Caption
    setCaption ( caption );
    // Description
    setDescription ( description );
    // StandardColor
    setStandardColor ( standardColor );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final ColorItem clone ()
  {
    return new ColorItem ( this.color, this.caption, this.description,
        this.standardColor );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( ColorItem other )
  {
    return this.caption.compareTo ( other.caption );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof ColorItem )
    {
      ColorItem colorItem = ( ColorItem ) other;
      return ( ( this.color.equals ( colorItem.color ) ) && ( this.standardColor
          .equals ( colorItem.standardColor ) ) );
    }
    return false;
  }


  /**
   * Returns the caption.
   * 
   * @return The caption.
   * @see #caption
   */
  public final String getCaption ()
  {
    return this.caption;
  }


  /**
   * Returns the color.
   * 
   * @return The color.
   * @see #color
   */
  public final Color getColor ()
  {
    return this.color;
  }


  /**
   * Returns the description.
   * 
   * @return The description.
   * @see #description
   */
  public final String getDescription ()
  {
    return this.description;
  }


  /**
   * Returns the icon of this item.
   * 
   * @return The icon of this item.
   */
  public final ImageIcon getIcon ()
  {
    BufferedImage image = new BufferedImage ( 16, 10,
        BufferedImage.TYPE_INT_RGB );
    Graphics graphics = image.getGraphics ();
    graphics.setColor ( this.color );
    graphics.fillRect ( 0, 0, 16, 10 );
    graphics.setColor ( this.color.darker () );
    graphics.drawRect ( 0, 0, 15, 9 );
    return new ImageIcon ( image );
  }


  /**
   * Returns the standard color.
   * 
   * @return The standard color.
   * @see #standardColor
   */
  public final Color getStandardColor ()
  {
    return this.standardColor;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.color.hashCode () + this.standardColor.hashCode ();
  }


  /**
   * Restores the default {@link Color} of this item.
   */
  public final void restore ()
  {
    this.color = this.standardColor;
  }


  /**
   * Sets the caption.
   * 
   * @param caption The caption to set.
   */
  public final void setCaption ( String caption )
  {
    if ( caption == null )
    {
      throw new NullPointerException ( "caption is null" ); //$NON-NLS-1$
    }
    if ( caption.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "caption is empty" ); //$NON-NLS-1$
    }
    this.caption = caption;
  }


  /**
   * Sets the color.
   * 
   * @param color The color to set.
   */
  public final void setColor ( Color color )
  {
    if ( color == null )
    {
      throw new NullPointerException ( "color is null" ); //$NON-NLS-1$
    }
    this.color = color;
  }


  /**
   * Sets the description.
   * 
   * @param description The description to set.
   */
  public final void setDescription ( String description )
  {
    if ( description == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    if ( description.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "description is empty" ); //$NON-NLS-1$
    }
    this.description = description;
  }


  /**
   * Sets the standard color.
   * 
   * @param standardColor The standard color to set.
   */
  public final void setStandardColor ( Color standardColor )
  {
    if ( standardColor == null )
    {
      throw new NullPointerException ( "standard color is null" ); //$NON-NLS-1$
    }
    this.standardColor = standardColor;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.caption;
  }
}
