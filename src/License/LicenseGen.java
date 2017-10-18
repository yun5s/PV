package License;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class LicenseGen {

    public String createLicenseKey(String email, String firstName, String payType) {
        final String s = email + "|" + firstName + "|" + payType;
        final HashFunction hashFunction = Hashing.sha1();
        final HashCode hashCode = hashFunction.hashString(s);
        final String upper = hashCode.toString().toUpperCase();
        return group(upper);
    }

    private String group(String s) {
        String result = "";
        for (int i=0; i < s.length(); i++) {
            if (i%6==0 && i > 0) {
                result += '-';
            }
            result += s.charAt(i);
        }
        return result;
    }

}