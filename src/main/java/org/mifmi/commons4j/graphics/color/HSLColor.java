/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.color;

public class HSLColor implements IColor {

	private double h; // [0, 360)
	private double s; // [0, 1]
	private double l; // [0, 1]
	private double a; // [0, 1]
	private RGBColor rgb = null;

	public HSLColor(double h, int s, int l) {
		this(h, s, l, 1.0);
	}
	
	public HSLColor(double h, double s, double l, double a) {
		this.h = h;
		this.s = s;
		this.l = l;
		this.a = a;
	}

	public double getH() {
		return h;
	}

	public double getS() {
		return s;
	}

	public double getL() {
		return l;
	}

	public double getA() {
		return a;
	}
	
	public RGBColor toRGB() {
		if (rgb == null) {
			rgb = RGBColor.fromHSL(this);
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
		
		HSLColor other = (HSLColor) obj;
		if (this.h != other.h) {
			return false;
		}
		if (this.s != other.s) {
			return false;
		}
		if (this.l != other.l) {
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
		hash = hash * prime + Double.valueOf(h).hashCode();
		hash = hash * prime + Double.valueOf(s).hashCode();
		hash = hash * prime + Double.valueOf(l).hashCode();
		hash = hash * prime + Double.valueOf(a).hashCode();
		
		return hash;
	}
}
