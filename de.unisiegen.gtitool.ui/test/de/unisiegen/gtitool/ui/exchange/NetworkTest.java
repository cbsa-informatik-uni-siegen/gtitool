package de.unisiegen.gtitool.ui.exchange;


/**
 * The {@link Network} test class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NetworkTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    if ( ( arguments.length > 0 ) && ( arguments [ 0 ].equals ( "--server" ) ) ) //$NON-NLS-1$
    {
      Network network = new Network ( "localhost", 44444 ); //$NON-NLS-1$
      network.listen ();
      System.out.println ( "Server is listening" ); //$NON-NLS-1$
    }
    else if ( ( arguments.length > 0 )
        && ( arguments [ 0 ].equals ( "--client" ) ) ) //$NON-NLS-1$
    {
      Network network = new Network ( "localhost", 44444 ); //$NON-NLS-1$
      network.connect ();
      System.out.println ( "Client is connected" ); //$NON-NLS-1$
    }
    else
    {
      System.out.println ( "Usage: --server | --client" ); //$NON-NLS-1$
    }
  }
}
