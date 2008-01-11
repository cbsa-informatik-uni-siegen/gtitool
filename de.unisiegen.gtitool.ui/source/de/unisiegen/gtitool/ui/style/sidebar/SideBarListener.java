package de.unisiegen.gtitool.ui.style.sidebar;


import java.util.EventListener;


/**
 * Interface for listeners on the {@link SideBar}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface SideBarListener extends EventListener
{

  /**
   * Inserts a given text at the given index.
   * 
   * @param index The index in the text, where the text should be inserted.
   * @param insertText The text which should be inserted.
   */
  public void insertText ( int index, String insertText );


  /**
   * Marks the text with the given offsets.
   * 
   * @param left The left offset of the text which should be marked.
   * @param right The right offset of the text which should be marked.
   */
  public void markText ( int left, int right );
}
