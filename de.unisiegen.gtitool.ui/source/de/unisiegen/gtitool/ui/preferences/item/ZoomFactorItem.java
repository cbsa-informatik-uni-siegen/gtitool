package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Indicates which zoom factor is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum ZoomFactorItem
{
  /**
   * The zoom factor for 50 percent.
   */
  ZOOM_50 ( 50 ),

  /**
   * The zoom factor for 100 percent.
   */
  ZOOM_100 ( 100 ),

  /**
   * The zoom factor for 150 percent.
   */
  ZOOM_150 ( 150 );

  /**
   * The {@link Logger} for this enum.
   */
  private static final Logger logger = Logger.getLogger ( ZoomFactorItem.class );


  /**
   * Creates a new {@link ZoomFactorItem} of the given zoom factor value, or
   * throws an exception if the zoom factor is not supported.
   * 
   * @param zoomFactor The zoom factor value.
   * @return A new {@link ZoomFactorItem}.
   */
  public static ZoomFactorItem create ( int zoomFactor )
  {
    switch ( zoomFactor )
    {
      case 50 :
      {
        return ZOOM_50;
      }
      case 100 :
      {
        return ZOOM_100;
      }
      case 150 :
      {
        return ZOOM_150;
      }
      default :
      {
        logger.error ( "create", "zoom factor not supported" ); //$NON-NLS-1$ //$NON-NLS-2$
        return PreferenceManager.DEFAULT_ZOOM_FACTOR_ITEM;
      }
    }
  }


  /**
   * The zoom factor.
   */
  private int factor;


  /**
   * Creates a new {@link ZoomFactorItem}.
   * 
   * @param factor The zoom factor.
   */
  private ZoomFactorItem ( int factor )
  {
    this.factor = factor;
  }


  /**
   * Returns the zoom factor.
   * 
   * @return The zoom factor.
   */
  public int getFactor ()
  {
    return this.factor;
  }
}
