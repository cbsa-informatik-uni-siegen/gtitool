package de.unisiegen.gtitool.ui.model;


import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.event.EventListenerList;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.DisjunctionNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.OptionalNode;
import de.unisiegen.gtitool.core.entities.regex.PlusNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultNodeView;
import de.unisiegen.gtitool.ui.jgraph.DefaultRegexEdgeView;
import de.unisiegen.gtitool.ui.jgraph.EdgeRenderer;
import de.unisiegen.gtitool.ui.jgraph.GPCellViewFactory;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.JGraphpadParallelSplineRouter;
import de.unisiegen.gtitool.ui.jgraph.NodeView;


/**
 * The Default model for a Regex
 */
public class DefaultRegexModel implements DefaultModel, Storable, Modifyable
{

  /**
   * The default y-space in the graph
   */
  private int Y_SPACE = 50;


  /**
   * The default x-space in the graph
   */
  private int X_SPACE = 70;


  /**
   * The default x-border of the graph
   */
  private final int X_BORDER = 15;


  /**
   * The {@link DefaultRegex}
   */
  private DefaultRegex regex;


  /**
   * The initial {@link DefaultRegex}
   */
  private DefaultRegex initialRegex;


  /**
   * The {@link JGTIGraph}
   */
  private JGTIGraph jGTIGraph;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * A list of all {@link DefaultNodeView}s
   */
  private ArrayList < DefaultNodeView > nodeViewList = new ArrayList < DefaultNodeView > ();


  /**
   * A list of all {@link DefaultRegexEdgeView}s
   */
  private ArrayList < DefaultRegexEdgeView > regexEdgeViewList = new ArrayList < DefaultRegexEdgeView > ();


  /**
   * The {@link DefaultGraphModel} for this model.
   */
  private DefaultGraphModel graphModel;


  /**
   * The {@link Regex} version.
   */
  private static final int REGEX_VERSION = 1;


