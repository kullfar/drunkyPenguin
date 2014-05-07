package net.groster.moex.forts.drunkypenguin.core.fast.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class FASTConfigsUpdatesChecker extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(FASTConfigsUpdatesChecker.class);
    public static final String CONFIGURATION_XML_FILE_NAME = "configuration.xml";
    public static final String TEMPLATES_XML_FILE_NAME = "templates.xml";
    private static final String START_CHECKING_LOG_STRING = "Checking for updates for {'configurationXmlFileName'='"
            + CONFIGURATION_XML_FILE_NAME + "', 'templatesXmlFileName'='" + TEMPLATES_XML_FILE_NAME + "'}.";
    private static final String FINISH_CHECKING_LOG_STRING
            = "Finished checking for updates for {'configurationXmlFileName'='"
            + CONFIGURATION_XML_FILE_NAME + "', 'templatesXmlFileName'='" + TEMPLATES_XML_FILE_NAME + "'}.";
    private static final int PERIOD = 86_400_000; //daily
    private volatile boolean continueWorking = true;
    @Resource
    private String fastConfURL;
    @Resource
    private Long currentConfigurationXmlTimestamp;
    private String currentConfigurationXmlDateTimeString;
    private final FASTConfigFileUptodateState configurationXmlUptodateState = new FASTConfigFileUptodateState();
    @Resource
    private Long currentTemplateXmlTimestamp;
    private String currentTemplateXmlDateTimeString;
    private final FASTConfigFileUptodateState templateXmlUptodateState = new FASTConfigFileUptodateState();

    @Override
    public void run() {
        while (continueWorking) {
            LOGGER.info(START_CHECKING_LOG_STRING);
            try {
                checkConfFile(CONFIGURATION_XML_FILE_NAME, currentConfigurationXmlTimestamp,
                        currentConfigurationXmlDateTimeString, configurationXmlUptodateState);
                checkConfFile(TEMPLATES_XML_FILE_NAME, currentTemplateXmlTimestamp, currentTemplateXmlDateTimeString,
                        templateXmlUptodateState);
            } catch (IOException iOE) {
                LOGGER.error("Smth wrong.", iOE);
            }

            LOGGER.info(FINISH_CHECKING_LOG_STRING);
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException iE) {
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        currentConfigurationXmlDateTimeString = new DateTime(currentConfigurationXmlTimestamp).toString();
        currentTemplateXmlDateTimeString = new DateTime(currentTemplateXmlTimestamp).toString();

    }

    @PreDestroy
    public void preDestroy() {
        continueWorking = false;
        interrupt();
    }

    private void checkConfFile(final String xmlFileName, final long currentXmlTimestamp,
            final String currentXmlFileDateTimeString, final FASTConfigFileUptodateState configFileUptodateState) throws
            IOException {
        LOGGER.info("Checking for '" + xmlFileName + "'.");
        try {
            final String remoteXmlFileUrl = fastConfURL + xmlFileName;

            final HttpURLConnection httpURLConnection = ((HttpURLConnection) new URL(remoteXmlFileUrl).openConnection());
            httpURLConnection.setRequestMethod("HEAD");
            final long remoteFileTimestamp = httpURLConnection.getLastModified();

            final DateTime remoteFileDateTime;
            final boolean uptodate;

            if (remoteFileTimestamp == 0) {
                LOGGER.error("{'url'='" + remoteXmlFileUrl + "'} returned {'responseCode'='" + httpURLConnection.getResponseCode() + "'}.");
                remoteFileDateTime = null;
                uptodate = false;
            } else {
                remoteFileDateTime = new DateTime(remoteFileTimestamp);
                uptodate = currentXmlTimestamp >= remoteFileTimestamp;
            }

            synchronized (configFileUptodateState) {
                configFileUptodateState.setRemoteFileDateTime(remoteFileDateTime);
                configFileUptodateState.setLastCheckFinishedDateTime(DateTime.now());
                configFileUptodateState.setUptodate(uptodate);
            }

            final StringBuilder logStatementBuilder = new StringBuilder("Local '").append(xmlFileName).append(
                    "' is from {'dt'='").append(currentXmlFileDateTimeString).append("', 'ts'='").append(
                            currentXmlTimestamp).append("'}, {'remote'='").append(remoteXmlFileUrl).append(
                            "'} is from {'dt'='").append(remoteFileDateTime).append("', 'ts'='").append(
                            remoteFileTimestamp).append("'}. Local file is ");
            if (uptodate) {
                LOGGER.info(logStatementBuilder.append("newer, no need to do anything.").toString());
            } else {
                LOGGER.error(logStatementBuilder.append(
                        "older, need to download new one. And update drunkyPenguin version.").toString());
            }
        } catch (MalformedURLException mURLE) {
            LOGGER.error("{'fastConfURL'='" + fastConfURL + "'} is invalid.", mURLE);
        }
    }

    public FASTConfigFileUptodateState getConfigurationXmlUptodateState() {
        return configurationXmlUptodateState;
    }

    public FASTConfigFileUptodateState getTemplateXmlUptodateState() {
        return templateXmlUptodateState;
    }

    public String getCurrentConfigurationXmlDateTimeString() {
        return currentConfigurationXmlDateTimeString;
    }

    public String getCurrentTemplateXmlDateTimeString() {
        return currentTemplateXmlDateTimeString;
    }
}
