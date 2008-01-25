package de.unisiegen.gtitool.ui.swing;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;


/**
 * Special {@link JButton}.
 * 
 * @author Christian Fehler
 * @version $Id: JGTITable.java 465 2008-01-23 00:52:59Z fehler $
 */
public class JGTIButton extends JButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1746095496850915318L;


  /**
   * The minimal width of this {@link JGTIButton}.
   */
  private static int MIN_WIDTH = 80;


  /**
   * Allocates a new {@link JGTIButton}.
   */
  public JGTIButton ()
  {
    super ();
    setFocusPainted ( false );
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
    if ( size.width < MIN_WIDTH )
    {
      size.width = MIN_WIDTH;
    }
    return size;
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
    if ( size.width < MIN_WIDTH )
    {
      size.width = MIN_WIDTH;
    }
    return size;
  }
}
