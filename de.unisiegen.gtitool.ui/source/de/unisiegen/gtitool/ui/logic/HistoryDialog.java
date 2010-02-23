package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Transition.TransitionType;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.history.HistoryPath;
import de.unisiegen.gtitool.ui.history.HistoryPathPart;
import de.unisiegen.gtitool.ui.history.HistoryPathTableCellRenderer;
import de.unisiegen.gtitool.ui.history.TransitionSymbolPair;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.HistoryDialogForm;


/**
 * The {@link HistoryDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryDialog implements LogicClass < HistoryDialogForm >
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( HistoryDialog.class );


  /**
   * The max calculating step.
   */
  private static final int MAX_CALCULATING_STEP = 10000;


  /**
   * The current calculating step.
   */
  private int calculatingStep = -1;


  /**
   * The {@link TableColumnModel}.
   */
  private DefaultTableColumnModel columnModel;


  /**
   * The {@link HistoryDialogForm}.
   */
  private HistoryDialogForm gui;


  /**
   * The {@link TableModel}.
   */
  private DefaultTableModel historyModel;


  /**
   * The {@link HistoryPath} list.
   */
  private ArrayList < HistoryPath > historyPathList;


  /**
   * The {@link StateMachine}.
   */
  private StateMachine machine;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The remaining {@link HistoryPathPart} list.
   */
  private ArrayList < HistoryPathPart > remainingHistoryPathList = new ArrayList < HistoryPathPart > ();


  /**
   * The {@link StateMachinePanel}.
   */
  private StateMachinePanel machinePanel;


  /**
   * The complete readed {@link Symbol} list.
   */
  private ArrayList < Symbol > completeReadedSymbolList;


  /**
   * Allocates a new {@link HistoryDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machine The {@link StateMachine}.
   * @param machinePanel The {@link StateMachinePanel}.
   */
  public HistoryDialog ( JFrame parent, StateMachine machine,
      StateMachinePanel machinePanel )
  {
    logger.debug ( "HistoryDialog", "allocate a new history dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.parent = parent;
    this.machine = machine;
    this.machinePanel = machinePanel;

    this.historyPathList = new ArrayList < HistoryPath > ();

    this.gui = new HistoryDialogForm ( this, parent );

    // Model
    this.historyModel = new DefaultTableModel ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -5238984692740682487L;


      @Override
      public boolean isCellEditable ( @SuppressWarnings ( "unused" ) int row,
          @SuppressWarnings ( "unused" ) int column )
      {
        return false;
      }
    };

    this.historyModel.addColumn ( "history" ); //$NON-NLS-1$

    this.completeReadedSymbolList = new ArrayList < Symbol > ();
    ArrayList < Symbol > inputList = new ArrayList < Symbol > ();
    try
    {
      this.completeReadedSymbolList.addAll ( this.machine.getReadedSymbols () );
    }
    catch ( Exception exc )
    {
      // Do nothing
    }
    inputList.addAll ( this.completeReadedSymbolList );

    for ( State current : this.machine.getState () )
    {
      if ( current.isActive () )
      {
        HistoryPathPart pathPart = new HistoryPathPart (
            new ArrayList < TransitionSymbolPair > (), inputList, current,
            this.completeReadedSymbolList );
        this.remainingHistoryPathList.add ( pathPart );
      }
    }

    calculate ();

    Collections.sort ( this.historyPathList );

    for ( HistoryPath current : this.historyPathList )
    {
      this.historyModel.addRow ( new Object []
      { current } );
    }

    // ColumnModel
    this.columnModel = new DefaultTableColumnModel ();
    TableColumn historyColumn = new TableColumn ( 0 );
    historyColumn.setHeaderValue ( Messages
        .getString ( "HistoryDialog.HistoryColumn" ) ); //$NON-NLS-1$
    historyColumn.setCellRenderer ( new HistoryPathTableCellRenderer () );
    this.columnModel.addColumn ( historyColumn );

    this.gui.jGTITableHistory.setModel ( this.historyModel );
    this.gui.jGTITableHistory.setColumnModel ( this.columnModel );
    this.gui.jGTITableHistory.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableHistory.getTableHeader ().setResizingAllowed ( false );
  }


  /**
   * Calculates the list of history paths.
   */
  private final void calculate ()
  {
    this.calculatingStep++ ;
    if ( this.remainingHistoryPathList.size () == 0 )
    {
      return;
    }

    HistoryPathPart path = this.remainingHistoryPathList.remove ( 0 );

    if ( this.calculatingStep >= MAX_CALCULATING_STEP )
    {
      logger.error ( "calculate", "max calculating step reached" ); //$NON-NLS-1$ //$NON-NLS-2$
      return;
    }

    ArrayList < TransitionSymbolPair > transitionList = path
        .getTransitionList ();
    ArrayList < Symbol > readedSymbolList = path.getReadedSymbolList ();

    State state;
    if ( path.getState () == null )
    {
      state = transitionList.get ( transitionList.size () - 1 ).getFirst ()
          .getStateBegin ();
    }
    else
    {
      state = path.getState ();
    }

    if ( state.isStartState () && readedSymbolList.isEmpty () )
    {
      this.historyPathList.add ( getHistoryPath ( transitionList ) );

      calculate ();
      return;
    }

    for ( Transition currentTransition : state.getTransitionEnd () )
    {
      if ( currentTransition.getTransitionType ().equals (
          TransitionType.EPSILON_ONLY ) )
      {
        ArrayList < TransitionSymbolPair > newTransitionList = new ArrayList < TransitionSymbolPair > ();
        newTransitionList.addAll ( transitionList );
        Symbol epsilonSymbol = null;
        for ( Symbol current : currentTransition )
        {
          if ( current.isEpsilon () )
          {
            epsilonSymbol = current;
            break;
          }
        }
        newTransitionList.add ( new TransitionSymbolPair ( currentTransition,
            epsilonSymbol ) );

        ArrayList < Symbol > newReadedSymbolList = new ArrayList < Symbol > ();
        newReadedSymbolList.addAll ( readedSymbolList );

        HistoryPathPart newPathPart = new HistoryPathPart ( newTransitionList,
            newReadedSymbolList, null, this.completeReadedSymbolList );
        cycleDetection ( newPathPart );
      }
      else if ( currentTransition.getTransitionType ().equals (
          TransitionType.EPSILON_SYMBOL ) )
      {
        // epsilon transition handling
        ArrayList < TransitionSymbolPair > newTransitionList = new ArrayList < TransitionSymbolPair > ();
        newTransitionList.addAll ( transitionList );

        Symbol epsilonSymbol = null;
        for ( Symbol current : currentTransition )
        {
          if ( current.isEpsilon () )
          {
            epsilonSymbol = current;
            break;
          }
        }
        newTransitionList.add ( new TransitionSymbolPair ( currentTransition,
            epsilonSymbol ) );

        ArrayList < Symbol > newReadedSymbolList = new ArrayList < Symbol > ();
        newReadedSymbolList.addAll ( readedSymbolList );

        HistoryPathPart newPathPart = new HistoryPathPart ( newTransitionList,
            newReadedSymbolList, null, this.completeReadedSymbolList );
        cycleDetection ( newPathPart );

        // symbol transition handling
        if ( ( readedSymbolList.size () > 0 )
            && currentTransition.contains ( readedSymbolList
                .get ( readedSymbolList.size () - 1 ) ) )
        {
          Symbol currentSymbol = readedSymbolList.get ( readedSymbolList
              .size () - 1 );

          newTransitionList = new ArrayList < TransitionSymbolPair > ();
          newTransitionList.addAll ( transitionList );
          newTransitionList.add ( new TransitionSymbolPair ( currentTransition,
              currentSymbol ) );

          newReadedSymbolList = new ArrayList < Symbol > ();
          newReadedSymbolList.addAll ( readedSymbolList );
          newReadedSymbolList.remove ( newReadedSymbolList.size () - 1 );

          newPathPart = new HistoryPathPart ( newTransitionList,
              newReadedSymbolList, null, this.completeReadedSymbolList );
          cycleDetection ( newPathPart );
        }
      }
      else if ( ( readedSymbolList.size () > 0 )
          && currentTransition.contains ( readedSymbolList
              .get ( readedSymbolList.size () - 1 ) ) )
      {
        Symbol currentSymbol = readedSymbolList
            .get ( readedSymbolList.size () - 1 );

        ArrayList < TransitionSymbolPair > newTransitionList = new ArrayList < TransitionSymbolPair > ();
        newTransitionList.addAll ( transitionList );
        newTransitionList.add ( new TransitionSymbolPair ( currentTransition,
            currentSymbol ) );

        ArrayList < Symbol > newReadedSymbolList = new ArrayList < Symbol > ();
        newReadedSymbolList.addAll ( readedSymbolList );
        newReadedSymbolList.remove ( newReadedSymbolList.size () - 1 );

        HistoryPathPart newPathPart = new HistoryPathPart ( newTransitionList,
            newReadedSymbolList, null, this.completeReadedSymbolList );
        cycleDetection ( newPathPart );
      }
    }

    calculate ();
  }


  /**
   * Detects cycles in the given {@link HistoryPathPart}.
   * 
   * @param historyPathPart The {@link HistoryPathPart}.
   */
  private final void cycleDetection ( HistoryPathPart historyPathPart )
  {
    if ( historyPathPart.isCycleDetected () )
    {
      logger.debug ( "cycleDetection", "cycle detected: " + historyPathPart ); //$NON-NLS-1$//$NON-NLS-2$
    }
    else
    {
      this.remainingHistoryPathList.add ( historyPathPart );
    }
  }


  /**
   * Returns the columnModel.
   * 
   * @return The columnModel.
   * @see #columnModel
   */
  public DefaultTableColumnModel getColumnModel ()
  {
    return this.columnModel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final HistoryDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the historyModel.
   * 
   * @return The historyModel.
   * @see #historyModel
   */
  public DefaultTableModel getHistoryModel ()
  {
    return this.historyModel;
  }


  /**
   * Returns the {@link HistoryPath} of the given {@link Transition} list.
   * 
   * @param transitionList The {@link Transition} list.
   * @return The {@link HistoryPath} of the given {@link Transition} list.
   */
  private final HistoryPath getHistoryPath (
      ArrayList < TransitionSymbolPair > transitionList )
  {
    HistoryPath historyPath = new HistoryPath ();

    if ( transitionList.size () == 0 )
    {
      State startState = null;
      for ( State currentState : this.machine.getState () )
      {
        if ( currentState.isStartState () )
        {
          startState = currentState;
          break;
        }
      }

      historyPath.setStartState ( startState );
    }
    else
    {
      for ( int i = transitionList.size () - 1 ; i >= 0 ; i-- )
      {
        Transition currentTransition = transitionList.get ( i ).getFirst ();
        historyPath.add ( currentTransition, transitionList.get ( i )
            .getSecond () );
      }
    }

    return historyPath;
  }


  /**
   * Returns the machinePanel.
   * 
   * @return The machinePanel.
   * @see #machinePanel
   */
  public StateMachinePanel getMachinePanel ()
  {
    return this.machinePanel;
  }


  /**
   * Closes the {@link HistoryDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handleClose", "handle close" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handle print action.
   */
  public void handlePrint ()
  {
    PrintDialog dialog = new PrintDialog ( this.parent, this );
    dialog.show ();
  }


  /**
   * Shows the {@link HistoryDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the history dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
