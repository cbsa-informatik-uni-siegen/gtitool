package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import de.unisiegen.gtitool.core.util.Theme;
import de.unisiegen.gtitool.logger.Logger;


/**
 * Special {@link JTextArea}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITextArea extends JTextArea
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6856520361707837256L;


  /**
   * The max length.
   */
  private static final int MAX_LENGTH = 1000000;


  /**
   * The {@link Logger} for this class.
   */
  protected static final Logger logger = Logger.getLogger ( JGTITextArea.class );


  /**
   * The initial {@link Color}.
   */
  private Color initialColor;


  /**
   * Allocates a new {@link JGTITextArea}.
   */
  public JGTITextArea ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTITextArea}.
   * 
   * @param doc The {@link Document}.
   */
  public JGTITextArea ( Document doc )
  {
    super ( doc );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextArea}.
   * 
   * @param doc The {@link Document}.
   * @param text The text.
   * @param rows The rows.
   * @param columns The columns.
   */
  public JGTITextArea ( Document doc, String text, int rows, int columns )
  {
    super ( doc, text, rows, columns );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextArea}.
   * 
   * @param rows The rows.
   * @param columns The columns.
   */
  public JGTITextArea ( int rows, int columns )
  {
    super ( rows, columns );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextArea}.
   * 
   * @param text The text.
   */
  public JGTITextArea ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTITextArea}.
   * 
   * @param text The text.
   * @param rows The rows.
   * @param columns The columns.
   */
  public JGTITextArea ( String text, int rows, int columns )
  {
    super ( text, rows, columns );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    this.initialColor = getBackground ();
    setBorder ( null );
    setLineWrap ( true );
    setWrapStyleWord ( true );

    setDocument ( new PlainDocument ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = 1L;


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
