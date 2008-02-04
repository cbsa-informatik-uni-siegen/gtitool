package de.unisiegen.gtitool.ui.preferences.item;


import org.apache.log4j.Logger;

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
   * The zoom factor for 75 percent.
   */
  ZOOM_75 ( 75 ),

  /**
   * The zoom factor for 100 percent.
   */
  ZOOM_100 ( 100 ),

  /**
   * The zoom factor for 125 percent.
   */
  ZOOM_125 ( 125 ),

  /**
   * The zoom factor for 150 percent.
   */
  ZOOM_150 ( 150 ),

  /**
   * The zoom factor for 175 percent.
   */
  ZOOM_175 ( 175 ),

  /**
   * The zoom factor for 200 percent.
   */
  ZOOM_200 ( 200 );

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
      case 75 :
      {
        return ZOOM_75;
      }
      case 100 :
      {
        return ZOOM_100;
      }
      case 125 :
      {
        return ZOOM_125;
      }
      case 150 :
      {
        return ZOOM_150;
      }
      case 175 :
      {
        return ZOOM_175;
      }
      case 200 :
      {
        return ZOOM_200;
      }
      default :
      {
        logger.error ( "zoom factor not supported" ); //$NON-NLS-1$
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
