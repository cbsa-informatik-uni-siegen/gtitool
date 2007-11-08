package de.unisiegen.gtitool.ui.preferences;


/**
 * Indicates which zoom factor is choosen.
 * 
 * @author Christian Fehler
 */
public enum ZoomFactor
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
   * Creates a new {@link ZoomFactor} of the given zoom factor value, or throws
   * an exception if the zoom factor is not supported.
   * 
   * @param pZoomFactor The zoom factor value.
   * @return A new {@link ZoomFactor}.
   */
  public static ZoomFactor createFactor ( int pZoomFactor )
  {
    switch ( pZoomFactor )
    {
      case 50 :
        return ZOOM_50;
      case 75 :
        return ZOOM_75;
      case 100 :
        return ZOOM_100;
      case 125 :
        return ZOOM_125;
      case 150 :
        return ZOOM_150;
      case 175 :
        return ZOOM_175;
      case 200 :
        return ZOOM_200;
      default :
        throw new IllegalArgumentException ( "zoom factor not supported" ); //$NON-NLS-1$
    }
  }


  /**
   * The zoom factor.
   */
  private int factor;


  /**
   * Creates a new <code>ZoomFactor</code>.
   * 
   * @param pFactor The zoom factor.
   */
  private ZoomFactor ( int pFactor )
  {
    this.factor = pFactor;
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
