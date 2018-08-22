package com.opencore;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;

public class DelegationTokenReader {

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Requires two parameters: <token file location> <kind>");
      System.exit(1);
    }

    String fileLocation = args[0];
    File source = new File(fileLocation);
    if (!source.isFile()) {
      System.err.println("Source file " + source.getCanonicalPath() + " not found");
      System.exit(1);
    }

    Credentials cred = Credentials.readTokenStorageFile(source, null);
    Text kind = new Text(args[1]);
    for (Token<? extends TokenIdentifier> token : cred.getAllTokens()) {
      if (token.getKind().equals(kind)) {
        System.out.println(token.encodeToUrlString());
        System.exit(0);
      }
    }

    System.err.println("No token of kind " + args[1] + " found");
    System.exit(1);
  }

}
