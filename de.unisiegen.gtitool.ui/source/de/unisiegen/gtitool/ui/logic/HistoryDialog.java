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
   * Allocates a new {@link HistoryDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machine The {@link Machine}.
   */
  public HistoryDialog ( JFrame parent, Machine machine )
  {
    logger.debug ( "HistoryDialog", "allocate a new about dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new HistoryDialogForm ( this, parent );

    // History
    ArrayList < HistoryItem > historyItemList = machine.getHistory ();

    for ( HistoryItem current : historyItemList )
    {
      System.out.println ( current );
    }

    HistoryPath historyPath = new HistoryPath ();
    ArrayList < ObjectPair < Transition, Symbol >> list = new ArrayList < ObjectPair < Transition, Symbol >> ();

    for ( State activeState : machine.getActiveState () )
    {
      State currentState = activeState;
      for ( int i = historyItemList.size () - 1 ; i >= 0 ; i-- )
      {
        loopTransition : for ( Transition currentTransition : historyItemList
            .get ( i ).getTransitionSet () )
        {
          if ( currentTransition.getStateEnd () == currentState )
          {
            ArrayList < Symbol > symbols = historyItemList.get ( i )
                .getSymbolSet ();
            if ( symbols.size () > 0 )
            {
              list.add ( 0, new ObjectPair < Transition, Symbol > (
                  currentTransition, symbols.get ( 0 ) ) );
            }
            else
            {
              list.add ( 0, new ObjectPair < Transition, Symbol > (
                  currentTransition, null ) );
            }
            currentState = currentTransition.getStateBegin ();
            break loopTransition;
          }
        }
      }
    }

    for ( ObjectPair < Transition, Symbol > current : list )
    {
      System.err.println ( current );

      historyPath.add ( current.getFirst ().getStateBegin (), current
          .getFirst (), current.getFirst ().getStateEnd (), current
          .getSecond () );

    }

    // The old source code

    // for ( int i = 0 ; i < historyItemList.size () ; i++ )
    // {
    // State beginState = historyItemList.get ( i ).getStateSet ().first ();
    // Transition transition = historyItemList.get ( i ).getTransitionSet ()
    // .first ();
    // State endState;
    // if ( i == historyItemList.size () - 1 )
    // {
    // endState = machine.getActiveState ().first ();
    // }
    // else
    // {
    // endState = historyItemList.get ( i + 1 ).getStateSet ().first ();
    // }
    //
    // ArrayList < Symbol > symbols = historyItemList.get ( i ).getSymbolSet ();
    // if ( symbols.size () > 0 )
    // {
    // historyPath.add ( beginState, transition, endState, symbols.get ( 0 ) );
    // }
    // else
    // {
    // historyPath.add ( beginState, transition, endState ,null);
    // }
    // }

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
    historyModel.addRow ( new Object []
    { historyPath } );

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
    logger.debug ( "show", "show the about dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
