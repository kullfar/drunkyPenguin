package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

public enum SecurityType {

    REPO("Repo instrument"),
    MLEG("Calendar spread");
    private final String description;

    private SecurityType(final String description) {
        this.description = description;
    }

}
