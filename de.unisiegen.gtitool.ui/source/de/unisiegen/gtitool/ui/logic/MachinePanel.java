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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraphcomponents.GPCellViewFactory;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;
import de.unisiegen.gtitool.ui.popup.StatePopupMenu;
import de.unisiegen.gtitool.ui.popup.TransitionPopupMenu;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * The Panel containing the diagramm and table representing a machine
 * 
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler
 */
public class MachinePanel implements EditorPanel
{

  //
  // Static Attributes
  //

  /** Number of created states */
  public static int statecount = 0;


  //
  // Attributes
  //
  /** the parent window */
  private JFrame parent;


  /** The {@linkMachinesPanelForm} */
  private MachinesPanelForm machinePanel;


  /** The {@link JGraph} containing the diagramm */
  private JGraph graph;


  /** The {@link GraphModel} for this graph */
  private GraphModel model;


  /** The {@link MouseAdapter} for the mouse icon in the toolbar */
  private MouseAdapter mouse;


  /** The {@link MouseAdapter} for the add State icon in the toolbar */
  private MouseAdapter addState;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  private MouseAdapter transition;


  /** The {@link MouseAdapter} for the transition icon in the toolbar */
  private MouseMotionAdapter transitionMove;


  /** The {@link MouseAdapter} for the start icon in the toolbar */
  private MouseAdapter start;


  /** The {@link MouseAdapter} for the end icon in the toolbar */
  private MouseAdapter end;


  /** The source state for a new Transition */
  private DefaultStateView firstState;


  /** The tmp state for a new Transition */
  private DefaultGraphCell tmpState;


  /** The tmp transition */
  private DefaultEdge tmpTransition;


  /** The position of the first state */
  private Point firstStatePosition;


  /** Signals if drag in progress */
  private boolean dragged;


  /** The alphabet of this Machine */
  private Alphabet alphabet;


