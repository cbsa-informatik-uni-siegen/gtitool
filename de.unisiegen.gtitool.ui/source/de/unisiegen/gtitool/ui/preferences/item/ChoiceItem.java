package de.unisiegen.gtitool.ui.preferences.item;


import de.unisiegen.gtitool.ui.Messages;


/**
 * Indicates which choice is choosen.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum ChoiceItem
{
  /**
   * The without return to the mouse value.
   */
  WITHOUT_RETURN_TO_MOUSE ( 0, "PreferencesDialog.ChoiceWithoutReturn" ), //$NON-NLS-1$

  /**
   * The with return to the mouse value.
   */
  WITH_RETURN_TO_MOUSE ( 1, "PreferencesDialog.ChoiceWithReturn" ); //$NON-NLS-1$

  /**
   * Creates a new {@link ChoiceItem} of the given index, or throws an exception
   * if the index is not supported.
   * 
   * @param index The index.
   * @return A new {@link ChoiceItem}.
   */
  public final static ChoiceItem create ( int index )
  {
    switch ( index )
    {
      case 0 :
      {
        return WITHOUT_RETURN_TO_MOUSE;
      }
      case 1 :
      {
        return WITH_RETURN_TO_MOUSE;
      }
      default :
      {
        throw new IllegalArgumentException ( "choice item not supported" ); //$NON-NLS-1$
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
   * Allocates a new <code>ChoiceItem</code>.
   * 
   * @param index
   * @param title
   */
  private ChoiceItem ( int index, String title )
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
