package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Indicates {@link Word} mode is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum WordModeItem
{
  /**
   * The {@link Word} is displayed left aligned.
   */
  LEFT ( 0, "PreferencesDialog.WordModeLeft" ), //$NON-NLS-1$

  /**
   * The {@link Word} is displayed right aligned.
   */
  RIGHT ( 1, "PreferencesDialog.WordModeRight" ); //$NON-NLS-1$

  /**
   * The {@link Logger} for this enum.
   */
  private static final Logger logger = Logger.getLogger ( WordModeItem.class );


  /**
   * Creates a new {@link WordModeItem} of the given index, or throws an
   * exception if the index is not supported.
   * 
   * @param index The index.
   * @return A new {@link WordModeItem}.
   */
  public final static WordModeItem create ( int index )
  {
    switch ( index )
    {
      case 0 :
      {
        return LEFT;
      }
      case 1 :
      {
        return RIGHT;
      }
      default :
      {
        logger.error ( "create", "word mode index not supported" ); //$NON-NLS-1$ //$NON-NLS-2$
        return PreferenceManager.DEFAULT_WORD_MODE_ITEM;
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
   * Allocates a new {@link WordModeItem}.
   * 
   * @param index The index.
   * @param title The title.
   */
  private WordModeItem ( int index, String title )
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
