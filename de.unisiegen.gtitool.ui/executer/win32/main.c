#include <process.h>
#include <stdio.h>
#include <windows.h>


int WINAPI WinMain (HINSTANCE hThisInstance,
                    HINSTANCE hPrevInstance,
                    LPSTR lpszArgument,
                    int nFunsterStil)

{
  char* args [4];
  int i=0;
  int proc;
  args [i++] = "javaw.exe";
  args [i++] = "-jar";
  args [i++] = "de.unisiegen.gtitool.ui*.jar";
  args [i++] = NULL;
  
  proc = _spawnvp (_P_NOWAIT, args [0],  args);
  if (proc == -1) 
  {  
    MessageBox (0, "No suitable JRE found.\nPlease download ande install a JRE amd make sure the Java binary is in the PATH.", "Error", MB_OK);
  }
  return 0;
}
