package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.HistoryItem;
import de.unisiegen.gtitool.core.machines.HistoryPath;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.style.renderer.HistoryPathTableCellRenderer;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.HistoryDialogForm;


/**
 * The {@link HistoryDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( HistoryDialog.class );


  /**
   * The {@link HistoryDialogForm}.
   */
  private HistoryDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Machine}.
   */
  private Machine machine;


  /**
   * The {@link HistoryPath} list.
   */
  private ArrayList < ArrayList < ObjectPair < Transition, Symbol > >> historyPathList = new ArrayList < ArrayList < ObjectPair < Transition, Symbol >> > ();


  /**
   * Allocates a new {@link HistoryDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machine The {@link Machine}.
   */
  public HistoryDialog ( JFrame parent, Machine machine )
  {
    logger.debug ( "HistoryDialog", "allocate a new history dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.parent = parent;
    this.machine = machine;

    this.gui = new HistoryDialogForm ( this, parent );

    // Model
    DefaultTableModel historyModel = new DefaultTableModel ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -5238984692740682487L;


      @Override
      public boolean isCellEditable ( @SuppressWarnings ( "unused" )
      int row, @SuppressWarnings ( "unused" )
      int column )
      {
        return false;
      }
    };

    historyModel.addColumn ( "history" ); //$NON-NLS-1$

    ArrayList < HistoryPath > historyPathLocalList = new ArrayList < HistoryPath > ();

    while ( true )
    {
      getNextHistoryPath ();
      HistoryPath historyPath = new HistoryPath ();
      for ( ObjectPair < Transition, Symbol > currentPair : this.historyPathList
          .get ( this.historyPathList.size () - 1 ) )
      {
        historyPath.add ( currentPair.getFirst ().getStateBegin (), currentPair
            .getFirst (), currentPair.getFirst ().getStateEnd (), currentPair
            .getSecond () );
      }
      if ( historyPathLocalList.contains ( historyPath ) )
      {
        System.err.println ( historyPath );
        break;
      }
      historyPathLocalList.add ( historyPath );
    }

    for ( HistoryPath current : historyPathLocalList )
    {
      historyModel.addRow ( new Object []
      { current } );
    }

    // ColumnModel
    DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();
    TableColumn historyColumn = new TableColumn ( 0 );
    historyColumn.setHeaderValue ( Messages
        .getString ( "HistoryDialog.HistoryColumn" ) ); //$NON-NLS-1$
    historyColumn.setCellRenderer ( new HistoryPathTableCellRenderer () );
    columnModel.addColumn ( historyColumn );

    this.gui.jGTITableHistory.setModel ( historyModel );
    this.gui.jGTITableHistory.setColumnModel ( columnModel );
    this.gui.jGTITableHistory.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableHistory.getTableHeader ().setResizingAllowed ( false );
  }


  /**
   * Adds the next {@link HistoryPath}.
   */
  private final void getNextHistoryPath ()
  {
    ArrayList < ObjectPair < Transition, Symbol >> list = new ArrayList < ObjectPair < Transition, Symbol >> ();

    ArrayList < HistoryItem > historyItemList = this.machine.getHistory ();

    for ( State activeState : this.machine.getActiveState () )
    {
      State currentState = activeState;
      for ( int i = historyItemList.size () - 1 ; i >= 0 ; i-- )
      {
        loopTransition : for ( Transition currentTransition : historyItemList
            .get ( i ).getTransitionSet () )
        {
          if ( currentTransition.getStateEnd () == currentState )
          {
            ArrayList < ObjectPair < Transition, Symbol >> tmpList = new ArrayList < ObjectPair < Transition, Symbol >> ();
            tmpList.addAll ( list );

            ArrayList < Symbol > symbols = historyItemList.get ( i )
                .getSymbolSet ();
            if ( symbols.size () > 0 )
            {
              tmpList.add ( 0, new ObjectPair < Transition, Symbol > (
                  currentTransition, symbols.get ( 0 ) ) );
            }
            else
            {
              tmpList.add ( 0, new ObjectPair < Transition, Symbol > (
                  currentTransition, null ) );
            }

            boolean equals = this.historyPathList.size () > 0;
            loopEquals : for ( ArrayList < ObjectPair < Transition, Symbol > > current : this.historyPathList )
            {
              ArrayList < ObjectPair < Transition, Symbol > > currentSmall = new ArrayList < ObjectPair < Transition, Symbol > > ();

              for ( int k = 0 ; k < tmpList.size () ; k++ )
              {
                currentSmall.add ( current.get ( k
                    + ( current.size () - tmpList.size () ) ) );
              }

              if ( currentSmall.size () == tmpList.size () )
              {
                for ( int k = 0 ; k < currentSmall.size () ; k++ )
                {
                  if ( ! ( currentSmall.get ( k ).getFirst () == tmpList.get (
                      k ).getFirst () ) )
                  {
                    equals = false;
                    break loopEquals;
                  }
                }
              }
              else
              {
                equals = false;
                break loopEquals;
              }
            }

            if ( equals )
            {
              continue loopTransition;
            }

            list.clear ();
            list.addAll ( tmpList );

            currentState = currentTransition.getStateBegin ();
            break loopTransition;
          }
        }
      }
    }

    this.historyPathList.add ( list );
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
