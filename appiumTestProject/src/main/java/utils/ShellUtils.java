package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.testng.Reporter;

public class ShellUtils {
  public StringBuilder executeCommand(String command) {
    StringBuilder output = new StringBuilder();
    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      int exitVal = process.waitFor();
      if (exitVal == 0) {
        Reporter.log("Successfully executed command: " + command);
        Reporter.log("Output is: " + output);
      } else {
        //abnormal...
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  return output;
  }
}
