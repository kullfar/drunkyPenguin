package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Event;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.FuturesSecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.InstrumentLeg;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.MDFeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.OptionsSecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Underlying;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketSegmentID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.SecurityType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.TradingSessionID;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;
import org.openfast.SequenceValue;

public class SecurityDefinition extends SecurityDefinitionUpdateReport implements FuturesSecurityDefinition, OptionsSecurityDefinition {

    private final int totNumReports;
    private final String symbol;
    private final String securityDesc;
    private final String cfiCode;
    private final BigDecimal minPriceIncrement;
    private final String currency;
    private final BigDecimal lowLimitPx;
    private final BigDecimal highLimitPx;
    private final MarketSegmentID marketSegmentID;
    private final MarketID marketID;
    private final Integer securityTradingStatus;
    private final List<MDFeedType> mdFeedTypes;
    private final SecurityType securityType;
    private final BigDecimal strikePrice;
    private final BigDecimal contractMultiplier;
    private final TradingSessionID tradingSessionID;
    private final Integer exchangeTradingSessionID;
    private List<Underlying> underlyings;
    private final BigDecimal minPriceIncrementAmount;
    private final BigDecimal initialMarginOnBuy;
    private final BigDecimal initialMarginOnSell;
    private final BigDecimal initialMarginSyntetic;
    private List<InstrumentLeg> instrumentLegs;
    private List<Event> evntGrp;
    private final LocalDate maturityDate;
    private final LocalTime maturityTime;

