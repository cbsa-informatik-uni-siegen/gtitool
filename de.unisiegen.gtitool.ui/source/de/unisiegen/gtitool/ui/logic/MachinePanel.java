package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;


/**
 * The Panel containing the diagramm and table representing a machine
 * 
 * @author Benjamin Mies
 */
public class MachinePanel implements EditorPanel
{

  /** Number of created states */
  private static int statecount = 0;


  /** the parent window */
  JFrame parent;


  /** The {@linkMachinesPanelForm} */
  MachinesPanelForm machinePanel;


  /** The {@link JGraph} containing the diagramm */
  JGraph graph;


  /** The {@link GraphModel} for this graph */
  GraphModel model;


  /** The {@link MouseAdapter} for the mouse icon in the toolbar */
  MouseAdapter mouse;


  /** The {@link MouseAdapter} for the add State icon in the toolbar */
  MouseAdapter addState;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  MouseAdapter transition;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  MouseMotionAdapter transitionMove;


  /** The {@link MouseAdapter} for the remove icon in the toolbar */
  MouseAdapter remove;


  /** The {@link MouseAdapter} for the start icon in the toolbar */
  MouseAdapter start;


  /** The {@link MouseAdapter} for the end icon in the toolbar */
  MouseAdapter end;


  /** The source state for a new Transition */
  DefaultGraphCell firstState;


  /** The tmp state for a new Transition */
  DefaultGraphCell tmpState;


  /** The tmp transition */
  DefaultEdge tmpTransition;
  
  /** The position of the first state */
  Point firstStatePosition;
  


  /**
   * Create a new Machine Panel Object
   * 
   * @param pParent The parent frame
   */
  public MachinePanel ( JFrame pParent )
  {
    this.parent = pParent;
    initializeGraph ();
    this.machinePanel = new MachinesPanelForm ();
    this.machinePanel.setMachinePanel ( this );
    // this.machinePanel.alphabetPanel.add ( alphabetPanel );

    this.machinePanel.diagrammContentPanel.setViewportView ( this.graph );

    intitializeMouseAdapter ();

    this.graph.setSizeable ( false );
    this.graph.setConnectable ( false );
    this.graph.setDisconnectable ( false );
    this.graph.setEdgeLabelsMovable ( false );

  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.machinePanel;
  }


