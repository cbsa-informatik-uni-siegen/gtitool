package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Graphics;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * TODO
 */
public class SimpleLRStateView extends StateView
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 273534362459832829L;


  /**
   * TODO
   */
  public static class JGraphEllipseRenderer extends
      StateView.JGraphEllipseRenderer
  {

    /**
     * TODO
     */
    private static final long serialVersionUID = 395388242313364260L;


    /**
     * {@inheritDoc}
     * 
     * @see de.unisiegen.gtitool.ui.jgraph.StateView.JGraphEllipseRenderer#getStateString(de.unisiegen.gtitool.core.entities.State)
     */
    @Override
    protected PrettyString getStateString ( final State state )
    {
      return ( ( LRState ) state ).shortName ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see de.unisiegen.gtitool.ui.jgraph.StateView.JGraphEllipseRenderer#showFinalStates()
     */
    @Override
    protected boolean showFinalStates ()
    {
      return false;
    }


    /**
     * {@inheritDoc}
     * 
     * @see de.unisiegen.gtitool.ui.jgraph.StateView.JGraphEllipseRenderer#paint(java.awt.Graphics)
     */
    @Override
    public void paint ( Graphics g )
    {
      getDefaultStateView ().getState ().setShortNameUsed ( true );
      super.paint ( g );
    }


    /**
     * TODO
     * 
     * @param lrstateview
     */
    public JGraphEllipseRenderer ( final SimpleLRStateView lrstateview )
    {
      super ( lrstateview );

    }

  }


  /**
   * Create a new {@link StateView}.
   * 
   * @param cell The cell {@link Object} for this view.
   */
  public SimpleLRStateView ( final Object cell )
  {
    super ( cell );
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }
}
