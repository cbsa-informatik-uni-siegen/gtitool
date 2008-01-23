package de.unisiegen.gtitool.ui.swing;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;


/**
 * Special {@link JTable}, that supports drag and drop.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITable extends JTable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6702446627778010971L;


  /**
   * The into drop mode.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  public static final int DROP_INTO = 0;


  /**
   * The between drop mode.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  public static final int DROP_BETWEEN = 1;


  /**
   * The drop mode used for this {@link JGTITable}.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  private int dropMode = DROP_INTO;


  /**
   * Allocates a new {@link JGTITable}.
   */
  public JGTITable ()
  {
    super ();
  }


  /**
   * Returns the drop mode of this {@link JGTITable}.
   * 
   * @return The drop mode of this {@link JGTITable}.
   * @see #setDropMode(int)
   */
  public final int getDropMode ()
  {
    return this.dropMode;
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
    Container parent = getParent ();
    if ( parent instanceof JViewport )
    {
      parent = parent.getParent ();
      if ( parent instanceof JScrollPane )
      {
        JScrollPane scrollPane = ( JScrollPane ) parent;
        int height = scrollPane.getViewportBorderBounds ().height;
        if ( size.height < height )
        {
          size.height = height;
        }
      }
    }
    return size;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );
  }


  /**
   * Sets the drop modeof this {@link JGTITable}.
   * 
   * @param dropMode The new drop mode.
   */
  public final void setDropMode ( int dropMode )
  {
    if ( ( dropMode != DROP_INTO ) && ( dropMode != DROP_BETWEEN ) )
    {
      throw new IllegalArgumentException ( "drop mode is invalid" ); //$NON-NLS-1$
    }

    this.dropMode = dropMode;
  }
}
