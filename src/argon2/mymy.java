package de.mkammerer.argon2;

public class mymy {

    public static void main(String arg[]) {
        String password = "123";


        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] passwordo = password.toCharArray();

        try {
            // Hash password
            String hash = argon2.hash(2, 65536, 1, password);

            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                System.out.println(hash);
            } else {
                // Hash doesn't match password
                System.out.println("NO");

            }
        } finally {
            // Wipe confidential data
            argon2.wipeArray(passwordo);
        }
    }
}
