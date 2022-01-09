/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.color;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mifmi.commons4j.util.CollectionUtilz;

public class RGBColor implements IColor {
	public static final RGBColor ALICE_BLUE = fromRGB8(240, 248, 255);
	public static final RGBColor ANTIQUE_WHITE = fromRGB8(250, 235, 215);
	public static final RGBColor AQUA = fromRGB8(0, 255, 255);
	public static final RGBColor AQUAMARINE = fromRGB8(127, 255, 212);
	public static final RGBColor AZURE = fromRGB8(240, 255, 255);
	public static final RGBColor BEIGE = fromRGB8(245, 245, 220);
	public static final RGBColor BISQUE = fromRGB8(255, 228, 196);
	public static final RGBColor BLACK = fromRGB8(0, 0, 0);
	public static final RGBColor BLANCHED_ALMOND = fromRGB8(255, 235, 205);
	public static final RGBColor BLUE = fromRGB8(0, 0, 255);
	public static final RGBColor BLUE_VIOLET = fromRGB8(138, 43, 226);
	public static final RGBColor BROWN = fromRGB8(165, 42, 42);
	public static final RGBColor BURLY_WOOD = fromRGB8(222, 184, 135);
	public static final RGBColor CADET_BLUE = fromRGB8(95, 158, 160);
	public static final RGBColor CHARTREUSE = fromRGB8(127, 255, 0);
	public static final RGBColor CHOCOLATE = fromRGB8(210, 105, 30);
	public static final RGBColor CORAL = fromRGB8(255, 127, 80);
	public static final RGBColor CORNFLOWER_BLUE = fromRGB8(100, 149, 237);
	public static final RGBColor CORNSILK = fromRGB8(255, 248, 220);
	public static final RGBColor CRIMSON = fromRGB8(220, 20, 60);
	public static final RGBColor CYAN = fromRGB8(0, 255, 255);
	public static final RGBColor DARK_BLUE = fromRGB8(0, 0, 139);
	public static final RGBColor DARK_CYAN = fromRGB8(0, 139, 139);
	public static final RGBColor DARK_GOLDENROD = fromRGB8(184, 134, 11);
	public static final RGBColor DARK_GRAY = fromRGB8(169, 169, 169);
	public static final RGBColor DARK_GREEN = fromRGB8(0, 100, 0);
	public static final RGBColor DARK_KHAKI = fromRGB8(189, 183, 107);
	public static final RGBColor DARK_MAGENTA = fromRGB8(139, 0, 139);
	public static final RGBColor DARK_OLIVE_GREEN = fromRGB8(85, 107, 47);
	public static final RGBColor DARK_ORANGE = fromRGB8(255, 140, 0);
	public static final RGBColor DARK_ORCHID = fromRGB8(153, 50, 204);
	public static final RGBColor DARK_RED = fromRGB8(139, 0, 0);
	public static final RGBColor DARK_SALMON = fromRGB8(233, 150, 122);
	public static final RGBColor DARK_SEA_GREEN = fromRGB8(143, 188, 143);
	public static final RGBColor DARK_SLATE_BLUE = fromRGB8(72, 61, 139);
	public static final RGBColor DARK_SLATE_GRAY = fromRGB8(47, 79, 79);
	public static final RGBColor DARK_TURQUOISE = fromRGB8(0, 206, 209);
	public static final RGBColor DARK_VIOLET = fromRGB8(148, 0, 211);
	public static final RGBColor DEEP_PINK = fromRGB8(255, 20, 147);
	public static final RGBColor DEEP_SKY_BLUE = fromRGB8(0, 191, 255);
	public static final RGBColor DIM_GRAY = fromRGB8(105, 105, 105);
	public static final RGBColor DODGER_BLUE = fromRGB8(30, 144, 255);
	public static final RGBColor FIRE_BRICK = fromRGB8(178, 34, 34);
	public static final RGBColor FLORAL_WHITE = fromRGB8(255, 250, 240);
	public static final RGBColor FOREST_GREEN = fromRGB8(34, 139, 34);
	public static final RGBColor FUCHSIA = fromRGB8(255, 0, 255);
	public static final RGBColor GAINSBORO = fromRGB8(220, 220, 220);
	public static final RGBColor GHOST_WHITE = fromRGB8(248, 248, 255);
	public static final RGBColor GOLD = fromRGB8(255, 215, 0);
	public static final RGBColor GOLDENROD = fromRGB8(218, 165, 32);
	public static final RGBColor GRAY = fromRGB8(128, 128, 128);
	public static final RGBColor GREEN = fromRGB8(0, 128, 0);
	public static final RGBColor GREEN_YELLOW = fromRGB8(173, 255, 47);
	public static final RGBColor HONEYDEW = fromRGB8(240, 255, 240);
	public static final RGBColor HOT_PINK = fromRGB8(255, 105, 180);
	public static final RGBColor INDIAN_RED = fromRGB8(205, 92, 92);
	public static final RGBColor INDIGO = fromRGB8(75, 0, 130);
	public static final RGBColor IVORY = fromRGB8(255, 255, 240);
	public static final RGBColor KHAKI = fromRGB8(240, 230, 140);
	public static final RGBColor LAVENDER = fromRGB8(230, 230, 250);
	public static final RGBColor LAVENDER_BLUSH = fromRGB8(255, 240, 245);
	public static final RGBColor LAWN_GREEN = fromRGB8(124, 252, 0);
	public static final RGBColor LEMON_CHIFFON = fromRGB8(255, 250, 205);
	public static final RGBColor LIGHT_BLUE = fromRGB8(173, 216, 230);
	public static final RGBColor LIGHT_CORAL = fromRGB8(240, 128, 128);
	public static final RGBColor LIGHT_CYAN = fromRGB8(224, 255, 255);
	public static final RGBColor LIGHT_GOLDENROD_YELLOW = fromRGB8(250, 250, 210);
	public static final RGBColor LIGHT_GREEN = fromRGB8(144, 238, 144);
	public static final RGBColor LIGHT_GREY = fromRGB8(211, 211, 211);
	public static final RGBColor LIGHT_PINK = fromRGB8(255, 182, 193);
	public static final RGBColor LIGHT_SALMON = fromRGB8(255, 160, 122);
	public static final RGBColor LIGHT_SEA_GREEN = fromRGB8(32, 178, 170);
	public static final RGBColor LIGHT_SKY_BLUE = fromRGB8(135, 206, 250);
	public static final RGBColor LIGHT_SLATE_GRAY = fromRGB8(119, 136, 153);
	public static final RGBColor LIGHT_STEEL_BLUE = fromRGB8(176, 196, 222);
	public static final RGBColor LIGHT_YELLOW = fromRGB8(255, 255, 224);
	public static final RGBColor LIME = fromRGB8(0, 255, 0);
	public static final RGBColor LIME_GREEN = fromRGB8(50, 205, 50);
	public static final RGBColor LINEN = fromRGB8(250, 240, 230);
	public static final RGBColor MAGENTA = fromRGB8(255, 0, 255);
	public static final RGBColor MAROON = fromRGB8(128, 0, 0);
	public static final RGBColor MEDIUMAQU_AMARINE = fromRGB8(102, 205, 170);
	public static final RGBColor MEDIUM_BLUE = fromRGB8(0, 0, 205);
	public static final RGBColor MEDIUM_ORCHID = fromRGB8(186, 85, 211);
	public static final RGBColor MEDIUM_PURPLE = fromRGB8(147, 112, 219);
	public static final RGBColor MEDIUM_SEA_GREEN = fromRGB8(60, 179, 113);
	public static final RGBColor MEDIUM_SLATE_BLUE = fromRGB8(123, 104, 238);
	public static final RGBColor MEDIUM_SPRING_GREEN = fromRGB8(0, 250, 154);
	public static final RGBColor MEDIUM_TURQUOISE = fromRGB8(72, 209, 204);
	public static final RGBColor MEDIUM_VIOLET_RED = fromRGB8(199, 21, 133);
	public static final RGBColor MIDNIGHT_BLUE = fromRGB8(25, 25, 112);
	public static final RGBColor MINT_CREAM = fromRGB8(245, 255, 250);
	public static final RGBColor MISTY_ROSE = fromRGB8(255, 228, 225);
	public static final RGBColor MOCCASIN = fromRGB8(255, 228, 181);
	public static final RGBColor NAVAJO_WHITE = fromRGB8(255, 222, 173);
	public static final RGBColor NAVY = fromRGB8(0, 0, 128);
	public static final RGBColor OLD_LACE = fromRGB8(253, 245, 230);
	public static final RGBColor OLIVE = fromRGB8(128, 128, 0);
	public static final RGBColor OLIVE_DRAB = fromRGB8(107, 142, 35);
	public static final RGBColor ORANGE = fromRGB8(255, 165, 0);
	public static final RGBColor ORANGE_RED = fromRGB8(255, 69, 0);
	public static final RGBColor ORCHID = fromRGB8(218, 112, 214);
	public static final RGBColor PALE_GOLDENROD = fromRGB8(238, 232, 170);
	public static final RGBColor PALE_GREEN = fromRGB8(152, 251, 152);
	public static final RGBColor PALE_TURQUOISE = fromRGB8(175, 238, 238);
	public static final RGBColor PALE_VIOLET_RED = fromRGB8(219, 112, 147);
	public static final RGBColor PAPAYA_WHIP = fromRGB8(255, 239, 213);
	public static final RGBColor PEACH_PUFF = fromRGB8(255, 218, 185);
	public static final RGBColor PERU = fromRGB8(205, 133, 63);
	public static final RGBColor PINK = fromRGB8(255, 192, 203);
	public static final RGBColor PLUM = fromRGB8(221, 160, 221);
	public static final RGBColor POWDER_BLUE = fromRGB8(176, 224, 230);
	public static final RGBColor PURPLE = fromRGB8(128, 0, 128);
	public static final RGBColor RED = fromRGB8(255, 0, 0);
	public static final RGBColor ROSY_BROWN = fromRGB8(188, 143, 143);
	public static final RGBColor ROYAL_BLUE = fromRGB8(65, 105, 225);
	public static final RGBColor SADDLE_BROWN = fromRGB8(139, 69, 19);
	public static final RGBColor SALMON = fromRGB8(250, 128, 114);
	public static final RGBColor SANDY_BROWN = fromRGB8(244, 164, 96);
	public static final RGBColor SEA_GREEN = fromRGB8(46, 139, 87);
	public static final RGBColor SEASHELL = fromRGB8(255, 245, 238);
	public static final RGBColor SIENNA = fromRGB8(160, 82, 45);
	public static final RGBColor SILVER = fromRGB8(192, 192, 192);
	public static final RGBColor SKY_BLUE = fromRGB8(135, 206, 235);
	public static final RGBColor SLATE_BLUE = fromRGB8(106, 90, 205);
	public static final RGBColor SLATE_GRAY = fromRGB8(112, 128, 144);
	public static final RGBColor SNOW = fromRGB8(255, 250, 250);
	public static final RGBColor SPRING_GREEN = fromRGB8(0, 255, 127);
	public static final RGBColor STEEL_BLUE = fromRGB8(70, 130, 180);
	public static final RGBColor TAN = fromRGB8(210, 180, 140);
	public static final RGBColor TEAL = fromRGB8(0, 128, 128);
	public static final RGBColor THISTLE = fromRGB8(216, 191, 216);
	public static final RGBColor TOMATO = fromRGB8(255, 99, 71);
	public static final RGBColor TURQUOISE = fromRGB8(64, 224, 208);
	public static final RGBColor VIOLET = fromRGB8(238, 130, 238);
	public static final RGBColor WHEAT = fromRGB8(245, 222, 179);
	public static final RGBColor WHITE = fromRGB8(255, 255, 255);
	public static final RGBColor WHITE_SMOKE = fromRGB8(245, 245, 245);
	public static final RGBColor YELLOW = fromRGB8(255, 255, 0);
	public static final RGBColor YELLOW_GREEN = fromRGB8(154, 205, 50);
	
