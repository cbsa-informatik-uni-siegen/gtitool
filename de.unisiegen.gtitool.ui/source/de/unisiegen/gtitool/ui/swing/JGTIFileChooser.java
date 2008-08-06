package de.unisiegen.gtitool.ui.swing;


import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

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
    init ();
  }


  /**
   * Allocates a new {@link JGTIFileChooser}.
   * 
   * @param currentDirectory The current directory.
   */
  public JGTIFileChooser ( File currentDirectory )
  {
    super ( currentDirectory );
    init ();
  }


  /**
   * Allocates a new {@link JGTIFileChooser}.
   * 
   * @param currentDirectory The current directory.
   * @param fsv The {@link FileSystemView}.
   */
  public JGTIFileChooser ( File currentDirectory, FileSystemView fsv )
  {
    super ( currentDirectory, fsv );
    init ();
  }


  /**
   * Allocates a new {@link JGTIFileChooser}.
   * 
   * @param fsv The {@link FileSystemView}.
   */
  public JGTIFileChooser ( FileSystemView fsv )
  {
    super ( fsv );
    init ();
  }


  /**
   * Allocates a new {@link JGTIFileChooser}.
   * 
   * @param currentDirectoryPath The current directory path.
   */
  public JGTIFileChooser ( String currentDirectoryPath )
  {
    super ( currentDirectoryPath );
    init ();
  }


  /**
   * Allocates a new {@link JGTIFileChooser}.
   * 
   * @param currentDirectoryPath The current directory path.
   * @param fsv The {@link FileSystemView}.
   */
  public JGTIFileChooser ( String currentDirectoryPath, FileSystemView fsv )
  {
    super ( currentDirectoryPath, fsv );
    init ();
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
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setAcceptAllFileFilterUsed ( false );
    setControlButtonsAreShown ( false );
    setBorder ( null );
    setMultiSelectionEnabled ( true );
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
