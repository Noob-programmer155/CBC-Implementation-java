# CBC-Implementation-java
CBC algorithm implementation with java 

#How To Use It
```
String keys = "keys$#^js12";
long iv = 100;
CBC gd = new CBC();
byte[] enc = gd.enc("Example Plain Text1845$%&1927".getBytes(StandardCharsets.UTF_8),keys,iv);

//To get Encryption data to string
System.out.println(new String(enc));

//Decrypt
System.out.println(gd.dec(enc,keys,iv));
```
