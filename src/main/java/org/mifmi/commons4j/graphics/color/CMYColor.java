/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.color;

public class CMYColor implements IColor {
	private double c; // [0, 1]
	private double m; // [0, 1]
	private double y; // [0, 1]
	private double a; // [0, 1]
	private RGBColor rgb = null;

	public CMYColor(double c, double m, double y) {
		this(c, m, y, 1.0);
	}
	
	public CMYColor(double c, double m, double y, double a) {
		this.c = c;
		this.m = m;
		this.y = y;
		this.a = a;
	}

	public double getC() {
		return c;
	}

	public double getM() {
		return m;
	}

	public double getY() {
		return y;
	}

	public double getA() {
		return a;
	}
	
	public RGBColor toRGB() {
		if (rgb == null) {
			rgb = RGBColor.fromCMY(this);
		}
		return rgb;
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
		
		CMYColor other = (CMYColor) obj;
		if (this.c != other.c) {
			return false;
		}
		if (this.m != other.m) {
			return false;
		}
		if (this.y != other.y) {
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
		hash = hash * prime + Double.valueOf(c).hashCode();
		hash = hash * prime + Double.valueOf(m).hashCode();
		hash = hash * prime + Double.valueOf(y).hashCode();
		hash = hash * prime + Double.valueOf(a).hashCode();
		
		return hash;
	}
}
