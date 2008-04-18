package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.HistoryPath;


/**
 * This class implements the {@link PrettyPrintable} {@link HistoryPath}
 * {@link Component}.
 * 
 * @author Christian Fehler
 */
public final class HistoryPathComponent extends JLabel
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
   * The serial version uid.
   */
  private static final long serialVersionUID = 88702245703588279L;


  /**
   * The used font.
   */
  private static final Font FONT = new Font ( "Dialog", Font.PLAIN, 12 ); //$NON-NLS-1$


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
   * Initializes the {@link HistoryPathComponent}.
   * 
   * @param historyPath The {@link HistoryPath}.
   */
  public HistoryPathComponent ( HistoryPath historyPath )
  {
    super ();
    setBorder ( new EmptyBorder ( 1, 1, 1, 1 ) );

    this.historyPath = historyPath;

    // Used to calculate the preferered size.
    setText ( "Component" ); //$NON-NLS-1$
  }


  /**
   * Returns the row height.
   * 
   * @return The row height.
   */
  public final int getRowHeight ()
  {
    return ROW_HEIGHT * this.rowCount;
  }


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
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics g )
  {
    g.setColor ( getBackground () );
    g.fillRect ( 0, 0, getWidth (), ROW_HEIGHT );
    g.setFont ( FONT );

    this.xPosition = 0;
    this.yPosition = getHeight ();

    for ( int i = 0 ; i < this.historyPath.getTransitionList ().size () ; i++ )
    {
      // State
      paintState ( this.historyPath.getStateList ().get ( i ), g );
      // Space
      this.xPosition += SPACE_WIDTH;
      // Transition
      paintTransition ( this.historyPath.getTransitionList ().get ( i ), g );
      // Space
      this.xPosition += SPACE_WIDTH;
    }
    if ( this.historyPath.getStateList ().size () > 0 )
    {
      paintState ( this.historyPath.getStateList ().get (
          this.historyPath.getStateList ().size () - 1 ), g );
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

      if ( !currentToken.getStyle ().isBold ()
          && !currentToken.getStyle ().isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.getStyle ().isBold ()
          && currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.getStyle ().isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getStyle ().getColor () );
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

    for ( PrettyToken currentToken : transition.toPrettyString ()
        .getPrettyToken () )
    {
      Font font = null;

      if ( !currentToken.getStyle ().isBold ()
          && !currentToken.getStyle ().isItalic () )
      {
        font = FONT;
      }
      else if ( currentToken.getStyle ().isBold ()
          && currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.BOLD | Font.ITALIC );
      }
      else if ( currentToken.getStyle ().isBold () )
      {
        font = FONT.deriveFont ( Font.BOLD );
      }
      else if ( currentToken.getStyle ().isItalic () )
      {
        font = FONT.deriveFont ( Font.ITALIC );
      }

      g.setFont ( font );
      g.setColor ( currentToken.getStyle ().getColor () );

      char [] chars = currentToken.getChar ();
      for ( int i = 0 ; i < chars.length ; i++ )
      {
        g.drawChars ( chars, i, 1, this.xPosition, this.yPosition
            - TRANSITION_OFFSET );
        this.xPosition += metrics.charWidth ( chars [ i ] );
      }
    }

    g.setColor ( Color.BLACK );
    g.drawLine ( oldX - ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ),
        this.yPosition - TRANSITION_ARROW_OFFSET );

    g.drawLine ( this.xPosition + ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ) - 4,
        this.yPosition - TRANSITION_ARROW_OFFSET - 2 );

    g.drawLine ( this.xPosition + ( SPACE_WIDTH / 2 ), this.yPosition
        - TRANSITION_ARROW_OFFSET, this.xPosition + ( SPACE_WIDTH / 2 ) - 4,
        this.yPosition - TRANSITION_ARROW_OFFSET + 2 );
  }
}
