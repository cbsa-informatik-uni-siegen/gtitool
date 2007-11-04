package de.unisiegen.gtitool.ui.preferences;


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
   * @param pColor The {@link Color} of this item.
   * @param pCaption The caption of this item.
   * @param pDescription The description of this item.
   * @param pStandardColor The standard {@link Color} of this item.
   */
  public ColorItem ( Color pColor, String pCaption, String pDescription,
      Color pStandardColor )
  {
    // Color
    setColor ( pColor );
    // Caption
    setCaption ( pCaption );
    // Description
    setDescription ( pDescription );
    // StandardColor
    setStandardColor ( pStandardColor );
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
  public final int compareTo ( ColorItem pOther )
  {
    return this.caption.compareTo ( pOther.caption );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof ColorItem )
    {
      ColorItem other = ( ColorItem ) pOther;
      return ( ( this.color.equals ( other.color ) ) && ( this.standardColor
          .equals ( other.standardColor ) ) );
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
   * @param pCaption The caption to set.
   */
  public final void setCaption ( String pCaption )
  {
    if ( pCaption == null )
    {
      throw new NullPointerException ( "caption is null" ); //$NON-NLS-1$
    }
    if ( pCaption.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "caption is empty" ); //$NON-NLS-1$
    }
    this.caption = pCaption;
  }


  /**
   * Sets the color.
   * 
   * @param pColor The color to set.
   */
  public final void setColor ( Color pColor )
  {
    if ( pColor == null )
    {
      throw new NullPointerException ( "color is null" ); //$NON-NLS-1$
    }
    this.color = pColor;
  }


  /**
   * Sets the description.
   * 
   * @param pDescription The description to set.
   */
  public final void setDescription ( String pDescription )
  {
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    if ( pDescription.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "description is empty" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Sets the standard color.
   * 
   * @param pStandardColor The standard color to set.
   */
  public final void setStandardColor ( Color pStandardColor )
  {
    if ( pStandardColor == null )
    {
      throw new NullPointerException ( "standard color is null" ); //$NON-NLS-1$
    }
    this.standardColor = pStandardColor;
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
