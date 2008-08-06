package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import de.unisiegen.gtitool.core.util.Theme;
import de.unisiegen.gtitool.logger.Logger;


/**
 * Special {@link JTextField}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITextField extends JTextField
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8082101999864785450L;


  /**
   * The max length.
   */
  private static final int MAX_LENGTH = 1000000;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( JGTITextField.class );


  /**
   * The initial {@link Color}.
   */
  private Color initialColor;


  /**
   * Allocates a new {@link JGTITextField}.
   */
  public JGTITextField ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTITextField}.
   * 
   * @param doc The {@link Document}.
   * @param text The text.
   * @param columns The columns.
   */
  public JGTITextField ( Document doc, String text, int columns )
  {
    super ( doc, text, columns );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextField}.
   * 
   * @param columns The columns.
   */
  public JGTITextField ( int columns )
  {
    super ( columns );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextField}.
   * 
   * @param text The text.
   */
  public JGTITextField ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextField}.
   * 
   * @param text The text.
   * @param columns The columns.
   */
  public JGTITextField ( String text, int columns )
  {
    super ( text, columns );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    this.initialColor = getBackground ();
    setBorder ( new LineBorder ( Color.BLACK, 1 ) );

    setDocument ( new PlainDocument ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = 1L;


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public final void insertString ( int offset, String string,
          AttributeSet attributeSet ) throws BadLocationException
      {
        if ( string == null )
        {
          return;
        }
        if ( getLength () + string.length () <= MAX_LENGTH )
        {
          super.insertString ( offset, string, attributeSet );
        }
        else
        {
          logger.debug ( "insertString", //$NON-NLS-1$
              "text not inserted, larger than " + MAX_LENGTH + "characters" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
      }
    } );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#setEnabled(boolean)
   */
  @Override
  public final void setEnabled ( boolean enabled )
  {
    super.setEnabled ( enabled );
    if ( enabled )
    {
      setBackground ( this.initialColor );
    }
    else
    {
      setBackground ( Theme.DISABLED_COMPONENT_COLOR );
    }
  }
}
