package de.unisiegen.gtitool.ui.preferences.item;


import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * Indicates which auto step interval is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum AutoStepItem
{
  /**
   * The auto step interval 0 is choosen.
   */
  AUTO_STEP_0 ( 0 ),

  /**
   * The auto step interval 500 is choosen.
   */
  AUTO_STEP_500 ( 500 ),

  /**
   * The auto step interval 1000 is choosen.
   */
  AUTO_STEP_1000 ( 1000 ),

  /**
   * The auto step interval 1500 is choosen.
   */
  AUTO_STEP_1500 ( 1500 ),

  /**
   * The auto step interval 2000 is choosen.
   */
  AUTO_STEP_2000 ( 2000 ),

  /**
   * The auto step interval 2500 is choosen.
   */
  AUTO_STEP_2500 ( 2500 ),

  /**
   * The auto step interval 3000 is choosen.
   */
  AUTO_STEP_3000 ( 3000 ),

  /**
   * The auto step interval 3500 is choosen.
   */
  AUTO_STEP_3500 ( 3500 ),

  /**
   * The auto step interval 4000 is choosen.
   */
  AUTO_STEP_4000 ( 4000 ),

  /**
   * The auto step interval 4500 is choosen.
   */
  AUTO_STEP_4500 ( 4500 ),

  /**
   * The auto step interval 5000 is choosen.
   */
  AUTO_STEP_5000 ( 5000 );

  /**
   * The {@link Logger} for this enum.
   */
  private static final Logger logger = Logger.getLogger ( AutoStepItem.class );


  /**
   * Creates a new {@link AutoStepItem} of the given auto step interval value,
   * or throws an exception if the auto step interval is not supported.
   * 
   * @param autoStepInterval The auto step interval value.
   * @return A new {@link AutoStepItem}.
   */
  public static AutoStepItem create ( int autoStepInterval )
  {
    switch ( autoStepInterval )
    {
      case 0 :
      {
        return AUTO_STEP_0;
      }
      case 500 :
      {
        return AUTO_STEP_500;
      }
      case 1000 :
      {
        return AUTO_STEP_1000;
      }
      case 1500 :
      {
        return AUTO_STEP_1500;
      }
      case 2000 :
      {
        return AUTO_STEP_2000;
      }
      case 2500 :
      {
        return AUTO_STEP_2500;
      }
      case 3000 :
      {
        return AUTO_STEP_3000;
      }
      case 3500 :
      {
        return AUTO_STEP_3500;
      }
      case 4000 :
      {
        return AUTO_STEP_4000;
      }
      case 4500 :
      {
        return AUTO_STEP_4500;
      }
      case 5000 :
      {
        return AUTO_STEP_5000;
      }
      default :
      {
        logger.error ( "auto step interval is not supported" ); //$NON-NLS-1$
        return PreferenceManager.DEFAULT_AUTO_STEP_INTERVAL_ITEM;
      }
    }
  }


  /**
   * The auto step interval.
   */
  private int autoStepInterval;


  /**
   * Creates a new {@link AutoStepItem}.
   * 
   * @param autoStepInterval The auto step interval.
   */
  private AutoStepItem ( int autoStepInterval )
  {
    this.autoStepInterval = autoStepInterval;
  }


  /**
   * Returns the auto step interval.
   * 
   * @return The auto step interval.
   */
  public int getAutoStepInterval ()
  {
    return this.autoStepInterval;
  }
}
