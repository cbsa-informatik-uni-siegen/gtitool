package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;


/**
 * Special {@link JColorChooser}.
 * 
 * @author Christian Fehler
 */
public final class JGTIColorChooser extends JColorChooser
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3745141498172501423L;


  /**
   * Allocates a new {@link JGTIColorChooser}.
   */
  public JGTIColorChooser ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTIColorChooser}.
   * 
   * @param initialColor The initial {@link Color}.
   */
  public JGTIColorChooser ( Color initialColor )
  {
    super ( initialColor );
    init ();
  }


  /**
   * Allocates a new {@link JGTIColorChooser}.
   * 
   * @param model The {@link ColorSelectionModel}.
   */
  public JGTIColorChooser ( ColorSelectionModel model )
  {
    super ( model );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setPreviewPanel ( new JPanel () );
  }
}
