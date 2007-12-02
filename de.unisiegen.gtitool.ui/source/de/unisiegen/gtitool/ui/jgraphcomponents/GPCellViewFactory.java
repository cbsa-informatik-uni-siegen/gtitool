package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.util.Map;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexView;

/**
 * A default view factory for a JGraph. This simple factory associate a given
 * cell class to a cell view. This is a javabean, just parameter it correctly in
 * order it meets your requirements (else subclass it or subclass
 * DefaultCellViewFactory). You can also recover the gpConfiguration of that
 * javabean via an XML file via XMLEncoder/XMLDecoder.
 * 
 */
public class GPCellViewFactory extends DefaultCellViewFactory {
	
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2222911017108871188L;
  
  public static final String VIEW_CLASS_KEY = "viewClassKey"; //$NON-NLS-1$

  /**
	 * TODO
	 *
	 * @param map
	 * @param viewClass
	 */
	public static final void setViewClass(Map  map, String viewClass) {
		map.put(VIEW_CLASS_KEY, viewClass);
	}

  /**
   * {@inheritDoc}
   *
   * @see org.jgraph.graph.DefaultCellViewFactory#createVertexView(java.lang.Object)
   */
  @Override
	protected VertexView createVertexView(Object v) {
		try {
			DefaultGraphCell cell = (DefaultGraphCell) v;
			String viewClass = (String) cell.getAttributes().get(VIEW_CLASS_KEY);

			VertexView view = (VertexView) Thread.currentThread()
					.getContextClassLoader().loadClass(viewClass).newInstance();
			view.setCell(v);
			return view;
		} catch (Exception e) {
      // Nothing to do here
		}
		return super.createVertexView(v);
	}
}
