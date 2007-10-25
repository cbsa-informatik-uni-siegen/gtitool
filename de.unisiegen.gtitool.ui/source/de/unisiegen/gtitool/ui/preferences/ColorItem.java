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
public class ColorItem
{

  /**
   * The name of this item.
   */
  private String name;


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
   * @param pName The name of this item.
   * @param pColor The {@link Color} of this item.
   * @param pCaption The caption of this item.
   * @param pDescription The description of this item.
   */
  public ColorItem ( String pName, Color pColor, String pCaption,
      String pDescription )
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    this.name = pName;
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
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public String getName ()
  {
    return this.name;
  }


  /**
   * Sets the caption.
   * 
   * @param pCaption The caption to set.
   */
  public void setCaption ( String pCaption )
  {
    if ( pCaption == null )
    {
      throw new NullPointerException ( "caption is null" ); //$NON-NLS-1$
    }
    this.caption = pCaption;
  }


  /**
   * Sets the color.
   * 
   * @param pColor The color to set.
   */
  public void setColor ( Color pColor )
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
  public void setDescription ( String pDescription )
  {
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
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