    public SecurityDefinition(final Message fastMessage) {
        super(fastMessage);
        totNumReports = fastMessage.getInt("TotNumReports");
        symbol = fastMessage.getString("Symbol");
        securityDesc = fastMessage.getString("SecurityDesc");
        cfiCode = fastMessage.getString("CFICode");

        final ScalarValue minPriceIncrementScalarValue = fastMessage.getScalar("MinPriceIncrement");
        minPriceIncrement = minPriceIncrementScalarValue == null ? null : minPriceIncrementScalarValue.toBigDecimal();

        currency = fastMessage.getString("Currency");

        final ScalarValue lowLimitPxScalarValue = fastMessage.getScalar("LowLimitPx");
        lowLimitPx = lowLimitPxScalarValue == null ? null : lowLimitPxScalarValue.toBigDecimal();

        final ScalarValue highLimitPxScalarValue = fastMessage.getScalar("HighLimitPx");
        highLimitPx = highLimitPxScalarValue == null ? null : highLimitPxScalarValue.toBigDecimal();

        marketSegmentID = MarketSegmentID.valueOf(fastMessage.getString("MarketSegmentID"));
        marketID = MarketID.valueOf(fastMessage.getString("MarketID"));

        final ScalarValue securityTradingStatusScalarValue = fastMessage.getScalar("SecurityTradingStatus");
        securityTradingStatus = securityTradingStatusScalarValue == null ? null : securityTradingStatusScalarValue.
                toInt();

        final GroupValue[] mdFeedTypesArray = fastMessage.getSequence("MDFeedTypes").getValues();
        final List<MDFeedType> mdFeedTypesList = new ArrayList<>(mdFeedTypesArray.length);
        for (final GroupValue mdFeedTypeValue : mdFeedTypesArray) {
            final MDFeedType mdFeedType = new MDFeedType();

            mdFeedType.setMdFeedType(mdFeedTypeValue.getString("MDFeedType"));

            final ScalarValue MarketDepthScalarValue = mdFeedTypeValue.getScalar("MarketDepth");
            mdFeedType.setMarketDepth(MarketDepthScalarValue == null ? null : MarketDepthScalarValue.toInt());

            final ScalarValue MDBookTypeScalarValue = mdFeedTypeValue.getScalar("MDBookType");
            mdFeedType.setMdBookType(MDBookTypeScalarValue == null ? null : MDBookTypeScalarValue.toInt());

            mdFeedTypesList.add(mdFeedType);
        }
        mdFeedTypes = mdFeedTypesList;

        final String securityTypeString = fastMessage.getString("SecurityType");
        securityType = securityTypeString == null ? null : SecurityType.valueOf(securityTypeString);

        final ScalarValue strikePriceScalarValue = fastMessage.getScalar("StrikePrice");
        strikePrice = strikePriceScalarValue == null ? null : strikePriceScalarValue.toBigDecimal();

        final ScalarValue contractMultiplierScalarValue = fastMessage.getScalar("ContractMultiplier");
        contractMultiplier = contractMultiplierScalarValue == null ? null : contractMultiplierScalarValue.toBigDecimal();

        final ScalarValue tradingSessionIDScalarValue = fastMessage.getScalar("TradingSessionID");
        tradingSessionID = tradingSessionIDScalarValue == null ? null : TradingSessionID.valueOf(tradingSessionIDScalarValue.toInt());

        final ScalarValue exchangeTradingSessionIDScalarValue = fastMessage.getScalar("ExchangeTradingSessionID");
        exchangeTradingSessionID = exchangeTradingSessionIDScalarValue == null ? null
                : exchangeTradingSessionIDScalarValue.toInt();

        final SequenceValue underlyingsSequenceValue = fastMessage.getSequence("Underlyings");
        if (underlyingsSequenceValue != null) {
            final GroupValue[] underlyingsArray = underlyingsSequenceValue.getValues();
            final List<Underlying> underlyingsList = new ArrayList<>(underlyingsArray.length);
            for (final GroupValue underlyingValue : underlyingsArray) {
                final Underlying underlying = new Underlying();
                underlying.setUnderlyingSymbol(underlyingValue.getString("UnderlyingSymbol"));

                final ScalarValue underlyingSecurityIDScalarValue = underlyingValue.getScalar(
                        "UnderlyingSecurityID");
                underlying.setUnderlyingSecurityID(underlyingSecurityIDScalarValue == null ? null
                        : underlyingSecurityIDScalarValue.toLong());

                underlyingsList.add(underlying);
            }
            underlyings = underlyingsList;
        }

        final ScalarValue minPriceIncrementAmountScalarValue = fastMessage.getScalar(
                "MinPriceIncrementAmount");
        minPriceIncrementAmount = minPriceIncrementAmountScalarValue == null ? null
                : minPriceIncrementAmountScalarValue.toBigDecimal();

        final ScalarValue initialMarginOnBuyScalarValue = fastMessage.getScalar("InitialMarginOnBuy");
        initialMarginOnBuy = initialMarginOnBuyScalarValue == null ? null : initialMarginOnBuyScalarValue.toBigDecimal();

        final ScalarValue initialMarginOnSellScalarValue = fastMessage.getScalar("InitialMarginOnSell");
        initialMarginOnSell = initialMarginOnSellScalarValue == null ? null : initialMarginOnSellScalarValue.
                toBigDecimal();

        final ScalarValue initialMarginSynteticScalarValue = fastMessage.getScalar("InitialMarginSyntetic");
        initialMarginSyntetic = initialMarginSynteticScalarValue == null ? null : initialMarginSynteticScalarValue.
                toBigDecimal();

        final SequenceValue instrumentLegsSequenceValue = fastMessage.getSequence("InstrumentLegs");
        if (instrumentLegsSequenceValue != null) {
            final GroupValue[] instrumentLegsArray = instrumentLegsSequenceValue.getValues();
            final List<InstrumentLeg> instrumentLegsList = new ArrayList<>(instrumentLegsArray.length);
            for (final GroupValue instrumentLegValue : instrumentLegsArray) {
                final InstrumentLeg instrumentLeg = new InstrumentLeg();
                instrumentLeg.setLegSymbol(instrumentLegValue.getString("LegSymbol"));
                instrumentLeg.setLegSecurityID(instrumentLegValue.getLong("LegSecurityID"));
                instrumentLeg.setLegRatioQty(instrumentLegValue.getBigDecimal("LegRatioQty"));
                instrumentLegsList.add(instrumentLeg);
            }
            instrumentLegs = instrumentLegsList;
        }

        final SequenceValue evntGrpSequenceValue = fastMessage.getSequence("EvntGrp");
        if (evntGrpSequenceValue != null) {
            final GroupValue[] eventsArray = evntGrpSequenceValue.getValues();
            final List<Event> events = new ArrayList<>(eventsArray.length);
            for (final GroupValue eventValue : eventsArray) {
                final Event event = new Event();
                event.setEventType(eventValue.getInt("EventType"));
                event.setEventDate(MessageType.FAST_DATE_UTC_FORMATTER.parseDateTime(Integer.toString(eventValue.getInt(
                        "EventDate"))).withZone(Constants.MOEX_TIME_ZONE).toLocalDate());
                event.setEventTime(MessageType.FAST_DATETIME_UTC_FORMATTER.parseDateTime(Long.toString(eventValue.
                        getLong("EventTime"))).withZone(Constants.MOEX_TIME_ZONE));
                events.add(event);
            }
            evntGrp = events;
        }

        final ScalarValue maturityDateScalarValue = fastMessage.getScalar("MaturityDate");
        maturityDate = maturityDateScalarValue == null ? null : MessageType.FAST_DATE_UTC_FORMATTER.parseDateTime(
                Integer.toString(maturityDateScalarValue.toInt())).withZone(Constants.MOEX_TIME_ZONE).toLocalDate();

        final ScalarValue maturityTimeScalarValue = fastMessage.getScalar("MaturityTime");
        maturityTime = maturityTimeScalarValue == null ? null : MessageType.FAST_TIME_UTC_FORMATTER.parseDateTime(
                Integer.toString(maturityTimeScalarValue.toInt())).withZone(Constants.MOEX_TIME_ZONE).toLocalTime();
    }

