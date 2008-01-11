package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.Messages;


/**
 * Indicates which {@link Transition} choice is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum TransitionItem
{
  /**
   * The {@link Transition} can be created per mouse drag.
   */
  DRAG_MODE ( 0, "PreferencesDialog.TransitionDrag" ), //$NON-NLS-1$

  /**
   * The {@link Transition} can be created per mouse click.
   */
  CLICK_MODE ( 1, "PreferencesDialog.TransitionClick" ); //$NON-NLS-1$

  /**
   * Creates a new {@link TransitionItem} of the given index, or throws an
   * exception if the index is not supported.
   * 
   * @param index The index.
   * @return A new {@link TransitionItem}.
   */
  public final static TransitionItem create ( int index )
  {
    switch ( index )
    {
      case 0 :
      {
        return DRAG_MODE;
      }
      case 1 :
      {
        return CLICK_MODE;
      }
      default :
      {
        throw new IllegalArgumentException ( "transition mode not supported" ); //$NON-NLS-1$
      }
    }
  }


  /**
   * The index.
   */
  private int index;


  /**
   * The title.
   */
  private String title;


  /**
   * Allocates a new <code>TransitionItem</code>.
   * 
   * @param index The index.
   * @param title The title.
   */
  private TransitionItem ( int index, String title )
  {
    this.index = index;
    this.title = title;
  }


  /**
   * Returns the index.
   * 
   * @return The index.
   */
  public final int getIndex ()
  {
    return this.index;
  }


  /**
   * Returns the title.
   * 
   * @return The title.
   */
  public final String getTitle ()
  {
    return Messages.getString ( this.title );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Enum#toString()
   */
  @Override
  public final String toString ()
  {
    return getTitle ();
  }
}
