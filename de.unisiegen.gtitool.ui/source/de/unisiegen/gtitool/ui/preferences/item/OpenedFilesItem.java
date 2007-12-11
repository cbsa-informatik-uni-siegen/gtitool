package de.unisiegen.gtitool.ui.preferences.item;


import java.io.File;
import java.util.ArrayList;


/**
 * The class which handles the opened files and the index of the last
 * active file.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class OpenedFilesItem extends RecentlyUsedFilesItem
{
  /**
   * The index of the last active file.
   */
  private int activeIndex;


  /**
   * Allocates a new <code>RecentlyUsedItem</code>.
   * 
   * @param pFiles The opened files.
   * @param pActiveIndex The index of the last active file.
   */
  public OpenedFilesItem ( ArrayList < File > pFiles, int pActiveIndex )
  {
    super(pFiles);
    // ActiveIndex
    this.activeIndex = pActiveIndex;
  }


  /**
   * Returns the activeIndex.
   * 
   * @return The activeIndex.
   * @see #activeIndex
   */
  public final int getActiveIndex ()
  {
    return this.activeIndex;
  }
}
