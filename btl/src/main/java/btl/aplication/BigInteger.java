package btl.aplication;

import java.util.ArrayList;

public class BigInteger {
    private DigitList digits;
    private int sign;

    public BigInteger() {
        this.digits = null;
        this.sign = 1;
    }

    public BigInteger(DigitList L) {
        this.digits = L;
        this.sign = 1;
    }

    public BigInteger(int i, DigitList L) {
        this.digits = L;	
        this.sign = sgn(i);
    }

    public BigInteger(int i) {
        this.digits = DigitList.digitize(Math.abs(i));
        this.sign = sgn(i);
    }

    public BigInteger(String str) {
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            this.digits = DigitList.digitize(str);
            this.sign = -1;
        } else {
            this.digits = DigitList.digitize(str);
            this.sign = 1;
        }
    }

    public DigitList getDigits() {
        return this.digits;
    }

    public int getSign() {
        return this.sign;
    }

    public void setDigits(DigitList digits) {
        this.digits = digits;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    private int sgn(int i) {
        if (i < 0)
            return -1;
        else
            return 1;
    }

    public int length() {
        if (this.digits == null)
            return 0;
        else
            return this.digits.length();
    }

    public BigInteger copy() {
        if (this.digits == null)
            return new BigInteger(0);
        else
            return new BigInteger(this.sign, this.digits.copy());
    }

    public BigInteger trimDigit() {
        return new BigInteger(this.sign, DigitList.trimDigitList(this.digits));
    }

    public boolean equals(Object obj) {
        if (obj instanceof BigInteger) {
            BigInteger other = (BigInteger) obj;
            if (this.sign == other.sign && DigitList.compareDigitLists(this.digits, other.digits) == 0) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (this.digits != null) {
            String integer = "";
            DigitList tmp = this.digits;
            integer = tmp.getDigit() + integer;
            tmp = tmp.getNextDigit();
            while (tmp != null) {
                integer = tmp.getDigit() + integer;
                tmp = tmp.getNextDigit();
            }
            return ((this.sign == -1) ? "-" : "") + integer;
        } else
            return "";
    }
	
	public BigInteger leftDigits(int n) {
        return new BigInteger(this.digits.leftDigits(n));
    }

    public BigInteger rightDigits(int n) {
        return new BigInteger(this.digits.rightDigits(n));
    }

    public BigInteger shift(int n) {
		if (n < 1)
            return this;
        else {
            BigInteger clone = this.copy();
            clone.digits = new DigitList(0, clone.digits);
            return clone.shift(n - 1);
        }
    }

    /******************************** STUDENT'S CODE ********************************/

    public BigInteger add(BigInteger other) {
        // complete this code
        if (this.sign == other.sign)
            return new BigInteger(this.sign, DigitList.addDigitLists(0, this.digits, other.digits));
        else{
			if (DigitList.compareDigitLists(this.digits, other.digits) == 0) {
				return new BigInteger(Math.abs(this.sign), new DigitList(0, null));
			} else if (DigitList.compareDigitLists(this.digits, other.digits) == 1) {
				return new BigInteger(this.sign, DigitList.subDigitLists(0, this.digits, other.digits));

			} else {
				return new BigInteger(other.sign, DigitList.subDigitLists(0, other.digits, this.digits));
			}
		}
        
    }

    public BigInteger sub(BigInteger other) {
        // code here
		if (this.sign != other.sign) {
			return new BigInteger(this.sign, DigitList.addDigitLists(0, this.digits, other.digits));
		}
//		else if(this.sign != other.sign&&this.digits.getDigit()<other.digits.getDigit()) {
//				return new BigInteger(other.sign, DigitList.addDigitLists(0, this.digits, other.digits));
//		}

		else {
			int cmp = DigitList.compareDigitLists(this.digits, other.digits);
			if (cmp == 0 ) {
				return new BigInteger(Math.abs(this.sign), new DigitList(0, null));
			}
			if (cmp == 1) {
				return new BigInteger(this.sign, DigitList.subDigitLists(0, this.digits, other.digits));
			} else {
				return new BigInteger(-this.sign, DigitList.subDigitLists(0, other.digits, this.digits));
			}
		}
       
    }    
	public BigInteger addZero(int count) {
		DigitList tmp = this.digits;

		while (tmp.getNextDigit() != null)
			tmp = tmp.getNextDigit();

		while (count > 0) {
			tmp.setNextDigit(new DigitList(0, null));
			tmp = tmp.getNextDigit();
			count--;
		}

		return new BigInteger(this.sign, tmp);
	}

    public BigInteger mul(BigInteger other) {
        // code here
       if (this.digits.length() > other.digits.length()) {
			other.addZero(this.digits.length() - other.digits.length());
		}
		if (this.digits.length() < other.digits.length()) {
			this.addZero(other.digits.length() - this.digits.length());
		}

		BigInteger xleft = this.leftDigits(this.digits.length() / 2);
		BigInteger xright = this.rightDigits(this.digits.length() - this.digits.length() / 2);
		BigInteger yleft = other.leftDigits(other.digits.length() / 2);
		BigInteger yright = other.rightDigits(other.digits.length() - other.digits.length() / 2);

		if (this.digits.length() == 1 && other.digits.length() == 1) {
			return new BigInteger(this.sign * other.sign,	DigitList.trimDigitList(DigitList.digitize(this.digits.getDigit() * other.digits.getDigit())));

		} else {

			BigInteger tmp1 = xleft.mul(yleft);
			BigInteger tmp2 = (xright.mul(yleft).add(xleft.mul(yright))).shift(this.digits.length() / 2);
			BigInteger tmp3 = (xright.mul(yright)).shift(2 * (this.digits.length() / 2));
			BigInteger tmp4 = tmp1.add(tmp2.add(tmp3));
			return new BigInteger(this.sign * other.sign, DigitList.trimDigitList(tmp4.digits));

		}
    }

    public static BigInteger pow(BigInteger X, BigInteger Y) {
    	
    	
    	if(DigitList.compareDigitLists(Y.digits, DigitList.digitize(1))==0) {
    		return new BigInteger(X.sign,X.digits);
    	}
    	else {
    		BigInteger count =Y.sub(new BigInteger(1,DigitList.digitize(1)));
    		Y.setDigits(count.digits);
    		return new BigInteger(X.sign,(X.mul(pow(X,Y))).digits);
    	}
        
    }

    public static BigInteger factorial(BigInteger X) {
        // code here
    	if(DigitList.compareDigitLists(X.digits, DigitList.digitize(1))==0) {
    		return new BigInteger(X.sign,X.digits);
    	}
    	else {
    		BigInteger count =X.sub(new BigInteger(1,DigitList.digitize(1)));
    		return new BigInteger(X.sign,(X.mul(factorial(count))).digits);
    	}
        
    }

    public static BigInteger computeValue(ArrayList<BigInteger> operandArr, ArrayList<Character> operatorArr) {
		// complete - and * operator
		BigInteger output = operandArr.get(0);
        for (int j = 0; j < operatorArr.size(); j++) {
            switch (operatorArr.get(j)) {
                case '+':
                    output = output.add(operandArr.get(j + 1));
                    break;
                case '-':
					output = output.sub(operandArr.get(j + 1));
                    break;
                case '*':
					output = output.mul(operandArr.get(j + 1));
                    break;
                default:
                    break;
            }
        }
        return output;
    }
}