	public static final RGBColor TRANSPARENT = fromRGB8(0, 0, 0, 0.0);

	private static final Map<String, RGBColor> NAME_RGB_MAP;
	private static final Map<RGBColor, String> RGB_NAME_MAP;

	static {
		Map<String, RGBColor> nameRGBMap = new HashMap<String, RGBColor>();
		nameRGBMap.put("aliceblue", ALICE_BLUE);
		nameRGBMap.put("antiquewhite", ANTIQUE_WHITE);
		nameRGBMap.put("aqua", AQUA);
		nameRGBMap.put("aquamarine", AQUAMARINE);
		nameRGBMap.put("azure", AZURE);
		nameRGBMap.put("beige", BEIGE);
		nameRGBMap.put("bisque", BISQUE);
		nameRGBMap.put("black", BLACK);
		nameRGBMap.put("blanchedalmond", BLANCHED_ALMOND);
		nameRGBMap.put("blue", BLUE);
		nameRGBMap.put("blueviolet", BLUE_VIOLET);
		nameRGBMap.put("brown", BROWN);
		nameRGBMap.put("burlywood", BURLY_WOOD);
		nameRGBMap.put("cadetblue", CADET_BLUE);
		nameRGBMap.put("chartreuse", CHARTREUSE);
		nameRGBMap.put("chocolate", CHOCOLATE);
		nameRGBMap.put("coral", CORAL);
		nameRGBMap.put("cornflowerblue", CORNFLOWER_BLUE);
		nameRGBMap.put("cornsilk", CORNSILK);
		nameRGBMap.put("crimson", CRIMSON);
		nameRGBMap.put("cyan", CYAN);
		nameRGBMap.put("darkblue", DARK_BLUE);
		nameRGBMap.put("darkcyan", DARK_CYAN);
		nameRGBMap.put("darkgoldenrod", DARK_GOLDENROD);
		nameRGBMap.put("darkgray", DARK_GRAY);
		nameRGBMap.put("darkgreen", DARK_GREEN);
		nameRGBMap.put("darkkhaki", DARK_KHAKI);
		nameRGBMap.put("darkmagenta", DARK_MAGENTA);
		nameRGBMap.put("darkolivegreen", DARK_OLIVE_GREEN);
		nameRGBMap.put("darkorange", DARK_ORANGE);
		nameRGBMap.put("darkorchid", DARK_ORCHID);
		nameRGBMap.put("darkred", DARK_RED);
		nameRGBMap.put("darksalmon", DARK_SALMON);
		nameRGBMap.put("darkseagreen", DARK_SEA_GREEN);
		nameRGBMap.put("darkslateblue", DARK_SLATE_BLUE);
		nameRGBMap.put("darkslategray", DARK_SLATE_GRAY);
		nameRGBMap.put("darkturquoise", DARK_TURQUOISE);
		nameRGBMap.put("darkviolet", DARK_VIOLET);
		nameRGBMap.put("deeppink", DEEP_PINK);
		nameRGBMap.put("deepskyblue", DEEP_SKY_BLUE);
		nameRGBMap.put("dimgray", DIM_GRAY);
		nameRGBMap.put("dodgerblue", DODGER_BLUE);
		nameRGBMap.put("firebrick", FIRE_BRICK);
		nameRGBMap.put("floralwhite", FLORAL_WHITE);
		nameRGBMap.put("forestgreen", FOREST_GREEN);
		nameRGBMap.put("fuchsia", FUCHSIA);
		nameRGBMap.put("gainsboro", GAINSBORO);
		nameRGBMap.put("ghostwhite", GHOST_WHITE);
		nameRGBMap.put("gold", GOLD);
		nameRGBMap.put("goldenrod", GOLDENROD);
		nameRGBMap.put("gray", GRAY);
		nameRGBMap.put("green", GREEN);
		nameRGBMap.put("greenyellow", GREEN_YELLOW);
		nameRGBMap.put("honeydew", HONEYDEW);
		nameRGBMap.put("hotpink", HOT_PINK);
		nameRGBMap.put("indianred", INDIAN_RED);
		nameRGBMap.put("indigo", INDIGO);
		nameRGBMap.put("ivory", IVORY);
		nameRGBMap.put("khaki", KHAKI);
		nameRGBMap.put("lavender", LAVENDER);
		nameRGBMap.put("lavenderblush", LAVENDER_BLUSH);
		nameRGBMap.put("lawngreen", LAWN_GREEN);
		nameRGBMap.put("lemonchiffon", LEMON_CHIFFON);
		nameRGBMap.put("lightblue", LIGHT_BLUE);
		nameRGBMap.put("lightcoral", LIGHT_CORAL);
		nameRGBMap.put("lightcyan", LIGHT_CYAN);
		nameRGBMap.put("lightgoldenrodyellow", LIGHT_GOLDENROD_YELLOW);
		nameRGBMap.put("lightgreen", LIGHT_GREEN);
		nameRGBMap.put("lightgrey", LIGHT_GREY);
		nameRGBMap.put("lightpink", LIGHT_PINK);
		nameRGBMap.put("lightsalmon", LIGHT_SALMON);
		nameRGBMap.put("lightseagreen", LIGHT_SEA_GREEN);
		nameRGBMap.put("lightskyblue", LIGHT_SKY_BLUE);
		nameRGBMap.put("lightslategray", LIGHT_SLATE_GRAY);
		nameRGBMap.put("lightsteelblue", LIGHT_STEEL_BLUE);
		nameRGBMap.put("lightyellow", LIGHT_YELLOW);
		nameRGBMap.put("lime", LIME);
		nameRGBMap.put("limegreen", LIME_GREEN);
		nameRGBMap.put("linen", LINEN);
		nameRGBMap.put("magenta", MAGENTA);
		nameRGBMap.put("maroon", MAROON);
		nameRGBMap.put("mediumaquamarine", MEDIUMAQU_AMARINE);
		nameRGBMap.put("mediumblue", MEDIUM_BLUE);
		nameRGBMap.put("mediumorchid", MEDIUM_ORCHID);
		nameRGBMap.put("mediumpurple", MEDIUM_PURPLE);
		nameRGBMap.put("mediumseagreen", MEDIUM_SEA_GREEN);
		nameRGBMap.put("mediumslateblue", MEDIUM_SLATE_BLUE);
		nameRGBMap.put("mediumspringgreen", MEDIUM_SPRING_GREEN);
		nameRGBMap.put("mediumturquoise", MEDIUM_TURQUOISE);
		nameRGBMap.put("mediumvioletred", MEDIUM_VIOLET_RED);
		nameRGBMap.put("midnightblue", MIDNIGHT_BLUE);
		nameRGBMap.put("mintcream", MINT_CREAM);
		nameRGBMap.put("mistyrose", MISTY_ROSE);
		nameRGBMap.put("moccasin", MOCCASIN);
		nameRGBMap.put("navajowhite", NAVAJO_WHITE);
		nameRGBMap.put("navy", NAVY);
		nameRGBMap.put("oldlace", OLD_LACE);
		nameRGBMap.put("olive", OLIVE);
		nameRGBMap.put("olivedrab", OLIVE_DRAB);
		nameRGBMap.put("orange", ORANGE);
		nameRGBMap.put("orangered", ORANGE_RED);
		nameRGBMap.put("orchid", ORCHID);
		nameRGBMap.put("palegoldenrod", PALE_GOLDENROD);
		nameRGBMap.put("palegreen", PALE_GREEN);
		nameRGBMap.put("paleturquoise", PALE_TURQUOISE);
		nameRGBMap.put("palevioletred", PALE_VIOLET_RED);
		nameRGBMap.put("papayawhip", PAPAYA_WHIP);
		nameRGBMap.put("peachpuff", PEACH_PUFF);
		nameRGBMap.put("peru", PERU);
		nameRGBMap.put("pink", PINK);
		nameRGBMap.put("plum", PLUM);
		nameRGBMap.put("powderblue", POWDER_BLUE);
		nameRGBMap.put("purple", PURPLE);
		nameRGBMap.put("red", RED);
		nameRGBMap.put("rosybrown", ROSY_BROWN);
		nameRGBMap.put("royalblue", ROYAL_BLUE);
		nameRGBMap.put("saddlebrown", SADDLE_BROWN);
		nameRGBMap.put("salmon", SALMON);
		nameRGBMap.put("sandybrown", SANDY_BROWN);
		nameRGBMap.put("seagreen", SEA_GREEN);
		nameRGBMap.put("seashell", SEASHELL);
		nameRGBMap.put("sienna", SIENNA);
		nameRGBMap.put("silver", SILVER);
		nameRGBMap.put("skyblue", SKY_BLUE);
		nameRGBMap.put("slateblue", SLATE_BLUE);
		nameRGBMap.put("slategray", SLATE_GRAY);
		nameRGBMap.put("snow", SNOW);
		nameRGBMap.put("springgreen", SPRING_GREEN);
		nameRGBMap.put("steelblue", STEEL_BLUE);
		nameRGBMap.put("tan", TAN);
		nameRGBMap.put("teal", TEAL);
		nameRGBMap.put("thistle", THISTLE);
		nameRGBMap.put("tomato", TOMATO);
		nameRGBMap.put("turquoise", TURQUOISE);
		nameRGBMap.put("violet", VIOLET);
		nameRGBMap.put("wheat", WHEAT);
		nameRGBMap.put("white", WHITE);
		nameRGBMap.put("whitesmoke", WHITE_SMOKE);
		nameRGBMap.put("yellow", YELLOW);
		nameRGBMap.put("yellowgreen", YELLOW_GREEN);
		
		nameRGBMap.put("transparent", TRANSPARENT);
		
		Map<RGBColor, String> rgbNameMap = CollectionUtilz.swapKeyValue(nameRGBMap, new HashMap<RGBColor, String>(nameRGBMap.size()));
		
		NAME_RGB_MAP = Collections.unmodifiableMap(nameRGBMap);
		RGB_NAME_MAP = Collections.unmodifiableMap(rgbNameMap);
	}

