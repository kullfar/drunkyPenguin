package net.groster.moex.forts.drunkypenguin.core.fast;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Configuration;
import net.groster.moex.forts.drunkypenguin.core.fast.config.Checker;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features.InstrumentFuturesFastFeed;
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
    @Inject
    private InstrumentFuturesFastFeed instrumentFuturesFastFeed;

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

    public void start() {
        instrumentFuturesFastFeed.start();
    }

    //unused yet Feeds
    //<MarketDataGroup feedType="RTS-1" marketID="E" marketDepth="1" label="Top of book for RTS Standart">
    //<MarketDataGroup feedType="RTS-1" marketID="F" marketDepth="1" label="Top of book for Futures">
    //<MarketDataGroup feedType="RTS-1" marketID="O" marketDepth="1" label="Top of book for Options">
    //<MarketDataGroup feedType="RTS-1" marketID="S" marketDepth="1" label="Top of Book for Spot">
    //<MarketDataGroup feedType="RTS-20" marketID="E" marketDepth="20" label="Top 20 Price Levels for RTS Standart">
    //<MarketDataGroup feedType="RTS-20" marketID="F" marketDepth="20" label="Top 20 Price Levels for Futures">
    //<MarketDataGroup feedType="RTS-20" marketID="O" marketDepth="20" label="Top 20 Price Levels for Options">
    //<MarketDataGroup feedType="RTS-20" marketID="S" marketDepth="20" label="Top 20 Price Levels for Spot">
    //<MarketDataGroup feedType="RTS-50" marketID="E" marketDepth="50" label="Top 50 Price Levels for RTS Standart">
    //<MarketDataGroup feedType="RTS-50" marketID="F" marketDepth="50" label="Top 50 Price Levels for Futures">
    //<MarketDataGroup feedType="RTS-50" marketID="O" marketDepth="50" label="Top 50 Price Levels for Options">
    //<MarketDataGroup feedType="RTS-5" marketID="E" marketDepth="5" label="Top 5 Price Levels for RTS Standart">
    //<MarketDataGroup feedType="RTS-5" marketID="F" marketDepth="5" label="Top 5 Price Levels for Futures">
    //<MarketDataGroup feedType="RTS-5" marketID="O" marketDepth="5" label="Top 5 Price Levels for Options">
    //<MarketDataGroup feedType="RTS-INDEX" marketID="I" label="RTS Index">
    //<MarketDataGroup feedType="RTS-INSTR" marketID="E" label="RTS Standart">
    //<MarketDataGroup feedType="RTS-INSTR" marketID="O" label="All Options defintion">
    //<MarketDataGroup feedType="RTS-INSTR" marketID="S" label="RTS Classica instruments">
    //<MarketDataGroup feedType="RTS-NEWS-SYS" marketID="F" label="News feed">
    //<MarketDataGroup feedType="RTS-TRADES" marketID="E" label="Trades for RTS Standart">
    //<MarketDataGroup feedType="RTS-TRADES" marketID="F" label="Trades for Futures">
    //<MarketDataGroup feedType="RTS-TRADES" marketID="O" label="Trades for Options">
    //<MarketDataGroup feedType="RTS-TRADES" marketID="S" label="Trades for Spot">
    public MarketDataGroup getMarketDataGroup(final FeedType feedType, final MarketID marketId) {
        for (final MarketDataGroup marketDataGroup : configuration.getMarketDataGroups()) {
            if (marketDataGroup.getFeedType() == feedType && marketDataGroup.getMarketID() == marketId) {
                return marketDataGroup;
            }
        }
        return null;
    }

    public MessageTemplate[] getTemplates() {
        return templates;
    }

}
