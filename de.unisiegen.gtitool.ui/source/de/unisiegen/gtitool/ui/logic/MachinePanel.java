package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.AttributeMap.SerializableRectangle2D;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;


public class MachinePanel implements EditorPanel
{

  private static int statecount = 0;


  /** the parent window */
  JFrame parent;


  /** The panel containing the alphabet button */
  JPanel alphabetButtons;


  /** The {@linkMachinesPanelForm} */
  MachinesPanelForm machinePanel;


  JGraph graph;


  GraphModel model;


  MouseListener mouse;


  MouseListener addState;


  MouseListener transition;


  MouseListener removeState;


  MouseListener start;


  MouseListener end;


  DefaultGraphCell firstState;


  public MachinePanel ( JFrame pParent, JPanel alphabetPanel )
  {
    this.parent = pParent;
    this.alphabetButtons = alphabetPanel;
    initializeGraph ();
    this.machinePanel = new MachinesPanelForm ();
    this.machinePanel.setMachinePanel ( this );
    // this.machinePanel.alphabetPanel.add ( alphabetPanel );

    this.machinePanel.diagrammContentPanel.setViewportView ( this.graph );

    intitializeMouseListener ();

    this.graph.setSizeable ( false );
    this.graph.setConnectable ( false );
    this.graph.setDisconnectable ( false );

  }


  public JPanel getPanel ()
  {
    return this.machinePanel;
  }


  private void initializeGraph ()
  {
    // Construct Model and Graph
    this.model = new DefaultGraphModel ();

    this.graph = new JGraph ( this.model );

    this.graph.getGraphLayoutCache ().setFactory ( new GPCellViewFactory () );

    // Control-drag should clone selection
    this.graph.setCloneable ( true );

    // Enable edit without final RETURN keystroke
    this.graph.setInvokesStopCellEditing ( true );

    // When over a cell, jump to its default port (we only have one, anyway)
    this.graph.setJumpToDefaultPort ( true );

//    // Insert all three cells in one call, so we need an array to store them
//    DefaultGraphCell [] cells = new DefaultGraphCell [ 7 ];
//
//    cells [ 0 ] = createVertex ( 20, 20, "hello", Color.BLUE );
//
//    cells [ 1 ] = createVertex ( 140, 25, "brave", null );
//
//    cells [ 2 ] = createVertex ( 20, 145, "new", null );
//
//    cells [ 3 ] = createVertex ( 140, 140, "world", Color.ORANGE );
//
//    // Create Edges
//    DefaultEdge edge0 = new DefaultEdge ( "Test" );
//    edge0.setSource ( cells [ 0 ].getChildAt ( 0 ) );
//    edge0.setTarget ( cells [ 1 ].getChildAt ( 0 ) );
//    cells [ 4 ] = edge0;
//    GraphConstants.setLineEnd ( edge0.getAttributes (),
//        GraphConstants.ARROW_CIRCLE );
//    GraphConstants.setEndFill ( edge0.getAttributes (), true );
//
//    DefaultEdge edge1 = new DefaultEdge ();
//    edge1.setSource ( cells [ 1 ].getChildAt ( 0 ) );
//    edge1.setTarget ( cells [ 2 ].getChildAt ( 0 ) );
//    cells [ 5 ] = edge1;
//    GraphConstants.setLineEnd ( edge1.getAttributes (),
//        GraphConstants.ARROW_CLASSIC );
//    GraphConstants.setEndFill ( edge1.getAttributes (), true );
//
//    DefaultEdge edge2 = new DefaultEdge ();
//    edge2.setSource ( cells [ 2 ].getChildAt ( 0 ) );
//    edge2.setTarget ( cells [ 3 ].getChildAt ( 0 ) );
//    cells [ 6 ] = edge2;
//    GraphConstants.setLineEnd ( edge2.getAttributes (),
//        GraphConstants.ARROW_DIAMOND );
//    GraphConstants.setEndFill ( edge2.getAttributes (), true );
//
//    // Insert the cells via the cache, so they get selected
//    this.graph.getGraphLayoutCache ().insert ( cells );
  }


