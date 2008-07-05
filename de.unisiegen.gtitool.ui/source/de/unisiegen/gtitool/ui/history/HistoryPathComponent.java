package de.unisiegen.gtitool.ui.history;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;


/**
 * This class implements the {@link PrettyPrintable} {@link HistoryPath}
 * {@link Component}.
 * 
 * @author Christian Fehler
 */
public final class HistoryPathComponent extends JLabel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 88702245703588279L;


  /**
   * The used font.
   */
  private static final Font FONT = new Font ( "Dialog", Font.PLAIN, 12 ); //$NON-NLS-1$


  /**
   * The row height.
   */
  private static final int ROW_HEIGHT = 24;


  /**
   * The space width.
   */
  private static final int SPACE_WIDTH = 10;


  /**
   * The state offset.
   */
  private static final int STATE_OFFSET = 3;


  /**
   * The transition offset.
   */
  private static final int TRANSITION_OFFSET = 10;


  /**
   * The transition arrow offset.
   */
  private static final int TRANSITION_ARROW_OFFSET = 6;


  /**
   * The {@link HistoryPath}.
   */
  private HistoryPath historyPath;


  /**
   * The x position.
   */
  private int xPosition = 0;


  /**
   * The y position.
   */
  private int yPosition = 0;


  /**
   * The row count.
   */
  private int rowCount = 1;


  /**
   * The used {@link JTable}.
   */
  private JTable table;


  /**
   * The {@link JTable} row.
   */
  private int tableRow;


  /**
   * Initializes the {@link HistoryPathComponent}.
   * 
   * @param historyPath The {@link HistoryPath}.
   * @param table The used {@link JTable}.
   * @param tableRow The {@link JTable} row.
   */
  public HistoryPathComponent ( HistoryPath historyPath, JTable table,
      int tableRow )
  {
    super ();
    setBorder ( null );

    this.historyPath = historyPath;
    this.table = table;
    this.tableRow = tableRow;
  }


  /**
   * Returns the row count.
   * 
   * @param g The {@link Graphics}.
   * @return The row count.
   */
  private final int calculateRowCount ( Graphics g )
  {
    int width = getWidth ();
    int count = 1;

    this.xPosition = 0;
    this.yPosition = ROW_HEIGHT;

    for ( int i = 0 ; i < this.historyPath.getTransitionList ().size () ; i++ )
    {
      Transition transition = this.historyPath.getTransitionList ().get ( i );

      // Line break
      int stateWidth = calculateStateWidth ( transition.getStateBegin (), g );
      if ( ( this.xPosition + stateWidth ) > width )
      {
        count++ ;
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      // State
      this.xPosition += stateWidth;

      // Line break
      int transitionWidth = calculateTransitionWidth ( transition, g );
      if ( ( this.xPosition + transitionWidth ) > width )
      {
        count++ ;
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      // Transition
      this.xPosition += transitionWidth;
    }

    // Last state
    if ( this.historyPath.getTransitionList ().size () > 0 )
    {
      State state = this.historyPath.getTransitionList ().get (
          this.historyPath.getTransitionList ().size () - 1 ).getStateEnd ();

      // Line break
      int stateWidth = calculateStateWidth ( state, g );
      if ( this.xPosition + stateWidth > width )
      {
        count++ ;
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      // State
      this.xPosition += stateWidth;
    }

    // Start state
    if ( this.historyPath.getStartState () != null )
    {
      State state = this.historyPath.getStartState ();

      // Line break
      int stateWidth = calculateStateWidth ( state, g );
      if ( this.xPosition + stateWidth > width )
      {
        count++ ;
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      // State
      this.xPosition += stateWidth;
    }

    return count;
  }


  /**
   * Calculates the {@link State} width.
   * 
   * @param state The {@link State}.
   * @param g The {@link Graphics}.
   * @return The {@link State} width.
   */
  private final int calculateStateWidth ( State state, Graphics g )
  {
    FontMetrics metrics = g.getFontMetrics ();
    char [] prettyString = state.toPrettyString ().toString ().toCharArray ();
    int result = 0;
    for ( char element : prettyString )
    {
      result += metrics.charWidth ( element );
    }
    return result;
  }


  /**
   * Calculates the {@link Transition} width.
   * 
   * @param transition The {@link Transition}.
   * @param g The {@link Graphics}.
   * @return The {@link Transition} width.
   */
  private final int calculateTransitionWidth ( Transition transition, Graphics g )
  {
    FontMetrics metrics = g.getFontMetrics ();
    int result = 0;
    for ( PrettyToken currentToken : transition.toPrettyString ()
        .getPrettyToken () )
    {
      Font font = null;
      if ( !currentToken.isBold () && !currentToken.isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.isBold () && currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getColor () );
      char [] chars = currentToken.getChar ();
      for ( char c : chars )
      {
        result += metrics.charWidth ( c );
      }
    }
    return result + ( 2 * SPACE_WIDTH );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics g )
  {
    g.setFont ( FONT );

    int width = getWidth ();
    this.rowCount = calculateRowCount ( g );

    g.setColor ( getBackground () );
    g.fillRect ( 0, 0, getWidth (), ROW_HEIGHT * this.rowCount );

    this.xPosition = 0;
    this.yPosition = ROW_HEIGHT;

    for ( int i = 0 ; i < this.historyPath.getTransitionList ().size () ; i++ )
    {
      Transition transition = this.historyPath.getTransitionList ().get ( i );
      State state = transition.getStateBegin ();

      // Line break
      if ( this.xPosition + calculateStateWidth ( state, g ) > width )
      {
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      paintState ( state, g );

      // Line break
      if ( this.xPosition + calculateTransitionWidth ( transition, g ) > width )
      {
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      paintTransition ( transition, g );
    }

    // Last state
    if ( this.historyPath.getTransitionList ().size () > 0 )
    {
      State state = this.historyPath.getTransitionList ().get (
          this.historyPath.getTransitionList ().size () - 1 ).getStateEnd ();

      // Line break
      if ( this.xPosition + calculateStateWidth ( state, g ) > width )
      {
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      paintState ( state, g );
    }

    // Start state
    if ( this.historyPath.getStartState () != null )
    {
      State state = this.historyPath.getStartState ();

      // Line break
      if ( this.xPosition + calculateStateWidth ( state, g ) > width )
      {
        this.xPosition = 0;
        this.yPosition += ROW_HEIGHT;
      }

      paintState ( state, g );
    }

    // Update the table row height
    if ( this.table.getRowHeight ( this.tableRow ) != ROW_HEIGHT
        * this.rowCount )
    {
      this.table.setRowHeight ( this.tableRow, ROW_HEIGHT * this.rowCount );
    }
  }


  /**
   * Paints the given {@link State}.
   * 
   * @param state The {@link State} to paint.
   * @param g The {@link Graphics}.
   */
  private final void paintState ( State state, Graphics g )
  {
    FontMetrics metrics = g.getFontMetrics ();

    for ( PrettyToken currentToken : state.toPrettyString ().getPrettyToken () )
    {
      Font font = null;
      if ( !currentToken.isBold () && !currentToken.isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.isBold () && currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getColor () );
      char [] chars = currentToken.getChar ();
      for ( int i = 0 ; i < chars.length ; i++ )
      {
        g.drawChars ( chars, i, 1, this.xPosition, this.yPosition
            - STATE_OFFSET );
        this.xPosition += metrics.charWidth ( chars [ i ] );
      }
    }
  }


  /**
   * Paints the given {@link Transition}.
   * 
   * @param transition The {@link Transition} to paint.
   * @param g The {@link Graphics}.
   */
  private final void paintTransition ( Transition transition, Graphics g )
  {
    FontMetrics metrics = g.getFontMetrics ();

    int oldX = this.xPosition;

    this.xPosition += SPACE_WIDTH;
    for ( PrettyToken currentToken : transition.toPrettyString ()
        .getPrettyToken () )
    {
      Font font = null;

      if ( !currentToken.isBold () && !currentToken.isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.isBold () && currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getColor () );

      char [] chars = currentToken.getChar ();
      for ( int i = 0 ; i < chars.length ; i++ )
      {
        g.drawChars ( chars, i, 1, this.xPosition, this.yPosition
            - TRANSITION_OFFSET );
        this.xPosition += metrics.charWidth ( chars [ i ] );
      }
    }

    g.setColor ( Color.BLACK );
    g.drawLine ( oldX + ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ),
        this.yPosition - TRANSITION_ARROW_OFFSET );

    g.drawLine ( this.xPosition + ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ) - 4,
        this.yPosition - TRANSITION_ARROW_OFFSET - 2 );

    g.drawLine ( this.xPosition + ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ) - 4,
        this.yPosition - TRANSITION_ARROW_OFFSET + 2 );

    this.xPosition += SPACE_WIDTH;
  }
}