  /**
   * Constructor for a new {@link DefaultRegexModel}
   * 
   * @param regex The {@link DefaultRegex} for this model
   * @param startModified Set the new Model modified or not
   */
  public DefaultRegexModel ( DefaultRegex regex, boolean startModified )
  {
    this.regex = regex;
    if ( !startModified )
    {
      this.initialRegex = regex.clone ();
    }
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
   * Changes the RegexNode
   * 
   * @param node The new {@link RegexNode}
   * @param regexString The new String for the Regex
   */
  public void changeRegexNode ( RegexNode node, String regexString )
  {
    this.regex.setRegexNode ( node, regexString );
    fireModifyStatusChanged ( false );
  }


  /**
   * Constructor for a saved {@link DefaultRegexModel}
   * 
   * @param element The saved {@link DefaultRegex}
   * @throws Exception
   */
  public DefaultRegexModel ( Element element ) throws Exception
  {
    boolean foundVersion = false;
    String regexString = new String ();
    for ( Attribute attr : element.getAttribute () )
    {
      if ( attr.getName ().equals ( "regexVersion" ) ) //$NON-NLS-1$
      {
        foundVersion = true;
        if ( attr.getValueInt () != REGEX_VERSION )
        {
          throw new StoreException ( de.unisiegen.gtitool.core.i18n.Messages
              .getString ( "StoreException.IncompatibleVersion" ) ); //$NON-NLS-1$
        }
      }
      if ( attr.getName ().equals ( "regexString" ) ) //$NON-NLS-1$
      {
        regexString = attr.getValue ();
      }
    }
    if ( regexString == null || regexString.length () == 0 )
    {
      throw new StoreException ( de.unisiegen.gtitool.core.i18n.Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }
    DefaultRegexAlphabet da = new DefaultRegexAlphabet ( element
        .getElement ( 0 ) );
    this.regex = new DefaultRegex ( da );
    RegexParseable rp = new RegexParseable ();
    this.regex.setRegexNode ( ( RegexNode ) rp.newParser ( regexString )
        .parse (), regexString );
    this.initialRegex = this.regex.clone ();

    if ( !foundVersion )
    {
      throw new StoreException ( de.unisiegen.gtitool.core.i18n.Messages
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
   * {@inheritDoc}
   * 
   * @see DefaultModel#getElement()
   */
  public Element getElement ()
  {
    Element newElement = new Element ( "RegexModel" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "regexVersion", //$NON-NLS-1$
        REGEX_VERSION ) );
    newElement.addElement ( this.regex.getAlphabet () );
    newElement.addAttribute ( new Attribute ( "regexString", //$NON-NLS-1$
        this.regex.getRegexString () ) );
    return newElement;
  }


  /**
   * The {@link FontMetrics}
   */
  private FontMetrics metrics = null;


  /**
   * Initializes the {@link JGTIGraph}
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

    this.metrics = getJGTIGraph ().getFontMetrics ( this.jGTIGraph.getFont () );

    EdgeView.renderer = new EdgeRenderer ();
    EdgeView.renderer.setForeground ( Color.BLACK );
  }


  /**
   * The x overhead
   */
  private int x_moving = 0;


  /**
   * Creates the Tree
   */
  public void createTree ()
  {
    this.X_SPACE = 70;
    this.Y_SPACE = 50;

    this.regexEdgeViewList.clear ();
    this.nodeViewList.clear ();

    if ( this.regex.getRegexNode ().getWidth () > 18 )
    {
      this.X_SPACE -= 20;
    }
    if ( this.regex.getRegexNode ().getHeight () > 8 )
    {
      this.Y_SPACE -= 10;
    }

    DefaultNodeView parent = createNodeView ( 0, 0, this.regex.getRegexNode () );
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
    this.x_moving = x_overhead / ( this.X_SPACE / 2 );
    if ( x_overhead > 0 )
    {
      x_overhead += this.X_BORDER;

      for ( DefaultNodeView nodeView : getNodeViewList () )
      {
        nodeView.moveRelative ( x_overhead, 0 );
      }
      getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( getGraphModel () ) );
    }
  }


  /**
   * Recursivly add the Nodes to the Model
   * 
   * @param parent The parent {@link DefaultNodeView}
   */
  private void addNodesToModel ( DefaultNodeView parent )
  {

    int y = parent.getY () + this.Y_SPACE;
    int x = parent.getX ();

    if ( parent.getNode ().getChildren ().size () > 1 )
    {
      for ( int i = 0 ; i < parent.getNode ().getChildren ().size () ; i++ )
      {
        RegexNode childNode = parent.getNode ().getChildren ().get ( i );
        if ( i == 0 )
        {
          // Left Node
          x -= ( this.X_SPACE / 2 );
          if ( childNode instanceof ConcatenationNode )
          {
            ConcatenationNode con = ( ConcatenationNode ) childNode;
            x -= ( ( this.X_SPACE / 2 ) * con.countRightChildren () );
          }
          else if ( childNode instanceof DisjunctionNode )
          {
            DisjunctionNode dis = ( DisjunctionNode ) childNode;
            x -= ( ( this.X_SPACE / 2 ) * dis.countRightChildren () );
          }
          else if ( childNode instanceof KleeneNode
              || childNode instanceof PlusNode
              || childNode instanceof OptionalNode )
          {
            x -= ( ( this.X_SPACE / 2 ) * ( childNode.getRightChildrenCount () ) );
          }
        }
        else
        {
          // Right node
          x = parent.getX ();
          x += ( this.X_SPACE / 2 );
          if ( childNode instanceof ConcatenationNode )
          {
            ConcatenationNode con = ( ConcatenationNode ) childNode;
            x += ( ( this.X_SPACE / 2 ) * con.countLeftChildren () );
          }
          else if ( childNode instanceof DisjunctionNode )
          {
            DisjunctionNode dis = ( DisjunctionNode ) childNode;
            x += ( ( this.X_SPACE / 2 ) * dis.countLeftChildren () );
          }
          else if ( childNode instanceof KleeneNode
              || childNode instanceof PlusNode
              || childNode instanceof OptionalNode )
          {
            x += ( ( this.X_SPACE / 2 ) * ( childNode.getLeftChildrenCount () ) );
          }
        }

        DefaultNodeView child = createNodeView ( x, y, childNode );
        createRegexEdgeView ( parent, child );
        addNodesToModel ( child );
      }

    }
    else if ( parent.getNode ().getChildren ().size () == 1 )
    {
      DefaultNodeView child = createNodeView ( x, y, parent.getNode ()
          .getChildren ().get ( 0 ) );
      createRegexEdgeView ( parent, child );
      addNodesToModel ( child );
    }

  }


  /**
   * Creates a new {@link DefaultRegexEdgeView}
   * 
   * @param parent The parent {@link DefaultNodeView}
   * @param child The child {@link DefaultNodeView}
   * @return The created {@link DefaultRegexEdgeView}
   */
  public DefaultRegexEdgeView createRegexEdgeView ( DefaultNodeView parent,
      DefaultNodeView child )
  {
    DefaultRegexEdgeView edgeView = new DefaultRegexEdgeView ( parent, child );

    // Set the parallel routing
    JGraphpadParallelSplineRouter.getSharedInstance ().setEdgeSeparation ( 25 );
    GraphConstants.setRouting ( edgeView.getAttributes (),
        JGraphpadParallelSplineRouter.getSharedInstance () );

    GraphConstants.setSelectable ( edgeView.getAttributes (), false );

    this.jGTIGraph.getGraphLayoutCache ().insertEdge ( edgeView,
        parent.getChildAt ( 0 ), child.getChildAt ( 0 ) );

    this.regexEdgeViewList.add ( edgeView );

    return edgeView;
  }


  /**
   * Creates a new {@link DefaultNodeView}
   * 
   * @param x The x value
   * @param y The y value
   * @param node The content
   * @return The created {@link DefaultNodeView}
   */
  @SuppressWarnings ( "unchecked" )
  public DefaultNodeView createNodeView ( double x, double y, RegexNode node )
  {
    DefaultNodeView nodeView = new DefaultNodeView ( node, ( int ) x, ( int ) y );

    String viewClass = NodeView.class.getName ();

    int maxY = this.metrics.getHeight () + 10;

    int maxX = 15;

    if ( node instanceof LeafNode )
    {
      LeafNode leaf = ( LeafNode ) node;
      if ( leaf.isPositionShown () )
      {
        maxY = this.metrics.getHeight () * 2 + 4;
        maxX = Math.max ( 25, Math.max ( this.metrics.stringWidth ( node
            .getNodeString ().toString () ), this.metrics.stringWidth ( String
            .valueOf ( leaf.getPosition () ) ) ) );
        // System.err.println ("Node: " + node.getNodeString () + " maxX: " +
        // maxX);
      }
      else
      {
        maxX = Math.max ( 25, this.metrics.stringWidth ( node.getNodeString ()
            .toString () ) );
      }
    }
    // Set bounds
    GraphConstants.setBounds ( nodeView.getAttributes (),
        new Rectangle2D.Double ( x, y, maxX, maxY ) );

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


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Fired if the Regex was modified
   * 
   * @param forceModify Flag indicates that modify was forced
   */
  public void fireModifyStatusChanged ( final boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    if ( this.regex != null )
    {
      return !this.regex.equals ( this.initialRegex );
    }
    return this.initialRegex != null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Converts the graph to Latex
   * 
   * @return The latex string
   */
  public String toLatexString ()
  {
    String s = ""; //$NON-NLS-1$
    RegexNode node = this.regex.getRegexNode ();

    int w = node.getWidth ();
    s += " %" + Messages.getString ( "LatexComment.CreateTabular" ) + "\n"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    s += " \\begin{tabular}{"; //$NON-NLS-1$
    for ( int i = 0 ; i < w ; i++ )
    {
      s += "c"; //$NON-NLS-1$
    }
    s += "}\n"; //$NON-NLS-1$

    ArrayList < DefaultNodeView > nodes = this.nodeViewList;
    Collections.sort ( nodes );

    int j = 0;
    s += "  %" + Messages.getString ( "LatexComment.CreateNodes" ) + "\n"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    for ( int i = 0 ; i < nodes.size () ; i++ )
    {
      DefaultNodeView view = nodes.get ( i );
      int x_over = ( view.getX () ) / ( this.X_SPACE / 2 );
      // Add space
      s += "  "; //$NON-NLS-1$
      x_over += this.x_moving;
      for ( ; j < x_over ; j++ )
      {
        s += " & "; //$NON-NLS-1$
      }
      String name = view.getNode ().getNodeString ().toString ();
      if ( name.equals ( "#" ) ) //$NON-NLS-1$
      {
        name = "\\#"; //$NON-NLS-1$
      }
      else if ( name.equals ( "|" ) ) //$NON-NLS-1$
      {
        name = "$|$"; //$NON-NLS-1$
      }
      else if ( name.equals ( "\u03B5" ) ) //$NON-NLS-1$
      {
        name = "$\\epsilon$"; //$NON-NLS-1$
      }
      s += "\\node{r" + i + "}{" + name + "}"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
      if ( !view.equals ( nodes.get ( nodes.size () - 1 ) )
          && view.getY () != nodes.get ( i + 1 ).getY () )
      {
        s += "\\\\[4ex]\n"; //$NON-NLS-1$
        j = 0;
      }
    }
    s += "\n \\end{tabular}\n"; //$NON-NLS-1$
    s += " %" + Messages.getString ( "LatexComment.NodeConnect" ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    for ( DefaultRegexEdgeView v : this.regexEdgeViewList )
    {
      int parentId = nodes.indexOf ( v.getParentView () );
      int childId = nodes.indexOf ( v.getChildView () );
      s += " \\nodeconnect{r" + parentId + "}{r" + childId + "}\n"; //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
    }
    return s;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.initialRegex = this.regex.clone ();
  }

}
