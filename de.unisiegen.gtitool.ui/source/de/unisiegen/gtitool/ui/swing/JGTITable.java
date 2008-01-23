package de.unisiegen.gtitool.ui.swing;


import java.awt.Container;
import java.awt.Dimension;

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
   * Allocates a new {@link JGTITable}.
   */
  public JGTITable ()
  {
    super ();
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
}