	private double r; // [0, 1]
	private double g; // [0, 1]
	private double b; // [0, 1]
	private double a; // [0, 1]
	private int r8 = -1; // [0, 255]
	private int g8 = -1; // [0, 255]
	private int b8 = -1; // [0, 255]
	private HSLColor hsl = null;
	private HSVColor hsv = null;
	private CMYColor cmy = null;
	private CMYKColor cmyk = null;
	private Color awtColor = null;


	public RGBColor(double r, double g, double b) {
		this(r, g, b, 1.0);
	}
	
	public RGBColor(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}

	public double getA() {
		return a;
	}

	
	public int getR8() {
		if (this.r8 == -1) {
			this.r8 = (int) Math.round(this.r * 255.0);
		}
		return this.r8;
	}

	public int getG8() {
		if (this.g8 == -1) {
			this.g8 = (int) Math.round(this.g * 255.0);
		}
		return this.g8;
	}

	public int getB8() {
		if (this.b8 == -1) {
			this.b8 = (int) Math.round(this.b * 255.0);
		}
		return this.b8;
	}
	
	
	public RGBColor toRGB() {
		return this;
	}
	
	public HSLColor toHSL() {
		if (hsl == null) {
			hsl = toHSL(this);
		}
		return hsl;
	}
	
	public HSVColor toHSV() {
		if (hsv == null) {
			hsv = toHSV(this);
		}
		return hsv;
	}
	
