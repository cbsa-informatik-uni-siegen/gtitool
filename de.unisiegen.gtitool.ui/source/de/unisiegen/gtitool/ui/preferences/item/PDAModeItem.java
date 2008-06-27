package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Indicates pda mode is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum PDAModeItem
{
  /**
   * Show all components.
   */
  SHOW ( 0, "PreferencesDialog.PDAModeShow" ), //$NON-NLS-1$

  /**
   * Show only if a PDA is choosen.
   */
  HIDE ( 1, "PreferencesDialog.PDAModeHide" ); //$NON-NLS-1$

  /**
   * The {@link Logger} for this enum.
   */
  private static final Logger logger = Logger.getLogger ( PDAModeItem.class );


  /**
   * Creates a new {@link PDAModeItem} of the given index, or throws an
   * exception if the index is not supported.
   * 
   * @param index The index.
   * @return A new {@link PDAModeItem}.
   */
  public final static PDAModeItem create ( int index )
  {
    switch ( index )
    {
      case 0 :
      {
        return SHOW;
      }
      case 1 :
      {
        return HIDE;
      }
      default :
      {
        logger.error ( "create", "pda mode index not supported" ); //$NON-NLS-1$ //$NON-NLS-2$
        return PreferenceManager.DEFAULT_PDA_MODE_ITEM;
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
   * Allocates a new {@link PDAModeItem}.
   * 
   * @param index The index.
   * @param title The title.
   */
  private PDAModeItem ( int index, String title )
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
