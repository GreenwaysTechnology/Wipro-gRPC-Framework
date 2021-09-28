package com.example.grpc.types;

import com.wipro.models.Credentials;
import com.wipro.models.EmailCredentials;
import com.wipro.models.PhoneOTP;

public class OneOfMain {
    public static void main(String[] args) {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("admin@gmail.com")
                .setPassword("admin")
                .build();
        PhoneOTP phoneOTP = PhoneOTP.newBuilder()
                .setNumber(334334234)
                .setCode(200)
                .build();
        Credentials credentials = Credentials.newBuilder()
                .setPhoneMode(phoneOTP)
                .build();
        login(credentials);
        credentials = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .build();
        login(credentials);
    }

    private static void login(Credentials credentials) {
        System.out.println(credentials);
        switch (credentials.getModeCase()) {
            case EMAILMODE:
                System.out.println(credentials.getEmailMode().getEmail());
                break;
            case PHONEMODE:
                System.out.println(credentials.getPhoneMode().getNumber());
                break;
        }
    }
}
