public class CBC {
    private byte[] toByte(int[] data){
        byte[] bytes = new byte[data.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) data[i];
        }
        return bytes;
    }

    private int[] toInt(byte[] data) {
        int intArr[] = new int[data.length];
        for(int i = 0; i < intArr.length; i++) {
            int ye = data[i];
            if(ye < 0) {
                ye = 256 + ye;
            }
            intArr[i] = ye;
        }
        return intArr;
    }

    private int getIv(long iv) {
        return (int)((iv >= 256)? iv%256:iv);
    }

    public byte[] enc(byte[] msg, String key, long iv) {
        int ivV = getIv(iv);
        String msgDt = new String(msg);
        String keyDt = key;
        if (msgDt.length() > key.length()) {
            for (int y=0;y < Math.floor(msgDt.length()/key.length())+1;y++) {
                keyDt += key;
            }
        }
        keyDt = keyDt.substring(0, msgDt.length());
        int[] bin = new int[msgDt.length()];
        for(int y=0;y < msgDt.length();y++) {
            long msgb = msgDt.codePointAt(y);
            long kyb = keyDt.codePointAt(y);
            long enc = (msgb ^ ivV) ^ kyb;
            String kyk = String.format("%8s",Long.toBinaryString(enc)).replaceAll(" ","0");
            kyk += (kyk.charAt(0) == '0')?'0':'1';
//            System.out.println(enc);
            int ygw = (int)Long.parseLong(kyk.substring(kyk.length()-8,9),2);
            bin[y] += ygw;
            System.out.println(ygw);
            ivV = ygw;
        }
        return toByte(bin);
    }

    public String dec(byte[] msg, String key, long iv) {
        int ivV = getIv(iv);
        int[] msgDt = toInt(msg);
        String keyDt = key;
        if (msgDt.length > key.length()) {
            for (int y=0;y < Math.floor(msgDt.length/key.length())+1;y++) {
                keyDt += key;
            }
        }
        keyDt = keyDt.substring(0, msgDt.length);
        char[] bin = new char[msgDt.length];
        for(int y=0;y < msgDt.length;y++) {
            String msgu = String.format("%8s",Long.toBinaryString(msgDt[y])).replaceAll(" ","0");
            msgu = ((msgu.charAt(msgu.length()-1) == '0')?'0':'1') + msgu;
            long msgb = Long.parseLong(msgu.substring(0,8),2);
            long kyb = keyDt.codePointAt(y);
            long dec = (msgb ^ kyb) ^ ivV;
            bin[y] += (char)dec;
//            System.out.println(dec);
            ivV = msgDt[y];
        }
        String hj = "";
        for(char b : bin) {
            hj += b;
        }
        return hj;
    }
}