  /**
   * Initialize this JGraph
   */
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

  }

  
  /**
   * Create a new State view
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @param name the name of the new state view
   * @param bg the background color of the new state view
   * @return {@link DefaultGraphCell} the new created state view
   */
  public static DefaultGraphCell createStateView ( double x, double y,
      String name, Color bg )
  {
   return  createStateView(x, y, name, bg, false, 40, 40);
    
  }

  /**
   * Create a new State view
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @param name the name of the new state view
   * @param bg the background color of the new state view
   * @return {@link DefaultGraphCell} the new created state view
   */
  public static DefaultGraphCell createStateView ( double x, double y,
      String name, Color bg, boolean raised, double w, double h )
  {

    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.JGraphEllipseView"; //$NON-NLS-1$
    DefaultGraphCell cell = new DefaultGraphCell ( name );

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x, y, w, h ) );

    // Set fill color
    if ( bg != null )
      GraphConstants.setGradientColor ( cell.getAttributes (), bg );
    GraphConstants.setOpaque ( cell.getAttributes (), true );

    // Set raised border
    if ( raised )
    {
      GraphConstants.setBorder ( cell.getAttributes (), BorderFactory
          .createRaisedBevelBorder () );

    }
    else
    {
      // Set black border
      GraphConstants.setBorderColor ( cell.getAttributes (), Color.black );
    }
    // Add a Floating Port
    cell.addPort ();

    return cell;
  }


  /**
   * Initialize the Mouse Adapter of the Toolbar
   */
  private void intitializeMouseAdapter ()
  {
    this.mouse = new MouseAdapter ()
    {
      // Nothing to Override
    };

    this.addState = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseClicked ( MouseEvent e )
      {
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, null ) );
        MachinePanel.statecount++ ;
      }
    };

    this.transition = new MouseAdapter ()
    {

      @Override
      public void mouseClicked ( MouseEvent e )
      {

        if ( MachinePanel.this.firstState == null )
        {
          MachinePanel.this.firstState = ( DefaultGraphCell ) MachinePanel.this.graph
              .getSelectionCellAt ( e.getPoint () );
          MachinePanel.this.firstStatePosition = e.getPoint ();
        }
        else
        {

          MachinePanel.this.model.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );

          DefaultGraphCell target = ( DefaultGraphCell ) MachinePanel.this.graph
              .getSelectionCellAt ( e.getPoint () );
          if ( target != null )
          {
            DefaultEdge newEdge = new DefaultEdge ( "Test" ); //$NON-NLS-1$
            GraphConstants.setLineEnd ( newEdge.getAttributes (),
                GraphConstants.ARROW_CLASSIC );
            GraphConstants.setEndFill ( newEdge.getAttributes (), true );

            MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                newEdge, MachinePanel.this.firstState.getChildAt ( 0 ),
                target.getChildAt ( 0 ) );
          }

          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
        }
      }

    };

    this.transitionMove = new MouseMotionAdapter ()
    {

      public void mouseMoved ( MouseEvent e )
      {
        int x,y;

        if ( MachinePanel.this.firstState != null )
        {
          // Remove old tmp state and transition
          MachinePanel.this.model.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          x = MachinePanel.this.firstStatePosition.x < e.getX () ? e.getX ()-5 : e.getX () + 5;
          y = MachinePanel.this.firstStatePosition.y < e.getY () ? e.getY ()-10 : e.getY () + 10;
          MachinePanel.this.tmpState = createStateView ( x ,
              y, "", null, true, 1, 1 );
          MachinePanel.this.graph.getGraphLayoutCache ().insert (
              MachinePanel.this.tmpState );

          MachinePanel.this.tmpTransition = new DefaultEdge ( "" ); //$NON-NLS-1$
          GraphConstants.setLineEnd ( MachinePanel.this.tmpTransition
              .getAttributes (), GraphConstants.ARROW_CLASSIC );
          GraphConstants.setEndFill ( MachinePanel.this.tmpTransition
              .getAttributes (), true );

          MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
              MachinePanel.this.tmpTransition,
              MachinePanel.this.firstState.getChildAt ( 0 ),
              MachinePanel.this.tmpState.getChildAt ( 0 ) );
        }
      }
    };

    this.remove = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent e )
      {
        DefaultGraphCell cell = ( DefaultGraphCell ) MachinePanel.this.graph
            .getSelectionCellAt ( e.getPoint () );
        if ( cell != null )
        {
          int choice = JOptionPane.NO_OPTION;
          if ( cell instanceof DefaultEdge )
          {
            String message = "Soll die Transition \"" //$NON-NLS-1$
                + cell.toString () + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
            choice = JOptionPane.showConfirmDialog ( null, message,
                "Zustand löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
          }
          else
          {
            String message = "Soll der Zustand \"" //$NON-NLS-1$
                + cell.toString () + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
            choice = JOptionPane.showConfirmDialog ( null, message,
                "Zustand löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
          }
          if ( choice == JOptionPane.YES_OPTION )
          {
            MachinePanel.this.model.remove ( new Object []
            { cell } );
          }
        }
      }
    };

    this.start = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent e )
      {
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, Color.green ) );
        MachinePanel.statecount++ ;
      }
    };

    this.end = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent e )
      {
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, Color.red ) );
        MachinePanel.statecount++ ;
      }
    };
  }


  /**
   * Handle Toolbar Add State button value changed
   * 
   * @param state The new State of the Add State Toolbar button
   */
  public void handleToolbarAddState ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.addState );
    else
      this.graph.removeMouseListener ( this.addState );
  }


  /**
   * Handle Toolbar Mouse button value changed
   * 
   * @param state The new State of the Mouse Toolbar button
   */
  public void handleToolbarMouse ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.mouse );
    else
      this.graph.removeMouseListener ( this.mouse );
  }


  /**
   * Handle Toolbar Transition button value changed
   * 
   * @param state The new State of the Transition Toolbar button
   */
  public void handleToolbarTransition ( boolean state )
  {
    if ( state )
    {
      this.graph.addMouseListener ( this.transition );
      this.graph.addMouseMotionListener ( this.transitionMove );
    }
    else
    {
      this.graph.removeMouseListener ( this.transition );
      this.graph.removeMouseMotionListener ( this.transitionMove );
    }
  }


  /**
   * Handle Toolbar Start button value changed
   * 
   * @param state The new State of the Start Toolbar button
   */
  public void handleToolbarStart ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.start );
    else
      this.graph.removeMouseListener ( this.start );
  }


  /**
   * Handle Toolbar End button value changed
   * 
   * @param state The new State of the End Toolbar button
   */
  public void handleToolbarEnd ( boolean state )
  {
    if ( state )
      this.graph.addMouseListener ( this.end );
    else
      this.graph.removeMouseListener ( this.end );
  }


  /**
   * Handle Toolbar Alphabet button action event
   */
  public void handleToolbarAlphabet ()
  {
    JDialog alphabet = new JDialog ( this.parent, true );
    // alphabet.setContentPane ( this.alphabetButtons );
    alphabet.setTitle ( "Edit Alphabet" ); //$NON-NLS-1$
    alphabet.pack ();
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( alphabet.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( alphabet.getHeight () / 2 );
    alphabet.setBounds ( x, y, alphabet.getWidth (), alphabet.getHeight () );
    alphabet.setVisible ( true );
  }
}
