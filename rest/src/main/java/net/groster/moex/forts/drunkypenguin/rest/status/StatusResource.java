package net.groster.moex.forts.drunkypenguin.rest.status;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.FASTConfigFileUptodateState;
import net.groster.moex.forts.drunkypenguin.core.fast.config.FASTConfigsUpdatesChecker;
import org.joda.time.DateTime;

@Path("status")
@Singleton
public class StatusResource {

    @Inject
    private FASTConfigsUpdatesChecker fastConfigsUpdatesChecker;

    @GET
    @Produces(MediaType.TEXT_XML)
    public XmlStatus get() {
        final XmlStatus xmlStatus = new XmlStatus();
        final Set<XmlFastConfigFileUptodateState> xmlFastConfigFileUptodateStates = new HashSet<>(2);
        xmlStatus.setXmlFastConfigFileUptodateStates(xmlFastConfigFileUptodateStates);

        xmlFastConfigFileUptodateStates.add(getXmlFastConfigFileUptodateState(
                FASTConfigsUpdatesChecker.CONFIGURATION_XML_FILE_NAME, fastConfigsUpdatesChecker.
                getConfigurationXmlUptodateState(), fastConfigsUpdatesChecker.getCurrentConfigurationXmlDateTimeString()));
        xmlFastConfigFileUptodateStates.add(getXmlFastConfigFileUptodateState(
                FASTConfigsUpdatesChecker.TEMPLATES_XML_FILE_NAME, fastConfigsUpdatesChecker.
                getTemplateXmlUptodateState(), fastConfigsUpdatesChecker.getCurrentTemplateXmlDateTimeString()));
        return xmlStatus;
    }

    private static XmlFastConfigFileUptodateState getXmlFastConfigFileUptodateState(final String fileName,
            final FASTConfigFileUptodateState fastConfigFileUptodateState, final String localFileDateTimeString) {
        final XmlFastConfigFileUptodateState xmlFastConfigFileUptodateState = new XmlFastConfigFileUptodateState();
        xmlFastConfigFileUptodateState.setFileName(fileName);
        xmlFastConfigFileUptodateState.setLocalFileDateTimeString(localFileDateTimeString);

        final DateTime lastCheckFinishedDateTime;
        final DateTime remoteFileDateTime;
        final boolean uptodate;
        synchronized (fastConfigFileUptodateState) {
            lastCheckFinishedDateTime = fastConfigFileUptodateState.getLastCheckFinishedDateTime();
            remoteFileDateTime = fastConfigFileUptodateState.getRemoteFileDateTime();
            uptodate = fastConfigFileUptodateState.isUptodate();
        }

        xmlFastConfigFileUptodateState.setRemoteFileDateTimeString(remoteFileDateTime == null ? "null"
                : remoteFileDateTime.toString());
        xmlFastConfigFileUptodateState.setLastCheckFinishedDateTimeString(lastCheckFinishedDateTime == null ? "null"
                : lastCheckFinishedDateTime.toString());
        xmlFastConfigFileUptodateState.setFastConfigFileUptodateCheckingState(lastCheckFinishedDateTime == null
                ? FastConfigFileUptodateCheckingState.CHECKING : uptodate ? FastConfigFileUptodateCheckingState.OK
                : FastConfigFileUptodateCheckingState.FAIL);

        return xmlFastConfigFileUptodateState;
    }
}
