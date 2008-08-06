package de.unisiegen.gtitool.ui.swing;


import javax.swing.Icon;
import javax.swing.JLabel;


/**
 * Special {@link JLabel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTILabel extends JLabel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7796262176480313891L;


  /**
   * Allocates a new {@link JGTILabel}.
   */
  public JGTILabel ()
  {
    super ();
  }


  /**
   * Allocates a new {@link JGTILabel}.
   * 
   * @param image The {@link Icon}.
   */
  public JGTILabel ( Icon image )
  {
    super ( image );
  }


  /**
   * Allocates a new {@link JGTILabel}.
   * 
   * @param image The {@link Icon}.
   * @param horizontalAlignment The horizontal alignment.
   */
  public JGTILabel ( Icon image, int horizontalAlignment )
  {
    super ( image, horizontalAlignment );
  }


  /**
   * Allocates a new {@link JGTILabel}.
   * 
   * @param text The text.
   */
  public JGTILabel ( String text )
  {
    super ( text );
  }


  /**
   * Allocates a new {@link JGTILabel}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   * @param horizontalAlignment The horizontal alignment.
   */
  public JGTILabel ( String text, Icon icon, int horizontalAlignment )
  {
    super ( text, icon, horizontalAlignment );
  }


  /**
   * Allocates a new {@link JGTILabel}.
   * 
   * @param text The text.
   * @param horizontalAlignment The horizontal alignment.
   */
  public JGTILabel ( String text, int horizontalAlignment )
  {
    super ( text, horizontalAlignment );
  }
}
