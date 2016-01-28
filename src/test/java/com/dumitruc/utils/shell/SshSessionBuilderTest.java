package com.dumitruc.utils.shell;

import com.jcraft.jsch.JSchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dima on 28/01/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SshSessionBuilderTest {

    @Autowired
    @Qualifier("sshServer")
    SshSessionBuilder sshServer;

    @Test
    public void connectionCheck() throws JSchException {
        sshServer.openSshConnection();
        assert (sshServer.isConnected());
    }

}
