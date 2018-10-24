package com.feloro;

import java.util.Arrays;

public class Main {

    static Long[] possibleArrays = {1L,2L,3L,5L,7L,11L,13L};

    public static void main(String[] args) {
        Long controlBits = 4L;
        Long[] message = {3L,1L};
        Long galuaCount = 7L;
        Long galuaPrimitive = 5L;
        Long[] sizedMessage = Arrays.copyOfRange(message, 0, (int) (message.length+controlBits));
        Long[] coded = new Long[sizedMessage.length];
        Arrays.fill(coded, 0L);
        Arrays.fill(sizedMessage, message.length, sizedMessage.length, 0L);
        for (int i = 0; i<sizedMessage.length; i++) {
            for (int j = 0; j<sizedMessage.length; j++) {
                coded[i] += (sizedMessage[j] * (long) powWithMod(galuaPrimitive, (long) (j * i), galuaCount))% galuaCount;
            }
            coded[i] %= galuaCount;
        }

        printMessage(sizedMessage);
        printMessage(coded);

        Long[] errors = {0L,0L,0L,2L,0L, 0L};
        for (int i = 0; i < coded.length; i++) coded[i]=(coded[i]+errors[i])%galuaCount;

        printMessage(decode(coded, galuaPrimitive, galuaCount));
    }



    public static Long[] decode(Long[] message, Long galuaPrimitive, Long galuaCount){
        Long[] decoded = new Long[message.length];
        Arrays.fill(decoded, 0L);
        for (int i = 0; i < message.length; i++) {
            for (int j=0; j<message.length; j++) {
                decoded[i]+=div(message[j],powWithMod(galuaPrimitive, (long) (j * i), galuaCount), galuaCount);
            }
            decoded[i] = div(decoded[i], galuaCount-1, galuaCount);
        }
        return decoded;
    }

    public static Long powWithMod(Long x, Long y, Long mod) {
        if (y==0) return 1L;
        Long result = 1L;
        while (y>0) {
            result=(result*x)%mod;
            y--;
        }
        return result;
    }

    public static Long div(Long x, Long y, Long mod) {
        int counter = 0;
        while (y*counter%mod!=1)counter++;
        return (x*counter)%mod;
    }

    public static void printMessage(Long[] message){
        for (Long code: message) System.out.print(code+" ");
        System.out.println();
    }
}
