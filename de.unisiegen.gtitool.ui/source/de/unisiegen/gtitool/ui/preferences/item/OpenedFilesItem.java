package de.unisiegen.gtitool.ui.preferences.item;


import java.io.File;
import java.util.ArrayList;

import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane.ActiveEditor;


/**
 * The class which handles the opened files and the index of the last active
 * file.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class OpenedFilesItem
{

  /**
   * The last active {@link File}.
   */
  private File activeFile;


  /**
   * The recently used files.
   */
  private ArrayList < ObjectPair < File, ActiveEditor > > files;


  /**
   * Allocates a new {@link OpenedFilesItem}.
   * 
   * @param files The opened files.
   * @param activeFile The last active {@link File}.
   */
  public OpenedFilesItem (
      ArrayList < ObjectPair < File, ActiveEditor > > files, File activeFile )
  {
    // files
    if ( files == null )
    {
      throw new NullPointerException ( "files is null" ); //$NON-NLS-1$
    }
    this.files = files;

    // active file
    this.activeFile = activeFile;
  }


  /**
   * Returns the opened used files.
   * 
   * @return The opened used files.
   * @see #files
   */
  public final ArrayList < ObjectPair < File, ActiveEditor > > getFiles ()
  {
    return this.files;
  }


  /**
   * Returns the last active {@link File}.
   * 
   * @return The last active {@link File}.
   * @see #activeFile
   */
  public final File getActiveFile ()
  {
    return this.activeFile;
  }
}
