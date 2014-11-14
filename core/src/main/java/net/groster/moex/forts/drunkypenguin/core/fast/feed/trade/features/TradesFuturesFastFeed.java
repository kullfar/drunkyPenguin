package net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.features;

import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.AbstractTradesFastFeed;
import org.openfast.MessageHandler;

//<MarketDataGroup feedType="RTS-TRADES" marketID="F" label="Trades for Futures">
@Named
@Singleton
public class TradesFuturesFastFeed extends AbstractTradesFastFeed {

    @Override
    public MarketID getMarketID() {
        return MarketID.FUTURES;
    }

    @Override
    public MessageHandler createTradesIncrementalMessageHandler() {
        return new TradesFuturesIncrementalMessageHandler();
    }
}
