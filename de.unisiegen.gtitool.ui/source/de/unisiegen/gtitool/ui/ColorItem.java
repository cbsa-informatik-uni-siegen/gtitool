package de.unisiegen.gtitool.ui;


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
public class ColorItem
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
   * Allocates a new <code>ColorItem</code>.
   * 
   * @param pColor The {@link Color} of this item.
   * @param pCaption The caption of this item.
   * @param pDescription The description of this item.
   */
  public ColorItem ( Color pColor, String pCaption, String pDescription )
  {
    // Color
    if ( pColor == null )
    {
      throw new NullPointerException ( "color is null" ); //$NON-NLS-1$
    }
    this.color = pColor;
    // Caption
    if ( pCaption == null )
    {
      throw new NullPointerException ( "caption is null" ); //$NON-NLS-1$
    }
    this.caption = pCaption;
    // Description
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Returns the caption.
   * 
   * @return The caption.
   * @see #caption
   */
  public String getCaption ()
  {
    return this.caption;
  }


  /**
   * Returns the color.
   * 
   * @return The color.
   * @see #color
   */
  public Color getColor ()
  {
    return this.color;
  }


  /**
   * Returns the description.
   * 
   * @return The description.
   * @see #description
   */
  public String getDescription ()
  {
    return this.description;
  }


  /**
   * Returns the icon of this item.
   * 
   * @return The icon of this item.
   */
  public ImageIcon getIcon ()
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
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.caption;
  }
}
