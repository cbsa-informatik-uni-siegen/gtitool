package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.jgraph.DefaultNodeView;
import de.unisiegen.gtitool.ui.jgraph.DefaultRegexEdgeView;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.EdgeRenderer;
import de.unisiegen.gtitool.ui.jgraph.GPCellViewFactory;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.JGraphpadParallelSplineRouter;
import de.unisiegen.gtitool.ui.jgraph.NodeView;


/**
 * TODO
 */
public class DefaultRegexModel implements DefaultModel, Storable, Modifyable
{


  /**
   * The default y-space in the graph
   */
  private final int Y_SPACE = 20;


  /**
   * The default x-space in the graph
   */
  private final int X_SPACE = 50;


  /**
   * The default x-border of the graph
   */
  private final int X_BORDER = 20;
  
  private DefaultRegex regex;


  private JGTIGraph jGTIGraph;


  /**
   * A list of all {@link DefaultStateView}s
   */
  private ArrayList < DefaultNodeView > nodeViewList = new ArrayList < DefaultNodeView > ();

  private ArrayList <DefaultRegexEdgeView> regexEdgeViewList = new ArrayList < DefaultRegexEdgeView >();

  /**
   * The {@link DefaultGraphModel} for this model.
   */
  private DefaultGraphModel graphModel;


  /**
   * The {@link Regex} version.
   */
  private static final int REGEX_VERSION = 1;


  /**
   * TODO
   */
  public DefaultRegexModel ( DefaultRegex regex )
  {
    this.regex = regex;
  }


  /**
   * Returns the regex.
   * 
   * @return The regex.
   * @see #regex
   */
  public DefaultRegex getRegex ()
  {
    return this.regex;
  }
  
  
  /**
   * Sets the regex.
   *
   * @param regex The regex to set.
   * @see #regex
   */
  public void setRegex ( DefaultRegex regex )
  {
    this.regex = regex;
  }


  /**
   * TODO
   * 
   * @throws StoreException
   */
  public DefaultRegexModel ( Element element ) throws StoreException
  {
    boolean foundVersion = false;
    for ( Attribute attr : element.getAttribute () )
    {
      if ( attr.getName ().equals ( "regexVersion" ) ) //$NON-NLS-1$
      {
        foundVersion = true;
        if ( attr.getValueInt () != REGEX_VERSION )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.IncompatibleVersion" ) ); //$NON-NLS-1$
        }
      }
    }
    if ( !foundVersion )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Returns the jGTIGraph.
   * 
   * @return The jGTIGraph.
   * @see #jGTIGraph
   */
  public JGTIGraph getJGTIGraph ()
  {
    return this.jGTIGraph;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.model.DefaultModel#getElement()
   */
  public Element getElement ()
  {
    Element newElement = new Element ( "RegexModel" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "regexVersion", //$NON-NLS-1$
        REGEX_VERSION ) );
    newElement.addElement ( this.regex.getAlphabet ().getElement () );
    newElement.addElement ( this.regex.getElement () );
    return newElement;
  }


  /**
   * TODO
   *
   */
  public void initializeGraph ()
  {
    this.graphModel = new DefaultGraphModel ();

    this.jGTIGraph = new JGTIGraph ( this.graphModel );
    this.jGTIGraph.setDoubleBuffered ( false );
    this.jGTIGraph.getGraphLayoutCache ()
        .setFactory ( new GPCellViewFactory () );
    this.jGTIGraph.setInvokesStopCellEditing ( true );
    this.jGTIGraph.setJumpToDefaultPort ( true );
    this.jGTIGraph.setSizeable ( false );
    this.jGTIGraph.setConnectable ( false );
    this.jGTIGraph.setDisconnectable ( false );
    this.jGTIGraph.setEdgeLabelsMovable ( false );
    this.jGTIGraph.setEditable ( false );
    this.jGTIGraph.setHandleSize ( 0 );
    this.jGTIGraph.setXorEnabled ( false );
    
    EdgeView.renderer = new EdgeRenderer ();
    EdgeView.renderer.setForeground ( Color.BLACK );
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.regex.addModifyStatusChangedListener ( listener );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.regex.isModified ();
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.regex.removeModifyStatusChangedListener ( listener );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.regex.resetModify ();
  }