	public CMYColor toCMY() {
		if (cmy == null) {
			cmy = toCMY(this);
		}
		return cmy;
	}
	
	public CMYKColor toCMYK() {
		if (cmyk == null) {
			cmyk = toCMYK(this);
		}
		return cmyk;
	}
	
	public Color toAwtColor() {
		if (awtColor == null) {
			awtColor = toAwtColor(this);
		}
		return awtColor;
	}

	public static RGBColor fromRGB8(int r8, int g8, int b8) {
		return fromRGB8(r8, g8, b8, 1.0);
	}

	public static RGBColor fromRGB8(int r8, int g8, int b8, double a) {
		return new RGBColor(
				((double) r8) / 255.0,
				((double) g8) / 255.0,
				((double) b8) / 255.0,
				a);
	}
	
	public static RGBColor fromName(String name) {
		return NAME_RGB_MAP.get(name);
	}
	
	public static String getName(RGBColor rgb) {
		return RGB_NAME_MAP.get(rgb);
	}

	public static RGBColor fromHSL(HSLColor hsl) {
		if (hsl == null) {
			return null;
		}
		
		double h = hsl.getH();
		double s = hsl.getS();
		double l = hsl.getL();
		double a = hsl.getA();

		double q;
		if (l <= 0.5) {
			q = l * (1.0 + s);
		} else {
			q = l + s - l * s;
		}
		double p = 2.0 * l - q;
 
		double r = hue2rgb(q, p, h + 120.0);
		double g = hue2rgb(q, p, h);
		double b = hue2rgb(q, p, h - 120.0);
 
		RGBColor rgb = new RGBColor(r, g, b, a);
		rgb.hsl = hsl;
		
		return rgb;
	}
	
