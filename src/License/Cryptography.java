package License;

public class Cryptography{

    public static void main(String[] args){
        String keyy="tyt";
        char mychar;
        String input = "hello";
        int ascii=0;

        char outchar;
        char charArray [] = new char[input.length()];




        String enoutput=Encrypting(input,keyy);
        String deoutput=Decrypting(enoutput,keyy);

        System.out.println(input);
        System.out.println("Encrypting to: "+enoutput);
        System.out.println("Decrypting to:"+deoutput);


    }

    public static String Encrypting(String input, String key){




        int extra = 0;
        int sum=0;
        char outchar;
        char charArray [] = new char[input.length()];
        char mychar;
        char mykey;
        int ascii=0;
        int keyy=0;
        int keyval=0;
        key = String.valueOf(charArray);

        for(int pos=0;pos<input.length();pos++){
            mychar= input.charAt(pos);
            ascii = (int) mychar;        //char to ascii
            keyval= pos+ascii;
            outchar = (char) keyval;     //ascii to string
            charArray[pos]=outchar;
        }



        for(int pos=0;pos<input.length();pos++){
            mychar= input.charAt(pos);

            mykey = key.charAt(pos);
            keyy = (int) mykey;

            ascii = (int) mychar;//char to ascii

            sum =nthPrime(keyy)+pos+ascii;
            if (sum>126){
                extra = sum%126;
                sum = extra + 32;
                if (sum>126){
                    extra = sum%126;
                    sum = extra + 32;
                }
            }

            outchar = (char) sum;     //ascii to string
            charArray[pos]=outchar;
        }
        String output = String.valueOf(charArray);
        return output;
    }

    //------------//---------------------<DECRYPT>---------------------------------------------------
    public static String Decrypting(String output, String key){

        char mychar,outchar;
        int deascii=0;
        char mykey;
        char charArray [] = new char[output.length()];
        int keys=0;
        int sum=0;
        int ascii=0;

        int keyy=0;
        int keyval=0;
        key = String.valueOf(charArray);

        for(int pos=0;pos<output.length();pos++){
            mychar= output.charAt(pos);
            ascii = (int) mychar;        //char to ascii
            keyval= pos+ascii;
            outchar = (char) keyval;     //ascii to string
            charArray[pos]=outchar;
        }
        //sum =nthPrime(key)+pos+deascii;

        //ascii = sum -nprime-pos;
        for(int pos=0;pos<output.length();pos++){


            mykey = key.charAt(pos);
            keys = (int) mykey;

            deascii = keys - pos;
            outchar = (char) deascii;     //ascii to string
            charArray[pos]=outchar;
        }
        output = String.valueOf(charArray);
        return output;
    }

    //------------//---------------------<PRIME FINDER>---------------------------------------------------
    // Count number of set bits in an int
    public static int popCount(int n) {
        n -= (n >>> 1) & 0x55555555;
        n = ((n >>> 2) & 0x33333333) + (n & 0x33333333);
        n = ((n >> 4) & 0x0F0F0F0F) + (n & 0x0F0F0F0F);
        return (n * 0x01010101) >> 24;
    }

    // Speed up counting by counting the primes per
// array slot and not individually. This yields
// another factor of about 1.24 or so.
    public static int nthPrime(int n) {
        if (n < 2) return 2;
        if (n == 2) return 3;
        if (n == 3) return 5;
        int limit, root, count = 2;
        limit = (int)(n*(Math.log(n) + Math.log(Math.log(n)))) + 3;
        root = (int)Math.sqrt(limit);
        switch(limit%6) {
            case 0:
                limit = 2*(limit/6) - 1;
                break;
            case 5:
                limit = 2*(limit/6) + 1;
                break;
            default:
                limit = 2*(limit/6);
        }
        switch(root%6) {
            case 0:
                root = 2*(root/6) - 1;
                break;
            case 5:
                root = 2*(root/6) + 1;
                break;
            default:
                root = 2*(root/6);
        }
        int dim = (limit+31) >> 5;
        int[] sieve = new int[dim];
        for(int i = 0; i < root; ++i) {
            if ((sieve[i >> 5] & (1 << (i&31))) == 0) {
                int start, s1, s2;
                if ((i & 1) == 1) {
                    start = i*(3*i+8)+4;
                    s1 = 4*i+5;
                    s2 = 2*i+3;
                } else {
                    start = i*(3*i+10)+7;
                    s1 = 2*i+3;
                    s2 = 4*i+7;
                }
                for(int j = start; j < limit; j += s2) {
                    sieve[j >> 5] |= 1 << (j&31);
                    j += s1;
                    if (j >= limit) break;
                    sieve[j >> 5] |= 1 << (j&31);
                }
            }
        }
        int i;
        for(i = 0; count < n; ++i) {
            count += popCount(~sieve[i]);
        }
        --i;
        int mask = ~sieve[i];
        int p;
        for(p = 31; count >= n; --p) {
            count -= (mask >> p) & 1;
        }
        return 3*(p+(i<<5))+7+(p&1);
    }
//------------//--------------------------<PRIME FINDER>----------------------------------------------

}