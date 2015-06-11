package net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.options;

import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.AbstractTradesFastFeed;
import org.openfast.MessageHandler;

//<MarketDataGroup feedType="RTS-TRADES" marketID="O" label="Trades for Options">
@Named
@Singleton
public class TradesOptionsFastFeed extends AbstractTradesFastFeed {

    @Override
    public MarketID getMarketID() {
        return MarketID.OPTIONS;
    }

    @Override
    public MessageHandler createTradesIncrementalMessageHandler() {
        return new TradesOptionsIncrementalMessageHandler();
    }
}
