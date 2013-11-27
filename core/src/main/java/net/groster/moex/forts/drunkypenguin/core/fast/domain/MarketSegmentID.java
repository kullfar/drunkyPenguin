package net.groster.moex.forts.drunkypenguin.core.fast.domain;

public enum MarketSegmentID {

    F("FORTS (Futures)"),
    O("FORTS (Options)"),
    I("Indexes"),
    SA("Classica, Equities (quote-driven market, see details at http://www.rts.ru/s567)"),
    SBOND("Classica, Bonds (Quote driven market)"),
    SBONDE("Classica, Eurobonds"),
    SBONDG("Quote driven market, Government bonds"),
    SBOARD("RTS Board, Equities"),
    SBOARDC("RTS Board, Equities"),
    SBOARDB("RTS Board, Bonds"),
    SGAZ("RTS T+0, Equities (Gazprom on SPB)"),
    SGTS("Order driven market, Equities"),
    SGTB("Order driven market, Bonds"),
    SAG_CLS("Quote driven market, agricultural products"),
    SAG_CLSN("Quote driven market, agricultural products (without value added tax)"),
    SOL_CLS("Section of trade in oil products"),
    SOL_CLSN("Section of trade in oil products (without value added tax)"),
    SMT_CLS("Section of trade in metals"),
    SAG_AA("Agricultural products"),
    SMT_AA("Manufactured goods"),
    Q("OTC Trades"),
    E("Stock market (section \"Standard\")"),
    SKRIN("SKRIN News");

    private final String description;

    private MarketSegmentID(final String description) {
        this.description = description;
    }

}
