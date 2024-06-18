package net.kyori.adventure.text.format;

import chikachi.discord.core.MinecraftFormattingCodes;
import net.kyori.adventure.util.HSVLike;
import net.kyori.adventure.util.Index;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class NamedTextColor implements TextColor {
  private static final int BLACK_VALUE = 0x000000;
  private static final int DARK_BLUE_VALUE = 0x0000aa;
  private static final int DARK_GREEN_VALUE = 0x00aa00;
  private static final int DARK_AQUA_VALUE = 0x00aaaa;
  private static final int DARK_RED_VALUE = 0xaa0000;
  private static final int DARK_PURPLE_VALUE = 0xaa00aa;
  private static final int GOLD_VALUE = 0xffaa00;
  private static final int GRAY_VALUE = 0xaaaaaa;
  private static final int DARK_GRAY_VALUE = 0x555555;
  private static final int BLUE_VALUE = 0x5555ff;
  private static final int GREEN_VALUE = 0x55ff55;
  private static final int AQUA_VALUE = 0x55ffff;
  private static final int RED_VALUE = 0xff5555;
  private static final int LIGHT_PURPLE_VALUE = 0xff55ff;
  private static final int YELLOW_VALUE = 0xffff55;
  private static final int WHITE_VALUE = 0xffffff;

  /**
   * The standard {@code black} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor BLACK = new NamedTextColor("black", BLACK_VALUE, MinecraftFormattingCodes.BLACK);
  /**
   * The standard {@code dark_blue} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_BLUE = new NamedTextColor("dark_blue", DARK_BLUE_VALUE, MinecraftFormattingCodes.DARK_BLUE);
  /**
   * The standard {@code dark_green} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_GREEN = new NamedTextColor("dark_green", DARK_GREEN_VALUE, MinecraftFormattingCodes.DARK_GREEN);
  /**
   * The standard {@code dark_aqua} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_AQUA = new NamedTextColor("dark_aqua", DARK_AQUA_VALUE, MinecraftFormattingCodes.DARK_AQUA);
  /**
   * The standard {@code dark_red} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_RED = new NamedTextColor("dark_red", DARK_RED_VALUE, MinecraftFormattingCodes.DARK_RED);
  /**
   * The standard {@code dark_purple} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_PURPLE = new NamedTextColor("dark_purple", DARK_PURPLE_VALUE, MinecraftFormattingCodes.DARK_PURPLE);
  /**
   * The standard {@code gold} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor GOLD = new NamedTextColor("gold", GOLD_VALUE, MinecraftFormattingCodes.GOLD);
  /**
   * The standard {@code gray} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor GRAY = new NamedTextColor("gray", GRAY_VALUE, MinecraftFormattingCodes.GRAY);
  /**
   * The standard {@code dark_gray} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor DARK_GRAY = new NamedTextColor("dark_gray", DARK_GRAY_VALUE, MinecraftFormattingCodes.DARK_GRAY);
  /**
   * The standard {@code blue} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor BLUE = new NamedTextColor("blue", BLUE_VALUE, MinecraftFormattingCodes.BLUE);
  /**
   * The standard {@code green} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor GREEN = new NamedTextColor("green", GREEN_VALUE, MinecraftFormattingCodes.GREEN);
  /**
   * The standard {@code aqua} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor AQUA = new NamedTextColor("aqua", AQUA_VALUE, MinecraftFormattingCodes.AQUA);
  /**
   * The standard {@code red} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor RED = new NamedTextColor("red", RED_VALUE, MinecraftFormattingCodes.RED);
  /**
   * The standard {@code light_purple} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor LIGHT_PURPLE = new NamedTextColor("light_purple", LIGHT_PURPLE_VALUE, MinecraftFormattingCodes.LIGHT_PURPLE);
  /**
   * The standard {@code yellow} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor YELLOW = new NamedTextColor("yellow", YELLOW_VALUE, MinecraftFormattingCodes.YELLOW);
  /**
   * The standard {@code white} colour.
   *
   * @since 4.0.0
   */
  public static final NamedTextColor WHITE = new NamedTextColor("white", WHITE_VALUE, MinecraftFormattingCodes.WHITE);

  private static final List<NamedTextColor> VALUES = Collections.unmodifiableList(Arrays.asList(BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE));
  /**
   * An index of name to color.
   *
   * @since 4.0.0
   */
  public static final Index<String, NamedTextColor> NAMES = Index.create(constant -> constant.name, VALUES);

  /**
   * Gets the named color exactly matching the provided color.
   *
   * @param value the color to match
   * @return the matched color, or null
   * @since 4.10.0
   */
  public static @Nullable NamedTextColor namedColor(final int value) {
    switch (value) {
      case BLACK_VALUE: return BLACK;
      case DARK_BLUE_VALUE: return DARK_BLUE;
      case DARK_GREEN_VALUE: return DARK_GREEN;
      case DARK_AQUA_VALUE: return DARK_AQUA;
      case DARK_RED_VALUE: return DARK_RED;
      case DARK_PURPLE_VALUE: return DARK_PURPLE;
      case GOLD_VALUE: return GOLD;
      case GRAY_VALUE: return GRAY;
      case DARK_GRAY_VALUE: return DARK_GRAY;
      case BLUE_VALUE: return BLUE;
      case GREEN_VALUE: return GREEN;
      case AQUA_VALUE: return AQUA;
      case RED_VALUE: return RED;
      case LIGHT_PURPLE_VALUE: return LIGHT_PURPLE;
      case YELLOW_VALUE: return YELLOW;
      case WHITE_VALUE: return WHITE;
      default: return null;
    }
  }

  /**
   * Gets the named color exactly matching the provided color.
   *
   * @param value the color to match
   * @return the matched color, or null
   * @since 4.0.0
   * @deprecated for removal since 4.10.0, use {@link #namedColor(int)} instead
   */
  @Deprecated
  public static @Nullable NamedTextColor ofExact(final int value) {
    switch (value) {
      case BLACK_VALUE: return BLACK;
      case DARK_BLUE_VALUE: return DARK_BLUE;
      case DARK_GREEN_VALUE: return DARK_GREEN;
      case DARK_AQUA_VALUE: return DARK_AQUA;
      case DARK_RED_VALUE: return DARK_RED;
      case DARK_PURPLE_VALUE: return DARK_PURPLE;
      case GOLD_VALUE: return GOLD;
      case GRAY_VALUE: return GRAY;
      case DARK_GRAY_VALUE: return DARK_GRAY;
      case BLUE_VALUE: return BLUE;
      case GREEN_VALUE: return GREEN;
      case AQUA_VALUE: return AQUA;
      case RED_VALUE: return RED;
      case LIGHT_PURPLE_VALUE: return LIGHT_PURPLE;
      case YELLOW_VALUE: return YELLOW;
      case WHITE_VALUE: return WHITE;
      default: return null;
    }
  }

  /**
   * Find the named colour nearest to the provided colour.
   *
   * @param any colour to match
   * @return nearest named colour. will always return a value
   * @since 4.0.0
   */
  public static @NotNull NamedTextColor nearestTo(final @NotNull TextColor any) {
    if (any instanceof NamedTextColor) {
      return (NamedTextColor) any;
    }

    return TextColor.nearestColorTo(VALUES, any);
  }

  private final String name;
  private final int value;
  private final HSVLike hsv;
  private final MinecraftFormattingCodes code;

  private NamedTextColor(final String name, final int value, MinecraftFormattingCodes code) {
    this.name = name;
    this.value = value;
    this.hsv = HSVLike.fromRGB(this.red(), this.green(), this.blue());
    this.code = code;
  }

  @Override
  public int value() {
    return this.value;
  }

  public String legacyColor() {
      return code.toString();
  }

  @Override
  public @NotNull HSVLike asHSV() {
    return this.hsv;
  }

  @Override
  public @NotNull String toString() {
    return this.name;
  }
}
