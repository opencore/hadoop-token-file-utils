package com.opencore;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;

public class DelegationTokenLister {

  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Requires one parameter: <token file location>");
      System.exit(1);
    }

    String fileLocation = args[0];
    File source = new File(fileLocation);
    if (!source.isFile()) {
      System.err.println("Source file " + source.getCanonicalPath() + " not found");
      System.exit(1);
    }

    Credentials cred = Credentials.readTokenStorageFile(source, null);
    for (Token<? extends TokenIdentifier> token : cred.getAllTokens()) {
      System.out.println(token.getKind() + "=" + token.encodeToUrlString());
    }
  }

}
