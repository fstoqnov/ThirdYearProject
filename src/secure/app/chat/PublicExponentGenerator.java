package secure.app.chat;

public class PublicExponentGenerator {
	
	public String convertID(String userId) {
		String convertedId = "";
		char[] ch  = userId.toCharArray();
		for(char c : ch) {
			if(Character.isLetter(c)) {
				int temp = (int)c;
				int temp_integer = 96; //for lower case
				if(temp<=122 & temp>=97) {
					convertedId = convertedId + (temp-temp_integer);
				}
			} else {
				convertedId = convertedId + c;
			}
		}
		System.out.println("Alphabetical positions " + convertedId);
		return convertedId;
	}
	
	
	public String generateExponent(String originalId) {
		String alphaId = convertID(originalId);
		long num = 1;
		if(alphaId.length() > 9) {
			num = Long.parseLong(alphaId.substring(0, alphaId.length() - 3));
		} else if(alphaId.length() > 8) {
			num = Long.parseLong(alphaId.substring(0, alphaId.length() - 2));
			System.out.println(num);
		}else if(alphaId.length() > 10) {
			num = Long.parseLong(alphaId.substring(0, alphaId.length() - 4));
			System.out.println(num);
		} else {
			num = Long.parseLong(alphaId);
		}
		//Long num= new Long(convertID(alphaId));
        long diff1=0,diff2=0;
        long num1=0,num2=0;
      //No end limit as when prime will be found we will break the loop.	
        for(long i=num;;i++) {
            if(isPrime(i)) {
                diff1=i-num;
                num1=i;
                break;
            }
        }
        for(long i=num;;i--) {
            if(isPrime(i)) {
                diff2=num-i;
                num2=i;
                break;
            }
        }
        if(diff1<diff2) {
        	System.out.println("Public key " + num1);
            return String.valueOf(num1);
        } else {
        	System.out.println("Public key " + num2);
        	return String.valueOf(num2);
        }
    }
	
	public boolean isPrime(long n) {
        long c=0;
        for(long i=1;i<=n;i++) {
            if(n%i==0)
                c++;
        }
        if(c==2) {
            return true;
        } else {
            return false;
        }
    }
}
