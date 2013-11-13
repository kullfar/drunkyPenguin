package net.groster.moex.forts.drunkypenguin.core.fast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Event;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.InstrumentAttribute;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.InstrumentLeg;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.MDFeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.Underlying;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;
import org.openfast.SequenceValue;
import org.openfast.template.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MessageType {

    DEFAULT_INCREMENTAL_REFRESH_MESSAGE("DefaultIncrementalRefreshMessage"),
    DEFAULT_SNAPSHOT_MESSAGE("DefaultSnapshotMessage"),
    SECURITY_DEFINITION("SecurityDefinition") {
                @Override
                public SecurityDefinition parseFASTMessage(final Message fastMessage) {
                    final SecurityDefinition sd = new SecurityDefinition();
                    sd.setApplVerID(fastMessage.getString("ApplVerID"));
                    sd.setMessageType(fastMessage.getString("MessageType"));
                    sd.setSenderCompID(fastMessage.getString("SenderCompID"));
                    sd.setMsgSeqNum(fastMessage.getInt("MsgSeqNum"));
                    sd.setSendingTime(FAST_DATETIME_UTC_FORMATTER.parseDateTime(Long.toString(fastMessage.getLong(
                                                    "SendingTime"))).withZone(Constants.MOEX_TIME_ZONE));
                    sd.setTotNumReports(fastMessage.getInt("TotNumReports"));
                    sd.setSymbol(fastMessage.getString("Symbol"));
                    sd.setSecurityDesc(fastMessage.getString("SecurityDesc"));
                    sd.setSecurityID(fastMessage.getLong("SecurityID"));
                    sd.setSecurityIDSource(fastMessage.getInt("SecurityIDSource"));
                    sd.setSecurityAltID(fastMessage.getString("SecurityAltID"));
                    sd.setSecurityAltIDSource(fastMessage.getString("SecurityAltIDSource"));
                    sd.setSecurityType(fastMessage.getString("SecurityType"));
                    sd.setCfiCode(fastMessage.getString("CFICode"));

                    final ScalarValue strikePriceScalarValue = fastMessage.getScalar("StrikePrice");
                    sd.setStrikePrice(strikePriceScalarValue == null ? null : strikePriceScalarValue.toBigDecimal());

                    final ScalarValue contractMultiplierScalarValue = fastMessage.getScalar("ContractMultiplier");
                    sd.setContractMultiplier(contractMultiplierScalarValue == null ? null : contractMultiplierScalarValue.
                            toBigDecimal());

                    final ScalarValue securityTradingStatusScalarValue = fastMessage.getScalar("SecurityTradingStatus");
                    sd.setSecurityTradingStatus(securityTradingStatusScalarValue == null ? null
                            : securityTradingStatusScalarValue.toInt());

                    sd.setCurrency(fastMessage.getString("Currency"));
                    sd.setMarketID(fastMessage.getString("MarketID"));
                    sd.setMarketSegmentID(fastMessage.getString("MarketSegmentID"));

                    final ScalarValue tradingSessionIDScalarValue = fastMessage.getScalar("TradingSessionID");
                    sd.setTradingSessionID(tradingSessionIDScalarValue == null ? null : tradingSessionIDScalarValue.toInt());

                    final ScalarValue exchangeTradingSessionIDScalarValue = fastMessage.getScalar("ExchangeTradingSessionID");
                    sd.setExchangeTradingSessionID(exchangeTradingSessionIDScalarValue == null ? null
                            : exchangeTradingSessionIDScalarValue.toInt());

                    final ScalarValue volatilityScalarValue = fastMessage.getScalar("Volatility");
                    sd.setVolatility(volatilityScalarValue == null ? null : volatilityScalarValue.toBigDecimal());

                    final GroupValue[] mdFeedTypesArray = fastMessage.getSequence("MDFeedTypes").getValues();
                    final List<MDFeedType> mdFeedTypes = new ArrayList<>(mdFeedTypesArray.length);
                    for (final GroupValue mdFeedTypeValue : mdFeedTypesArray) {
                        final MDFeedType mdFeedType = new MDFeedType();

                        mdFeedType.setMdFeedType(mdFeedTypeValue.getString("MDFeedType"));

                        final ScalarValue MarketDepthScalarValue = mdFeedTypeValue.getScalar("MarketDepth");
                        mdFeedType.setMarketDepth(MarketDepthScalarValue == null ? null : MarketDepthScalarValue.toInt());

                        final ScalarValue MDBookTypeScalarValue = mdFeedTypeValue.getScalar("MDBookType");
                        mdFeedType.setMdBookType(MDBookTypeScalarValue == null ? null : MDBookTypeScalarValue.toInt());

                        mdFeedTypes.add(mdFeedType);
                    }
                    sd.setMdFeedTypes(mdFeedTypes);

                    final SequenceValue underlyingsSequenceValue = fastMessage.getSequence("Underlyings");
                    if (underlyingsSequenceValue != null) {
                        final GroupValue[] underlyingsArray = underlyingsSequenceValue.getValues();
                        final List<Underlying> underlyings = new ArrayList<>(underlyingsArray.length);
                        for (final GroupValue underlyingValue : underlyingsArray) {
                            final Underlying underlying = new Underlying();
                            underlying.setUnderlyingSymbol(underlyingValue.getString("UnderlyingSymbol"));

                            final ScalarValue underlyingSecurityIDScalarValue = underlyingValue.getScalar(
                                    "UnderlyingSecurityID");
                            underlying.setUnderlyingSecurityID(underlyingSecurityIDScalarValue == null ? null
                                    : underlyingSecurityIDScalarValue.toLong());

                            underlyings.add(underlying);
                        }
                        sd.setUnderlying(underlyings);
                    }

                    final ScalarValue highLimitPxScalarValue = fastMessage.getScalar("HighLimitPx");
                    sd.setHighLimitPx(highLimitPxScalarValue == null ? null : highLimitPxScalarValue.toBigDecimal());

                    final ScalarValue lowLimitPxScalarValue = fastMessage.getScalar("LowLimitPx");
                    sd.setLowLimitPx(lowLimitPxScalarValue == null ? null : lowLimitPxScalarValue.toBigDecimal());

                    final ScalarValue minPriceIncrementScalarValue = fastMessage.getScalar("MinPriceIncrement");
                    sd.setMinPriceIncrement(minPriceIncrementScalarValue == null ? null : minPriceIncrementScalarValue.
                            toBigDecimal());

                    final ScalarValue minPriceIncrementAmountScalarValue = fastMessage.getScalar("MinPriceIncrementAmount");
                    sd.setMinPriceIncrementAmount(minPriceIncrementAmountScalarValue == null ? null
                            : minPriceIncrementAmountScalarValue.toBigDecimal());

                    final ScalarValue initialMarginOnBuyScalarValue = fastMessage.getScalar("InitialMarginOnBuy");
                    sd.setInitialMarginOnBuy(initialMarginOnBuyScalarValue == null ? null : initialMarginOnBuyScalarValue.
                            toBigDecimal());

                    final ScalarValue initialMarginOnSellScalarValue = fastMessage.getScalar("InitialMarginOnSell");
                    sd.setInitialMarginOnSell(initialMarginOnSellScalarValue == null ? null : initialMarginOnSellScalarValue.
                            toBigDecimal());

                    final ScalarValue initialMarginSynteticScalarValue = fastMessage.getScalar("InitialMarginSyntetic");
                    sd.setInitialMarginSyntetic(initialMarginSynteticScalarValue == null ? null
                            : initialMarginSynteticScalarValue.toBigDecimal());

                    sd.setQuotationList(fastMessage.getString("QuotationList"));

                    final ScalarValue theorPriceScalarValue = fastMessage.getScalar("TheorPrice");
                    sd.setTheorPrice(theorPriceScalarValue == null ? null : theorPriceScalarValue.toBigDecimal());

                    final ScalarValue theorPriceLimitScalarValue = fastMessage.getScalar("TheorPriceLimit");
                    sd.setTheorPriceLimit(theorPriceLimitScalarValue == null ? null : theorPriceLimitScalarValue.
                            toBigDecimal());

                    final SequenceValue instrumentLegsSequenceValue = fastMessage.getSequence("InstrumentLegs");
                    if (instrumentLegsSequenceValue != null) {
                        final GroupValue[] instrumentLegsArray = instrumentLegsSequenceValue.getValues();
                        final List<InstrumentLeg> instrumentLegs = new ArrayList<>(instrumentLegsArray.length);
                        for (final GroupValue instrumentLegValue : instrumentLegsArray) {
                            final InstrumentLeg instrumentLeg = new InstrumentLeg();
                            instrumentLeg.setLegSymbol(instrumentLegValue.getString("LegSymbol"));
                            instrumentLeg.setLegSecurityID(instrumentLegValue.getLong("LegSecurityID"));
                            instrumentLeg.setLegRatioQty(instrumentLegValue.getBigDecimal("LegRatioQty"));
                            instrumentLegs.add(instrumentLeg);
                        }
                        sd.setInstrumentLegs(instrumentLegs);
                    }

                    final SequenceValue instrumentAttributesSequenceValue = fastMessage.getSequence("InstrumentAttributes");
                    if (instrumentAttributesSequenceValue != null) {
                        final GroupValue[] instrumentAttributesArray = instrumentAttributesSequenceValue.getValues();
                        final List<InstrumentAttribute> instrumentAttributes = new ArrayList<>(
                                instrumentAttributesArray.length);
                        for (final GroupValue instrumentAttributeValue : instrumentAttributesArray) {
                            final InstrumentAttribute instrumentAttribute = new InstrumentAttribute();
                            instrumentAttribute.setInstrAttribType(instrumentAttributeValue.getInt("InstrAttribType"));
                            instrumentAttribute.setInstrAttribValue(instrumentAttributeValue.getString("InstrAttribValue"));
                            instrumentAttributes.add(instrumentAttribute);
                        }
                        sd.setInstrumentAttributes(instrumentAttributes);
                    }

                    final ScalarValue underlyingQtyValue = fastMessage.getScalar("UnderlyingQty");
                    sd.setUnderlyingQty(underlyingQtyValue == null ? null : underlyingQtyValue.toBigDecimal());

                    sd.setUnderlyingCurrency(fastMessage.getString("UnderlyingCurrency"));

                    final SequenceValue evntGrpSequenceValue = fastMessage.getSequence("EvntGrp");
                    if (evntGrpSequenceValue != null) {
                        final GroupValue[] eventsArray = evntGrpSequenceValue.getValues();
                        final List<Event> events = new ArrayList<>(eventsArray.length);
                        for (final GroupValue eventValue : eventsArray) {
                            final Event event = new Event();
                            event.setEventType(eventValue.getInt("EventType"));
                            event.setEventDate(FAST_DATE_UTC_FORMATTER.parseDateTime(Integer.toString(eventValue.getInt(
                                                            "EventDate"))).withZone(Constants.MOEX_TIME_ZONE).toLocalDate());
                            event.setEventTime(FAST_DATETIME_UTC_FORMATTER.parseDateTime(Long.toString(eventValue.getLong(
                                                            "EventTime"))).withZone(Constants.MOEX_TIME_ZONE));
                            events.add(event);
                        }
                        sd.setEvntGrp(events);
                    }

                    final ScalarValue maturityDateScalarValue = fastMessage.getScalar("MaturityDate");
                    sd.setMaturityDate(maturityDateScalarValue == null ? null : FAST_DATE_UTC_FORMATTER.parseDateTime(
                                    Integer.toString(maturityDateScalarValue.toInt())).withZone(Constants.MOEX_TIME_ZONE).
                            toLocalDate());

                    final ScalarValue maturityTimeScalarValue = fastMessage.getScalar("MaturityTime");
                    sd.setMaturityTime(maturityTimeScalarValue == null ? null : FAST_TIME_UTC_FORMATTER.parseDateTime(
                                    Integer.toString(maturityTimeScalarValue.toInt())).withZone(Constants.MOEX_TIME_ZONE).
                            toLocalTime());

                    return sd;
                }
            },
    SECURITY_DEFINITION_UPDATE_REPORT("SecurityDefinitionUpdateReport"),
    SECURITY_STATUS("SecurityStatus"),
    HEARTBEAT("Heartbeat"),
    SEQUENCE_RESET("SequenceReset"),
    RESET("Reset"),
    TRADING_SESSION_STATUS("TradingSessionStatus"),
    NEWS("News"),
    LOGON("Logon"),
    LOGOUT("Logout");
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageType.class);
    private static final DateTimeFormatter FAST_DATETIME_UTC_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS").
            withZone(DateTimeZone.UTC);
    private static final DateTimeFormatter FAST_DATE_UTC_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd").withZone(
            DateTimeZone.UTC);
    private static final DateTimeFormatter FAST_TIME_UTC_FORMATTER = DateTimeFormat.forPattern("HHmmssSSS").withZone(
            DateTimeZone.UTC);
    private final String name;
    private static final Map<String, MessageType> NAME_2_MESSAGE_TYPE;
    private static final Map<Integer, MessageType> ID_2_MESSAGE_TYPE = new HashMap<>(MessageType.values().length);

    static {
        final Map<String, MessageType> tmpName2MessageType = new HashMap<>(MessageType.values().length);
        for (final MessageType messageType : MessageType.values()) {
            tmpName2MessageType.put(messageType.getName(), messageType);
        }
        NAME_2_MESSAGE_TYPE = Collections.unmodifiableMap(tmpName2MessageType);
    }

    private MessageType(final String name) {
        this.name = name;
    }

    public void setId(final int id) {
        ID_2_MESSAGE_TYPE.put(id, this);
    }

    public Object parseFASTMessage(final Message fastMessage) {
        return null;
    }

    public static MessageType getByName(final String name) {
        final MessageType messageType = NAME_2_MESSAGE_TYPE.get(name);
        if (messageType == null) {
            throw new IllegalArgumentException();
        }
        return messageType;
    }

    public static MessageType getById(final Integer id) {
        final MessageType messageType = ID_2_MESSAGE_TYPE.get(id);
        if (messageType == null) {
            throw new IllegalArgumentException();
        }
        return messageType;
    }

    public String getName() {
        return name;
    }

    public static void logUnknownFASTMessage(final Message message) {
        final StringBuilder details = new StringBuilder("Have received unknown FAST message\n")
                .append("'rawMessage' = '").append(message).append("'.\n")
                .append("Full details are:\n");
        for (final Field f : message.getTemplate().getFieldDefinitions()) {
            details.append("\t'").append(f.getName()).append("'='");
            if (f.getValueType() == ScalarValue.class) {
                final ScalarValue scalarValue = message.getScalar(f.getName());
                if (scalarValue == null) {
                    details.append("null");
                } else {
                    details.append('(').append(scalarValue.getClass()).append(')').append(scalarValue);
                }
            } else {
                final SequenceValue sequenceValue = message.getSequence(f.getName());
                if (sequenceValue == null) {
                    details.append("[null]");
                } else {
                    for (final GroupValue groupValue : sequenceValue.getValues()) {
                        details.append("[\n");
                        for (final Field groupField : groupValue.getGroup().getFieldDefinitions()) {
                            details.append("\t\t'").append(groupField.getName()).append("'='");
                            if (groupValue.getValue(groupField.getName()) == null) {
                                details.append("null");
                            } else {
                                final ScalarValue scalarValue = groupValue.getScalar(groupField.getName());
                                if (scalarValue == null) {
                                    details.append("null");
                                } else {
                                    details.append('(').append(scalarValue.getClass()).append(')').append(
                                            scalarValue);
                                }
                            }
                            details.append("\'\n");
                        }
                        details.append("\t]");
                    }
                }
            }
            details.append("\'\n");
        }
        LOGGER.warn(details.toString());
    }

}
