package com.dumitruc.utils.shell;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dima on 28/01/2016.
 */
public class SshSessionBuilder {

    public static int MAX_RETRY_COUNT = 100;
    public static int SLEEP_MILLISECONDS = 1000;

    private String username;
    private String password;
    private String destinationHost;
    private int connectionPort;
    private String privateKeyLocation;
    private Session session;


    public SshSessionBuilder(String username, String password, String destinationHost, int connectionPort) {
        this.username = username;
        this.password = password;
        this.destinationHost = destinationHost;
        this.connectionPort = connectionPort;
    }

    public SshSessionBuilder(String username, File publicKey, String destinationHost, int connectionPort) throws FileNotFoundException {
        this.username = username;
        this.destinationHost = destinationHost;
        this.connectionPort = connectionPort;
        if (publicKey.exists() && publicKey.canRead()) {
            this.privateKeyLocation = publicKey.getAbsolutePath();
        } else {
            throw new FileNotFoundException("Could not read public key: " + publicKey.getAbsolutePath() + "!");
        }
    }

    public void openSshConnection() throws JSchException {
        JSch jSch = new JSch();
        if (privateKeyLocation != null) {
            session = jSch.getSession(username, destinationHost, connectionPort);
            jSch.addIdentity(privateKeyLocation);
        } else {
            session = jSch.getSession(username, destinationHost, connectionPort);
            session.setPassword(password);
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password"); //Skipping kerberos authentication
        session.connect(SLEEP_MILLISECONDS);
    }

    public boolean isConnected() {
        if (session == null) {
            return false;
        }
        return session.isConnected();
    }

}
