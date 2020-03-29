package com.nkutsche.fuu.ant;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

public class SystemLogger implements BuildListener {
    @Override
    public void buildStarted(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void buildFinished(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void targetStarted(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void targetFinished(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void taskStarted(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void taskFinished(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    @Override
    public void messageLogged(BuildEvent buildEvent) {
        systemLog(buildEvent);
    }

    private void systemLog(BuildEvent event){
        System.out.println(event.getMessage());
    }
}
