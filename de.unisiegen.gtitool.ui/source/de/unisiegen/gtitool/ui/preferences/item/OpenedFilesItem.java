package de.unisiegen.gtitool.ui.preferences.item;


import java.io.File;
import java.util.ArrayList;


/**
 * The class which handles the opened files and the index of the last active
 * file.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class OpenedFilesItem extends RecentlyUsedFilesItem
{

  /**
   * The last active {@link File}.
   */
  private File activeFile;


  /**
   * Allocates a new {@link OpenedFilesItem}.
   * 
   * @param files The opened files.
   * @param activeFile The last active {@link File}.
   */
  public OpenedFilesItem ( ArrayList < File > files, File activeFile )
  {
    super ( files );
    // ActiveFile
    this.activeFile = activeFile;
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
