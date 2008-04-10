package de.unisiegen.gtitool.start;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
 * @version $Id:Start.java 761 2008-04-10 22:22:51Z fehler $
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
    JFrame jFrameInfo = new JFrame ( Messages.getString (
        "JavaVersion.Title", new Object [] {} ) ); //$NON-NLS-1$
    jFrameInfo.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
    jFrameInfo.setResizable ( false );
    jFrameInfo.setLayout ( new GridBagLayout () );

    GridBagConstraints gridBagConstraints;

    JScrollPane jScrollPaneInfo = new JScrollPane ();
    JTextArea jTextAreaInfo = new JTextArea ();
    JButton jGTIButtonClose = new JButton ();

    jScrollPaneInfo.setBorder ( null );
    jTextAreaInfo.setFocusable ( false );
    jTextAreaInfo.setFont ( new Font ( "Dialog", 1, 12 ) ); //$NON-NLS-1$
    jTextAreaInfo.setOpaque ( false );
    jTextAreaInfo.setBorder ( null );
    jTextAreaInfo.setLineWrap ( true );
    jTextAreaInfo.setWrapStyleWord ( true );
    jTextAreaInfo.setText ( Messages.getString ( "JavaVersion.Message", false, //$NON-NLS-1$
        new Object []
        { new Double ( this.currentJavaVersion ),
            new Double ( MIN_JAVA_VERSION ) } ) );
    jScrollPaneInfo.setViewportView ( jTextAreaInfo );

    gridBagConstraints = new GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new Insets ( 16, 16, 5, 16 );
    jFrameInfo.add ( jScrollPaneInfo, gridBagConstraints );

    jGTIButtonClose.setFocusable ( false );
    jGTIButtonClose.setText ( Messages.getString ( "JavaVersion.Close", //$NON-NLS-1$
        new Object [] {} ) );
    jGTIButtonClose.addActionListener ( new ActionListener ()
    {

      public void actionPerformed ( ActionEvent event )
      {
        System.exit ( 0 );
      }
    } );

    gridBagConstraints = new GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = GridBagConstraints.NONE;
    gridBagConstraints.weightx = 0.0;
    gridBagConstraints.weighty = 0.0;
    gridBagConstraints.insets = new Insets ( 5, 16, 16, 16 );
    jFrameInfo.add ( jGTIButtonClose, gridBagConstraints );

    int rows = 3;
    int columns = 16;
    jTextAreaInfo.setRows ( rows );
    jTextAreaInfo.setColumns ( columns );
    jFrameInfo.pack ();

    int heightViewport = jScrollPaneInfo.getViewport ().getBounds ().height;
    int heightView = jScrollPaneInfo.getViewport ().getView ().getBounds ().height;

    while ( ( rows < 10 ) && ( heightView > heightViewport ) )
    {
      rows++ ;
      columns = columns + 2;
      jTextAreaInfo.setRows ( rows );
      jTextAreaInfo.setColumns ( columns );
      jFrameInfo.pack ();
      heightViewport = jScrollPaneInfo.getViewport ().getBounds ().height;
      heightView = jScrollPaneInfo.getViewport ().getView ().getBounds ().height;
    }

    int screenWidth = Toolkit.getDefaultToolkit ().getScreenSize ().width;
    int screenHeight = Toolkit.getDefaultToolkit ().getScreenSize ().height;
    jFrameInfo.setBounds (
        ( screenWidth / 2 ) - ( jFrameInfo.getWidth () / 2 ),
        ( screenHeight / 2 ) - ( jFrameInfo.getHeight () / 2 ), jFrameInfo
            .getWidth (), jFrameInfo.getHeight () );
    jFrameInfo.setVisible ( true );
  }
}
