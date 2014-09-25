package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.SecurityType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractSecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Event;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.InstrumentAttribute;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.InstrumentLeg;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Underlying;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;
import org.openfast.SequenceValue;

public class SecurityDefinition extends AbstractSecurityDefinition {

    private final String securityAltID;
    private final String securityAltIDSource;
    private final SecurityType securityType;
    private final BigDecimal strikePrice;
    private final BigDecimal contractMultiplier;
    private final Integer tradingSessionID;
    private final Integer exchangeTradingSessionID;
    private final BigDecimal volatility;
    private List<Underlying> underlyings;
    private final BigDecimal minPriceIncrementAmount;
    private final BigDecimal initialMarginOnBuy;
    private final BigDecimal initialMarginOnSell;
    private final BigDecimal initialMarginSyntetic;
    private final String quotationList;
    private final BigDecimal theorPrice;
    private final BigDecimal theorPriceLimit;
    private List<InstrumentLeg> instrumentLegs;
    private List<InstrumentAttribute> instrumentAttributes;
    private final BigDecimal underlyingQty;
    private final String underlyingCurrency;
    private List<Event> evntGrp;
    private final LocalDate maturityDate;
    private final LocalTime maturityTime;

    public SecurityDefinition(final Message fastMessage) {
        super(fastMessage);
        securityAltID = fastMessage.getString("SecurityAltID");
        securityAltIDSource = fastMessage.getString("SecurityAltIDSource");

        final String securityTypeString = fastMessage.getString("SecurityType");
        securityType = securityTypeString == null ? null : SecurityType.valueOf(securityTypeString);

        final ScalarValue strikePriceScalarValue = fastMessage.getScalar("StrikePrice");
        strikePrice = strikePriceScalarValue == null ? null : strikePriceScalarValue.toBigDecimal();

        final ScalarValue contractMultiplierScalarValue = fastMessage.getScalar("ContractMultiplier");
        contractMultiplier = contractMultiplierScalarValue == null ? null : contractMultiplierScalarValue.toBigDecimal();

        final ScalarValue tradingSessionIDScalarValue = fastMessage.getScalar("TradingSessionID");
        tradingSessionID = tradingSessionIDScalarValue == null ? null : tradingSessionIDScalarValue.toInt();

        final ScalarValue exchangeTradingSessionIDScalarValue = fastMessage.getScalar("ExchangeTradingSessionID");
        exchangeTradingSessionID = exchangeTradingSessionIDScalarValue == null ? null
                : exchangeTradingSessionIDScalarValue.toInt();

        final ScalarValue volatilityScalarValue = fastMessage.getScalar("Volatility");
        volatility = volatilityScalarValue == null ? null : volatilityScalarValue.toBigDecimal();

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

        quotationList = fastMessage.getString("QuotationList");

        final ScalarValue theorPriceScalarValue = fastMessage.getScalar("TheorPrice");
        theorPrice = theorPriceScalarValue == null ? null : theorPriceScalarValue.toBigDecimal();

        final ScalarValue theorPriceLimitScalarValue = fastMessage.getScalar("TheorPriceLimit");
        theorPriceLimit = theorPriceLimitScalarValue == null ? null : theorPriceLimitScalarValue.toBigDecimal();

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

        final SequenceValue instrumentAttributesSequenceValue = fastMessage.getSequence(
                "InstrumentAttributes");
        if (instrumentAttributesSequenceValue != null) {
            final GroupValue[] instrumentAttributesArray = instrumentAttributesSequenceValue.getValues();
            final List<InstrumentAttribute> instrumentAttributesList = new ArrayList<>(
                    instrumentAttributesArray.length);
            for (final GroupValue instrumentAttributeValue : instrumentAttributesArray) {
                final InstrumentAttribute instrumentAttribute = new InstrumentAttribute();
                instrumentAttribute.setInstrAttribType(instrumentAttributeValue.getInt("InstrAttribType"));
                instrumentAttribute.setInstrAttribValue(instrumentAttributeValue.getString(
                        "InstrAttribValue"));
                instrumentAttributesList.add(instrumentAttribute);
            }
            instrumentAttributes = instrumentAttributesList;
        }

        final ScalarValue underlyingQtyValue = fastMessage.getScalar("UnderlyingQty");
        underlyingQty = underlyingQtyValue == null ? null : underlyingQtyValue.toBigDecimal();

        underlyingCurrency = fastMessage.getString("UnderlyingCurrency");

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
}
