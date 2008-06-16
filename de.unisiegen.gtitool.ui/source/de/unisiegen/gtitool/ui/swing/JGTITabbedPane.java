package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


/**
 * Special {@link JTabbedPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITabbedPane extends JTabbedPane
{

  /**
   * The {@link TabMouseHandler}.
   * 
   * @author Christian Fehler
   */
  protected final class TabMouseHandler extends MouseInputAdapter
  {

    /**
     * The source tab index.
     */
    private int sourceTabIndex = -1;


    /**
     * {@inheritDoc}
     * 
     * @see MouseInputAdapter#mouseDragged(MouseEvent)
     */
    @SuppressWarnings ( "synthetic-access" )
    @Override
    public final void mouseDragged ( MouseEvent event )
    {
      if ( this.sourceTabIndex != -1 )
      {
        int targetTabIndex = getTabIndex ( event.getX (), event.getY () );
        if ( ( targetTabIndex == -1 )
            || ( this.sourceTabIndex == targetTabIndex ) )
        {
          setCursor ( DragSource.DefaultMoveNoDrop );
          clearHighlightTab ();
        }
        else
        {
          setCursor ( DragSource.DefaultMoveDrop );
          highlightTab ( targetTabIndex );
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseInputAdapter#mousePressed(MouseEvent)
     */
    @SuppressWarnings ( "synthetic-access" )
    @Override
    public final void mousePressed ( MouseEvent event )
    {
      if ( !event.isPopupTrigger ()
          && ( event.getButton () == MouseEvent.BUTTON1 ) )
      {
        int tabIndex = getTabIndex ( event.getX (), event.getY () );
        if ( tabIndex != -1 )
        {
          this.sourceTabIndex = tabIndex;
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseInputAdapter#mouseReleased(MouseEvent)
     */
    @SuppressWarnings ( "synthetic-access" )
    @Override
    public final void mouseReleased ( MouseEvent event )
    {
      if ( !event.isPopupTrigger ()
          && ( event.getButton () == MouseEvent.BUTTON1 ) )
      {
        if ( this.sourceTabIndex != -1 )
        {
          int tabIndex = getTabIndex ( event.getX (), event.getY () );
          if ( ( tabIndex != -1 ) && ( tabIndex != this.sourceTabIndex ) )
          {
            moveTab ( this.sourceTabIndex, tabIndex );
            setSelectedIndex ( tabIndex );
          }
          this.sourceTabIndex = -1;
          setCursor ( Cursor.getDefaultCursor () );
          clearHighlightTab ();
        }
      }
    }
  }


  /**
   * The highlight {@link Color}.
   */
  private static final Color HIGHLIGHT_COLOR = new Color ( 150, 200, 250 );


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5744054037095918506L;


  /**
   * The {@link MouseInputListener}.
   */
  private MouseInputListener mouseInputListener;


  /**
   * Allocates a new {@link JGTITabbedPane}.
   */
  public JGTITabbedPane ()
  {
    this ( TOP );
  }


  /**
   * Allocates a new {@link JGTITabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   */
  public JGTITabbedPane ( int tabPlacement )
  {
    this ( tabPlacement, WRAP_TAB_LAYOUT );
  }


  /**
   * Allocates a new {@link JGTITabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   * @param tabLayoutPolicy The tab layout policy.
   */
  public JGTITabbedPane ( int tabPlacement, int tabLayoutPolicy )
  {
    super ( tabPlacement, tabLayoutPolicy );

    this.mouseInputListener = new TabMouseHandler ();
    addMouseListener ( this.mouseInputListener );
    addMouseMotionListener ( this.mouseInputListener );
  }


  /**
   * Returns the tab index.
   * 
   * @param x The x position.
   * @param y The y position.
   * @return the tab index.
   */
  private final int getTabIndex ( int x, int y )
  {
    return getUI ().tabForCoordinate ( this, x, y );
  }


  /**
   * Clears the highlight of the tabs.
   */
  private final void clearHighlightTab ()
  {
    for ( int i = 0 ; i < getComponentCount () ; i++ )
    {
      setBackgroundAt ( i, null );
    }
  }


  /**
   * Highlights the tab with the given index.
   * 
   * @param tabIndex The tab index.
   */
  private final void highlightTab ( int tabIndex )
  {
    if ( tabIndex < 0 )
    {
      throw new IllegalArgumentException ( "tab index to small" ); //$NON-NLS-1$
    }
    if ( tabIndex >= getComponentCount () )
    {
      throw new IllegalArgumentException ( "tab index to large" ); //$NON-NLS-1$
    }

    clearHighlightTab ();
    setBackgroundAt ( tabIndex, HIGHLIGHT_COLOR );
  }


  /**
   * Moves the tab.
   * 
   * @param oldIndex The old index.
   * @param newIndex The new index.
   */
  private final void moveTab ( int oldIndex, int newIndex )
  {
    Component component = getComponentAt ( oldIndex );
    remove ( component );
    add ( component, newIndex );
  }
}
