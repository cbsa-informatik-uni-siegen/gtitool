package de.unisiegen.gtitool.core.preferences.item;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * The color item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ColorItem extends DefaultMutableTreeNode implements
    Comparable < ColorItem >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2610306920052697585L;


  /**
   * The {@link Color} of this item.
   */
  private Color color = null;


  /**
   * The caption of this item.
   */
  private String caption = null;


  /**
   * The description of this item.
   */
  private String description = null;


  /**
   * The standard {@link Color} of this item.
   */
  private Color standardColor = null;


  /**
   * The expanded value.
   */
  private boolean expanded = false;


  /**
   * Allocates a new {@link ColorItem}.
   * 
   * @param color The {@link Color} of this item.
   * @param caption The caption of this item.
   * @param description The description of this item.
   * @param standardColor The standard {@link Color} of this item.
   */
  public ColorItem ( Color color, String caption, String description,
      Color standardColor )
  {
    super ( caption );
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
   * Allocates a new {@link ColorItem}.
   * 
   * @param caption The caption of this item.
   * @param description The description of this item.
   * @param expanded The expanded value.
   */
  public ColorItem ( String caption, String description, boolean expanded )
  {
    super ( caption );
    // Caption
    setCaption ( caption );
    // Description
    setDescription ( description );
    // Expanded
    setExpanded ( expanded );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final ColorItem clone ()
  {
    if ( this.color != null )
    {
      return new ColorItem ( this.color, this.caption, this.description,
          this.standardColor );
    }
    return new ColorItem ( this.caption, this.description, this.expanded );
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
   * Returns true if this {@link ColorItem} is expanded.
   * 
   * @return True if this {@link ColorItem} is expanded.
   * @see #expanded
   */
  public final boolean isExpanded ()
  {
    return this.expanded;
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
    this.color = color;
  }


  /**
   * Sets the description.
   * 
   * @param description The description to set.
   */
  public final void setDescription ( String description )
  {
    this.description = description;
  }


  /**
   * Sets the expanded value.
   * 
   * @param expanded The expanded value to set.
   * @see #expanded
   */
  public final void setExpanded ( boolean expanded )
  {
    this.expanded = expanded;
  }


  /**
   * Sets the standard color.
   * 
   * @param standardColor The standard color to set.
   */
  public final void setStandardColor ( Color standardColor )
  {
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