	private static double hue2rgb(double p, double q, double hue) {
		hue = (hue + 180.0) % 360.0;
		if (hue < 60.0) {
			return p + (q - p) * hue / 60.0;
		} else if (hue < 180.0) {
			return q;
		} else if (hue < 240.0) {
			return p + (q - p) * (240.0 - hue) / 60.0;
		} else {
			return p;
		}
	}
	
	private static HSLColor toHSL(RGBColor rgb) {
		if (rgb == null) {
			return null;
		}
		
		double r = rgb.getR();
		double g = rgb.getG();
		double b = rgb.getB();
		double a = rgb.getA();
		
		double max = Math.max(Math.max(r, g), b);
		double min = Math.min(Math.min(r, g), b);
		
		double h;
		double s;
		double l = (max + min) / 2.0;

		if (max == min) {
			h = 0.0;
			s = 0.0;
		} else {
			double d = max - min;
			if (r == max) {
				// between yellow & magenta
				h = (g - b) / d + ((g < b) ? 6.0 : 0.0);
			} else if (g == max) {
				h = (b - r) / d + 2.0;
			} else {
				h = (r - g) / d + 4.0;
			}
			h *= 60.0; // degrees

			if (h < 0.0) {
				h += 360.0;
			}
			
			if (0.5 < l) {
				s = d / (2.0 - d);
			} else {
				s = d / (max + min);
			}
		}
		
		return new HSLColor(h, s, l, a);
	}

