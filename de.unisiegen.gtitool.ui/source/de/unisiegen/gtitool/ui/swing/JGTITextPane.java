package de.unisiegen.gtitool.ui.swing;


import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import de.unisiegen.gtitool.logger.Logger;


/**
 * Special {@link JTextPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITextPane extends JTextPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3335212157343560547L;


  /**
   * The max length.
   */
  private static final int MAX_LENGTH = 1000000;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( JGTITextPane.class );


  /**
   * Allocates a new {@link JGTITextPane}.
   */
  public JGTITextPane ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTITextPane}.
   * 
   * @param doc The {@link StyledDocument}.
   */
  public JGTITextPane ( StyledDocument doc )
  {
    super ( doc );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setBorder ( null );

    setDocument ( new DefaultStyledDocument ()
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
}
