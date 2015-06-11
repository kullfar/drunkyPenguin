package net.groster.moex.forts.drunkypenguin.core.fast.config;

import org.joda.time.DateTime;

public class FASTConfigFileUptodateState {

    private DateTime remoteFileDateTime;
    private DateTime lastCheckFinishedDateTime;
    private boolean uptodate;

    public void setRemoteFileDateTime(final DateTime remoteFileDateTime) {
        this.remoteFileDateTime = remoteFileDateTime;
    }

    public void setLastCheckFinishedDateTime(final DateTime lastCheckFinishedDateTime) {
        this.lastCheckFinishedDateTime = lastCheckFinishedDateTime;
    }

    public void setUptodate(final boolean uptodate) {
        this.uptodate = uptodate;
    }

    public DateTime getRemoteFileDateTime() {
        return remoteFileDateTime;
    }

    public DateTime getLastCheckFinishedDateTime() {
        return lastCheckFinishedDateTime;
    }

    public boolean isUptodate() {
        return uptodate;
    }

}