	public static RGBColor fromHSV(HSVColor hsv) {
		double h = hsv.getH();
		double s = hsv.getS();
		double v = hsv.getV();
		double a = hsv.getA();

		double hue = h % 360.0;
		if (hue < 0) {
			hue += 360.0;
		}
		
		int i = (int) (hue / 60.0);
		double f = hue / 60.0 - (double) i;
		double p = v * (1 - s);
		double q = v * (1 - f * s);
		double t = v * (1 - (1 - f) * s);
		
		RGBColor rgb;
		switch (i) {
			case 0: rgb = new RGBColor(v, t, p, a); break;
			case 1: rgb = new RGBColor(q, v, p, a); break;
			case 2: rgb = new RGBColor(p, v, t, a); break;
			case 3: rgb = new RGBColor(p, q, v, a); break;
			case 4: rgb = new RGBColor(t, p, v, a); break;
			case 5: rgb = new RGBColor(v, p, q, a); break;
			default: throw new IllegalStateException();
		}
		rgb.hsv = hsv;
		
		return rgb;
	}
	
	public static HSVColor toHSV(RGBColor rgb) {

		double r = rgb.getR();
		double g = rgb.getG();
		double b = rgb.getB();
		double a = rgb.getA();
		
		double min = Math.min(Math.min(r, g), b);
		double max = Math.max(Math.max(r, g), b);
		double d = max - min;

		double h;
		double s = (max == 0.0) ? 0.0 : d / max;
		double v = max;

		if (max == min) {
			h = 0.0;
		} else {
			if (r == max) {
				// between yellow & magenta
				h = (g - b) / d + ((g < b) ? 6.0 : 0.0);
			} else if (g == max) {
				h = (b - r) / d + 2.0;
			} else {
				h = (r - g) / d + 4.0;
			}
			h *= 60.0; // degrees
			
			if (h < 0.0) {
				h += 360.0;
			}
		}
		
		return new HSVColor(h, s, v, a);
	}

