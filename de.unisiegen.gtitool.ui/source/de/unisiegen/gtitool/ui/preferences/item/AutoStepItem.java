package de.unisiegen.gtitool.ui.preferences.item;


/**
 * Indicates which auto step interval is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum AutoStepItem
{
  /**
   * The auto step interval 1 is choosen.
   */
  AUTO_STEP_1 ( 1 ),

  /**
   * The auto step interval 2 is choosen.
   */
  AUTO_STEP_2 ( 2 ),

  /**
   * The auto step interval 3 is choosen.
   */
  AUTO_STEP_3 ( 3 ),

  /**
   * The auto step interval 4 is choosen.
   */
  AUTO_STEP_4 ( 4 ),

  /**
   * The auto step interval 5 is choosen.
   */
  AUTO_STEP_5 ( 5 );

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
      case 1 :
      {
        return AUTO_STEP_1;
      }
      case 2 :
      {
        return AUTO_STEP_2;
      }
      case 3 :
      {
        return AUTO_STEP_3;
      }
      case 4 :
      {
        return AUTO_STEP_4;
      }
      case 5 :
      {
        return AUTO_STEP_5;
      }
      default :
      {
        throw new IllegalArgumentException (
            "auto step interval is not supported" ); //$NON-NLS-1$
      }
    }
  }


  /**
   * The auto step interval.
   */
  private int autoStepInterval;


  /**
   * Creates a new <code>AutoStepItem</code>.
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
