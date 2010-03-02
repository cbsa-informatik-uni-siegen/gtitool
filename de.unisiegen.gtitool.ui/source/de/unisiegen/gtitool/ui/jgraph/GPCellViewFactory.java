package de.unisiegen.gtitool.ui.jgraph;


import java.util.Map;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.LRState;


/**
 * A default view factory for a JGraph. This simple factory associate a given
 * cell class to a cell view. This is a javabean, just parameter it correctly in
 * order it meets your requirements (else subclass it or subclass
 * DefaultCellViewFactory). You can also recover the gpConfiguration of that
 * javabean via an XML file via XMLEncoder/XMLDecoder.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class GPCellViewFactory extends DefaultCellViewFactory
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2222911017108871188L;


  /**
   * The view class key.
   */
  public static final String VIEW_CLASS_KEY = "viewClassKey"; //$NON-NLS-1$


  /**
   * Sets the view class.
   * 
   * @param map The map.
   * @param viewClass The view class.
   */
  public static final void setViewClass ( Map < String, String > map,
      String viewClass )
  {
    map.put ( VIEW_CLASS_KEY, viewClass );
  }


  /**
   * {@inheritDoc}
   * 
   * @see DefaultCellViewFactory#createVertexView(Object)
   */
  @Override
  protected VertexView createVertexView ( Object value )
  {
    try
    {
      // TODO: where do we put this?
      if ( value instanceof DefaultStateView
          && ( ( DefaultStateView ) value ).getState () instanceof LRState )
      {
        return new LRStateView ( value );
      }

      DefaultGraphCell cell = ( DefaultGraphCell ) value;
      String viewClass = ( String ) cell.getAttributes ().get ( VIEW_CLASS_KEY );

      VertexView view = ( VertexView ) Thread.currentThread ()
          .getContextClassLoader ().loadClass ( viewClass ).newInstance ();
      view.setCell ( value );
      return view;
    }
    catch ( Exception e )
    {
      // Nothing to do here
    }
    return super.createVertexView ( value );
  }


  public static VertexView staticCreateVertexView ( final Object value )
  {
    return ( new GPCellViewFactory () ).createVertexView ( value );
  }
}
