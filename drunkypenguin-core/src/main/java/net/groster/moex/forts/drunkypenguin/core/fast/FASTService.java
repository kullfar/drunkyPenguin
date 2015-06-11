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
import net.groster.moex.forts.drunkypenguin.core.fast.config.FASTConfigsUpdatesChecker;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Configuration;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features.InstrumentFuturesFastFeed;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.options.InstrumentOptionsFastFeed;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.features.TradesFuturesFastFeed;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.options.TradesOptionsFastFeed;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class FASTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FASTService.class);
    private MessageTemplate[] templates;
    private Configuration configuration;
    @Inject
    private InstrumentFuturesFastFeed instrumentFuturesFastFeed;
    @Inject
    private InstrumentOptionsFastFeed instrumentOptionsFastFeed;
    @Inject
    private TradesFuturesFastFeed tradesFuturesFastFeed;
    @Inject
    private TradesOptionsFastFeed tradesOptionsFastFeed;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Loading FAST templates.");
        final XMLMessageTemplateLoader xmlMessageTemplateLoader = new XMLMessageTemplateLoader();
        try (InputStream templateSource = ClassLoader.getSystemResourceAsStream(FASTConfigsUpdatesChecker.TEMPLATES_XML_FILE_NAME)) {
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
                getSystemResourceAsStream(FASTConfigsUpdatesChecker.CONFIGURATION_XML_FILE_NAME)) {
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
        instrumentOptionsFastFeed.start();
        tradesFuturesFastFeed.start();
        tradesOptionsFastFeed.start();
    }

    //TODO:unused yet Feeds, use'em
    //<MarketDataGroup feedType="RTS-50" marketID="F" marketDepth="50" label="Top 50 Price Levels for Futures">
    //<MarketDataGroup feedType="RTS-50" marketID="O" marketDepth="50" label="Top 50 Price Levels for Options">
    //<MarketDataGroup feedType="RTS-INDEX" marketID="I" label="MOEX Indexes">
    //<MarketDataGroup feedType="RTS-NEWS-SYS" marketID="F" label="News feed">
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
