package de.unisiegen.gtitool.ui.swing;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;


/**
 * Special {@link JTable}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITable extends JTable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6702446627778010971L;


  /**
   * The into drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_INTO = 0;


  /**
   * The between drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_BETWEEN = 1;


  /**
   * The drop mode used for this {@link JGTITable}.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  private int dndMode = DROP_INTO;


  /**
   * Allocates a new {@link JGTITable}.
   */
  public JGTITable ()
  {
    super ();
  }


  /**
   * Returns the drag and drop mode of this {@link JGTIList}.
   * 
   * @return The drag and drop mode of this {@link JGTIList}.
   * @see #setDndMode(int)
   */
  public final int getDndMode ()
  {
    return this.dndMode;
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
   * Sets the drag and drop mode of this {@link JGTITable}.
   * 
   * @param dndMode The new drag and drop mode.
   */
  public final void setDndMode ( int dndMode )
  {
    if ( ( dndMode != DROP_INTO ) && ( dndMode != DROP_BETWEEN ) )
    {
      throw new IllegalArgumentException ( "dnd mode is invalid" ); //$NON-NLS-1$
    }
    this.dndMode = dndMode;
  }
}