  /**
   * Creates the Tree
   */
  public void createTree() {

    DefaultNodeView parent = createNodeView (
        0, 0, this.regex
            .getRegexNode () );
    addNodesToModel ( parent );

    int x_overhead = 0;

    for ( DefaultNodeView nodeView : getNodeViewList () )
    {
      int act_x = nodeView.getX ();
      if ( act_x < 0 && Math.abs ( act_x ) > x_overhead )
      {
        x_overhead = Math.abs ( act_x );
      }
    }
    if ( x_overhead > 0 )
    {
      x_overhead += this.X_BORDER;

      for ( DefaultNodeView nodeView : getNodeViewList () )
      {
        nodeView.moveRelative ( x_overhead, 0 );
      }
      getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( getGraphModel () ));
    }
  }

  /**
   * TODO
   * 
   * @param parent
   */
  private void addNodesToModel ( DefaultNodeView parent )
  {

    int y = parent.getY () + this.Y_SPACE;
    int x = parent.getX ();

    if ( parent.getNode ().getChildren ().size () > 1 )
    {
      for ( int i = 0 ; i < parent.getNode ().getChildren ().size () ; i++ )
      {
        RegexNode childNode = parent.getRegexNode ().getChildren ().get ( i );
        if ( i == 0 )
        {
          // Left node
          x -= this.X_SPACE / 2;
          for ( int j = 0 ; j < childNode.getRightChildrenCount () ; j++ )
          {
            x -= this.X_SPACE / 2;
          }
          if ( childNode.getRightChildrenCount () >= 2 )
          {
            x -= ( this.X_SPACE / 2 )
                * ( childNode.getRightChildrenCount () - 1 );
          }
        }
        else
        {
          // Right node
          x = parent.getX ();
          x += this.X_SPACE / 2;
          for ( int j = 0 ; j < childNode.getLeftChildrenCount () ; j++ )
          {
            x += this.X_SPACE / 2;
          }
          if ( childNode.getLeftChildrenCount () >= 2 )
          {
            x += ( this.X_SPACE / 2 )
                * ( childNode.getLeftChildrenCount () - 1 );
          }
        }

        DefaultNodeView child = createNodeView ( x, y, childNode );
        x += this.X_SPACE;
        createRegexEdgeView ( parent, child );
        addNodesToModel ( child );
      }

    }
    else if ( parent.getNode ().getChildren ().size () == 1 )
    {
      DefaultNodeView child = createNodeView ( x, y, parent
          .getRegexNode ().getChildren ().get ( 0 ) );
      createRegexEdgeView ( parent, child );
      addNodesToModel ( child );
    }

  }

  /**
   * TODO
   *
   * @param parent
   * @param child
   * @return
   */
  public DefaultRegexEdgeView createRegexEdgeView ( DefaultNodeView parent,
      DefaultNodeView child )
  {
    DefaultRegexEdgeView edgeView = new DefaultRegexEdgeView(parent, child);

    
    // Set the parallel routing
    JGraphpadParallelSplineRouter.getSharedInstance ().setEdgeSeparation ( 25 );
    GraphConstants.setRouting ( edgeView.getAttributes (),
        JGraphpadParallelSplineRouter.getSharedInstance () );

    
    this.jGTIGraph.getGraphLayoutCache ().insertEdge ( edgeView,
        parent.getChildAt ( 0 ), child.getChildAt ( 0 ) );
    
    this.regexEdgeViewList.add ( edgeView );
    
    return edgeView;
  }


  /**
   * TODO
   *
   * @param x
   * @param y
   * @param node
   * @return
   */
  @SuppressWarnings("unchecked")
  public DefaultNodeView createNodeView ( double x, double y, RegexNode node )
  {
    DefaultNodeView nodeView = new DefaultNodeView ( this, this.graphModel,
        node, (int)x, (int)y );

    String viewClass = NodeView.class.getName ();

    //Set bounds
    GraphConstants.setBounds ( nodeView.getAttributes (),
        new Rectangle2D.Double ( x, y, 20, 20 ) );
        

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( nodeView.getAttributes (), viewClass );

    // Opaque
    GraphConstants.setOpaque ( nodeView.getAttributes (), true );

    // Gradient
    GraphConstants.setGradientColor ( nodeView.getAttributes (), Color.BLACK );
    
    // Set black border
    GraphConstants.setBorderColor ( nodeView.getAttributes (), Color.BLACK );

    // Set the line width
    GraphConstants.setLineWidth ( nodeView.getAttributes (), 1 );

    // Add a floating port
    nodeView.addPort ();

    this.jGTIGraph.getGraphLayoutCache ().insert ( nodeView );
    this.nodeViewList.add ( nodeView );

    // Reset modify
    nodeView.resetModify ();

    return nodeView;
  }
  
  
  /**
   * Returns the nodeViewList.
   *
   * @return The nodeViewList.
   * @see #nodeViewList
   */
  public ArrayList < DefaultNodeView > getNodeViewList ()
  {
    return this.nodeViewList;
  }
  
  
  /**
   * Returns the graphModel.
   *
   * @return The graphModel.
   * @see #graphModel
   */
  public DefaultGraphModel getGraphModel ()
  {
    return this.graphModel;
  }

}
