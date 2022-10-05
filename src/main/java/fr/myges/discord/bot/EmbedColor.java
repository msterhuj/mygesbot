package fr.myges.discord.bot;

public enum EmbedColor {
    RED(0xff0000),
    GREEN(0x00ff00),
    BLUE(0x0000ff),
    YELLOW(0xffff00),
    PURPLE(0xff00ff),
    CYAN(0x00ffff),
    WHITE(0xffffff),
    GRAY(0x808080),
    BLACK(0x000000);

    private final int value;

    EmbedColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
