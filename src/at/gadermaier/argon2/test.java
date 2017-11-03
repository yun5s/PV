package at.gadermaier.argon2;

public class test {
    public static void main(String args[]) {

        Argon2 argon = new Argon2();
        char[] password = "Hello".toCharArray();

        // Generate salt
        String salt = argon.generateSalt();

        // Hash password - Builder pattern
        String hash = Argon2Factory.create()
                .setIterations(2)
                .setMemory(14)
                .setParallelism(1)
                .hash(password, salt);

        System.out.println(salt);

        System.out.println(hash);
    }
}