	public static RGBColor fromHWB(HWBColor hwb) {
		double h = hwb.getH();
		double w = hwb.getW();
		double b = hwb.getB();
		double a = hwb.getA();

		double hue = h % 360.0;
		if (hue < 0.0) {
			hue += 360.0;
		}
		
		double d = 1.0 - w - b;
		d = Math.min(d, 0.0);
		
		HSLColor hsl = new HSLColor(hue, 1.0, 0.5, a);
		RGBColor rgb = hsl.toRGB();
		
		return new RGBColor(
				rgb.getR() * d + w,
				rgb.getG() * d + w,
				rgb.getB() * d + w,
				rgb.getA()
				);
	}

	public static RGBColor fromCMY(CMYColor cmy) {
		double c = cmy.getC();
		double m = cmy.getM();
		double y = cmy.getY();
		double a = cmy.getA();

		double r = 1.0 - c;
		double g = 1.0 - m;
		double b = 1.0 - y;
		
		RGBColor rgb = new RGBColor(r, g, b, a);
		rgb.cmy = cmy;
		
		return rgb;
	}
	
	public static CMYColor toCMY(RGBColor rgb) {
		if (rgb == null) {
			return null;
		}
		
		double r = rgb.getR();
		double g = rgb.getG();
		double b = rgb.getB();
		double a = rgb.getA();
		
		double c = 1.0 - r;
		double m = 1.0 - g;
		double y = 1.0 - b;
		
		return new CMYColor(c, m, y, a);
	}

