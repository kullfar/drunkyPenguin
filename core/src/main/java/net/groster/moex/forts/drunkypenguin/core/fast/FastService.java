package net.groster.moex.forts.drunkypenguin.core.fast;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Configuration;
import net.groster.moex.forts.drunkypenguin.core.fast.config.Checker;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class FastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastService.class);
    private MessageTemplate[] templates;
    private Configuration configuration;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Loading FAST templates.");
        final XMLMessageTemplateLoader xmlMessageTemplateLoader = new XMLMessageTemplateLoader();
        try (InputStream templateSource = ClassLoader.getSystemResourceAsStream(Checker.TEMPLATES_XML_FILE_NAME)) {
            templates = xmlMessageTemplateLoader.load(templateSource);
            if (templates.length != MessageType.values().length) {
                LOGGER.error("Wrong templates number. {'templates.length'='" + templates.length
                        + "'} != {'MessageType.values().length'='" + MessageType.values().length + "'}.");
            }
            for (final MessageTemplate template : templates) {
                MessageType.getByName(template.getName()).setId(Integer.parseInt(template.getId()));
            }
            LOGGER.info("Loaded FAST templates successfully.");
        } catch (IOException ioE) {
        }

        LOGGER.info("Loading FAST configuration.");
        try (InputStream configurationSource = ClassLoader.
                getSystemResourceAsStream(Checker.CONFIGURATION_XML_FILE_NAME)) {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(Configuration.class).createUnmarshaller();
            configuration = (Configuration) unmarshaller.unmarshal(configurationSource);
            LOGGER.info("Loaded FAST configuration successfully.");
            //Use next line for testing. Write parsed xml file to disk and compare it with original
            //JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(configuration, new File("conf.xml"));
        } catch (IOException ioE) {
        } catch (JAXBException jaxbE) {
            throw new RuntimeException("It's fatal, dunno what to do with this", jaxbE);
        }
    }

}
