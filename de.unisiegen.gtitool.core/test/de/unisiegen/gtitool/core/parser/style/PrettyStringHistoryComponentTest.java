package de.unisiegen.gtitool.core.parser.style;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.HistoryItem;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringHistoryTableCellRenderer;


/**
 * The test class of the {@link PrettyStringHistoryComponent}.
 * 
 * @author Christian Fehler
 * @version $Id: PrettyStringHistoryComponentTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class PrettyStringHistoryComponentTest
{

  // TODOCF
  // History:
  // [z0]
  // [{1}, {1}]
  // [1, 1]
  // Current:
  // [z1, z2]

  // History:
  // [z0]
  // [{1}, {1}]
  // [1, 1]
  // [z1, z2]
  // [{0, 1}]
  // [0]
  // Current:
  // [z2]

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    try
    {
      // Item0
      TreeSet < State > item0States = new TreeSet < State > ();
      item0States.add ( new DefaultState ( "z0" ) ); //$NON-NLS-1$
      TreeSet < Transition > item0Transitions = new TreeSet < Transition > ();
      item0Transitions.add ( new DefaultTransition ( new DefaultWord (),
          new DefaultWord (), new DefaultSymbol ( "a" ), new DefaultSymbol ( //$NON-NLS-1$
              "b" ) ) ); //$NON-NLS-1$
      ArrayList < Symbol > item0Symbols = new ArrayList < Symbol > ();
      item0Symbols.add ( new DefaultSymbol ( "a" ) ); //$NON-NLS-1$
      item0Symbols.add ( new DefaultSymbol ( "b" ) ); //$NON-NLS-1$
      Stack item0Stack = new DefaultStack ();
      HistoryItem item0 = new HistoryItem ( item0States, item0Transitions,
          item0Symbols, item0Stack );

      // Item1
      TreeSet < State > item1States = new TreeSet < State > ();
      item1States.add ( new DefaultState ( "z1" ) ); //$NON-NLS-1$
      TreeSet < Transition > item1Transitions = new TreeSet < Transition > ();
      item1Transitions.add ( new DefaultTransition ( new DefaultWord (),
          new DefaultWord (), new DefaultSymbol ( "a" ), new DefaultSymbol ( //$NON-NLS-1$
              "b" ) ) ); //$NON-NLS-1$
      ArrayList < Symbol > item1Symbols = new ArrayList < Symbol > ();
      item1Symbols.add ( new DefaultSymbol ( "b" ) ); //$NON-NLS-1$
      Stack item1Stack = new DefaultStack ();
      HistoryItem item1 = new HistoryItem ( item1States, item1Transitions,
          item1Symbols, item1Stack );

      // Item2
      TreeSet < State > item2States = new TreeSet < State > ();
      item2States.add ( new DefaultState ( "z2" ) ); //$NON-NLS-1$
      TreeSet < Transition > item2Transitions = new TreeSet < Transition > ();
      item2Transitions.add ( new DefaultTransition ( new DefaultWord (),
          new DefaultWord (), new DefaultSymbol ( "a" ), new DefaultSymbol ( //$NON-NLS-1$
              "b" ) ) ); //$NON-NLS-1$
      ArrayList < Symbol > item2Symbols = new ArrayList < Symbol > ();
      item2Symbols.add ( new DefaultSymbol ( "b" ) ); //$NON-NLS-1$
      Stack item2Stack = new DefaultStack ();
      HistoryItem item2 = new HistoryItem ( item2States, item2Transitions,
          item2Symbols, item2Stack );

      // History list
      ArrayList < HistoryItem > historyItemList = new ArrayList < HistoryItem > ();
      historyItemList.add ( item0 );
      historyItemList.add ( item1 );
      historyItemList.add ( item2 );

      // Model
      DefaultTableModel model = new DefaultTableModel ();
      model.addColumn ( "History" ); //$NON-NLS-1$
      model.addRow ( new Object []
      { historyItemList } );

      // ColumnModel
      DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();
      TableColumn historyColumn = new TableColumn ( 0 );
      historyColumn.setHeaderValue ( "History" ); //$NON-NLS-1$
      historyColumn
          .setCellRenderer ( new PrettyStringHistoryTableCellRenderer () );
      columnModel.addColumn ( historyColumn );

      JFrame jFrame = new JFrame ( "PrettyStringHistoryComponentTest" ); //$NON-NLS-1$
      JTable jTable = new JTable ()
      {

        /**
         * The serial version uid.
         */
        private static final long serialVersionUID = -585804705346618616L;


        @Override
        public boolean isCellEditable ( @SuppressWarnings ( "unused" )
        int x, @SuppressWarnings ( "unused" )
        int y )
        {
          return false;
        }
      };
      jTable.setModel ( model );
      jTable.setColumnModel ( columnModel );

      jFrame.add ( jTable );
      jFrame.setBounds ( 0, 0, 800, 600 );
      jFrame.setLocationRelativeTo ( null );
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
      jFrame.setVisible ( true );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