  public static DefaultGraphCell createVertex ( double x, double y,
      String name, Color bg )
  {

    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.JGraphEllipseView";
    double w = 40;
    double h = 40;
    boolean raised = false;
    DefaultGraphCell cell = new DefaultGraphCell ( name );

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x, y, w, h ) );

    // Set fill color
    if ( bg == null )
      bg = new Color ( 255, 255, 255 );
    GraphConstants.setGradientColor ( cell.getAttributes (), bg );
    GraphConstants.setOpaque ( cell.getAttributes (), true );

    // Set raised border
    if ( raised )
      GraphConstants.setBorder ( cell.getAttributes (), BorderFactory
          .createRaisedBevelBorder () );
    else
      // Set black border
      GraphConstants.setBorderColor ( cell.getAttributes (), Color.black );

    // Add a Floating Port
    cell.addPort ();

    return cell;
  }


  @SuppressWarnings ( "unused" )
  private void intitializeMouseListener ()
  {
    this.mouse = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };

    this.addState = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createVertex ( e.getPoint ().x, e.getPoint ().y, "Z"
                + MachinePanel.this.statecount, null ) );
        MachinePanel.statecount++ ;
      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };

    this.transition = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        // TODO
        DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.graph
            .getSelectionCellAt ( e.getPoint () );
        if ( !(cell instanceof DefaultEdge) )
        {
          if ( MachinePanel.this.firstState == null )
          {

            SerializableRectangle2D rectangle = ( SerializableRectangle2D ) cell
                .getAttributes ().get ( "bounds" );
            model.remove ( new Object []
            { cell } );
            MachinePanel.this.firstState = createVertex ( rectangle.x,
                rectangle.y, cell.toString (), Color.cyan );
            MachinePanel.this.graph.getGraphLayoutCache ().insert (
                MachinePanel.this.firstState );
          }
          else
          {
            SerializableRectangle2D rectangle = ( SerializableRectangle2D ) MachinePanel.this.firstState
                .getAttributes ().get ( "bounds" );
            DefaultGraphCell first = createVertex ( rectangle.x, rectangle.y,
                MachinePanel.this.firstState.toString (), null );
            model.remove ( new Object []
            { MachinePanel.this.firstState } );
            MachinePanel.this.graph.getGraphLayoutCache ().insert ( first );
            DefaultEdge edge0 = new DefaultEdge ( "Test" );
            GraphConstants.setLineEnd ( edge0.getAttributes (),
                GraphConstants.ARROW_CLASSIC );
            GraphConstants.setEndFill ( edge0.getAttributes (), true );
            if ( !cell.equals ( MachinePanel.this.firstState ) )
              MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                  edge0, first.getChildAt ( 0 ), cell.getChildAt ( 0 ) );
            else
              MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                  edge0, first.getChildAt ( 0 ), first.getChildAt ( 0 ) );

            MachinePanel.this.firstState = null;
          }
        }

      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };

    this.removeState = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.graph
            .getSelectionCellAt ( e.getPoint () );
        if ( cell != null )
        {
          int choice = JOptionPane.NO_OPTION;
          if (cell instanceof DefaultEdge){
            String message = "Soll die Transition \"" //$NON-NLS-1$
              + cell.toString () + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
           choice = JOptionPane.showConfirmDialog ( null, message,
              "Zustand löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
          }
          else {
          String message = "Soll der Zustand \"" //$NON-NLS-1$
              + cell.toString () + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
           choice = JOptionPane.showConfirmDialog ( null, message,
              "Zustand löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
          }
          if ( choice == JOptionPane.YES_OPTION )
          {
            model.remove ( new Object []
            { cell } );
          }
        }
      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };

    this.start = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.graph
            .getSelectionCellAt ( e.getPoint () );
        if ( cell != null && ! ( cell instanceof DefaultEdge ) )
        {
          SerializableRectangle2D rectangle = ( SerializableRectangle2D ) cell
              .getAttributes ().get ( "bounds" );
          String name = cell.toString ();
          model.remove ( new Object []
          { cell } );
          MachinePanel.this.graph.getGraphLayoutCache ().insert (
              createVertex ( rectangle.x, rectangle.y, name, Color.green ) );
        }
      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };

    this.end = new MouseListener ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      public void mouseClicked ( MouseEvent e )
      {
        DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.graph
            .getSelectionCellAt ( e.getPoint () );
        if ( cell != null && ! ( cell instanceof DefaultEdge ) )
        {
          SerializableRectangle2D rectangle = ( SerializableRectangle2D ) cell
              .getAttributes ().get ( "bounds" );
          String name = cell.toString ();
          model.remove ( new Object []
          { cell } );
          MachinePanel.this.graph.getGraphLayoutCache ().insert (
              createVertex ( rectangle.x, rectangle.y, name, Color.red ) );
        }
      }


      /**
       * Invoked when a mouse button has been pressed on a component.
       */
      public void mousePressed ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when a mouse button has been released on a component.
       */
      public void mouseReleased ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse enters a component.
       */
      public void mouseEntered ( MouseEvent e )
      {
        // Nothing to do here
      }


      /**
       * Invoked when the mouse exits a component.
       */
      public void mouseExited ( MouseEvent e )
      {
        // Nothing to do here
      }
    };
  }


  public void handleToolbarAddState ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.addState );
    else
      this.graph.removeMouseListener ( this.addState );
  }


  public void handleToolbarMouse ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.mouse );
    else
      this.graph.removeMouseListener ( this.mouse );
  }


  public void handleToolbarTransition ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.transition );
    else
      this.graph.removeMouseListener ( this.transition );
  }


  public void handleToolbarDelState ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.removeState );
    else
      this.graph.removeMouseListener ( this.removeState );
  }


  public void handleToolbarStart ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.start );
    else
      this.graph.removeMouseListener ( this.start );
  }


  public void handleToolbarEnd ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.end );
    else
      this.graph.removeMouseListener ( this.end );
  }


  public void handleToolbarAlphabet ()
  {
    JDialog alphabet = new JDialog ( this.parent, true );
    alphabet.setContentPane ( this.alphabetButtons );
    alphabet.setTitle ( "Edit Alphabet" );
    alphabet.pack ();
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( alphabet.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( alphabet.getHeight () / 2 );
    alphabet.setBounds ( x, y, alphabet.getWidth (), alphabet.getHeight () );
    alphabet.setVisible ( true );
  }
}
