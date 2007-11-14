/**
 * 
 */
package de.unisiegen.gtitool.ui.style.sidebar;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import de.unisiegen.gtitool.core.parser.exceptions.ParserWarningException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.preferences.listener.ExceptionsChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserDocument;


/**
 * This class implements the sidebar which is used in the source views.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class SideBar extends JComponent
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5819550523704175785L;


  /**
   * The parser normal error icon.
   */
  private ImageIcon errorIcon = null;


  /**
   * The parser warning icon.
   */
  private ImageIcon warningIcon = null;


  /**
   * The vertical positions of the errors.
   */
  private int [] verticalPositions;


  /**
   * The vertical {@link JScrollBar}.
   */
  private JScrollBar verticalScrollBar;


  /**
   * The horizontal {@link JScrollBar}.
   */
  private JScrollBar horizontalScrollBar;


  /**
   * The used {@link JScrollPane}.
   */
  private JScrollPane scrollPane;


  /**
   * The list of {@link ScannerException}.
   */
  private ArrayList < ScannerException > exceptionList;


  /**
   * The used {@link StyledParserDocument}.
   */
  private StyledParserDocument document;


  /**
   * The used text component.
   */
  private JTextComponent textComponent;


  /**
   * The status if something has changed.
   */
  private boolean proppertyChanged;


  /**
   * The left offset of the current exception.
   */
  private int currentLeft;


  /**
   * The right offset of the current exception.
   */
  private int currentRight;


  /**
   * Initializes the {@link SideBar}.
   * 
   * @param pScrollPane The used {@link JScrollPane}.
   * @param pDocument The used {@link StyledParserDocument}.
   * @param pTextComponent The used text component.
   */
  public SideBar ( JScrollPane pScrollPane, StyledParserDocument pDocument,
      JTextComponent pTextComponent )
  {
    super ();
    this.currentLeft = -1;
    this.currentRight = -1;
    this.scrollPane = pScrollPane;
    this.document = pDocument;
    this.textComponent = pTextComponent;
    this.errorIcon = new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/error.gif" ) ); //$NON-NLS-1$
    this.warningIcon = new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/warning.gif" ) ); //$NON-NLS-1$
    int imageWidth = this.errorIcon.getIconWidth ();
    this.proppertyChanged = false;
    setMinimumSize ( new Dimension ( imageWidth, imageWidth ) );
    this.verticalScrollBar = this.scrollPane.getVerticalScrollBar ();
    this.horizontalScrollBar = this.scrollPane.getHorizontalScrollBar ();
    this.verticalScrollBar.addAdjustmentListener ( new AdjustmentListener ()
    {

      public void adjustmentValueChanged ( @SuppressWarnings ( "unused" )
      AdjustmentEvent event )
      {
        repaint ();
      }
    } );
    this.document
        .addExceptionsChangedListener ( new ExceptionsChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void exceptionsChanged ()
          {
            SideBar.this.proppertyChanged = true;
            repaint ();
          }
        } );
    this.addMouseMotionListener ( new MouseMotionAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseMoved ( MouseEvent event )
      {
        SideBar.this.mouseMoved ( event );
      }
    } );
    this.addMouseListener ( new MouseAdapter ()
    {

      @Override
      public void mouseClicked ( MouseEvent event )
      {
        SideBar.this.mouseClicked ( event );
      }
    } );
  }


  /**
   * Adds the given {@link SideBarListener}.
   * 
   * @param pSideBarListener The given {@link SideBarListener}.
   */
  public void addSideBarListener ( SideBarListener pSideBarListener )
  {
    this.listenerList.add ( SideBarListener.class, pSideBarListener );
  }


  /**
   * Builds the marks.
   */
  private void buildMarks ()
  {
    this.exceptionList = this.document.getExceptionList ();
    this.verticalPositions = new int [ this.exceptionList.size () ];
    for ( int i = 0 ; i < this.exceptionList.size () ; i++ )
    {
      try
      {
        this.verticalPositions [ i ] = -1;
        Rectangle rect = this.textComponent.modelToView ( this.exceptionList
            .get ( i ).getLeft () );
        if ( rect == null )
        {
          return;
        }
        this.verticalPositions [ i ] = rect.y + rect.height / 2;
      }
      catch ( Exception e )
      {
        continue;
      }
    }
    this.proppertyChanged = false;
  }


  /**
   * Inserts a given text at the given index.
   * 
   * @param pInsertText The text which should be inserted.
   */
  private void fireInsertText ( String pInsertText )
  {
    Object listeners[] = this.listenerList.getListenerList ();
    for ( int i = 0 ; i < listeners.length ; i++ )
    {
      if ( listeners [ i ] == SideBarListener.class )
      {
        ( ( SideBarListener ) listeners [ i + 1 ] ).insertText (
            this.currentRight, pInsertText );
      }
    }
  }


  /**
   * Marks the text with the given offsets.
   */
  private void fireMarkText ()
  {
    Object listeners[] = this.listenerList.getListenerList ();
    for ( int i = 0 ; i < listeners.length ; i++ )
    {
      if ( listeners [ i ] == SideBarListener.class )
      {
        ( ( SideBarListener ) listeners [ i + 1 ] ).markText (
            this.currentLeft, this.currentRight );
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Dimension getPreferredSize ()
  {
    return new Dimension ( this.errorIcon.getIconWidth (), getHeight () );
  }


  /**
   * Handles the mouse clicked event.
   * 
   * @param pMouseEvent The {@link MouseEvent}.
   */
  public void mouseClicked ( MouseEvent pMouseEvent )
  {
    if ( this.currentLeft == -1 || this.currentRight == -1 )
    {
      return;
    }
    int y = pMouseEvent.getY () + this.verticalScrollBar.getValue ();
    int hh = this.errorIcon.getIconHeight () / 2;
    for ( int i = 0 ; i < this.verticalPositions.length ; i++ )
    {
      if ( y > this.verticalPositions [ i ] - hh
          && y <= this.verticalPositions [ i ] + hh )
      {
        if ( this.exceptionList.get ( i ) instanceof ParserWarningException )
        {
          ParserWarningException ecx = ( ParserWarningException ) this.exceptionList
              .get ( i );
          this.currentLeft = this.exceptionList.get ( i ).getLeft ();
          this.currentRight = this.exceptionList.get ( i ).getRight ();
          fireInsertText ( ecx.getInsertText () );
          return;
        }
      }
    }
    fireMarkText ();
  }


  /**
   * Handles the mouse move event.
   * 
   * @param pMouseEvent The {@link MouseEvent}.
   */
  private void mouseMoved ( MouseEvent pMouseEvent )
  {
    if ( this.verticalPositions == null )
    {
      return;
    }
    int y = pMouseEvent.getY () + this.verticalScrollBar.getValue ();
    int hh = this.errorIcon.getIconHeight () / 2;
    for ( int i = 0 ; i < this.verticalPositions.length ; i++ )
    {
      if ( y > this.verticalPositions [ i ] - hh
          && y <= this.verticalPositions [ i ] + hh )
      {
        this.currentLeft = this.exceptionList.get ( i ).getLeft ();
        this.currentRight = this.exceptionList.get ( i ).getRight ();
        setToolTipText ( this.exceptionList.get ( i ).getMessage () );
        setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
        return;
      }
    }
    setCursor ( new Cursor ( Cursor.DEFAULT_CURSOR ) );
    setToolTipText ( null );
    this.currentLeft = -1;
    this.currentRight = -1;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintComponent ( Graphics pGraphics )
  {
    if ( this.proppertyChanged )
    {
      buildMarks ();
    }
    pGraphics.setColor ( getBackground () );
    pGraphics.fillRect ( 0, 0, getWidth (), getHeight () );
    if ( this.verticalPositions == null )
    {
      return;
    }
    for ( int i = 0 ; i < this.verticalPositions.length ; i++ )
    {
      if ( this.verticalPositions [ i ] == -1 )
      {
        continue;
      }
      int y0 = this.verticalPositions [ i ] - this.errorIcon.getIconHeight ()
          / 2 - this.verticalScrollBar.getValue ();
      int y1 = y0 + this.errorIcon.getIconHeight ();
      if ( y1 < 0 || y0 > getHeight () )
      {
        continue;
      }
      if ( this.exceptionList.get ( i ) instanceof ParserWarningException )
      {
        pGraphics.drawImage ( this.warningIcon.getImage (), 0, y0, this );
      }
      else
      {
        pGraphics.drawImage ( this.errorIcon.getImage (), 0, y0, this );
      }
    }
    pGraphics.fillRect ( 0, getHeight ()
        - this.horizontalScrollBar.getHeight (), getWidth (),
        this.horizontalScrollBar.getHeight () );
  }


  /**
   * Adds the given {@link SideBarListener}.
   * 
   * @param pSideBarListener The given {@link SideBarListener}.
   */
  public void removeSideBarListener ( SideBarListener pSideBarListener )
  {
    this.listenerList.remove ( SideBarListener.class, pSideBarListener );
  }
}
