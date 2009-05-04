package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.PrintDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.swing.JGTITable;


/**
 * The {@link PrintDialog}.
 * 
 * @author Benjamin Mies
 * @version $Id: PrintDialog.java 1159 2008-07-17 12:50:34Z fehler $
 */
public final class PrintDialog implements LogicClass < PrintDialogForm >,
    Printable
{

  /**
   * The {@link PrintService} list cell renderer.
   * 
   * @author Benjamin Mies
   */
  public class PrintServiceListCellRenderer extends DefaultListCellRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 4446455799980336206L;


    /**
     * Allocates a new {@link PrintServiceListCellRenderer}.
     */
    public PrintServiceListCellRenderer ()
    {
      super ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultListCellRenderer#getListCellRendererComponent(JList, Object,
     *      int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent ( JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus )
    {
      super.getListCellRendererComponent ( list, value, index, isSelected,
          cellHasFocus );
      setText ( ( ( PrintService ) value ).getName () );
      return this;
    }
  }


  /**
   * The background {@link Color}.
   */
  private static final Color BACKGROUND = new Color ( 227, 227, 227 );


  /**
   * The border offset.
   */
  private static final int BORDER_OFFSET = 2;


  /**
   * The border width.
   */
  private static final int BORDER_WIDTH = 2;


  /**
   * The {@link Font} size.
   */
  private static final int FONT_SIZE = 7;


  /**
   * The header centered flag.
   */
  private static final boolean HEADER_CENTERED = false;


  /**
   * The row height.
   */
  private static final int ROW_HEIGHT = 10;


  /**
   * The header height.
   */
  private static final int HEADER_HEIGHT = ( int ) ( ROW_HEIGHT * 1.5 );


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( PrintDialog.class );


  /**
   * The {@link ConvertMachineDialog}.
   */
  private ConvertMachineDialog convertMachineDialog;


  /**
   * The {@link ConvertRegexToMachineDialog}.
   */
  private ConvertRegexToMachineDialog convertRegexToMachineDialog;


  /**
   * The {@link ConvertGrammarDialog}.
   */
  private ConvertGrammarDialog convertGrammarDialog;


  /**
   * The {@link GrammarPanel}.
   */
  private GrammarPanel grammarPanel;


  /**
   * The {@link PrintDialogForm}.
   */
  private PrintDialogForm gui;


  /**
   * The header {@link Font}.
   */
  private Font headerFont;


  /**
   * The {@link HistoryDialog}.
   */
  private HistoryDialog historyDialog;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The bottom margin.
   */
  private int marginBottom;


  /**
   * The left margin.
   */
  private int marginLeft;


  /**
   * The right margin.
   */
  private int marginRight;


  /**
   * The top margin.
   */
  private int marginTop;


  /**
   * The {@link MinimizeMachineDialog}.
   */
  private MinimizeMachineDialog minimizeMachineDialog;


  /**
   * The normal {@link Font}.
   */
  private Font normalFont;


  /**
   * The page count.
   */
  private int pageCount;


  /**
   * The page height.
   */
  private int pageHeight;


  /**
   * The page width.
   */
  private int pageWidth;


  /**
   * The parentFrame {@link JFrame}.
   */
  private JFrame parentFrame;


  /**
   * The parentFrame {@link JDialog}.
   */
  private JDialog parentDialog;


  /**
   * The {@link HashMap} which contains the printed rows.
   */
  private HashMap < Integer, Integer > printedRows = new HashMap < Integer, Integer > ();


  /**
   * The {@link ReachableStatesDialog}.
   */
  private ReachableStatesDialog reachableStatesDialog;


  /**
   * The {@link JGTITable}.
   */
  private JGTITable table = null;


  /**
   * The {@link TableColumnModel}.
   */
  private TableColumnModel tableColumnModel = null;


  /**
   * The {@link TableModel}.
   */
  private TableModel tableModel = null;


  /**
   * The {@link RegexPanel}
   */
  private RegexPanel regexPanel;


  /**
   * The {@link RegexPanel}
   */
  private TextWindow textWindow;


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param convertMachineDialog The {@link ConvertMachineDialog}.
   */
  public PrintDialog ( JFrame parent, ConvertMachineDialog convertMachineDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.convertMachineDialog = convertMachineDialog;

    hideChooseComponents ();
    this.gui.jGTIPanelConvertMachine.setVisible ( true );

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param convertRegexToMachineDialog The {@link ConvertMachineDialog}.
   */
  public PrintDialog ( JFrame parent,
      ConvertRegexToMachineDialog convertRegexToMachineDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.convertRegexToMachineDialog = convertRegexToMachineDialog;

    hideChooseComponents ();
    this.gui.jGTIPanelConvertMachine.setVisible ( true );

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param convertGrammarDialog The {@link ConvertGrammarDialog}.
   */
  public PrintDialog ( JFrame parent, ConvertGrammarDialog convertGrammarDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.convertGrammarDialog = convertGrammarDialog;

    hideChooseComponents ();
    this.gui.jGTIPanelConvertMachine.setVisible ( true );

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param grammarPanel The {@link GrammarPanel}.
   */
  public PrintDialog ( JFrame parent, GrammarPanel grammarPanel )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.grammarPanel = grammarPanel;

    hideChooseComponents ();

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param historyDialog The {@link HistoryDialog}.
   */
  public PrintDialog ( JFrame parent, HistoryDialog historyDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.historyDialog = historyDialog;

    hideChooseComponents ();

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   */
  public PrintDialog ( JFrame parent, MachinePanel machinePanel )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.machinePanel = machinePanel;

    hideChooseComponents ();
    this.gui.jGTIPanelMachine.setVisible ( true );

    if ( !this.machinePanel.getMachine ().getMachineType ().equals (
        MachineType.PDA ) )
    {
      if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.SHOW ) )
      {
        // do nothing
      }
      else if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.HIDE ) )
      {
        this.gui.jGTIRadioButtonMachinePDATable.setVisible ( false );
      }
      else
      {
        throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$
      }
    }

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param minimizeMachineDialog The {@link MinimizeMachineDialog}.
   */
  public PrintDialog ( JFrame parent,
      MinimizeMachineDialog minimizeMachineDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.minimizeMachineDialog = minimizeMachineDialog;

    hideChooseComponents ();
    this.gui.jGTIPanelMinimizeMachine.setVisible ( true );
    this.gui.jGTIRadioButtonMinimizeMachineMinimizedGraph
        .setVisible ( this.minimizeMachineDialog.isEndReached () );

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param reachableStatesDialog The {@link ReachableStatesDialog}.
   */
  public PrintDialog ( JFrame parent,
      ReachableStatesDialog reachableStatesDialog )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.reachableStatesDialog = reachableStatesDialog;

    hideChooseComponents ();
    this.gui.jGTIPanelReachableStates.setVisible ( true );

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parentFrame {@link JFrame}.
   * @param regexPanel The {@link RegexPanel}.
   */
  public PrintDialog ( JFrame parent, RegexPanel regexPanel )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parentFrame = parent;
    this.gui = new PrintDialogForm ( this, parent );
    this.regexPanel = regexPanel;

    hideChooseComponents ();

    initialize ();
  }


  /**
   * Allocates a new {@link PrintDialog}.
   * 
   * @param parent The parent {@link Window}.
   * @param textWindow The {@link TextWindow}.
   */
  public PrintDialog ( Window parent, TextWindow textWindow )
  {
    logger.debug ( "PrintDialog", "allocate a new print dialog" ); //$NON-NLS-1$//$NON-NLS-2$
    if ( parent instanceof JFrame )
    {
      this.parentFrame = (JFrame)parent;
      this.gui = new PrintDialogForm ( this, ( JFrame ) parent );
    }
    else
    {
      this.parentDialog = (JDialog)parent;
      this.gui = new PrintDialogForm ( this, ( JDialog ) parent );
    }
    this.textWindow = textWindow;

    initialize ();
  }


  /**
   * Returns the calculated column widths.
   * 
   * @return The calculated column widths.
   */
  private final int [] calculateColumnWidth ()
  {
    int printWidth = this.pageWidth - this.marginLeft - this.marginRight;
    int [] columnWidth = new int [ this.tableColumnModel.getColumnCount () ];

    for ( int i = 0 ; i < this.tableColumnModel.getColumnCount () ; i++ )
    {
      TableColumn tableColumn = this.tableColumnModel.getColumn ( i );
      if ( tableColumn.getMaxWidth () < Integer.MAX_VALUE )
      {
        columnWidth [ i ] = tableColumn.getMaxWidth ();
      }
      else
      {
        columnWidth [ i ] = 0;
      }
    }

    int sum = 0;
    for ( int element : columnWidth )
    {
      sum += element;
    }

    int variableColumns = 0;
    for ( int element : columnWidth )
    {
      if ( element == 0 )
      {
        variableColumns++ ;
      }
    }

    while ( ( sum + variableColumns * 50 ) > printWidth )
    {
      sum = 0;
      for ( int i = 0 ; i < columnWidth.length ; i++ )
      {
        columnWidth [ i ] = columnWidth [ i ] * 9 / 10;
        sum += columnWidth [ i ];
      }
    }

    int variableColumnsWidth = ( printWidth - sum ) / variableColumns;
    for ( int i = 0 ; i < columnWidth.length ; i++ )
    {
      if ( columnWidth [ i ] == 0 )
      {
        columnWidth [ i ] = variableColumnsWidth;
      }
    }
    return columnWidth;
  }


  /**
   * Draws the border.
   * 
   * @param g The {@link Graphics}.
   * @param y The y position.
   */
  private final void drawBackground ( Graphics g, int y )
  {
    g.setColor ( BACKGROUND );
    g.fillRect ( this.marginLeft, y, g.getClipBounds ().width - this.marginLeft
        - this.marginRight, ROW_HEIGHT );
    g.setColor ( Color.BLACK );
  }


  /**
   * Draws the border.
   * 
   * @param g The {@link Graphics}.
   * @param endY The y position of the table end.
   */
  private final void drawBorder ( Graphics g, int endY )
  {
    int x1 = this.marginLeft - BORDER_OFFSET;
    int x2 = g.getClipBounds ().width - this.marginRight + BORDER_OFFSET;
    int y1 = this.marginTop - BORDER_OFFSET;
    int y2 = this.marginTop - BORDER_OFFSET + HEADER_HEIGHT;
    int y3 = endY + BORDER_OFFSET;

    drawLine ( g, x1, y1, x2, y1, BORDER_WIDTH );
    drawLine ( g, x1, y3, x2, y3, BORDER_WIDTH );
    drawLine ( g, x1, y1, x1, y3, BORDER_WIDTH );
    drawLine ( g, x2, y1, x2, y3, BORDER_WIDTH );
    drawLine ( g, x1, y2, x2, y2, BORDER_WIDTH );
  }


  /**
   * Draws the border.
   * 
   * @param g The {@link Graphics}.
   * @param columnWidth The column widths.
   */
  private final void drawHeader ( Graphics g, int [] columnWidth )
  {
    g.setFont ( this.headerFont );

    int internOffset = 3;
    int x = this.marginLeft;
    int y = this.marginTop + ROW_HEIGHT - internOffset
        + ( ( HEADER_HEIGHT - ROW_HEIGHT ) / 2 );
    for ( int column = 0 ; column < this.tableColumnModel.getColumnCount () ; column++ )
    {
      TableColumn tableColumn = this.tableColumnModel.getColumn ( column );

      Object value = tableColumn.getHeaderValue ();

      String text = value == null ? "" : value.toString (); //$NON-NLS-1$
      int stringWidth = g.getFontMetrics ().stringWidth ( text );

      if ( HEADER_CENTERED )
      {
        g.drawString ( text, x + ( columnWidth [ column ] - stringWidth ) / 2,
            y );
      }
      else
      {
        g.drawString ( text, x, y );
      }
      x += columnWidth [ column ];
    }
  }


  /**
   * Draws the border.
   * 
   * @param g The {@link Graphics}.
   * @param y The y position.
   * @param row The row.
   * @param columnWidth The column widths.
   */
  private final void drawIconAndText ( Graphics g, int y, int row,
      int [] columnWidth )
  {
    g.setFont ( this.normalFont );

    int internOffset = 2;
    int x = this.marginLeft;

    for ( int column = 0 ; column < this.tableColumnModel.getColumnCount () ; column++ )
    {
      TableColumn tableColumn = this.tableColumnModel.getColumn ( column );

      int dx = x;
      int modelColumn = tableColumn.getModelIndex ();
      Object value = this.tableModel.getValueAt ( row, modelColumn );

      TableCellRenderer renderer = tableColumn.getCellRenderer ();
      boolean performNormal = true;

      if ( renderer != null )
      {
        Component component = tableColumn.getCellRenderer ()
            .getTableCellRendererComponent ( this.table, value, false, false,
                row, modelColumn );
        if ( component instanceof PrettyStringComponent )
        {
          PrettyStringComponent prettyStringComponent = ( PrettyStringComponent ) component;

          for ( PrettyToken currentToken : prettyStringComponent
              .getPrettyString () )
          {
            Font font = null;

            if ( !currentToken.isBold () && !currentToken.isItalic () )
            {
              font = g.getFont ().deriveFont ( Font.PLAIN );
            }
            else if ( currentToken.isBold () && currentToken.isItalic () )
            {
              font = g.getFont ().deriveFont ( Font.BOLD | Font.ITALIC );
            }
            else if ( currentToken.isBold () )
            {
              font = g.getFont ().deriveFont ( Font.BOLD );
            }
            else if ( currentToken.isItalic () )
            {
              font = g.getFont ().deriveFont ( Font.ITALIC );
            }

            g.setFont ( font );
            g.setColor ( currentToken.getColor () );
            char [] chars = currentToken.getChar ();
            for ( int i = 0 ; i < chars.length ; i++ )
            {
              g.drawChars ( chars, i, 1, dx, y + ROW_HEIGHT - internOffset );
              dx += g.getFontMetrics ().charWidth ( chars [ i ] );
            }
          }

          performNormal = false;
        }
      }

      if ( performNormal )
      {
        g
            .drawString (
                value == null ? "" : value.toString (), x, y + ROW_HEIGHT - internOffset ); //$NON-NLS-1$
      }
      x += columnWidth [ column ];
    }
  }


  /**
   * Draws a line.
   * 
   * @param g The {@link Graphics}.
   * @param x1 The x1.
   * @param y1 The y1.
   * @param x2 The x2.
   * @param y2 The y2.
   * @param lineWidth The line width.
   */
  private final void drawLine ( Graphics g, int x1, int y1, int x2, int y2,
      int lineWidth )
  {
    int xPoints[] = new int [ 4 ];
    int yPoints[] = new int [ 4 ];

    xPoints [ 0 ] = x1 + lineWidth - 1;
    yPoints [ 0 ] = y1 + lineWidth - 1;
    xPoints [ 1 ] = x1;
    yPoints [ 1 ] = y1;
    xPoints [ 2 ] = x2;
    yPoints [ 2 ] = y2;
    xPoints [ 3 ] = x2 + lineWidth - 1;
    yPoints [ 3 ] = y2 + lineWidth - 1;

    g.fillPolygon ( xPoints, yPoints, 4 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final PrintDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the page count.
   * 
   * @return The page count.
   */
  private final int getPageCount ()
  {
    int result = 1;
    int y = this.marginTop + HEADER_HEIGHT;

    for ( int i = 0 ; i < this.tableModel.getRowCount () ; i++ )
    {
      if ( isPageBreakNeeded ( y ) )
      {
        y = this.marginTop + HEADER_HEIGHT;
        result++ ;
      }
      y += ROW_HEIGHT;
    }
    return result;
  }


  /**
   * Handles cancel action performed.
   */
  public final void handleCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * Handles print action performed.
   */
  public final void handlePrint ()
  {
    this.gui.setVisible ( false );

    // Margin
    this.marginLeft = ( int ) ( 2.8346456693 * ( ( Number ) this.gui.jSpinnerMarginLeft
        .getValue () ).intValue () );
    this.marginRight = ( int ) ( 2.8346456693 * ( ( Number ) this.gui.jSpinnerMarginRight
        .getValue () ).intValue () );
    this.marginTop = ( int ) ( 2.8346456693 * ( ( Number ) this.gui.jSpinnerMarginTop
        .getValue () ).intValue () );
    this.marginBottom = ( int ) ( 2.8346456693 * ( ( Number ) this.gui.jSpinnerMarginBottom
        .getValue () ).intValue () );

    logger.debug ( "handlePrint", "printing" ); //$NON-NLS-1$ //$NON-NLS-2$
    if ( this.machinePanel != null )
    {
      printMachinePanel ();
    }
    else if ( this.grammarPanel != null )
    {
      printGrammarPanel ();
    }
    else if ( this.minimizeMachineDialog != null )
    {
      printMinimizeMachineDialog ();
    }
    else if ( this.convertMachineDialog != null )
    {
      printConvertMachineDialog ();
    }
    else if ( this.convertRegexToMachineDialog != null )
    {
      printConvertRegexToMachineDialog ();
    }
    else if ( this.convertGrammarDialog != null )
    {
      printConvertGrammarDialog ();
    }
    else if ( this.regexPanel != null )
    {
      printRegexPanel ();
    }
    else if ( this.historyDialog != null )
    {
      printHistoryDialog ();
    }
    else if ( this.reachableStatesDialog != null )
    {
      printReachableStatesDialog ();
    }
    else if ( this.textWindow != null )
    {
      printTextWindow ();
    }

    logger.debug ( "handlePrint", "printed" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Prints a text window
   */
  private void printTextWindow ()
  {
    PrinterJob job = PrinterJob.getPrinterJob ();

    try
    {
      PageFormat pageFormat = new PageFormat ();
      Paper paper = new Paper ();
      paper.setSize ( 8.27 * 72, 11.69 * 72 );
      paper.setImageableArea ( 0, 0, paper.getWidth (), paper.getHeight () );
      pageFormat.setPaper ( paper );
      if ( this.gui.jGTIRadioButtonPortrait.isSelected () )
      {
        pageFormat.setOrientation ( PageFormat.PORTRAIT );
      }
      else
      {
        pageFormat.setOrientation ( PageFormat.LANDSCAPE );
      }

      job.setPrintService ( ( PrintService ) this.gui.jGTIComboBoxPrinter
          .getSelectedItem () );
      job.setPrintable ( this.textWindow.getGUI ().jGTITextPaneAlgorithm
          .getPrintable ( null, null ), pageFormat );
      job.setJobName ( this.textWindow.getJobName () );
      job.print ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Hides the choose components.
   */
  private final void hideChooseComponents ()
  {
    this.gui.jGTIPanelMachine.setVisible ( false );
    this.gui.jGTIPanelConvertMachine.setVisible ( false );
    this.gui.jGTIPanelMinimizeMachine.setVisible ( false );
    this.gui.jGTIPanelReachableStates.setVisible ( false );
  }


  /**
   * Initializes this {@link PrintDialog}.
   */
  private final void initialize ()
  {
    this.normalFont = new Font ( "Dialog", Font.PLAIN, FONT_SIZE ); //$NON-NLS-1$
    this.headerFont = this.normalFont.deriveFont ( Font.BOLD );

    // printer
    PrintService [] printServices = PrintServiceLookup.lookupPrintServices (
        null, null );

    this.gui.jGTIComboBoxPrinter.setModel ( new DefaultComboBoxModel (
        printServices ) );
    this.gui.jGTIComboBoxPrinter
        .setRenderer ( new PrintServiceListCellRenderer () );

    // margin
    this.gui.jSpinnerMarginLeft.setModel ( new SpinnerNumberModel ( 20, 0, 50,
        1 ) );
    this.gui.jSpinnerMarginRight.setModel ( new SpinnerNumberModel ( 20, 0, 50,
        1 ) );
    this.gui.jSpinnerMarginTop
        .setModel ( new SpinnerNumberModel ( 20, 0, 50, 1 ) );
    this.gui.jSpinnerMarginBottom.setModel ( new SpinnerNumberModel ( 20, 0,
        50, 1 ) );
  }


  /**
   * Returns true if a page break is needed.
   * 
   * @param y The y position.
   * @return True if a page break is needed.
   */
  private final boolean isPageBreakNeeded ( int y )
  {
    return ( y + ROW_HEIGHT + this.marginBottom ) > this.pageHeight;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Printable#print(Graphics, PageFormat, int)
   */
  public final int print ( Graphics g,
      @SuppressWarnings ( "unused" ) PageFormat pageFormat, int pageIndex )
      throws PrinterException
  {
    if ( ( pageIndex < 0 ) || ( pageIndex >= this.pageCount ) )
    {
      return NO_SUCH_PAGE;
    }
    try
    {
      int [] columnWidth = calculateColumnWidth ();

      drawHeader ( g, columnWidth );

      int y = this.marginTop + HEADER_HEIGHT;

      int start = 0;
      int end = this.tableModel.getRowCount ();

      // Get the printed rows
      Integer lastPage = new Integer ( pageIndex - 1 );
      start = this.printedRows.get ( lastPage ) == null ? 0 : this.printedRows
          .get ( lastPage ).intValue ();

      for ( int row = start ; row < end ; row++ )
      {
        if ( isPageBreakNeeded ( y ) )
        {
          drawBorder ( g, y );

          // Save the printed rows
          this.printedRows
              .put ( new Integer ( pageIndex ), new Integer ( row ) );

          return PAGE_EXISTS;
        }

        if ( ( row % 2 ) == 0 )
        {
          drawBackground ( g, y );
        }

        drawIconAndText ( g, y, row, columnWidth );

        y += ROW_HEIGHT;
      }
      drawBorder ( g, y );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      throw new PrinterException ( exc.getMessage () );
    }
    return PAGE_EXISTS;
  }


  /**
   * Handles print {@link ConvertMachineDialog}.
   */
  private final void printConvertMachineDialog ()
  {

    try
    {
      if ( this.gui.jGTIRadioButtonConvertMachineOriginalGraph.isSelected () )
      {
        printJGraph ( this.convertMachineDialog.getModelOriginal ()
            .getJGTIGraph (), this.convertMachineDialog.getMachinePanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Original" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }
      else if ( this.gui.jGTIRadioButtonConvertMachineConvertedGraph
          .isSelected () )
      {
        printJGraph ( this.convertMachineDialog.getModelConverted ()
            .getJGTIGraph (), this.convertMachineDialog.getMachinePanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Converted" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }

      else if ( this.gui.jGTIRadioButtonConvertMachineTable.isSelected () )
      {
        this.tableModel = this.convertMachineDialog
            .getConvertMachineTableModel ();
        this.tableColumnModel = this.convertMachineDialog
            .getTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.convertMachineDialog.getMachinePanel ()
            .getName ()
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.ConvertMachine" ) ); //$NON-NLS-1$
      }

    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link ConvertRegexToMachineDialog}.
   */
  private final void printConvertRegexToMachineDialog ()
  {

    try
    {
      if ( this.gui.jGTIRadioButtonConvertMachineOriginalGraph.isSelected () )
      {
        printJGraph ( this.convertRegexToMachineDialog.getModelOriginal ()
            .getJGTIGraph (), this.convertRegexToMachineDialog.getRegexPanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Original" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }
      else if ( this.gui.jGTIRadioButtonConvertMachineConvertedGraph
          .isSelected () )
      {
        printJGraph ( this.convertRegexToMachineDialog.getModelConverted ()
            .getJGTIGraph (), this.convertRegexToMachineDialog.getRegexPanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Converted" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }

      else if ( this.gui.jGTIRadioButtonConvertMachineTable.isSelected () )
      {
        this.tableModel = this.convertRegexToMachineDialog
            .getConvertMachineTableModel ();
        this.tableColumnModel = this.convertRegexToMachineDialog
            .getTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.convertRegexToMachineDialog.getRegexPanel ()
            .getName ()
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.ConvertRegex" ) ); //$NON-NLS-1$
      }

    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link ConvertRegexToMachineDialog}.
   */
  private final void printConvertGrammarDialog ()
  {

    try
    {
      if ( this.gui.jGTIRadioButtonConvertMachineOriginalGraph.isSelected () )
      {
        this.tableModel = this.convertGrammarDialog.getJGTITableOriginal ()
            .getModel ();
        this.tableColumnModel = this.convertGrammarDialog
            .getJGTITableOriginal ().getColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.convertGrammarDialog.getPanel ().getName () );
      }
      else if ( this.gui.jGTIRadioButtonConvertMachineConvertedGraph
          .isSelected () )
      {
        this.tableModel = this.convertGrammarDialog.getJGTITableConverted ()
            .getModel ();
        this.tableColumnModel = this.convertGrammarDialog
            .getJGTITableConverted ().getColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.convertGrammarDialog.getPanel ().getName () );
      }

      else if ( this.gui.jGTIRadioButtonConvertMachineTable.isSelected () )
      {
        this.tableModel = this.convertGrammarDialog
            .getConvertMachineTableModel ();
        this.tableColumnModel = this.convertGrammarDialog
            .getTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.convertGrammarDialog.getPanel ().getName ()
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.ConvertRegex" ) ); //$NON-NLS-1$
      }

    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link GrammarPanel}.
   */
  private final void printGrammarPanel ()
  {
    try
    {
      this.tableModel = this.grammarPanel.getGrammarTableModel ();
      this.tableColumnModel = this.grammarPanel.getGrammarTableColumnModel ();
      this.table = new JGTITable ();
      this.table.setModel ( this.tableModel );
      this.table.setColumnModel ( this.tableColumnModel );

      printTableModel ( this.grammarPanel.getName () );
    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link HistoryDialog}.
   */
  private final void printHistoryDialog ()
  {
    try
    {
      this.tableModel = this.historyDialog.getHistoryModel ();
      this.tableColumnModel = this.historyDialog.getColumnModel ();
      this.table = new JGTITable ();
      this.table.setModel ( this.tableModel );
      this.table.setColumnModel ( this.tableColumnModel );

      printTableModel ( this.historyDialog.getMachinePanel ().getName ()
          + " " + Messages.getString ( "MainWindow.History" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Prints the {@link JGTIGraph}.
   * 
   * @param graph The {@link JGTIGraph} to print.
   * @param jobName The job name.
   */
  private final void printJGraph ( JGTIGraph graph, String jobName )
  {
    PrinterJob job = PrinterJob.getPrinterJob ();

    graph.setMarginTop ( this.marginTop );
    graph.setMarginBottom ( this.marginBottom );
    graph.setMarginLeft ( this.marginLeft );
    graph.setMarginRight ( this.marginRight );
    try
    {

      PageFormat pageFormat = new PageFormat ();
      Paper paper = new Paper ();
      paper.setSize ( 8.27 * 72, 11.69 * 72 );
      paper.setImageableArea ( 0, 0, paper.getWidth (), paper.getHeight () );
      pageFormat.setPaper ( paper );
      if ( this.gui.jGTIRadioButtonPortrait.isSelected () )
      {
        pageFormat.setOrientation ( PageFormat.PORTRAIT );
      }
      else
      {
        pageFormat.setOrientation ( PageFormat.LANDSCAPE );
      }

      job.setPrintService ( ( PrintService ) this.gui.jGTIComboBoxPrinter
          .getSelectedItem () );
      job.setPrintable ( graph, pageFormat );
      job.setJobName ( jobName );
      job.print ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link RegexPanel}.
   */
  private final void printRegexPanel ()
  {
    printJGraph ( this.regexPanel.getJGTIGraph (), this.regexPanel.getName () );
  }


  /**
   * Handles print {@link MachinePanel}.
   */
  private final void printMachinePanel ()
  {
    try
    {
      if ( this.gui.jGTIRadioButtonMachineGraph.isSelected () )
      {
        printJGraph ( this.machinePanel.getJGTIGraph (), this.machinePanel
            .getName () );
      }
      else if ( this.gui.jGTIRadioButtonMachinePDATable.isSelected () )
      {
        this.tableModel = this.machinePanel.getPDATableModel ();
        this.tableColumnModel = this.machinePanel.getPdaTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.machinePanel.getName ()
            + " " + Messages.getString ( "PrintDialog.PDATable" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      }
      else
      {
        this.tableModel = this.machinePanel.getMachineTableModel ();
        this.tableColumnModel = this.machinePanel.getMachineTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );
        printTableModel ( this.machinePanel.getName ()
            + " " + Messages.getString ( "PrintDialog.Table" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      }
    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link MinimizeMachineDialog}.
   */
  private final void printMinimizeMachineDialog ()
  {
    try
    {
      if ( this.gui.jGTIRadioButtonMinimizeMachineOriginalGraph.isSelected () )
      {
        printJGraph ( this.minimizeMachineDialog.getModelOriginal ()
            .getJGTIGraph (), this.minimizeMachineDialog.getMachinePanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Original" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }
      else if ( this.gui.jGTIRadioButtonMinimizeMachineMinimizedGraph
          .isSelected () )
      {
        printJGraph ( this.minimizeMachineDialog.getModelMinimized ()
            .getJGTIGraph (), this.minimizeMachineDialog.getMachinePanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.Minimized" ) ); //$NON-NLS-1$//$NON-NLS-2$
      }

      else if ( this.gui.jGTIRadioButtonMinimizeMachineTable.isSelected () )
      {
        this.tableModel = this.minimizeMachineDialog
            .getMinimizeMachineTableModel ();
        this.tableColumnModel = this.minimizeMachineDialog
            .getTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.minimizeMachineDialog.getMachinePanel ()
            .getName ()
            + " " + Messages.getString ( "PrintDialog.MinimizeMachine" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      }

    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Handles print {@link ReachableStatesDialog}.
   */
  private void printReachableStatesDialog ()
  {
    try
    {

      if ( this.gui.jGTIRadioButtonReachableStatesGraph.isSelected () )
      {
        printJGraph ( this.reachableStatesDialog.getModelOriginal ()
            .getJGTIGraph (), this.reachableStatesDialog.getMachinePanel ()
            .getName ()
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.ReachableStates" ) //$NON-NLS-1$
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.Original" ) ); //$NON-NLS-1$
      }

      else if ( this.gui.jGTIRadioButtonReachableStatesTable.isSelected () )
      {
        this.tableModel = this.reachableStatesDialog
            .getReachableStatesTableModel ();
        this.tableColumnModel = this.reachableStatesDialog
            .getTableColumnModel ();
        this.table = new JGTITable ();
        this.table.setModel ( this.tableModel );
        this.table.setColumnModel ( this.tableColumnModel );

        printTableModel ( this.reachableStatesDialog.getMachinePanel ()
            .getName ()
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.ReachableStates" ) //$NON-NLS-1$
            + " " //$NON-NLS-1$
            + Messages.getString ( "PrintDialog.Table" ) ); //$NON-NLS-1$
      }

    }
    catch ( PrinterException exc )
    {
      InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
          .getString ( "PrintDialog.ErrorPrintMessage" ), Messages //$NON-NLS-1$
          .getString ( "PrintDialog.ErrorPrint" ) ); //$NON-NLS-1$
      dialog.show ();
    }
  }


  /**
   * Prints the {@link TableModel}.
   * 
   * @param jobName The print job name.
   * @throws PrinterException If the something with the print dialog fails.
   */
  private final void printTableModel ( String jobName ) throws PrinterException
  {
    PrinterJob job = PrinterJob.getPrinterJob ();

    PageFormat pageFormat = new PageFormat ();
    Paper paper = new Paper ();
    paper.setSize ( 8.27 * 72, 11.69 * 72 );
    paper.setImageableArea ( 0, 0, paper.getWidth (), paper.getHeight () );
    pageFormat.setPaper ( paper );
    if ( this.gui.jGTIRadioButtonPortrait.isSelected () )
    {
      pageFormat.setOrientation ( PageFormat.PORTRAIT );
    }
    else
    {
      pageFormat.setOrientation ( PageFormat.LANDSCAPE );
    }
    this.pageWidth = ( int ) pageFormat.getWidth ();
    this.pageHeight = ( int ) pageFormat.getHeight ();

    // Calculate the page count
    this.pageCount = getPageCount ();

    job.setPrintable ( this, pageFormat );
    job.setPrintService ( ( PrintService ) this.gui.jGTIComboBoxPrinter
        .getSelectedItem () );

    job.setJobName ( jobName );

    logger.debug ( "handlePrint", "printing" ); //$NON-NLS-1$ //$NON-NLS-2$
    job.print ();
    logger.debug ( "handlePrint", "printed" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Shows the {@link PrintDialog}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the print dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.pack ();
    this.gui.setLocationRelativeTo ( this.parentFrame );
    if ( this.gui.jGTIComboBoxPrinter.getModel ().getSize () > 0 )
    {
      this.gui.setVisible ( true );
    }
    else
    {
      if ( this.parentFrame != null )
      {
        InfoDialog dialog = new InfoDialog ( this.parentFrame, Messages
            .getString ( "PrintDialog.ErrorPrinterMessage" ), Messages //$NON-NLS-1$
            .getString ( "PrintDialog.ErrorPrinter" ) ); //$NON-NLS-1$
        dialog.show ();
      }
      else
      {
        InfoDialog dialog = new InfoDialog ( this.parentDialog, Messages
            .getString ( "PrintDialog.ErrorPrinterMessage" ), Messages //$NON-NLS-1$
            .getString ( "PrintDialog.ErrorPrinter" ) ); //$NON-NLS-1$
        dialog.show ();
      }
    }
  }
}
