package de.unisiegen.gtitool.ui.style;


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
   * @param pIndex The index in the text, where the text should be inserted.
   * @param pInsertText The text which should be inserted.
   */
  public void insertText ( int pIndex, String pInsertText );


  /**
   * Marks the text with the given offsets.
   * 
   * @param pLeft The left offset of the text which should be marked.
   * @param pRight The right offset of the text which should be marked.
   */
  public void markText ( int pLeft, int pRight );
}
