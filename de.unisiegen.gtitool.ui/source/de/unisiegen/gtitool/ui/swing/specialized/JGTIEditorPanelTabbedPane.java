package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTabbedPane;

import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;
import de.unisiegen.gtitool.ui.swing.JGTITabbedPane;


/**
 * Special {@link JTabbedPane} for the {@link EditorPanel}s.
 * 
 * @author Christian Fehler
 * @version $Id: JGTIEditorPanelTabbedPane.java 959 2008-06-03 21:39:09Z fehler
 *          $
 */
public final class JGTIEditorPanelTabbedPane extends JGTITabbedPane implements
    Iterable < EditorPanel >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4431483132252509771L;


  /**
   * Allocates a new {@link JGTIEditorPanelTabbedPane}.
   */
  public JGTIEditorPanelTabbedPane ()
  {
    super ();
  }


  /**
   * Allocates a new {@link JGTIEditorPanelTabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   */
  public JGTIEditorPanelTabbedPane ( int tabPlacement )
  {
    super ( tabPlacement );
  }


  /**
   * Allocates a new {@link JGTIEditorPanelTabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   * @param tabLayoutPolicy The tab layout policy.
   */
  public JGTIEditorPanelTabbedPane ( int tabPlacement, int tabLayoutPolicy )
  {
    super ( tabPlacement, tabLayoutPolicy );
  }


  /**
   * Adds the given {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} to add.
   */
  public final void addEditorPanel ( EditorPanel editorPanel )
  {
    add ( editorPanel.getName (), editorPanel.getPanel () );
  }


  /**
   * Returns the {@link EditorPanel} with the given index.
   * 
   * @param index The index.
   * @return The {@link EditorPanel}.
   */
  @SuppressWarnings ( "unchecked" )
  public final EditorPanel getEditorPanel ( int index )
  {
    Component component = getComponentAt ( index );
    if ( component == null )
    {
      return null;
    }
    return ( EditorPanel ) ( ( GUIClass ) component ).getLogic ();
  }


  /**
   * Returns the title of the given {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel}.
   * @return The title of the given {@link EditorPanel}.
   */
  public final String getEditorPanelTitle ( EditorPanel editorPanel )
  {
    int index = -1;
    for ( int i = 0 ; i < getComponentCount () ; i++ )
    {
      if ( editorPanel.getPanel () == getComponentAt ( i ) )
      {
        index = i;
        break;
      }
    }
    return getTitleAt ( index );
  }


  /**
   * Returns the selected {@link EditorPanel}. Returns null if there is no
   * currently selected tab.
   * 
   * @return The selected {@link EditorPanel}. Returns null if there is no
   *         currently selected tab.
   */
  @SuppressWarnings ( "unchecked" )
  public final EditorPanel getSelectedEditorPanel ()
  {
    Component component = getSelectedComponent ();
    if ( component == null )
    {
      return null;
    }
    return ( EditorPanel ) ( ( GUIClass ) component ).getLogic ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  @SuppressWarnings ( "unchecked" )
  public final Iterator < EditorPanel > iterator ()
  {
    ArrayList < EditorPanel > editorPanelList = new ArrayList < EditorPanel > (
        getComponentCount () );
    for ( Component current : getComponents () )
    {
      editorPanelList.add ( ( EditorPanel ) ( ( GUIClass ) current )
          .getLogic () );
    }
    return editorPanelList.iterator ();
  }


  /**
   * Removes the given {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} to remove.
   */
  public final void removeEditorPanel ( EditorPanel editorPanel )
  {
    remove ( editorPanel.getPanel () );
  }


  /**
   * Removes the selected {@link EditorPanel}.
   */
  public final void removeSelectedEditorPanel ()
  {
    remove ( getSelectedIndex () );
  }


  /**
   * Sets the selected {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} which text should be set.
   * @param title The title to set.
   */
  public final void setEditorPanelTitle ( EditorPanel editorPanel, String title )
  {
    int index = -1;
    for ( int i = 0 ; i < getComponentCount () ; i++ )
    {
      if ( editorPanel.getPanel () == getComponentAt ( i ) )
      {
        index = i;
        break;
      }
    }
    setTitleAt ( index, title );
  }


  /**
   * Sets the selected {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} to select.
   */
  public final void setSelectedEditorPanel ( EditorPanel editorPanel )
  {
    setSelectedComponent ( editorPanel.getPanel () );
  }
}