  /**
   * Create a new State view
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @return {@link DefaultGraphCell} the new created cell
   */
  public static DefaultGraphCell createStateView ( double x, double y )
  {
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$
    
    DefaultGraphCell cell = new DefaultGraphCell (  );

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x  , y , 1, 1 ) );

      GraphConstants.setBorder ( cell.getAttributes (), BorderFactory
          .createRaisedBevelBorder () );


    // Set the line width
    GraphConstants.setLineWidth ( cell.getAttributes (), 2 );

    // Add a Floating Port
    cell.addPort ();

    return cell;

  }


  /**
   * Create a new State view
   * 
   * @param x the x position of the new state view
   * @param y the y position of the new state view
   * @param name the name of the new state view
   * @param bg the background color of the new state view
   * @param raised signals if border is raised
   * @param finalState signals if state is a final state
   * @return {@link DefaultStateView} the new created state view
   */
  public static DefaultStateView createStateView ( double x, double y,
      State pState, String name, Color bg, 
      boolean startState, boolean finalState )
  {
    String viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.StateView"; //$NON-NLS-1$
    if ( finalState )
      viewClass = "de.unisiegen.gtitool.ui.jgraphcomponents.FinalStateView"; //$NON-NLS-1$
    State state = null;
    try
    {
      state = pState != null ? pState : new State ( new Alphabet (), name, startState,
          finalState );
    }
    catch ( StateException exc )
    {
      // Do Nothing
      // Is not possible, because states were created with standard Name
    }
    DefaultStateView cell = new DefaultStateView ( state, name );

    // set the view class (indirection for the renderer and the editor)
    GPCellViewFactory.setViewClass ( cell.getAttributes (), viewClass );

    // Set bounds
    GraphConstants.setBounds ( cell.getAttributes (), new Rectangle2D.Double (
        x - 35 , y - 35, 70, 70 ) );

    // Set fill color
    if ( bg != null )
      GraphConstants.setGradientColor ( cell.getAttributes (), bg );
    GraphConstants.setOpaque ( cell.getAttributes (), true );

      // Set black border
      GraphConstants.setBorderColor ( cell.getAttributes (), Color.black );

    // Set the line width
    GraphConstants.setLineWidth ( cell.getAttributes (), 2 );

    // Add a Floating Port
    cell.addPort ();

    return cell;
  }


  /**
   * Create a new Machine Panel Object
   * 
   * @param pParent The parent frame
   * @param pAlphabet the {@link Alphabet} of this Machine
   */
  public MachinePanel ( JFrame pParent, Alphabet pAlphabet )
  {
    this.parent = pParent;
    this.alphabet = pAlphabet;
    this.machinePanel = new MachinesPanelForm ();
    this.machinePanel.setMachinePanel ( this );
    initializeGraph ();
    intitializeMouseAdapter ();
    this.graph.addMouseListener ( this.mouse );
    this.machinePanel.diagrammContentPanel.setViewportView ( this.graph );
    
    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            MachinePanel.this.machinePanel.jButtonMouse.setToolTipText ( Messages.getString ( "MachinePanel.Mouse" ) ); //$NON-NLS-1$
            MachinePanel.this.machinePanel.jButtonAddState.setToolTipText ( Messages.getString ( "MachinePanel.AddState" ) ); //$NON-NLS-1$
            MachinePanel.this.machinePanel.jButtonAddTransition.setToolTipText ( Messages.getString ( "MachinePanel.AddTransition" ) ); //$NON-NLS-1$
            MachinePanel.this.machinePanel.jButtonStartState.setToolTipText ( Messages.getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
            MachinePanel.this.machinePanel.jButtonFinalState.setToolTipText ( Messages.getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
            MachinePanel.this.machinePanel.jButtonEditAlphabet.setToolTipText ( Messages.getString ( "MachinePanel.EditAlphabet" ) ); //$NON-NLS-1$
          }
        });
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
   * Handle Toolbar Alphabet button action event
   */
  public void handleToolbarAlphabet ()
  {
    JDialog changeAlphabetDialog = new JDialog ( this.parent, true );
    // alphabet.setContentPane ( this.alphabetButtons );
    changeAlphabetDialog.setTitle ( "Edit Alphabet" ); //$NON-NLS-1$
    changeAlphabetDialog.pack ();
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( changeAlphabetDialog.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( changeAlphabetDialog.getHeight () / 2 );
    changeAlphabetDialog.setBounds ( x, y, changeAlphabetDialog.getWidth (),
        changeAlphabetDialog.getHeight () );
    changeAlphabetDialog.setVisible ( true );
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

    // Set states to not sizeable
    this.graph.setSizeable ( false );

    // Set states to not connectable and disconnectable
    // So Transitions are not moveable
    this.graph.setConnectable ( false );
    this.graph.setDisconnectable ( false );

    // Set the labels of the Transitions to not moveable
    this.graph.setEdgeLabelsMovable ( false );

    // Set states to not editable
    this.graph.setEditable ( false );

  }


  /**
   * Initialize the Mouse Adapter of the Toolbar
   */
  @SuppressWarnings ( "synthetic-access" )
  private void intitializeMouseAdapter ()
  {
    this.mouse = new MouseAdapter ()
    {

      @Override
      public void mouseClicked ( MouseEvent e )
      {
        if ( e.getButton () != MouseEvent.BUTTON3 )
          return;
        JPopupMenu popup = null;
        DefaultGraphCell object = ( DefaultGraphCell ) MachinePanel.this.graph
            .getFirstCellForLocation ( e.getPoint ().getX (), e.getPoint ()
                .getY () );
        if ( object == null )
          createPopupMenu ();
        else if ( object instanceof DefaultTransitionView )
          popup = createTransitionPopupMenu ( ( DefaultTransitionView ) object );
        else
          popup = createStatePopupMenu ( ( DefaultStateView ) object );
          
        if ( popup != null )
          popup.show ( MachinePanel.this.machinePanel.diagrammContentPanel, e.getX (), e.getY () );
      }
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
        if ( e.getButton () != MouseEvent.BUTTON1 )
          return;
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, null, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, null, false, false ) );
        MachinePanel.statecount++ ;
      }
    };

    this.transition = new MouseAdapter ()
    {

      @Override
      public void mouseClicked ( MouseEvent e )
      {
        if ( e.getButton () != MouseEvent.BUTTON1 )
          return;
        
        // if drag in progress return
        if ( MachinePanel.this.dragged )
          return;
        try
        {
          if ( MachinePanel.this.firstState == null )
          {
            MachinePanel.this.firstState = ( DefaultStateView ) MachinePanel.this.graph
                .getSelectionCellAt ( e.getPoint () );
            MachinePanel.this.firstStatePosition = e.getPoint ();
          }
          else
          {

            MachinePanel.this.model.remove ( new Object []
            { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
            DefaultStateView target = ( DefaultStateView ) MachinePanel.this.graph
                .getFirstCellForLocation ( e.getPoint ().getX (), e.getPoint ()
                    .getY () );

            if ( target == null )
            {
              target = createStateView ( e.getPoint ().x, e.getPoint ().y, null, "Z" //$NON-NLS-1$
                  + MachinePanel.statecount, null, false, false );
              MachinePanel.statecount++ ;
              MachinePanel.this.graph.getGraphLayoutCache ().insert ( target );
            }
              
              TransitionDialog dialog = new TransitionDialog (
                  MachinePanel.this.parent, MachinePanel.this.alphabet );
              dialog.show ();
              if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
              {
                // TODO
                Transition newTransition;
                if ( dialog.getAlphabet () == null )
                  newTransition = new Transition ( MachinePanel.this.alphabet,
                      MachinePanel.this.firstState.getState (), target
                          .getState () );
                else
                  newTransition = new Transition ( MachinePanel.this.alphabet,
                      MachinePanel.this.firstState.getState (), target
                          .getState (), dialog.getAlphabet ().getSymbols () );
                DefaultEdge newEdge = new DefaultTransitionView (
                    newTransition, dialog.getAlphabet () != null ? dialog
                        .getAlphabet ().toString () : dialog.epsilon
                        .toString () );

                GraphConstants.setLineEnd ( newEdge.getAttributes (),
                    GraphConstants.ARROW_CLASSIC );
                GraphConstants.setEndFill ( newEdge.getAttributes (), true );

                MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                    newEdge, MachinePanel.this.firstState.getChildAt ( 0 ),
                    target.getChildAt ( 0 ) );
                dialog.dispose ();
                
                MachinePanel.this.firstState = null;
                MachinePanel.this.tmpTransition = null;
                MachinePanel.this.tmpState = null;
              }
            }
         
        }
        catch ( TransitionSymbolNotInAlphabetException exc )
        {
          /*
           * NOTICE This exception is thrown if a symbol should be added to a
           * transition, but is not in the alphabet of the transition.
           */
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( TransitionSymbolOnlyOneTimeException exc )
        {
          /*
           * NOTICE This exception is thrown if a symbol should be added to a
           * transition, but is already in the transition.
           */
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( ClassCastException exc )
        {
          // Nothing to do here
        }
      }


      @Override
      public void mouseReleased ( MouseEvent e )
      {
        if ( e.getButton () != MouseEvent.BUTTON1 )
          return;
        try
        {
          if ( !MachinePanel.this.dragged )
            return;
          DefaultStateView target = ( DefaultStateView ) MachinePanel.this.graph
              .getFirstCellForLocation ( e.getPoint ().getX (), e.getPoint ()
                  .getY () );
          if ( target != null && target.equals ( MachinePanel.this.firstState ) )
            return;
          MachinePanel.this.model.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );

          if ( target != null )
          {
            TransitionDialog dialog = new TransitionDialog (
                MachinePanel.this.parent, MachinePanel.this.alphabet );
            dialog.show ();
            if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
            {
              // TODO
              Transition newTransition;
              if ( dialog.getAlphabet () == null )
                newTransition = new Transition ( MachinePanel.this.alphabet,
                    MachinePanel.this.firstState.getState (), target
                        .getState () );
              else
                newTransition = new Transition ( MachinePanel.this.alphabet,
                    MachinePanel.this.firstState.getState (), target
                        .getState (), dialog.getAlphabet ().getSymbols () );
              DefaultEdge newEdge = new DefaultTransitionView ( newTransition,
                  dialog.getAlphabet () != null ? dialog.getAlphabet ()
                      .toString () : dialog.epsilon.toString () );

              GraphConstants.setLineEnd ( newEdge.getAttributes (),
                  GraphConstants.ARROW_CLASSIC );
              GraphConstants.setEndFill ( newEdge.getAttributes (), true );

              MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                  newEdge, MachinePanel.this.firstState.getChildAt ( 0 ),
                  target.getChildAt ( 0 ) );
              dialog.dispose ();
            }

          }
          else
          {
            target = createStateView ( e.getPoint ().x, e.getPoint ().y, null, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, null, false, false );
            MachinePanel.statecount++ ;
            MachinePanel.this.graph.getGraphLayoutCache ().insert ( target );
            TransitionDialog dialog = new TransitionDialog (
                MachinePanel.this.parent, MachinePanel.this.alphabet );
            dialog.show ();
            if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
            {
              // TODO
              Transition newTransition;
              if ( dialog.getAlphabet () == null )
                newTransition = new Transition ( MachinePanel.this.alphabet,
                    MachinePanel.this.firstState.getState (), target
                        .getState () );
              else
                newTransition = new Transition ( MachinePanel.this.alphabet,
                    MachinePanel.this.firstState.getState (), target
                        .getState (), dialog.getAlphabet ().getSymbols () );
              DefaultEdge newEdge = new DefaultTransitionView ( newTransition,
                  dialog.getAlphabet () != null ? dialog.getAlphabet ()
                      .toString () : dialog.epsilon.toString () );

              GraphConstants.setLineEnd ( newEdge.getAttributes (),
                  GraphConstants.ARROW_CLASSIC );
              GraphConstants.setEndFill ( newEdge.getAttributes (), true );

              MachinePanel.this.graph.getGraphLayoutCache ().insertEdge (
                  newEdge, MachinePanel.this.firstState.getChildAt ( 0 ),
                  target.getChildAt ( 0 ) );
              dialog.dispose ();
            }

          }

          MachinePanel.this.firstState = null;
          MachinePanel.this.tmpTransition = null;
          MachinePanel.this.tmpState = null;
          MachinePanel.this.dragged = false;
        }
        catch ( TransitionSymbolNotInAlphabetException exc )
        {
          /*
           * NOTICE This exception is thrown if a symbol should be added to a
           * transition, but is not in the alphabet of the transition.
           */
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( TransitionSymbolOnlyOneTimeException exc )
        {
          /*
           * NOTICE This exception is thrown if a symbol should be added to a
           * transition, but is already in the transition.
           */
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( ClassCastException exc )
        {
          // Nothint to do here
        }
      }

    };

    this.transitionMove = new MouseMotionAdapter ()
    {

      @Override
      public void mouseDragged ( MouseEvent e )
      {
        int x, y;
        if ( MachinePanel.this.firstState == null )
        {
          MachinePanel.this.dragged = true;
          MachinePanel.this.firstState = ( DefaultStateView ) MachinePanel.this.graph
              .getFirstCellForLocation ( e.getPoint ().getX (), e.getPoint ()
                  .getY () );
          MachinePanel.this.firstStatePosition = e.getPoint ();
        }

        else
        {
          // Remove old tmp state and transition
          MachinePanel.this.model.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          x = MachinePanel.this.firstStatePosition.x < e.getX () ? e.getX () - 5
              : e.getX () + 5;
          y = MachinePanel.this.firstStatePosition.y < e.getY () ? e.getY () - 3
              : e.getY () + 10;
          MachinePanel.this.tmpState = createStateView ( x, y );
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


      /**
       * Invoked when the mouse button has been moved on a component (with no
       * buttons no down).
       */
      @Override
      public void mouseMoved ( MouseEvent e )
      {
        int x, y;

        if ( MachinePanel.this.firstState != null )
        {
          // Remove old tmp state and transition
          MachinePanel.this.model.remove ( new Object []
          { MachinePanel.this.tmpState, MachinePanel.this.tmpTransition } );
          x = MachinePanel.this.firstStatePosition.x < e.getX () ? e.getX () - 5
              : e.getX () + 5;
          y = MachinePanel.this.firstStatePosition.y < e.getY () ? e.getY () - 3
              : e.getY () + 10;
          MachinePanel.this.tmpState = createStateView ( x, y );
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

    this.start = new MouseAdapter ()
    {

      /**
       * Invoked when the mouse button has been clicked (pressed and released)
       * on a component.
       */
      @Override
      public void mouseClicked ( MouseEvent e )
      {
        if ( e.getButton () != MouseEvent.BUTTON1 )
          return;
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, null, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, Color.green, true, false ) );
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
        if ( e.getButton () != MouseEvent.BUTTON1 )
          return;
        MachinePanel.this.graph.getGraphLayoutCache ().insert (
            createStateView ( e.getPoint ().x, e.getPoint ().y, null, "Z" //$NON-NLS-1$
                + MachinePanel.statecount, null, false, true ) );
        MachinePanel.statecount++ ;
      }
    };
  }


  private void createPopupMenu ()
  {
    System.err.println ( "standard" );
  }


  private TransitionPopupMenu createTransitionPopupMenu (
      DefaultTransitionView pTransition )
  {
    return new TransitionPopupMenu ( this.model, pTransition );
  }


  private StatePopupMenu createStatePopupMenu ( DefaultStateView pState )
  {
    return new StatePopupMenu ( this.graph, this.model, pState );
  }
}
