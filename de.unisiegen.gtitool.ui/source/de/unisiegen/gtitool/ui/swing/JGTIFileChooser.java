package de.unisiegen.gtitool.ui.swing;


import javax.swing.JFileChooser;


/**
 * Special {@link JFileChooser}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIFileChooser extends JFileChooser
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6169927725866768170L;


  /**
   * Allocates a new {@link JGTIFileChooser}.
   */
  public JGTIFileChooser ()
  {
    super ();
    setAcceptAllFileFilterUsed ( false );
    setControlButtonsAreShown ( false );
    setBorder ( null );
    setMultiSelectionEnabled ( true );
  }
}
