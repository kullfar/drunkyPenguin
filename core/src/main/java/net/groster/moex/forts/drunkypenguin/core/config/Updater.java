package net.groster.moex.forts.drunkypenguin.core.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class Updater extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Updater.class);
    private static final String CONFIGURATION_XML_FILE_NAME = "configuration.xml";
    public static final String TEMPLATES_XML_FILE_NAME = "templates.xml";
    private static final String START_CHECKING_LOG_STRING = "Checking for {'configurationXmlFileName'='"
            + CONFIGURATION_XML_FILE_NAME + "', 'templatesXmlFileName'='" + TEMPLATES_XML_FILE_NAME + "'}.";
    private static final String FINISH_CHECKING_LOG_STRING = "Finished checking for {'configurationXmlFileName'='"
            + CONFIGURATION_XML_FILE_NAME + "', 'templatesXmlFileName'='" + TEMPLATES_XML_FILE_NAME + "'}.";
    private static final int PERIOD = 86_400_000; //daily
    private volatile boolean continueWorking = true;
    @Resource
    private String fastConfURI;
    @Resource
    private Updater updater;

    @Override
    public void run() {
        while (continueWorking) {
            LOGGER.info(START_CHECKING_LOG_STRING);

            try {
                checkConfFile(CONFIGURATION_XML_FILE_NAME);
                checkConfFile(TEMPLATES_XML_FILE_NAME);
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

    @PreDestroy
    public void preDestroy() {
        continueWorking = false;
        updater.interrupt();
    }

    private void checkConfFile(final String xmlFileName) throws IOException {
        LOGGER.info("Checking for '" + xmlFileName + "'.");
        final File xmlFile = new File(xmlFileName);
        if (xmlFile.isFile()) {
            try {
                final DateTime dtXmlFile = new DateTime(xmlFile.lastModified());
                final DateTime dtRemoteFile = new DateTime(new URL("http://" + fastConfURI + xmlFileName).
                        openConnection().getLastModified());
                LOGGER.info("Local '" + xmlFileName + "' is from '" + dtXmlFile.toString() + "', remote is from '"
                        + dtRemoteFile.toString() + "'.");
                if (dtXmlFile.isAfter(dtRemoteFile)) {
                    LOGGER.info("Local '" + xmlFileName + "' is newer, no need to do anything.");
                } else {
                    LOGGER.info("Local '" + xmlFileName + "' is older, need to download new one.");
                    final String newFileName = xmlFileName + '.' + LocalDate.now();
                    LOGGER.info("Renaming current '" + xmlFileName + "' to '" + newFileName + "'.");
                    if (xmlFile.renameTo(new File(newFileName))) {
                        LOGGER.info("Renamed current '" + xmlFileName + "' to '" + newFileName + "'.");
                    } else {
                        LOGGER.warn("Something went wrong while renaming current '" + xmlFileName + "' to '"
                                + newFileName + "'.");
                        if (!xmlFile.delete()) {
                            LOGGER.warn("Can not even delete '" + xmlFileName + "'.");
                        }
                    }
                    downloadNewConfFile(xmlFile, xmlFileName);
                }
            } catch (MalformedURLException mURLE) {
                LOGGER.error("{'fastConfURI'='" + fastConfURI + "'} is invalid.", mURLE);
            }
        } else {
            LOGGER.info("There is no '" + xmlFileName + "', need to download it");
            downloadNewConfFile(xmlFile, xmlFileName);
        }
    }

    private void downloadNewConfFile(final File xmlFile, final String xmlFileName) throws IOException {
        LOGGER.info("Start downloading '" + xmlFileName + "'.");

        if (!xmlFile.createNewFile()) {
            LOGGER.error("Can not create {'xmlFile'='" + xmlFile + "'}.");
            throw new IOException();
        }

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) new URL("ftp://"
                + fastConfURI + xmlFileName).getContent(), Charset.forName("UTF-8")));
                FileWriter fileWriter = new FileWriter(xmlFile, true)) {
            String current;
            while ((current = in.readLine()) != null) {
                fileWriter.append(current + '\n');
            }
            fileWriter.flush();
            LOGGER.info("Have downloaded '" + xmlFileName + "'.");
        } catch (MalformedURLException mURLE) {
            LOGGER.error("{'fastConfURI'='" + fastConfURI + "'} is invalid.", mURLE);
        }
    }
}
