/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.color;

public class HSVColor implements IColor {

	private double h; // [0, 360)
	private double s; // [0, 1]
	private double v; // [0, 1]
	private double a; // [0, 1]
	private RGBColor rgb = null;

	public HSVColor(double h, double s, double v) {
		this(h, s, v, 1.0);
	}
	
	public HSVColor(double h, double s, double v, double a) {
		this.h = h;
		this.s = s;
		this.v = v;
		this.a = a;
	}

	public double getH() {
		return h;
	}

	public double getS() {
		return s;
	}

	public double getV() {
		return v;
	}

	public double getA() {
		return a;
	}
	
	public RGBColor toRGB() {
		if (rgb == null) {
			rgb = RGBColor.fromHSV(this);
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
		
		HSVColor other = (HSVColor) obj;
		if (this.h != other.h) {
			return false;
		}
		if (this.s != other.s) {
			return false;
		}
		if (this.v != other.v) {
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
		hash = hash * prime + Double.valueOf(h).hashCode();;
		hash = hash * prime + Double.valueOf(s).hashCode();
		hash = hash * prime + Double.valueOf(v).hashCode();
		hash = hash * prime + Double.valueOf(a).hashCode();
		
		return hash;
	}
}
