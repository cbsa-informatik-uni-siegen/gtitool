package de.unisiegen.gtitool.start;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.ui.GTITool;


/**
 * This is the start class, which checks the java version and shows a error
 * message if the java version is to old, or if the java version is correct,
 * starts the GTI Tool project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Start
{

  /**
   * The minimum java version.
   */
  private static final double MIN_JAVA_VERSION = 1.5;


  /**
   * The main method.
   * 
   * @param arguments The command line arguments.
   */
  public final static void main ( String [] arguments )
  {
    Locale.setDefault ( Locale.GERMAN );
    new Start ( arguments );
  }


  /**
   * The current java version.
   */
  private double currentJavaVersion = 1.0;


  /**
   * Allocates a new {@link Start}.
   * 
   * @param arguments The command line arguments.
   */
  public Start ( String [] arguments )
  {
    if ( isJavaVersionCorrect () )
    {
      new GTITool ( arguments );
    }
    else
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        public void run ()
        {
          showJavaVersionError ();
        }
      } );
    }
  }


  /**
   * Returns true if the java version is correct, otherwise false.
   * 
   * @return True if the java version is correct, otherwise false.
   */
  private final boolean isJavaVersionCorrect ()
  {
    try
    {
      this.currentJavaVersion = Double.parseDouble ( System.getProperty (
          "java.version" ).substring ( 0, 3 ) ); //$NON-NLS-1$
      return this.currentJavaVersion >= MIN_JAVA_VERSION;
    }
    catch ( Exception exc )
    {
      return false;
    }
  }


  /**
   * Shows the java version error {@link JFrame}.
   */
  public final void showJavaVersionError ()
  {
    JFrame jFrameJavaVersion = new JFrame ( MessagesStart.getString (
        "JavaVersion.Title", new Object [] {} ) ); //$NON-NLS-1$
    jFrameJavaVersion.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
    jFrameJavaVersion.setResizable ( false );
    jFrameJavaVersion.setLayout ( new GridBagLayout () );
    jFrameJavaVersion.setLayout ( new GridBagLayout () );

    JScrollPane jScrollPaneInfo = new JScrollPane ();
    jScrollPaneInfo.setBorder ( null );

    JTextArea jTextAreaInfo = new JTextArea ();
    jTextAreaInfo.setFont ( jTextAreaInfo.getFont ()
        .deriveFont ( Font.BOLD, 14 ) );
    jTextAreaInfo.setColumns ( 16 );
    jTextAreaInfo.setRows ( 6 );
    jTextAreaInfo.setEditable ( false );
    jTextAreaInfo.setLineWrap ( true );
    jTextAreaInfo.setWrapStyleWord ( true );
    jTextAreaInfo.setText ( MessagesStart.getString ( "JavaVersion.Message", //$NON-NLS-1$
        false, new Object []
        { new Double ( this.currentJavaVersion ),
            new Double ( MIN_JAVA_VERSION ) } ) );
    jScrollPaneInfo.setViewportView ( jTextAreaInfo );

    GridBagConstraints gridBagConstraints = new GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jFrameJavaVersion.add ( jScrollPaneInfo, gridBagConstraints );

    jFrameJavaVersion.pack ();
    int screenWidth = Toolkit.getDefaultToolkit ().getScreenSize ().width;
    int screenHeight = Toolkit.getDefaultToolkit ().getScreenSize ().height;
    jFrameJavaVersion.setBounds ( ( screenWidth / 2 )
        - ( jFrameJavaVersion.getWidth () / 2 ), ( screenHeight / 2 )
        - ( jFrameJavaVersion.getHeight () / 2 ),
        jFrameJavaVersion.getWidth (), jFrameJavaVersion.getHeight () );
    jFrameJavaVersion.setVisible ( true );
  }
}
