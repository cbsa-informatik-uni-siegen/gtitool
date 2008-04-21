package de.unisiegen.gtitool.ui.swing;


import javax.swing.JFileChooser;

import de.unisiegen.gtitool.ui.logic.OpenDialog;
import de.unisiegen.gtitool.ui.logic.SaveDialog;


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
   * The {@link OpenDialog}.
   */
  private OpenDialog openDialog;


  /**
   * The {@link SaveDialog}.
   */
  private SaveDialog saveDialog;


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


  /**
   * {@inheritDoc}
   * 
   * @see JFileChooser#approveSelection()
   */
  @Override
  public final void approveSelection ()
  {
    if ( this.openDialog != null )
    {
      this.openDialog.approve ();
    }
    if ( this.saveDialog != null )
    {
      this.saveDialog.approve ();
    }
    super.approveSelection ();
  }


  /**
   * Setter for the {@link OpenDialog}.
   * 
   * @param openDialog The {@link OpenDialog}.
   */
  public final void setOpenDialog ( OpenDialog openDialog )
  {
    this.openDialog = openDialog;
  }


  /**
   * Setter for the {@link SaveDialog}.
   * 
   * @param saveDialog The {@link SaveDialog}.
   */
  public final void setSaveDialog ( SaveDialog saveDialog )
  {
    this.saveDialog = saveDialog;
  }
}
