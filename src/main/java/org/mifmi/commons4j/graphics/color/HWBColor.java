/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.graphics.color;

public class HWBColor implements IColor {

	private double h; // [0, 360)
	private double w; // [0, 1]
	private double b; // [0, 1]
	private double a; // [0, 1]
	private RGBColor rgb = null;

	public HWBColor(double h, double w, double b) {
		this(h, w, b, 1.0);
	}
	
	public HWBColor(double h, double w, double b, double a) {
		this.h = h;
		this.w = w;
		this.b = b;
		this.a = a;
	}

	public double getH() {
		return h;
	}

	public double getW() {
		return w;
	}

	public double getB() {
		return b;
	}

	public double getA() {
		return a;
	}
	
	public RGBColor toRGB() {
		if (rgb == null) {
			rgb = RGBColor.fromHWB(this);
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
		
		HWBColor other = (HWBColor) obj;
		if (this.h != other.h) {
			return false;
		}
		if (this.w != other.w) {
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
		hash = hash * prime + Double.valueOf(h).hashCode();;
		hash = hash * prime + Double.valueOf(w).hashCode();
		hash = hash * prime + Double.valueOf(b).hashCode();
		hash = hash * prime + Double.valueOf(a).hashCode();
		
		return hash;
	}
}
