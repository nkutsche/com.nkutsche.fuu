package com.nkutsche.fuu.exec;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class JExecTest {

    @Test
    public void test(){
        String file = new File("dummy.xml").getAbsolutePath();
        String uri = new File("dummy.xml").getAbsoluteFile().toURI().toString();
        String[] args = new String[]{"com.nkutsche.fuu.exec.JExecTest", "url(" + file + ")", uri};
        JExec.main(args);
    }


    public static void main(String[] args){
        Assert.assertEquals(2, args.length);
        Assert.assertEquals(args[0], args[1]);
    }
}