    @Override
    public int getTotNumReports() {
        return totNumReports;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSecurityDesc() {
        return securityDesc;
    }

    @Override
    public String getCfiCode() {
        return cfiCode;
    }

    @Override
    public BigDecimal getMinPriceIncrement() {
        return minPriceIncrement;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getLowLimitPx() {
        return lowLimitPx;
    }

    @Override
    public BigDecimal getHighLimitPx() {
        return highLimitPx;
    }

    @Override
    public MarketSegmentID getMarketSegmentID() {
        return marketSegmentID;
    }

    @Override
    public MarketID getMarketID() {
        return marketID;
    }

    @Override
    public Integer getSecurityTradingStatus() {
        return securityTradingStatus;
    }

    @Override
    public List<MDFeedType> getMdFeedTypes() {
        return mdFeedTypes;
    }

    @Override
    public SecurityType getSecurityType() {
        return securityType;
    }

    @Override
    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    @Override
    public BigDecimal getContractMultiplier() {
        return contractMultiplier;
    }

    @Override
    public TradingSessionID getTradingSessionID() {
        return tradingSessionID;
    }

    @Override
    public Integer getExchangeTradingSessionID() {
        return exchangeTradingSessionID;
    }

    @Override
    public List<Underlying> getUnderlyings() {
        return underlyings;
    }

    @Override
    public BigDecimal getMinPriceIncrementAmount() {
        return minPriceIncrementAmount;
    }

    @Override
    public BigDecimal getInitialMarginOnBuy() {
        return initialMarginOnBuy;
    }

    @Override
    public BigDecimal getInitialMarginOnSell() {
        return initialMarginOnSell;
    }

    @Override
    public BigDecimal getInitialMarginSyntetic() {
        return initialMarginSyntetic;
    }

    @Override
    public List<InstrumentLeg> getInstrumentLegs() {
        return instrumentLegs;
    }

    @Override
    public List<Event> getEvntGrp() {
        return evntGrp;
    }

    @Override
    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    @Override
    public LocalTime getMaturityTime() {
        return maturityTime;
    }

}
