package de.unisiegen.gtitool.ui.preferences.item;


import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Indicates which mouse selection is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum MouseSelectionItem
{
  /**
   * The without return to the mouse value.
   */
  WITHOUT_RETURN_TO_MOUSE ( 0, "PreferencesDialog.MouseSelectionWithoutReturn" ), //$NON-NLS-1$

  /**
   * The with return to the mouse value.
   */
  WITH_RETURN_TO_MOUSE ( 1, "PreferencesDialog.MouseSelectionWithReturn" ); //$NON-NLS-1$

  /**
   * The {@link Logger} for this enum.
   */
  private static final Logger logger = Logger
      .getLogger ( MouseSelectionItem.class );


  /**
   * Creates a new {@link MouseSelectionItem} of the given index, or throws an
   * exception if the index is not supported.
   * 
   * @param index The index.
   * @return A new {@link MouseSelectionItem}.
   */
  public final static MouseSelectionItem create ( int index )
  {
    switch ( index )
    {
      case 0 :
      {
        return WITHOUT_RETURN_TO_MOUSE;
      }
      case 1 :
      {
        return WITH_RETURN_TO_MOUSE;
      }
      default :
      {
        logger.error ( "mouse selection index not supported" ); //$NON-NLS-1$
        return PreferenceManager.DEFAULT_MOUSE_SELECTION_ITEM;
      }
    }
  }


  /**
   * The index.
   */
  private int index;


  /**
   * The title.
   */
  private String title;


  /**
   * Allocates a new {@link MouseSelectionItem}.
   * 
   * @param index The index.
   * @param title The title.
   */
  private MouseSelectionItem ( int index, String title )
  {
    this.index = index;
    this.title = title;
  }


  /**
   * Returns the index.
   * 
   * @return The index.
   */
  public final int getIndex ()
  {
    return this.index;
  }


  /**
   * Returns the title.
   * 
   * @return The title.
   */
  public final String getTitle ()
  {
    return Messages.getString ( this.title );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Enum#toString()
   */
  @Override
  public final String toString ()
  {
    return getTitle ();
  }
}
