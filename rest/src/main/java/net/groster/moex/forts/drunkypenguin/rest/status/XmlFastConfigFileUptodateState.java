package net.groster.moex.forts.drunkypenguin.rest.status;

import javax.xml.bind.annotation.XmlElement;

public class XmlFastConfigFileUptodateState {

    @XmlElement
    private FastConfigFileUptodateCheckingState fastConfigFileUptodateCheckingState;
    @XmlElement
    private String fileName;
    @XmlElement
    private String localFileDateTimeString;
    @XmlElement
    private String remoteFileDateTimeString;
    @XmlElement
    private String lastCheckFinishedDateTimeString;

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setLocalFileDateTimeString(final String localFileDateTimeString) {
        this.localFileDateTimeString = localFileDateTimeString;
    }

    public void setRemoteFileDateTimeString(final String remoteFileDateTimeString) {
        this.remoteFileDateTimeString = remoteFileDateTimeString;
    }

    public void setLastCheckFinishedDateTimeString(final String lastCheckFinishedDateTimeString) {
        this.lastCheckFinishedDateTimeString = lastCheckFinishedDateTimeString;
    }

    public void setFastConfigFileUptodateCheckingState(
            final FastConfigFileUptodateCheckingState fastConfigFileUptodateCheckingState) {
        this.fastConfigFileUptodateCheckingState = fastConfigFileUptodateCheckingState;
    }

}