	public static RGBColor fromCMYK(CMYKColor cmyk) {
		double c = cmyk.getC();
		double m = cmyk.getM();
		double y = cmyk.getY();
		double k = cmyk.getK();
		double a = cmyk.getA();
		
		double r = 1.0 - Math.min(1.0, c + k);
		double g = 1.0 - Math.min(1.0, m + k);
		double b = 1.0 - Math.min(1.0, y + k);
		
		RGBColor rgb = new RGBColor(r, g, b, a);
		rgb.cmyk = cmyk;
		
		return rgb;
	}
	
	public static CMYKColor toCMYK(RGBColor rgb) {
		if (rgb == null) {
			return null;
		}
		
		double r = rgb.getR();
		double g = rgb.getG();
		double b = rgb.getB();
		double a = rgb.getA();

		// FIXME: should to use java.awt.color.ICC_ColorSpace
		
//		double c = 1.0 - r;
//		double m = 1.0 - g;
//		double y = 1.0 - b;
//		double k = Math.min(Math.min(c, m), y);
//		
//		c = (c - k) / (1 - k);
//		m = (m - k) / (1 - k);
//		y = (y - k) / (1 - k);

		double c = 1.0 - r;
		double m = 1.0 - g;
		double y = 1.0 - b;
		double k = 0.0;
		
		if (Double.compare(c, m) == 0 && Double.compare(m, y) == 0) {
			k = c;
			c = m = y = 0.0;
		}
		
		return new CMYKColor(c, m, y, k, a);
	}

	public static RGBColor fromAwtColor(Color color) {
		return new RGBColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static Color toAwtColor(RGBColor rgb) {
		return new Color((float)rgb.getR(), (float)rgb.getG(), (float)rgb.getB(), (float)rgb.getA());
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		RGBColor other = (RGBColor) obj;
		if (this.r != other.r) {
			return false;
		}
		if (this.g != other.g) {
			return false;
		}
		if (this.b != other.b) {
			return false;
		}
		if (this.a != other.a) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		
		int hash = 1;
		hash = hash * prime + Double.valueOf(r).hashCode();
		hash = hash * prime + Double.valueOf(g).hashCode();
		hash = hash * prime + Double.valueOf(b).hashCode();
		hash = hash * prime + Double.valueOf(a).hashCode();
		
		return hash;
	}
}
