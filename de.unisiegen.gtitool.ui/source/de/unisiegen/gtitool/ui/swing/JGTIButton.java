package de.unisiegen.gtitool.ui.swing;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;


/**
 * Special {@link JButton}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIButton extends JButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1746095496850915318L;


  /**
   * The default minimal width of this {@link JGTIButton}.
   */
  private static final int DEFAULT_MIN_WIDTH = 100;


  /**
   * The default maximum width of this {@link JGTIButton}.
   */
  private static final int DEFAULT_MAX_WIDTH = 1000;


  /**
   * The default minimal height of this {@link JGTIButton}.
   */
  private static final int DEFAULT_MIN_HEIGHT = 0;


  /**
   * The default maximum height of this {@link JGTIButton}.
   */
  private static final int DEFAULT_MAX_HEIGHT = 1000;


  /**
   * The minimal width of this {@link JGTIButton}.
   */
  private int minWidth;


  /**
   * The maximum width of this {@link JGTIButton}.
   */
  private int maxWidth;


  /**
   * The minimal height of this {@link JGTIButton}.
   */
  private int minHeight;


  /**
   * The maximum height of this {@link JGTIButton}.
   */
  private int maxHeight;


  /**
   * Allocates a new {@link JGTIButton}.
   */
  public JGTIButton ()
  {
    super ();
    setFocusPainted ( false );
    this.minWidth = DEFAULT_MIN_WIDTH;
    this.maxWidth = DEFAULT_MAX_WIDTH;
    this.minHeight = DEFAULT_MIN_HEIGHT;
    this.maxHeight = DEFAULT_MAX_HEIGHT;
  }


  /**
   * Returns the maximum height.
   * 
   * @return The maximum height.
   */
  public final int getMaximumHeight ()
  {
    return this.maxHeight;
  }


  /**
   * Returns the maximum width.
   * 
   * @return The maximum width.
   */
  public final int getMaximumWidth ()
  {
    return this.maxWidth;
  }


  /**
   * Returns the minimum height.
   * 
   * @return The minimum height.
   */
  public final int getMinimumHeight ()
  {
    return this.minHeight;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getMinimumSize()
   */
  @Override
  public final Dimension getMinimumSize ()
  {
    Dimension size = super.getMinimumSize ();
    if ( size.width < this.minWidth )
    {
      size.width = this.minWidth;
    }
    if ( size.width > this.maxWidth )
    {
      size.width = this.maxWidth;
    }
    if ( size.height < this.minHeight )
    {
      size.height = this.minHeight;
    }
    if ( size.height > this.maxHeight )
    {
      size.height = this.maxHeight;
    }
    return size;
  }


  /**
   * Returns the minimum width.
   * 
   * @return The minimum width.
   */
  public final int getMinimumWidth ()
  {
    return this.minWidth;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getPreferredSize()
   */
  @Override
  public final Dimension getPreferredSize ()
  {
    Dimension size = super.getPreferredSize ();
    if ( size.width < this.minWidth )
    {
      size.width = this.minWidth;
    }
    if ( size.width > this.maxWidth )
    {
      size.width = this.maxWidth;
    }
    if ( size.height < this.minHeight )
    {
      size.height = this.minHeight;
    }
    if ( size.height > this.maxHeight )
    {
      size.height = this.maxHeight;
    }
    return size;
  }


  /**
   * Sets the maximum height.
   * 
   * @param maxHeight The maximum height to set.
   */
  public final void setMaximumHeight ( int maxHeight )
  {
    this.maxHeight = maxHeight;
  }


  /**
   * Sets the maximum width.
   * 
   * @param maxWidth The maximum width to set.
   */
  public final void setMaximumWidth ( int maxWidth )
  {
    this.maxWidth = maxWidth;
  }


  /**
   * Sets the minimum height.
   * 
   * @param minHeight The minimum height to set.
   */
  public final void setMinimumHeight ( int minHeight )
  {
    this.minHeight = minHeight;
  }


  /**
   * Sets the minimum width.
   * 
   * @param minWidth The minimum width to set.
   */
  public final void setMinimumWidth ( int minWidth )
  {
    this.minWidth = minWidth;
  }
}
